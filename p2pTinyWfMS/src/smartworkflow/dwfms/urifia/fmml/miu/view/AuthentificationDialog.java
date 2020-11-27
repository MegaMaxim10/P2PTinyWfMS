/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartworkflow.dwfms.urifia.fmml.miu.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
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
public class AuthentificationDialog extends ChatDialogModel{
	private static final long serialVersionUID = 1L;
	private boolean connected = false;
    private JLabel label;
    private JTextField login;
    private JPasswordField password;
    private JCheckBox remember;
    @SuppressWarnings("unused")
	private int count = 0;
    private char echoChar;
    private JPanel panel;
    private JScrollPane scroll;
    
    public AuthentificationDialog (ConfigurationManager config, boolean modal, JFrame parent){
        super(ChatDialogModel.OK_CANCEL_BUTTON, config, parent, config.getLangValue("auth"), modal, false);
        this.setAlwaysOnTop(false);
        this.setSize(340, 390);
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
                    connected = false;
                    closeDialog();
                    return;
                }
                
                if(command.equalsIgnoreCase("show_pass")){
                    if(password.getEchoChar() == '\0'){
                        password.setEchoChar(echoChar);
                        ((ChatButton)e.getSource()).setIcon(new ImageIcon(getClass().getResource(APPLConstants.RESOURCES_FOLDER+"images/eye.png")));
                    }else{
                        password.setEchoChar('\0');
                        ((ChatButton)e.getSource()).setIcon(new ImageIcon(getClass().getResource(APPLConstants.RESOURCES_FOLDER+"images/eye_closed.png")));
                    }
                    return;
                }
                
                if(command.equalsIgnoreCase(OK_KEY)){
                    try{
                        char[] p = password.getPassword();
                        String pass = "";
                        for(char c : p)
                            pass += c;
                        if(login.getText() == null || login.getText().trim().isEmpty() || pass.isEmpty())
                            throw new Exception();
                        HashMap<String, String> uInfos = new HashMap<String, String>();
                        uInfos.put("login", login.getText());
                        uInfos.put("password", pass);
                        config.setUser(uInfos);
                        if(remember.isSelected()){
                            try {
                                config.setUserInfos(uInfos);
                            } catch (ApplException ex) {
                                
                            }
                        }
                        try {
                            config.setUserRememberState(remember.isSelected());
                        } catch (ApplException ex) {

                        }
                        connected = true;
                        closeDialog();
                    }catch(Exception ex){
                        connected = false;
                        stateLabel.setForeground(Color.red);
                        stateLabel.setText(config.getLangValue("auth_required"));
                        stateLabel.setIcon(new ImageIcon(getClass().getResource(APPLConstants.RESOURCES_FOLDER+"images/error.png")));
                    }
                }
            }
        };
        
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(300, 120));
        
        label = new JLabel(config.getLangValue("specify_identifiers"));
        addStep(label);
        
        stepTitle.setPreferredSize(new Dimension(300, 25));
        stateLabel.setPreferredSize(new Dimension(300, 25));
        
        label = new JLabel("");
        label.setPreferredSize(new Dimension(300, 10));
        panel.add(label);
        
        label = new JLabel(config.getLangValue("login")+" :");
        label.setFont(config.getTheme().getMenuFont());
        label.setPreferredSize(new Dimension(300, 25));
        panel.add(label);
        try {
            login = new JTextField(config.isUserRememberActivated() ? config.getUserInfos().get("login") : "");
        } catch (ApplException ex) {
            login = new JTextField();
        }
        login.setPreferredSize(new Dimension(300, 30));
        login.setFont(config.getTheme().getAreasFont());
        panel.add(login);
        
        label = new JLabel(config.getLangValue("password")+" :");
        label.setFont(config.getTheme().getMenuFont());
        label.setPreferredSize(new Dimension(300, 25));
        panel.add(label);
        try {
            password = new JPasswordField(config.isUserRememberActivated() ? config.getUserInfos().get("password") : "");
        } catch (ApplException ex) {
            password = new JPasswordField();
        }
        password.setPreferredSize(new Dimension(245, 30));
        password.setFont(config.getTheme().getAreasFont());
        echoChar = password.getEchoChar();
        panel.add(password);
        
        ChatButton button = new ChatButton(config.getTheme());
        button.setActionCommand("show_pass");
        button.setIcon(new ImageIcon(getClass().getResource(APPLConstants.RESOURCES_FOLDER+"images/eye.png")));
        button.setPreferredSize(new Dimension(49, 30));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setAreaFilled(false);
        button.setToolTipText("<html><body><font family=\""+config.getTheme().getToolTipFont().getFamily()+"\" size=\""+config.getTheme().getToolTipSize()+"\" color=\""+Theme.extractRGB(config.getTheme().getToolTipColor())+"\">"
                +config.getLangValue("show_pass") +"</font></body></html>");
        button.addActionListener(listener);
        panel.add(button);
        
        remember = new JCheckBox(config.getLangValue("remember_user"), config.isUserRememberActivated());
        remember.setFont(config.getTheme().getSecondTitleFont());
        remember.setPreferredSize(new Dimension(300, 25));
        remember.setHorizontalAlignment(JLabel.CENTER);
        panel.add(remember);
        
        label = new JLabel("");
        label.setPreferredSize(new Dimension(300, 10));
        label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, config.getTheme().getBgColor()));
        panel.add(label);
        
        scroll = new JScrollPane(panel);
        scroll.setBorder(null);
        mainPanel.add(scroll, "1");
        
        okButton.addActionListener(listener);
        okButton.setText(config.getLangValue("auth"));
        okButton.setPreferredSize(new Dimension((okButton.getText().length()) * 11, 30));
        
        cancelButton.addActionListener(listener);
        cancelButton.setText(config.getLangValue("close"));
        cancelButton.setPreferredSize(new Dimension((cancelButton.getText().length()) * 11, 30));
        
        this.remove(this.stepPanel);
        try {
            setStep(1);
        } catch (ApplException ex) {
            
        }
    }
    
    public boolean isConnected() {
        return connected;
    }
    
    public void setError(String error){
        stateLabel.setForeground(Color.red);
        stateLabel.setText(error);
        if(error != null && !error.isEmpty())
            stateLabel.setIcon(new ImageIcon(getClass().getResource(APPLConstants.RESOURCES_FOLDER+"images/error.png")));
    }
}
