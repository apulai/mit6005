package calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Multi-unit calculator.
 */
public class MultiUnitCalculator {

	/**
	 * @param expression
	 *            a String representing a multi-unit expression, as defined in
	 *            the problem set
	 * @return the value of the expression, as a number possibly followed by
	 *         units, e.g. "72pt", "3", or "4.882in"
	 */
	//public String evaluate(String expression) {
	//	// TODO implement for Problem 4
	//}

	/**
	 * Repeatedly reads expressions from the console, and outputs the results of
	 * evaluating them. Inputting an empty line will terminate the program.5
	 * 
	 * @param args5
	 *            unused
	 */
	public static void main(String[] args) throws IOException {
		MultiUnitCalculator calculator;
		String result;
		Lexer lex1;
		Parser par1;
		
		//BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//String expression;
		//while (true) {
			// display prompt
			//System.out.print("> ");
			// read input
			//expression = in.readLine();
			// terminate if input empty
			//if (expression.equals(""))
			//	break;

			// evaluate
		
			lex1= new Lexer("(3+2.4)in");
			par1= new Parser(lex1);
			par1.evaluate();
	    
			lex1= new Lexer("(3in * 2.4) pt");
			par1= new Parser(lex1);
			par1.evaluate();
		
		    //lex1= new Lexer("(1in+2in+3in)");
		    //par1= new Parser(lex1);
		    //par1.evaluate();
		    
		    //lex1= new Lexer("(1+2+3)*3in=");
		    //par1= new Parser(lex1);
		    //par1.evaluate();
		    
			//lex1= new Lexer("1 in");
			//par1= new Parser(lex1);
			//par1.evaluate();

			//lex1= new Lexer("1=");
			//par1= new Parser(lex1);
			//par1.evaluate();
			
			
			//lex1= new Lexer("");
			//par1= new Parser(lex1);
			//par1.evaluate();
			
			//lex1= new Lexer("=");
			//par1= new Parser(lex1);
			//par1.evaluate();
		    
			//lex1= new Lexer("1+2*3");
			//par1= new Parser(lex1);
			//par1.evaluate();
		    
			
			//lex1= new Lexer(" ( 25 in + 3.2 pt ) * 2 + 1");
			//par1= new Parser(lex1);
			//par1.evaluate();
			
			//lex1= new Lexer(" ( 25 in + 3.2 pt ) * 2 + 1 =");
			//par1= new Parser(lex1);
			//par1.evaluate();
			
			//calculator = new MultiUnitCalculator();
			//result = calculator.evaluate(expression);
			// display result
			//System.out.println(result);
		//}
	}
}
