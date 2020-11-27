package smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow;

import java.io.Serializable;
import java.util.ArrayList;

import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.verification.PrecedenceRule;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.verification.PrecedenceRuleRhs;

public class Production implements Serializable {
	private static final long serialVersionUID = 1L;

	private String prodName;
	private String prodLhs;
	private ProductionRhs prodRhs;

	public Production() {

	}

	public Production(String prodName, String prodLhs, ProductionRhs prodRhs) {
		this.prodName = prodName;
		this.prodLhs = prodLhs;
		this.prodRhs = prodRhs;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getProdLhs() {
		return prodLhs;
	}

	public void setProdLhs(String prodLhs) {
		this.prodLhs = prodLhs;
	}

	public ProductionRhs getProdRhs() {
		return prodRhs;
	}

	public void setProdRhs(ProductionRhs prodRhs) {
		this.prodRhs = prodRhs;
	}

	public boolean isSequentialProduction() {
		return prodRhs.isSequentialProduction();
	}

	public boolean isParallelProduction() {
		return prodRhs.isParallelProduction();
	}

	public boolean isNoneProduction() {
		return prodRhs.isNoneProduction();
	}

	public boolean isEpsilonProduction() {
		return prodRhs.isEpsilonProduction();
	}

	public String getProdType() {
		return prodRhs.getRhsType();
	}

	public ArrayList<PrecedenceRule> getAssocPrecRules() {
		ArrayList<PrecedenceRule> assocPrecRules = null;
		ArrayList<String> rhsSymbols;
		if (this.isEpsilonProduction()) {
			assocPrecRules = new ArrayList<PrecedenceRule>();
		} else if (this.isNoneProduction() || this.isSequentialProduction()) {
			assocPrecRules = new ArrayList<PrecedenceRule>();
			PrecedenceRule precRule = new PrecedenceRule(this.prodLhs,
					new PrecedenceRuleRhs(this.prodRhs.getRhsSymbols()));
			assocPrecRules.add(precRule);

		} else if (this.prodRhs.getRhsSymbols() != null) {
			assocPrecRules = new ArrayList<PrecedenceRule>();
			for (String symbol : this.prodRhs.getRhsSymbols()) {
				rhsSymbols = new ArrayList<String>();
				rhsSymbols.add(symbol);
				assocPrecRules.add(new PrecedenceRule(this.prodLhs, new PrecedenceRuleRhs(rhsSymbols)));
			}
		}
		return assocPrecRules;
	}

	public boolean equalsWithoutName(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Production other = (Production) obj;
		if (prodLhs == null) {
			if (other.prodLhs != null)
				return false;
		} else if (!prodLhs.equals(other.prodLhs))
			return false;
		if (prodRhs == null) {
			if (other.prodRhs != null)
				return false;
		} else if (!prodRhs.equals(other.prodRhs))
			return false;
		return true;
	}

	public void getAssociatePrecedenceRules() {

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((prodLhs == null) ? 0 : prodLhs.hashCode());
		result = prime * result
				+ ((prodName == null) ? 0 : prodName.hashCode());
		result = prime * result + ((prodRhs == null) ? 0 : prodRhs.hashCode());
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
		Production other = (Production) obj;
		if (prodLhs == null) {
			if (other.prodLhs != null)
				return false;
		} else if (!prodLhs.equals(other.prodLhs))
			return false;
		if (prodName == null) {
			if (other.prodName != null)
				return false;
		} else if (!prodName.equals(other.prodName))
			return false;
		if (prodRhs == null) {
			if (other.prodRhs != null)
				return false;
		} else if (!prodRhs.equals(other.prodRhs))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return prodName + ":" + prodLhs + " ---> " + prodRhs;
	}

}