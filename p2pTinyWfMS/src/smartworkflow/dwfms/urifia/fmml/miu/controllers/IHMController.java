package smartworkflow.dwfms.urifia.fmml.miu.controllers;

import p2pTinyWfMS.P2pTinyWfMS;
import smartworkflow.dwfms.urifia.fmml.miu.controllers.util.ControllerModel;
import smartworkflow.dwfms.urifia.fmml.miu.controllers.util.IEditorController;
import smartworkflow.dwfms.urifia.fmml.miu.models.IHMModel;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.PeerToPeerWorkflowRequest;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.PeerToPeerWorkflowResponse;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.WorkflowPeerConfiguration;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.WorkflowPeerStatus;


public class IHMController extends ControllerModel implements IEditorController {
    
    public IHMController(IHMModel model) {
        super(model);
    }
    
    public void editDesign() {
        ((IHMModel)model).editDesign();
    }
    
    public void startTask(String task){
        ((IHMModel)model).startTask(task);
    }
    
    public void stopTask(String task){
        ((IHMModel)model).stopTask(task);
    }
    
    public P2pTinyWfMS getSonComInterface() {
        return ((IHMModel)model).getSonComInterface();
	}
    
    public void setPeerConfig(WorkflowPeerConfiguration peerConfig){
    	((IHMModel)model).setPeerConfig(peerConfig);
	}
    
    @Override
	public void inForwardTo(String expeditor, PeerToPeerWorkflowRequest request) {
    	((IHMModel)model).requestReceived(request);
	}

	@Override
	public void inReturnTo(String expeditor, PeerToPeerWorkflowResponse response) {
		((IHMModel)model).responseReceived(response);
	}

	@Override
	public void outForwardTo(String expeditor, PeerToPeerWorkflowRequest request) {
		
	}

	@Override
	public void outReturnTo(String expeditor, PeerToPeerWorkflowResponse response) {
		
	}

	public void startWorkflowProcess(WorkflowPeerConfiguration peerConfig) {
		((IHMModel)model).startWorkflowProcess(peerConfig);
	}

	public void continueWorkflowProcess(WorkflowPeerConfiguration peerConfig, WorkflowPeerStatus peerStatus) {
		((IHMModel)model).continueWorkflowProcess(peerConfig, peerStatus);
	}

	public void expandAndDiffuse(WorkflowPeerConfiguration peerConfig, WorkflowPeerStatus peerStatus) {
		((IHMModel)model).expandAndDiffuse(peerConfig, peerStatus);
	}
}
