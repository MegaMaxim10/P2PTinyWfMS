/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartworkflow.dwfms.urifia.fmml.miu.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import smartworkflow.dwfms.urifia.fmml.miu.util.APPLConstants;
import smartworkflow.dwfms.urifia.fmml.miu.util.ConfigurationManager;
import smartworkflow.dwfms.urifia.fmml.miu.util.Theme;
import smartworkflow.dwfms.urifia.fmml.miu.util.exceptions.ApplException;
import smartworkflow.dwfms.urifia.fmml.miu.view.util.ChatButton;
import smartworkflow.dwfms.urifia.fmml.miu.view.util.ChatDialogModel;

/**
 *
 * @author Ndadji Maxime
 */
public class WorkspaceDialog extends ChatDialogModel{
	private static final long serialVersionUID = 1L;
    private JLabel label;
    private JFileChooser chooser;
    private JTextField workspace;
    private JCheckBox save;
    private JPanel panel;
    private JScrollPane scroll;
    public boolean changed = false;
    
    public WorkspaceDialog(ConfigurationManager config, boolean modal, JFrame parent){
        super(ChatDialogModel.OK_CANCEL_BUTTON, config, parent, config.getLangValue("workspace_manage"), modal, false);
        this.setSize(540, 300);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.initComponents();
    }

    private void initComponents() {
        listener = new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                if(command.equalsIgnoreCase(CANCEL_KEY)){
                    try {
                        File f = new File(APPLConstants.WORKSPACE_ID);
                        if(!f.exists())
                            f.mkdirs();
                        config.setWorkspace(f.getAbsolutePath().replace("%20", " "));
                        config.setAskWorkspaceState(!save.isSelected());
                        if(config.isWorkspaceValid())
                            closeDialog();
                        else
                            setError(config.getLangValue("workspace_invalid"));
                    } catch (Exception ex) {
                       setError(config.getLangValue("workspace_invalid"));
                    }
                    return;
                }
                
                if(command.equals("browse")){
                    File f = new File(workspace.getText());
                    chooser.setCurrentDirectory(f);
                    int result = chooser.showDialog(mainPanel, config.getLangValue("select"));
                    if(result == JFileChooser.APPROVE_OPTION){
                        workspace.setText(chooser.getSelectedFile().getPath());
                        workspace.setToolTipText("<html><body><font family=\""+config.getTheme().getToolTipFont().getFamily()+"\" size=\""+config.getTheme().getToolTipSize()+"\" color=\""+Theme.extractRGB(config.getTheme().getToolTipColor())+"\">"
                            +chooser.getSelectedFile().getPath()+"</font></body></html>");
                    }
                }
                
                if(command.equalsIgnoreCase(OK_KEY)){
                    try {
                        config.setWorkspace(workspace.getText());
                        if(config.isWorkspaceValid()){
                            config.setAskWorkspaceState(!save.isSelected());
                            closeDialog();
                        }else
                            setError(config.getLangValue("workspace_invalid"));
                    } catch (ApplException ex) {
                       setError(config.getLangValue("workspace_invalid"));
                    }
                }
            }
        };
        
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(500, 150));
        
        label = new JLabel(config.getLangValue("workspace_manage"));
        addStep(label);
        
        stepTitle.setPreferredSize(new Dimension(500, 25));
        stateLabel.setPreferredSize(new Dimension(500, 25));
        
        label = new JLabel("");
        label.setPreferredSize(new Dimension(500, 10));
        panel.add(label);
        
        label = new JLabel(config.getLangValue("workspace")+" :");
        label.setFont(config.getTheme().getMenuFont());
        label.setPreferredSize(new Dimension(500, 25));
        panel.add(label);
        
        workspace = new JTextField(config.getWorkspace());
        workspace.setPreferredSize(new Dimension(464, 30));
        workspace.setFont(config.getTheme().getAreasFont());
        panel.add(workspace);
        
        ChatButton button = new ChatButton(config.getTheme());
        button.setActionCommand("browse");
        button.setIcon(new ImageIcon(getClass().getResource(APPLConstants.RESOURCES_FOLDER+"images/search.png")));
        button.setPreferredSize(new Dimension(30, 30));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setAreaFilled(false);
        button.setToolTipText("<html><body><font family=\""+config.getTheme().getToolTipFont().getFamily()+"\" size=\""+config.getTheme().getToolTipSize()+"\" color=\""+Theme.extractRGB(config.getTheme().getToolTipColor())+"\">"
                +config.getLangValue("browse") +"</font></body></html>");
        button.addActionListener(listener);
        panel.add(button);
        
        chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setMultiSelectionEnabled(false);
        chooser.setFileHidingEnabled(true);
        chooser.setFont(config.getTheme().getAreasFont());
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setDialogTitle(config.getLangValue("select_workspace"));
        
        save = new JCheckBox(config.getLangValue("save_no_ask"), !config.isAskWorkspaceActivated());
        save.setFont(config.getTheme().getSecondTitleFont());
        save.setPreferredSize(new Dimension(500, 25));
        panel.add(save);
        
        label = new JLabel("");
        label.setPreferredSize(new Dimension(500, 10));
        label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, config.getTheme().getBgColor()));
        panel.add(label);
        
        scroll = new JScrollPane(panel);
        scroll.setBorder(null);
        mainPanel.add(scroll, "1");
        
        okButton.addActionListener(listener);
        okButton.setText(config.getLangValue("save"));
        okButton.setPreferredSize(new Dimension((okButton.getText().length()) * 11, 30));
        
        cancelButton.addActionListener(listener);
        cancelButton.setText(config.getLangValue("use_default"));
        cancelButton.setPreferredSize(new Dimension((cancelButton.getText().length()) * 11, 30));
        this.remove(this.stepPanel);
        try {
            setStep(1);
        } catch (ApplException ex) {
            
        }
    }
    
    public void setError(String error){
        stateLabel.setForeground(Color.red);
        stateLabel.setText(error);
        if(error != null && !error.isEmpty())
            stateLabel.setIcon(new ImageIcon(getClass().getResource(APPLConstants.RESOURCES_FOLDER+"images/error.png")));
    }
}
