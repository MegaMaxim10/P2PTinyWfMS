package smartworkflow.dwfms.urifia.fmml.miu.view.util.editor;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import smartworkflow.dwfms.urifia.fmml.miu.models.util.ModelModel;
import smartworkflow.dwfms.urifia.fmml.miu.util.ConfigurationManager;
import smartworkflow.dwfms.urifia.fmml.miu.util.exceptions.ApplException;
import smartworkflow.dwfms.urifia.fmml.miu.view.util.ChatDialogModel;

/**
 *
 * @author Ndadji Maxime
 */
public class MessagePreviewDialog extends ChatDialogModel{
	private static final long serialVersionUID = 1L;
	private JLabel label;
    private MIUEditor editor;
    private JEditorPane preview;
    private JPanel panel;
    private JScrollPane scroll;
    
    public MessagePreviewDialog(ConfigurationManager config, boolean modal, JFrame parent, MIUEditor editor){
        super(ChatDialogModel.OK_CANCEL_BUTTON, config, parent, config.getLangValue("message_preview"), modal, false);
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
        this.setResizable(false);
        this.editor = editor;
        this.initComponents();
    }

    private void initComponents() {
        listener = new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                if(command.equalsIgnoreCase(CANCEL_KEY)){
                    closeDialog();
                    return;
                }
                
                if(command.equalsIgnoreCase(OK_KEY)){
                    refresh();
                    return;
                }
            }
        };
        
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(460, 350));
        
        label = new JLabel(config.getLangValue("preview"));
        addStep(label);
        
        stepTitle.setPreferredSize(new Dimension(460, 25));
        stateLabel.setPreferredSize(new Dimension(460, 25));
        
        preview = new JEditorPane();
        preview.setContentType("text/html");
        preview.setEditable(false);
        preview.setPreferredSize(new Dimension(460, 330));
        JScrollPane pane = new JScrollPane(preview);
        pane.setBorder(null);
        pane.getVerticalScrollBar().setValue(pane.getVerticalScrollBar().getMinimum());
        pane.getHorizontalScrollBar().setValue(pane.getHorizontalScrollBar().getMinimum());
        panel.add(pane);
        
        label = new JLabel("");
        label.setPreferredSize(new Dimension(460, 10));
        label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, config.getTheme().getBgColor()));
        panel.add(label);
        
        scroll = new JScrollPane(panel);
        scroll.setBorder(null);
        mainPanel.add(scroll, "1");
        
        okButton.addActionListener(listener);
        okButton.setText(config.getLangValue("refresh"));
        okButton.setPreferredSize(new Dimension((okButton.getText().length()) * 11, 30));
        
        cancelButton.addActionListener(listener);
        cancelButton.setText(config.getLangValue("close"));
        cancelButton.setPreferredSize(new Dimension((cancelButton.getText().length()) * 11, 30));
        
        this.remove(this.stepPanel);
        try {
            setStep(1);
        } catch (ApplException ex) {
            
        }
    }

    public void refresh() {
        preview.setText(ModelModel.parseMessage(editor.getEditor().getText()));
    }

    public MIUEditor getEditor() {
        return editor;
    }
}
