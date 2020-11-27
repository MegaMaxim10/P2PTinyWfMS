/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartworkflow.dwfms.urifia.fmml.miu.view.util.editor;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.joda.time.DateTime;

import smartworkflow.dwfms.urifia.fmml.miu.util.APPLConstants;
import smartworkflow.dwfms.urifia.fmml.miu.util.ConfigurationManager;
import smartworkflow.dwfms.urifia.fmml.miu.util.Theme;
import smartworkflow.dwfms.urifia.fmml.miu.util.exceptions.ApplException;
import smartworkflow.dwfms.urifia.fmml.miu.view.util.ChatDialogModel;

/**
 *
 * @author Ndadji Maxime
 */
public class AvatarDialog extends ChatDialogModel{
	private static final long serialVersionUID = 1L;
	private String avatar = null;
    private JLabel label;
    private ArrayList<JButton> lButtons;
    private JPanel panel;
    private JScrollPane scroll;
    private MouseListener mouseListener;
    private MouseEvent mouseEvent;
    private boolean multiple = true;
    
    public AvatarDialog(ConfigurationManager config, boolean modal, JFrame parent, boolean multiple){
        super(ChatDialogModel.OK_CANCEL_BUTTON, config, parent, config.getLangValue("choose_avatar"), modal, false);
        this.setSize(320, 430);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.multiple = multiple;
        this.addWindowListener(new WindowAdapter(){

            @Override
            public void windowClosing(WindowEvent e) {
                avatar = null;
            }
            
        });
        this.initComponents();
    }

    private void initComponents() {
        mouseEvent = null;
        listener = new ActionListener(){
            private JButton lastCommand = null;
            private DateTime lastTime = null;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                if(command.equalsIgnoreCase(CANCEL_KEY)){
                    avatar = null;
                    closeDialog();
                    return;
                }
                
                if(command.equalsIgnoreCase(OK_KEY)){
                    closeDialog();
                    return;
                }
                
                if(mouseEvent.isControlDown() && multiple){
                    if(avatar != null){
                        if(!avatar.contains(command)){
                            avatar += ((avatar.length() != 0) ? APPLConstants.SEPARATOR : "") + command;
                        }
                    }else
                        avatar = command;
                    lastCommand = (JButton)e.getSource();
                    ((JButton)e.getSource()).setBackground(config.getTheme().getButtonBgColor());
                    return;
                }
                
                if(mouseEvent.isShiftDown() && multiple){
                    if(avatar == null || (avatar != null && avatar.length() == 0)){
                        avatar = command;
                        for(JButton button : lButtons){
                            button.setBackground(null);
                            if(button.getActionCommand().equalsIgnoreCase(avatar))
                                button.setBackground(config.getTheme().getButtonBgColor());
                        }
                        lastCommand = (JButton)e.getSource();
                        return;
                    }
                    
                    int i = (lButtons.indexOf(e.getSource()) < lButtons.indexOf(lastCommand)) ? lButtons.indexOf(e.getSource()) : lButtons.indexOf(lastCommand),
                        j = (i == lButtons.indexOf(lastCommand)) ? lButtons.indexOf(e.getSource()) : lButtons.indexOf(lastCommand);
                    avatar = null;
                    for(JButton button : lButtons){
                        int k = lButtons.indexOf(button);
                        if(k >= i && k <= j){
                            if(avatar != null){
                                avatar += ((avatar.length() != 0) ? APPLConstants.SEPARATOR : "") + button.getActionCommand();
                                button.setBackground(config.getTheme().getButtonBgColor());
                            }else
                                avatar = command;
                        }else
                            button.setBackground(null);
                    }
                    lastCommand = (JButton)e.getSource();
                    return;
                }
                
                if(avatar != null && lastTime != null && avatar.length() != 0 && e.getSource().equals(lastCommand) && (DateTime.now().getMillis() - lastTime.getMillis()) < 1000){
                    closeDialog();
                    return;
                }
                avatar = command;
                for(JButton button : lButtons){
                    button.setBackground(null);
                    if(button.getActionCommand().equalsIgnoreCase(avatar))
                        button.setBackground(config.getTheme().getButtonBgColor());
                }
                lastCommand = (JButton)e.getSource();
                lastTime = DateTime.now();
            }
        };
        
        mouseListener = new MouseAdapter(){

            @Override
            public void mousePressed(MouseEvent e) {
                mouseEvent = e;
            }
            
        };
        
        lButtons = new ArrayList<JButton>();
        JButton button;
        ArrayList<String> avatars = APPLConstants.getFilesNamesMatchingPattern(new File("ressources/images/avatars"), "^[a-zA-Z0-9_-]{1,}((.png)|(.PNG)|(.jpg)|(.JPG)|(.jpeg)|(.JPEG)|(.gif)|(.GIF))");
        panel = new JPanel();
        
        label = new JLabel(config.getLangValue("choose_avatar"));
        addStep(label);
        
        stepTitle.setPreferredSize(new Dimension(275, 25));
        stateLabel.setPreferredSize(new Dimension(210, 25));
        
        label = new JLabel("");
        label.setPreferredSize(new Dimension(275, 10));
        panel.add(label);
        
        panel.setPreferredSize(new Dimension(275, Math.round(avatars.size()/4) * 71 + 40));
        for(String ava : avatars){
            try{
                button = new JButton();
                button.setActionCommand(ava);
                button.setIcon(new ImageIcon("ressources/images/avatars/"+ava));
                button.setPreferredSize(new Dimension(65, 65));
                button.setContentAreaFilled(false);
                button.setBorder(BorderFactory.createEtchedBorder());
                button.addActionListener(listener);
                button.addMouseListener(mouseListener);
                button.setFocusPainted(false);
                button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                button.setToolTipText("<html><body><font family=\""+config.getTheme().getToolTipFont().getFamily()+"\" size=\""+config.getTheme().getToolTipSize()+"\" color=\""+Theme.extractRGB(config.getTheme().getToolTipColor())+"\">"
                    +ava+"</font></body></html>");
                lButtons.add(button);
                panel.add(button);
            }catch(Exception e){
                
            }
        }
        
        for(JButton but : lButtons){
            but.setBackground(null);
            if(but.getActionCommand().equalsIgnoreCase(avatar))
                but.setBackground(config.getTheme().getButtonBgColor());
        }
        
        label = new JLabel("");
        label.setPreferredSize(new Dimension(275, 10));
        label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, config.getTheme().getBgColor()));
        panel.add(label);
        
        scroll = new JScrollPane(panel);
        scroll.setBorder(null);
        mainPanel.add(scroll, "1");
        
        okButton.addActionListener(listener);
        okButton.setText(config.getLangValue("choose"));
        okButton.setPreferredSize(new Dimension((okButton.getText().length()) * 11, 30));
        
        cancelButton.addActionListener(listener);
        cancelButton.setPreferredSize(new Dimension((cancelButton.getText().length()) * 11, 30));
        
        this.remove(this.stepPanel);
        try {
            setStep(1);
        } catch (ApplException ex) {
            
        }
    }

    public String getAvatar() {
        return avatar;
    }
}
