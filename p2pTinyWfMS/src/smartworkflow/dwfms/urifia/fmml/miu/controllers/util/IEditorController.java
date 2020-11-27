package smartworkflow.dwfms.urifia.fmml.miu.controllers.util;

import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.PeerToPeerWorkflowRequest;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.PeerToPeerWorkflowResponse;

public interface IEditorController {
	public void inForwardTo(String expeditor, PeerToPeerWorkflowRequest request);
	public void inReturnTo(String expeditor, PeerToPeerWorkflowResponse response);
	public void outForwardTo(String adressee, PeerToPeerWorkflowRequest request);
	public void outReturnTo(String adressee, PeerToPeerWorkflowResponse response);
	
}
