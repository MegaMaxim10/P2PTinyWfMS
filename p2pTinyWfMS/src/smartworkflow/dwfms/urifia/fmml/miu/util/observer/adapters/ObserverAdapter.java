/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartworkflow.dwfms.urifia.fmml.miu.util.observer.adapters;

import java.util.ArrayList;

import smartworkflow.dwfms.urifia.fmml.miu.models.util.ModelModel;
import smartworkflow.dwfms.urifia.fmml.miu.util.observer.Observer;
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
public abstract class ObserverAdapter implements Observer{
    @Override
    public void updateStartLoaded(String message) {
        
    }

    @Override
    public void updateStartUtilities() {
        
    }

    @Override
    public void updateUserChanged() {
        
    }
    
    @Override
    public void updateThemeChanged() {
        
    }

    @Override
    public void updateWorkflow(PeerToPeerWorkflow workflow) {
        
    }
    
    @Override
    public void updateSuccess(String message) {
        ModelModel.displayMessageDialog(ModelModel.config.getLangValue("success_notification"), message);
    }

    @Override
    public void updateError(String message) {
        ModelModel.displayErrorDialog(ModelModel.config.getLangValue("error_notification"), message);
    }
    
    @Override
    public void updateTaskStarted(String t){
        
    }
    
    @Override
    public void updateTaskEnded(String t){
        
    }
    
    @Override
    public void updatePeerStatus(WorkflowPeerStatus peerStatus){
    	
    }
    
    @Override
    public void updateWorkflowRequests(
    		ArrayList<PeerToPeerWorkflowRequest> requests) {
    	
    }
    
    @Override
    public void updateWorkflowResponses(
    		ArrayList<PeerToPeerWorkflowResponse> responses) {
    	
    }
    
    @Override
    public void updateContinueWorkflowProcess() {
    	
    }
    
    @Override
    public void updateExpandedArtefact(PeerToPeerWorkflowArtefact pruned) {
    	
    }
    
    @Override
    public void updateAnsweredPeers(ArrayList<Stakeholder> peers) {
    	
    }
    
    @Override
    public void updateEndOfProcess() {
    	
    }
    
    @Override
    public void updateRequiredPeers(ArrayList<Stakeholder> peers) {
    	
    }
    
    @Override
    public void updateMergedArtefact(PeerToPeerWorkflowArtefact merged) {
    	
    }
    
    @Override
    public void updateReplicatedArtefact(
    		PeerToPeerWorkflowArtefact partialReplica) {
    	
    }
    
    @Override
    public void updateReplicatedArtefactToEdit(
    		PeerToPeerWorkflowArtefact partialReplica) {
    	
    }
}
