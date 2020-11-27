package smartworkflow.dwfms.urifia.fmml.miu.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import smartworkflow.dwfms.urifia.fmml.miu.controllers.util.ControllerModel;
import smartworkflow.dwfms.urifia.fmml.miu.util.graphview.GraphView;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.PeerToPeerWorkflowArtefact;
import smartworkflow.dwfms.urifia.fmml.miu.view.util.PanelModel;

public class WorkflowDisplayArtefactPanel extends PanelModel {
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private JToolBar toolBar;
	private String label;
	private JLabel labelLabel;
	private PeerToPeerWorkflowArtefact artefact;
	
	
	public WorkflowDisplayArtefactPanel(ControllerModel controller, PeerToPeerWorkflowArtefact artefact) {
        super(controller);
        this.artefact = artefact;
        this.initComponents();
    }
	
	public WorkflowDisplayArtefactPanel(ControllerModel controller, PeerToPeerWorkflowArtefact artefact, String label) {
        super(controller);
        this.label = label;
        this.artefact = artefact;
        this.initComponents();
    }

	@Override
	protected void initComponents() {
		this.setLayout(new BorderLayout());
        
        this.initListeners();
        
        this.initPanels();
        
        this.initMenus();
        
        if(label != null)
        	this.initToolBar();
	}

	private void initListeners() {
		
	}

	private void initPanels() {
		mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        
        if(artefact != null){
        	GraphView gView= new GraphView();
    		gView.setArtefact(artefact);
    		gView.setPanel(mainPanel);
    		gView.buildView();
        }
        
        this.add(mainPanel);
        this.setPreferredSize(new Dimension(mainPanel.getPreferredSize().width + 50, mainPanel.getPreferredSize().height + 50));
	}

	private void initMenus() {
		
	}
	
	private void initToolBar() {
		this.toolBar = new JToolBar();
		toolBar.setPreferredSize(new Dimension(20, 20));
		toolBar.setFloatable(false);
        
		this.labelLabel = new JLabel();
        this.labelLabel.setText(label);
        labelLabel.setFont(new Font("Cambria", Font.BOLD, 12));
        
        this.toolBar.add(this.labelLabel);
        
        this.add(this.toolBar, BorderLayout.SOUTH);
	}

}
