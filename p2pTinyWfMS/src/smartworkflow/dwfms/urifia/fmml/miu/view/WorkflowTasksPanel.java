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
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.GrammaticalModelOfWorkflow;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.PeerToPeerWorkflow;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.Task;
import smartworkflow.dwfms.urifia.fmml.miu.view.util.PanelModel;
import smartworkflow.dwfms.urifia.fmml.miu.view.util.TableModel;

public class WorkflowTasksPanel extends PanelModel {
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private JToolBar toolBar;
	private JTable tasksTable;
	private JLabel titleLabel;
	private PeerToPeerWorkflow workflow;
	
	public WorkflowTasksPanel(ControllerModel controller, PeerToPeerWorkflow workflow) {
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
        
        String[] titles = {ModelModel.getConfig().getLangValue("description"), ModelModel.getConfig().getLangValue("symbol")};
        tasksTable = new JTable(new TableModel(titles));
        tasksTable.setRowHeight(20);
        tasksTable.setSelectionBackground(ModelModel.getConfig().getTheme().getBgColor());
        tasksTable.setSelectionForeground(Color.WHITE);
        tasksTable.setGridColor(ModelModel.getConfig().getTheme().getBgColor());
        tasksTable.setFont(ModelModel.getConfig().getTheme().getAreasFont());
        tasksTable.setOpaque(false);
        tasksTable.getTableHeader().setReorderingAllowed(false);
        tasksTable.getTableHeader().setResizingAllowed(true);
        
        GrammaticalModelOfWorkflow gmwf = workflow.getGrammaticalModelOfWorkflow();
        for(String symb : gmwf.getSymbols()){
        	if(!gmwf.getTask(symb).getType().equals(Task.TASK_TYPE_VIRTUAL)){
	        	Object[] row = {gmwf.getTask(symb).getDesc(), symb};
	            ((TableModel)tasksTable.getModel()).addRow(row);
        	}
        }
        
        this.mainPanel.add(tasksTable.getTableHeader(), BorderLayout.NORTH);
        this.mainPanel.add(new JScrollPane(tasksTable));
        
        this.add(mainPanel);
	}

	private void initMenus() {
		
	}

	private void initToolBar() {
		this.toolBar = new JToolBar();
		toolBar.setPreferredSize(new Dimension(20, 20));
		toolBar.setFloatable(false);
        
		this.titleLabel = new JLabel();
        this.titleLabel.setText(ModelModel.getConfig().getLangValue("wokflow_tasks"));
        titleLabel.setFont(new Font("Cambria", Font.BOLD, 12));
        
        ImageIcon icon = new ImageIcon(getClass().getResource("/smartworkflow/dwfms/urifia/fmml/miu/resources/images/connect_small.png"));
        titleLabel.setIcon(icon);
        titleLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        
        this.toolBar.add(this.titleLabel);
        
        this.add(this.toolBar, BorderLayout.NORTH);
	}
}
