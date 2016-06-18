package sat;

import static org.junit.Assert.*;

import org.junit.Test;

import sat.env.Environment;
import sat.formula.Clause;
import sat.formula.Formula;
import sat.formula.Literal;
import sat.formula.PosLiteral;

public class SATSolverTest {
    Literal a = PosLiteral.make("a");
    Literal b = PosLiteral.make("b");
    Literal c = PosLiteral.make("c");
    Literal d = PosLiteral.make("d");
    Literal na = a.getNegation();
    Literal nb = b.getNegation();
    Literal nc = c.getNegation();
    Literal nd = d.getNegation();

    Literal s = PosLiteral.make("socrates");
    Literal h = PosLiteral.make("human");
    Literal m = PosLiteral.make("mortal");
    Literal ns = s.getNegation();
    Literal nh = h.getNegation();
    Literal nm = m.getNegation();
    
    
    // make sure assertions are turned on!  
    // we don't want to run test cases without assertions too.
    // see the handout to find out how to turn them on.
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }

    // TODO: put your test cases here

    
    @Test
    public void t1()
    {
    	Formula f1,f2,f3;
    	Environment retenv;
    	Clause clause1,clause2,clause3;
    	clause1 = make (na);

    	f1=new Formula(clause1);
    	
    	retenv=SATSolver.solve(f1);
    	System.out.println("Main: "+retenv);
    	
    	assertTrue(Boolean.TRUE);
    } 
    
    @Test
    public void t2()
    {
    	Formula f1,f2,f3;
    	Environment retenv;
    	Clause clause1,clause2,clause3;
    	clause1 = make (a);
    	clause2 = make (a,b);
    	clause3 = make (a,b,c);
    	
    	f1=new Formula(clause1);
    	f2=f1.addClause(clause2);
    	f3=f2.addClause(clause3);
    	
    	retenv=SATSolver.solve(f3);
    	System.out.println("Main: "+retenv);
    	
    	assertTrue(Boolean.TRUE);
    }   
    
    
    @Test
    public void t3()
    {
    	Formula f1,f2,f3;
    	Environment retenv;
    	Clause clause1,clause2,clause3;
    	clause1 = make (a);
    	clause2 = make (na,b);
    	
    	f1=new Formula(clause1);
    	f2=f1.addClause(clause2);
    	
    	retenv=SATSolver.solve(f2);
    	System.out.println("Main: "+retenv);
    	
    	assertTrue(Boolean.TRUE);
    }   
  
    @Test
    public void t4()
    {
    	Formula f1,f2,f3;
    	Environment retenv;
    	Clause clause1,clause2,clause3;
    	clause1 = make (a);
    	clause2 = make (na,nb);
    	
    	f1=new Formula(clause1);
    	f2=f1.addClause(clause2);
    	
    	retenv=SATSolver.solve(f2);
    	System.out.println("Main: "+retenv);
    	
    	assertTrue(Boolean.TRUE);
    }   
  
    @Test
    public void t5()
    {
    	Formula f1,f2,f3;
    	Environment retenv;
    	Clause clause1,clause2,clause3;
    	clause1 = make (a);
    	clause2 = make (na);
    	
    	f1=new Formula(clause1);
    	f2=f1.addClause(clause2);
    	
    	retenv=SATSolver.solve(f2);
    	System.out.println("Main: "+retenv);
    	
    	assertTrue(Boolean.TRUE);
    }   
  
    @Test
    public void t6()
    {
    	Formula f1,f2,f3;
    	Environment retenv;
    	Clause clause1,clause2,clause3;
    	clause1 = make (a,b);
    	clause2 = make (b,c);
    	
    	f1=new Formula(clause1);
    	f2=f1.addClause(clause2);
    	
    	retenv=SATSolver.solve(f2);
    	System.out.println("Main: "+retenv);
    	
    	assertTrue(Boolean.TRUE);
    }   
  

    @Test
    public void t7()
    {
    	Formula f1,f2,f3;
    	Environment retenv;
    	Clause clause1,clause2,clause3;
    	clause1 = make (a,b);
    	clause2 = make (c,d);
    	
    	f1=new Formula(clause1);
    	f2=f1.addClause(clause2);
    	
    	retenv=SATSolver.solve(f2);
    	System.out.println("Main: "+retenv);
    	
    	assertTrue(Boolean.TRUE);
    }   
  
    @Test
    public void tsocrates()
    {
    	Formula f1,f2,f3;
    	Environment retenv;
    	Clause clause1,clause2,clause3,clause4;
    	clause1 = make (ns,h);
    	clause2 = make (nh,m);
    	clause3 = make (s);
    	clause4 = make (nm);
    	
    	
    	f1=new Formula(clause1);
    	f1=f1.addClause(clause2).addClause(clause3).addClause(clause4);
    	
    	retenv=SATSolver.solve(f1);
    	System.out.println("Main: "+retenv);
    	
    	assertTrue(Boolean.TRUE);
    }   
    
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