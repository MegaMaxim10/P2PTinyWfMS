/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartworkflow.dwfms.urifia.fmml.miu.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import p2pTinyWfMS.P2pTinyWfMS;
import smartworkflow.dwfms.urifia.fmml.miu.controllers.IHMController;
import smartworkflow.dwfms.urifia.fmml.miu.models.util.ModelModel;
import smartworkflow.dwfms.urifia.fmml.miu.util.APPLConstants;
import smartworkflow.dwfms.urifia.fmml.miu.util.Theme;
import smartworkflow.dwfms.urifia.fmml.miu.util.observer.adapters.ObserverAdapter;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.Parsers;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.PeerToPeerWorkflow;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.WorkflowPeerConfiguration;
import smartworkflow.dwfms.urifia.fmml.miu.view.util.ChatButton;
import smartworkflow.dwfms.urifia.fmml.miu.view.util.FrameModel;

/**
 *
 * @author Ndadji Maxime
 */
public class MainView extends FrameModel{
	private static final long serialVersionUID = 1L;
    
    private JSplitPane splitV, leftSplitH, rightSplitH;
    private JPanel mainPanel;
    private JToolBar toolBar;
    private JMenuBar menuBar;
    private WorkflowExplorerPanel workflowExplorerPanel;
    private ControlPanel controlPanel;
    private StatePanel statePanel;
    private JTabbedPane workflowExplorerPane, mainPane, filePane; //statusPane;
    private Updater updater;
    private ActionListener listener;
    private PeerToPeerWorkflow workflow;
    private P2pTinyWfMS sonComInterface;
	//private EventPanel eventPanel;
    
	public MainView(IHMController controller) {
    	super(controller);
    	this.sonComInterface = ((IHMController)this.controller).getSonComInterface();
        this.setTitle(ModelModel.getConfig().getLangValue("appl_title") + " | " + sonComInterface.getIdName());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize((Integer.parseInt(ModelModel.getConfig().getWindowInfos().get("width")) < 900 ? 900 : Integer.parseInt(ModelModel.getConfig().getWindowInfos().get("width"))), 
                (Integer.parseInt(ModelModel.getConfig().getWindowInfos().get("height")) < 650 ? 650 : Integer.parseInt(ModelModel.getConfig().getWindowInfos().get("height"))));
        this.setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon(getClass().getResource(APPLConstants.RESOURCES_FOLDER+"images/logo.png"));
        this.setIconImage(icon.getImage());
        this.setMinimumSize(new Dimension(900, 650));
        this.setResizable(true);
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                HashMap<String, String> wInfos = new HashMap<String, String>();
                wInfos.put("width", (getSize().width < 900 ? 900 : getSize().width)+"");
                wInfos.put("height", (getSize().height < 650 ? 650 : getSize().height)+"");
                
                try {
                    ModelModel.getConfig().setWindowInfos(wInfos);
                } catch (Exception ex) {
                    
                }
            } 
        });
        
        updater = new Updater();
        
        this.initComponents();
        
        this.setVisible(true);
    }
    
    @Override
    protected final void initComponents() {
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
                if(command.equals("create")){
                    add();
                    return;
                }
                if("control".equals(command)){
                	
                }
            }
            
        };
    }
    
    public void add(){
        //WorkflowCreationDialog dialog = new WorkflowCreationDialog(ModelModel.getConfig(), true, null);
        //dialog.showDialog();
        //if(dialog.isCreate())
            //updater.notifyAll();
    }

    private void initPanels() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        
        statePanel = new StatePanel(controller);
        statePanel.setMinimumSize(new Dimension(400, 30));
        
        splitV = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        leftSplitH = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        rightSplitH = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        
        leftSplitH.setDividerSize(2);
        rightSplitH.setDividerSize(2);
        splitV.setDividerSize(2);
        
        workflowExplorerPane = new JTabbedPane();
        workflowExplorerPane.setPreferredSize(new Dimension(240, 300));
        workflowExplorerPane.setMinimumSize(new Dimension(240, 25));
        workflowExplorerPane.setFont(new Font("Cambria", Font.PLAIN, 12));
        workflowExplorerPane.setBorder(BorderFactory.createEtchedBorder());
        
        workflowExplorerPanel = new WorkflowExplorerPanel(controller);
        workflowExplorerPanel.addUpdater(updater);
        workflowExplorerPane.add(ModelModel.getConfig().getLangValue("remote_workflows"), workflowExplorerPanel);
        
        workflowExplorerPane.setMnemonicAt(0, KeyEvent.VK_L);
        
        filePane = new JTabbedPane();
        filePane.setPreferredSize(new Dimension(240, 250));
        filePane.setMinimumSize(new Dimension(240, 25));
        filePane.setFont(new Font("Cambria", Font.PLAIN, 12));
        filePane.setBorder(BorderFactory.createEtchedBorder());
        filePane.setTabPlacement(JTabbedPane.TOP);
        
        //leftSplitH.add(workflowExplorerPane);
        
        mainPane = new JTabbedPane();
        mainPane.setPreferredSize(new Dimension(500, 500));
        mainPane.setMinimumSize(new Dimension(500, 450));
        mainPane.setFont(new Font("Cambria", Font.PLAIN, 13));
        mainPane.setBorder(BorderFactory.createEtchedBorder());
        
        /*controlPanel = new ControlPanel(controller);
        controlPanel.addUpdater(updater);
        mainPane.add(ModelModel.getConfig().getLangValue("control_panel"), controlPanel);*/
        
        try{
	        PeerToPeerWorkflow workflow = Parsers.getPeerReviewWorkflow();
	        WorkflowPeerConfiguration peerConfig = workflow.projectGMWf(workflow.getStakeholder(sonComInterface.getIdName()));
	        //System.out.println(sonComInterface.getIdName() + " --- " + peerConfig.getLocalGMWf().getProductions());
	        
	        ((IHMController)controller).setPeerConfig(peerConfig);
	        
	        WorkflowPanel workflowPanel = new WorkflowPanel(controller, peerConfig);
	        workflowPanel.addUpdater(updater);
	        mainPane.add("Peer Review Wf", workflowPanel);
        }catch(Exception e){
        	ModelModel.displayErrorDialog(ModelModel.getConfig().getLangValue("error"), ModelModel.getConfig().getLangValue("creation_du_processus_impossible"));
        }
        
        /*try{
	        PeerToPeerWorkflow workflow = Parsers.getClaimProcessWorkflow();
	        WorkflowPeerConfiguration peerConfig = workflow.projectGMWf(workflow.getStakeholder(sonComInterface.getIdName()));
	        //System.out.println(sonComInterface.getIdName() + " --- " + peerConfig.getLocalGMWf().getProductions());
	        
	        ((IHMController)controller).setPeerConfig(peerConfig);
	        
	        WorkflowPanel workflowPanel = new WorkflowPanel(controller, peerConfig);
	        workflowPanel.addUpdater(updater);
	        mainPane.add("Traitement de réclamations", workflowPanel);
        }catch(Exception e){
        	e.printStackTrace();
        	ModelModel.displayErrorDialog(ModelModel.getConfig().getLangValue("error"), ModelModel.getConfig().getLangValue("creation_du_processus_impossible"));
        }*/
        
        /*statusPane = new JTabbedPane();
        statusPane.setPreferredSize(new Dimension(248, 250));
        statusPane.setMinimumSize(new Dimension(248, 25));
        statusPane.setFont(new Font("Cambria", Font.PLAIN, 12));
        statusPane.setBorder(BorderFactory.createEtchedBorder());
        statusPane.setTabPlacement(JTabbedPane.TOP);
        
        eventPanel = new EventPanel(controller);
        eventPanel.addUpdater(updater);
        statusPane.add(ModelModel.getConfig().getLangValue("events"), eventPanel);*/
        
        rightSplitH.add(mainPane);
        //rightSplitH.add(statusPane);
        
        splitV.add(leftSplitH);
        splitV.add(rightSplitH);
        
        splitV.setContinuousLayout(true);
        leftSplitH.setContinuousLayout(true);
        rightSplitH.setContinuousLayout(true);
        
        mainPanel.add(splitV);
        
        mainPanel.add(statePanel, BorderLayout.SOUTH);
        
        this.add(mainPanel);
    }

    private void initMenus() {
        menuBar = new JMenuBar();
        menuBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY.brighter()));
        
        JMenu menu = new JMenu(ModelModel.getConfig().getLangValue("file"));
        menu.setFont(ModelModel.getConfig().getTheme().getMenuFont());
        
        menuBar.add(menu);
        
        this.setJMenuBar(menuBar);
    }

    private void initToolBar() {
        toolBar = new JToolBar();
        toolBar.setPreferredSize(new Dimension(32, 32));
        toolBar.setBackground(ModelModel.getConfig().getTheme().getToolBarColor());
        
        toolBar.addSeparator(new Dimension(7, 7));
        
        ChatButton button = new ChatButton(ModelModel.getConfig().getTheme());
        button.setToolTipText("<html><body><font family=\""+ModelModel.getConfig().getTheme().getToolTipFont().getFamily()+"\" size=\""+ModelModel.getConfig().getTheme().getToolTipSize()+"\" color=\""+Theme.extractRGB(ModelModel.getConfig().getTheme().getToolTipColor())+"\">"
                +ModelModel.getConfig().getLangValue("control_panel") +"</font></body></html>");
        button.setPreferredSize(new Dimension(25, 25));
        button.setIcon(new ImageIcon(getClass().getResource(APPLConstants.RESOURCES_FOLDER+"images/home.png")));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setAreaFilled(false);
        button.setActionCommand("control");
        button.addActionListener(listener);
        toolBar.add(button);
        
        toolBar.addSeparator(new Dimension(15, 15));
        
        button = new ChatButton(ModelModel.getConfig().getTheme());
        button.setToolTipText("<html><body><font family=\""+ModelModel.getConfig().getTheme().getToolTipFont().getFamily()+"\" size=\""+ModelModel.getConfig().getTheme().getToolTipSize()+"\" color=\""+Theme.extractRGB(ModelModel.getConfig().getTheme().getToolTipColor())+"\">"
                +ModelModel.getConfig().getLangValue("switch_user") +"</font></body></html>");
        button.setPreferredSize(new Dimension(25, 25));
        button.setIcon(new ImageIcon(getClass().getResource(APPLConstants.RESOURCES_FOLDER+"images/users.png")));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setAreaFilled(false);
        button.setActionCommand("switch");
        button.addActionListener(listener);
        toolBar.add(button);
        
        toolBar.addSeparator(new Dimension(3, 3));
        
        button = new ChatButton(ModelModel.getConfig().getTheme());
        button.setToolTipText("<html><body><font family=\""+ModelModel.getConfig().getTheme().getToolTipFont().getFamily()+"\" size=\""+ModelModel.getConfig().getTheme().getToolTipSize()+"\" color=\""+Theme.extractRGB(ModelModel.getConfig().getTheme().getToolTipColor())+"\">"
                +ModelModel.getConfig().getLangValue("create_workflow") +"</font></body></html>");
        button.setPreferredSize(new Dimension(25, 25));
        button.setIcon(new ImageIcon(getClass().getResource(APPLConstants.RESOURCES_FOLDER+"images/add_big.png")));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setAreaFilled(false);
        button.setActionCommand("create");
        button.addActionListener(listener);
        toolBar.add(button);
        
        toolBar.addSeparator(new Dimension(3, 3));
        
        button = new ChatButton(ModelModel.getConfig().getTheme());
        button.setToolTipText("<html><body><font family=\""+ModelModel.getConfig().getTheme().getToolTipFont().getFamily()+"\" size=\""+ModelModel.getConfig().getTheme().getToolTipSize()+"\" color=\""+Theme.extractRGB(ModelModel.getConfig().getTheme().getToolTipColor())+"\">"
                +ModelModel.getConfig().getLangValue("edit_workflow") +"</font></body></html>");
        button.setPreferredSize(new Dimension(25, 25));
        button.setIcon(new ImageIcon(getClass().getResource(APPLConstants.RESOURCES_FOLDER+"images/edit.png")));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setAreaFilled(false);
        button.setActionCommand("edit");
        button.addActionListener(listener);
        toolBar.add(button);
        
        toolBar.addSeparator(new Dimension(3, 3));
        
        button = new ChatButton(ModelModel.getConfig().getTheme());
        button.setToolTipText("<html><body><font family=\""+ModelModel.getConfig().getTheme().getToolTipFont().getFamily()+"\" size=\""+ModelModel.getConfig().getTheme().getToolTipSize()+"\" color=\""+Theme.extractRGB(ModelModel.getConfig().getTheme().getToolTipColor())+"\">"
                +ModelModel.getConfig().getLangValue("delete_workflow") +"</font></body></html>");
        button.setPreferredSize(new Dimension(25, 25));
        button.setIcon(new ImageIcon(getClass().getResource(APPLConstants.RESOURCES_FOLDER+"images/trash.png")));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setAreaFilled(false);
        button.setActionCommand("delete");
        button.addActionListener(listener);
        toolBar.add(button);
        
        toolBar.addSeparator(new Dimension(15, 15));
        
        button = new ChatButton(ModelModel.getConfig().getTheme());
        button.setToolTipText("<html><body><font family=\""+ModelModel.getConfig().getTheme().getToolTipFont().getFamily()+"\" size=\""+ModelModel.getConfig().getTheme().getToolTipSize()+"\" color=\""+Theme.extractRGB(ModelModel.getConfig().getTheme().getToolTipColor())+"\">"
                +ModelModel.getConfig().getLangValue("connect_to_workflow") +"</font></body></html>");
        button.setPreferredSize(new Dimension(25, 25));
        button.setIcon(new ImageIcon(getClass().getResource(APPLConstants.RESOURCES_FOLDER+"images/connect.png")));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setAreaFilled(false);
        button.setActionCommand("connect");
        button.addActionListener(listener);
        toolBar.add(button);
        
        toolBar.addSeparator(new Dimension(3, 3));
        
        button = new ChatButton(ModelModel.getConfig().getTheme());
        button.setToolTipText("<html><body><font family=\""+ModelModel.getConfig().getTheme().getToolTipFont().getFamily()+"\" size=\""+ModelModel.getConfig().getTheme().getToolTipSize()+"\" color=\""+Theme.extractRGB(ModelModel.getConfig().getTheme().getToolTipColor())+"\">"
                +ModelModel.getConfig().getLangValue("save_workflow") +"</font></body></html>");
        button.setPreferredSize(new Dimension(25, 25));
        button.setIcon(new ImageIcon(getClass().getResource(APPLConstants.RESOURCES_FOLDER+"images/save.png")));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setAreaFilled(false);
        button.setActionCommand("save");
        button.addActionListener(listener);
        toolBar.add(button);
        
        toolBar.addSeparator(new Dimension(3, 3));
        
        button = new ChatButton(ModelModel.getConfig().getTheme());
        button.setToolTipText("<html><body><font family=\""+ModelModel.getConfig().getTheme().getToolTipFont().getFamily()+"\" size=\""+ModelModel.getConfig().getTheme().getToolTipSize()+"\" color=\""+Theme.extractRGB(ModelModel.getConfig().getTheme().getToolTipColor())+"\">"
                +ModelModel.getConfig().getLangValue("synchronize") +"</font></body></html>");
        button.setPreferredSize(new Dimension(25, 25));
        button.setIcon(new ImageIcon(getClass().getResource(APPLConstants.RESOURCES_FOLDER+"images/synchronize.png")));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setAreaFilled(false);
        button.setActionCommand("synchronize");
        button.addActionListener(listener);
        toolBar.add(button);
        
        toolBar.addSeparator(new Dimension(3, 3));
        
        button = new ChatButton(ModelModel.getConfig().getTheme());
        button.setToolTipText("<html><body><font family=\""+ModelModel.getConfig().getTheme().getToolTipFont().getFamily()+"\" size=\""+ModelModel.getConfig().getTheme().getToolTipSize()+"\" color=\""+Theme.extractRGB(ModelModel.getConfig().getTheme().getToolTipColor())+"\">"
                +ModelModel.getConfig().getLangValue("global_view_workflow") +"</font></body></html>");
        button.setPreferredSize(new Dimension(25, 25));
        button.setIcon(new ImageIcon(getClass().getResource(APPLConstants.RESOURCES_FOLDER+"images/global.png")));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setAreaFilled(false);
        button.setActionCommand("global");
        button.addActionListener(listener);
        toolBar.add(button);
        
        toolBar.addSeparator(new Dimension(15, 15));
        
        button = new ChatButton(ModelModel.getConfig().getTheme());
        button.setToolTipText("<html><body><font family=\""+ModelModel.getConfig().getTheme().getToolTipFont().getFamily()+"\" size=\""+ModelModel.getConfig().getTheme().getToolTipSize()+"\" color=\""+Theme.extractRGB(ModelModel.getConfig().getTheme().getToolTipColor())+"\">"
                +ModelModel.getConfig().getLangValue("gui_manage") +"</font></body></html>");
        button.setPreferredSize(new Dimension(25, 25));
        button.setIcon(new ImageIcon(getClass().getResource(APPLConstants.RESOURCES_FOLDER+"images/gallery.png")));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setAreaFilled(false);
        button.setActionCommand("gui");
        button.addActionListener(listener);
        toolBar.add(button);
        
        toolBar.addSeparator(new Dimension(15, 15));
        
        button = new ChatButton(ModelModel.getConfig().getTheme());
        button.setToolTipText("<html><body><font family=\""+ModelModel.getConfig().getTheme().getToolTipFont().getFamily()+"\" size=\""+ModelModel.getConfig().getTheme().getToolTipSize()+"\" color=\""+Theme.extractRGB(ModelModel.getConfig().getTheme().getToolTipColor())+"\">"
                +ModelModel.getConfig().getLangValue("mail_tool") +"</font></body></html>");
        button.setPreferredSize(new Dimension(25, 25));
        button.setIcon(new ImageIcon(getClass().getResource(APPLConstants.RESOURCES_FOLDER+"images/email.png")));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setAreaFilled(false);
        button.setActionCommand("mail");
        button.addActionListener(listener);
        toolBar.add(button);
        
        toolBar.addSeparator(new Dimension(3, 3));
        
        button = new ChatButton(ModelModel.getConfig().getTheme());
        button.setToolTipText("<html><body><font family=\""+ModelModel.getConfig().getTheme().getToolTipFont().getFamily()+"\" size=\""+ModelModel.getConfig().getTheme().getToolTipSize()+"\" color=\""+Theme.extractRGB(ModelModel.getConfig().getTheme().getToolTipColor())+"\">"
                +ModelModel.getConfig().getLangValue("appl_info_tool") +"</font></body></html>");
        button.setPreferredSize(new Dimension(25, 25));
        button.setIcon(new ImageIcon(getClass().getResource(APPLConstants.RESOURCES_FOLDER+"images/info.png")));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setAreaFilled(false);
        button.setActionCommand("infos");
        button.addActionListener(listener);
        toolBar.add(button);
        
        toolBar.addSeparator(new Dimension(3, 3));
        
        button = new ChatButton(ModelModel.getConfig().getTheme());
        button.setToolTipText("<html><body><font family=\""+ModelModel.getConfig().getTheme().getToolTipFont().getFamily()+"\" size=\""+ModelModel.getConfig().getTheme().getToolTipSize()+"\" color=\""+Theme.extractRGB(ModelModel.getConfig().getTheme().getToolTipColor())+"\">"
                +ModelModel.getConfig().getLangValue("help_tool") +"</font></body></html>");
        button.setPreferredSize(new Dimension(25, 25));
        button.setIcon(new ImageIcon(getClass().getResource(APPLConstants.RESOURCES_FOLDER+"images/help.png")));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setAreaFilled(false);
        button.setActionCommand("help");
        button.addActionListener(listener);
        toolBar.add(button);
        
        
        
        this.add(toolBar, BorderLayout.NORTH);
    }
    
    public void setCurrentWorkflow(PeerToPeerWorkflow workflow){
        
    }
    
    public void updateMainView(){
        mainPane.removeAll();
        mainPane.validate();
        mainPane.repaint();
        mainPane.updateUI();
        
        mainPane.add(ModelModel.getConfig().getLangValue("control_panel"), controlPanel);
        if(workflow != null){
            
        }
        
        mainPane.validate();
        mainPane.repaint();
        mainPane.updateUI();
    }
    
    public class Updater extends ObserverAdapter{

        @Override
        public void updateUserChanged() {
            workflowExplorerPanel.refresh();
            workflow = null;
            updateMainView();
        }

        @Override
        public void updateWorkflow(PeerToPeerWorkflow workflow) {
            setCurrentWorkflow(workflow);
        }
        
        @Override
        public void updateThemeChanged() {
            remove(toolBar);
            initToolBar();
            remove(menuBar);
            initMenus();
            validate();
            int confirm = JOptionPane.showConfirmDialog(null, config.getLangValue("effect_changes")+"\n"+config.getLangValue("restart_confirm"), config.getLangValue("restart_confirm")
                                , JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE); 
            if(confirm == JOptionPane.OK_OPTION){
                new MainView((IHMController)controller);
                dispose();
            }
        }
    }
}
