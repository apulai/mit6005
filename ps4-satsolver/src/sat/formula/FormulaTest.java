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
    Literal na = a.getNegation();
    Literal nb = b.getNegation();
    Literal nc = c.getNegation();

    // make sure assertions are turned on!  
    // we don't want to run test cases without assertions too.
    // see the handout to find out how to turn them on.
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
    
    
    // TODO: put your test cases here
    @Test
    public void t1empty()
    {
    	Formula f;
    	f=new Formula();
    	System.out.println(f.toString());
    	assertTrue(Boolean.TRUE);
    }
    
    @Test
    public void t1single()
    {
    	Formula f;
    	Variable var;
    	var=new Variable("Socrates");
    	f=new Formula(var);
    	System.out.println(f.toString());
    	assertTrue(Boolean.TRUE);
    }   
    
    @Test
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
    	System.out.println("Constructed formula:"+f3.toString());
    	System.out.println("Size is: "+f3.getSize());
    	System.out.println("list of caluses:"+f3.getClauses().toString());
    	
    	assertTrue(Boolean.TRUE);
    }   
    
    
    @Test
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
    	System.out.println("Constructed formula:"+f3.toString());
    	System.out.println("Size is: "+f3.getSize());
    	System.out.println("list of caluses:"+f3.getClauses().toString());
    	
    	assertTrue(Boolean.TRUE);
    }
    
    @Test
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
    	System.out.println("Constructed formula:"+f3.toString());
    	System.out.println("Size is: "+f3.getSize());
    	System.out.println("list of caluses:"+f3.getClauses().toString());
    	
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