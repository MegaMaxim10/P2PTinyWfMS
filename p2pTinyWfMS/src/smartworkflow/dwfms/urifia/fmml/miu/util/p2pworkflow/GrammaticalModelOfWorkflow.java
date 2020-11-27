package smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow;

import java.io.Serializable;
import java.util.ArrayList;

public class GrammaticalModelOfWorkflow  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static final String 
			PRODUCTION_TYPE_SEQ = "Sequential",
			PRODUCTION_TYPE_PAR = "Parallel",
			PRODUCTION_TYPE_NONE = "None",
			PRODUCTION_TYPE_EPS = "Epsilon";
			
	
	private ArrayList<Task> tasks;
    private String axiom;
    private ArrayList<Production> productions;

    public GrammaticalModelOfWorkflow(){
        tasks = new ArrayList<Task>();
        productions = new ArrayList<Production>();
    }

    public GrammaticalModelOfWorkflow(ArrayList<Task> symbols, ArrayList<Production> productions) {
        this.tasks = symbols;
        this.productions = productions;
    }
    
    public ArrayList<Production> getProductions() {
        return productions;
    }
    
    public ArrayList<Production> getXProductions(String symb) {
    	ArrayList<Production> list = new ArrayList<Production>();
    	for(Production prod : productions)
    		if(prod.getProdLhs().equals(symb))
    			list.add(prod);
        return list;
    }

    public void setProductions(ArrayList<Production> productions) {
        this.productions = productions;
    }

    public ArrayList<String> getSymbols() {
    	ArrayList<String> list = null;
    	if(tasks != null){
    		list = new ArrayList<String>();
    		for(Task task : tasks){
    			if(!list.contains(task.getSymbol()))
    				list.add(task.getSymbol());
    		}
    	}
        return list;
    }

    public void setSymbols(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }
    
    public ArrayList<PeerToPeerWorkflowArtefact> getArtefacts(){
    	return getArtefacts(axiom);
    }
    
    public ArrayList<PeerToPeerWorkflowArtefact> getArtefacts(String symb){
    	ArrayList<PeerToPeerWorkflowArtefact> artefacts = new ArrayList<PeerToPeerWorkflowArtefact>();
    	ArrayList<Production> xprods = getXProductions(getTask(symb).getSymbol());
    	for(Production xprod : xprods){
    		if(xprod.isEpsilonProduction()){
    			PeerToPeerWorkflowArtefact artefact = new PeerToPeerWorkflowArtefact(getTask(symb));
    			artefact.setProduction(xprod);
    			artefacts.add(artefact);
    		} else {
	    		ArrayList<ArrayList<PeerToPeerWorkflowArtefact>> sonsList = new ArrayList<ArrayList<PeerToPeerWorkflowArtefact>>();
	    		for(String sy : xprod.getProdRhs().getRhsSymbols()){
	    			sonsList.add(getArtefacts(sy));
	    		}
	    		ArrayList<ArrayList<PeerToPeerWorkflowArtefact>> cartList = new ArrayList<ArrayList<PeerToPeerWorkflowArtefact>>();
	    		for(int i = 0; i < sonsList.get(0).size(); i++){
	    			ArrayList<PeerToPeerWorkflowArtefact> l = new ArrayList<PeerToPeerWorkflowArtefact>();
	    			l.add(sonsList.get(0).get(i));
	    			cartList.add(l);
	    		}
	    		for(int i = 1; i < sonsList.size(); i++){
	    			cartList = mergeList(cartList, sonsList.get(i));
	    		}
	    		
	    		for(ArrayList<PeerToPeerWorkflowArtefact> sons : cartList){
	    			PeerToPeerWorkflowArtefact artefact = new PeerToPeerWorkflowArtefact(getTask(symb));
	    			artefact.setSons(sons);
	    			artefact.setProduction(xprod);
	    			artefacts.add(artefact);
	    		}
    		}
    	}
    	return artefacts;
    }
    
    private ArrayList<ArrayList<PeerToPeerWorkflowArtefact>> mergeList(
			ArrayList<ArrayList<PeerToPeerWorkflowArtefact>> cartList,
			ArrayList<PeerToPeerWorkflowArtefact> list) {
    	ArrayList<ArrayList<PeerToPeerWorkflowArtefact>> listToReturn = new ArrayList<ArrayList<PeerToPeerWorkflowArtefact>>();
    	for(int i = 0; i < cartList.size(); i++){
    		for(int j = 0; j < list.size(); j++){
    			ArrayList<PeerToPeerWorkflowArtefact> l = new ArrayList<PeerToPeerWorkflowArtefact>();
    			l.addAll(cartList.get(i));
    			l.add(list.get(j));
    			listToReturn.add(l);
        	}
    	}
		return listToReturn;
	}

	public Production getProduction(String prodKey){
        for(Production p : productions)
        	if(p.getProdName().equals(prodKey))
        		return p;
        return null;
    }

    public String getAxiom() {
        return this.axiom;
    }

    public void setAxiom(String axiom) {
    	if(isSymbol(axiom))
    		this.axiom = axiom;
    }
    
    public String lhs(String prodKey){
    	Production p = getProduction(prodKey);
        return (p != null) ? p.getProdLhs() : null;
    }
    
    public ProductionRhs rhs(String prodKey){
    	Production p = getProduction(prodKey);
    	return (p != null) ? p.getProdRhs() : null;
    }
    
    public String getProductionType(String prodKey){
    	Production p = getProduction(prodKey);
    	return (p != null) ? p.getProdRhs().getRhsType() : null;
    }
    
    public boolean isSequentialProduction(String prodKey){
    	return getProduction(prodKey).isSequentialProduction();
    }
    
    public boolean isParallelProduction(String prodKey){
    	return getProduction(prodKey).isParallelProduction();
    }
    
    public boolean isEpsilonProduction(String prodKey){
    	String type = getProductionType(prodKey);
    	return type.equalsIgnoreCase(PRODUCTION_TYPE_EPS);
    }
    
    public ArrayList<String> rhsSymbols(String prodKey){
    	ProductionRhs rhs = rhs(prodKey);
    	ArrayList<String> list = null;
    	if(rhs != null){
    		list = new ArrayList<String>();
    		for(int i = 1; i < rhs.getRhsSymbols().size(); i++)
                list.add(rhs.getRhsSymbols().get(i));
    	}
    	return list;
    }
    
    public int rhsLength(String prodKey){
    	ProductionRhs rhs = rhs(prodKey);
        return (rhs != null) ? rhs.getRhsSymbols().size() : 0;
    }
    
    public Task getTask(String symb){
    	for(Task task : tasks)
    		if(task.getSymbol().equals(symb))
    			return task;
    	return null;
    }
    
    public void addClassicTask(String symb, String desc) throws Exception{
        if(getTask(symb) == null){
        	Task task = new Task(symb, Task.TASK_TYPE_CLASSIC, desc);
        	tasks.add(task);
        }
        else
        	throw new Exception();
    }
    
    public void addTask(Task task) throws Exception{
        if(!tasks.contains(task))
        	tasks.add(task);
        else
        	throw new Exception();
    }
    
    public void addVirtualTask(String symb, String desc) throws Exception{
        if(getTask(symb) == null){
        	Task task = new Task(symb,Task.TASK_TYPE_VIRTUAL, desc);
        	tasks.add(task);
        }
        else
        	throw new Exception();
    }
    
    public void addProduction(String name, String type, String lhs, ArrayList<String> rhs) throws Exception{
        Production p = new Production();
        p.setProdName(name);
        p.setProdLhs(lhs);
        ProductionRhs prhs = new ProductionRhs(type, rhs);
        p.setProdRhs(prhs);
        addProduction(p);
    }
    
    public void addProduction(Production prod) throws Exception{
        boolean strangeTask = getTask(prod.getProdLhs()) == null;
        for(String s : prod.getProdRhs().getRhsSymbols()){
            if(getTask(s) == null){
                strangeTask = true;
                break;
            }
        }
        if(!strangeTask && !productions.contains(prod))
            productions.add(prod);
        else
            throw new Exception();
    }
    
	@Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GrammaticalModelOfWorkflow other = (GrammaticalModelOfWorkflow) obj;
        if(!axiom.equals(other.axiom))
            return false;
        
        if((tasks == null && other.tasks != null) || (tasks != null && other.tasks == null) || 
                (tasks != null && other.tasks != null && tasks.size() != other.tasks.size()))
            return false;
        
        for(Task task : tasks)
            if(!other.tasks.contains(task))
                return false;
        
        if((productions == null && other.productions != null) || (productions != null && other.productions == null) || 
                (productions != null && other.productions != null && productions.size() != other.productions.size()))
            return false;
        
        for(Production prod : productions)
            if(!other.productions.contains(prod))
                return false;
        
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + (this.tasks != null ? this.tasks.hashCode() : 0);
        hash = 23 * hash + (this.axiom != null ? this.axiom.hashCode() : 0);
        hash = 23 * hash + (this.productions != null ? this.productions.hashCode() : 0);
        return hash;
    }
    
    public String getGrammar(){
        String gram = "";
        for(String sy : getSymbolsAxiomTop()){
            for(Production prod : productions){
                if(prod.getProdLhs().equals(sy)){
                    gram += prod.getProdLhs()+" ->";
                    if(prod.getProdRhs().getRhsSymbols().size() == 0)
                        gram += " £\n";
                    else{
                        ArrayList<String> rh = prod.getProdRhs().getRhsSymbols();
                        String delimiter = prod.getProdRhs().getRhsType().equalsIgnoreCase(PRODUCTION_TYPE_SEQ) ? ";" : "||";
                        int i = 0;
                        for(i = 0; i < rh.size() - 1; i++){
                            gram += " " + rh.get(i) + " " + delimiter;
                        }
                        gram += " " + rh.get(i) + "\n";
                    }
                }
            }
        }
        return gram.substring(0, gram.length() - 1);
    }
    
    public ArrayList<String> getSymbolsAxiomTop() {
        ArrayList<String> list = new ArrayList<String>();
        list.add(axiom);
        for(Task task : tasks){
            if(!task.getSymbol().equals(axiom))
                list.add(task.getSymbol());
        }
        return list;
    }
    
    public boolean isSymbol(String symb){
        return getTask(symb) != null;
    }
    
    public boolean isProduction(String prod){
        return getProduction(prod) != null;
    }
    
    public boolean isProduction(Production prod){
        return productions.contains(prod);
    }
    
    public String getProduction(Production prod){
        for(Production p : productions)
            if(p.equalsWithoutName(prod))
                return p.getProdName();
        return null;
    }
    
    public GrammaticalModelOfWorkflow getGrammarForExecution() throws Exception{
    	GrammaticalModelOfWorkflow grammarForExec = new GrammaticalModelOfWorkflow();
    	for(Task task : tasks)
    		grammarForExec.addTask(task);
    	grammarForExec.setAxiom(axiom);
    	
    	for(Production prod : productions)
    		grammarForExec.addProduction(prod);
		
    	int i = 1;
    	for(String symb : getSymbols()){
			Production prod = new Production("P" + (productions.size() + i), symb, new ProductionRhs(GrammaticalModelOfWorkflow.PRODUCTION_TYPE_EPS, new ArrayList<String>()));
			boolean contains = false;
			for(Production p : productions){
				if(p.equalsWithoutName(prod)){
					contains = true;
					break;
				}
			}
			if(!contains){
				grammarForExec.addProduction(prod);
				i++;
			}
		}
    	return grammarForExec;
    }
}