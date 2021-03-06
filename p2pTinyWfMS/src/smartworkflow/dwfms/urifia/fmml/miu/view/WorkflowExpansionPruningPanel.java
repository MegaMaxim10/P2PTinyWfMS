package smartworkflow.dwfms.urifia.fmml.miu.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import smartworkflow.dwfms.urifia.fmml.miu.controllers.util.ControllerModel;
import smartworkflow.dwfms.urifia.fmml.miu.models.util.ModelModel;
import smartworkflow.dwfms.urifia.fmml.miu.util.observer.adapters.ObserverAdapter;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.PeerToPeerWorkflowArtefact;
import smartworkflow.dwfms.urifia.fmml.miu.view.util.PanelModel;

public class WorkflowExpansionPruningPanel extends PanelModel {

	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private JToolBar toolBar;
	private JLabel titleLabel;
	private Updater updater;
	
	public WorkflowExpansionPruningPanel(ControllerModel controller) {
        super(controller);
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
        
        this.initToolBar();
	}

	private void initListeners() {
		
	}

	private void initPanels() {
		this.mainPanel = new JPanel();
		//this.mainPanel.setLayout(new BorderLayout());
        
        this.add(new JScrollPane(mainPanel));
	}
	
	private void updateArtefact(PeerToPeerWorkflowArtefact artefact) {
		this.mainPanel.removeAll();
		WorkflowDisplayArtefactPanel panel = new WorkflowDisplayArtefactPanel(controller, artefact);
    	this.mainPanel.add(panel);
		this.mainPanel.setPreferredSize(panel.getPreferredSize());
		mainPanel.setOpaque(true);
        mainPanel.setBackground(Color.WHITE);
        
        mainPanel.validate();
        mainPanel.updateUI();
	}

	private void initMenus() {
		
	}
	
	private void initToolBar() {
		this.toolBar = new JToolBar();
		toolBar.setPreferredSize(new Dimension(20, 20));
		toolBar.setFloatable(false);
        
		this.titleLabel = new JLabel();
        this.titleLabel.setText(ModelModel.getConfig().getLangValue("after_expansion"));
        titleLabel.setFont(new Font("Cambria", Font.BOLD, 12));
        
        ImageIcon icon = new ImageIcon(getClass().getResource("/smartworkflow/dwfms/urifia/fmml/miu/resources/images/theme_small.png"));
        titleLabel.setIcon(icon);
        titleLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        
        this.toolBar.add(this.titleLabel);
        
        this.add(this.toolBar, BorderLayout.NORTH);
	}

	public class Updater extends ObserverAdapter{
		@Override
		public void updateExpandedArtefact(PeerToPeerWorkflowArtefact pruned) {
			updateArtefact(pruned);
		}
	}
}
