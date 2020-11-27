package smartworkflow.dwfms.urifia.fmml.miu.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import smartworkflow.dwfms.urifia.fmml.miu.controllers.util.ControllerModel;
import smartworkflow.dwfms.urifia.fmml.miu.models.util.ModelModel;
import smartworkflow.dwfms.urifia.fmml.miu.util.observer.adapters.ObserverAdapter;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.PeerToPeerWorkflowRequest;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.PeerToPeerWorkflowResponse;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.WorkflowPeerStatus;
import smartworkflow.dwfms.urifia.fmml.miu.view.util.PanelModel;
import smartworkflow.dwfms.urifia.fmml.miu.view.util.TableModel;

public class WorkflowLocalRecentEventPanel extends PanelModel {
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private JToolBar toolBar;
	private JTable eventsTable;
	private JLabel titleLabel;
	private WorkflowPeerStatus peerStatus;
	private Updater updater;

	public WorkflowLocalRecentEventPanel(ControllerModel controller) {
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
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		String[] titles = { ModelModel.getConfig().getLangValue("event_type"), ModelModel.getConfig().getLangValue("event_details") };
		eventsTable = new JTable(new TableModel(titles));
        eventsTable.setRowHeight(20);
        eventsTable.setSelectionBackground(ModelModel.getConfig().getTheme().getBgColor());
        eventsTable.setSelectionForeground(Color.WHITE);
        eventsTable.setGridColor(ModelModel.getConfig().getTheme().getBgColor());
        eventsTable.setFont(ModelModel.getConfig().getTheme().getAreasFont());
        eventsTable.setOpaque(false);
        eventsTable.getTableHeader().setReorderingAllowed(false);
        eventsTable.getTableHeader().setResizingAllowed(true);
        
        this.mainPanel.add(eventsTable.getTableHeader(), BorderLayout.NORTH);
        this.mainPanel.add(new JScrollPane(eventsTable));
        
        updateEvents();
        
        this.add(mainPanel);
	}

	private void updateEvents() {
		if(peerStatus != null){
			((TableModel)eventsTable.getModel()).removeAll();
			for(PeerToPeerWorkflowRequest request : peerStatus.getRequests()){
	        	Object[] row = {"Request", "Sent by " + request.getStakeholder().getId()};
	            ((TableModel)eventsTable.getModel()).addRow(row);
	    	}
			for(PeerToPeerWorkflowResponse response : peerStatus.getResponses()){
	        	Object[] row = {"Response", "Sent by " + response.getStakeholder().getId()};
	            ((TableModel)eventsTable.getModel()).addRow(row);
	    	}
			eventsTable.validate();
			eventsTable.updateUI();
		}
	}

	private void initMenus() {

	}

	private void initToolBar() {
		this.toolBar = new JToolBar();
		toolBar.setPreferredSize(new Dimension(20, 20));
		toolBar.setFloatable(false);
        
		this.titleLabel = new JLabel();
        this.titleLabel.setText(ModelModel.getConfig().getLangValue("workflow_events"));
        titleLabel.setFont(new Font("Cambria", Font.BOLD, 12));
        
        ImageIcon icon = new ImageIcon(getClass().getResource("/smartworkflow/dwfms/urifia/fmml/miu/resources/images/mess_inst_small.png"));
        titleLabel.setIcon(icon);
        titleLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        
        this.toolBar.add(this.titleLabel);
        
        this.add(this.toolBar, BorderLayout.NORTH);
	}
	
	public class Updater extends ObserverAdapter{
		@Override
		public void updatePeerStatus(WorkflowPeerStatus status) {
			peerStatus = status;
			
			updateEvents();
		}
	}
}
