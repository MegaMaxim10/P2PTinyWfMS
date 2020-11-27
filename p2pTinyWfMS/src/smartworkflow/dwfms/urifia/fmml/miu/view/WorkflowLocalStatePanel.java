package smartworkflow.dwfms.urifia.fmml.miu.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JSplitPane;

import smartworkflow.dwfms.urifia.fmml.miu.controllers.util.ControllerModel;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.WorkflowPeerConfiguration;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.WorkflowPeerStatus;
import smartworkflow.dwfms.urifia.fmml.miu.view.util.PanelModel;

public class WorkflowLocalStatePanel extends PanelModel {
	private static final long serialVersionUID = 1L;
	private JSplitPane splitV, leftSplitH, rightSplitH;
	private WorkflowLocalGlobalArtefactPanel workflowLocalGlobalArtefactPanel;
	private WorkflowLocalPartialReplicaPanel workflowLocalPartialReplicaPanel;
	private WorkflowLocalRecentEventPanel workflowRecentEventsPanel;
	private WorkflowLocalQuickActionsPanel workflowQuickAtionsPanel;
	private WorkflowPeerConfiguration peerConfig;
	private WorkflowPeerStatus peerStatus;
	
	public WorkflowLocalStatePanel(ControllerModel controller, WorkflowPeerConfiguration peerConfig) {
        super(controller);
        this.peerConfig = peerConfig;
        this.initComponents();
    }

	@Override
	protected void initComponents() {
		this.setLayout(new BorderLayout());
        
        this.initListeners();
        
        this.initPanels();
        
        this.initMenus();
	}

	private void initListeners() {
		
	}

	private void initPanels() {
		splitV = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		leftSplitH = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		rightSplitH = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        
		leftSplitH.setDividerSize(1);
		rightSplitH.setDividerSize(1);
        splitV.setDividerSize(1);
        
        
        this.workflowLocalGlobalArtefactPanel = new WorkflowLocalGlobalArtefactPanel(controller);
        workflowLocalGlobalArtefactPanel.setPreferredSize(new Dimension(260, 250));
        
        this.workflowLocalPartialReplicaPanel = new WorkflowLocalPartialReplicaPanel(controller);
        workflowLocalPartialReplicaPanel.setPreferredSize(new Dimension(260, 250));
        
        this.workflowRecentEventsPanel = new WorkflowLocalRecentEventPanel(controller);
        workflowRecentEventsPanel.setPreferredSize(new Dimension(240, 480));
        
        this.workflowQuickAtionsPanel = new WorkflowLocalQuickActionsPanel(controller, peerConfig);
        workflowQuickAtionsPanel.setPreferredSize(new Dimension(240, 60));
        
        leftSplitH.add(this.workflowLocalGlobalArtefactPanel);
        leftSplitH.add(this.workflowLocalPartialReplicaPanel);
        
        rightSplitH.add(this.workflowRecentEventsPanel);
        rightSplitH.add(this.workflowQuickAtionsPanel);
        
        splitV.add(leftSplitH);
        splitV.add(rightSplitH);
        
        splitV.setContinuousLayout(true);
        leftSplitH.setContinuousLayout(true);
        rightSplitH.setContinuousLayout(true);
        
        this.add(splitV);
	}

	private void initMenus() {
		
	}

}
