/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartworkflow.dwfms.urifia.fmml.miu.util.observer.adapters;

import java.util.ArrayList;
import java.util.List;

import smartworkflow.dwfms.urifia.fmml.miu.util.exceptions.ElementNotFoundException;
import smartworkflow.dwfms.urifia.fmml.miu.util.observer.Observable;
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
public abstract class ObservableAdapter implements Observable{

    protected List<Observer> observerList;
    
    public ObservableAdapter(){
        observerList = new ArrayList<Observer>();
    }
    
    public ObservableAdapter(List<Observer> observerList){
        this.observerList = observerList;
    }
    
    @Override
    public void addObserver(Observer observer) {
        if(!this.observerList.contains(observer))
            this.observerList.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) throws ElementNotFoundException {
        try{
            this.observerList.remove(observer);
        }
        catch(Exception e){
            throw new ElementNotFoundException(e.getMessage());
        }
    }
    
    @Override
    public void notifyStartLoaded(String message) {
        for(Observer observer : observerList)
            observer.updateStartLoaded(message);
    }

    @Override
    public void notifyStartUtilities() {
        for(Observer observer : observerList)
            observer.updateStartUtilities();
    }

    @Override
    public void notifyUserChanged() {
        for(Observer observer : observerList)
            observer.updateUserChanged();
    }
    
    @Override
    public void notifyThemeChanged() {
        for(Observer observer : observerList)
            observer.updateThemeChanged();
    }

    @Override
    public void notifyWorkflow(PeerToPeerWorkflow workflow) {
        for(Observer observer : observerList)
            observer.updateWorkflow(workflow);
    }
    
    @Override
    public void notifySuccess(String message) {
         for(Observer observer : observerList)
            observer.updateSuccess(message);
    }

    @Override
    public void notifyError(String message) {
        for(Observer observer : observerList)
            observer.updateError(message);
    }
    
    @Override
    public void notifyTaskStarted(String t){
        for(Observer observer : observerList)
            observer.updateTaskStarted(t);
    }
    
    @Override
    public void notifyTaskEnded(String t){
        for(Observer observer : observerList)
            observer.updateTaskEnded(t);
    }
    
    @Override
    public void notifyPeerStatus(WorkflowPeerStatus peerStatus) {
    	for(Observer observer : observerList)
            observer.updatePeerStatus(peerStatus);
	}
    
    @Override
    public void notifyWorkflowResponses(
			ArrayList<PeerToPeerWorkflowResponse> responses) {
    	for(Observer observer : observerList)
            observer.updateWorkflowResponses(responses);
	}

    @Override
    public void notifyWorkflowRequests(
			ArrayList<PeerToPeerWorkflowRequest> requests) {
    	for(Observer observer : observerList)
            observer.updateWorkflowRequests(requests);
	}
    
    @Override
    public void notifyContinueWorkflowProcess() {
    	for(Observer observer : observerList)
            observer.updateContinueWorkflowProcess();
	}
    
    @Override
    public void notifyExpandedArtefact(PeerToPeerWorkflowArtefact pruned) {
    	for(Observer observer : observerList)
            observer.updateExpandedArtefact(pruned);
	}
    
    @Override
    public void notifyRequiredPeers(ArrayList<Stakeholder> peers) {
    	for(Observer observer : observerList)
            observer.updateRequiredPeers(peers);
	}

    @Override
	public void notifyAnsweredPeers(ArrayList<Stakeholder> peers) {
		for(Observer observer : observerList)
            observer.updateAnsweredPeers(peers);
	}

    @Override
	public void notifyEndOfProcess() {
		for(Observer observer : observerList)
            observer.updateEndOfProcess();
	}
    
    @Override
    public void notifyReplicatedArtefactToEdit(
			PeerToPeerWorkflowArtefact partialReplica) {
    	for(Observer observer : observerList)
            observer.updateReplicatedArtefactToEdit(partialReplica);
	}

    @Override
	public void notifyReplicatedArtefact(
			PeerToPeerWorkflowArtefact partialReplica) {
		for(Observer observer : observerList)
            observer.updateReplicatedArtefact(partialReplica);
	}

    @Override
	public void notifyMergedArtefact(PeerToPeerWorkflowArtefact merged) {
		for(Observer observer : observerList)
            observer.updateMergedArtefact(merged);
	}
}
