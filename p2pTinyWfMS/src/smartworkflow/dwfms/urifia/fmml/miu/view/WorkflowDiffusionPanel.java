package smartworkflow.dwfms.urifia.fmml.miu.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import smartworkflow.dwfms.urifia.fmml.miu.controllers.util.ControllerModel;
import smartworkflow.dwfms.urifia.fmml.miu.models.util.ModelModel;
import smartworkflow.dwfms.urifia.fmml.miu.util.observer.adapters.ObserverAdapter;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.Stakeholder;
import smartworkflow.dwfms.urifia.fmml.miu.view.util.PanelModel;

public class WorkflowDiffusionPanel extends PanelModel {
	private static final long serialVersionUID = 1L;
	private JToolBar toolBar;
	private JLabel titleLabel;
	private JList<String> diffusionRecapJList;
	private Updater updater;
	
	public WorkflowDiffusionPanel(ControllerModel controller) {
        super(controller);
        updater = new Updater();
        this.addUpdater(updater);
        
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
        this.diffusionRecapJList = new JList<String>();
        diffusionRecapJList.setFont(ModelModel.getConfig().getTheme().getAreasFont());
        diffusionRecapJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        diffusionRecapJList.setVisibleRowCount(5);
        diffusionRecapJList.setPreferredSize(new Dimension(150, 25));
        diffusionRecapJList.setEnabled(false);
        this.add(new JScrollPane(diffusionRecapJList));
	}

	private void initMenus() {
		
	}
	
	private void updatePeers(ArrayList<Stakeholder> peers, String type) {
		int ind[] = new int[1];
		ind[0] = 0;
		diffusionRecapJList.removeAll();
        Vector<String> l = new Vector<String>();
        String pref = type.equals("request") ? "Request sent to " : "Answer sent to ";
		for(Stakeholder st : peers){
			l.add(pref + " " + st.getId());
		}
		diffusionRecapJList.setListData(l);
		
		diffusionRecapJList.validate();
		diffusionRecapJList.updateUI();
		
		validate();
		updateUI();
	}
	
	private void initToolBar() {
		this.toolBar = new JToolBar();
		toolBar.setPreferredSize(new Dimension(20, 20));
		toolBar.setFloatable(false);
        
		this.titleLabel = new JLabel();
        this.titleLabel.setText("Diffusion");
        titleLabel.setFont(new Font("Cambria", Font.BOLD, 12));
        
        ImageIcon icon = new ImageIcon(getClass().getResource("/smartworkflow/dwfms/urifia/fmml/miu/resources/images/help_small.png"));
        titleLabel.setIcon(icon);
        titleLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        
        this.toolBar.add(this.titleLabel);
        
        this.add(this.toolBar, BorderLayout.NORTH);
	}

	public class Updater extends ObserverAdapter{
		@Override
		public void updateAnsweredPeers(ArrayList<Stakeholder> peers) {
			updatePeers(peers, "response");
		}
		
		@Override
		public void updateEndOfProcess() {
			updatePeers(new ArrayList<Stakeholder>(), "request");
			ModelModel.displayMessageDialog(ModelModel.getConfig().getLangValue("process_ended"), ModelModel.getConfig().getLangValue("process_ended_message"));
		}
		
		@Override
		public void updateRequiredPeers(ArrayList<Stakeholder> peers) {
			updatePeers(peers, "request");
		}
	}
}
