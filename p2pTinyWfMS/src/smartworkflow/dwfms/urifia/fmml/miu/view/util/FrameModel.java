package smartworkflow.dwfms.urifia.fmml.miu.view.util;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import smartworkflow.dwfms.urifia.fmml.miu.controllers.util.ControllerModel;
import smartworkflow.dwfms.urifia.fmml.miu.util.ConfigurationManager;
import smartworkflow.dwfms.urifia.fmml.miu.util.exceptions.ElementNotFoundException;
import smartworkflow.dwfms.urifia.fmml.miu.util.observer.Observer;

/**
 *
 * @author Ndadji Maxime
 */
public abstract class FrameModel extends JFrame{
	private static final long serialVersionUID = 1L;
	protected List<Observer> updaterList;
    protected ControllerModel controller;
    protected static ConfigurationManager config;
    
    public FrameModel(){
        updaterList = new ArrayList<Observer>();
    }
    
    public FrameModel(ControllerModel controller){
        updaterList = new ArrayList<Observer>();
        this.controller = controller;
    }
    
    public FrameModel(List<Observer> updaterList){
        this.updaterList = updaterList;
    }
    
    public FrameModel(List<Observer> updaterList, ControllerModel controller){
        this.updaterList = updaterList;
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
