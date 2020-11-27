package p2pTinyWfMS;

import smartworkflow.dwfms.urifia.fmml.miu.controllers.IHMController;
import smartworkflow.dwfms.urifia.fmml.miu.controllers.StartupController;
import smartworkflow.dwfms.urifia.fmml.miu.controllers.util.IEditorController;
import smartworkflow.dwfms.urifia.fmml.miu.models.IHMModel;
import smartworkflow.dwfms.urifia.fmml.miu.models.StartupModel;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.PeerToPeerWorkflowRequest;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.PeerToPeerWorkflowResponse;
import smartworkflow.dwfms.urifia.fmml.miu.view.StartupView;

public abstract class P2pTinyWfMS extends inria.communicationprotocol.CommunicationProtocolFacade {
	protected IEditorController model = null;
	public P2pTinyWfMS () {
		//view = getView ();
		//SwingUtilities.invokeLater(new Runnable() {
       //     public void run() {
       //         view = getView ();
        //    }
        //});
	}

	public void inForwardTo(String expeditor, PeerToPeerWorkflowRequest request) {
		model.inForwardTo(expeditor, request);
	}
	
	public void inReturnTo(String expeditor, PeerToPeerWorkflowResponse response) {
		model.inReturnTo(expeditor, response);
	}

	public abstract void outForwardTo(String adressee, PeerToPeerWorkflowRequest request);
	public abstract void outReturnTo(String adressee, PeerToPeerWorkflowResponse response);
	
	public IEditorController getView() {
		if (model == null) {
			final StartupModel startupModel = new StartupModel();
	        StartupController controller = new StartupController(startupModel);
	        IHMModel ihmModel = new IHMModel(this);
	        IHMController ihmController = new IHMController(ihmModel);
	        StartupView startupView = new StartupView(ihmController, controller);
	        startupView.setEnabled(true);
			model = ihmController;
		}
		return model;
	}
	
	@Override
	protected void neighbourJustConnected(String name, String service) {
		if (name.equals("ComponentsManager")) {
			model = getView ();
		}
	}
	
	public void disconnectInput(String expeditor) {
		
	}
		
	public void shutdown(String expeditor) {
		
	}
	
	public Object requestTree(String expeditor) {
		return null;
	}
	
	public void quit(String expeditor) {
		
	}
}
