/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartworkflow.dwfms.urifia.fmml.miu.view.util.editor;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import smartworkflow.dwfms.urifia.fmml.miu.models.util.ModelModel;
import smartworkflow.dwfms.urifia.fmml.miu.util.APPLConstants;
import smartworkflow.dwfms.urifia.fmml.miu.util.ConfigurationManager;
import smartworkflow.dwfms.urifia.fmml.miu.util.Theme;
import smartworkflow.dwfms.urifia.fmml.miu.view.util.ChatButton;

/**
 *
 * @author Ndadji Maxime
 */
public class MIUEditor extends JPanel{
	private static final long serialVersionUID = 1L;
	private JEditorPane editor;
    private JToolBar toolBar;
    private ConfigurationManager config;
    private JScrollPane scroll;
    private ActionListener listener;
    private MessagePreviewDialog previewer;
    
    public MIUEditor(ConfigurationManager config){
        this.setLayout(new BorderLayout());
        this.config = config;
        
        this.initComponents();
    }

    private void initComponents() {
        previewer = new MessagePreviewDialog(config, false, null, this);
        this.initToolBar();
        
        editor = new JEditorPane();
        editor.setContentType("text/html");
        editor.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        editor.setText("<html><head><style>body{font-family:"+config.getTheme().getMessageFont().getFontName()+"; font-size:"+config.getTheme().getMessageFont().getSize()+";}blockquote{font-size: 0.92em; font-style: italic;}</style></head><body></body></html>");
        editor.selectAll();
        editor.setSelectionStart(editor.getSelectionEnd());
        editor.addFocusListener(new FocusAdapter(){

            @Override
            public void focusGained(FocusEvent e) {
                editor.selectAll();
            }
            
        });
        this.setBorder(BorderFactory.createEtchedBorder());
        scroll = new JScrollPane(editor);
        scroll.setBorder(null);
        this.add(scroll);
    }

    private void initToolBar() {
        listener = new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                
                int end = editor.getSelectionEnd();
                int start = editor.getSelectionStart();
                String text = editor.getSelectedText();
                
                if(command.equals("bold")){
                    editor.replaceSelection("[bold]"+((text != null) ? text : "")+"[/bold]");
                    if(text == null){
                        editor.setSelectionStart(end + 6);
                        editor.setSelectionEnd(end + 6);
                    }else{
                        editor.setSelectionStart(start + 6);
                        editor.setSelectionEnd(start + 6 + text.length());
                    }
                    return;
                }
                
                if(command.equals("italic")){
                    editor.replaceSelection("[italic]"+((text != null) ? text : "")+"[/italic]");
                    if(text == null){
                        editor.setSelectionStart(end + 8);
                        editor.setSelectionEnd(end + 8);
                    }else{
                        editor.setSelectionStart(start + 8);
                        editor.setSelectionEnd(start + 8 + text.length());
                    }
                    return;
                }
                
                if(command.equals("underline")){
                    editor.replaceSelection("[underline]"+((text != null) ? text : "")+"[/underline]");
                    if(text == null){
                        editor.setSelectionStart(end + 11);
                        editor.setSelectionEnd(end + 11);
                    }else{
                        editor.setSelectionStart(start + 11);
                        editor.setSelectionEnd(start + 11 + text.length());
                    }
                    return;
                }
                
                if(command.equals("left")){
                    editor.replaceSelection("[align value=\"left\"]"+((text != null) ? text : "")+"[/align]");
                    if(text == null){
                        editor.setSelectionStart(end + 20);
                        editor.setSelectionEnd(end + 20);
                    }else{
                        editor.setSelectionStart(start + 20);
                        editor.setSelectionEnd(start + 20 + text.length());
                    }
                    return;
                }
                
                if(command.equals("right")){
                    editor.replaceSelection("[align value=\"right\"]"+((text != null) ? text : "")+"[/align]");
                    if(text == null){
                        editor.setSelectionStart(end + 21);
                        editor.setSelectionEnd(end + 21);
                    }else{
                        editor.setSelectionStart(start + 21);
                        editor.setSelectionEnd(start + 21 + text.length());
                    }
                    return;
                }
                
                if(command.equals("center")){
                    editor.replaceSelection("[align value=\"center\"]"+((text != null) ? text : "")+"[/align]");
                    if(text == null){
                        editor.setSelectionStart(end + 22);
                        editor.setSelectionEnd(end + 22);
                    }else{
                        editor.setSelectionStart(start + 22);
                        editor.setSelectionEnd(start + 22 + text.length());
                    }
                    return;
                }
                
                if(command.equals("blockquote")){
                    editor.replaceSelection("[quote]"+((text != null) ? text : "")+"[/quote]");
                    if(text == null){
                        editor.setSelectionStart(end + 7);
                        editor.setSelectionEnd(end + 7);
                    }else{
                        editor.setSelectionStart(start + 7);
                        editor.setSelectionEnd(start + 7 + text.length());
                    }
                    return;
                }
                
                if(command.equals("bullets")){
                    editor.replaceSelection("[list]\n[item][/item]\n[/list]");
                    editor.setSelectionStart(start + 13);
                    editor.setSelectionEnd(start + 13);
                    return;
                }
                
                if(command.equals("smilie")){
                    String smilie = ModelModel.setSmilie(true);
                    if(smilie != null && !smilie.isEmpty()){
                        String[] images = smilie.split(APPLConstants.SEPARATOR);
                        String value = "[smi-"+images[0]+"]";
                        for(int i = 1; i < images.length; i++)
                            value += " [smi-"+images[i]+"]";
                        editor.replaceSelection(value);
                    }
                    return;
                }
                
                if(command.equals("avatar")){
                    String avatar = ModelModel.setAvatar(true);
                    if(avatar != null && !avatar.isEmpty()){
                        String[] images = avatar.split(APPLConstants.SEPARATOR);
                        String value = "[ava-"+images[0]+"]";
                        for(int i = 1; i < images.length; i++)
                            value += " [ava-"+images[i]+"]";
                        editor.replaceSelection(value);
                    }
                    return;
                }
                
                if(command.equals("preview")){
                    previewer = new MessagePreviewDialog(config, true, null, previewer.getEditor());
                    previewer.refresh();
                    previewer.showDialog();
                }
            }
            
        };
        
        toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setBackground(config.getTheme().getToolBarColor());
        
        ChatButton button = new ChatButton(config.getTheme());
        button.setIcon(new ImageIcon(getClass().getResource("/miu/perfumery/manager/ressources/images/editor/bold.gif")));
        button.setToolTipText("<html><body><font family=\""+config.getTheme().getToolTipFont().getFamily()+"\" size=\""+config.getTheme().getToolTipSize()+"\" color=\""+Theme.extractRGB(config.getTheme().getToolTipColor())+"\">"
                +config.getLangValue("bold") +"</font></body></html>");
        button.setPreferredSize(new Dimension(28, 28));
        button.setActionCommand("bold");
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setAreaFilled(false);
        button.setFocusable(false);
        button.addActionListener(listener);
        toolBar.add(button);
        
        button = new ChatButton(config.getTheme());
        button.setIcon(new ImageIcon(getClass().getResource("/miu/perfumery/manager/ressources/images/editor/italic.gif")));
        button.setToolTipText("<html><body><font family=\""+config.getTheme().getToolTipFont().getFamily()+"\" size=\""+config.getTheme().getToolTipSize()+"\" color=\""+Theme.extractRGB(config.getTheme().getToolTipColor())+"\">"
                +config.getLangValue("italic") +"</font></body></html>");
        button.setPreferredSize(new Dimension(28, 28));
        button.setActionCommand("italic");
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setAreaFilled(false);
        button.setFocusable(false);
        button.addActionListener(listener);
        toolBar.add(button);
        
        button = new ChatButton(config.getTheme());
        button.setIcon(new ImageIcon(getClass().getResource("/miu/perfumery/manager/ressources/images/editor/underline.gif")));
        button.setToolTipText("<html><body><font family=\""+config.getTheme().getToolTipFont().getFamily()+"\" size=\""+config.getTheme().getToolTipSize()+"\" color=\""+Theme.extractRGB(config.getTheme().getToolTipColor())+"\">"
                +config.getLangValue("underline") +"</font></body></html>");
        button.setPreferredSize(new Dimension(28, 28));
        button.setActionCommand("underline");
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setAreaFilled(false);
        button.setFocusable(false);
        button.addActionListener(listener);
        toolBar.add(button);
        
        toolBar.addSeparator(new Dimension(10, 5));
        
        button = new ChatButton(config.getTheme());
        button.setIcon(new ImageIcon(getClass().getResource("/miu/perfumery/manager/ressources/images/editor/left.gif")));
        button.setToolTipText("<html><body><font family=\""+config.getTheme().getToolTipFont().getFamily()+"\" size=\""+config.getTheme().getToolTipSize()+"\" color=\""+Theme.extractRGB(config.getTheme().getToolTipColor())+"\">"
                +config.getLangValue("align_left") +"</font></body></html>");
        button.setPreferredSize(new Dimension(28, 28));
        button.setActionCommand("left");
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setAreaFilled(false);
        button.setFocusable(false);
        button.addActionListener(listener);
        toolBar.add(button);
        
        button = new ChatButton(config.getTheme());
        button.setIcon(new ImageIcon(getClass().getResource("/miu/perfumery/manager/ressources/images/editor/center.gif")));
        button.setToolTipText("<html><body><font family=\""+config.getTheme().getToolTipFont().getFamily()+"\" size=\""+config.getTheme().getToolTipSize()+"\" color=\""+Theme.extractRGB(config.getTheme().getToolTipColor())+"\">"
                +config.getLangValue("align_center") +"</font></body></html>");
        button.setPreferredSize(new Dimension(28, 28));
        button.setActionCommand("center");
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setAreaFilled(false);
        button.setFocusable(false);
        button.addActionListener(listener);
        toolBar.add(button);
        
        button = new ChatButton(config.getTheme());
        button.setIcon(new ImageIcon(getClass().getResource("/miu/perfumery/manager/ressources/images/editor/right.gif")));
        button.setToolTipText("<html><body><font family=\""+config.getTheme().getToolTipFont().getFamily()+"\" size=\""+config.getTheme().getToolTipSize()+"\" color=\""+Theme.extractRGB(config.getTheme().getToolTipColor())+"\">"
                +config.getLangValue("align_right") +"</font></body></html>");
        button.setPreferredSize(new Dimension(28, 28));
        button.setActionCommand("right");
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setAreaFilled(false);
        button.setFocusable(false);
        button.addActionListener(listener);
        toolBar.add(button);
        
        toolBar.addSeparator(new Dimension(10, 5));
        
        button = new ChatButton(config.getTheme());
        button.setIcon(new ImageIcon(getClass().getResource("/miu/perfumery/manager/ressources/images/editor/bullets.gif")));
        button.setToolTipText("<html><body><font family=\""+config.getTheme().getToolTipFont().getFamily()+"\" size=\""+config.getTheme().getToolTipSize()+"\" color=\""+Theme.extractRGB(config.getTheme().getToolTipColor())+"\">"
                +config.getLangValue("insert_list") +"</font></body></html>");
        button.setPreferredSize(new Dimension(28, 28));
        button.setActionCommand("bullets");
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setAreaFilled(false);
        button.setFocusable(false);
        button.addActionListener(listener);
        toolBar.add(button);
        
        toolBar.addSeparator(new Dimension(10, 5));
        
        button = new ChatButton(config.getTheme());
        button.setIcon(new ImageIcon(getClass().getResource("/miu/perfumery/manager/ressources/images/editor/blockquote.png")));
        button.setToolTipText("<html><body><font family=\""+config.getTheme().getToolTipFont().getFamily()+"\" size=\""+config.getTheme().getToolTipSize()+"\" color=\""+Theme.extractRGB(config.getTheme().getToolTipColor())+"\">"
                +config.getLangValue("insert_quote") +"</font></body></html>");
        button.setPreferredSize(new Dimension(28, 28));
        button.setActionCommand("blockquote");
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setAreaFilled(false);
        button.setFocusable(false);
        button.addActionListener(listener);
        toolBar.add(button);
        
        toolBar.addSeparator(new Dimension(10, 5));
        
        button = new ChatButton(config.getTheme());
        button.setIcon(new ImageIcon(getClass().getResource("/miu/perfumery/manager/ressources/images/editor/heureux.png")));
        button.setToolTipText("<html><body><font family=\""+config.getTheme().getToolTipFont().getFamily()+"\" size=\""+config.getTheme().getToolTipSize()+"\" color=\""+Theme.extractRGB(config.getTheme().getToolTipColor())+"\">"
                +config.getLangValue("choose_smilie") +"</font></body></html>");
        button.setPreferredSize(new Dimension(28, 28));
        button.setActionCommand("smilie");
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setAreaFilled(false);
        button.setFocusable(false);
        button.addActionListener(listener);
        toolBar.add(button);
        
        button = new ChatButton(config.getTheme());
        button.setIcon(new ImageIcon(getClass().getResource("/miu/perfumery/manager/ressources/images/editor/default.png")));
        button.setToolTipText("<html><body><font family=\""+config.getTheme().getToolTipFont().getFamily()+"\" size=\""+config.getTheme().getToolTipSize()+"\" color=\""+Theme.extractRGB(config.getTheme().getToolTipColor())+"\">"
                +config.getLangValue("choose_avatar") +"</font></body></html>");
        button.setPreferredSize(new Dimension(28, 28));
        button.setActionCommand("avatar");
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setAreaFilled(false);
        button.setFocusable(false);
        button.addActionListener(listener);
        toolBar.add(button);
        
        toolBar.addSeparator(new Dimension(10, 5));
        
        button = new ChatButton(config.getTheme());
        button.setIcon(new ImageIcon(getClass().getResource("/miu/perfumery/manager/ressources/images/editor/preview.png")));
        button.setToolTipText("<html><body><font family=\""+config.getTheme().getToolTipFont().getFamily()+"\" size=\""+config.getTheme().getToolTipSize()+"\" color=\""+Theme.extractRGB(config.getTheme().getToolTipColor())+"\">"
                +config.getLangValue("message_preview") +"</font></body></html>");
        button.setPreferredSize(new Dimension(28, 28));
        button.setActionCommand("preview");
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setAreaFilled(false);
        button.setFocusable(false);
        button.addActionListener(listener);
        toolBar.add(button);
        
        this.add(toolBar, BorderLayout.NORTH);
    }

    public JEditorPane getEditor() {
        return editor;
    }

    public JToolBar getToolBar() {
        return toolBar;
    }
    
    public void quote(String message){
        int end = editor.getSelectionEnd();
        editor.replaceSelection("[quote]"+((message != null) ? message : "")+"[/quote]");
        if(message == null){
            editor.setSelectionStart(end + 7);
            editor.setSelectionEnd(end + 7);
        }
    }
}
