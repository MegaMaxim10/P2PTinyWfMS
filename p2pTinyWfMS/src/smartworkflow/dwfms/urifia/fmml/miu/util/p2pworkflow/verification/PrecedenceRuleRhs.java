package smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.verification;

import java.io.Serializable;
import java.util.ArrayList;

public class PrecedenceRuleRhs  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<String> rhsSymbols;

	public PrecedenceRuleRhs() {
		
	}

	public PrecedenceRuleRhs(ArrayList<String> rhsSymbols) {
		super();
		this.rhsSymbols = rhsSymbols;
	}

	public ArrayList<String> getRhsSymbols() {
		return rhsSymbols;
	}

	public void setRhsSymbols(ArrayList<String> rhsSymbols) {
		this.rhsSymbols = rhsSymbols;
	}

	@Override
	public String toString() {
		String rhs = "";
		
		for (String symbol : rhsSymbols) {
			rhs = rhs + symbol;
		}
		return  rhs;
	}
	
}
