package smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow;

import java.io.Serializable;

public class SpecializedEditorStatus implements Serializable{
	private static final long serialVersionUID = 1L;
	private String instanceId;
	private PeerToPeerWorkflowArtefact partialReplica;
	
	public SpecializedEditorStatus(String instanceId) {
		this.instanceId = instanceId;
		partialReplica = null;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public PeerToPeerWorkflowArtefact getPartialReplica() {
		return partialReplica;
	}

	public void setPartialReplica(
			PeerToPeerWorkflowArtefact partialReplica) {
		this.partialReplica = partialReplica;
	}
}