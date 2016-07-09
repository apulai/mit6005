package sat;

import java.util.ArrayList;
import java.util.List;

import immutable.EmptyImList;
import immutable.ImList;
import sat.env.Bool;
import sat.env.Environment;
import sat.formula.Clause;
import sat.formula.Formula;
import sat.formula.Literal;
import sat.formula.NegLiteral;

/**
 * A simple DPLL SAT solver. See http://en.wikipedia.org/wiki/DPLL_algorithm
 */
public class SATSolver {
	/**
	 * Solve the problem using a simple version of DPLL with backtracking and
	 * unit propagation. The returned environment binds literals of class
	 * bool.Variable rather than the special literals used in clausification of
	 * class clausal.Literal, so that clients can more readily use it.
	 * 
	 * @return an environment for which the problem evaluates to Bool.TRUE, or
	 *         null if no such environment exists.
	 */
	public static Environment solve(Formula formula) {
		// TODO: implement this.
		//throw new RuntimeException("not yet implemented.");

		Environment retenv;


		retenv=new Environment();
		retenv=solve(formula.getClauses(), retenv); 

		return retenv;

	}

	/**
	 * Takes a partial assignment of variables to values, and recursively
	 * searches for a complete satisfying assignment.
	 * 
	 * @param clauses
	 *            formula in conjunctive normal form
	 * @param env
	 *            assignment of some or all variables in clauses to true or
	 *            false values.
	 * @return an environment for which all the clauses evaluate to Bool.TRUE,
	 *         or null if no such environment exists.
	 */
	private static Environment solve(ImList<Clause> clauses, Environment env) {
		// TODO: implement this.
		//throw new RuntimeException("not yet implemented.");

		Environment retenv;
		Clause smallestcl;
		int minsize=-1;
		int debug=0;

		retenv = new Environment();

		//If there are no clauses, the formula is trivially satisfiable
		// ha nincsenek cluase-�k, akkor a lista nulla elem�
		// Minden claus�t siker�lt igazz� tenni.
		// Megoldottuk a probl�mat
		// visszaadjuk azt az env-et amit kaptunk

		if( debug > 0) System.out.println("solve start:" + clauses + " env: "+env);

		if( clauses.size() == 0 )
		{
			//if( debug > 0) 
			System.out.println("solve No clauses: trivially TURE: "+env);
			return env;
		}
		//If there is an empty clause, the clause list is unsatisfiable -- fail and backtrack  
		// V�gim�szunk az �sszes clause-�n �s ha valamelyik �res null-t adunk vissza.
		// Ez akkor fordult el�, ha az adott clause nem lett felt�tlen�l igaz att�l, hogy
		// egy v�ltoz�t egy m�sik klauz�ban igazz� tett�nk.

		for( Clause cl: clauses )
		{
			//if( cl.size() == 0)
			if( cl.isEmpty() )
			{
				//if( debug > 0) 
				System.out.println("solve empty clause: FALSE: "+env);
				return null;
			}      	
		}

		//Otherwise, find the smallest clause (by number of literals).
		// If the clause has only one literal, bind its variable in the environment so that the
		//clause is satisfied, substitute for the variable in all the other clauses (using the
		//suggested substitute() method), and recursively call solve().

		// Ezt csak az�rt csin�ljuk, hogy inicializ�lva legyen
		smallestcl= clauses.first();

		// Akkor most keress�k meg a legkisebbet
		for( Clause cl1: clauses )
		{
			if(minsize==-1) minsize=cl1.size();
			if(minsize >= cl1.size() ) 
			{
				smallestcl=cl1;
				minsize=cl1.size();
			}
		}
		if( debug > 0) System.out.println("Smallestcl: "+smallestcl);

		if( smallestcl.size() == 1)
		{
			// Nagyszeru, van 1 elemu clausa!
			// Most akkor v�gig kell menni mindegyiken �s kit�r�lni
			// azokat a claus�kat amiket igazz� tudunk tenni
			// Amiben a v�lasztott �rt�k neg�ltja szerepel, abb�l kivessz�k
			// mag�t a v�ltoz�t, de a claus�t bent kell hagyni, mert nem tudutuk
			// igazz� tenni
			// Sz�val ha m�dos�tottuk a clasue-list�t
			// rekurz�van r�h�vunk a solve-ra

			Literal l1;
			Bool b1;
			ImList<Clause> clauses2;


			//Ez vissza ad egy tetszoleges literalt
			//Mivel egy van benne, ez�rt j� lesz
			l1=smallestcl.chooseLiteral();

			b1=Bool.TRUE;
			// errol mondt�k hogy nem sz�p
			// de nincs m�s �teletem
			if ( l1 instanceof NegLiteral)  
			{
				b1=Bool.FALSE;      			
			}
			// Ezzel most igazz� tett�k ezt a klauz�t
			env=env.put(l1.getVariable(), b1);
			clauses2=substitute( clauses, l1);
			retenv=solve(clauses2,env);
			return retenv;
		}      	
		else
		{
			// - Otherwise, pick an arbitrary literal from this small clause:
			// - First try setting the literal to TRUE, substitute for it in all the clauses, then
			//solve() recursively.
			//- If that fails, then try setting the literal to FALSE, substitute, and solve()
			// recursively.
			//System.out.println("nem egyelemu: "+smallestcl);

			Bool b1;
			ImList<Clause> clauses2;

			// Most ebben a klauz�ban minden bet�n v�gigmegy�nk
			// M�g akkor is ha van �rv�nyes megold�sunk?

			for(Literal l1: smallestcl)
			{
				b1=Bool.TRUE;

				// errol mondt�k hogy nem sz�p
				// de nincs m�s �teletem
				if ( l1 instanceof NegLiteral)  
				{
					b1=Bool.FALSE;      			
				}
				env=env.put(l1.getVariable(), b1);
				clauses2=substitute( clauses, l1);
				retenv=solve(clauses2,env);


				if( retenv != null ) 
				{	
					if( debug > 0) System.out.println("solve return: "+retenv);
					return retenv;
				}
			}
		}

		return null;
	}




	/**
	 * given a clause list and literal, produce a new list resulting from
	 * setting that literal to true
	 * 
	 * @param clauses
	 *            , a list of clauses
	 * @param l
	 *            , a literal to set to true
	 * @return a new list of clauses resulting from setting l to true
	 */

	private static ImList<Clause> substitute(ImList<Clause> clauses,
			Literal l) {
		// TODO: implement this.
		//throw new RuntimeException("not yet implemented.");
		
		int debug=0;
		
		ImList<Clause> retcllist=new EmptyImList<Clause>();

		new EmptyImList<Clause>();

		ArrayList<Clause> templist=new ArrayList<Clause>();
		if (debug > 0) System.out.println("Substitue start: "+clauses+" literal: "+l);

		for( Clause cl: clauses )
		{
			// Ha benne van, akkor csak megyunk tovabb
			if( cl.contains(l) ) continue;

			// Ha a neg�ltja van benne, akkor ki kell mazsol�zni bel�le
			if( cl.contains(l.getNegation()) )		
			{
				Clause tempcl;
				tempcl = new Clause();
				for ( Literal l2 : cl)
				{
					if(l2.getVariable().getName() != l.getVariable().getName() )
					{
						tempcl=tempcl.add(l2);
						//System.out.println("Substitute tempcl hozzad1"+tempcl);
					}

				}
				//if( tempcl.size() != 0  ) 
				templist.add(tempcl);
			}
			// se a neg�ltja sem maga nincs benne
			else
			{
				// nincs benne, �gyhogy akkor csak hozz�adjuk.
				//System.out.println(" no");
				//System.out.println("Substitute tempcl hozzad2"+cl);
				templist.add(cl);
			}
		}

		if(templist.size()==0)
		{
			if (debug > 0)  System.out.println("Substitue return: empty");
			return new EmptyImList<Clause>();
		}
		else
		{
			for ( Clause tempcl: templist)
			{
				retcllist=retcllist.add(tempcl);
			}
			// �tm�solni �s visszat�rni
			if (debug > 0)  System.out.println("Substitue return: "+templist);
			return retcllist;
		}

	}

	
}
