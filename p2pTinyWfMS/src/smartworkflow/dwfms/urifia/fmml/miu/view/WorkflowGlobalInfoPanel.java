package smartworkflow.dwfms.urifia.fmml.miu.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JSplitPane;

import smartworkflow.dwfms.urifia.fmml.miu.controllers.util.ControllerModel;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.WorkflowPeerConfiguration;
import smartworkflow.dwfms.urifia.fmml.miu.view.util.PanelModel;

public class WorkflowGlobalInfoPanel extends PanelModel {
	private static final long serialVersionUID = 1L;
	private JSplitPane splitV, splitH;
	private WorkflowGlobalArtefactsPanel workflowGlobalArtefactsPanel;
	private WorkflowTasksPanel workflowTasksPanel;
	private WorkflowStakeholdersPanel workflowStakeholdersPanel;
	private WorkflowPeerConfiguration peerConfig;
	
	public WorkflowGlobalInfoPanel(ControllerModel controller, WorkflowPeerConfiguration peerConfig) {
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
        splitH = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        
        splitH.setDividerSize(1);
        splitV.setDividerSize(1);
		
        this.workflowGlobalArtefactsPanel = new WorkflowGlobalArtefactsPanel(controller, peerConfig.getPeerToPeerWorkflow());
        workflowGlobalArtefactsPanel.setPreferredSize(new Dimension(260, 400));
        
        this.workflowTasksPanel = new WorkflowTasksPanel(controller, peerConfig.getPeerToPeerWorkflow());
        workflowTasksPanel.setPreferredSize(new Dimension(270, 320));
        
        this.workflowStakeholdersPanel = new WorkflowStakeholdersPanel(controller, peerConfig.getPeerToPeerWorkflow());
        workflowStakeholdersPanel.setPreferredSize(new Dimension(270, 100));
        
        this.splitH.add(workflowTasksPanel);
        this.splitH.add(workflowStakeholdersPanel);
        
        
        this.splitV.add(splitH);
        this.splitV.add(workflowGlobalArtefactsPanel);
        
        splitV.setContinuousLayout(true);
        splitH.setContinuousLayout(true);
        
        this.add(splitV);
	}

	private void initMenus() {
		
	}

}
