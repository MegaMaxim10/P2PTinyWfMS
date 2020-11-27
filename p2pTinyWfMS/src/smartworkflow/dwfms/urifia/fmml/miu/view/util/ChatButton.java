package smartworkflow.dwfms.urifia.fmml.miu.view.util;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import smartworkflow.dwfms.urifia.fmml.miu.util.Theme;
import smartworkflow.dwfms.urifia.fmml.miu.util.Utilities;

/**
 *
 * @author Ndadji Maxime
 */
public class ChatButton extends JButton{
	private static final long serialVersionUID = 1L;
	private MouseListener listener = new MouseListener();
    private Theme theme;
    private boolean areaFilled = true, hover = false;
    
    public ChatButton(String cmd) {
        this();
        this.setText(cmd);
    }
    
    public ChatButton(String cmd, Theme theme) {
        this(theme);
        this.setText(cmd);
    }

    public ChatButton() {
        this.theme = new Theme();
        this.init();
    }
    
    public ChatButton(Theme theme) {
        this.theme = theme;
        this.init();
    }
    
    private void init(){
        this.setBorder(BorderFactory.createLineBorder(theme.getButtonBorderColor()));
        this.setBackground(theme.getButtonBgColor());
        this.setForeground(theme.getButtonForegroundColor());
        this.setFont(theme.getButtonFont());
        this.addMouseListener(listener);
        this.addKeyListener(Utilities.BUTTON_DEFAULT_KEY_LISTENER);
    }
    
    private class MouseListener extends MouseAdapter{

        @Override
        public void mouseEntered(MouseEvent e) {
            if(isEnabled()){
                ((ChatButton)e.getSource()).setContentAreaFilled(true);
                ((ChatButton)e.getSource()).setBackground(theme.getButtonHoverBgColor());
                ((ChatButton)e.getSource()).setForeground(theme.getButtonHoverForegroundColor());
                ((ChatButton)e.getSource()).setBorder(BorderFactory.createLineBorder(theme.getButtonHoverBorderColor()));
            }
            hover = true;
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if(isEnabled()){
                ((ChatButton)e.getSource()).setContentAreaFilled(areaFilled);
                ((ChatButton)e.getSource()).setBackground(theme.getButtonBgColor());
                ((ChatButton)e.getSource()).setForeground(theme.getButtonForegroundColor());
                ((ChatButton)e.getSource()).setBorder(BorderFactory.createLineBorder(theme.getButtonBorderColor()));
            }
            hover = false;
        }
    }

    public void setAreaFilled(boolean areaFilled) {
        this.areaFilled = areaFilled;
    }
    
    @Override
    public void setEnabled(boolean enabled){
        super.setEnabled(enabled);
        if(!enabled){
            setContentAreaFilled(areaFilled);
            setBackground(theme.getButtonDisabledBgColor());
            setBorder(BorderFactory.createLineBorder(theme.getButtonDisabledBorderColor()));
        }else{
            if(hover){
                setContentAreaFilled(true);
                setBackground(theme.getButtonHoverBgColor());
                setForeground(theme.getButtonHoverForegroundColor());
                setBorder(BorderFactory.createLineBorder(theme.getButtonHoverBorderColor()));
            }else{
                setContentAreaFilled(areaFilled);
                setBackground(theme.getButtonBgColor());
                setForeground(theme.getButtonForegroundColor());
                setBorder(BorderFactory.createLineBorder(theme.getButtonBorderColor()));
            }
        }
    }
}
