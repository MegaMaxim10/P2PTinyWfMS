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
import smartworkflow.dwfms.urifia.fmml.miu.util.observer.adapters.ObserverAdapter;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.PeerToPeerWorkflowRequest;
import smartworkflow.dwfms.urifia.fmml.miu.view.util.PanelModel;

public class WorkflowRequestQueuePanel extends PanelModel {
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private JToolBar toolBar;
	private JLabel titleLabel;
	private Updater updater;
	
	public WorkflowRequestQueuePanel(ControllerModel controller) {
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
        
        this.add(new JScrollPane(mainPanel));
	}
	
	private void updatePanel(ArrayList<PeerToPeerWorkflowRequest> requests){
		this.mainPanel.removeAll();
        int pwidth = 0, pheight = 0;
        for(PeerToPeerWorkflowRequest request : requests){
        	WorkflowDisplayArtefactPanel panel = new WorkflowDisplayArtefactPanel(controller, request.getArtefact(), "Sent by " + request.getStakeholder().getId());
        	pheight += 10 + panel.getPreferredSize().height;
        	if(pwidth < panel.getPreferredSize().width + 10)
        		pwidth = panel.getPreferredSize().width + 10;
        	this.mainPanel.add(panel);
        }
        
        this.mainPanel.setPreferredSize(new Dimension(pwidth, pheight));
        
        mainPanel.setOpaque(true);
        mainPanel.setBackground(Color.WHITE);
        
        mainPanel.validate();
        mainPanel.updateUI();
        
        validate();
        updateUI();
	}

	private void initMenus() {
		
	}
	
	private void initToolBar() {
		this.toolBar = new JToolBar();
		toolBar.setPreferredSize(new Dimension(20, 20));
		toolBar.setFloatable(false);
        
		this.titleLabel = new JLabel();
        this.titleLabel.setText("Requests Queue");
        titleLabel.setFont(new Font("Cambria", Font.BOLD, 12));
        
        ImageIcon icon = new ImageIcon(getClass().getResource("/smartworkflow/dwfms/urifia/fmml/miu/resources/images/talk_small.png"));
        titleLabel.setIcon(icon);
        titleLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        
        this.toolBar.add(this.titleLabel);
        
        this.add(this.toolBar, BorderLayout.NORTH);
	}

	public class Updater extends ObserverAdapter{
		@Override
		public void updateWorkflowRequests(
				ArrayList<PeerToPeerWorkflowRequest> requests) {
			updatePanel(requests);
		}
	}
}
