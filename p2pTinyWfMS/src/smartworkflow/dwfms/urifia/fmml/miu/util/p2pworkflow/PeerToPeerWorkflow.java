package smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import com.sun.tools.javac.util.Pair;

import smartworkflow.dwfms.urifia.fmml.miu.models.util.ModelModel;

/**
 *
 * @author Ndadji Maxime
 */
public class PeerToPeerWorkflow implements Serializable{
	private static final long serialVersionUID = 1L;
	protected GrammaticalModelOfWorkflow grammaticalModelOfWorkflow;
	protected HashMap<String, Accreditation> accreditations;
	protected ArrayList<Stakeholder> stakeholders;
    
	public PeerToPeerWorkflow() {
		this.accreditations = new HashMap<String, Accreditation>();
		this.grammaticalModelOfWorkflow = new GrammaticalModelOfWorkflow();
		this.stakeholders = new ArrayList<Stakeholder>();
	}
	
	public void addStakeholder(Stakeholder...actors){
		if(actors != null){
			for(Stakeholder actor : actors)
				stakeholders.add(actor);
		}
	}

	public Stakeholder executorOf(String symb){
		for(Stakeholder stakeholder : stakeholders){
			if(accreditations.get(stakeholder.getId()).isWritable(symb))
				return stakeholder;
		}
		return null;
	}
	
	public boolean canRequestExecution(Stakeholder stakeholder, String symb){
		Accreditation accreditation = accreditations.get(stakeholder.getId());
		return accreditation != null ? accreditation.isExecutable(symb) : false;
	}
	
	public GrammaticalModelOfWorkflow getGrammaticalModelOfWorkflow() {
		return grammaticalModelOfWorkflow;
	}

	public void setGrammaticalModelOfWorkflow(
			GrammaticalModelOfWorkflow grammaticalModelOfWorkflow) {
		this.grammaticalModelOfWorkflow = grammaticalModelOfWorkflow;
	}

	public HashMap<String, Accreditation> getAccreditations() {
		return accreditations;
	}
	
	public Accreditation getAccreditation(Stakeholder stakeholder) {
		return accreditations.get(stakeholder.getId());
	}

	public void setAccreditations(HashMap<String, Accreditation> accreditations) {
		this.accreditations = accreditations;
	}
	
	public void addAccreditation(Stakeholder stakeholder, Accreditation accreditation){
		this.accreditations.put(stakeholder.getId(), accreditation);
	}
	
	public ArrayList<Stakeholder> getStakeholders(){
		return stakeholders;
	}
	
	public Stakeholder getStakeholder(String id){
		for(Stakeholder stakeholder : stakeholders)
			if(stakeholder.getId().equals(id))
				return stakeholder;
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public WorkflowPeerConfiguration projectGMWf(Stakeholder stakeholder) throws Exception{
		if(!stakeholders.contains(stakeholder))
			return null;
		GrammaticalModelOfWorkflow localGMWf = new GrammaticalModelOfWorkflow();
		Accreditation accreditation = getAccreditation(stakeholder);
		ArrayList<String> readable = (ArrayList<String>)accreditation.getReadable().clone();
		for(String symb : readable)
			localGMWf.addTask(grammaticalModelOfWorkflow.getTask(symb));
		localGMWf.setAxiom(grammaticalModelOfWorkflow.getAxiom());
		
		ArrayList<Production> localProds = new ArrayList<Production>();
		ArrayList<String> virtualTasks = new ArrayList<>();
		
		ArrayList<PeerToPeerWorkflowArtefact> globalArtefacts = grammaticalModelOfWorkflow.getArtefacts();
		
		ArrayList<PeerToPeerWorkflowArtefact> projectedArtefacts = new ArrayList<PeerToPeerWorkflowArtefact>();
		for(PeerToPeerWorkflowArtefact artefact : globalArtefacts)
			projectedArtefacts.add(projectArtefact(artefact, readable, virtualTasks));
		virtualTasks = mergeSimilarVirtualTasks(projectedArtefacts, virtualTasks, new ArrayList<String>());
		
		for(PeerToPeerWorkflowArtefact artefact : projectedArtefacts){
			ArrayList<Production> l = artefact.getProductions();
			for(Production prod : l){
				boolean contains = false;
				for(Production p : localProds){
					if(p.equalsWithoutName(prod)){
						contains = true;
						break;
					}
				}
				if(!contains)
					localProds.add(prod);
			}
		}
		
		for(String symb : virtualTasks)
			localGMWf.addVirtualTask(symb, ModelModel.getConfig().getLangValue("restructuring_task"));
		
		for(int i = 0; i < localProds.size(); i++){
			Production prod = localProds.get(i);
			prod.setProdName("P" + (i + 1));
			localGMWf.addProduction(prod);
		}
		
		WorkflowPeerConfiguration peerConfig = new WorkflowPeerConfiguration(localGMWf, stakeholder, this, virtualTasks);
		return peerConfig;
	}
	
	@SuppressWarnings("unchecked")
	private PeerToPeerWorkflowArtefact projectArtefact(
			PeerToPeerWorkflowArtefact artefact,
			ArrayList<String> readable,
			ArrayList<String> virtualTasks){
		PeerToPeerWorkflowArtefact newArtefact = new PeerToPeerWorkflowArtefact(artefact.getTask(), artefact.getStatus(), artefact.getCreator());
		newArtefact.setProduction(artefact.getProduction());
		newArtefact.setSons((ArrayList<PeerToPeerWorkflowArtefact>)artefact.getSons().clone());
		for(int i = 0; i < newArtefact.getSons().size(); i++){
			PeerToPeerWorkflowArtefact son = newArtefact.getSons().get(i);
			if(!readable.contains(son.getTask().getSymbol()) && !virtualTasks.contains(son.getTask().getSymbol())){
				if(!son.getProduction().isEpsilonProduction()){
					if(newArtefact.getProduction().getProdType().equals(son.getProduction().getProdType()) 
							|| newArtefact.getProduction().isNoneProduction() 
							|| son.getProduction().isNoneProduction()){
						newArtefact.getSons().remove(i);
						newArtefact.getSons().addAll(i--, son.getSons());
						ArrayList<String> rhs = new ArrayList<String>();
						for(PeerToPeerWorkflowArtefact s : newArtefact.getSons())
							rhs.add(s.getTask().getSymbol());
						Production p = new Production(newArtefact.getProduction().getProdName(), newArtefact.getProduction().getProdLhs(),
								new ProductionRhs(newArtefact.getProduction().getProdType(), rhs));
						if(newArtefact.getSons().isEmpty())
							p.getProdRhs().setRhsType(GrammaticalModelOfWorkflow.PRODUCTION_TYPE_EPS);
						else if(newArtefact.getSons().size() == 1)
							p.getProdRhs().setRhsType(GrammaticalModelOfWorkflow.PRODUCTION_TYPE_NONE);
						else if(newArtefact.getProduction().isNoneProduction())
							p.getProdRhs().setRhsType(son.getProduction().getProdType());
						newArtefact.setProduction(p);
					} else {
						String s = "S" + (virtualTasks.size() + 1);
						PeerToPeerWorkflowArtefact art = new PeerToPeerWorkflowArtefact(new Task(s, Task.TASK_TYPE_VIRTUAL, ModelModel.getConfig().getLangValue("restructuring_task")), son.getStatus(), son.getCreator());
						art.setSons((ArrayList<PeerToPeerWorkflowArtefact>)son.getSons().clone());
						art.setProduction(new Production(son.getProduction().getProdName(), s, 
								new ProductionRhs(son.getProduction().getProdRhs().getRhsType(), (ArrayList<String>)son.getProduction().getProdRhs().getRhsSymbols().clone())));
						virtualTasks.add(s);
						newArtefact.getSons().remove(i);
						newArtefact.getSons().add(i, projectArtefact(art, readable, virtualTasks));
						ArrayList<String> rhs = new ArrayList<String>();
						for(PeerToPeerWorkflowArtefact st : newArtefact.getSons())
							rhs.add(st.getTask().getSymbol());
						Production p = new Production(newArtefact.getProduction().getProdName(), newArtefact.getProduction().getProdLhs(),
								new ProductionRhs(newArtefact.getProduction().getProdType(), rhs));
						newArtefact.setProduction(p);
					}
				} else {
					newArtefact.getSons().remove(i--);
					ArrayList<String> rhs = new ArrayList<String>();
					for(PeerToPeerWorkflowArtefact s : newArtefact.getSons())
						rhs.add(s.getTask().getSymbol());
					Production p = new Production(newArtefact.getProduction().getProdName(), newArtefact.getProduction().getProdLhs(),
							new ProductionRhs(newArtefact.getProduction().getProdType(), rhs));
					if(newArtefact.getSons().isEmpty())
						p.getProdRhs().setRhsType(GrammaticalModelOfWorkflow.PRODUCTION_TYPE_EPS);
					else if(newArtefact.getSons().size() == 1)
						p.getProdRhs().setRhsType(GrammaticalModelOfWorkflow.PRODUCTION_TYPE_NONE);
					newArtefact.setProduction(p);
				}
			} else {
				newArtefact.getSons().remove(i);
				newArtefact.getSons().add(i, projectArtefact(son, readable, virtualTasks));
			}
		}
		virtualTasks = removeInconsistentVirtualTasks(newArtefact, virtualTasks, new ArrayList<String>());
		return newArtefact;
	}
	
	@SuppressWarnings("unchecked")
	private ArrayList<String> mergeSimilarVirtualTasks(
			ArrayList<PeerToPeerWorkflowArtefact> artefacts,
			ArrayList<String> virtualTasks,
			ArrayList<String> newVirtualTasks){
		ArrayList<Production> productions = new ArrayList<Production>();
		for(PeerToPeerWorkflowArtefact artefact : artefacts){
			ArrayList<Production> l = artefact.getProductions();
			for(Production prod : l)
				if(!productions.contains(prod))
					productions.add(prod);
		}
		HashMap<String, ArrayList<Production>> mapProd = new HashMap<String, ArrayList<Production>>();
		for(String symb : virtualTasks){
			ArrayList<Production> xprods = new ArrayList<Production>();
			for(Production potentialXProd : productions)
				if(potentialXProd.getProdLhs().equals(symb))
					xprods.add(potentialXProd);
			mapProd.put(symb, xprods);
		}
		newVirtualTasks = (ArrayList<String>)virtualTasks.clone();
		for(int i = 0; i < newVirtualTasks.size(); i++){
			String symb1 = newVirtualTasks.get(i);
			for(int j = i + 1; j < newVirtualTasks.size(); j++){
				String symb2 = newVirtualTasks.get(j);
				if(mapProd.get(symb1).size() == mapProd.get(symb2).size()){
					boolean containsAll = true;
					for(Production symb1Prod : mapProd.get(symb1)){
						for(Production symb2Prod : mapProd.get(symb2)){
							if(!symb1Prod.getProdRhs().equals(symb2Prod.getProdRhs())){
								containsAll = false;
								break;
							}
						}
					}
					if(containsAll){
						newVirtualTasks.remove(j--);
						for(PeerToPeerWorkflowArtefact artefact : artefacts)
							artefact.renameSymbol(symb2, symb1);
					}
				}
			}
		}
		for(int i = 0; i < newVirtualTasks.size(); i++){
			String s = "S" + (i + 1);
			if(!newVirtualTasks.get(i).equals(s)){
				for(PeerToPeerWorkflowArtefact artefact : artefacts)
					artefact.renameSymbol(newVirtualTasks.get(i), s);
				newVirtualTasks.set(i, s);
			}
		}
		return newVirtualTasks;
	}
	
	private ArrayList<String> removeInconsistentVirtualTasks(
			PeerToPeerWorkflowArtefact artefact,
			ArrayList<String> virtualTasks,
			ArrayList<String> newVirtualTasks){
		PeerToPeerWorkflowArtefact son = null;
		boolean replaced = false;
		do{
			while(artefact.getProduction().isNoneProduction() && virtualTasks.contains((son = artefact.getSons().get(0)).getTask().getSymbol())){
				artefact.getSons().remove(0);
				artefact.getSons().addAll(son.getSons());
				ArrayList<String> rhs = new ArrayList<String>();
				for(PeerToPeerWorkflowArtefact s : artefact.getSons())
					rhs.add(s.getTask().getSymbol());
				Production p = new Production(artefact.getProduction().getProdName(), artefact.getProduction().getProdLhs(),
						new ProductionRhs(son.getProduction().getProdType(), rhs));
				artefact.setProduction(p);
			}
			replaced = false;
			for(int i = 0; i < artefact.getSons().size(); i++){
				son = artefact.getSons().get(i);
				if(virtualTasks.contains(son.getTask().getSymbol())){
					if(son.getProduction().isEpsilonProduction()){
						artefact.getSons().remove(i);
					} else if(son.getProduction().isNoneProduction()){
						artefact.getSons().remove(i);
						artefact.getSons().addAll(i, son.getSons());
						replaced = true;
					}
				}
			}
			if(replaced){
				ArrayList<String> rhs = new ArrayList<String>();
				for(PeerToPeerWorkflowArtefact s : artefact.getSons())
					rhs.add(s.getTask().getSymbol());
				Production p = new Production(artefact.getProduction().getProdName(), artefact.getProduction().getProdLhs(),
						new ProductionRhs(son.getProduction().getProdType(), rhs));
				if(artefact.getSons().isEmpty())
					p.getProdRhs().setRhsType(GrammaticalModelOfWorkflow.PRODUCTION_TYPE_EPS);
				else if(artefact.getSons().size() == 1)
					p.getProdRhs().setRhsType(GrammaticalModelOfWorkflow.PRODUCTION_TYPE_NONE);
				artefact.setProduction(p);
				//return removeInconsistentVirtualTasks(artefact, virtualTasks, newVirtualTasks);
			}
		}while(replaced);
		ArrayList<String> rhs = new ArrayList<String>();
		for(PeerToPeerWorkflowArtefact s : artefact.getSons()){
			if(virtualTasks.contains(s.getTask().getSymbol())){
				String st = "S" + (newVirtualTasks.size() + 1);
				s.getTask().setSymbol(st);
				newVirtualTasks.add(st);
			}
			rhs.add(s.getTask().getSymbol());
		}
		Production p = new Production(artefact.getProduction().getProdName(), artefact.getProduction().getProdLhs(),
				new ProductionRhs(artefact.getProduction().getProdType(), rhs));
		if(artefact.getSons().isEmpty())
			p.getProdRhs().setRhsType(GrammaticalModelOfWorkflow.PRODUCTION_TYPE_EPS);
		else if(artefact.getSons().size() == 1)
			p.getProdRhs().setRhsType(GrammaticalModelOfWorkflow.PRODUCTION_TYPE_NONE);
		artefact.setProduction(p);
		for(PeerToPeerWorkflowArtefact s : artefact.getSons())
			removeInconsistentVirtualTasks(s, virtualTasks, newVirtualTasks);
		return newVirtualTasks;
	}
	
	public PeerToPeerWorkflowArtefact projectArtefact(
			PeerToPeerWorkflowArtefact artefact,
			WorkflowPeerConfiguration peerConfig){
		ArrayList<String> readable = peerConfig.getReadable();
		ArrayList<String> virtualTasks = new ArrayList<String>();
		PeerToPeerWorkflowArtefact pArtefact = projectArtefact(artefact, readable, virtualTasks);
		ArrayList<PeerToPeerWorkflowArtefact> list = new ArrayList<PeerToPeerWorkflowArtefact>();
		list.add(pArtefact);
		virtualTasks = mergeSimilarVirtualTasks(list, virtualTasks, new ArrayList<String>());
		pArtefact.changeCreator(virtualTasks, peerConfig.getStakeholder());
		list = peerConfig.getLocalGMWf().getArtefacts();
		boolean isPrefix = false;
		for(PeerToPeerWorkflowArtefact gArt : list){
			if(pArtefact.isPrefixOf(gArt, peerConfig.getVirtualTasks(), virtualTasks)){
				pArtefact.updatePrefix(gArt, virtualTasks);
				isPrefix = true;
				break;
			}
		}
		return isPrefix ? pArtefact : null;
	}
	
	public PeerToPeerWorkflowArtefact expandArtefact(
			PeerToPeerWorkflowArtefact partialReplicaUpdated, 
			PeerToPeerWorkflowArtefact previousGlobalArtefact,
			WorkflowPeerConfiguration peerConfig) throws Exception{
		ArrayList<PeerToPeerWorkflowArtefact> partialArtefactsWithPrefix = getAllArtefactsWithPrefix(peerConfig.getLocalGMWf(), partialReplicaUpdated);
		if(partialArtefactsWithPrefix == null || partialArtefactsWithPrefix.isEmpty())
			return null;
		PeerToPeerWorkflowArtefact globalExpansionArtefact = null;
		for(PeerToPeerWorkflowArtefact partialArtefactWithPrefix : partialArtefactsWithPrefix){
			globalExpansionArtefact = getOneGlobalExpansionArtefact(partialArtefactWithPrefix, previousGlobalArtefact, peerConfig);
			if(globalExpansionArtefact != null)
				break;
		}
		return globalExpansionArtefact != null ? mergeArtefacts(globalExpansionArtefact, previousGlobalArtefact, partialReplicaUpdated, peerConfig) : null;
	}
	
	public void lockTasksBelowBudAbove(PeerToPeerWorkflowArtefact partialReplicaUpdated,
			PeerToPeerWorkflowArtefact previousGlobalArtefact,
			WorkflowPeerConfiguration peerConfig) throws Exception {
		PeerToPeerWorkflowArtefact artefact = expandArtefact(partialReplicaUpdated, previousGlobalArtefact, peerConfig);
		ArrayList<String> addrs = artefact.getBudsAboveAdresses(previousGlobalArtefact, peerConfig.getReadable());
		for(String addr : addrs){
			PeerToPeerWorkflowArtefact subArt = artefact.getSubArtefact(addr);
			PeerToPeerWorkflowArtefactCursor cursor = new PeerToPeerWorkflowArtefactCursor(subArt, PeerToPeerWorkflowArtefactCursor.LTR_PREFIX_ORDER);
			while(cursor.hasNext()){
				partialReplicaUpdated.lockTasksBelowBudAbove(cursor.next().snd.getTask());
			}
		}
	}

	@SuppressWarnings("unchecked")
	private PeerToPeerWorkflowArtefact mergeArtefacts (
			PeerToPeerWorkflowArtefact globalArtefactModel, 
			PeerToPeerWorkflowArtefact previousGlobalArtefact,
			PeerToPeerWorkflowArtefact partialReplicaUpdated,
			WorkflowPeerConfiguration peerConfig) throws Exception{
		PeerToPeerWorkflowArtefactCursor globalArtefactModelCursor = new PeerToPeerWorkflowArtefactCursor(globalArtefactModel.clone(), PeerToPeerWorkflowArtefactCursor.LTR_PREFIX_ORDER),
				previousGlobalArtefactCursor = new PeerToPeerWorkflowArtefactCursor(previousGlobalArtefact.clone(), PeerToPeerWorkflowArtefactCursor.LTR_PREFIX_ORDER),
				partialReplicaUpdatedCursor = new PeerToPeerWorkflowArtefactCursor(partialReplicaUpdated.clone(), PeerToPeerWorkflowArtefactCursor.LTR_PREFIX_ORDER);
		PeerToPeerWorkflowArtefact mergeArtefact = globalArtefactModel.clone(), subArt;
		Pair<String, PeerToPeerWorkflowArtefact> pgaNode, pruNode, gamNode;
		while(globalArtefactModelCursor.hasNext()){
			if(previousGlobalArtefactCursor.hasNext())
				pgaNode = previousGlobalArtefactCursor.next();
			else
				pgaNode = null;
			pruNode = partialReplicaUpdatedCursor.next();
			while(pruNode != null && peerConfig.getVirtualTasks().contains(pruNode.snd.getTask().getSymbol()) && partialReplicaUpdatedCursor.hasNext())
				pruNode = partialReplicaUpdatedCursor.next();
			gamNode = globalArtefactModelCursor.next();
			subArt = mergeArtefact.getSubArtefact(gamNode.fst);
			if(pruNode != null && gamNode.snd.getTask().equals(pruNode.snd.getTask())){
				subArt.setTask(pruNode.snd.getTask());
				subArt.setCreator(pruNode.snd.getCreator());
				if(pgaNode != null && !(gamNode.snd.getTask().equals(pgaNode.snd.getTask())))
					previousGlobalArtefactCursor.backCursor();
				if(!pruNode.snd.isClosed()){
					subArt.setStatus(pruNode.snd.getStatus());
					subArt.setSons((ArrayList<PeerToPeerWorkflowArtefact>)pruNode.snd.getSons().clone());
					subArt.setProduction(pruNode.snd.getProduction());
					String addr = gamNode.fst;
					while(gamNode.fst.startsWith(addr) && globalArtefactModelCursor.hasNext())
						gamNode = globalArtefactModelCursor.next();
					if(!gamNode.fst.startsWith(addr))
						globalArtefactModelCursor.backCursor();
				}
			} else if(pgaNode != null && gamNode.snd.getTask().equals(pgaNode.snd.getTask())) {
				subArt.setTask(pgaNode.snd.getTask());
				subArt.setCreator(pgaNode.snd.getCreator());
				if(!pgaNode.snd.isClosed()){
					subArt.setStatus(pgaNode.snd.getStatus());
					subArt.setSons((ArrayList<PeerToPeerWorkflowArtefact>)pgaNode.snd.getSons().clone());
					subArt.setProduction(pgaNode.snd.getProduction());
					String addr = gamNode.fst;
					while(gamNode.fst.startsWith(addr) && globalArtefactModelCursor.hasNext())
						gamNode = globalArtefactModelCursor.next();
					if(!gamNode.fst.startsWith(addr))
						globalArtefactModelCursor.backCursor();
				}
				if(pruNode != null && !peerConfig.getVirtualTasks().contains(pruNode.snd.getTask().getSymbol()))
					partialReplicaUpdatedCursor.backCursor();
			} else {
				subArt.setCreator(peerConfig.getStakeholder());
				if(pgaNode != null)
					previousGlobalArtefactCursor.backCursor();
				if(pruNode != null && !peerConfig.getVirtualTasks().contains(pruNode.snd.getTask().getSymbol()))
					partialReplicaUpdatedCursor.backCursor();
			} 
		}
		return mergeArtefact;
	}
	
	public PeerToPeerWorkflowArtefact getOneGlobalExpansionArtefact(PeerToPeerWorkflowArtefact partialArtefact,
			PeerToPeerWorkflowArtefact globalExpansionPrefix, WorkflowPeerConfiguration peerConfig){
		ArrayList<PeerToPeerWorkflowArtefact> list = grammaticalModelOfWorkflow.getArtefacts();
		for(PeerToPeerWorkflowArtefact artefact : list){
			ArrayList<String> virtualTasks = new ArrayList<>();
			PeerToPeerWorkflowArtefact pArtefact = projectArtefact(artefact, peerConfig.getReadable(), virtualTasks);
			ArrayList<PeerToPeerWorkflowArtefact> l = new ArrayList<PeerToPeerWorkflowArtefact>();
			l.add(pArtefact);
			virtualTasks = mergeSimilarVirtualTasks(l, virtualTasks, new ArrayList<String>());
			if(pArtefact.isEqualTo(partialArtefact, peerConfig.getVirtualTasks(), virtualTasks)
					&& (globalExpansionPrefix == null || globalExpansionPrefix.isPrefixOf(artefact)))
				return artefact;
		}
		return null;
	}
	
	public ArrayList<PeerToPeerWorkflowArtefact> getAllGlobalExpansionArtefacts(PeerToPeerWorkflowArtefact partialArtefact,
			PeerToPeerWorkflowArtefact globalExpansionPrefix, WorkflowPeerConfiguration peerConfig){
		ArrayList<PeerToPeerWorkflowArtefact> list = grammaticalModelOfWorkflow.getArtefacts();
		ArrayList<PeerToPeerWorkflowArtefact> rl = new ArrayList<PeerToPeerWorkflowArtefact>();
		for(PeerToPeerWorkflowArtefact artefact : list){
			ArrayList<String> virtualTasks = new ArrayList<>();
			PeerToPeerWorkflowArtefact pArtefact = projectArtefact(artefact, peerConfig.getReadable(), virtualTasks);
			ArrayList<PeerToPeerWorkflowArtefact> l = new ArrayList<PeerToPeerWorkflowArtefact>();
			l.add(pArtefact);
			virtualTasks = mergeSimilarVirtualTasks(l, virtualTasks, new ArrayList<String>());
			if(pArtefact.isEqualTo(partialArtefact, peerConfig.getVirtualTasks(), virtualTasks)
					&& (globalExpansionPrefix == null || globalExpansionPrefix.isPrefixOf(artefact)))
				rl.add(artefact);
		}
		return rl;
	}
	
	public PeerToPeerWorkflowArtefact getOneArtefactWithPrefix(GrammaticalModelOfWorkflow localGMWf, 
			PeerToPeerWorkflowArtefact prefix){
		ArrayList<PeerToPeerWorkflowArtefact> list = localGMWf.getArtefacts();
		for(PeerToPeerWorkflowArtefact artefact : list)
			if(prefix == null || prefix.isPrefixOf(artefact))
				return artefact;
		return null;
	}
	
	public ArrayList<PeerToPeerWorkflowArtefact> getAllArtefactsWithPrefix(GrammaticalModelOfWorkflow localGMWf, 
			PeerToPeerWorkflowArtefact prefix){
		ArrayList<PeerToPeerWorkflowArtefact> list = localGMWf.getArtefacts();
		ArrayList<PeerToPeerWorkflowArtefact> l = new ArrayList<PeerToPeerWorkflowArtefact>();
		for(PeerToPeerWorkflowArtefact artefact : list)
			if(prefix == null || prefix.isPrefixOf(artefact))
				l.add(artefact);
		return l;
	}
	
	public PeerToPeerWorkflowArtefact getOneArtefactWithPrefix(PeerToPeerWorkflowArtefact prefix){
		return getOneArtefactWithPrefix(grammaticalModelOfWorkflow, prefix);
	}
	
	public ArrayList<PeerToPeerWorkflowArtefact> getAllArtefactsWithPrefix(PeerToPeerWorkflowArtefact prefix){
		return getAllArtefactsWithPrefix(grammaticalModelOfWorkflow, prefix);
	}
	
	public PeerToPeerWorkflowArtefact pruneArtefact(
			PeerToPeerWorkflowArtefact artefact, 
			PeerToPeerWorkflowArtefact previousArtefact,
			ArrayList<String> readable){
		PeerToPeerWorkflowArtefact art = artefact.clone();
		ArrayList<String> adresses = art.getBudsAboveAdresses(previousArtefact, readable);
		for(String adress : adresses)
			art.prune(adress);
		return art;
	}
	
	public PeerToPeerWorkflowArtefact mergeArtefacts(
			PeerToPeerWorkflowArtefact artefact1, 
			PeerToPeerWorkflowArtefact artefact2,
			Stakeholder stakeholder, ArrayList<String> writable)  throws Exception{
		PeerToPeerWorkflowArtefact artefact = mergeArtefacts(artefact1, artefact2);
		if(artefact != null)
			artefact.unlockBuds(stakeholder, writable);
		return artefact;
	}
	
	private PeerToPeerWorkflowArtefact mergeArtefacts (
			PeerToPeerWorkflowArtefact artefact1, 
			PeerToPeerWorkflowArtefact artefact2) throws Exception{
		if(artefact1 == null && artefact2 == null)
			return null;
		else if(artefact1 == null)
			return artefact2.clone();
		else if(artefact2 == null)
			return artefact1.clone();
		else{
			if(!artefact1.getTask().equals(artefact2.getTask()) /*|| !artefact1.getCreator().equals(artefact2.getCreator())*/
					|| (!artefact1.getProduction().isEpsilonProduction() && !artefact2.getProduction().isEpsilonProduction() && !artefact1.getProduction().equals(artefact2.getProduction())))
				throw new Exception();
			String status = artefact1.isClosed() || artefact2.isClosed() ? PeerToPeerWorkflowArtefact.TASK_STATUS_CLOSED : 
				(artefact1.isUnlocked() || artefact2.isUnlocked() ? PeerToPeerWorkflowArtefact.TASK_STATUS_UNLOCKED : PeerToPeerWorkflowArtefact.TASK_STATUS_LOCKED);
			PeerToPeerWorkflowArtefact artefact = new PeerToPeerWorkflowArtefact(artefact1.getTask(), status, artefact1.getCreator());
			if(artefact.getTask().getStatus() == null)
				artefact.getTask().setStatus(artefact2.getTask().getStatus());
			if(artefact1.getProduction().isEpsilonProduction()){
				artefact.setProduction(artefact2.getProduction());
				ArrayList<PeerToPeerWorkflowArtefact> sonsOf = new ArrayList<PeerToPeerWorkflowArtefact>();
				for(int i = 0; i < artefact2.getSons().size(); i++)
					sonsOf.add(mergeArtefacts(artefact2.getSons().get(i), null));
				artefact.setSons(sonsOf);
				return artefact;
			}
			if(artefact2.getProduction().isEpsilonProduction()){
				artefact.setProduction(artefact1.getProduction());
				ArrayList<PeerToPeerWorkflowArtefact> sonsOf = new ArrayList<PeerToPeerWorkflowArtefact>();
				for(int i = 0; i < artefact1.getSons().size(); i++)
					sonsOf.add(mergeArtefacts(artefact1.getSons().get(i), null));
				artefact.setSons(sonsOf);
				return artefact;
			}
			artefact.setProduction(artefact1.getProduction());
			ArrayList<PeerToPeerWorkflowArtefact> sonsOf = new ArrayList<PeerToPeerWorkflowArtefact>();
			for(int i = 0; i < artefact1.getSons().size(); i++)
				sonsOf.add(mergeArtefacts(artefact1.getSons().get(i), artefact2.getSons().get(i)));
			artefact.setSons(sonsOf);
			return artefact;
		}
	}
	
	public ArrayList<Stakeholder> getRequiredPeers(
			PeerToPeerWorkflowArtefact artefact, Stakeholder stakeholder){
		ArrayList<Pair<String, Task>> readyTasks = artefact.getReadyTasks();
		ArrayList<Stakeholder> requiredPeers = new ArrayList<Stakeholder>();
		for(Pair<String, Task> pair : readyTasks)
			if(artefact.getSubArtefact(pair.fst).getCreator().equals(stakeholder) && canRequestExecution(stakeholder, pair.snd.getSymbol()))
				requiredPeers.add(executorOf(pair.snd.getSymbol()));
		return requiredPeers;
	}

	public PeerToPeerWorkflowArtefact getInitialArtefact() {
		PeerToPeerWorkflowArtefact artefact = new PeerToPeerWorkflowArtefact(grammaticalModelOfWorkflow.getTask(grammaticalModelOfWorkflow.getAxiom()), 
				PeerToPeerWorkflowArtefact.TASK_STATUS_UNLOCKED, executorOf(grammaticalModelOfWorkflow.getAxiom()));
		artefact.setProduction(new Production("", grammaticalModelOfWorkflow.getAxiom(), new ProductionRhs(GrammaticalModelOfWorkflow.PRODUCTION_TYPE_EPS, new ArrayList<String>())));
		return artefact;
	}
}
