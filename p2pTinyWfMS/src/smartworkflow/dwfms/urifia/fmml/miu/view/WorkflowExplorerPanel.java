/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartworkflow.dwfms.urifia.fmml.miu.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import smartworkflow.dwfms.urifia.fmml.miu.controllers.util.ControllerModel;
import smartworkflow.dwfms.urifia.fmml.miu.models.util.ModelModel;
import smartworkflow.dwfms.urifia.fmml.miu.util.APPLConstants;
import smartworkflow.dwfms.urifia.fmml.miu.util.Theme;
import smartworkflow.dwfms.urifia.fmml.miu.view.util.ChatButton;
import smartworkflow.dwfms.urifia.fmml.miu.view.util.PanelModel;

/**
 *
 * @author Ndadji Maxime
 */
public class WorkflowExplorerPanel extends PanelModel{
	private static final long serialVersionUID = 1L;
    private JToolBar toolBar;
    private ActionListener toolBarListener;
    private JPanel mainPanel;
    private JTextField filter;
    private JTree jtree;
    private ArrayList<HashMap<String, String>> workflows;

    public WorkflowExplorerPanel(ControllerModel controller) {
        super(controller);
        this.initComponents();
    }
    
    @Override
    protected final void initComponents() {
        this.setLayout(new BorderLayout());
        
        this.initListeners();
        
        this.initPanels();
        
        this.initMenus();
        
        this.initToolBar();
        
        refresh();
    }

    private void initListeners() {
        toolBarListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                if(command.equals("connect")){
                    return;
                }
                
                if(command.equals("add")){
                    add();
                    return;
                }
                
                if(command.equals("delete")){
                    delete();
                    return;
                }
                
                if(command.equals("gview")){
                    gview();
                    return;
                }
                
                if(command.equals("refresh")){
                    refresh();
                    return;
                }
                
                if(command.equals("switch")){
                    if(ModelModel.authentify())
                        //notifier.notifyUserChanged();
                    return;
                }
            }
        };
    }
    
    public void gview(){
        
    }
    
    public void delete(){
        if(jtree != null){
            Object path = jtree.getLastSelectedPathComponent();
            if(path != null){
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)path;
                while(node.getLevel() > 1)
                    node = (DefaultMutableTreeNode)node.getParent();
                DefaultMutableTreeNode id = (DefaultMutableTreeNode)node.getFirstChild();
                String idVal = id.toString().split(" : ")[1];
                try{
                    idVal = APPLConstants.encryptMessage(idVal);
                    for(HashMap<String, String> wf : workflows){
                        if(idVal.equals(wf.get("id"))){
                            String encLogin = APPLConstants.encryptMessage(ModelModel.getConfig().getUser().get("login"));
                            if(wf.get("creator").equals(encLogin)){
                                if(JOptionPane.showConfirmDialog(null, ModelModel.getConfig().getLangValue("confirm_delete_message"), ModelModel.getConfig().getLangValue("confirm_delete"), 
                                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION){
                                    boolean deleted = true;
                                    if(deleted)
                                        refresh();
                                    else
                                        JOptionPane.showMessageDialog(null, ModelModel.getConfig().getLangValue("delete_error_message"), ModelModel.getConfig().getLangValue("delete_error"), 
                                            JOptionPane.ERROR_MESSAGE);
                                }
                            }else{
                                JOptionPane.showMessageDialog(null, ModelModel.getConfig().getLangValue("cannot_delete"), ModelModel.getConfig().getLangValue("delete_error"), 
                                        JOptionPane.ERROR_MESSAGE); 
                            }
                        }

                    }
                }catch(Throwable ex){
                    JOptionPane.showMessageDialog(null, ModelModel.getConfig().getLangValue("delete_error_message"), ModelModel.getConfig().getLangValue("delete_error"), 
                            JOptionPane.ERROR_MESSAGE);
                }
            }else{
                JOptionPane.showMessageDialog(null, ModelModel.getConfig().getLangValue("select_element_message"), ModelModel.getConfig().getLangValue("select_element"), 
                        JOptionPane.ERROR_MESSAGE);
            }
        }else{
            JOptionPane.showMessageDialog(null, ModelModel.getConfig().getLangValue("select_element_message"), ModelModel.getConfig().getLangValue("select_element"), 
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void add(){
        
    }
    
    private void initPanels() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        this.add(mainPanel);
    }

    private void initMenus() {
        
    }

    private void initToolBar() {
        toolBar = new JToolBar(JToolBar.HORIZONTAL);
        toolBar.setPreferredSize(new Dimension(100, 25));
        toolBar.setFloatable(false);
        toolBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY.brighter()));
        
        filter = new JTextField();
        filter.setPreferredSize(new Dimension(160, 25));
        filter.setFont(ModelModel.getConfig().getTheme().getAreasFont());
        toolBar.add(filter);
        
        toolBar.addSeparator(new Dimension(6, 6));
        
        ChatButton button = new ChatButton();
        button.setToolTipText("<html><body><font family=\""+ModelModel.getConfig().getTheme().getToolTipFont().getFamily()+"\" size=\""+ModelModel.getConfig().getTheme().getToolTipSize()+"\" color=\""+Theme.extractRGB(ModelModel.getConfig().getTheme().getToolTipColor())+"\">"
                +ModelModel.getConfig().getLangValue("connect_to_workflow")+"</font></body></html>");
        button.setPreferredSize(new Dimension(25, 25));
        button.setIcon(new ImageIcon(getClass().getResource(APPLConstants.RESOURCES_FOLDER+"images/connect_small.png")));
        button.setActionCommand("connect");
        button.addActionListener(toolBarListener);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setAreaFilled(false);
        toolBar.add(button);
        
        toolBar.addSeparator(new Dimension(6, 6));
        
        button = new ChatButton();
        button.setToolTipText("<html><body><font family=\""+ModelModel.getConfig().getTheme().getToolTipFont().getFamily()+"\" size=\""+ModelModel.getConfig().getTheme().getToolTipSize()+"\" color=\""+Theme.extractRGB(ModelModel.getConfig().getTheme().getToolTipColor())+"\">"
                +ModelModel.getConfig().getLangValue("create_workflow")+"</font></body></html>");
        button.setPreferredSize(new Dimension(25, 25));
        button.setIcon(new ImageIcon(getClass().getResource(APPLConstants.RESOURCES_FOLDER+"images/add.png")));
        button.setActionCommand("add");
        button.addActionListener(toolBarListener);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setAreaFilled(false);
        toolBar.add(button);
        
        this.add(toolBar, BorderLayout.NORTH);
    }
    
    public void refresh(){
        
    }
}
