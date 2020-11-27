package smartworkflow.dwfms.urifia.fmml.miu.util.graphview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.Parsers;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.PeerToPeerWorkflowArtefact;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.Production;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.util.mxSwingConstants;
import com.mxgraph.util.mxConstants;

public class GraphView {

	private PeerToPeerWorkflowArtefact artefact;
	private CustomGraph graph;
	private CustomGraphComponent graphComponent;
	private JPanel panel;

	public static String LOCKED_BUD_STYLE=mxConstants.STYLE_FILLCOLOR + "=#D40773;";
	public static String BUD_STYLE=mxConstants.STYLE_FILLCOLOR + "=#5450F8;";
	public static String CLOSED_STYLE=mxConstants.STYLE_FILLCOLOR + "=#ffffff;";
	public static String STRUCTURED_EDGE_STYLE=mxConstants.STYLE_STROKECOLOR+"="+"#ffffff;";
	public static String STRUCTURED_NODE_STYLE=mxConstants.STYLE_FILLCOLOR + "=#ffffff;"+mxConstants.STYLE_STROKECOLOR+"="+"#ffffff;";
	public void buildView() {
		panel.setLayout(new BorderLayout());
		panel.removeAll();
		mxSwingConstants.SHADOW_COLOR = Color.LIGHT_GRAY;
		mxConstants.W3C_SHADOWCOLOR = "#D3D3D3";
		graph = new CustomGraph();
		Object parent = graph.getDefaultParent();

		graph.getModel().beginUpdate();
		
		mxHierarchicalLayout layoutForParent = new mxHierarchicalLayout(graph);
		//mxCompactTreeLayout layoutForParent = new mxCompactTreeLayout(graph, false);
		//layoutForParent.setLevelDistance(20);
		//layoutForParent.setNodeDistance(10);
		layoutForParent.setInterRankCellSpacing(25);
		
		try {
			Object mxNode = graph.insertVertex(parent, null, artefact, 100, 100,getMxGraphNodeLengthStyle(artefact),30,getMxGraphNodeStyle(artefact));
			proceedArtefact(artefact, mxNode, parent);
			layoutForParent.execute(parent);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			graph.getModel().endUpdate();
		}
		
		graphComponent = new CustomGraphComponent(graph);
        graphComponent.setBorder(null);
        //graphComponent.setAutoScroll(false);

        graphComponent.refresh();
     
        panel.add(graphComponent);
        panel.updateUI();
        
        panel.validate();
        panel.repaint();
	        
	}
	
	public String getMxGraphNodeStyle(PeerToPeerWorkflowArtefact artefact){
		String baseStyle=mxConstants.STYLE_SHAPE + "="+mxConstants.SHAPE_ELLIPSE+";";
		String fontColorStyle=mxConstants.STYLE_FONTCOLOR+"=#FFFFFF;";
		if(artefact.isLocked()){
			return baseStyle+LOCKED_BUD_STYLE+fontColorStyle;
		}else if(artefact.isUnlocked()){
			return baseStyle+BUD_STYLE+fontColorStyle;
		}
		return baseStyle+CLOSED_STYLE;
	}
	
	public int getMxGraphNodeLengthStyle(PeerToPeerWorkflowArtefact artefact){
		int fact=30;
		int val=(artefact.getTask().getSymbol().length())*fact;
		if(artefact.isLocked()){
			val= (artefact.getTask().getSymbol().length()+2)*fact;
		}else if(artefact.isUnlocked()){
			val= (artefact.getTask().getSymbol().length()+1)*fact;
		}
		// a static value for simple test this line should be remove
		val=30;
		return val ;
	}
	
	public void proceedArtefact(PeerToPeerWorkflowArtefact artefact, Object mxNode, Object parent){
		
		Production production = artefact.getProduction();
		String motif=";";
		if(production.isParallelProduction()){
			motif="||";
		}
		ArrayList<PeerToPeerWorkflowArtefact> sons = artefact.getSons();
		if(sons!=null){
			if(sons.size()>=1){
				Object sonNode = graph.insertVertex(parent, null, sons.get(0), 100, 100, getMxGraphNodeLengthStyle(sons.get(0)), 30,getMxGraphNodeStyle(sons.get(0)));
				graph.insertEdge(parent, null, "", mxNode, sonNode);
				proceedArtefact(sons.get(0), sonNode, parent);
				if(sons.size()>1){
					for(int i=1;i<sons.size();i++){
						Object strNode = graph.insertVertex(parent, null, motif, 100, 100, 10, 10,STRUCTURED_NODE_STYLE);
						graph.insertEdge(parent, null, "", mxNode, strNode,STRUCTURED_EDGE_STYLE);
						
						//Real Node
						Object otherSonNode = graph.insertVertex(parent, null, sons.get(i), 100, 100, getMxGraphNodeLengthStyle(sons.get(i)),30 ,getMxGraphNodeStyle(sons.get(i)));
						graph.insertEdge(parent, null, "", mxNode, otherSonNode);
						proceedArtefact(sons.get(i), otherSonNode, parent);
					}
				}
			}
		}
	}
	
	public static void main(String Args[]){
		
		//BasicFrame frame= new BasicFrame();

		//frame.setVisible(true);
		PeerToPeerWorkflowArtefact artefact = Parsers.getPrunedArtefactLockedBud();
		//PeerToPeerWorkflowArtefact artefact = Parsers.getPrunedArtefactUnlockedBud();
		GraphView gView= new GraphView();
		//gView.setPanel(frame.getMainPanel());
		gView.setArtefact(artefact);
		//gView.buildView();
		JFrame frame = new JFrame();
		frame.setBounds(new Rectangle(100, 100, 500, 500));
		JPanel panelV= new JPanel();
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(panelV);
		gView.setPanel(panelV);
		frame.setVisible(true);
		gView.buildView();
		
	}

	public PeerToPeerWorkflowArtefact getArtefact() {
		return artefact;
	}

	public void setArtefact(PeerToPeerWorkflowArtefact artefact) {
		this.artefact = artefact;
	}

	public CustomGraph getGraph() {
		return graph;
	}

	public void setGraph(CustomGraph graph) {
		this.graph = graph;
	}

	public CustomGraphComponent getGraphComponent() {
		return graphComponent;
	}

	public void setGraphComponent(CustomGraphComponent graphComponent) {
		this.graphComponent = graphComponent;
	}

	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}
	
	
}
