package smartworkflow.dwfms.urifia.fmml.miu.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JTabbedPane;

import smartworkflow.dwfms.urifia.fmml.miu.controllers.util.ControllerModel;
import smartworkflow.dwfms.urifia.fmml.miu.models.util.ModelModel;
import smartworkflow.dwfms.urifia.fmml.miu.util.observer.adapters.ObserverAdapter;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.WorkflowPeerConfiguration;
import smartworkflow.dwfms.urifia.fmml.miu.view.util.PanelModel;

public class WorkflowPanel extends PanelModel{
	private static final long serialVersionUID = 1L;
	private JTabbedPane workflowPane;
	private WorkflowOverviewPanel workflowOverviewPanel;
	private WorkflowExecutionPanel workflowExecutionPanel;
	private Updater updater;
	private WorkflowPeerConfiguration peerConfig;

	public WorkflowPanel(ControllerModel controller, WorkflowPeerConfiguration peerConfig) {
        super(controller);
        this.peerConfig = peerConfig;
        updater = new Updater();
        this.addUpdater(updater);
        
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
        workflowPane = new JTabbedPane();
        workflowPane.setPreferredSize(new Dimension(248, 300));
        workflowPane.setMinimumSize(new Dimension(248, 25));
        workflowPane.setFont(new Font("Cambria", Font.PLAIN, 12));
        workflowPane.setBorder(BorderFactory.createEtchedBorder());
        workflowPane.setTabPlacement(JTabbedPane.BOTTOM);
        
        workflowOverviewPanel = new WorkflowOverviewPanel(controller, peerConfig);
        
        workflowPane.add(ModelModel.getConfig().getLangValue("workflow_overview"), workflowOverviewPanel);
        
        workflowExecutionPanel = new WorkflowExecutionPanel(controller, peerConfig);
        
        this.add(workflowPane);
	}

	private void initPanels() {
		
	}

	private void initMenus() {
		
	}
	
	public class Updater extends ObserverAdapter{
		@Override
		public void updateContinueWorkflowProcess() {
			if(workflowPane.getComponentCount() < 2)
				workflowPane.add(ModelModel.getConfig().getLangValue("workflow_execution"), workflowExecutionPanel);
			workflowPane.setSelectedIndex(1);
		}
    }
}
