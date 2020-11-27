/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartworkflow.dwfms.urifia.fmml.miu.models.util;

import java.awt.Desktop;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import smartworkflow.dwfms.urifia.fmml.miu.util.ConfigurationManager;
import smartworkflow.dwfms.urifia.fmml.miu.util.exceptions.ApplException;
import smartworkflow.dwfms.urifia.fmml.miu.util.observer.adapters.ObservableAdapter;
import smartworkflow.dwfms.urifia.fmml.miu.view.AuthentificationDialog;
import smartworkflow.dwfms.urifia.fmml.miu.view.LoadingDialog;
import smartworkflow.dwfms.urifia.fmml.miu.view.SettingGUIDialog;
import smartworkflow.dwfms.urifia.fmml.miu.view.WorkspaceDialog;
import smartworkflow.dwfms.urifia.fmml.miu.view.util.editor.AvatarDialog;
import smartworkflow.dwfms.urifia.fmml.miu.view.util.editor.SmilieDialog;

/**
 *
 * @author Ndadji Maxime
 */
public abstract class ModelModel extends ObservableAdapter{
    public static ConfigurationManager config = null;
    public static boolean display = true;
    private static LoadingDialog loadingDialog;
    protected static Desktop desktop = null;
    
    static{
        try {
            config = new ConfigurationManager();
        } catch (ApplException ex) {
            
        }
    }
    
    public ModelModel(ConfigurationManager config) {
        ModelModel.config = config;
        if(Desktop.isDesktopSupported())
            desktop = Desktop.getDesktop();
    }

    public ModelModel() {
        if(Desktop.isDesktopSupported())
            desktop = Desktop.getDesktop();
    }
    
    public static boolean authentify(){
        AuthentificationDialog dialog = new AuthentificationDialog(config, true, null);
        dialog.showDialog();
        return dialog.isConnected();
    }
    
    public static String getThisAdress() throws UnknownHostException{
        return InetAddress.getLocalHost().getHostAddress();
    }
    
    public static boolean setWorkspace(){
        WorkspaceDialog dialog = new WorkspaceDialog(config, true, null);
        dialog.showDialog();
        return true;
    }
    
    public static boolean setTheme(){
        SettingGUIDialog dialog = new SettingGUIDialog(config, true, null);
        dialog.showDialog();
        return dialog.changed;
    }

    public static ConfigurationManager getConfig() {
        return config;
    }
    
    public static void displayErrorDialog(String title, String message){
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE); 
    }
    
    public static void displayMessageDialog(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE); 
    }
    
    public static void addProcess(String lab){
        if(loadingDialog != null)
            loadingDialog.addProcess(lab);
    }

    public static void removeProcess(String lab){
        if(loadingDialog != null)
            loadingDialog.removeProcess(lab);
    }
    
    public static String setAvatar(boolean multiple) {
        AvatarDialog dialog = new AvatarDialog(config, true, null, multiple);
        dialog.showDialog();
        return dialog.getAvatar();
    }
    
    public static String setSmilie(boolean multiple) {
        SmilieDialog dialog = new SmilieDialog(config, true, null, multiple);
        dialog.showDialog();
        return dialog.getSmilie();
    }
    
    public static String parseMessage(String message) {
        message = message.replace("&quot;", "\"");
        message = message.replace("[br]", "<br />");
        message = message.replace("[darr]", "&darr;");
        message = message.replace("[bold]", "<b>");
        message = message.replace("[/bold]", "</b>");
        message = message.replace("[italic]", "<i>");
        message = message.replace("[/italic]", "</i>");
        message = message.replace("[underline]", "<u>");
        message = message.replace("[/underline]", "</u>");
        message = message.replace("[quote]", "<blockquote>");
        message = message.replace("[/quote]", "</blockquote>");
        message = message.replace("[list]", "<ul>");
        message = message.replace("[list type=\"square\"]", "<ul type=\"square\">");
        message = message.replace("[list type=\"circle\"]", "<ul type=\"circle\">");
        message = message.replace("[/list]", "</ul>");
        message = message.replace("[olist]", "<ol>");
        message = message.replace("[/olist]", "</ol>");
        message = message.replace("[sup]", "<sup>");
        message = message.replace("[/sup]", "</sup>");
        message = message.replace("[/sub]", "</sub>");
        message = message.replace("[strike]", "<strike>");
        message = message.replace("[/strike]", "</strike>");
        message = message.replace("[item]", "<li>");
        message = message.replace("[/item]", "</li>");
        message = message.replace("[color value=\"blue\"]", "<font color=\"blue\">");
        message = message.replace("[color value=\"red\"]", "<font color=\"red\">");
        message = message.replace("[color value=\"gray\"]", "<font color=\"gray\">");
        message = message.replace("[color value=\"purple\"]", "<font color=\"purple\">");
        message = message.replace("[color value=\"green\"]", "<font color=\"green\">");
        message = message.replace("[color value=\"yellow\"]", "<font color=\"yellow\">");
        message = message.replace("[color value=\"white\"]", "<font color=\"white\">");
        message = message.replace("[/color]", "</font>");
        message = message.replace("[bgcolor value=\"blue\"]", "<font bgcolor=\"blue\">");
        message = message.replace("[bgcolor value=\"red\"]", "<font bgcolor=\"red\">");
        message = message.replace("[bgcolor value=\"gray\"]", "<font bgcolor=\"gray\">");
        message = message.replace("[bgcolor value=\"purple\"]", "<font bgcolor=\"purple\">");
        message = message.replace("[bgcolor value=\"green\"]", "<font bgcolor=\"green\">");
        message = message.replace("[bgcolor value=\"yellow\"]", "<font bgcolor=\"yellow\">");
        message = message.replace("[/bgcolor]", "</font>");
        message = message.replace("[align value=\"left\"]", "<p align=\"left\">");
        message = message.replace("[align value=\"right\"]", "<p align=\"right\">");
        message = message.replace("[align value=\"center\"]", "<p align=\"center\">");
        message = message.replace("[/align]", "</p>");
        
        Pattern pattern = Pattern.compile("\\[ava-[a-zA-Z0-9 _-]{1,}\\.[a-zA-Z]{3,}\\]");
        Matcher matcher = pattern.matcher(message);
        while(matcher.find()){
            try {
                String group = matcher.group();
                message = message.replace(group, "<img src=\"file:///"+(new File("ressources/images/avatars/"+group.substring(5, group.length() - 1))).getAbsolutePath().replace("%20", " ") +"\" />");
            } catch (Exception ex) {
                
            }
        }
        
        pattern = Pattern.compile("\\[smi-[a-zA-Z0-9 _-]{1,}\\.[a-zA-Z]{3,}\\]");
        matcher = pattern.matcher(message);
        while(matcher.find()){
            try {
                String group = matcher.group();
                message = message.replace(group, "<img src=\"file:///"+(new File("ressources/images/smilies/"+group.substring(5, group.length() - 1))).getAbsolutePath().replace("%20", " ") +"\" />");
            } catch (Exception ex) {
                
            }
        }
        return message;
    }
}
