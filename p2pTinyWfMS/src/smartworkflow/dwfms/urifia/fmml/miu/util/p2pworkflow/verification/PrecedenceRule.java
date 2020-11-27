package smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.verification;

import java.io.Serializable;

public class PrecedenceRule implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ruleLhs;
	private PrecedenceRuleRhs ruleRhs;
	
	
	public PrecedenceRule() {
		
	}

	public PrecedenceRule(String ruleLhs, PrecedenceRuleRhs ruleRhs) {
		super();
		this.ruleLhs = ruleLhs;
		this.ruleRhs = ruleRhs;
	}

	public String getRuleLhs() {
		return ruleLhs;
	}

	public void setRuleLhs(String ruleLhs) {
		this.ruleLhs = ruleLhs;
	}

	public PrecedenceRuleRhs getRuleRhs() {
		return ruleRhs;
	}

	public void setRuleRhs(PrecedenceRuleRhs ruleRhs) {
		this.ruleRhs = ruleRhs;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		return ruleLhs + " ==> " + ruleRhs;
	}
	
}
