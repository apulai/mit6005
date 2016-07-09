/**
 * Author: dnj, Hank Huang
 * Date: March 7, 2009
 * 6.005 Elements of Software Construction
 * (c) 2007-2009, MIT 6.005 Staff
 */
package sat.formula;

import immutable.EmptyImList;
import immutable.ImList;
import immutable.NonEmptyImList;

import java.util.Iterator;

import sat.env.Variable;

/**
 * Formula represents an immutable boolean formula in
 * conjunctive normal form, intended to be solved by a
 * SAT solver.
 */

//Formula = ImList<Clause> // a list of clauses ANDed together
//Clause = ImList<Literal> // a list of literals ORed together
//Literal = Positive(v: Var) + Negative(v:Var) // either a variable P or its negation ¬P
//Var = String

/* Internet converting forumlas to CNF
 * https://www.cs.jhu.edu/~jason/tutorials/convert-to-CNF.html
 */
public class Formula {
	private final ImList<Clause> clauses;

	public enum myoperations { OR, AND, NOT, IMPLIES, XOR, FURA }

	// Rep invariant:
	//      clauses != null
	//      clauses contains no null elements (ensured by spec of ImList)
	//
	// Note: although a formula is intended to be a set,  
	// the list may include duplicate clauses without any problems. 
	// The cost of ensuring that the list has no duplicates is not worth paying.
	//
	//    
	//    Abstraction function:
	//        The list of clauses c1,c2,...,cn represents 
	//        the boolean formula (c1 and c2 and ... and cn)
	//        
	//        For example, if the list contains the two clauses (a,b) and (!c,d), then the
	//        corresponding formula is (a or b) and (!c or d).

	void checkRep() {
		assert this.clauses != null : "SATProblem, Rep invariant: clauses non-null";
	}

	/**
	 * Create a new problem for solving that contains no clauses (that is the
	 * vacuously true problem)
	 * 
	 * @return the true problem
	 */
	public Formula() {	
		// TODO: implement this.
		// throw new RuntimeException("not yet implemented.");
		clauses=new EmptyImList<Clause>();
	}

	/**
	 * Create a new problem for solving that contains a single clause with a
	 * single literal
	 * 
	 * @return the problem with a single clause containing the literal l
	 */
	public Formula(Variable var) {
		// TODO: implement this.
		//throw new RuntimeException("not yet implemented.");
		Clause c;
		c=new Clause(PosLiteral.make(var));	
		clauses=new NonEmptyImList<Clause>(c);
	}

	/**
	 * Create a new problem for solving that contains a single clause
	 * 
	 * @return the problem with a single clause c
	 */
	public Formula(Clause c) {
		// TODO: implement this.
		//throw new RuntimeException("not yet implemented.");
		clauses=new NonEmptyImList<Clause>(c);     
	}

	/**
	 * Add a clause to this problem
	 * 
	 * @return a new problem with the clauses of this, but c added
	 */
	// I have created here a "hidden" private constructor
	// since the only way to modify a final field is in the
	// constructor. The idea came from the NonEmptyImList
	// file
	// we are saving space, since the "rest" or "tail" of the IM list
	// is the previous IM list
	private Formula (Clause c, ImList<Clause> r) {
		//System.out.println("Test1"+r.toString());
		//System.out.println("Test1"+c);
		clauses=r.add(c);
		//System.out.println("Test2"+clauses.toString());
		checkRep();
	}

	public Formula addClause(Clause c) {
		// TODO: implement this.
		// throw new RuntimeException("not yet implemented.");

		Formula f2;
		f2=new Formula(c,this.clauses);
		return f2;
	}

	/**
	 * Get the clauses of the formula.
	 * 
	 * @return list of clauses
	 */
	public ImList<Clause> getClauses() {
		// TODO: implement this.
		//throw new RuntimeException("not yet implemented.");
		return clauses;
	}

	/**
	 * Iterator over clauses
	 * 
	 * @return an iterator that yields each clause of this in some arbitrary
	 *         order
	 */
	public Iterator<Clause> iterator() {
		// TODO: implement this.
		//throw new RuntimeException("not yet implemented.");
		//
		return clauses.iterator();
	}

	/**
	 * @return a new problem corresponding to the conjunction of this and p
	 */
	public Formula and(Formula p) {
		// TODO: implement this.
		//throw new RuntimeException("not yet implemented.");
		//System.out.println("Formula AND starting: "+this+p);
		Formula f2; 
		// A belsõ konstruktorunkkal csinálunk egy formulát
		// mert ott volt olyan, hogy cl, cllist
		f2=new Formula( p.getClauses().first() , this.clauses);
		for( Clause c1: p.getClauses().rest())
		{
			f2=new Formula(c1, f2.clauses );
		}
		System.out.println("Formula AND returning: "+f2);
		return f2;
	}

	/**
	 * @return a new problem corresponding to the disjunction of this and p
	 */
	public Formula or(Formula p) {
		// TODO: implement this.
		// Hint: you'll need to use the distributive law to preserve conjunctive normal form, i.e.:
		//   to do (a & b) .or (c & d),
		//   you'll need to make (a | d) & (a | c) & (b | c) & (b | d)
		// (P or (Q and R)) -> ((P or Q) and (P or R))}
		// (P or (Q or R )) ->  ((P or Q) or (P or R))
		//https://www.cs.jhu.edu/~jason/tutorials/convert-to-CNF.html
		//    	  So we need a CNF formula equivalent to
		//          (P1 ^ P2 ^ ... ^ Pm) v (Q1 ^ Q2 ^ ... ^ Qn).
		//       So return (P1 v Q1) ^ (P1 v Q2) ^ ... ^ (P1 v Qn)
		//               ^ (P2 v Q1) ^ (P2 v Q2) ^ ... ^ (P2 v Qn)
		//                 ...
		//               ^ (Pm v Q1) ^ (Pm v Q2) ^ ... ^ (Pm v Qn)


		Clause tempcl;
		Formula retform;
		int debug=1;

		if( debug == 1) System.out.println("Formula OR start:"+this.toString()+" OR "+p.toString());

		retform=new Formula();
		for( Clause c1: this.getClauses())
		{	
			if( c1.size()!=0 )
			{
				for ( Clause c2: p.getClauses())
				{
					if( c2.size() != 0 )
					{
						tempcl=c1.merge(c2);
						if( tempcl != null  )
							retform=retform.addClause(tempcl);	
					}			
				}  				
			}
		}
		if(debug==1) System.out.println("Formula OR returning:"+retform.toString());
		return retform;
	}

	/**
	 * @return a new problem corresponding to the negation of this
	 */
	public Formula not(Formula p) {
		// TODO: implement this.
		// Hint: you'll need to apply DeMorgan's Laws (http://en.wikipedia.org/wiki/De_Morgan's_laws)
		// to move the negation down to the literals, and the distributive law to preserve 
		// conjunctive normal form, i.e.:
		//   if you start with (a | b) & c,
		//   you'll need to make !((a | b) & c) 
		//                       => (!a & !b) | !c            (moving negation down to the literals)
		//                       => (!a | !c) & (!b | !c)    (conjunctive normal form)
		//throw new RuntimeException("not yet implemented.");
		Clause tempcl;
		Formula retform,f1;
		int debug=1;

		if( debug == 1) System.out.println("Formula NOT start:"+this.toString());

		retform=new Formula();
		for( Clause c1: this.getClauses())
		{	
			f1=new Formula();
			if( c1.size()!=0 )
			{
				//    			tempcl=new Clause();
				for( Literal l1: c1)
				{
					tempcl=new Clause( l1.getNegation());
					if( debug == 1) System.out.println("Formula NOT tempcl:"+tempcl.toString());

					f1=f1.and(new Formula(tempcl));
					if( debug == 1) System.out.println("Formula NOT f1:"+f1.toString());

				}
			}
			if( retform.getSize() == 0 ) 
			{
				retform=f1;
			}
			else
			{
				retform=retform.or(f1);
			}
			if( debug == 1) System.out.println("Formula NOT retform:"+retform.toString());

		}

		if(debug==1) System.out.println("Formula NOT returning:"+retform.toString());
		return retform;



	}

	/**
	 * 
	 * @return number of clauses in this
	 */
	public int getSize() {
		// TODO: implement this.
		//throw new RuntimeException("not yet implemented.");
		return clauses.size();
	}

	/**
	 * @return string representation of this formula
	 */
	public String toString() {
		String result = "Problem[";
		for (Clause c : clauses)
			result += "\n" + c;
		return result + "]";
	}


}
