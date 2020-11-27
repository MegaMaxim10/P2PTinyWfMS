/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow;

import java.util.ArrayList;
import java.util.Arrays;

import smartworkflow.dwfms.urifia.fmml.miu.models.util.ModelModel;


/**
 *
 * @author Ndadji Maxime
 */
public class Parsers {
    public static PeerToPeerWorkflow getPeerReviewWorkflow(){
    	PeerToPeerWorkflow p2pWorkflow = new PeerToPeerWorkflow();
    	GrammaticalModelOfWorkflow gmwf = p2pWorkflow.getGrammaticalModelOfWorkflow();
    	try {
			gmwf.addVirtualTask("Ag", ModelModel.getConfig().getLangValue("axiom"));
			gmwf.addClassicTask("A", ModelModel.getConfig().getLangValue("peer_review_task_a"));
			gmwf.addClassicTask("B", ModelModel.getConfig().getLangValue("peer_review_task_b"));
			gmwf.addClassicTask("C", ModelModel.getConfig().getLangValue("peer_review_task_c"));
			gmwf.addClassicTask("D", ModelModel.getConfig().getLangValue("peer_review_task_d"));
			gmwf.addClassicTask("E", ModelModel.getConfig().getLangValue("peer_review_task_e"));
			gmwf.addClassicTask("F", ModelModel.getConfig().getLangValue("peer_review_task_f"));
			gmwf.addClassicTask("G1", ModelModel.getConfig().getLangValue("peer_review_task_g1"));
			gmwf.addClassicTask("G2", ModelModel.getConfig().getLangValue("peer_review_task_g2"));
			gmwf.addClassicTask("H1", ModelModel.getConfig().getLangValue("peer_review_task_h1"));
			gmwf.addClassicTask("H2", ModelModel.getConfig().getLangValue("peer_review_task_h2"));
			gmwf.addClassicTask("I1", ModelModel.getConfig().getLangValue("peer_review_task_i1"));
			gmwf.addClassicTask("I2", ModelModel.getConfig().getLangValue("peer_review_task_i2"));
			
			gmwf.setAxiom("Ag");
			
			ArrayList<String> rhs = new ArrayList<String>();
			rhs.addAll(Arrays.asList("A"));
			gmwf.addProduction("P1", GrammaticalModelOfWorkflow.PRODUCTION_TYPE_NONE, "Ag", rhs);
			rhs = new ArrayList<String>();
			rhs.addAll(Arrays.asList("B", "D"));
			gmwf.addProduction("P2", GrammaticalModelOfWorkflow.PRODUCTION_TYPE_SEQ, "A", rhs);
			rhs = new ArrayList<String>();
			rhs.addAll(Arrays.asList("C", "D"));
			gmwf.addProduction("P3", GrammaticalModelOfWorkflow.PRODUCTION_TYPE_SEQ, "A", rhs);
			rhs = new ArrayList<String>();
			rhs.addAll(Arrays.asList("E", "F"));
			gmwf.addProduction("P4", GrammaticalModelOfWorkflow.PRODUCTION_TYPE_SEQ, "C", rhs);
			rhs = new ArrayList<String>();
			rhs.addAll(Arrays.asList("G1", "G2"));
			gmwf.addProduction("P5", GrammaticalModelOfWorkflow.PRODUCTION_TYPE_PAR, "E", rhs);
			rhs = new ArrayList<String>();
			rhs.addAll(Arrays.asList("H1", "I1"));
			gmwf.addProduction("P6", GrammaticalModelOfWorkflow.PRODUCTION_TYPE_SEQ, "G1", rhs);
			rhs = new ArrayList<String>();
			rhs.addAll(Arrays.asList("H2", "I2"));
			gmwf.addProduction("P7", GrammaticalModelOfWorkflow.PRODUCTION_TYPE_SEQ, "G2", rhs);
			rhs = new ArrayList<String>();
			gmwf.addProduction("P8", GrammaticalModelOfWorkflow.PRODUCTION_TYPE_EPS, "B", rhs);
			rhs = new ArrayList<String>();
			gmwf.addProduction("P9", GrammaticalModelOfWorkflow.PRODUCTION_TYPE_EPS, "D", rhs);
			rhs = new ArrayList<String>();
			gmwf.addProduction("P10", GrammaticalModelOfWorkflow.PRODUCTION_TYPE_EPS, "F", rhs);
			rhs = new ArrayList<String>();
			gmwf.addProduction("P11", GrammaticalModelOfWorkflow.PRODUCTION_TYPE_EPS, "H1", rhs);
			rhs = new ArrayList<String>();
			gmwf.addProduction("P12", GrammaticalModelOfWorkflow.PRODUCTION_TYPE_EPS, "I1", rhs);
			rhs = new ArrayList<String>();
			gmwf.addProduction("P13", GrammaticalModelOfWorkflow.PRODUCTION_TYPE_EPS, "H2", rhs);
			rhs = new ArrayList<String>();
			gmwf.addProduction("P14", GrammaticalModelOfWorkflow.PRODUCTION_TYPE_EPS, "I2", rhs);
			
			Stakeholder ec = new Stakeholder("EC"),
				ae = new Stakeholder("AE"),
				r1 = new Stakeholder("R1"),
				r2 = new Stakeholder("R2");
			
			p2pWorkflow.addStakeholder(ec, ae, r1, r2);
			
			Accreditation accrEc = new Accreditation(),
				accrAe = new Accreditation(),
				accrR1 = new Accreditation(),
				accrR2 = new Accreditation();
			accrEc.addReadable("Ag", "A", "B", "C", "D", "F", "H1", "H2", "I1", "I2");
			accrEc.addWritable("Ag", "A", "B", "D");
			accrEc.addExecutable("C");
			
			accrAe.addReadable("Ag", "A", "C", "E", "F", "H1", "H2", "I1", "I2");
			accrAe.addWritable("C", "E", "F");
			accrAe.addExecutable("G1", "G2");
			
			accrR1.addReadable("Ag", "C", "G1", "H1", "I1");
			accrR1.addWritable("G1", "H1", "I1");
			
			accrR2.addReadable("Ag", "C", "G2", "H2", "I2");
			accrR2.addWritable("G2", "H2", "I2");
			
			p2pWorkflow.addAccreditation(ec, accrEc);
			p2pWorkflow.addAccreditation(ae, accrAe);
			p2pWorkflow.addAccreditation(r1, accrR1);
			p2pWorkflow.addAccreditation(r2, accrR2);
			
			p2pWorkflow.setGrammaticalModelOfWorkflow(gmwf);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return p2pWorkflow;
    }
    
    public static PeerToPeerWorkflow getClaimProcessWorkflow(){
    	PeerToPeerWorkflow p2pWorkflow = new PeerToPeerWorkflow();
    	GrammaticalModelOfWorkflow gmwf = p2pWorkflow.getGrammaticalModelOfWorkflow();
    	try {
			gmwf.addVirtualTask("Ag", ModelModel.getConfig().getLangValue("axiom"));
			gmwf.addVirtualTask("P", "Parallèliser");
			gmwf.addClassicTask("A", "Réception et enregistrement du dossier de réclamation");
			gmwf.addClassicTask("B", "Vérification de l’assurance du client afin d’être sûr qu’il"
					+ " est dans la base de données et qu’il est à jour dans le paiement"
					+ " de son assurance");
			gmwf.addClassicTask("C", "Contact du garage pour s’assurer de la "
					+ "conformité de la facture");
			gmwf.addClassicTask("D", "Validation du montant à reverser au client");
			gmwf.addClassicTask("E", "Prise de décision à partir du rapport "
					+ "d’expertise et de la vérification de l’assurance du client");
			gmwf.addClassicTask("F", "Notification du client (Envoie de la lettre "
					+ "d’acceptation du remboursement ou de rejet)");
			gmwf.addClassicTask("G", "Récupération des informations bancaires du client.");
			gmwf.addClassicTask("H", "Paiement des frais de réparation");
			
			gmwf.setAxiom("Ag");
			
			ArrayList<String> rhs = new ArrayList<String>();
			rhs.addAll(Arrays.asList("A"));
			gmwf.addProduction("P1", GrammaticalModelOfWorkflow.PRODUCTION_TYPE_NONE, "Ag", rhs);
			rhs = new ArrayList<String>();
			rhs.addAll(Arrays.asList("P", "E"));
			gmwf.addProduction("P2", GrammaticalModelOfWorkflow.PRODUCTION_TYPE_SEQ, "A", rhs);
			rhs = new ArrayList<String>();
			rhs.addAll(Arrays.asList("B", "C"));
			gmwf.addProduction("P3", GrammaticalModelOfWorkflow.PRODUCTION_TYPE_PAR, "P", rhs);
			rhs = new ArrayList<String>();
			rhs.addAll(Arrays.asList("D"));
			gmwf.addProduction("P4", GrammaticalModelOfWorkflow.PRODUCTION_TYPE_NONE, "C", rhs);
			rhs = new ArrayList<String>();
			rhs.addAll(Arrays.asList("F"));
			gmwf.addProduction("P5", GrammaticalModelOfWorkflow.PRODUCTION_TYPE_NONE, "E", rhs);
			rhs = new ArrayList<String>();
			rhs.addAll(Arrays.asList("F", "G"));
			gmwf.addProduction("P6", GrammaticalModelOfWorkflow.PRODUCTION_TYPE_SEQ, "E", rhs);
			rhs = new ArrayList<String>();
			rhs.addAll(Arrays.asList("H"));
			gmwf.addProduction("P7", GrammaticalModelOfWorkflow.PRODUCTION_TYPE_NONE, "G", rhs);
			rhs = new ArrayList<String>();
			gmwf.addProduction("P8", GrammaticalModelOfWorkflow.PRODUCTION_TYPE_EPS, "B", rhs);
			rhs = new ArrayList<String>();
			gmwf.addProduction("P9", GrammaticalModelOfWorkflow.PRODUCTION_TYPE_EPS, "D", rhs);
			rhs = new ArrayList<String>();
			gmwf.addProduction("P10", GrammaticalModelOfWorkflow.PRODUCTION_TYPE_EPS, "F", rhs);
			rhs = new ArrayList<String>();
			gmwf.addProduction("P11", GrammaticalModelOfWorkflow.PRODUCTION_TYPE_EPS, "H", rhs);
			
			Stakeholder as1 = new Stakeholder("AS1"),
						as2 = new Stakeholder("AS2"),
						ex  = new Stakeholder("EX"),
						ba  = new Stakeholder("BA");
			
			p2pWorkflow.addStakeholder(as1, as2, ex, ba);
			
			Accreditation accrAs1 = new Accreditation(),
						  accrAs2 = new Accreditation(),
						  accrEx  = new Accreditation(),
						  accrBa  = new Accreditation();
			
			accrAs1.addReadable("Ag", "A", "B", "C", "D", "E", "F", "G", "H", "P");
			accrAs1.addWritable("Ag", "A", "B", "P");
			accrAs1.addExecutable("C", "E");
			
			accrAs2.addReadable("Ag", "A", "B", "C", "D", "E", "F", "G", "H", "P");
			accrAs2.addWritable("C", "E", "F", "G");
			accrAs2.addExecutable("D", "H");
			
			accrEx.addReadable("Ag", "C", "D");
			accrEx.addWritable("D");
			
			accrBa.addReadable("Ag", "G", "H");
			accrBa.addWritable("H");
			
			p2pWorkflow.addAccreditation(as1, accrAs1);
			p2pWorkflow.addAccreditation(as2, accrAs2);
			p2pWorkflow.addAccreditation(ex, accrEx);
			p2pWorkflow.addAccreditation(ba, accrBa);
			
			p2pWorkflow.setGrammaticalModelOfWorkflow(gmwf);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return p2pWorkflow;
    }
    
    public static void main(String [] args){
    	try{
	    	PeerToPeerWorkflow p2pWorkflow = getPeerReviewWorkflow();
	    	System.out.println(p2pWorkflow.getGrammaticalModelOfWorkflow().getArtefacts());
	        Stakeholder ec = p2pWorkflow.getStakeholder("EC"),
					ae = p2pWorkflow.getStakeholder("AE"),
					r1 = p2pWorkflow.getStakeholder("R1"),
					r2 = p2pWorkflow.getStakeholder("R2");
	        WorkflowPeerConfiguration ecWorkflow = p2pWorkflow.projectGMWf(ec),
	        		aeWorkflow = p2pWorkflow.projectGMWf(ae),
	        		r1Workflow = p2pWorkflow.projectGMWf(r1),
	        		r2Workflow = p2pWorkflow.projectGMWf(r2);
	        System.out.println(ecWorkflow.getLocalGMWf().getProductions());
	        System.out.println(aeWorkflow.getLocalGMWf().getProductions());
	        System.out.println(r1Workflow.getLocalGMWf().getProductions());
	        System.out.println(r2Workflow.getLocalGMWf().getProductions());
	        System.out.println(getPrunedArtefactUnlockedBud());
	        System.out.println(getPrunedArtefactLockedBud());
	        
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public static PeerToPeerWorkflowArtefact getPrunedArtefactUnlockedBud(){
    	try{
	    	PeerToPeerWorkflow p2pWorkflow = getPeerReviewWorkflow();
	    	PeerToPeerWorkflowArtefact art = p2pWorkflow.getGrammaticalModelOfWorkflow().getArtefacts().get(0);
	    	art.prune("1.2");
	    	return art;
    	} catch(Exception e) {
    		return null;
    	}
    }
    
    public static PeerToPeerWorkflowArtefact getPrunedArtefactLockedBud(){
    	try{
	    	PeerToPeerWorkflow p2pWorkflow = getPeerReviewWorkflow();
	    	PeerToPeerWorkflowArtefact art = p2pWorkflow.getGrammaticalModelOfWorkflow().getArtefacts().get(1);
	    	art.prune("1.1.1.2");
	    	art.getSubArtefact("1.1.1.2").setStatus(PeerToPeerWorkflowArtefact.TASK_STATUS_LOCKED);
	    	return art;
    	} catch(Exception e) {
    		return null;
    	}
    }
}
