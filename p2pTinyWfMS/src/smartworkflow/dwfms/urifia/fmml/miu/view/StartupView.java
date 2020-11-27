/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartworkflow.dwfms.urifia.fmml.miu.view;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import smartworkflow.dwfms.urifia.fmml.miu.controllers.IHMController;
import smartworkflow.dwfms.urifia.fmml.miu.controllers.StartupController;
import smartworkflow.dwfms.urifia.fmml.miu.models.util.ModelModel;
import smartworkflow.dwfms.urifia.fmml.miu.util.APPLConstants;
import smartworkflow.dwfms.urifia.fmml.miu.util.observer.adapters.ObserverAdapter;
import smartworkflow.dwfms.urifia.fmml.miu.view.util.FrameModel;

/**
 *
 * @author Ndadji Maxime
 */
public class StartupView extends FrameModel{
	private static final long serialVersionUID = 1L;
	
    private StartupPanel mainPanel;
    private Updater updater;
    
    public StartupView(IHMController controller, StartupController startupController) {
        super(controller);
        this.setTitle(ModelModel.getConfig() != null ? ModelModel.getConfig().getLangValue("startup_title") : "Starting P2PTinyCE");
        this.setUndecorated(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(550, 330);
        this.setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon(getClass().getResource(APPLConstants.RESOURCES_FOLDER+"images/logo.png"));
        this.setIconImage(icon.getImage());
        updater = new Updater();
        startupController.getModel().addObserver(updater);
        
        this.initComponents();
        
        this.setVisible(true);
        
        startupController.startupLoading();
    }
    
    @Override
    protected final void initComponents() {
        mainPanel = new StartupPanel();
        this.setContentPane(mainPanel);
    }
    
    @SuppressWarnings({ "unused" })
	private void constructMainView() {
        MainView view = new MainView((IHMController)controller);
        this.dispose();
    }
    
    public class Updater extends ObserverAdapter{

        @Override
        public void updateStartLoaded(String message) {
            mainPanel.check(message);
        }

        @Override
        public void updateStartUtilities() {
            mainPanel.check(ModelModel.getConfig().getLangValue("loading_component"));
            constructMainView();
        }
    }
}
