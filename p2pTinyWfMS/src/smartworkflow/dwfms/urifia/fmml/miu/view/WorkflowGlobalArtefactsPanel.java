package smartworkflow.dwfms.urifia.fmml.miu.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import smartworkflow.dwfms.urifia.fmml.miu.controllers.util.ControllerModel;
import smartworkflow.dwfms.urifia.fmml.miu.models.util.ModelModel;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.PeerToPeerWorkflow;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.PeerToPeerWorkflowArtefact;
import smartworkflow.dwfms.urifia.fmml.miu.view.util.PanelModel;

public class WorkflowGlobalArtefactsPanel extends PanelModel {
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private JToolBar toolBar;
	private JLabel titleLabel;
	private PeerToPeerWorkflow workflow;
	
	public WorkflowGlobalArtefactsPanel(ControllerModel controller, PeerToPeerWorkflow workflow) {
        super(controller);
        this.workflow = workflow;
        this.initComponents();
    }

	@Override
	protected void initComponents() {
		this.setLayout(new BorderLayout());
        
        this.initListeners();
        
        this.initPanels();
        
        this.initMenus();
        
        this.initToolBar();
	}

	private void initListeners() {
		
	}

	private void initPanels() {
        this.mainPanel = new JPanel();
        
        ArrayList<PeerToPeerWorkflowArtefact> artefacts = workflow.getGrammaticalModelOfWorkflow().getArtefacts();
        int pwidth = 0, pheight = 0;
        for(PeerToPeerWorkflowArtefact artefact : artefacts){
        	WorkflowDisplayArtefactPanel panel = new WorkflowDisplayArtefactPanel(controller, artefact);
        	pheight += 10 + panel.getPreferredSize().height;
        	if(pwidth < panel.getPreferredSize().width + 10)
        		pwidth = panel.getPreferredSize().width + 10;
        	this.mainPanel.add(panel);
        }
        
        this.mainPanel.setPreferredSize(new Dimension(pwidth, pheight));
        
        mainPanel.setOpaque(true);
        mainPanel.setBackground(Color.WHITE);
        
        this.add(new JScrollPane(mainPanel));
	}

	private void initMenus() {
		
	}
	
	private void initToolBar() {
		this.toolBar = new JToolBar();
		toolBar.setPreferredSize(new Dimension(20, 20));
		toolBar.setFloatable(false);
        
		this.titleLabel = new JLabel();
        this.titleLabel.setText(ModelModel.getConfig().getLangValue("workflow_artefacts"));
        titleLabel.setFont(new Font("Cambria", Font.BOLD, 12));
        
        ImageIcon icon = new ImageIcon(getClass().getResource("/smartworkflow/dwfms/urifia/fmml/miu/resources/images/edit_small.png"));
        titleLabel.setIcon(icon);
        titleLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        
        this.toolBar.add(this.titleLabel);
        
        this.add(this.toolBar, BorderLayout.NORTH);
	}
}
