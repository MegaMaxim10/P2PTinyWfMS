/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartworkflow.dwfms.urifia.fmml.miu.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import smartworkflow.dwfms.urifia.fmml.miu.controllers.IHMController;
import smartworkflow.dwfms.urifia.fmml.miu.util.ConfigurationManager;
import smartworkflow.dwfms.urifia.fmml.miu.util.exceptions.ApplException;
import smartworkflow.dwfms.urifia.fmml.miu.view.util.ChatDialogModel;

/**
 *
 * @author Ndadji Maxime
 */
public class LoadingDialog extends ChatDialogModel{
	private static final long serialVersionUID = 1L;
	
	private JLabel label;
    private ArrayList<JLabel> labels, inBack;
    private JPanel panel;
    private JScrollPane scroll;
    private boolean back = false;
    private IHMController ihmController;
    
    
    public LoadingDialog (ConfigurationManager config, boolean modal, JFrame parent, IHMController ihmController){
        super(ChatDialogModel.CANCEL_BUTTON, config, parent, config.getLangValue("processes_manager"), modal, false);
        this.ihmController = ihmController;
        this.setSize(340, 280);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setAlwaysOnTop(true);
        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                runInBack();
            }
            
        });
        this.initComponents();
    }

    private void initComponents() {
        listener = new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                if(command.equalsIgnoreCase(CANCEL_KEY)){
                    runInBack();
                    return;
                }
                
                if(command.equalsIgnoreCase(OK_KEY)){
                    runInBack();
                }
            }
            
        };
        
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(315, 100));
        labels = new ArrayList<JLabel>();
        inBack = new ArrayList<JLabel>();
        
        label = new JLabel(config.getLangValue("running_processes"));
        addStep(label);
        
        stepTitle.setPreferredSize(new Dimension(300, 25));
        stateLabel.setPreferredSize(new Dimension(300, 25));
        stateLabel.setForeground(Color.red.darker());
        
        scroll = new JScrollPane(panel);
        scroll.setBorder(null);
        mainPanel.add(scroll, "1");
        
        cancelButton.addActionListener(listener);
        cancelButton.setText(config.getLangValue("run_in_back"));
        cancelButton.setPreferredSize(new Dimension((cancelButton.getText().length()) * 11, 30));
        
        this.remove(this.stepPanel);
        try {
            setStep(1);
        } catch (ApplException ex) {
            
        }
    }

    public void addProcess(String proc) {
        synchronized(this){
            JLabel lab = new JLabel(proc);
            lab.setPreferredSize(new Dimension(300, 25));
            lab.setFont(config.getTheme().getMenuFont());
            lab.setIcon(new ImageIcon(getClass().getResource("/miu/perfumery/manager/ressources/images/loader.gif")));
            panel.setPreferredSize(new Dimension(panel.getPreferredSize().width, panel.getPreferredSize().height + 30));
            panel.add(lab);
            labels.add(lab);
            panel.validate();
            panel.repaint();
            mainPanel.validate();
            mainPanel.repaint();
            back = false;
            if(!isVisible())
                showDialog();
        }
    }

    public void removeProcess(String proc) {
        synchronized(this){
            for(JLabel lab : labels){
                if(lab.getText().equals(proc)){
                    labels.remove(lab);
                    panel.remove(lab);
                    panel.setPreferredSize(new Dimension(panel.getPreferredSize().width, panel.getPreferredSize().height - 30));
                    panel.validate();
                    panel.repaint();
                    mainPanel.validate();
                    mainPanel.repaint();
                    if(labels.isEmpty() && isVisible())
                        closeDialog();
                    return;
                }
            }
            for(JLabel lab : inBack){
                if(lab.getText().equals(proc)){
                    inBack.remove(lab);
                    ihmController.stopTask(proc);
                    if(inBack.isEmpty())
                        stateLabel.setText("");
                    else
                        stateLabel.setText(inBack.size()+" "+config.getLangValue("task_in_back"));
                    mainPanel.validate();
                    mainPanel.repaint();
                    return;
                }
            }
        }
    }
    
    public void runInBack(){
        if(!back){
            back = true;
            inBack.addAll(labels);
            for(JLabel lab : labels){
                panel.remove(lab);
                panel.setPreferredSize(new Dimension(panel.getPreferredSize().width, panel.getPreferredSize().height - 30));
                ihmController.startTask(lab.getText());
            }
            labels.removeAll(inBack);
            closeDialog();
            stateLabel.setText(inBack.size()+" "+config.getLangValue("task_in_back"));
            panel.validate();
            panel.repaint();
            mainPanel.validate();
            mainPanel.repaint();
        }
    }

    public boolean haveProcesses() {
        return (!labels.isEmpty() || !inBack.isEmpty());
    }
}
