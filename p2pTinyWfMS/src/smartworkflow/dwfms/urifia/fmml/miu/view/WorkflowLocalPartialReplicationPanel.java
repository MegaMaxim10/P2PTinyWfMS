package smartworkflow.dwfms.urifia.fmml.miu.view;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;

import smartworkflow.dwfms.urifia.fmml.miu.controllers.util.ControllerModel;
import smartworkflow.dwfms.urifia.fmml.miu.models.util.ModelModel;
import smartworkflow.dwfms.urifia.fmml.miu.view.util.PanelModel;

public class WorkflowLocalPartialReplicationPanel extends PanelModel {
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	
	private JPanel artefactsPanel;
	private JPanel artefact;
	
	
	private JToolBar topToolBar;
	private JToolBar bottomToolBar;
	
	private JScrollPane listOfArtefacts;

	private JLabel panelTitleLabel;
	
	private String panelTitle = ModelModel.getConfig().getLangValue("current_partial_replication");
	
	public WorkflowLocalPartialReplicationPanel(ControllerModel controller) {
        super(controller);
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
		mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        
        this.artefactsPanel = new JPanel();
        this.artefactsPanel.setLayout(new BorderLayout());
        
        this.artefact = new WorkflowDisplayArtefactPanel(controller, null);
        
        this.topToolBar = new JToolBar(); 
        this.panelTitleLabel = new JLabel();
        this.panelTitleLabel.setText(this.panelTitle);
        this.topToolBar.add(this.panelTitleLabel);
        
        
        this.bottomToolBar = new JToolBar();
       
        
        this.listOfArtefacts = new JScrollPane(this.artefactsPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        listOfArtefacts.setBounds(0, 0, 930, 610);
        
        this.artefactsPanel.add(this.artefact, BorderLayout.CENTER);
        
        
        this.mainPanel.add(this.topToolBar, BorderLayout.NORTH);
        this.mainPanel.add(this.artefactsPanel, BorderLayout.CENTER);
        this.mainPanel.add(this.bottomToolBar, BorderLayout.SOUTH);
        
        
        
        this.add(mainPanel);
	}

	private void initMenus() {
		
	}

}
