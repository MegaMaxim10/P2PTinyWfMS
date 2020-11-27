package smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.verification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.Accreditation;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.GrammaticalModelOfWorkflow;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.Parsers;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.PeerToPeerWorkflow;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.Production;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.Stakeholder;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.Task;

/**
 *
 * @author TIALO NECHEU Innocent
 */

public class VerificationHelper {

	public static ArrayList<ArrayList<PrecedenceRule>> getChainOfPrecedenceFrom(
			String X, GrammaticalModelOfWorkflow gmwf) throws Exception {

		ArrayList<Production> X_Prods = null;
		ArrayList<ArrayList<PrecedenceRule>> CPx = null;
		ArrayList<ArrayList<PrecedenceRule>> CPy = null;
		ArrayList<PrecedenceRule> RPp = null;
		ArrayList<PrecedenceRule> chainOfPrecedence = null;
		ArrayList<PrecedenceRule> firstChainOfPrecedence = null;

		if (gmwf.getSymbols().contains(X)) {
			X_Prods = getX_Prods(X, gmwf);
			CPx = new ArrayList<ArrayList<PrecedenceRule>>();

			for (Production production : X_Prods) {
				RPp = production.getAssocPrecRules();
				if (RPp.isEmpty()) {
					return new ArrayList<ArrayList<PrecedenceRule>>();
				} else {
					for (PrecedenceRule precedenceRule : RPp) {
						firstChainOfPrecedence = new ArrayList<PrecedenceRule>();
						firstChainOfPrecedence.add(precedenceRule);
						chainOfPrecedence = new ArrayList<PrecedenceRule>();
						chainOfPrecedence.add(precedenceRule);
						CPx.add(chainOfPrecedence);

						for (String Y : precedenceRule.getRuleRhs()
								.getRhsSymbols()) {
							CPy = getChainOfPrecedenceFrom(Y, gmwf);

							for (ArrayList<PrecedenceRule> chainOfPrecedenceFromY : CPy) {
								ArrayList<PrecedenceRule> newChainOfPrecedence = new ArrayList<PrecedenceRule>();
								newChainOfPrecedence
										.addAll(firstChainOfPrecedence);
								newChainOfPrecedence
										.addAll(chainOfPrecedenceFromY);
								CPx.add(newChainOfPrecedence);

							}
						}
					}

				}
			}
			return CPx;
		} else {
			throw new Exception(
					"Le symbole indiqué n'appartient pas à l'ensembles des symboles de la grammaire");
		}
	}

	public static boolean estAtteignableXDeS0(String S0, String X,
			PeerToPeerWorkflow p2pwf) throws Exception {

		if (p2pwf.getGrammaticalModelOfWorkflow().getSymbols()
				.containsAll(Arrays.asList(S0, X))) {
			if (S0.equals(X)) {
				return true;
			} else {
				ArrayList<ArrayList<PrecedenceRule>> sigma = new ArrayList<ArrayList<PrecedenceRule>>();
				sigma = getChainOfPrecedenceFomXToY(S0, X,
						p2pwf.getGrammaticalModelOfWorkflow());
				PrecedenceRule pr = null;
				String Si_1 = null, Si = null, Xij = null;
				ArrayList<String> prRhs = null;
				boolean condition = true, condition11 = true, condition12 = true, condition13 = true, condition2 = true;
				if (sigma.isEmpty()) {
					throw new Exception(
							"Il n'existe pas de chaine de précédence de " + S0
									+ " à " + X);
				} else {
					ArrayList<String> cpSymbols = null;
					for (ArrayList<PrecedenceRule> cp : sigma) {
						cpSymbols = new ArrayList<String>();
						for (PrecedenceRule p : cp) {
							cpSymbols.add(p.getRuleLhs());
						}
						cpSymbols.add(X);
						for (int i = 0; i < cp.size(); i++) {
							pr = cp.get(i);
							prRhs = pr.getRuleRhs().getRhsSymbols();
							Si_1 = pr.getRuleLhs();
							Si = cpSymbols.get(i + 1);
							condition11 = p2pwf
									.getAccreditation(p2pwf.executorOf(Si_1))
									.getWritable().contains(Si);
							condition12 = p2pwf
									.getAccreditation(p2pwf.executorOf(Si_1))
									.getExecutable().contains(Si);
							condition13 = getAllWritableSymbole(p2pwf)
									.contains(Si);
							for (int j = 0; j < prRhs.indexOf(Si) - 1; j++) {
								Xij = prRhs.get(j);
								condition11 = condition11
										&& (p2pwf.getAccreditation(
												p2pwf.executorOf(Si_1))
												.getWritable().contains(Xij));
								condition12 = condition12
										&& (p2pwf.getAccreditation(
												p2pwf.executorOf(Si_1))
												.getExecutable().contains(Xij));
								condition13 = condition13
										&& (getAllWritableSymbole(p2pwf)
												.contains(Xij));
								for (String s : getDevivableSymbolsFromX(Xij,
										p2pwf.getGrammaticalModelOfWorkflow())) {
									try {
										condition2 = condition2
												&& estAtteignableXDeS0(Xij, s,
														p2pwf);
									} catch (Exception e) {
										throw new Exception(
												"Le symbole "
														+ s
														+ " n'est pas atteignable à partir de "
														+ Xij);
									}
								}
							}
						}
						condition = condition
								&& ((condition11 || (condition12 && condition13)) && condition2);
					}
					if (condition) {
						return true;
					} else {
						throw new Exception(
								"problème au niveau de la distribution des accréditations");
					}
				}
			}
		} else {
			throw new Exception("Symbole(s) inexistant(s)");
		}
	}

	public static ArrayList<String> getAllWritableSymbole(
			PeerToPeerWorkflow p2pworkflow) {

		List<String> allWritableAccreditations = new ArrayList<String>();
		for (Map.Entry<String, Accreditation> accreditation : p2pworkflow
				.getAccreditations().entrySet()) {
			allWritableAccreditations.addAll(accreditation.getValue()
					.getWritable());
		}

		return (ArrayList<String>) allWritableAccreditations;
	}

	public static ArrayList<String> getDevivableSymbolsFromX(String X,
			GrammaticalModelOfWorkflow gmwf) {

		ArrayList<String> derivableSymbolFromX = new ArrayList<String>();
		derivableSymbolFromX.add(X);
		try {

			for (Production X_production : getX_Prods(X, gmwf)) {
				for (String symbol : X_production.getProdRhs().getRhsSymbols()) {
					derivableSymbolFromX.addAll(getDevivableSymbolsFromX(
							symbol, gmwf));
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return derivableSymbolFromX;
	}

	public static ArrayList<ArrayList<PrecedenceRule>> getChainOfPrecedenceFomXToY(
			String X, String Y, GrammaticalModelOfWorkflow gmwf)
			throws Exception {

		ArrayList<ArrayList<PrecedenceRule>> chainOfPrecedencesFromXToY = new ArrayList<ArrayList<PrecedenceRule>>();
		PrecedenceRule lastRuleOfChain = null;
		if (gmwf.getSymbols().contains(X) && gmwf.getSymbols().contains(Y)) {
			try {
				ArrayList<ArrayList<PrecedenceRule>> chainOfPrecedencesFromX = getChainOfPrecedenceFrom(
						X, gmwf);

				for (ArrayList<PrecedenceRule> chainOfPrecedence : chainOfPrecedencesFromX) {
					lastRuleOfChain = chainOfPrecedence.get(chainOfPrecedence
							.size() - 1);

					if (lastRuleOfChain.getRuleRhs().getRhsSymbols()
							.contains(Y)) {
						chainOfPrecedencesFromXToY.add(chainOfPrecedence);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
			return chainOfPrecedencesFromXToY;
		} else {
			throw new Exception(
					"L'un des symboles ou les deux, n'appartient (ou n'appartiennent) pas au MGWf du workflow spécifié");
		}
	}

	public static ArrayList<Production> getX_Prods(String X,
			GrammaticalModelOfWorkflow gmwf) throws Exception {

		if (gmwf.getSymbols().contains(X)) {
			ArrayList<Production> x_productions = new ArrayList<>();
			for (Production production : gmwf.getProductions()) {
				if (production.getProdLhs().equals(X)) {
					x_productions.add(production);
				}
			}
			return x_productions;
		} else {
			throw new Exception(
					"Le symbole indiqué n'appartient pas à l'ensembles des symboles de la grammaire");
		}

	}

	public static void printSetOfRule(
			ArrayList<PrecedenceRule> chainOfPrecedence) {
		System.out.print("{");
		for (PrecedenceRule precedenceRule : chainOfPrecedence) {
			if (chainOfPrecedence.get(chainOfPrecedence.size() - 1).equals(
					precedenceRule)) {
				System.out.print(precedenceRule);
			} else {
				System.out.print(precedenceRule + ", ");
			}
		}
		System.out.print("}");
	}

	public static void printSetOfChainOfPrecedence(
			ArrayList<ArrayList<PrecedenceRule>> setOfChainOfPrecedence) {
		System.out.print("{");
		for (ArrayList<PrecedenceRule> chainOfPrecedence : setOfChainOfPrecedence) {
			if (setOfChainOfPrecedence.get(setOfChainOfPrecedence.size() - 1)
					.equals(chainOfPrecedence)) {
				printSetOfRule(chainOfPrecedence);
			} else {
				printSetOfRule(chainOfPrecedence);
				System.out.print(", ");
			}
		}
		System.out.print("}");
	}

	public static <T> ArrayList<T> intersection(ArrayList<T> arrayList1,
			ArrayList<T> arrayList2) {
		ArrayList<T> intersectionOfArrayLists = new ArrayList<T>();
		for (T t : arrayList1) {
			if (arrayList2.contains(t))
				intersectionOfArrayLists.add(t);
		}
		return intersectionOfArrayLists;
	}

	public static <T> ArrayList<T> intersection(ArrayList<T> arrayList1,
			@SuppressWarnings("unchecked") ArrayList<T>... arrayLists) {
		ArrayList<T> intersectionOfArrayLists = new ArrayList<T>();
		boolean isInIntersection = true;

		for (T t : arrayList1) {
			isInIntersection = true;
			for (ArrayList<T> arrayList : arrayLists) {
				if (!arrayList.contains(t)) {
					isInIntersection = false;
					break;
				}
			}
			if (isInIntersection) {
				intersectionOfArrayLists.add(t);
			}
		}

		return intersectionOfArrayLists;
	}

	public static <T> ArrayList<T> intersection(
			ArrayList<ArrayList<T>> arrayLists) {
		ArrayList<T> intersectionOfArrayLists = new ArrayList<T>();
		ArrayList<T> arrayList = null;
		boolean isInIntersection = true;

		for (T t : arrayLists.get(0)) {
			isInIntersection = true;
			for (int i = 1; i < arrayLists.size(); i++) {
				arrayList = arrayLists.get(i);
				if (!arrayList.contains(t)) {
					isInIntersection = false;
				}
			}
			if (isInIntersection) {
				intersectionOfArrayLists.add(t);
			}
		}

		return intersectionOfArrayLists;
	}

	// critère de correction pour les workflows spécifiés à l'aide du langage
	// des MGWf

	/*
	 * Cette méthode permet de s'assurer que chaque tâche n'a qu'un seul acteur
	 * qui lui soit accrédité en écriture;
	 */
	public static boolean noConflictsDuringMergingsPendingExecutionOf(
			PeerToPeerWorkflow p2pwf) throws Exception {

		ArrayList<Stakeholder> actors = p2pwf.getStakeholders();
		ArrayList<ArrayList<String>> listOfWritableAccrditation = new ArrayList<ArrayList<String>>();

		for (Stakeholder actor : actors) {
			listOfWritableAccrditation.add(p2pwf.getAccreditation(actor)
					.getWritable());
		}
		for (ArrayList<String> list : listOfWritableAccrditation) {
			for (ArrayList<String> l : listOfWritableAccrditation) {
				if (!l.equals(list)) {
					if (!intersection(list, l).isEmpty()) {
						throw new Exception(
								"Il existe deux acteurs accrédités en écriture sur la même tâche!"
										+ "Les symboles de la liste "
										+ intersection(list, l)
										+ " appartiennent aux accréditations"
										+ " en écriture de deux acteurs!");
					}
				}
			}
		}
		return true;
	}

	/*
	 * Cette méthode permet de s'assurer que tous les acteurs sont accrédités en
	 * lecture sur l'axiom
	 */
	public static boolean allStakeholdersCanSeeAxiomIn(PeerToPeerWorkflow p2pwf) throws Exception {
		ArrayList<Stakeholder> actors = p2pwf.getStakeholders();
		ArrayList<ArrayList<String>> listOfReadableAccrditation = new ArrayList<ArrayList<String>>();

		for (Stakeholder actor : actors) {
			listOfReadableAccrditation.add(p2pwf.getAccreditation(actor)
					.getReadable());
		}

		if ((!intersection(listOfReadableAccrditation).isEmpty())
				&& (intersection(listOfReadableAccrditation).contains(p2pwf
						.getGrammaticalModelOfWorkflow().getAxiom())))
			return true;
		else
			throw new Exception ("L'axiome n'appartient pas à la vue de tous les acteurs");
	}

	/*
	 * cette méthode permet de s'assurer que toutes les tâches sont atteignables
	 */	
	public static HashMap<Task, String> getUnReachableTasks(PeerToPeerWorkflow p2pwf){
		
		HashMap<Task, String> listOfUnreachableTask = new HashMap<Task, String>();
		for (String symbol : p2pwf.getGrammaticalModelOfWorkflow().getSymbols()) {
			try {
				if (estAtteignableXDeS0(p2pwf.getGrammaticalModelOfWorkflow().getAxiom(), symbol, p2pwf)){
					continue;
				} 
			} catch (Exception e) {
				listOfUnreachableTask.put(p2pwf.getGrammaticalModelOfWorkflow().getTask(symbol), e.getMessage());
			}
		}
		
		return listOfUnreachableTask;
	}
	
	/*
	 * cette méthode permet de s'assurer que tous les acteurs accrédité en écriture sur une tâche le sont 
	 * également en lecture: cette méthode retourne la liste des symboles sur lesquelles un acteur est accrédité en 
	 * écriture sans l'être en lecture avec l'acteur correspondant!
	 */	
	public static HashMap<Stakeholder, ArrayList<String>> getWritableSymboWhichAreUnreadeable (PeerToPeerWorkflow p2pwf) {
		HashMap<Stakeholder, ArrayList<String>> mapToReturn = new HashMap<Stakeholder, ArrayList<String>>();
		ArrayList<String> symbolsSearched = null;
		Accreditation accreditation = null;
		for(Stakeholder actor: p2pwf.getStakeholders()){
			accreditation = p2pwf.getAccreditation(actor);
			if(!accreditation.getReadable().containsAll(accreditation.getWritable())){
				symbolsSearched = new ArrayList<String>();
				for(String symbol: accreditation.getWritable()){
					if(!accreditation.getReadable().contains(symbol))
						symbolsSearched.add(symbol);
				}
				
				mapToReturn.put(actor, symbolsSearched);
			}
		}
		return mapToReturn;
	}
	
	public static void main(String[] args) {
		PeerToPeerWorkflow processClaimWorkflow = Parsers
				.getClaimProcessWorkflow();

		ArrayList<String> list1 = new ArrayList<String>();
		ArrayList<String> list2 = new ArrayList<String>();
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		list.add(list1);
		list.add(list2);

		list1.addAll(Arrays.asList("A", "B", "C", "D"));
		list2.addAll(Arrays.asList("A", "C", "D", "toto", "sdsf", "sdsdf"));

		System.out.println(intersection(list));
		System.out.println("ca maaaaaaaaaaaaaaaaaaaarche!");
		System.out.println();
		System.out.println();
		
		HashMap<Task, String> l = getUnReachableTasks(processClaimWorkflow);
		
		HashMap<Stakeholder, ArrayList<String>> writeWhichIsNotRead = getWritableSymboWhichAreUnreadeable(processClaimWorkflow);
		
		System.out.println("yeppppppppppppppppppppppp "+writeWhichIsNotRead);
		
		System.out.println("tandandandannnnnnnnnnnnnnn "+l);

		try {
			printSetOfChainOfPrecedence(getChainOfPrecedenceFrom("P",
					processClaimWorkflow.getGrammaticalModelOfWorkflow()));
			System.out.println();
			System.out.println();
			if(allStakeholdersCanSeeAxiomIn(processClaimWorkflow))
				System.out.println("perfect white!");
			System.out.println();
			System.out.println();
			if (noConflictsDuringMergingsPendingExecutionOf(processClaimWorkflow))
				System.out.println("tu es génial petit!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		System.out.println();
		System.out.println(processClaimWorkflow.getGrammaticalModelOfWorkflow()
				.getProductions());
		try {
			printSetOfChainOfPrecedence(getChainOfPrecedenceFomXToY("Ag", "P",
					processClaimWorkflow.getGrammaticalModelOfWorkflow()));
			System.out.println("me voici yaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
			System.out.println(getAllWritableSymbole(processClaimWorkflow));
			System.out.println(getDevivableSymbolsFromX("P",
					processClaimWorkflow.getGrammaticalModelOfWorkflow()));
			if (estAtteignableXDeS0("P", "D", processClaimWorkflow)) {
				System.out.println("wouééééééééééééééééééééééééééééééé");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

	}

}
