package smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow;

import java.io.Serializable;
import java.util.ArrayList;

import com.sun.tools.javac.util.Pair;

public class PeerToPeerWorkflowArtefactCursor implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static final int 
			LTR_PREFIX_ORDER = 0,
			RTL_PREFIX_ORDER = 1,
			LTR_INFIX_ORDER = 2,
			RTL_INFIX_ORDER = 3,
			LTR_POSTFIX_ORDER = 4,
			RTL_POSTFIX_ORDER = 5,
			LTR_TTD_LARG_ORDER = 6,
			RTL_TTD_LARG_ORDER = 7,
			LTR_DTT_LARG_ORDER = 8,
			RTL_DTT_LARG_ORDER = 9;
			
	
	private PeerToPeerWorkflowArtefact artefact;
	private ArrayList<Pair<String, PeerToPeerWorkflowArtefact>> iterationList;
	private int cursor;
	
	public PeerToPeerWorkflowArtefactCursor(PeerToPeerWorkflowArtefact artefact, int order) {
		this.artefact = artefact;
		buildIterationList(order);
		resetCursor();
	}

	private void buildIterationList(int order) {
		switch(order){
			case LTR_PREFIX_ORDER :
				ltrPrefixOrder();
				break;
			case RTL_PREFIX_ORDER :
				rtlPrefixOrder();
				break;
			case LTR_INFIX_ORDER :
				ltrInfixOrder();
				break;
			case RTL_INFIX_ORDER :
				rtlInfixOrder();
				break;
			case LTR_POSTFIX_ORDER :
				ltrPostfixOrder();
				break;
			case RTL_POSTFIX_ORDER :
				rtlPostfixOrder();
				break;
			case LTR_TTD_LARG_ORDER :
				ltrTtdLargOrder();
				break;
			case RTL_TTD_LARG_ORDER :
				rtlTtdLargOrder();
				break;
			case LTR_DTT_LARG_ORDER :
				ltrDttLargOrder();
				break;
			case RTL_DTT_LARG_ORDER :
				rtlDttLargOrder();
				break;
			default :
				iterationList = new ArrayList<Pair<String, PeerToPeerWorkflowArtefact>>();
		}
	}

	private void rtlDttLargOrder() {
		iterationList = new ArrayList<Pair<String, PeerToPeerWorkflowArtefact>>();
	}

	private void ltrDttLargOrder() {
		iterationList = new ArrayList<Pair<String, PeerToPeerWorkflowArtefact>>();
	}

	private void rtlTtdLargOrder() {
		iterationList = new ArrayList<Pair<String, PeerToPeerWorkflowArtefact>>();
	}

	private void ltrTtdLargOrder() {
		iterationList = new ArrayList<Pair<String, PeerToPeerWorkflowArtefact>>();
	}

	private void rtlPostfixOrder() {
		iterationList = new ArrayList<Pair<String, PeerToPeerWorkflowArtefact>>();
	}

	private void ltrPostfixOrder() {
		iterationList = new ArrayList<Pair<String, PeerToPeerWorkflowArtefact>>();
	}

	private void rtlInfixOrder() {
		iterationList = new ArrayList<Pair<String, PeerToPeerWorkflowArtefact>>();
	}

	private void ltrInfixOrder() {
		iterationList = new ArrayList<Pair<String, PeerToPeerWorkflowArtefact>>();
	}

	private void rtlPrefixOrder() {
		iterationList = new ArrayList<Pair<String, PeerToPeerWorkflowArtefact>>();
	}

	private void ltrPrefixOrder() {
		iterationList = new ArrayList<Pair<String, PeerToPeerWorkflowArtefact>>();
		if(artefact != null){
			Pair<String, PeerToPeerWorkflowArtefact> pair = new Pair<String, PeerToPeerWorkflowArtefact>("", artefact);
			iterationList.add(pair);
			for(int i = 0; i < artefact.getSons().size(); i++)
				iterationList.addAll(ltrPrefixOrder(artefact.getSons().get(i), "", i + 1));
		}
	}
	
	private ArrayList<Pair<String, PeerToPeerWorkflowArtefact>> ltrPrefixOrder(PeerToPeerWorkflowArtefact art, String pAddr, int sonId) {
		ArrayList<Pair<String, PeerToPeerWorkflowArtefact>> list = new ArrayList<Pair<String, PeerToPeerWorkflowArtefact>>();
		String myAddr = pAddr != null && !pAddr.isEmpty() ? pAddr + "." + sonId : "" + sonId;
		Pair<String, PeerToPeerWorkflowArtefact> pair = new Pair<String, PeerToPeerWorkflowArtefact>(myAddr, art);
		list.add(pair);
		for(int i = 0; i < art.getSons().size(); i++)
			list.addAll(ltrPrefixOrder(art.getSons().get(i), myAddr, i + 1));
		return list;
	}
	
	public void resetCursor(){
		cursor = 0;
	}
	
	public void backCursor(){
		if(cursor > 0)
			cursor--;
	}
	
	public boolean hasNext(){
		return cursor < iterationList.size();
	}
	
	public Pair<String, PeerToPeerWorkflowArtefact> next(){
		return hasNext() ? iterationList.get(cursor++) : null;
	}
	
	public void changeOrder(int order){
		buildIterationList(order);
		resetCursor();
	}
}
