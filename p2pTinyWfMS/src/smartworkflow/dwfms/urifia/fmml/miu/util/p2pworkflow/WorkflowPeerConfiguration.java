package smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow;

import java.io.Serializable;
import java.util.ArrayList;

import smartworkflow.dwfms.urifia.fmml.miu.models.util.ModelModel;

import com.sun.tools.javac.util.Pair;

public class WorkflowPeerConfiguration implements Serializable{
	private static final long serialVersionUID = 1L;
	private PeerToPeerWorkflow peerToPeerWorkflow;
	private GrammaticalModelOfWorkflow localGMWf;
	private Stakeholder stakeholder;
	private ArrayList<String> virtualTasks;
	
	public WorkflowPeerConfiguration(
			GrammaticalModelOfWorkflow localGMWf,
			Stakeholder stakeholder, 
			PeerToPeerWorkflow peerToPeerWorkflow,
			ArrayList<String> virtualTasks) {
		this.localGMWf = localGMWf;
		this.stakeholder = stakeholder;
		this.peerToPeerWorkflow = peerToPeerWorkflow;
		this.virtualTasks = virtualTasks;
	}
	
	public ArrayList<String> getVirtualTasks() {
		return virtualTasks;
	}

	public void setVirtualTasks(ArrayList<String> virtualTasks) {
		this.virtualTasks = virtualTasks;
	}

	public PeerToPeerWorkflow getPeerToPeerWorkflow() {
		return peerToPeerWorkflow;
	}
	
	public void setPeerToPeerWorkflow(PeerToPeerWorkflow peerToPeerWorkflow) {
		this.peerToPeerWorkflow = peerToPeerWorkflow;
	}
	
	public GrammaticalModelOfWorkflow getLocalGMWf() {
		return localGMWf;
	}
	
	public void setLocalGMWf(
			GrammaticalModelOfWorkflow localGMWf) {
		this.localGMWf = localGMWf;
	}
	
	public Stakeholder getStakeholder() {
		return stakeholder;
	}
	
	public void setStakeholder(Stakeholder stakeholder) {
		this.stakeholder = stakeholder;
	}
	
	public SpecializedEditorConfiguration getSpecializedEditorConfiguration(){
		return new SpecializedEditorConfiguration(localGMWf, stakeholder, virtualTasks, peerToPeerWorkflow);
	}

	public ArrayList<String> getReadable() {
		return peerToPeerWorkflow.getAccreditation(stakeholder).getReadable();
	}
	
	public ArrayList<String> getWritable() {
		return peerToPeerWorkflow.getAccreditation(stakeholder).getWritable();
	}
	
	public ArrayList<String> getConcreteWritable() {
		ArrayList<String> list = new ArrayList<>();
		list.addAll(getWritable());
		list.addAll(virtualTasks);
		return list;
	}
	
	public boolean canLaunchProcess(){
		return stakeholder.equals(peerToPeerWorkflow.executorOf(peerToPeerWorkflow.getGrammaticalModelOfWorkflow().getAxiom()));
	}

	public WorkflowPeerStatus getInitialStatus() {
		return new WorkflowPeerStatus(this, null);
	}

	public void editVirtualTasks(PeerToPeerWorkflowArtefact artefact, PeerToPeerWorkflowArtefact previousGlobalArtefact) {
		try{
			boolean edited = true;
			while(edited){
				artefact.unlockBuds(getConcreteWritable());
				edited = false;
				ArrayList<Pair<String, Task>> readyTasks = artefact.getReadyTasks();
				for(Pair<String, Task> pair : readyTasks){
					if(pair.snd.isVirtual()){
						if(virtualTasks.contains(pair.snd.getSymbol()) || stakeholder.equals(peerToPeerWorkflow.executorOf(pair.snd.getSymbol()))){
							ArrayList<Production> candidates = localGMWf.getXProductions(pair.snd.getSymbol());
							if(candidates.size() == 1){
								Production p = candidates.get(0);
								editTask(artefact, pair.fst, p, ModelModel.getConfig().getLangValue("virtual_task_auto_completed"));
								edited = true;
							}
						}
					}
				}
				peerToPeerWorkflow.lockTasksBelowBudAbove(artefact, previousGlobalArtefact, this);
			}
		} catch (Exception e){
			
		}
	}
	
	public void editTask(PeerToPeerWorkflowArtefact artefact, String adress, Production p, String status) {
		PeerToPeerWorkflowArtefact subArt = artefact.getSubArtefact(adress);
		subArt.setProduction(p);
		subArt.setStatus(PeerToPeerWorkflowArtefact.TASK_STATUS_CLOSED);
		subArt.getTask().setStatus(status);
		ArrayList<PeerToPeerWorkflowArtefact> sons = new ArrayList<PeerToPeerWorkflowArtefact>();
		for(int i = 0; i < p.getProdRhs().getRhsSymbols().size() ; i++){
			String symb = p.getProdRhs().getRhsSymbols().get(i);
			PeerToPeerWorkflowArtefact art = new PeerToPeerWorkflowArtefact(localGMWf.getTask(symb), /*p.isSequentialProduction() && i != 0 ?*/ PeerToPeerWorkflowArtefact.TASK_STATUS_LOCKED/* : PeerToPeerWorkflowArtefact.TASK_STATUS_UNLOCKED*/, stakeholder);
			art.setProduction(new Production("", symb, new ProductionRhs(GrammaticalModelOfWorkflow.PRODUCTION_TYPE_EPS, new ArrayList<String>())));
			sons.add(art);
		}
		subArt.setSons(sons);
	}

	public ArrayList<Pair<String, Task>> getReadyTasks(PeerToPeerWorkflowArtefact artefact) {
		ArrayList<Pair<String, Task>> readyTasks = artefact.getReadyTasks();
		for(int i = 0; i < readyTasks.size(); i++){
			if(!virtualTasks.contains(readyTasks.get(i).snd.getSymbol()) && !stakeholder.equals(peerToPeerWorkflow.executorOf(readyTasks.get(i).snd.getSymbol())))
				readyTasks.remove(i--);
		}
		return readyTasks;
	}
}