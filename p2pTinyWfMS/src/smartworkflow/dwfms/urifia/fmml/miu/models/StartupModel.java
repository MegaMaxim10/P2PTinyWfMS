/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartworkflow.dwfms.urifia.fmml.miu.models;

import javax.swing.JOptionPane;

import smartworkflow.dwfms.urifia.fmml.miu.models.util.ModelModel;
import smartworkflow.dwfms.urifia.fmml.miu.util.ConfigurationManager;

/**
 *
 * @author Ndadji Maxime
 */
public class StartupModel extends ModelModel{

    public StartupModel(ConfigurationManager config) {
        super(config);
    }

    public StartupModel() {
    }
    
    public void startupLoading(){
        try{
            if(config == null)
                config = new ConfigurationManager();
            notifyStartLoaded(ModelModel.getConfig().getLangValue("checking_workspace"));
            if(config.isAskWorkspaceActivated() || !config.isWorkspaceValid())
                setWorkspace();
            notifyStartLoaded(ModelModel.getConfig().getLangValue("checked_workspace"));
            try{
                Thread.sleep(500);
            }catch(Exception e){
                
            }
            /*notifyStartLoaded(ModelModel.getConfig().getLangValue("connecting_server"));
            //setServer();
            notifyStartLoaded(ModelModel.getConfig().getLangValue("connected_server")+" "+ModelModel.getConfig().getServerInfos().get("host"));
            try{
                Thread.sleep(500);
            }catch(Exception e){
                
            }
            notifyStartLoaded(ModelModel.getConfig().getLangValue("authenticating"));
            authentify();
            notifyStartLoaded(ModelModel.getConfig().getLangValue("authenticated"));
            try{
                Thread.sleep(500);
            }catch(Exception e){
                
            }*/
            notifyStartLoaded(ModelModel.getConfig().getLangValue("loading_preferences"));
            if(config.isAskThemActivated())
                setTheme();
            notifyStartLoaded(ModelModel.getConfig().getLangValue("loaded_preferences"));
            try{
                Thread.sleep(500);
            }catch(Exception e){
                
            }
            notifyStartUtilities();
        }
        catch(Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "There was a problem when loading the application."
                    + "\nJust contact our teams for support.\n", "Stopping the"
                    + " application", 
                    JOptionPane.ERROR_MESSAGE); 
            System.exit(0); 
        }
    }
}
