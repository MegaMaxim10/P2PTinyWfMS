package smartworkflow.dwfms.urifia.fmml.miu.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import smartworkflow.dwfms.urifia.fmml.miu.controllers.IHMController;
import smartworkflow.dwfms.urifia.fmml.miu.controllers.util.ControllerModel;
import smartworkflow.dwfms.urifia.fmml.miu.models.util.ModelModel;
import smartworkflow.dwfms.urifia.fmml.miu.util.APPLConstants;
import smartworkflow.dwfms.urifia.fmml.miu.util.Theme;
import smartworkflow.dwfms.urifia.fmml.miu.util.observer.adapters.ObserverAdapter;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.PeerToPeerWorkflowArtefact;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.Production;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.Task;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.WorkflowPeerConfiguration;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.WorkflowPeerStatus;
import smartworkflow.dwfms.urifia.fmml.miu.view.util.ChatButton;
import smartworkflow.dwfms.urifia.fmml.miu.view.util.PanelModel;

import com.sun.tools.javac.util.Pair;

public class WorkflowWYSIWYGExecutionPanel extends PanelModel {
	private static final long serialVersionUID = 1L;
	private JSplitPane splitH;
	private JPanel editorPanel;
	private JPanel editFormPanel;
	private JToolBar toolBar;
	private JLabel titleLabel;
	private JList<String> productionList;
	private JList<String> readyTasks;
	private WorkflowPeerStatus peerStatus;
	private PeerToPeerWorkflowArtefact artefact;
	private Updater updater;
	private JTextField status;
	private ChatButton save;
	private ActionListener listener;
	private WorkflowPeerConfiguration peerConfig;
	private ArrayList<Pair<String, Task>> readyTasksList;
	private ArrayList<Production> candidates;
	private ListSelectionListener listListener;
	
	public WorkflowWYSIWYGExecutionPanel(ControllerModel controller,  WorkflowPeerConfiguration peerConfig) {
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
                if("save".equals(command)){
                	String adress = readyTasksList.get(readyTasks.getSelectedIndex()).fst;
                	String state = status.getText();
                	Production p = candidates.get(productionList.getSelectedIndex());
                	peerConfig.editTask(artefact, adress, p, state);
                	afterEdition();
                }
            }
            
        };
        
        listListener = new ListSelectionListener() {
        	
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(e.getSource() == readyTasks){
					if(readyTasks.getSelectedIndex() != -1)
						candidates = peerConfig.getLocalGMWf().getXProductions(readyTasksList.get(readyTasks.getSelectedIndex()).snd.getSymbol());
					else
						candidates = new ArrayList<>();
					updateCandidates();
				}else{
					status.setEditable(true);
					save.setEnabled(true);
				}
			}
			
		};
	}

	private void initPanels() {
		this.splitH = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitH.setDividerSize(1);
		splitH.setContinuousLayout(true);
		
        this.editorPanel = new JPanel();
        //this.editorPanel.setLayout(new BorderLayout());
        editorPanel.setPreferredSize(new Dimension(300, 400));
        
        this.editFormPanel = new JPanel();
        this.editFormPanel.setLayout(new BorderLayout());
        this.editFormPanel.setPreferredSize(new Dimension(300, 150));
        
        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        JLabel label = new JLabel(ModelModel.getConfig().getLangValue("ready_tasks"));
        label.setFont(ModelModel.getConfig().getTheme().getMenuFont());
        label.setPreferredSize(new Dimension(150, 25));
        panel.add(label, BorderLayout.NORTH);
        
        readyTasks = new JList<>();
        readyTasks.setFont(ModelModel.getConfig().getTheme().getAreasFont());
        readyTasks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        readyTasks.setVisibleRowCount(2);
        readyTasks.setPreferredSize(new Dimension(150, 25));
        readyTasks.addListSelectionListener(listListener);
        panel.add(new JScrollPane(readyTasks));
        
        this.editFormPanel.add(panel, BorderLayout.NORTH);
        
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        label = new JLabel(ModelModel.getConfig().getLangValue("candidate_productions"));
        label.setFont(ModelModel.getConfig().getTheme().getMenuFont());
        label.setPreferredSize(new Dimension(150, 25));
        panel.add(label, BorderLayout.NORTH);
        
        productionList = new JList<>();
        productionList.setFont(ModelModel.getConfig().getTheme().getAreasFont());
        productionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        productionList.setVisibleRowCount(3);
        productionList.setPreferredSize(new Dimension(150, 25));
        panel.add(new JScrollPane(productionList));
        
        JToolBar bottomToolBar = new JToolBar(JToolBar.HORIZONTAL);
        bottomToolBar.setPreferredSize(new Dimension(100, 25));
        bottomToolBar.setFloatable(false);
        bottomToolBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY.brighter()));
        
        status = new JTextField(ModelModel.getConfig().getLangValue("enter_exec_status"));
        status.setPreferredSize(new Dimension(160, 25));
        status.setFont(ModelModel.getConfig().getTheme().getAreasFont());
        status.setEditable(false);
        status.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusGained(FocusEvent e) {
        		if(ModelModel.getConfig().getLangValue("enter_exec_status").equals(status.getText()))
        			status.setText("");
        	}
		});
        bottomToolBar.add(status);
        
        bottomToolBar.addSeparator(new Dimension(6, 6));
        
        save = new ChatButton(ModelModel.getConfig().getLangValue("ok"), ModelModel.getConfig().getTheme());
        save.setToolTipText("<html><body><font family=\""+ModelModel.getConfig().getTheme().getToolTipFont().getFamily()+"\" size=\""+ModelModel.getConfig().getTheme().getToolTipSize()+"\" color=\""+Theme.extractRGB(ModelModel.getConfig().getTheme().getToolTipColor())+"\">"
                +ModelModel.getConfig().getLangValue("save")+"</font></body></html>");
        save.setPreferredSize(new Dimension(60, 30));
        save.setIcon(new ImageIcon(getClass().getResource(APPLConstants.RESOURCES_FOLDER+"images/save_small.png")));
        save.setActionCommand("save");
        save.addActionListener(listener);
        save.setEnabled(false);
        bottomToolBar.add(save);
        
        panel.add(bottomToolBar, BorderLayout.SOUTH);
        
        this.editFormPanel.add(panel);
        
        this.splitH.add(new JScrollPane(editorPanel));
        this.splitH.add(editFormPanel);
       
        this.add(this.splitH);
	}
	
	private void afterEdition(){
		this.editorPanel.removeAll();
		peerConfig.editVirtualTasks(artefact, peerStatus.getGlobalArtefact());
		
		WorkflowDisplayArtefactPanel panel = new WorkflowDisplayArtefactPanel(controller, artefact);
    	this.editorPanel.add(panel);
		this.editorPanel.setPreferredSize(new Dimension(panel.getPreferredSize().width < editorPanel.getPreferredSize().width ? editorPanel.getPreferredSize().width : panel.getPreferredSize().width, 
				panel.getPreferredSize().height < editorPanel.getPreferredSize().height ? editorPanel.getPreferredSize().height : panel.getPreferredSize().height));
		editorPanel.setOpaque(true);
		editorPanel.setBackground(Color.WHITE);
        
		editorPanel.validate();
		editorPanel.updateUI();
		
		updateEditForm();
	}
	
	private void updateArtefact() {
		if(artefact != null){
			this.editorPanel.removeAll();
			peerConfig.editVirtualTasks(artefact, peerStatus.getGlobalArtefact());
			
			updateEditForm();
			
			WorkflowDisplayArtefactPanel panel = new WorkflowDisplayArtefactPanel(controller, artefact);
	    	this.editorPanel.add(panel);
			this.editorPanel.setPreferredSize(new Dimension(panel.getPreferredSize().width < editorPanel.getPreferredSize().width ? editorPanel.getPreferredSize().width : panel.getPreferredSize().width, panel.getPreferredSize().height));
			editorPanel.setOpaque(true);
			editorPanel.setBackground(Color.WHITE);
	        
			editorPanel.validate();
			editorPanel.updateUI();
		}
	}

	private void updateEditForm() {
		int ind[] = new int[1];
		ind[0] = 0;
		readyTasks.removeAll();
		readyTasksList = peerConfig.getReadyTasks(artefact);
		Vector<String> l = new Vector<String>();
		for(Pair<String, Task> pair : readyTasksList){
			l.add("(" + pair.snd.getSymbol() + ", " + pair.fst + ") - " + pair.snd.getDesc());
		}
		readyTasks.setListData(l);
		if(!l.isEmpty()){
			readyTasks.setSelectedIndices(ind);
			candidates = peerConfig.getLocalGMWf().getXProductions(readyTasksList.get(readyTasks.getSelectedIndex()).snd.getSymbol());
			updateCandidates();
		}else{
			status.setEditable(false);
			save.setEnabled(false);
			peerStatus.setExecuting(false);
			peerStatus.setPartialReplica(artefact);
			((IHMController)controller).expandAndDiffuse(peerConfig, peerStatus);
		}
		
		readyTasks.validate();
		readyTasks.updateUI();
		
		editFormPanel.validate();
		editFormPanel.updateUI();
	}
	
	private void updateCandidates() {
		int ind[] = new int[1];
		ind[0] = 0;
        productionList.removeAll();
        Vector<String> l = new Vector<String>();
		for(Production p : candidates){
			l.add(p.toString());
		}
		productionList.setListData(l);
		if(!l.isEmpty()){
			productionList.setSelectedIndices(ind);
			status.setEditable(true);
			save.setEnabled(true);
		}else{
			status.setEditable(false);
			save.setEnabled(false);
		}
		
		productionList.validate();
		productionList.updateUI();
		
		editFormPanel.validate();
		editFormPanel.updateUI();
	}

	private void initMenus() {
		
	}
	
	private void initToolBar() {
		this.toolBar = new JToolBar();
		toolBar.setPreferredSize(new Dimension(20, 20));
		toolBar.setFloatable(false);
        
		this.titleLabel = new JLabel();
        this.titleLabel.setText(ModelModel.getConfig().getLangValue("wysiwyg_execution"));
        titleLabel.setFont(new Font("Cambria", Font.BOLD, 12));
        
        ImageIcon icon = new ImageIcon(getClass().getResource("/smartworkflow/dwfms/urifia/fmml/miu/resources/images/edit_small.png"));
        titleLabel.setIcon(icon);
        titleLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        
        this.toolBar.add(this.titleLabel);
        
        this.add(this.toolBar, BorderLayout.NORTH);
	}

	public class Updater extends ObserverAdapter{
		@Override
		public void updatePeerStatus(WorkflowPeerStatus status) {
			peerStatus = status;
		}
		
		@Override
		public void updateReplicatedArtefactToEdit(
				PeerToPeerWorkflowArtefact partialReplica) {
			artefact = partialReplica.clone();
			updateArtefact();
		}
	}
}
