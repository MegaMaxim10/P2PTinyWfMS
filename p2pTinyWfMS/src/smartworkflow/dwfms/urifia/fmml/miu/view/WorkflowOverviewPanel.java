package smartworkflow.dwfms.urifia.fmml.miu.view;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import smartworkflow.dwfms.urifia.fmml.miu.controllers.util.ControllerModel;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.WorkflowPeerConfiguration;
import smartworkflow.dwfms.urifia.fmml.miu.view.util.PanelModel;

public class WorkflowOverviewPanel extends PanelModel{
	private static final long serialVersionUID = 1L;
	private WorkflowGlobalInfoPanel workflowGlobalInfoPanel;
	private WorkflowLocalStatePanel workflowLocalStatePanel;
	private JSplitPane splitV;
	private WorkflowPeerConfiguration peerConfig;
	
	
	public WorkflowOverviewPanel(ControllerModel controller, WorkflowPeerConfiguration peerConfig) {
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
        splitV.setDividerSize(1);
        
        this.workflowGlobalInfoPanel = new WorkflowGlobalInfoPanel(controller, peerConfig);
        this.workflowLocalStatePanel = new WorkflowLocalStatePanel(controller, peerConfig);
        
        splitV.add(workflowGlobalInfoPanel);
        splitV.add(workflowLocalStatePanel);
        
        splitV.setContinuousLayout(true);
        
        this.add(new JScrollPane(splitV));
	}

	private void initMenus() {
		
	}
}
