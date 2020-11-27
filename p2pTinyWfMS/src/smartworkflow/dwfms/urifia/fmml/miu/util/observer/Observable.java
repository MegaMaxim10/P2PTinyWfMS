/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartworkflow.dwfms.urifia.fmml.miu.util.observer;

import java.util.ArrayList;

import smartworkflow.dwfms.urifia.fmml.miu.util.exceptions.ElementNotFoundException;
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
public interface Observable {
    public void addObserver(Observer observer);
    public void removeObserver(Observer observer) throws ElementNotFoundException;
    public void notifyStartLoaded(String message);
    public void notifyStartUtilities();
    public void notifyUserChanged();
    public void notifyThemeChanged();
    public void notifyWorkflow(PeerToPeerWorkflow workflow);
    public void notifySuccess (String message);
    public void notifyError (String message);
    public void notifyTaskStarted(String t);
    public void notifyTaskEnded(String t);
	public void notifyPeerStatus(WorkflowPeerStatus peerStatus);
	public void notifyWorkflowResponses(ArrayList<PeerToPeerWorkflowResponse> responses);
	public void notifyWorkflowRequests(ArrayList<PeerToPeerWorkflowRequest> requests);
	public void notifyContinueWorkflowProcess();
	public void notifyExpandedArtefact(PeerToPeerWorkflowArtefact pruned);
	public void notifyRequiredPeers(ArrayList<Stakeholder> peers);
	public void notifyAnsweredPeers(ArrayList<Stakeholder> peers);
	public void notifyEndOfProcess();
	public void notifyReplicatedArtefactToEdit(PeerToPeerWorkflowArtefact partialReplica);
	public void notifyReplicatedArtefact(PeerToPeerWorkflowArtefact partialReplica);
	public void notifyMergedArtefact(PeerToPeerWorkflowArtefact merged);
}
