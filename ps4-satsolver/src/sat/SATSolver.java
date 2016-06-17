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
        
        System.out.println("----");
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
        retenv = new Environment();
        
      //If there are no clauses, the formula is trivially satisfiable
        // ha nincsenek cluase-�k, akkor a lista nulla elem�
        // jobb h�j�n visszaadunk egy �res k�rnyezetet
        
        System.out.println("solve start:" + clauses + " env: "+env);
        
        if( clauses.size() == 0 )
        {
        	retenv = new Environment();
        	return retenv;
        }
        //If there is an empty clause, the clause list is unsatisfiable -- fail and backtrack  
        // V�gim�szunk az �sszes clause-�n �s ha valamelyik �res null-t adunk vissza.
        for( Clause cl: clauses )
        {
        	if( cl.size() == 0)
        	{
        		return null;
        	}      	
        }
        
        //Otherwise, find the smallest clause (by number of literals).
        // If the clause has only one literal, bind its variable in the environment so that the
        //clause is satisfied, substitute for the variable in all the other clauses (using the
        //suggested substitute() method), and recursively call solve().
        
        for( Clause cl: clauses )
        {
        	if( cl.size() == 1)
        	{
        		// Nagyszeru, van 1 elemu clausa!
        		// Most akkor v�gig kell menni mindegyiken �s sz�pen helyettes�teni
        		// ez most azt jelenti, hogy ki kell venni bel�le ak�r neg�lva, ak�r norm�lban van ott
        		// majd rekurz�van r�h�vni a solve-ra
        		
        		//Ez vissza ad egy tetszoleges literalt
        		//Mivel egy van benne, ez�rt j� lesz
        		Literal l1;
        		Bool b1;
        		ImList<Clause> clauses2;
       
        		b1=Bool.TRUE;
        		l1=cl.chooseLiteral();
        		// n�zz�k, hogy most ez most neg�lt-e
        		// h�tha igy kell
        		if ( l1.negates(l1)) 
        		{
        			b1=Bool.FALSE;      			
        		}
        		env=env.put(l1.getVariable(), b1);
        		clauses2=substitute( clauses, l1);
        		retenv=solve(clauses2,env);
        		
        		// Megvan, most akkor megy�nk tov�bb
        		continue;
        	}      	
        }
        
        // - Otherwise, pick an arbitrary literal from this small clause:
        // - First try setting the literal to TRUE, substitute for it in all the clauses, then
        //solve() recursively.
        //- If that fails, then try setting the literal to FALSE, substitute, and solve()
        // recursively.
        return retenv;
        
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
    	ImList<Clause> retcllist=new EmptyImList<Clause>();
    	
    	new EmptyImList<Clause>();
    	
        ArrayList<Clause> templist=new ArrayList<Clause>();
        //System.out.println("Substitue start:"+clauses+" literal"+l);
        
        for( Clause cl: clauses )
        {
        	// Ha ebben a klauz�ban benne van az a literal amit ki kell venni
        	//System.out.println("Substitue: benne van?: "+cl+l);
        	if( cl.contains(l))
        	{
        		Clause tempcl;
        		//System.out.println(" yes");
        		tempcl = new Clause();
        		for ( Literal l2 : cl)
            	{
            		if(l2.getVariable().getName() != l.getVariable().getName() )
            		{
            			tempcl=tempcl.add(l2);
            			//System.out.println("Substitute tempcl hozzad"+tempcl);
            		}
            		     
            	}
        		if( tempcl.size() != 0) templist.add(tempcl);
        	}
        	else
        	{
        		// nincs benne, �gyhogy akkor csak hozz�adjuk.
        		//System.out.println(" no");
        		templist.add(cl);
        	}
        }
          
        if(templist.size()==0)
        {
        	//System.out.println("Substitue return: empty");
        	return new EmptyImList<Clause>();
        }
        else
        {
        	for ( Clause tempcl: templist)
        	{
        		retcllist=retcllist.add(tempcl);
        	}
        	// �tm�solni �s visszat�rni
        	//System.out.println("Substitue return:"+templist);
        	return retcllist;
        }
        
    }

}
