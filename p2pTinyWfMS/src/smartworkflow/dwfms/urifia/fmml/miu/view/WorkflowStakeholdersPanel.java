package smartworkflow.dwfms.urifia.fmml.miu.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import smartworkflow.dwfms.urifia.fmml.miu.controllers.util.ControllerModel;
import smartworkflow.dwfms.urifia.fmml.miu.models.util.ModelModel;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.Accreditation;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.PeerToPeerWorkflow;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.Stakeholder;
import smartworkflow.dwfms.urifia.fmml.miu.view.util.PanelModel;
import smartworkflow.dwfms.urifia.fmml.miu.view.util.TableModel;

public class WorkflowStakeholdersPanel extends PanelModel {

	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private JToolBar toolBar;
	private JTable stakeholdersTable;
	private JLabel titleLabel;
	private PeerToPeerWorkflow workflow;

	public WorkflowStakeholdersPanel(ControllerModel controller, PeerToPeerWorkflow workflow) {
		super(controller);
		this.workflow = workflow;
		this.initComponents();
	}

	@Override
	protected void initComponents() {
		this.setLayout(new BorderLayout());

		this.initListeners();

		this.initPanels();

		this.initMenus();
		
		this.initToolBar();
	}

	private void initListeners() {

	}

	private void initPanels() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		String[] titles = { "ID", "A(r)", "A(w)", "A(x)" };
		stakeholdersTable = new JTable(new TableModel(titles));
        stakeholdersTable.setRowHeight(20);
        stakeholdersTable.setSelectionBackground(ModelModel.getConfig().getTheme().getBgColor());
        stakeholdersTable.setSelectionForeground(Color.WHITE);
        stakeholdersTable.setGridColor(ModelModel.getConfig().getTheme().getBgColor());
        stakeholdersTable.setFont(ModelModel.getConfig().getTheme().getAreasFont());
        stakeholdersTable.setOpaque(false);
        stakeholdersTable.getTableHeader().setReorderingAllowed(false);
        stakeholdersTable.getTableHeader().setResizingAllowed(true);
        
        for(Stakeholder stakeholder : workflow.getStakeholders()){
        	Accreditation acc = workflow.getAccreditation(stakeholder);
        	Object[] row = {stakeholder.getId(), acc.getReadable().toString(), acc.getWritable().toString(), acc.getExecutable().toString()};
            ((TableModel)stakeholdersTable.getModel()).addRow(row);
        }
        
        this.mainPanel.add(stakeholdersTable.getTableHeader(), BorderLayout.NORTH);
        this.mainPanel.add(new JScrollPane(stakeholdersTable));
        
        this.add(mainPanel);
	}

	private void initMenus() {

	}

	private void initToolBar() {
		this.toolBar = new JToolBar();
		toolBar.setPreferredSize(new Dimension(20, 20));
		toolBar.setFloatable(false);
        
		this.titleLabel = new JLabel();
        this.titleLabel.setText(ModelModel.getConfig().getLangValue("wokflow_stakeholder"));
        titleLabel.setFont(new Font("Cambria", Font.BOLD, 12));
        
        ImageIcon icon = new ImageIcon(getClass().getResource("/smartworkflow/dwfms/urifia/fmml/miu/resources/images/email_small.png"));
        titleLabel.setIcon(icon);
        titleLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        
        this.toolBar.add(this.titleLabel);
        
        this.add(this.toolBar, BorderLayout.NORTH);
	}
}
