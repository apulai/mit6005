package sat.formula;

import static org.junit.Assert.*;
import org.junit.Test;

import immutable.EmptyImList;
import sat.env.Variable;

//Formula = ImList<Clause> // a list of clauses ANDed together
//Clause = ImList<Literal> // a list of literals ORed together
//Literal = Positive(v: Var) + Negative(v:Var) // either a variable P or its negation ¬P
//Var = String

public class FormulaTest {    

	Literal a = PosLiteral.make("a");
	Literal b = PosLiteral.make("b");
	Literal c = PosLiteral.make("c");
	Literal d = PosLiteral.make("d");
	Literal na = a.getNegation();
	Literal nb = b.getNegation();
	Literal nc = c.getNegation();
	Literal nd = d.getNegation();

	// make sure assertions are turned on!  
	// we don't want to run test cases without assertions too.
	// see the handout to find out how to turn them on.
	@Test(expected=AssertionError.class)
	public void testAssertionsEnabled() {
		assert false;
	}


	// TODO: put your test cases here
	//@Test
	public void t0empty()
	{
		Formula f;
		System.out.println("---");
		f=new Formula();
		System.out.println(f.toString());
		assertTrue(Boolean.TRUE);
	}

	//@Test
	public void t1single()
	{
		Formula f;
		Variable var;
		var=new Variable("Socrates");
		f=new Formula(var);
		System.out.println("---");
		System.out.println(f.toString());
		assertTrue(Boolean.TRUE);
	}   

	//@Test
	public void t2normal()
	{
		Formula f1,f2,f3;
		Clause clause1,clause2,clause3;
		clause1 = make (a,b);
		clause2 = make (b,c,a);
		clause3 = make (na,nb);

		f1=new Formula(clause1);
		f2=f1.addClause(clause2);
		f3=f2.addClause(clause3);
		//System.out.println(f1.toString());
		System.out.println("---");
		System.out.println("Constructed formula:"+f3.toString());
		System.out.println("Size is: "+f3.getSize());
		System.out.println("list of caluses:"+f3.getClauses().toString());

		assertTrue(Boolean.TRUE);
	}   


	//@Test
	public void t3normal()
	{
		Formula f1,f2,f3;
		Variable v1,v2;
		Clause clause1,clause2,clause3;
		clause1 = make (a,b);
		clause2 = make (b,c,a);
		clause3 = make (na,nb);

		v1=new Variable("Socrates");
		v2=new Variable("Platon");
		f1=new Formula(v1);
		f2=f1.addClause(clause2);
		f3=f2.addClause(clause3);
		//System.out.println(f1.toString());
		System.out.println("---");
		System.out.println("Constructed formula:"+f3.toString());
		System.out.println("Size is: "+f3.getSize());
		System.out.println("list of caluses:"+f3.getClauses().toString());

		assertTrue(Boolean.TRUE);
	}

	//@Test
	public void t4normal()
	{
		Formula f1,f2,f3;
		Variable v1,v2;
		Clause clause1,clause2,clause3;
		clause1 = make (a,b);
		clause3 = make (na,nb);

		f1=new Formula(clause1);
		f3=f1.addClause(clause3);
		//System.out.println(f1.toString());
		System.out.println("---");
		System.out.println("Constructed formula:"+f3.toString());
		System.out.println("Size is: "+f3.getSize());
		System.out.println("list of caluses:"+f3.getClauses().toString());

		assertTrue(Boolean.TRUE);
	}

	@Test
	public void t5and()
	{
		Formula f1,f2,f3;
		Variable v1,v2;
		Clause clause1,clause2,clause3;
		clause1 = make (a,b);
		clause2 = make (c,d);
		clause3 = make (na,nb);

		f1=new Formula(clause1);
		f2=new Formula(clause3).addClause(clause2);
		//System.out.println(f1.toString());

		f3=f1.and(f2);
		System.out.println("---");
		System.out.println("AND5 test");
		System.out.println("Constructed formula:"+f3.toString());
		System.out.println("Size is: "+f3.getSize());
		System.out.println("list of caluses:"+f3.getClauses().toString());

		assertTrue(Boolean.TRUE);
	}


	@Test
	public void t6or()
	{
		Formula f1,f2,f3;
		Variable v1,v2;
		Clause clause1,clause2,clause3;
		clause1 = make (a,b);
		clause2 = make (c,d);
		clause3 = make (na,nb);

		f1=new Formula(clause1);
		f2=new Formula(clause2);

		//f2=new Formula(clause3).addClause(clause2);
		//System.out.println(f1.toString());

		f3=f1.or(f2);
		System.out.println("---");
		System.out.println("OR6 test: (a,b) or (c,d) ");
		System.out.println("Constructed formula:"+f3.toString());
		System.out.println("Size is: "+f3.getSize());
		System.out.println("list of caluses:"+f3.getClauses().toString());

		assertTrue(Boolean.TRUE);
	}

	@Test
	public void t7or()
	{
		Formula f1,f2,f3;
		Variable v1,v2;
		Clause clause1,clause2,clause3;
		clause1 = make (a,b);
		clause2 = make (c,d);
		clause3 = make (na,nb);

		f1=new Formula(clause1);
		
		f2=new Formula(clause3).addClause(clause2);
		//System.out.println(f1.toString());

		f3=f1.or(f2);
		System.out.println("---");
		System.out.println("OR7 test: (a,b) or ( (c,d) and (na,nb))");
		System.out.println("Constructed formula:"+f2.toString());
		
		System.out.println("Constructed formula:"+f3.toString());
		System.out.println("Size is: "+f3.getSize());
		System.out.println("list of caluses:"+f3.getClauses().toString());

		assertTrue(Boolean.TRUE);
	}

	@Test
	public void t8test()
	{
		//   to do (a & b) .or (c & d),
		//   you'll need to make (a | d) & (a | c) & (b | c) & (b | d)
		Formula f1,f2,f3,f4,f5;
		Variable v1,v2;
		Clause clause1,clause2,clause3, clause4;
		clause1 = make (a);
		clause2 = make (b);
		clause3 = make (c);
		clause4 = make (d);

		f1=new Formula(clause1);
		f2=new Formula(clause2);
		f3=new Formula(clause3);
		f4=new Formula(clause4);

		f5=(f1.and(f2)).or(f3.and(f4));

		//System.out.println(f1.toString());

		System.out.println("---");
		System.out.println("T8: (a and b) or (c and d)");
		System.out.println("Constructed formula:"+f5.toString());
		System.out.println("Size is: "+f5.getSize());
		System.out.println("list of caluses:"+f5.getClauses().toString());

		assertTrue(Boolean.TRUE);
	}

	@Test
	public void t9not()
	{
		//   to do (a & b) .or (c & d),
		//   you'll need to make (a | d) & (a | c) & (b | c) & (b | d)
		Formula f1,f2,f3,f4,f5,f6;
		Variable v1,v2;
		Clause clause1,clause2,clause3, clause4;
		clause1 = make (a);
		clause2 = make (b);
		clause3 = make (c);
		clause4 = make (d);

		f1=new Formula(clause1);
		f2=new Formula(clause2);
		f3=new Formula(clause3);
		f4=new Formula(clause4);

		f5=(f1.and(f2)).or(f3.and(f4));
		f6=f5.not(f5);
		//System.out.println(f1.toString());

		System.out.println("---");
		
		System.out.println("T9not: not( ( a and b ) or (c and d)) ");
		System.out.println("Constructed formula:"+f5.toString());
		System.out.println("Constructed nEGATED formula:"+f6.toString());
		
		System.out.println("Size is: "+f5.getSize());
		System.out.println("list of caluses:"+f5.getClauses().toString());

		assertTrue(Boolean.TRUE);
	}
	@Test
	public void t10not()
	{
		//   if you start with (a | b) & c,
		//   you'll need to make !((a | b) & c) 
		//                       => (!a & !b) | !c            (moving negation down to the literals)
		//                       => (!a | !c) & (!b | !c)    (conjunctive normal form)
		Formula f1,f2,f3,f4,f5;
		Variable v1,v2;
		Clause clause1,clause2,clause3, clause4;
		clause1 = make (a,b);
		clause2 = make (c);

		f1=new Formula(clause1);
		f2=new Formula(clause2);
		f3=f1.and(f2);

		//f5=(f1.and(f2)).or(f3.and(f4));
		f5=f3.not(f3);
		//System.out.println(f1.toString());

		System.out.println("---");
		System.out.println("T10not: !((a | b) & c) ");
		System.out.println("Constructed formula:"+f5.toString());
		System.out.println("Size is: "+f5.getSize());
		System.out.println("list of caluses:"+f5.getClauses().toString());

		assertTrue(Boolean.TRUE);
	}




	//(a or not b) and (a or b) should return a: True, b: anything
	//(a and b) and (a and not b) should return: null
	//(a and b) and (not b or c) should return: a: True, b: True, c: True.  

	// Helper function for constructing a clause.  Takes
	// a variable number of arguments, e.g.
	//  clause(a, b, c) will make the clause (a or b or c)
	// @param e,...   literals in the clause
	// @return clause containing e,...
	private Clause make(Literal... e) {
		Clause c = new Clause();
		for (int i = 0; i < e.length; ++i) {
			c = c.add(e[i]);
		}
		return c;
	}
}