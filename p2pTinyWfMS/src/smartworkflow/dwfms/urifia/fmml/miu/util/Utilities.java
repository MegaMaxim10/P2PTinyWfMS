/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartworkflow.dwfms.urifia.fmml.miu.util;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;

/**
 *
 * @author Ndadji Maxime
 */
public class Utilities {
    public static KeyListener BUTTON_DEFAULT_KEY_LISTENER = new KeyAdapter(){

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyChar() == '\n'){
                    try{
                        ((JButton)e.getSource()).doClick();
                    }catch(Exception ex){
                        
                    }
                }
            }
            
        };
    
    public Utilities(){
        
    }
}
