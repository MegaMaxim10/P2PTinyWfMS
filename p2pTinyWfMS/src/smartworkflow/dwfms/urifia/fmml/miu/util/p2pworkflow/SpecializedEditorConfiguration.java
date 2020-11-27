package smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow;

import java.io.Serializable;
import java.util.ArrayList;

public class SpecializedEditorConfiguration implements Serializable{
	private static final long serialVersionUID = 1L;
	private GrammaticalModelOfWorkflow localGMWf;
	private Stakeholder stakeholder;
	private ArrayList<String> virtualTasks;
	private PeerToPeerWorkflow peerToPeerWorkflow;
	
	public SpecializedEditorConfiguration(
			GrammaticalModelOfWorkflow localGMWf,
			Stakeholder stakeholder,
			ArrayList<String> virtualTasks,
			PeerToPeerWorkflow peerToPeerWorkflow) {
		this.localGMWf = localGMWf;
		this.stakeholder = stakeholder;
		this.virtualTasks = virtualTasks;
		this.peerToPeerWorkflow = peerToPeerWorkflow;
	}

	public ArrayList<String> getVirtualTasks() {
		return virtualTasks;
	}

	public void setVirtualTasks(ArrayList<String> virtualTasks) {
		this.virtualTasks = virtualTasks;
	}

	public GrammaticalModelOfWorkflow getLocalGMWf() {
		return localGMWf;
	}

	public void setLocalGMWf(
			GrammaticalModelOfWorkflow localGMWf) {
		this.localGMWf = localGMWf;
	}

	public Stakeholder getStakeholder() {
		return stakeholder;
	}

	public void setStakeholder(Stakeholder stakeholder) {
		this.stakeholder = stakeholder;
	}

	public PeerToPeerWorkflow getPeerToPeerWorkflow() {
		return peerToPeerWorkflow;
	}

	public void setPeerToPeerWorkflow(PeerToPeerWorkflow peerToPeerWorkflow) {
		this.peerToPeerWorkflow = peerToPeerWorkflow;
	}
}