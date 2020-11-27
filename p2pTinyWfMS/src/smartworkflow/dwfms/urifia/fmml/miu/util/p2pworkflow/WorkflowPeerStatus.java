package smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow;

import java.io.Serializable;
import java.util.ArrayList;

public class WorkflowPeerStatus implements Serializable{
	private static final long serialVersionUID = 1L;
	private WorkflowPeerConfiguration peerConfig;
	private String instanceId;
	private ArrayList<PeerToPeerWorkflowRequest> requests;
	private ArrayList<PeerToPeerWorkflowResponse> responses;
	private ArrayList<Stakeholder> stakeholdersWaitingResponses;
	private PeerToPeerWorkflowArtefact globalArtefact, partialReplica, globalArtefactUpdated;
	private boolean executionFlag = false;
	
	public WorkflowPeerStatus(WorkflowPeerConfiguration peerConfig, String instanceId) {
		this.peerConfig = peerConfig;
		this.instanceId = instanceId;
		requests = new ArrayList<PeerToPeerWorkflowRequest>();
		responses = new ArrayList<PeerToPeerWorkflowResponse>();
		stakeholdersWaitingResponses = new ArrayList<Stakeholder>();
		if(peerConfig.canLaunchProcess())
			globalArtefact = peerConfig.getPeerToPeerWorkflow().getInitialArtefact();
		else
			globalArtefact = null;
		partialReplica = null;
		globalArtefactUpdated = null;
	}

	public PeerToPeerWorkflowArtefact getGlobalArtefact() {
		return globalArtefact;
	}

	public void setGlobalArtefact(
			PeerToPeerWorkflowArtefact globalArtefact) {
		this.globalArtefact = globalArtefact;
	}

	public PeerToPeerWorkflowArtefact getPartialReplica() {
		return partialReplica;
	}

	public void setPartialReplica(
			PeerToPeerWorkflowArtefact partialReplica) {
		this.partialReplica = partialReplica;
	}

	public PeerToPeerWorkflowArtefact getGlobalArtefactUpdated() {
		return globalArtefactUpdated;
	}

	public void setGlobalArtefactUpdated(
			PeerToPeerWorkflowArtefact globalArtefactUpdated) {
		this.globalArtefactUpdated = globalArtefactUpdated;
	}
	
	public void addStakeholderWaitingResponse(Stakeholder stakeholder){
		stakeholdersWaitingResponses.add(stakeholder);
	}
	
	public void addResponse(PeerToPeerWorkflowResponse reponse){
		responses.add(reponse);
	}
	
	public void addRequest(PeerToPeerWorkflowRequest request){
		requests.add(request);
	}

	public ArrayList<PeerToPeerWorkflowRequest> getRequests() {
		return requests;
	}

	public ArrayList<PeerToPeerWorkflowResponse> getResponses() {
		return responses;
	}

	public ArrayList<Stakeholder> getStakeholdersWaitingResponses() {
		return stakeholdersWaitingResponses;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	
	public boolean isExecuting(){
		return executionFlag;
	}
	
	public void setExecuting(boolean executionFlag){
		this.executionFlag = executionFlag;
	}

	public WorkflowPeerConfiguration getPeerConfig() {
		return peerConfig;
	}

	public void setStakeholdersWaitingResponses(ArrayList<Stakeholder> arrayList) {
		this.stakeholdersWaitingResponses = arrayList;
	}
}