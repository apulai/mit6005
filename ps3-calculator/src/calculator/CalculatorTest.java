package calculator;

import static org.junit.Assert.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;



//import piwords.WordFinder;

public class CalculatorTest {

	// TODO write tests for MultiUnitCalculator.evaluate

	boolean approxEquals(String expr1, String expr2, boolean compareUnits) {
		return new Value(expr1).approxEquals(new Value(expr2), compareUnits);
	}

	static class Value {
		static float delta = 0.001f;
 
		enum Unit {
			POINT, INCH, SCALAR
		}

		Unit unit;
		// in points if a length
		float value;

		Value(String value) {
			value = value.trim();
			if (value.endsWith("pt")) {
				unit = Unit.POINT;
				this.value = Float.parseFloat(value.substring(0,
						value.length() - 2).trim());
			} else if (value.endsWith("in")) {
				unit = Unit.INCH;
				this.value = 72 * Float.parseFloat(value.substring(0,
						value.length() - 2).trim());
			} else {
				unit = Unit.SCALAR;
				this.value = Float.parseFloat(value);
			}
		}

		boolean approxEquals(Value that, boolean compareUnits) {
			return (this.unit == that.unit || !compareUnits)
					&& Math.abs(this.value - that.value) < delta;
		}
	}

	@Test
    public void emptyinput() {
		String parse_result;
		Lexer lex1;
		Parser par1;

		
		lex1= new Lexer("");
		par1= new Parser(lex1);
		parse_result=par1.evaluate().toString();
		
		assertTrue(approxEquals("", parse_result , true));
		 
	}
	
	@Test
	public void singleiteminput() {
		String parse_result;
		Lexer lex1;
		Parser par1;

		
		lex1= new Lexer("1 in =");
		par1= new Parser(lex1);
		parse_result=par1.evaluate().toString();
		
		assertTrue(approxEquals("1 in", parse_result , true));
			}
	
	@Test
    public void simple_add_in() {
		String parse_result;
		Lexer lex1;
		Parser par1;

		
		lex1= new Lexer("(1in+(2in)+3in)");
	    par1= new Parser(lex1);
	    parse_result=par1.evaluate().toString();
	    
	    assertTrue(approxEquals("6 in", parse_result , true));
        
	}
	
	@Test
	public void t1() {
		String parse_result;
		Lexer lex1;
		Parser par1;
		
		lex1= new Lexer("3+2.4=");
	    par1= new Parser(lex1);
	    parse_result=par1.evaluate().toString();
	    
	    assertTrue(approxEquals("5.4", parse_result , true));
	   }
	
	public void t2() {
		String parse_result;
		Lexer lex1;
		Parser par1;
		
		lex1= new Lexer(" 3 + 2.4  = ");
	    par1= new Parser(lex1);
	    parse_result=par1.evaluate().toString();
	    
	    assertTrue(approxEquals("5.4", parse_result , true));
	    }
	
	@Test
	public void t3() {
		String parse_result;
		Lexer lex1;
		Parser par1;
		
		lex1= new Lexer(" 1 - 2.4  = ");
	    par1= new Parser(lex1);
	    parse_result=par1.evaluate().toString();
	    
	    assertTrue(approxEquals("-1.4", parse_result , true));
	    }
	
	@Test
	public void t4() {
		String parse_result;
		Lexer lex1;
		Parser par1;
		
		lex1= new Lexer("(3 + 4)*2.4  = ");
	    par1= new Parser(lex1);
	    parse_result=par1.evaluate().toString();
	    parse_result=par1.evaluate().toString();
	    
	    assertTrue(approxEquals("16.8", parse_result , true));
		}
	
	@Test
	public void t5() {
		String parse_result;
		Lexer lex1;
		Parser par1;
		
		lex1= new Lexer("3 + 2.4in  = ");
	    par1= new Parser(lex1);
	    parse_result=par1.evaluate().toString();
	    
	    System.out.println("T5: prase restult: "+parse_result);
	    assertTrue(approxEquals("5.4in", parse_result , true));
	    }
	
	@Test
	public void t6() {
		String parse_result;
		Lexer lex1;
		Parser par1;
		Value v1,v2;
		
		lex1= new Lexer("3pt * 2.4in =");
	    par1= new Parser(lex1);
	    parse_result=par1.evaluate().toString();
	    
	    System.out.println("T6: prase result: "+parse_result);
	    assertTrue(approxEquals("518.4pt", parse_result , true));
	}
	
	@Test
	public void t7() {
		String parse_result;
		Lexer lex1;
		Parser par1;
		Value v1,v2;
		
		lex1= new Lexer("3in * 2.4 =");
	    par1= new Parser(lex1);
	    parse_result=par1.evaluate().toString();
	    
	    System.out.println("T7: prase result: "+parse_result);
	    assertTrue(approxEquals("7.2in", parse_result , true));
	}

	@Test
	public void t8() {
		String parse_result;
		Lexer lex1;
		Parser par1;
		Value v1,v2;
		
		lex1= new Lexer("4pt+(3in*2.4) =");
	    par1= new Parser(lex1);
	    parse_result=par1.evaluate().toString();
	    
	    System.out.println("T8: prase result: "+parse_result);
	    assertTrue(approxEquals("522.4pt", parse_result , true));
	     
	}

	@Test
	public void t9() {
		String parse_result;
		Lexer lex1;
		Parser par1;
		Value v1,v2;
		
		lex1= new Lexer("4pt+((3in*2.4)) =");
	    par1= new Parser(lex1);
	    parse_result=par1.evaluate().toString();
	    
	    System.out.println("T9: prase result: "+parse_result);
	    assertTrue(approxEquals("522.4pt", parse_result , true));
	      
	}
	
	@Test
	public void t10() {
		String parse_result;
		Lexer lex1;
		Parser par1;
		Value v1,v2;
		
		lex1= new Lexer("(3 + 2.4) in =");
	    par1= new Parser(lex1);
	    parse_result=par1.evaluate().toString();
	    
	    System.out.println("T10: prase result: "+parse_result);
	    assertTrue(approxEquals("5.4in", parse_result , true));
	      
	}
	
	@Test
	public void t11() {
		String parse_result;
		Lexer lex1;
		Parser par1;
		Value v1,v2;
		
		lex1= new Lexer("(3in * 2.4) pt =");
	    par1= new Parser(lex1);
	    parse_result=par1.evaluate().toString();
	    
	    System.out.println("T11: prase result: "+parse_result);
	    assertTrue(approxEquals("518.4pt", parse_result , true));
	  
	}
	

	
}
