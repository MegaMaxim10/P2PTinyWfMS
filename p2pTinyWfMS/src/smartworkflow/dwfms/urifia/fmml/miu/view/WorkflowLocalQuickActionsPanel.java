package smartworkflow.dwfms.urifia.fmml.miu.view;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import smartworkflow.dwfms.urifia.fmml.miu.controllers.IHMController;
import smartworkflow.dwfms.urifia.fmml.miu.controllers.util.ControllerModel;
import smartworkflow.dwfms.urifia.fmml.miu.models.util.ModelModel;
import smartworkflow.dwfms.urifia.fmml.miu.util.observer.adapters.ObserverAdapter;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.WorkflowPeerConfiguration;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.WorkflowPeerStatus;
import smartworkflow.dwfms.urifia.fmml.miu.view.util.ChatButton;
import smartworkflow.dwfms.urifia.fmml.miu.view.util.PanelModel;

public class WorkflowLocalQuickActionsPanel extends PanelModel {
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private JToolBar toolBar;
	private JLabel titleLabel;
	private JButton runProcess;
	private WorkflowPeerStatus peerStatus;
	private WorkflowPeerConfiguration peerConfig;
	private ActionListener listener;
	private Updater updater;
	
	public WorkflowLocalQuickActionsPanel(ControllerModel controller, WorkflowPeerConfiguration peerConfig) {
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
        
        this.initToolBar();
	}

	private void initListeners() {
		listener = new ActionListener(){
        	
			@Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                if("start".equals(command)){
                	((IHMController)controller).startWorkflowProcess(peerConfig);
                }
                
                if("continue".equals(command)){
                	((IHMController)controller).continueWorkflowProcess(peerConfig, peerStatus);
                }
            }
            
        };
	}

	private void initPanels() {
		mainPanel = new JPanel();
        
		runProcess = new ChatButton("Start Process Execution", ModelModel.getConfig().getTheme());
		runProcess.setActionCommand("start");
		runProcess.setPreferredSize(new Dimension((runProcess.getText().length()) * 10, 30));
		runProcess.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		runProcess.addActionListener(listener);
        mainPanel.add(runProcess);
		
        this.add(new JScrollPane(mainPanel));
        
        updateButtons();
	}

	private void updateButtons() {
		if(peerStatus == null){
			if(peerConfig.canLaunchProcess()){
				runProcess.setText(ModelModel.getConfig().getLangValue("start_process_execution"));
				runProcess.setActionCommand("start");
				runProcess.setEnabled(true);
			} else {
				runProcess.setEnabled(false);
			}
		} else {
			if(!peerStatus.getRequests().isEmpty() || !peerStatus.getResponses().isEmpty() || peerStatus.isExecuting()){
				runProcess.setText(ModelModel.getConfig().getLangValue("continue_execution"));
				runProcess.setActionCommand("continue");
				runProcess.setEnabled(true);
			} else {
				if(!peerStatus.isExecuting()){
				runProcess.setText(ModelModel.getConfig().getLangValue("continue_execution"));
					runProcess.setActionCommand("continue");
					runProcess.setEnabled(true);
				} else
					runProcess.setEnabled(false);
			}
		}
	}

	private void initMenus() {
		
	}
	
	private void initToolBar() {
		this.toolBar = new JToolBar();
		toolBar.setPreferredSize(new Dimension(20, 20));
		toolBar.setFloatable(false);
        
		this.titleLabel = new JLabel();
        this.titleLabel.setText(ModelModel.getConfig().getLangValue("quick_actions"));
        titleLabel.setFont(new Font("Cambria", Font.BOLD, 12));
        
        ImageIcon icon = new ImageIcon(getClass().getResource("/smartworkflow/dwfms/urifia/fmml/miu/resources/images/settings_small.png"));
        titleLabel.setIcon(icon);
        titleLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        
        this.toolBar.add(this.titleLabel);
        
        this.add(this.toolBar, BorderLayout.NORTH);
	}
	
	public class Updater extends ObserverAdapter{
		@Override
		public void updatePeerStatus(WorkflowPeerStatus status) {
			peerStatus = status;
			
			updateButtons();
		}
	}
}
