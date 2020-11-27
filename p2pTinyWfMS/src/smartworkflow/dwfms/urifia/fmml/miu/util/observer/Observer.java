/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartworkflow.dwfms.urifia.fmml.miu.util.observer;

import java.util.ArrayList;

import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.PeerToPeerWorkflow;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.PeerToPeerWorkflowArtefact;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.PeerToPeerWorkflowRequest;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.PeerToPeerWorkflowResponse;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.Stakeholder;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.WorkflowPeerStatus;

/**
 *
 * @author Ndadji Maxime
 */
public interface Observer {
    public void updateStartLoaded(String message);
    public void updateStartUtilities();
    public void updateUserChanged();
    public void updateThemeChanged();
    public void updateWorkflow(PeerToPeerWorkflow workflow);
    public void updateSuccess (String message);
    public void updateError (String message);
    public void updateTaskStarted(String t);
    public void updateTaskEnded(String t);
	public void updatePeerStatus(WorkflowPeerStatus peerStatus);
	public void updateWorkflowResponses(ArrayList<PeerToPeerWorkflowResponse> responses);
	public void updateWorkflowRequests(ArrayList<PeerToPeerWorkflowRequest> requests);
	public void updateContinueWorkflowProcess();
	public void updateExpandedArtefact(PeerToPeerWorkflowArtefact pruned);
	public void updateRequiredPeers(ArrayList<Stakeholder> peers);
	public void updateAnsweredPeers(ArrayList<Stakeholder> peers);
	public void updateEndOfProcess();
	public void updateReplicatedArtefactToEdit(PeerToPeerWorkflowArtefact partialReplica);
	public void updateReplicatedArtefact(PeerToPeerWorkflowArtefact partialReplica);
	public void updateMergedArtefact(PeerToPeerWorkflowArtefact merged);
}
