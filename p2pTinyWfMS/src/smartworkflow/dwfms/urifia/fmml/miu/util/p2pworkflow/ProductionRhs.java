package smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductionRhs  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String rhsType;
	private ArrayList<String> rhsSymbols;

	public ProductionRhs(String rhsType, ArrayList<String> rhsSymbols) {
		this.rhsType = rhsType;
		this.rhsSymbols = rhsSymbols;
	}

	public String getRhsType() {
		return rhsType;
	}

	public void setRhsType(String rhsType) {
		this.rhsType = rhsType;
	}

	public ArrayList<String> getRhsSymbols() {
		return rhsSymbols;
	}

	public void setRhsSymbols(ArrayList<String> rhsSymbols) {
		this.rhsSymbols = rhsSymbols;
	}
	
	public boolean isSequentialProduction(){
    	return rhsType.equalsIgnoreCase(GrammaticalModelOfWorkflow.PRODUCTION_TYPE_SEQ);
    }
    
    public boolean isParallelProduction(){
    	return rhsType.equalsIgnoreCase(GrammaticalModelOfWorkflow.PRODUCTION_TYPE_PAR);
    }
    
    public boolean isNoneProduction(){
    	return rhsType.equalsIgnoreCase(GrammaticalModelOfWorkflow.PRODUCTION_TYPE_NONE);
    }
    
    public boolean isEpsilonProduction(){
    	return rhsType.equalsIgnoreCase(GrammaticalModelOfWorkflow.PRODUCTION_TYPE_EPS);
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((rhsSymbols == null) ? 0 : rhsSymbols.hashCode());
		result = prime * result
				+ ((rhsType == null) ? 0 : rhsType.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductionRhs other = (ProductionRhs) obj;
		if (rhsType == null) {
			if (other.rhsType != null)
				return false;
		} else if (!rhsType.equals(other.rhsType))
			return false;
		if((rhsSymbols == null && other.rhsSymbols != null) || (rhsSymbols != null && other.rhsSymbols == null) || 
                (rhsSymbols != null && other.rhsSymbols != null && rhsSymbols.size() != other.rhsSymbols.size()))
            return false;
		if(isSequentialProduction()){
			for(int i = 0; i < rhsSymbols.size(); i++)
				if(!rhsSymbols.get(i).equals(other.rhsSymbols.get(i)))
					return false;
			return true;
		}
        for(String symb : rhsSymbols)
            if(!other.rhsSymbols.contains(symb))
                return false;
		return true;
	}

	@Override
	public String toString() {
		if(isEpsilonProduction())
			return "£";
		if(isNoneProduction())
			return rhsSymbols.get(0);
		String sep = isSequentialProduction() ? ";" : "||", rhs = "";
		for(String symb : rhsSymbols)
			rhs += symb + " " + sep + " ";
		rhs = rhs.substring(0, rhs.length() - (" " + sep + " ").length());
		return rhs;
	}
}