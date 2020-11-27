package smartworkflow.dwfms.urifia.fmml.miu.view.util;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import smartworkflow.dwfms.urifia.fmml.miu.util.APPLConstants;
import smartworkflow.dwfms.urifia.fmml.miu.util.ConfigurationManager;
import smartworkflow.dwfms.urifia.fmml.miu.util.Theme;
import smartworkflow.dwfms.urifia.fmml.miu.util.exceptions.ApplException;

/**
 *
 * @author Ndadji Maxime
 */
public abstract class ChatDialogModel extends JDialog{
	private static final long serialVersionUID = 1L;
	public static int   OK_BUTTON = 0,
                        OK_CANCEL_BUTTON = 1,
                        CANCEL_BUTTON = 2;
    public static String    OK_KEY = "ok",
                            CANCEL_KEY = "cancel",
                            NEXT_KEY = "next",
                            PREV_KEY = "prev";
    protected JPanel mainPanel, menuPanel, stepPanel, titlePanel, statePanel, contentPanel;
    protected ActionListener listener;
    protected static ConfigurationManager config;
    protected ChatButton okButton, cancelButton, nextButton, prevButton;
    protected int commandType = 1;
    protected boolean showNextPrevKeys = false, focusOnAction = true, stateLabelToggleStepsEnabled = true;
    protected JLabel stepTitle, stateLabel;
    protected ArrayList<JLabel> steps;
    protected Font stepFont = new Font("Cambria", Font.PLAIN, 13),
                   stepActiveFont = new Font("Cambria", Font.BOLD, 13);
    protected int step;
    protected MouseListener mouseListener;
    protected KeyListener keyListener;
    
    public ChatDialogModel (ConfigurationManager config, Frame owner, String title, boolean modal, boolean showNextPrevKeys){
        this(OK_CANCEL_BUTTON, config, owner, title, modal, showNextPrevKeys);
    }
    
    public ChatDialogModel (int commandType, ConfigurationManager config, Frame owner, String title, boolean modal, boolean showNextPrevKeys){
        super(owner, title, modal);
        this.setAlwaysOnTop(false);
        ImageIcon icon = new ImageIcon(getClass().getResource(APPLConstants.RESOURCES_FOLDER + "images/logo.png"));
        this.setIconImage(icon.getImage());
        this.commandType = commandType;
        this.showNextPrevKeys = showNextPrevKeys;
        steps = new ArrayList<JLabel>();
        ChatDialogModel.config = config;
        initPanel();
    }
    
    public ChatDialogModel (ConfigurationManager config, Frame owner, String title, boolean modal, boolean showNextPrevKeys, boolean focusOnAction){
        this(OK_CANCEL_BUTTON, config, owner, title, modal, showNextPrevKeys, focusOnAction);
    }
    
    public ChatDialogModel (int commandType, ConfigurationManager config, Frame owner, String title, boolean modal, boolean showNextPrevKeys, boolean focusOnAction){
        super(owner, title, modal);
        this.setAlwaysOnTop(false);
        ImageIcon icon = new ImageIcon(getClass().getResource(APPLConstants.RESOURCES_FOLDER + "images/logo.png"));
        this.setIconImage(icon.getImage());
        this.commandType = commandType;
        this.showNextPrevKeys = showNextPrevKeys;
        this.focusOnAction = focusOnAction;
        steps = new ArrayList<JLabel>();
        ChatDialogModel.config = config;
        initPanel();
    }
    
    public ChatDialogModel (ConfigurationManager config, Dialog owner, String title, boolean modal, boolean showNextPrevKeys){
        this(OK_CANCEL_BUTTON, config, owner, title, modal, showNextPrevKeys);
    }
    
    public ChatDialogModel (int commandType, ConfigurationManager config, Dialog owner, String title, boolean modal, boolean showNextPrevKeys){
        super(owner, title, modal);
        this.setAlwaysOnTop(true);
        ImageIcon icon = new ImageIcon(getClass().getResource(APPLConstants.RESOURCES_FOLDER + "images/logo.png"));
        this.setIconImage(icon.getImage());
        this.commandType = commandType;
        this.showNextPrevKeys = showNextPrevKeys;
        steps = new ArrayList<JLabel>();
        ChatDialogModel.config = config;
        initPanel();
    }
    
    public ChatDialogModel (ConfigurationManager config, Dialog owner, String title, boolean modal, boolean showNextPrevKeys, boolean focusOnAction){
        this(OK_CANCEL_BUTTON, config, owner, title, modal, showNextPrevKeys, focusOnAction);
    }
    
    public ChatDialogModel (int commandType, ConfigurationManager config, Dialog owner, String title, boolean modal, boolean showNextPrevKeys, boolean focusOnAction){
        super(owner, title, modal);
        this.setAlwaysOnTop(true);
        ImageIcon icon = new ImageIcon(getClass().getResource(APPLConstants.RESOURCES_FOLDER + "images/logo.png"));
        this.setIconImage(icon.getImage());
        this.commandType = commandType;
        this.showNextPrevKeys = showNextPrevKeys;
        this.focusOnAction = focusOnAction;
        steps = new ArrayList<JLabel>();
        ChatDialogModel.config = config;
        initPanel();
    }
    
    private void initPanel(){
        menuPanel = new JPanel();
        menuPanel.setPreferredSize(new Dimension(100, 45));
        JLabel lab = new JLabel();
        lab.setPreferredSize(new Dimension(50, 30));
        menuPanel.add(lab);
        if(showNextPrevKeys){
            prevButton = new ChatButton(config.getLangValue(PREV_KEY), config.getTheme());
            prevButton.setActionCommand(PREV_KEY);
            prevButton.setPreferredSize(new Dimension((prevButton.getText().length()) * 12, 30));
            prevButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            menuPanel.add(prevButton);
            
            nextButton = new ChatButton(config.getLangValue(NEXT_KEY), config.getTheme());
            nextButton.setActionCommand(NEXT_KEY);
            nextButton.setPreferredSize(new Dimension((nextButton.getText().length()) * 12, 30));
            nextButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            menuPanel.add(nextButton);
        }
        lab = new JLabel();
        lab.setPreferredSize(new Dimension(15, 30));
        menuPanel.add(lab);
        if(commandType != CANCEL_BUTTON){
            okButton = new ChatButton(config.getLangValue(OK_KEY), config.getTheme());
            okButton.setActionCommand(OK_KEY);
            okButton.setPreferredSize(new Dimension((okButton.getText().length()) * 12, 30));
            okButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            menuPanel.add(okButton);
        }
        if(commandType != OK_BUTTON){
            cancelButton = new ChatButton(config.getLangValue(CANCEL_KEY), config.getTheme());
            cancelButton.setActionCommand(CANCEL_KEY);
            cancelButton.setPreferredSize(new Dimension((cancelButton.getText().length()) * 12, 30));
            cancelButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            menuPanel.add(cancelButton);
        }
        
        menuPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, config.getTheme().getBgColor()));
        
        stepPanel = new StyledPanel(StyledPanel.SIMPLE_CHRISTMAS, config);
        stepPanel.setPreferredSize(new Dimension(200, 500));
        stepPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY.brighter()));
        
        JLabel label = new JLabel(config.getLangValue("steps"));
        label.setPreferredSize(new Dimension(190, 25));
        label.setFont(config.getTheme().getSecondTitleFont());
        label.setForeground(Color.BLACK);
        label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, config.getTheme().getBgColor()));
        
        stepPanel.add(label);
        
        this.add(stepPanel, BorderLayout.WEST);
        this.add(menuPanel, BorderLayout.SOUTH);
        
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        
        titlePanel = new JPanel();
        titlePanel.setPreferredSize(new Dimension(190, 35));
        
        stepTitle = new JLabel();
        stepTitle.setPreferredSize(new Dimension(190, 25));
        stepTitle.setFont(config.getTheme().getSecondTitleFont());
        stepTitle.setForeground(Color.BLACK);
        stepTitle.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, config.getTheme().getBgColor()));
        
        titlePanel.add(stepTitle);
        
        contentPanel.add(titlePanel, BorderLayout.NORTH);
        
        mainPanel = new JPanel();
        mainPanel.setLayout(new CardLayout());
        contentPanel.add(mainPanel);
        
        statePanel = new JPanel();
        statePanel.setPreferredSize(new Dimension(190, 35));
        
        stateLabel = new JLabel();
        stateLabel.setPreferredSize(new Dimension(190, 25));
        stateLabel.setFont(config.getTheme().getSecondTitleFont());
        
        statePanel.add(stateLabel);
        
        contentPanel.add(statePanel, BorderLayout.SOUTH);
        
        this.add(contentPanel);
        
        mouseListener = new MouseAdapter(){

            @Override
            public void mouseReleased(MouseEvent e) {
                try {
                	if(stateLabelToggleStepsEnabled)
                		setStep(steps.indexOf(e.getSource()) + 1);
                } catch (ApplException ex) {
                    
                }
            }
            
        };
        
        keyListener = new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == 10){
                    if((e.getSource() instanceof JButton)){
                        if(((JButton)e.getSource()).isEnabled())
                            ((JButton)e.getSource()).doClick();
                        return;
                    }
                    if((e.getSource() instanceof ChatButton)){
                        if(((ChatButton)e.getSource()).isEnabled())
                            ((ChatButton)e.getSource()).doClick();
                        return;
                    }
                    if((e.getSource() instanceof JTextField) || (e.getSource() instanceof JPasswordField)){
                        if(showNextPrevKeys && nextButton.isEnabled()){
                            nextButton.doClick();
                            return;
                        }
                        if(okButton.isEnabled())
                            okButton.doClick();
                        return;
                    }
                }
            }
            
        };
        if(focusOnAction){
            this.addWindowFocusListener(new WindowAdapter() {
                @Override
                public void windowGainedFocus(WindowEvent e) {
                    if(commandType != CANCEL_BUTTON && (!showNextPrevKeys || !nextButton.isEnabled())){
                        okButton.requestFocusInWindow();
                        return;
                    }
                    if(showNextPrevKeys && nextButton.isEnabled())
                        nextButton.requestFocusInWindow();
                }
            });
        }
    }
    
    public void showDialog(){
        this.setVisible(true);
    }
    
    public void closeDialog(){
        this.setVisible(false);
    }
    
    public void addStep(JLabel step){
        step.setFont(stepFont);
        step.setPreferredSize(new Dimension(170, 15));
        step.setToolTipText("<html><body><font family=\""+config.getTheme().getToolTipFont().getFamily()+"\" size=\""+config.getTheme().getToolTipSize()+"\" color=\""+Theme.extractRGB(config.getTheme().getToolTipColor())+"\">"
                +step.getText()+"</font></body></html>");
        steps.add(step);
        JLabel lab = new JLabel("-"+(steps.indexOf(step) + 1)+"-");
        lab.setPreferredSize(new Dimension(15, 15));
        lab.setFont(stepFont);
        step.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        stepPanel.add(lab);
        step.addMouseListener(mouseListener);
        stepPanel.add(step);
    }
    
    public void setStep(int step) throws ApplException{
        if(step > 0 && step <= steps.size()){
            stepTitle.setText(steps.get(step - 1).getText());
            steps.get(step - 1).setFont(stepActiveFont);
            this.step = step;
            stateLabel.setText("");
            stateLabel.setIcon(null);
            if(prevButton != null){
                nextButton.setEnabled(true);
                prevButton.setEnabled(true);
                if(step == 1)
                    prevButton.setEnabled(false);
                if(step == steps.size())
                    nextButton.setEnabled(false);
            }
            try{
                ((CardLayout)mainPanel.getLayout()).show(mainPanel, ""+step);
            }catch(Exception ex){
                throw new ApplException(ex.getMessage(), ex);
            }
            for(JLabel lab : steps){
                if(!lab.equals(steps.get(step - 1)))
                    lab.setFont(stepFont);
            }
        }
    }
    
    public boolean isStateLabelToggleStepsEnabled() {
        return stateLabelToggleStepsEnabled;
    }

    public void setStateLabelToggleStepsEnabled(boolean stateLabelToggleStepsEnabled) {
        this.stateLabelToggleStepsEnabled = stateLabelToggleStepsEnabled;
    }
}
