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
public class SmilieDialog extends ChatDialogModel{
	private static final long serialVersionUID = 1L;
	private String smilie = null;
    private JLabel label;
    private ArrayList<JButton> lButtons;
    private JPanel panel;
    private JScrollPane scroll;
    private MouseListener mouseListener;
    private MouseEvent mouseEvent;
    private boolean multiple = true;
    
    public SmilieDialog(ConfigurationManager config, boolean modal, JFrame parent, boolean multiple){
        super(ChatDialogModel.OK_CANCEL_BUTTON, config, parent, config.getLangValue("choose_smilie"), modal, false);
        this.setSize(270, 440);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.multiple = multiple;
        this.addWindowListener(new WindowAdapter(){

            @Override
            public void windowClosing(WindowEvent e) {
                smilie = null;
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
                    smilie = null;
                    closeDialog();
                    return;
                }
                
                if(command.equalsIgnoreCase(OK_KEY)){
                    closeDialog();
                    return;
                }
                
                if(mouseEvent.isControlDown() && multiple){
                    if(smilie != null){
                        if(!smilie.contains(command)){
                            smilie += ((smilie.length() != 0) ? APPLConstants.SEPARATOR : "") + command;
                        }
                    }else
                        smilie = command;
                    lastCommand = (JButton)e.getSource();
                    ((JButton)e.getSource()).setBackground(config.getTheme().getButtonBgColor());
                    return;
                }
                
                if(mouseEvent.isShiftDown() && multiple){
                    if(smilie == null || (smilie != null && smilie.length() == 0)){
                        smilie = command;
                        for(JButton button : lButtons){
                            button.setBackground(null);
                            if(button.getActionCommand().equalsIgnoreCase(smilie))
                                button.setBackground(config.getTheme().getButtonBgColor());
                        }
                        lastCommand = (JButton)e.getSource();
                        return;
                    }
                    
                    int i = (lButtons.indexOf(e.getSource()) < lButtons.indexOf(lastCommand)) ? lButtons.indexOf(e.getSource()) : lButtons.indexOf(lastCommand),
                        j = (i == lButtons.indexOf(lastCommand)) ? lButtons.indexOf(e.getSource()) : lButtons.indexOf(lastCommand);
                    smilie = null;
                    for(JButton button : lButtons){
                        int k = lButtons.indexOf(button);
                        if(k >= i && k <= j){
                            if(smilie != null){
                                smilie += ((smilie.length() != 0) ? APPLConstants.SEPARATOR : "") + button.getActionCommand();
                                button.setBackground(config.getTheme().getButtonBgColor());
                            }else
                                smilie = command;
                        }else
                            button.setBackground(null);
                    }
                    lastCommand = (JButton)e.getSource();
                    return;
                }
                
                if(smilie != null && lastTime != null && smilie.length() != 0 && e.getSource().equals(lastCommand) && (DateTime.now().getMillis() - lastTime.getMillis()) < 1000){
                    closeDialog();
                    return;
                }
                smilie = command;
                for(JButton button : lButtons){
                    button.setBackground(null);
                    if(button.getActionCommand().equalsIgnoreCase(smilie))
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
        ArrayList<String> smilies = APPLConstants.getFilesNamesMatchingPattern(new File("ressources/images/smilies"), "^[a-zA-Z0-9_-]{1,}((.png)|(.PNG)|(.jpg)|(.JPG)|(.jpeg)|(.JPEG)|(.gif)|(.GIF))");
        
        panel = new JPanel();
        
        label = new JLabel(config.getLangValue("choose_smilie"));
        addStep(label);
        
        stepTitle.setPreferredSize(new Dimension(230, 25));
        stateLabel.setPreferredSize(new Dimension(230, 25));
        
        label = new JLabel("");
        label.setPreferredSize(new Dimension(230, 10));
        panel.add(label);
        
        panel.setPreferredSize(new Dimension(210, Math.round(smilies.size()/4) * 35 + 40));
        for(String smi : smilies){
            try{
                button = new JButton();
                button.setActionCommand(smi);
                button.setIcon(new ImageIcon("ressources/images/smilies/"+smi));
                button.setPreferredSize(new Dimension(40, 34));
                button.setContentAreaFilled(false);
                button.setBorder(BorderFactory.createEtchedBorder());
                button.addActionListener(listener);
                button.addMouseListener(mouseListener);
                button.setFocusPainted(false);
                button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                button.setToolTipText("<html><body><font family=\""+config.getTheme().getToolTipFont().getFamily()+"\" size=\""+config.getTheme().getToolTipSize()+"\" color=\""+Theme.extractRGB(config.getTheme().getToolTipColor())+"\">"
                    +smi+"</font></body></html>");
                lButtons.add(button);
                panel.add(button);
            }catch(Exception e){
                
            }
        }
        
        for(JButton but : lButtons){
            but.setBackground(null);
            if(but.getActionCommand().equalsIgnoreCase(smilie))
                but.setBackground(config.getTheme().getButtonBgColor());
        }
        
        label = new JLabel("");
        label.setPreferredSize(new Dimension(230, 10));
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

    public String getSmilie() {
        return smilie;
    }
}
