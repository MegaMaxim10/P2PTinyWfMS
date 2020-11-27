/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartworkflow.dwfms.urifia.fmml.miu.view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import smartworkflow.dwfms.urifia.fmml.miu.models.util.ModelModel;
import smartworkflow.dwfms.urifia.fmml.miu.util.APPLConstants;
import smartworkflow.dwfms.urifia.fmml.miu.util.ConfigurationManager;
import smartworkflow.dwfms.urifia.fmml.miu.util.Theme;
import smartworkflow.dwfms.urifia.fmml.miu.util.exceptions.ApplException;
import smartworkflow.dwfms.urifia.fmml.miu.util.observer.adapters.ObserverAdapter;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.Parsers;
import smartworkflow.dwfms.urifia.fmml.miu.view.util.ChatButton;
import smartworkflow.dwfms.urifia.fmml.miu.view.util.ChatDialogModel;
//import miu.lifa.tinyce.models.ModelModel;
//import miu.lifa.tinyce.remote.interfaces.CommunicatorInterface;
//import miu.lifa.tinyce.util.APPLConstants;
//import miu.lifa.tinyce.util.ConfigurationManager;
//import miu.lifa.tinyce.util.Theme;
//import miu.lifa.tinyce.util.editor.DistributedEditionWorkflow;
//import miu.lifa.tinyce.util.editor.Grammar;
//import miu.lifa.tinyce.util.editor.GrammarAndViews;
//import miu.lifa.tinyce.util.editor.Parsers;
//import miu.lifa.tinyce.util.editor.UserInfos;
//import miu.lifa.tinyce.util.exceptions.ApplException;
//import miu.lifa.tinyce.util.observer.ObserverAdapter;
//import miu.lifa.tinyce.views.util.ChatButton;
//import miu.lifa.tinyce.views.util.ChatDialogModel;
//import miu.lifa.tinyce.views.util.StyledPanel;
import smartworkflow.dwfms.urifia.fmml.miu.view.util.StyledPanel;


/**
 *
 * @author Ndadji Maxime
 */
public class WorkflowCreationDialog{ //extends ChatDialogModel{
	  /*private boolean create = false;
    private JLabel label;
    private JTextField ownerLogin, syncServer, templateName, workflowName;
    private ProductionsArea prods;
    private JComboBox mode, template, symbs;
    private JPasswordField ownerPassword;
    private JCheckBox syncHere, saveAsTemplate;
    private char echoChar;
    private JPanel panel, initPanel, grammarPanel, docPanel, viewsNamesPanel, authorsPanel, authsNamesPanel;
    private JScrollPane scroll;
    private DistributedEditionWorkflow<String, String> edition = new DistributedEditionWorkflow<String, String>();
    private boolean goodGrammar = false;
    private Grammar<String, String> prevGram;
    private JTextArea document;
    
    public WorkflowCreationDialog (ConfigurationManager config, boolean modal, JFrame parent){
        super(ChatDialogModel.OK_CANCEL_BUTTON, config, parent, config.getLangValue("workflow_creation"), modal, true);
        this.setAlwaysOnTop(false);
        this.setSize(740, 540);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setStateLabelToggleStepsEnabled(false);
        this.initComponents();
    }

    private void initComponents() {
        listener = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                if(command.equalsIgnoreCase(CANCEL_KEY)){
                    create = false;
                    closeDialog();
                    return;
                }
                
                if(command.equalsIgnoreCase("mode")){
                    if(mode.getSelectedIndex() == 1){
                        template.setEnabled(true);
                        template.setSelectedIndex(0);
                    }else{
                        template.setEnabled(false);
                        edition = new DistributedEditionWorkflow<String, String>();
                        updateWorkflow();
                    }
                    return;
                }
                
                if(command.equalsIgnoreCase("syncHere")){
                    try {
                        syncServer.setText(ModelModel.getThisAdress());
                    } catch (UnknownHostException ex) {
                        setError(config.getLangValue("unknown_host"));
                    }
                    syncServer.setEnabled(!syncHere.isSelected());
                }
                
                if(command.equalsIgnoreCase("save_as_template")){
                    templateName.setEnabled(saveAsTemplate.isSelected());
                }
                
                if(command.equalsIgnoreCase("setTemplate")){
                    DistributedEditionWorkflow<String, String> ed = config.getEditionFromTemplate((String)template.getSelectedItem());
                    if(ed != null){
                        edition = ed;
                        updateWorkflow();
                    }else{
                        setError(config.getLangValue("unavailable_template"));
                    }
                }
                
                if(command.equalsIgnoreCase("preview")){
                    if(document.getText() != null && !document.getText().trim().isEmpty()){
                        String doc = document.getText();
                        doc = doc.replace("\n", "");
                        doc = doc.replace("\t", "");
                        doc = doc.replace(" ", "");
                        String ast = null;
                        try{
                            ast = Parsers.derToAstWithoutBud(doc, edition.getGrammar());
                            if(ast != null){
                                PreviewAstDialog dialog = new PreviewAstDialog(config, true, null, ast, edition.getGrammar());
                                dialog.showDialog();
                            }else{
                                setError(config.getLangValue("invalid_document"));
                            }
                        }catch(Exception ex){
                            setError(config.getLangValue("invalid_document"));
                        }
                    }else{
                        setError(config.getLangValue("invalid_document"));
                    }
                }
                
                if(command.equalsIgnoreCase("create_view")){
                    controlProds();
                    if(goodGrammar){
                        CreateViewDialog dialog = new CreateViewDialog(config, true, null, edition.getGrammar().getSymbols());
                        dialog.showDialog();
                        if(dialog.isCreate()){
                            if(!edition.getViews().contains(dialog.getView())){
                                edition.getViews().add(dialog.getView());
                                updateWorkflow();
                            }
                        }
                    }
                }
                
                if(command.equalsIgnoreCase("create_coauthor")){
                    if(edition.getViews() != null && !edition.getViews().isEmpty()){
                        CreateAuthorDialog dialog = new CreateAuthorDialog(config, true, null, edition.getViews());
                        dialog.showDialog();
                        if(dialog.isCreate()){
                            UserInfos uInfos = dialog.getuInfos();
                            edition.getCoAuthors().put(uInfos.getLogin(), uInfos);
                            updateWorkflow();
                        }
                    }else{
                        setError(config.getLangValue("create_view_first"));
                    }
                }
                
                if(command.startsWith("delete-coauthor_")){
                    String key = command.split("_")[1];
                    edition.getCoAuthors().remove(key);
                    updateWorkflow();
                }
                
                if(command.startsWith("delete-view_")){
                    String key = command.split("_")[1];
                    int i, l;
                    for(i = 0, l = edition.getViews().size(); i < l; i++){
                        if(edition.getViews().get(i).get(0).equals(key))
                            break;
                    }
                    edition.getViews().remove(i);
                    updateWorkflow();
                }
                
                if(command.equalsIgnoreCase("show_pass")){
                    if(ownerPassword.getEchoChar() == '\0'){
                        ownerPassword.setEchoChar(echoChar);
                        ((ChatButton)e.getSource()).setIcon(new ImageIcon(getClass().getResource(APPLConstants.RESOURCES_FOLDER+"images/eye.png")));
                    }else{
                        ownerPassword.setEchoChar('\0');
                        ((ChatButton)e.getSource()).setIcon(new ImageIcon(getClass().getResource(APPLConstants.RESOURCES_FOLDER+"images/eye_closed.png")));
                    }
                    return;
                }
                
                if(command.equalsIgnoreCase(OK_KEY)){
                    if(isInitDocValid()){
                        String host = edition.getWorkflowServer();
                        if(host == null || host.trim().isEmpty()){
                            setError(config.getLangValue("invalid_sync_server"));
                            return;
                        }
                        CommunicatorInterface communicator = null;
                        try{
                            communicator = config.getCommunicator(host);
                            edition.setCurrentDocument(edition.getInitialDocument());
                            String key = ConfigurationManager.generate(16);
                            String initVect = ConfigurationManager.generate(16);
                            String cryptedKey = APPLConstants.encryptMessage(key);
                            String cryptedInitVect = APPLConstants.encryptMessage(initVect);
                            final GsonBuilder builder = new GsonBuilder();
                            final Gson gson = builder.create();
                            final String json = gson.toJson(edition);
                            String cryptedEdition = APPLConstants.encryptMessage(json, key, initVect);
                            String workflowID = communicator.createWorkflow(cryptedEdition, cryptedKey, cryptedInitVect);
                            edition.setWorkflowID(APPLConstants.decryptMessage(workflowID));
                            String nameSuffix = templateName.getText().trim();
                            nameSuffix = APPLConstants.getFileNameFromString(nameSuffix);
                            if(saveAsTemplate.isSelected())
                                config.saveEditionAsTemplate(edition, nameSuffix);
                            config.exportGrammarAndViews(new GrammarAndViews<String>(edition.getGrammar(), edition.getViews()), nameSuffix, true, GrammarAndViews.TGF, null);
                        }catch(Throwable ex){
                            setError(config.getLangValue("unavailable_sync_server"));
                            return;
                        }
                        create = true;
                        closeDialog();
                        return;
                    }
                }
                
                if(command.equalsIgnoreCase(NEXT_KEY)){
                    if(step == 1){
                        try{
                            if(isCreationModeValid()){
                                setStep(step + 1);
                                resetError();
                            }
                        } catch (ApplException ex) {
                            
                        }
                        return;
                    }
                    
                    if(step == 2){
                        try {
                            if(isGrammarValid()){
                                setStep(step + 1);
                                resetError();
                            }
                        } catch (ApplException ex) {
                            
                        }
                        return;
                    }
                    
                    if(step == 3 && isUsersValid()){
                        try {
                            setStep(step + 1);
                            resetError();
                            okButton.setEnabled(true);
                        } catch (ApplException ex) {

                        }
                        return;
                    }
                }
                
                if(command.equalsIgnoreCase(PREV_KEY)){
                    try {
                        setStep(step - 1);
                        if(step != 4)
                            okButton.setEnabled(false);
                    } catch (ApplException ex) {
                        
                    }
                }
            }
        };
        
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(520, 500));
        panel.setLayout(new BorderLayout());
        
        initPanel = new JPanel();
        initPanel.setPreferredSize(new Dimension(510, 200));
        
        label = new JLabel(config.getLangValue("creation_mode"));
        addStep(label);
        
        stepTitle.setPreferredSize(new Dimension(520, 25));
        stateLabel.setPreferredSize(new Dimension(520, 25));
        
        label = new JLabel("");
        label.setPreferredSize(new Dimension(520, 10));
        initPanel.add(label);
        
        label = new JLabel(config.getLangValue("mode")+" :");
        label.setFont(config.getTheme().getMenuFont());
        label.setPreferredSize(new Dimension(110, 25));
        initPanel.add(label);
        
        mode = new JComboBox();
        mode.addItem(config.getLangValue("new"));
        mode.addItem(config.getLangValue("from_template"));
        mode.setPreferredSize(new Dimension(200, 30));
        mode.setBorder(config.getTheme().getComboBorder());
        mode.setFont(config.getTheme().getAreasFont());
        ArrayList<String> temps = config.getTemplatesNames();
        if(temps.isEmpty())
            mode.setEnabled(false);
        mode.setActionCommand("mode");
        mode.addActionListener(listener);
        initPanel.add(mode);
        
        label = new JLabel("");
        label.setPreferredSize(new Dimension(190, 10));
        initPanel.add(label);
        
        label = new JLabel(config.getLangValue("template")+" :");
        label.setFont(config.getTheme().getMenuFont());
        label.setPreferredSize(new Dimension(110, 25));
        initPanel.add(label);
        
        template = new JComboBox();
        for(String temp : temps)
            template.addItem(temp);
        template.setPreferredSize(new Dimension(200, 30));
        template.setBorder(config.getTheme().getComboBorder());
        template.setFont(config.getTheme().getAreasFont());
        template.setActionCommand("setTemplate");
        template.addActionListener(listener);
        template.setEnabled(false);
        initPanel.add(template);
        
        label = new JLabel("");
        label.setPreferredSize(new Dimension(190, 10));
        initPanel.add(label);
        
        syncHere = new JCheckBox(config.getLangValue("sync_here"));
        syncHere.setActionCommand("syncHere");
        syncHere.setFont(config.getTheme().getSecondTitleFont());
        syncHere.setPreferredSize(new Dimension(520, 25));
        syncHere.setSelected(true);
        syncHere.addActionListener(listener);
        initPanel.add(syncHere);
        
        label = new JLabel(config.getLangValue("sync_server")+" :");
        label.setFont(config.getTheme().getMenuFont());
        label.setPreferredSize(new Dimension(110, 25));
        initPanel.add(label);
        
        try {
            syncServer = new JTextField(ModelModel.getThisAdress());
        } catch (UnknownHostException ex) {
            syncServer = new JTextField();
        }
        syncServer.setPreferredSize(new Dimension(200, 30));
        syncServer.setFont(config.getTheme().getAreasFont());
        syncServer.setEnabled(!syncHere.isSelected());
        initPanel.add(syncServer);
        
        label = new JLabel("");
        label.setPreferredSize(new Dimension(190, 10));
        initPanel.add(label);
        
        label = new JLabel("");
        label.setPreferredSize(new Dimension(520, 15));
        initPanel.add(label);
        
        label = new JLabel(config.getLangValue("workflow_name")+" :");
        label.setFont(config.getTheme().getMenuFont());
        label.setPreferredSize(new Dimension(520, 25));
        initPanel.add(label);
        
        workflowName = new JTextField();
        workflowName.setPreferredSize(new Dimension(520, 30));
        workflowName.setFont(config.getTheme().getAreasFont());
        initPanel.add(workflowName);
        
        label = new JLabel("");
        label.setPreferredSize(new Dimension(520, 10));
        label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, config.getTheme().getBgColor()));
        initPanel.add(label);
        
        scroll = new JScrollPane(initPanel);
        scroll.setBorder(null);
        panel.add(scroll);
        mainPanel.add(panel, "1");
        
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(520, 500));
        panel.setLayout(new BorderLayout());
        
        grammarPanel = new JPanel();
        grammarPanel.setPreferredSize(new Dimension(500, 450));
        
        label = new JLabel(config.getLangValue("define_grammar"));
        addStep(label);
        
        label = new JLabel("");
        label.setPreferredSize(new Dimension(500, 10));
        grammarPanel.add(label);
        
        label = new JLabel(config.getLangValue("enter_productions")+" :");
        label.setFont(config.getTheme().getMenuFont());
        label.setPreferredSize(new Dimension(500, 25));
        grammarPanel.add(label);
        
        Updater updater = new Updater();
        prods = new ProductionsArea(updater);
        prods.setPreferredSize(new Dimension(500, 200));
        prods.setBorder(config.getTheme().getComboBorder());
        prods.setFont(config.getTheme().getAreasFont());
        prods.addFocusListener(new FocusAdapter(){

            @Override
            public void focusGained(FocusEvent e) {
                prods.selectAll();
                resetError();
            }

            @Override
            public void focusLost(FocusEvent e) {
                controlProds();
                if((prevGram != null && !prevGram.equals(edition.getGrammar()))){
                    edition.setViews(new ArrayList<ArrayList<String>>());
                    updateWorkflow();
                }
                prevGram = edition.getGrammar();
            }
            
        });
        grammarPanel.add(new JScrollPane(prods));
        
        label = new JLabel(config.getLangValue("dnd_grammar"));
        label.setFont(new Font("Cambria", Font.ITALIC, 13));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setPreferredSize(new Dimension(500, 25));
        grammarPanel.add(label);
        
        label = new JLabel(config.getLangValue("choose_axiom")+" :");
        label.setFont(config.getTheme().getMenuFont());
        label.setPreferredSize(new Dimension(130, 25));
        grammarPanel.add(label);
        
        symbs = new JComboBox();
        symbs.setPreferredSize(new Dimension(200, 30));
        symbs.setBorder(config.getTheme().getComboBorder());
        symbs.setFont(config.getTheme().getAreasFont());
        grammarPanel.add(symbs);
        
        label = new JLabel("");
        label.setPreferredSize(new Dimension(150, 10));
        grammarPanel.add(label);
        
        label = new JLabel("");
        label.setPreferredSize(new Dimension(500, 10));
        label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, config.getTheme().getBgColor()));
        grammarPanel.add(label);
        
        label = new JLabel(config.getLangValue("views_list")+" :");
        label.setFont(new Font("Cambria", Font.BOLD, 15));
        label.setPreferredSize(new Dimension(380, 25));
        grammarPanel.add(label);
        
        ChatButton button = new ChatButton(config.getTheme());
        button.setText(config.getLangValue("create_view"));
        button.setActionCommand("create_view");
        button.setPreferredSize(new Dimension(110, 25));
        button.addActionListener(listener);
        grammarPanel.add(button);
        
        label = new JLabel("");
        label.setPreferredSize(new Dimension(500, 10));
        label.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, config.getTheme().getBgColor()));
        grammarPanel.add(label);
        
        viewsNamesPanel = new JPanel();
        viewsNamesPanel.setPreferredSize(new Dimension(500, 30));
        grammarPanel.add(viewsNamesPanel);
        
        label = new JLabel(config.getLangValue("no_views"));
        label.setFont(new Font("Cambria", Font.ITALIC, 13));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setPreferredSize(new Dimension(500, 25));
        viewsNamesPanel.add(label);
        
        label = new JLabel("");
        label.setPreferredSize(new Dimension(500, 10));
        label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, config.getTheme().getBgColor()));
        grammarPanel.add(label);
        
        scroll = new JScrollPane(grammarPanel);
        scroll.setBorder(null);
        panel.add(scroll);
        mainPanel.add(panel, "2");
        
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(520, 500));
        panel.setLayout(new BorderLayout());
        
        authorsPanel = new JPanel();
        authorsPanel.setPreferredSize(new Dimension(500, 300));
        
        label = new JLabel(config.getLangValue("define_coauthors"));
        addStep(label);
        
        label = new JLabel("");
        label.setPreferredSize(new Dimension(500, 10));
        authorsPanel.add(label);
        
        label = new JLabel(config.getLangValue("owner_login")+" :");
        label.setFont(config.getTheme().getMenuFont());
        label.setPreferredSize(new Dimension(110, 25));
        authorsPanel.add(label);
        
        ownerLogin = new JTextField(config.getUser() != null ? config.getUser().get("login") : "");
        ownerLogin.setPreferredSize(new Dimension(260, 30));
        ownerLogin.setFont(config.getTheme().getAreasFont());
        authorsPanel.add(ownerLogin);
        
        label = new JLabel("");
        label.setPreferredSize(new Dimension(115, 25));
        authorsPanel.add(label);
        
        label = new JLabel(config.getLangValue("owner_password")+" :");
        label.setFont(config.getTheme().getMenuFont());
        label.setPreferredSize(new Dimension(110, 25));
        authorsPanel.add(label);
        
        ownerPassword = new JPasswordField(config.getUser() != null ? config.getUser().get("password") : "");
        ownerPassword.setPreferredSize(new Dimension(200, 30));
        ownerPassword.setFont(config.getTheme().getAreasFont());
        echoChar = ownerPassword.getEchoChar();
        authorsPanel.add(ownerPassword);
        
        button = new ChatButton(config.getTheme());
        button.setActionCommand("show_pass");
        button.setIcon(new ImageIcon(getClass().getResource(APPLConstants.RESOURCES_FOLDER+"images/eye.png")));
        button.setPreferredSize(new Dimension(49, 30));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setAreaFilled(false);
        button.setToolTipText("<html><body><font family=\""+config.getTheme().getToolTipFont().getFamily()+"\" size=\""+config.getTheme().getToolTipSize()+"\" color=\""+Theme.extractRGB(config.getTheme().getToolTipColor())+"\">"
                +config.getLangValue("show_pass") +"</font></body></html>");
        button.addActionListener(listener);
        authorsPanel.add(button);
        
        label = new JLabel("");
        label.setPreferredSize(new Dimension(120, 50));
        authorsPanel.add(label);
        
        label = new JLabel("");
        label.setPreferredSize(new Dimension(500, 10));
        label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, config.getTheme().getBgColor()));
        authorsPanel.add(label);
        
        label = new JLabel(config.getLangValue("coauthors_list")+" :");
        label.setFont(new Font("Cambria", Font.BOLD, 15));
        label.setPreferredSize(new Dimension(380, 25));
        authorsPanel.add(label);
        
        button = new ChatButton(config.getTheme());
        button.setText(config.getLangValue("create_coauthor"));
        button.setActionCommand("create_coauthor");
        button.setPreferredSize(new Dimension(110, 25));
        button.addActionListener(listener);
        authorsPanel.add(button);
        
        label = new JLabel("");
        label.setPreferredSize(new Dimension(500, 10));
        label.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, config.getTheme().getBgColor()));
        authorsPanel.add(label);
        
        
        authsNamesPanel = new JPanel();
        authsNamesPanel.setPreferredSize(new Dimension(500, 30));
        authorsPanel.add(authsNamesPanel);
        
        label = new JLabel(config.getLangValue("no_coauthors"));
        label.setFont(new Font("Cambria", Font.ITALIC, 13));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setPreferredSize(new Dimension(500, 25));
        authsNamesPanel.add(label);
        
        
        label = new JLabel("");
        label.setPreferredSize(new Dimension(190, 10));
        authorsPanel.add(label);
        
        label = new JLabel("");
        label.setPreferredSize(new Dimension(500, 10));
        label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, config.getTheme().getBgColor()));
        authorsPanel.add(label);
        
        scroll = new JScrollPane(authorsPanel);
        scroll.setBorder(null);
        panel.add(scroll);
        mainPanel.add(panel, "3");
        
        
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(520, 500));
        panel.setLayout(new BorderLayout());
        
        docPanel = new JPanel();
        docPanel.setPreferredSize(new Dimension(510, 200));
        
        label = new JLabel(config.getLangValue("initial_document"));
        addStep(label);
        
        stepTitle.setPreferredSize(new Dimension(520, 25));
        stateLabel.setPreferredSize(new Dimension(520, 25));
        
        label = new JLabel("");
        label.setPreferredSize(new Dimension(520, 10));
        docPanel.add(label);
        
        label = new JLabel(config.getLangValue("document_structure")+" :");
        label.setFont(config.getTheme().getMenuFont());
        label.setPreferredSize(new Dimension(500, 25));
        docPanel.add(label);
        
        document = new JTextArea();
        document.setPreferredSize(new Dimension(500, 70));
        document.setBorder(config.getTheme().getComboBorder());
        document.setFont(config.getTheme().getAreasFont());
        docPanel.add(new JScrollPane(document));
        
        button = new ChatButton(config.getTheme());
        button.setText(config.getLangValue("preview"));
        button.setActionCommand("preview");
        button.setPreferredSize(new Dimension(110, 25));
        button.addActionListener(listener);
        docPanel.add(button);
        
        label = new JLabel("");
        label.setPreferredSize(new Dimension(380, 10));
        docPanel.add(label);
        
        saveAsTemplate = new JCheckBox(config.getLangValue("save_as_template"));
        saveAsTemplate.setFont(config.getTheme().getSecondTitleFont());
        saveAsTemplate.setPreferredSize(new Dimension(500, 25));
        saveAsTemplate.setSelected(false);
        saveAsTemplate.setActionCommand("save_as_template");
        saveAsTemplate.addActionListener(listener);
        docPanel.add(saveAsTemplate);
        
        label = new JLabel(config.getLangValue("template_name")+" :");
        label.setFont(config.getTheme().getMenuFont());
        label.setPreferredSize(new Dimension(110, 25));
        docPanel.add(label);
        
        templateName = new JTextField();
        templateName.setPreferredSize(new Dimension(377, 30));
        templateName.setFont(config.getTheme().getAreasFont());
        templateName.setEnabled(false);
        docPanel.add(templateName);
        
        label = new JLabel("");
        label.setPreferredSize(new Dimension(520, 10));
        label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, config.getTheme().getBgColor()));
        docPanel.add(label);
        
        scroll = new JScrollPane(docPanel);
        scroll.setBorder(null);
        panel.add(scroll);
        mainPanel.add(panel, "4");
        
        
        okButton.addActionListener(listener);
        okButton.setText(config.getLangValue("finish"));
        okButton.setPreferredSize(new Dimension((okButton.getText().length()) * 11, 30));
        okButton.setEnabled(false);
        
        cancelButton.addActionListener(listener);
        cancelButton.setPreferredSize(new Dimension((cancelButton.getText().length()) * 11, 30));
        
        nextButton.addActionListener(listener);
        prevButton.addActionListener(listener);
        
        //this.remove(this.stepPanel);
        try {
            setStep(1);
        } catch (ApplException ex) {
            
        }
    }
    
    public boolean isCreate() {
        return create;
    }
    
    private void controlProds() {
        String text = prods.getText();
        HashMap<String, ArrayList<String>> productions = new HashMap<String, ArrayList<String>>();
        ArrayList<String> symbols = new ArrayList<String>();
        ArrayList<ArrayList<String>> p = new ArrayList<ArrayList<String>>();
        if(text != null && !text.trim().equals("")){
            text = text.trim();
            text = text.replace("|", "~");
            text = text.replace("\n", "@");
            text = text.replaceAll("[ ]{0,}~[ ]{0,}", "~");
            text = text.replaceAll("[ ]{0,}->[ ]{0,}", "->");
            text = text.replaceAll("[ ]{0,}@[ ]{0,}", "@");
            text = text.replaceAll("@{2,}", "@");
            String textCmp = text.replaceAll("[pP][0-9]{1,}", "");
            if(text.equals(textCmp)){
                String[] prds = text.split("@");
                goodGrammar = true;
                for(String prd : prds){
                    String[] parts = prd.split("->");
                    if(parts.length != 2 || !parts[0].matches("[a-zA-Z0-9_]{1,}") || !parts[1].matches("((([a-zA-Z0-9_]{1,})( ([a-zA-Z0-9_]{1,})){0,})|£)(([ ]{0,1}~[ ]{0,1}((([a-zA-Z0-9_]{1,})( ([a-zA-Z0-9_]{1,})){0,})|£)){0,})")){
                        goodGrammar = false;
                        break;
                    }
                    if(!symbols.contains(parts[0]))
                        symbols.add(parts[0]);
                    String[] rhss = parts[1].split("~");
                    for(String rhs : rhss){
                        ArrayList<String> pr = new ArrayList<String>();
                        pr.add(parts[0]);
                        if(rhs.equals("£")){
                            if(!p.contains(pr))
                                p.add(pr);
                        }else{
                            String[] rhssy = rhs.split(" ");
                            for(String sy : rhssy){
                                if(!symbols.contains(sy))
                                    symbols.add(sy);
                                pr.add(sy);
                            }
                            p.add(pr);
                        }
                    }
                }
            }
        }
        symbs.removeAllItems();
        if(goodGrammar){
            resetError();
            for(String sy : symbols)
                symbs.addItem(sy);
            Grammar<String, String> gram = new Grammar<String, String>();
            gram.setSymbols(symbols);
            gram.setAxiom(symbols.get(0));
            for(int i = 0, l = p.size(); i < l; i++)
                productions.put("P"+(i + 1), p.get(i));
            gram.setProductions(productions);
            edition.setGrammar(gram);
            prods.setText(gram.getGrammar());
            if(prevGram == null){
                prevGram = gram;
            }
        }else{
            setError(config.getLangValue("grammar_error"));
        }
    }
    
    public void setError(String error){
        stateLabel.setForeground(Color.red);
        stateLabel.setText(error);
        if(error != null && !error.isEmpty())
            stateLabel.setIcon(new ImageIcon(getClass().getResource(APPLConstants.RESOURCES_FOLDER+"images/error.png")));
    }
    
    public void resetError(){
        stateLabel.setText("");
        stateLabel.setIcon(null);
    }
    
    private void updateWorkflow(){
        if(edition != null){
            if(edition.getGrammar() != null){
                prods.setText(edition.getGrammar().getGrammar());
                if(prevGram == null)
                    prevGram = edition.getGrammar();
                symbs.removeAllItems();
                ArrayList<String> symbols = edition.getGrammar().getSymbolsAxiomTop();
                for(String symb : symbols)
                    symbs.addItem(symb);
                goodGrammar = true;
            }else{
                prods.setText("");
                symbs.removeAllItems();
            }
            
            if(edition.getCoAuthors() != null){
                Set<String> logins = edition.getCoAuthors().keySet();
                authsNamesPanel.removeAll();
                authsNamesPanel.validate();
                authsNamesPanel.repaint();
                authsNamesPanel.updateUI();
                
                authsNamesPanel.setPreferredSize(new Dimension(500, logins.size()*50 + 30));
                authorsPanel.setPreferredSize(new Dimension(500, 300 + logins.size()*50));
                if(logins.isEmpty()){
                    label = new JLabel(config.getLangValue("no_coauthors"));
                    label.setFont(new Font("Cambria", Font.ITALIC, 13));
                    label.setHorizontalAlignment(JLabel.CENTER);
                    label.setPreferredSize(new Dimension(500, 25));
                    authsNamesPanel.add(label);
                }else{
                    StyledPanel pan;
                    for(String login : logins){
                        pan = new StyledPanel(StyledPanel.SIMPLE, config);
                        pan.setPreferredSize(new Dimension(500, 40));
                        String dLogin = "";
                        try{
                            dLogin = APPLConstants.decryptMessage(login);
                        }catch(Throwable e){
                            
                        }
                        UserInfos inf = edition.getCoAuthors().get(login);
                        String val = "";
                        val = dLogin + " ; " + config.getLangValue("global_view") + " = " + 
                                (inf.isCanSeeGlobalWorkflow() ? config.getLangValue("yes") : config.getLangValue("no"))+" ; "+
                                config.getLangValue("choose_consensus") + " = " +(inf.isCanDecideSynchro() ? config.getLangValue("yes") : config.getLangValue("no"))
                                + " ; "+config.getLangValue("view") + " = "+(inf.getView().get(0).toString().replace("[", "{").replace("]", "}"));
                        
                        label = new JLabel(val);
                        label.setFont(new Font("Cambria", Font.BOLD, 14));
                        label.setHorizontalAlignment(JLabel.LEFT);
                        label.setPreferredSize(new Dimension(440, 35));
                        pan.add(label);
                        
                        ChatButton button = new ChatButton(config.getTheme());
                        button.setActionCommand("delete-coauthor_" + login);
                        button.setIcon(new ImageIcon(getClass().getResource(APPLConstants.RESOURCES_FOLDER+"images/delete.png")));
                        button.setPreferredSize(new Dimension(20, 20));
                        button.setBorderPainted(false);
                        button.setFocusPainted(false);
                        button.setContentAreaFilled(false);
                        button.setAreaFilled(false);
                        button.setToolTipText("<html><body><font family=\""+config.getTheme().getToolTipFont().getFamily()+"\" size=\""+config.getTheme().getToolTipSize()+"\" color=\""+Theme.extractRGB(config.getTheme().getToolTipColor())+"\">"
                                +config.getLangValue("delete_coauthor") +"</font></body></html>");
                        button.addActionListener(listener);
                        pan.add(button);
                        
                        authsNamesPanel.add(pan);
                    }
                }
                authsNamesPanel.validate();
                authsNamesPanel.repaint();
                authsNamesPanel.updateUI();
            }else{
                
            }
            
            if(edition.getViews() != null){
                ArrayList<ArrayList<String>> views = edition.getViews();
                viewsNamesPanel.removeAll();
                viewsNamesPanel.validate();
                viewsNamesPanel.repaint();
                viewsNamesPanel.updateUI();
                
                viewsNamesPanel.setPreferredSize(new Dimension(500, views.size()*50 + 30));
                grammarPanel.setPreferredSize(new Dimension(500, 450 + views.size()*50));
                if(views.isEmpty()){
                    label = new JLabel(config.getLangValue("no_views"));
                    label.setFont(new Font("Cambria", Font.ITALIC, 13));
                    label.setHorizontalAlignment(JLabel.CENTER);
                    label.setPreferredSize(new Dimension(500, 25));
                    viewsNamesPanel.add(label);
                }else{
                    StyledPanel pan;
                    for(ArrayList<String> view : views){
                        pan = new StyledPanel(StyledPanel.SIMPLE, config);
                        pan.setPreferredSize(new Dimension(500, 40));
                        
                        ArrayList<String> v = new ArrayList<String>();
                        v.addAll(view);
                        v.remove(0);
                        
                        label = new JLabel((view.get(0) + " = " + v).replace("[", "{").replace("]", "}"));
                        label.setFont(new Font("Cambria", Font.BOLD, 14));
                        label.setHorizontalAlignment(JLabel.LEFT);
                        label.setPreferredSize(new Dimension(440, 35));
                        pan.add(label);
                        
                        ChatButton button = new ChatButton(config.getTheme());
                        button.setActionCommand("delete-view_" + view.get(0));
                        button.setIcon(new ImageIcon(getClass().getResource(APPLConstants.RESOURCES_FOLDER+"images/delete.png")));
                        button.setPreferredSize(new Dimension(20, 20));
                        button.setBorderPainted(false);
                        button.setFocusPainted(false);
                        button.setContentAreaFilled(false);
                        button.setAreaFilled(false);
                        button.setToolTipText("<html><body><font family=\""+config.getTheme().getToolTipFont().getFamily()+"\" size=\""+config.getTheme().getToolTipSize()+"\" color=\""+Theme.extractRGB(config.getTheme().getToolTipColor())+"\">"
                                +config.getLangValue("delete_view") +"</font></body></html>");
                        button.addActionListener(listener);
                        pan.add(button);
                        
                        viewsNamesPanel.add(pan);
                    }
                }
                viewsNamesPanel.validate();
                viewsNamesPanel.repaint();
                viewsNamesPanel.updateUI();
            }
            
            if(edition.getInitialDocument() != null && !edition.getInitialDocument().isEmpty()){
                document.setText(edition.getInitialDocument());
            }else{
                if(edition.getGrammar() != null){
                    String buds = "";
                    Set<String> pr = edition.getGrammar().getProductions().keySet();
                    for(String prod : pr){
                        if(edition.getGrammar().lhs(prod).equals(edition.getGrammar().getAxiom()) && 
                                !edition.getGrammar().rhs(prod).isEmpty()){
                            ArrayList<String> rhs = edition.getGrammar().rhs(prod);
                            for(int i = 0, l = rhs.size(); i < l; i++){
                                if(i == 0)
                                    buds += rhs.get(i)+"omega[]";
                                else
                                    buds += ","+rhs.get(i)+"omega[]";
                            }
                            break;
                        }
                    }
                    document.setText(edition.getGrammar().getAxiom()+"["+buds+"]");
                }
            }
            
            if(edition.getWorkflowName() != null){
                templateName.setText(APPLConstants.getFileNameFromString(edition.getWorkflowName()));
                workflowName.setText(edition.getWorkflowName());
            }else
                workflowName.setText("");
            
            if(edition.getOwnerLogin() != null){
                String dLogin = "";
                try{
                    dLogin = APPLConstants.decryptMessage(edition.getOwnerLogin());
                }catch(Throwable e){

                }
                ownerLogin.setText(dLogin);
            }
            
            if(edition.getOwnerPassword() != null){
                String dPass = "";
                try{
                    dPass = APPLConstants.decryptMessage(edition.getOwnerPassword());
                }catch(Throwable e){

                }
                ownerPassword.setText(dPass);
            }
        }
    }
    
    private boolean isCreationModeValid(){
        String name = workflowName.getText();
        if(name == null || name.trim().isEmpty()){
            setError(config.getLangValue("invalid_workflow_name"));
            return false;
        }
        edition.setWorkflowName(name);
        
        String host = syncServer.getText();
        if(host == null || host.trim().isEmpty()){
            setError(config.getLangValue("invalid_sync_server"));
            return false;
        }
        try{
            config.getCommunicator(host);
        }catch(Exception e){
            setError(config.getLangValue("unavailable_sync_server"));
            return false;
        }
        edition.setWorkflowServer(host);
        
        updateWorkflow();
        return true;
    }
    
    private boolean isGrammarValid(){
        if(!goodGrammar || edition.getGrammar() == null){
            setError(config.getLangValue("grammar_error"));
            return false;
        }
        
        edition.getGrammar().setAxiom((String)symbs.getSelectedItem());
        
        if(edition.getViews() == null || edition.getViews().isEmpty()){
            setError(config.getLangValue("no_views"));
            return false;
        }
        
        for(ArrayList<String> symbss : edition.getViews()){
            int i = 0;
            if(symbss.size() <= 1 || !symbss.get(0).matches("[a-z]([a-zA-Z0-9]{3,})")){
                setError(config.getLangValue("bad_views"));
                return false;
            }
            for(String sy : symbss){
                if(i > 0 && !edition.getGrammar().isSymbol(sy)){
                    setError(config.getLangValue("invalid_view_symbol"));
                    return false;
                }
                i++;
            }
        }
        
        updateWorkflow();
        return true;
    }
    
    private boolean isUsersValid(){
        char[] p = ownerPassword.getPassword();
        String pass = "";
        for(char c : p)
            pass += c;
        if(ownerLogin.getText() == null || ownerLogin.getText().trim().isEmpty() || pass.isEmpty()){
            setError(config.getLangValue("ids_required"));
            return false;
        }
        try{
            edition.setOwnerLogin(APPLConstants.encryptMessage(ownerLogin.getText()));
            edition.setOwnerPassword(APPLConstants.encryptMessage(pass));
        }catch(Throwable e){
            
        }
        
        Set<String> logins = edition.getCoAuthors().keySet();
        for(String log : logins){
            try{
                APPLConstants.decryptMessage(log);
            }catch(Throwable e){
                setError(config.getLangValue("user_bad_def"));
                return false;
            }
            UserInfos<String> uInf = edition.getCoAuthors().get(log);
            if(uInf == null || !edition.getViews().contains(uInf.getView())){
                setError(config.getLangValue("user_bad_def"));
                return false;
            }
        }
        
        updateWorkflow();
        return true;
    }
    
    private boolean isInitDocValid(){
        String doc = document.getText();
        doc = doc.replace("\n", "");
        doc = doc.replace(" ", "");
        String ast = null;
        try{
            ast = Parsers.derToAstWithoutBud(doc, edition.getGrammar());
            if(ast == null){
                setError(config.getLangValue("invalid_document"));
                return false;
            }
        }catch(Exception ex){
            setError(config.getLangValue("invalid_document"));
            return false;
        }
        edition.setInitialDocument(doc);
        return true;
    }
    
    public class Updater extends ObserverAdapter{

        public Updater() {
        }

        @Override
        public void updateGrammarAndViews(GrammarAndViews<String> gramAndViews) {
            edition.setGrammar(gramAndViews.getGrammar());
            edition.setViews(gramAndViews.getViews() != null ? gramAndViews.getViews() : new ArrayList<ArrayList<String>>());
            updateWorkflow();
        }
        
        @Override
        public void updateGrammarError(String err){
            setError(err);
        }
    }*/
}
