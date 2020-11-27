package smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow;

import java.io.Serializable;

public class PeerToPeerWorkflowResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	private String instanceId;
	private Stakeholder stakeholder;
	private PeerToPeerWorkflowArtefact artefact;
	
	public PeerToPeerWorkflowResponse(String instanceId,
			Stakeholder stakeholder,
			PeerToPeerWorkflowArtefact artefact) {
		this.stakeholder = stakeholder;
		this.artefact = artefact;
		this.instanceId = instanceId;
	}

	public Stakeholder getStakeholder() {
		return stakeholder;
	}

	public void setStakeholder(Stakeholder stakeholder) {
		this.stakeholder = stakeholder;
	}

	public PeerToPeerWorkflowArtefact getArtefact() {
		return artefact;
	}

	public void setArtefact(
			PeerToPeerWorkflowArtefact artefact) {
		this.artefact = artefact;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
}
