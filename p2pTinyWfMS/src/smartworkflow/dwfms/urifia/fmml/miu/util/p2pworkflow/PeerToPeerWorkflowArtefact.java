package smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow;

import java.io.Serializable;
import java.util.ArrayList;

import com.sun.tools.javac.util.Pair;

public class PeerToPeerWorkflowArtefact implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static final String 
			TASK_STATUS_CLOSED = "Closed",
			TASK_STATUS_LOCKED = "Locked",
			TASK_STATUS_UNLOCKED = "Unlocked";
	
	private Task task;
	private String status;
	private Stakeholder creator;
	private Production production;
	private ArrayList<PeerToPeerWorkflowArtefact> sons;
	
	public PeerToPeerWorkflowArtefact(
			Task task){
		this.task = task;
		this.sons = new ArrayList<PeerToPeerWorkflowArtefact>();
		this.status = TASK_STATUS_CLOSED;
	}
	
	public void setSons(ArrayList<PeerToPeerWorkflowArtefact> sons) {
		this.sons = sons;
	}

	public PeerToPeerWorkflowArtefact(
			Task task, String status, Stakeholder creator) {
		this.task = task;
		this.status = status;
		this.creator = creator;
		this.sons = new ArrayList<PeerToPeerWorkflowArtefact>();
	}
	
	public boolean isClosed(){
		return this.status.equals(TASK_STATUS_CLOSED);
	}
	
	public boolean isLocked(){
		return this.status.equals(TASK_STATUS_LOCKED);
	}
	
	public boolean isUnlocked(){
		return this.status.equals(TASK_STATUS_UNLOCKED);
	}

	public Task getTask() {
		return task;
	}

	public Stakeholder getCreator() {
		return creator;
	}

	public Production getProduction() {
		return production;
	}

	public ArrayList<Production> getProductions() {
		ArrayList<Production> list = new ArrayList<Production>();
		list.add(production);
		for(PeerToPeerWorkflowArtefact son : sons){
			ArrayList<Production> l = son.getProductions();
			for(Production prod : l)
				if(!list.contains(prod))
					list.add(prod);
		}
		return list;
	}
	
	public void renameSymbol(String symb1, String symb2) {
		if(task.getSymbol().equals(symb1)){
			task.setSymbol(symb2);
			production.setProdLhs(symb2);
		}
		for(int i = 0; i < sons.size(); i++){
			if(sons.get(i).getTask().getSymbol().equals(symb1)){
				sons.get(i).getTask().setSymbol(symb2);
				sons.get(i).getProduction().setProdLhs(symb2);
				production.getProdRhs().getRhsSymbols().remove(i);
				production.getProdRhs().getRhsSymbols().add(i, symb2);
			}
			sons.get(i).renameSymbol(symb1, symb2);
		}
	}
	
	@SuppressWarnings("unchecked")
	public PeerToPeerWorkflowArtefact clone(){
		Task t = new Task(task.getSymbol(), task.getType(), task.getDesc());
		t.setStatus(task.getStatus());
		PeerToPeerWorkflowArtefact artefact = new PeerToPeerWorkflowArtefact(t, status, creator);
		artefact.setProduction(new Production(production.getProdName(), production.getProdLhs(), new ProductionRhs(production.getProdType(), (ArrayList<String>)production.getProdRhs().getRhsSymbols().clone())));
		ArrayList<PeerToPeerWorkflowArtefact> sonsOf = new ArrayList<PeerToPeerWorkflowArtefact>();
		for(PeerToPeerWorkflowArtefact son : sons)
			sonsOf.add(son.clone());
		artefact.setSons(sonsOf);
		return artefact;
	}
	
	public boolean unlockBuds(Stakeholder stakeholder, ArrayList<String> writable){
		if(isLocked() && (creator.equals(stakeholder) || writable.contains(task.getSymbol()))){
			setStatus(TASK_STATUS_UNLOCKED);
			return false;
		}
		if(!isClosed())
			return false;
		boolean pred = true;
		if(production.isNoneProduction() || production.isParallelProduction()){
			for(PeerToPeerWorkflowArtefact son : sons){
				boolean b = son.unlockBuds(stakeholder, writable);
				pred = b && pred;
			}
			return pred;
		}
		for(int i = 0; i < sons.size() && pred; i++)
			pred = sons.get(i).unlockBuds(stakeholder, writable);
		return pred;
	}
	
	public boolean unlockBuds(ArrayList<String> writable){
		if(isLocked() && (writable.contains(task.getSymbol()))){
			setStatus(TASK_STATUS_UNLOCKED);
			return false;
		}
		if(!isClosed())
			return false;
		boolean pred = true;
		if(production.isNoneProduction() || production.isParallelProduction()){
			for(PeerToPeerWorkflowArtefact son : sons){
				boolean b = son.unlockBuds(writable);
				pred = b && pred;
			}
			return pred;
		}
		for(int i = 0; i < sons.size() && pred; i++)
			pred = sons.get(i).unlockBuds(writable);
		return pred;
	}
	
	public void lockBuds(){
		if(isLocked())
			return;
		if(isUnlocked()){
			setStatus(TASK_STATUS_LOCKED);
			return;
		}
		for(int i = 0; i < sons.size(); i++)
			sons.get(i).lockBuds();
	}
	
	public ArrayList<Pair<String, Task>> getReadyTasks(){
		ArrayList<Pair<String, Task>> list = new ArrayList<Pair<String, Task>>();
		if(isUnlocked()){
			Pair<String, Task> pair = new Pair<>("", task);
			list.add(pair);
		} else {
			for(int i = 0; i < sons.size(); i++){
				ArrayList<Pair<String, Task>> l = sons.get(i).getReadyTasks("", i + 1);
				list.addAll(l);
			}
		}
		return list;
	}
	
	private ArrayList<Pair<String, Task>> getReadyTasks(String pAddr, int sonId) {
		String myAddr = pAddr != null && !pAddr.isEmpty() ? pAddr + "." + sonId : "" + sonId;
		ArrayList<Pair<String, Task>> list = new ArrayList<Pair<String, Task>>();
		if(isUnlocked()){
			Pair<String, Task> pair = new Pair<>(myAddr, task);
			list.add(pair);
		} else {
			for(int i = 0; i < sons.size(); i++){
				ArrayList<Pair<String, Task>> l = sons.get(i).getReadyTasks(myAddr, i + 1);
				list.addAll(l);
			}
		}
		return list;
	}
	
	public void prune(String adress) {
		PeerToPeerWorkflowArtefact subArt = getSubArtefact(adress);
		if(subArt != null){
			subArt.setSons(new ArrayList<PeerToPeerWorkflowArtefact>());
			subArt.getProduction().getProdRhs().setRhsType(GrammaticalModelOfWorkflow.PRODUCTION_TYPE_EPS);
			subArt.getProduction().getProdRhs().setRhsSymbols(new ArrayList<String>());
			subArt.setStatus(TASK_STATUS_LOCKED);
		}
	}

	public PeerToPeerWorkflowArtefact getSubArtefact(String rootAdress){
		if(rootAdress.equals("")){
			return this;
		}
		for(int i = 0; i < sons.size(); i++){
			if(rootAdress.startsWith("" + (i + 1)))
				return sons.get(i).getSubArtefact(rootAdress, "", i + 1);
		}
		return null;
	}
	
	private PeerToPeerWorkflowArtefact getSubArtefact(String rootAdress,
			String pAddr, int sonId) {
		String myAddr = pAddr != null && !pAddr.isEmpty() ? pAddr + "." + sonId : "" + sonId;
		if(rootAdress.equals(myAddr)){
			return this;
		}
		for(int i = 0; i < sons.size(); i++){
			if(rootAdress.startsWith(myAddr + "." + (i + 1)))
				return sons.get(i).getSubArtefact(rootAdress, myAddr, i + 1);
		}
		return null;
	}

	public ArrayList<String> getBudsAboveAdresses(PeerToPeerWorkflowArtefact artefact, ArrayList<String> readable){
		ArrayList<String> list = new ArrayList<>();
		if(isClosed() && artefact != null && artefact.isPrefixOf(this)){
			if(!artefact.production.isEpsilonProduction()){
				for(int i = 0; i < sons.size(); i++){
					ArrayList<String> l = sons.get(i).getBudsAboveAdresses(artefact.getSons().get(i), readable, "", i + 1);
					list.addAll(l);
				}
			}else{
				for(int i = 0; i < sons.size(); i++){
					ArrayList<String> l = sons.get(i).getBudsAboveAdresses(null, readable, "", i + 1);
					list.addAll(l);
				}
			}
		}
		return list;
	}

	private ArrayList<String> getBudsAboveAdresses(PeerToPeerWorkflowArtefact artefact, ArrayList<String> readable, String pAddr, int sonId) {
		String myAddr = pAddr != null && !pAddr.isEmpty() ? pAddr + "." + sonId : "" + sonId;
		ArrayList<String> list = new ArrayList<>();
		if(isClosed()){
			if(artefact == null && !readable.contains(task.getSymbol())){
				list.add(myAddr);
			} else {
				if(artefact != null && !artefact.production.isEpsilonProduction()){
					for(int i = 0; i < sons.size(); i++){
						ArrayList<String> l = sons.get(i).getBudsAboveAdresses(artefact.getSons().get(i), readable, myAddr, i + 1);
						list.addAll(l);
					}
				}else{
					for(int i = 0; i < sons.size(); i++){
						ArrayList<String> l = sons.get(i).getBudsAboveAdresses(null, readable, myAddr, i + 1);
						list.addAll(l);
					}
				}
			}
		}
		return list;
	}

	public void changeCreator(ArrayList<String> virtualTasks,
			Stakeholder newCreator) {
		if(virtualTasks.contains(task.getSymbol())){
			creator = newCreator;
		}
		for(PeerToPeerWorkflowArtefact artefact : sons)
			artefact.changeCreator(virtualTasks, newCreator);
	}
	
	public boolean isEqualTo(PeerToPeerWorkflowArtefact gArt,
			ArrayList<String> gArtVirtualTasks, ArrayList<String> myVirtualTasks) {
		if((myVirtualTasks.contains(task.getSymbol()) && !gArtVirtualTasks.contains(gArt.getTask().getSymbol()))
				|| (!myVirtualTasks.contains(task.getSymbol()) && gArtVirtualTasks.contains(gArt.getTask().getSymbol())))
			return false;
		if(!myVirtualTasks.contains(task.getSymbol()) && !task.getSymbol().equals(gArt.getTask().getSymbol()))
			return false;
		if(!production.getProdType().equals(gArt.getProduction().getProdType()) ||
				sons.size() != gArt.getSons().size())
			return false;
		for(int i = 0; i < sons.size(); i++)
			if(!sons.get(i).isEqualTo(gArt.getSons().get(i), myVirtualTasks, gArtVirtualTasks))
				return false;
		return true;
	}
	
	public boolean isEqualTo(PeerToPeerWorkflowArtefact gArt) {
		return isEqualTo(gArt, new ArrayList<String>(), new ArrayList<String>());
	}
	
	public boolean isPrefixOf(PeerToPeerWorkflowArtefact gArt,
			ArrayList<String> gArtVirtualTasks, ArrayList<String> myVirtualTasks) {
		if((myVirtualTasks.contains(task.getSymbol()) && !gArtVirtualTasks.contains(gArt.getTask().getSymbol()))
				|| (!myVirtualTasks.contains(task.getSymbol()) && gArtVirtualTasks.contains(gArt.getTask().getSymbol())))
			return false;
		if(!myVirtualTasks.contains(task.getSymbol()) && !task.getSymbol().equals(gArt.getTask().getSymbol()))
			return false;
		if((!production.getProdType().equals(gArt.getProduction().getProdType()) ||
				sons.size() != gArt.getSons().size()) && !production.isEpsilonProduction())
			return false;
		for(int i = 0; i < sons.size(); i++)
			if(!sons.get(i).isPrefixOf(gArt.getSons().get(i), myVirtualTasks, gArtVirtualTasks))
				return false;
		return true;
	}
	
	public boolean isPrefixOf(PeerToPeerWorkflowArtefact gArt) {
		return isPrefixOf(gArt, new ArrayList<String>(), new ArrayList<String>());
	}
	
	public void updatePrefix(PeerToPeerWorkflowArtefact gArt,
			ArrayList<String> myVirtualTasks) {
		if(myVirtualTasks.contains(task.getSymbol()))
			task.setSymbol(gArt.getTask().getSymbol());
		
		for(int i = 0; i < sons.size(); i++)
			sons.get(i).updatePrefix(gArt.getSons().get(i), myVirtualTasks);
	}

	public void setProduction(
			Production production) {
		this.production = production;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public void setCreator(Stakeholder creator) {
		this.creator = creator;
	}

	public ArrayList<PeerToPeerWorkflowArtefact> getSons() {
		return sons;
	}

	@Override
	public String toString() {
		String linear = task.getSymbol() + (isLocked() ? "wc" : (isUnlocked() ? "w" : "")) + "(";
		if(sons.size() == 1)
			return linear + sons.get(0).toString() + ")";
		if(sons.isEmpty())
			return linear + ")";
		String sep = production == null ? " " : (production.isSequentialProduction() ? ";" : "||");
		for(PeerToPeerWorkflowArtefact artefact : sons)
			linear += artefact.toString() + sep;
		linear = linear.substring(0, linear.length() - sep.length());
		return linear + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creator == null) ? 0 : creator.hashCode());
		result = prime * result
				+ ((production == null) ? 0 : production.hashCode());
		result = prime * result + ((sons == null) ? 0 : sons.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((task == null) ? 0 : task.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PeerToPeerWorkflowArtefact other = (PeerToPeerWorkflowArtefact) obj;
		if (production == null) {
			if (other.production != null)
				return false;
		} else if (!production.equals(other.production))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return isEqualTo(other);
	}

	public boolean contains(PeerToPeerWorkflowArtefact artefact) {
		if(artefact == null)
			return false;
		if(task.equals(artefact.task))
			return true;
		for(PeerToPeerWorkflowArtefact son : sons)
			if(son.contains(artefact))
				return true;
		return false;
	}

	public boolean lockTasksBelowBudAbove(Task task2) {
		if(task.equals(task2)){
			setStatus(PeerToPeerWorkflowArtefact.TASK_STATUS_LOCKED);
			return true;
		}
		for(PeerToPeerWorkflowArtefact son : sons)
			if(son.lockTasksBelowBudAbove(task2))
				return true;
		return false;
	}
}
