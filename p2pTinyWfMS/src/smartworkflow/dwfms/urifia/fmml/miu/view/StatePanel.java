/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartworkflow.dwfms.urifia.fmml.miu.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JToolBar;

import smartworkflow.dwfms.urifia.fmml.miu.controllers.util.ControllerModel;
import smartworkflow.dwfms.urifia.fmml.miu.util.observer.adapters.ObserverAdapter;
import smartworkflow.dwfms.urifia.fmml.miu.view.util.PanelModel;

/**
 *
 * @author Ndadji Maxime
 */
public class StatePanel extends PanelModel{
	private static final long serialVersionUID = 1L;
	
    private Updater updater;
    private ActionListener listener;
    private JLabel task, loading;
    private ArrayList<String> tasks;
    private ImageIcon icon;
    private JToolBar toolBar;

    public StatePanel(ControllerModel controller) {
        super(controller);
        
        tasks = new ArrayList<String>();
        initComponents();
        updater = new Updater();
        addUpdater(updater);
    }
    
    @Override
    protected final void initComponents() {
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
        
        setLayout(new BorderLayout());
        
        //setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));
        
        toolBar = new JToolBar(JToolBar.HORIZONTAL);
        toolBar.setPreferredSize(new Dimension(20, 20));
        toolBar.setFloatable(false);
        toolBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY.brighter()));
        
        task = new JLabel("");
        task.setFont(new Font("Cambria", Font.PLAIN, 13));
        task.setPreferredSize(new Dimension(550, 15));
        task.setHorizontalAlignment(JLabel.RIGHT);
        toolBar.add(task);
        
        toolBar.addSeparator(new Dimension(9, 9));
        
        loading = new JLabel("");
        loading.setFont(new Font("Cambria", Font.PLAIN, 13));
        loading.setPreferredSize(new Dimension(10, 10));
        icon = new ImageIcon(getClass().getResource("/smartworkflow/dwfms/urifia/fmml/miu/resources/images/loading.gif"));
        toolBar.add(loading);
        
        add(toolBar);
    }
    
    public void addTask(String t){
        tasks.add(0, t);
        updateTasks();
    }
    
    public void removeTask(String t){
        tasks.remove(t);
        updateTasks();
    }
    
    public void updateTasks(){
        int size = tasks.size();
        if(size > 0){
            task.setText(tasks.get(0)+(size > 1 ? " (+"+ (size - 1) + " " + config.getLangValue("more") + ")" : ""));
            loading.setIcon(icon);
        }else{
            loading.setIcon(null);
            task.setText("");
        }
        validate();
        updateUI();
    }
    
    public class Updater extends ObserverAdapter{
        @Override
        public void updateTaskStarted(String t){
            addTask(t);
        }
        
        @Override
        public void updateTaskEnded(String t){
            removeTask(t);
        }
    }
}
