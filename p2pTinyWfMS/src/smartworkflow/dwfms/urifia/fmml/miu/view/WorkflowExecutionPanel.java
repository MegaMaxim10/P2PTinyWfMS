package smartworkflow.dwfms.urifia.fmml.miu.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import smartworkflow.dwfms.urifia.fmml.miu.controllers.util.ControllerModel;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.WorkflowPeerConfiguration;
import smartworkflow.dwfms.urifia.fmml.miu.view.util.PanelModel;

public class WorkflowExecutionPanel extends PanelModel{
	private static final long serialVersionUID = 1L;
	private JSplitPane mainSplitV;
	private JSplitPane leftSplitV; 
	private JSplitPane leftLeftSplitH;
	private JSplitPane leftRightSplitH;
	private JSplitPane rightSplitH;
	private JSplitPane rightSplitV;
	private  WorkflowPeerConfiguration peerConfig;
	
	
	private WorkflowRequestQueuePanel workflowRequestQueuePanel;
	private WorkflowMergerPanel workflowMergerPanel;
	private WorkflowWYSIWYGExecutionPanel workflowWYSIWYGExecutionPanel;
	private WorkflowResponseQueuePanel WorkflowResponseQueuePanel;
	private WorkflowReplicationPanel workflowReplicationPanel;
	private WorkflowExpansionPruningPanel workflowExpansionPruningPanel;
	private WorkflowDiffusionPanel workflowDiffusionPanel;
	
	
	
	public WorkflowExecutionPanel(ControllerModel controller,  WorkflowPeerConfiguration peerConfig) {
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
		this.mainSplitV = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		mainSplitV.setDividerSize(1);
		this.leftSplitV = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		leftSplitV.setDividerSize(1);
		this.leftLeftSplitH = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		leftLeftSplitH.setDividerSize(1);
		this.leftRightSplitH = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		leftRightSplitH.setDividerSize(1);
		this.rightSplitH = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		rightSplitH.setDividerSize(1);
		this.rightSplitV = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		rightSplitV.setDividerSize(1);      
		
		this.mainSplitV.setContinuousLayout(true);         
		this.leftSplitV.setContinuousLayout(true);         
		this.leftLeftSplitH.setContinuousLayout(true);         
		this.leftRightSplitH.setContinuousLayout(true);         
		this.rightSplitH.setContinuousLayout(true);         
		this.rightSplitV.setContinuousLayout(true);
		
		this.workflowRequestQueuePanel = new WorkflowRequestQueuePanel(controller);
		workflowRequestQueuePanel.setPreferredSize(new Dimension(240, 250));
		
		this.WorkflowResponseQueuePanel = new WorkflowResponseQueuePanel(controller);
		WorkflowResponseQueuePanel.setPreferredSize(new Dimension(240, 250));
		
		this.workflowMergerPanel = new WorkflowMergerPanel(controller);
		workflowMergerPanel.setPreferredSize(new Dimension(240, 250));
		
		this.workflowReplicationPanel = new WorkflowReplicationPanel(controller);
		workflowReplicationPanel.setPreferredSize(new Dimension(240, 250));
		
		this.workflowWYSIWYGExecutionPanel = new WorkflowWYSIWYGExecutionPanel(controller, peerConfig);
		workflowWYSIWYGExecutionPanel.setPreferredSize(new Dimension(400, 480));
		
		this.workflowExpansionPruningPanel = new WorkflowExpansionPruningPanel(controller);
		workflowExpansionPruningPanel.setPreferredSize(new Dimension(240, 400));
		
		this.workflowDiffusionPanel = new WorkflowDiffusionPanel(controller);
		workflowDiffusionPanel.setPreferredSize(new Dimension(240, 140));
		
		
		this.leftRightSplitH.add(this.workflowMergerPanel);
		this.leftRightSplitH.add(this.workflowReplicationPanel);
		this.leftLeftSplitH.add(this.workflowRequestQueuePanel);
		this.leftLeftSplitH.add(this.WorkflowResponseQueuePanel);
		this.rightSplitH.add(this.workflowExpansionPruningPanel);
		this.rightSplitH.add(this.workflowDiffusionPanel);
		this.rightSplitV.add(this.workflowWYSIWYGExecutionPanel);
		this.rightSplitV.add(this.rightSplitH);
		this.leftSplitV.add(this.leftLeftSplitH);
		this.leftSplitV.add(this.leftRightSplitH);
		this.mainSplitV.add(this.leftSplitV);
		this.mainSplitV.add(this.rightSplitV);      
        
        this.add(new JScrollPane(mainSplitV));
	}

	private void initMenus() {
		
	}
}
