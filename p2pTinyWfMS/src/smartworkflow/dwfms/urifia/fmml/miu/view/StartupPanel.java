/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartworkflow.dwfms.urifia.fmml.miu.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import org.joda.time.DateTime;

import smartworkflow.dwfms.urifia.fmml.miu.util.APPLConstants;

/**
 *
 * @author Ndadji Maxime
 */
public class StartupPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private String message = "";
    
    public StartupPanel(){
        this.setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        ImageIcon icon = new ImageIcon(getClass().getResource(APPLConstants.RESOURCES_FOLDER+"images/splash.png"));
        g.drawImage(icon.getImage(), 0, 0, 550, 330, this);
        
        g.setColor(Color.MAGENTA.darker());
        g.setFont(new Font("Times", Font.BOLD, 13));
        g.drawString(message+"...", 30, 105);
        
        DateTime d = new DateTime(new Date());
        if((d.getDayOfMonth() >= 18 && d.getMonthOfYear() == 12) || (d.getDayOfMonth() <= 10 && d.getMonthOfYear() == 1)){
            icon = new ImageIcon(getClass().getResource(APPLConstants.RESOURCES_FOLDER+"images/christmas.gif"));
            g.drawImage(icon.getImage(), getWidth() - 50, 93, 25, 25, this);
        }else{
            icon = new ImageIcon(getClass().getResource(APPLConstants.RESOURCES_FOLDER+"images/camer.gif"));
            g.drawImage(icon.getImage(), getWidth() - 50, 93, 25, 14, this);
        }
    }
    
    public void check (String message){
        this.message = message;
    }
}
