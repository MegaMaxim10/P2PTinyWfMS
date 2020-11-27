package smartworkflow.dwfms.urifia.fmml.miu.view.util;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import smartworkflow.dwfms.urifia.fmml.miu.controllers.util.ControllerModel;
import smartworkflow.dwfms.urifia.fmml.miu.util.ConfigurationManager;
import smartworkflow.dwfms.urifia.fmml.miu.util.exceptions.ElementNotFoundException;
import smartworkflow.dwfms.urifia.fmml.miu.util.observer.Observer;
/**
 *
 * @author Ndadji Maxime
 */
public abstract class PanelModel extends JPanel{
	private static final long serialVersionUID = 1L;
	protected List<Observer> updaterList;
    protected ControllerModel controller;
    public static ConfigurationManager config;
    
    public PanelModel(){
        updaterList = new ArrayList<Observer>();
        //StyledPanel p = new StyledPanel(config);
    }
    
    public PanelModel(ControllerModel controller){
        updaterList = new ArrayList<Observer>();
        this.controller = controller;
    }
    
    public PanelModel(List<Observer> observerList){
        this.updaterList = observerList;
    }
    
    public PanelModel(List<Observer> observerList, ControllerModel controller){
        this.updaterList = observerList;
        this.controller = controller;
    }
    
    public void addUpdater(Observer observer) {
        if(!this.updaterList.contains(observer)){
            this.updaterList.add(observer);
            this.controller.getModel().addObserver(observer);
        }
    }

    public void removeUpdater(Observer observer) throws ElementNotFoundException {
        try{
            this.updaterList.remove(observer);
            this.controller.getModel().removeObserver(observer);
        }
        catch(Exception e){
            throw new ElementNotFoundException(e.getMessage());
        }
    }
    
    protected abstract void initComponents();

    public void setController(ControllerModel controller) {
    	this.controller = controller;
    }
}
