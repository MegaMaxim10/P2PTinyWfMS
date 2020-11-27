package smartworkflow.dwfms.urifia.fmml.miu.models;

import java.util.ArrayList;

import p2pTinyWfMS.P2pTinyWfMS;
import smartworkflow.dwfms.urifia.fmml.miu.models.util.ModelModel;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.PeerToPeerWorkflowArtefact;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.PeerToPeerWorkflowRequest;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.PeerToPeerWorkflowResponse;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.Stakeholder;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.WorkflowPeerConfiguration;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.WorkflowPeerStatus;
import smartworkflow.dwfms.urifia.fmml.miu.view.SettingGUIDialog;

public class IHMModel extends ModelModel{
	protected P2pTinyWfMS sonComInterface;
	private WorkflowPeerStatus status;
	private WorkflowPeerConfiguration peerConfig;
	
    public IHMModel (P2pTinyWfMS sonComInterface){
        this.sonComInterface = sonComInterface;
    }

    public void editDesign() {
        SettingGUIDialog dialog = new SettingGUIDialog(config, true, null);
        dialog.showDialog();
        if(dialog.changed){
            notifyThemeChanged();
        }
    }
    
    public void startTask(String task){
        notifyTaskStarted(task);
    }
    
    public void stopTask(String task){
        notifyTaskEnded(task);
    }

	public P2pTinyWfMS getSonComInterface() {
		return sonComInterface;
	}

	public void setSonComInterface(P2pTinyWfMS sonComInterface) {
		this.sonComInterface = sonComInterface;
	}

	public void startWorkflowProcess(WorkflowPeerConfiguration peerConfig) {
		WorkflowPeerStatus peerStatus = peerConfig.getInitialStatus();
		peerStatus.setExecuting(false);
		status = peerStatus;
		notifyPeerStatus(peerStatus);
	}
	
	public void requestReceived(PeerToPeerWorkflowRequest request){
		if(status == null){
			status = peerConfig.getInitialStatus();
			status.setExecuting(false);
			//status.setGlobalArtefactUpdated(request.getArtefact());
		}
		status.getRequests().add(request);
		notifyPeerStatus(status);
	}
	
	public void responseReceived(PeerToPeerWorkflowResponse response){
		if(status == null){
			status = peerConfig.getInitialStatus();
			status.setExecuting(false);
			//status.setGlobalArtefactUpdated(response.getArtefact());
		}
		status.getResponses().add(response);
		notifyPeerStatus(status);
	}
	
	public void setPeerConfig(WorkflowPeerConfiguration peerConfig){
		this.peerConfig = peerConfig;
	}

	public void continueWorkflowProcess(WorkflowPeerConfiguration peerConfig, WorkflowPeerStatus peerStatus) {
		try{
			if(!peerStatus.isExecuting()){
				merger(peerConfig, peerStatus);
				replication(peerConfig, peerStatus);
				peerStatus.setExecuting(true);
				status = peerStatus;
				notifyPeerStatus(peerStatus);
				notifyReplicatedArtefactToEdit(peerStatus.getPartialReplica());
			}
			notifyContinueWorkflowProcess();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	private PeerToPeerWorkflowArtefact replication(WorkflowPeerConfiguration peerConfig,
			WorkflowPeerStatus peerStatus) throws Exception {
		PeerToPeerWorkflowArtefact merged = peerStatus.getGlobalArtefact();
		if(peerStatus.getGlobalArtefactUpdated() != null)
			merged = peerConfig.getPeerToPeerWorkflow().mergeArtefacts(peerStatus.getGlobalArtefactUpdated(), merged, peerConfig.getStakeholder(), peerConfig.getWritable());
		merged.lockBuds();
		PeerToPeerWorkflowArtefact partialReplica = peerConfig.getPeerToPeerWorkflow().projectArtefact(merged, peerConfig);
		partialReplica.unlockBuds(peerConfig.getStakeholder(), peerConfig.getConcreteWritable());
		peerStatus.setPartialReplica(partialReplica);
		notifyReplicatedArtefact(peerStatus.getPartialReplica());
		return peerStatus.getPartialReplica();
	}

	private PeerToPeerWorkflowArtefact merger(WorkflowPeerConfiguration peerConfig,
			WorkflowPeerStatus peerStatus) throws Exception {
		PeerToPeerWorkflowArtefact merged = peerStatus.getGlobalArtefact();
		notifyWorkflowRequests(peerStatus.getRequests());
		if(peerStatus.getRequests() != null && !peerStatus.getRequests().isEmpty()){
			for(int i = 0; i < peerStatus.getRequests().size(); i++){
				merged = peerConfig.getPeerToPeerWorkflow().mergeArtefacts(merged, peerStatus.getRequests().get(i).getArtefact(), peerConfig.getStakeholder(), peerConfig.getWritable());
				if(!peerStatus.getStakeholdersWaitingResponses().contains(peerStatus.getRequests().get(i).getStakeholder()))
					peerStatus.getStakeholdersWaitingResponses().add(peerStatus.getRequests().get(i).getStakeholder());
				peerStatus.getRequests().remove(i--);
			}
		}
		notifyWorkflowResponses(peerStatus.getResponses());
		if(peerStatus.getResponses() != null && !peerStatus.getResponses().isEmpty()){
			for(int i = 0; i < peerStatus.getResponses().size(); i++){
				merged = peerConfig.getPeerToPeerWorkflow().mergeArtefacts(merged, peerStatus.getResponses().get(i).getArtefact(), peerConfig.getStakeholder(), peerConfig.getWritable());
				peerStatus.getResponses().remove(i--);
			}
		}
		peerStatus.setGlobalArtefact(merged);
		notifyMergedArtefact(merged);
		return merged;
	}

	public void expandAndDiffuse(WorkflowPeerConfiguration peerConfig,
			WorkflowPeerStatus peerStatus) {
		try{
			expansionPruning(peerConfig, peerStatus);
			diffusion(peerConfig, peerStatus);
			peerStatus.setExecuting(false);
			notifyPeerStatus(peerStatus);
			status = peerStatus;
		} catch (Exception e){
			
		}
	}

	private void diffusion(WorkflowPeerConfiguration peerConfig,
			WorkflowPeerStatus peerStatus) {
		PeerToPeerWorkflowArtefact pruned = peerStatus.getGlobalArtefact();
		ArrayList<Stakeholder> peers = peerConfig.getPeerToPeerWorkflow().getRequiredPeers(pruned, peerConfig.getStakeholder());
		pruned.lockBuds();
		if(peers == null || peers.isEmpty()){
			ArrayList<Stakeholder> replies = peerStatus.getStakeholdersWaitingResponses();
			peerStatus.setStakeholdersWaitingResponses(new ArrayList<Stakeholder>());
			if(replies == null || replies.isEmpty()){
				notifyEndOfProcess();
			}else{
				PeerToPeerWorkflowResponse response = new PeerToPeerWorkflowResponse("", peerConfig.getStakeholder(), pruned);
				for(Stakeholder stakeholder : replies)
					sonComInterface.outReturnTo(stakeholder.getId(), response);
				notifyAnsweredPeers(replies);
			}
		}else{
			PeerToPeerWorkflowRequest request = new PeerToPeerWorkflowRequest("", peerConfig.getStakeholder(), pruned);
			for(Stakeholder stakeholder : peers)
				sonComInterface.outForwardTo(stakeholder.getId(), request);
			notifyRequiredPeers(peers);
		}
	}

	private PeerToPeerWorkflowArtefact expansionPruning(WorkflowPeerConfiguration peerConfig,
			WorkflowPeerStatus peerStatus) throws Exception {
		PeerToPeerWorkflowArtefact expanded = peerConfig.getPeerToPeerWorkflow().expandArtefact(peerStatus.getPartialReplica(), peerStatus.getGlobalArtefact(), peerConfig);
		PeerToPeerWorkflowArtefact pruned = peerConfig.getPeerToPeerWorkflow().pruneArtefact(expanded, peerStatus.getGlobalArtefact(), peerConfig.getReadable());
		pruned.unlockBuds(peerConfig.getStakeholder(), peerConfig.getWritable());
		peerStatus.setGlobalArtefactUpdated(expanded);
		peerStatus.setGlobalArtefact(pruned);
		notifyExpandedArtefact(pruned);
		return pruned;
	}
}
