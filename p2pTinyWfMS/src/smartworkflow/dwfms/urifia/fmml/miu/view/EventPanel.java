package smartworkflow.dwfms.urifia.fmml.miu.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import smartworkflow.dwfms.urifia.fmml.miu.controllers.util.ControllerModel;
import smartworkflow.dwfms.urifia.fmml.miu.util.observer.adapters.ObserverAdapter;
import smartworkflow.dwfms.urifia.fmml.miu.view.util.PanelModel;

public class EventPanel extends PanelModel{
	private static final long serialVersionUID = 1L;
	
    private Updater updater;
    private ActionListener listener;
    private JToolBar toolBar;
    private JPanel mainPanel;

    public EventPanel(ControllerModel controller) {
        super(controller);
        
        initComponents();
        updater = new Updater();
        addUpdater(updater);
    }
    
    @Override
    protected final void initComponents() {
        setLayout(new BorderLayout());
        
        this.initListeners();
        
        this.initPanels();
        
        this.initMenus();
        
        this.initToolBar();
    }
    
    private void initToolBar() {
    	toolBar = new JToolBar(JToolBar.HORIZONTAL);
        toolBar.setPreferredSize(new Dimension(20, 20));
        toolBar.setFloatable(false);
        toolBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY.brighter()));
        
        add(toolBar, BorderLayout.NORTH);
	}

	private void initMenus() {
		
	}

	private void initListeners(){
    	listener = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                if(command.equalsIgnoreCase("clear")){
                    
                    return;
                }
                if(command.equalsIgnoreCase("print")){
                    return;
                }
                if(command.equalsIgnoreCase("add")){
                    
                    return;
                }
            }
        };
    }
    
    private void initPanels(){
    	mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);
    }
    
    public class Updater extends ObserverAdapter{
        
    }
}
