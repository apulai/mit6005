package calculator;

import calculator.Lexer;
import calculator.CalculatorTest.Value;
import calculator.CalculatorTest.Value.Unit;
import calculator.Lexer.Token;

/*
 * 
 * GRAMMAR:
   expr :== (tag | tag + tag | tag - tag )
   tag :== (prod | prod * prod | prod / prod )
   prod :== ( in | pt | num | ( exp ) | ( exp )(in|pt) )
   in :== num in
   pt :== num pt
   num  :== ( part | part.part )
   part :== [0-9]+
 */

/**
 * Calculator parser. All values are measured in pt.
 */
class Parser {

	@SuppressWarnings("serial")
	static class ParserException extends RuntimeException {
	}

	/**
	 * Type of values.
	 */
	private enum ValueType {
		POINTS, INCHES, SCALAR
	};

	/**
	 * Internal value is always in points.
	 */
	public class Value {
		final double value;
		final ValueType type;

		Value(double value, ValueType type) {
			this.value = value;
			this.type = type;
		}

		@Override
		public String toString() {
			switch (type) {
			case INCHES:
				return value / PT_PER_IN + " in";
			case POINTS:
				return value + " pt";
			default:
				return "" + value;
			}
		}

		// addition use the type of the fist operand



	}

	private static final double PT_PER_IN = 72;

	private final Lexer lexer;




	public static class pToken {
		//parser token
		// same as lextoken
		final Type type;
		final String text;

		pToken(Token t) {
			this.type = t.type;
			this.text = t.text;
		}

		public String toString()
		{
			StringBuilder sb;
			sb=new StringBuilder("Type: ");
			sb=sb.append(this.type);
			sb=sb.append(" Text: ");
			sb=sb.append(this.text);
			sb=sb.append(" ");
			return sb.toString();
		}

	}


	pToken pt;
	int neednewtoken=1;
	int debug=0;
	// TODO write method spec
	Parser(Lexer lexer) {
		// TODO implement for Problem 3
		this.lexer=lexer;	
	}

	private pToken getnexttoken()
	{
		if( neednewtoken == 1 ) 
		{
			if( lexer.hasNext())
			{
				pt=new pToken(lexer.next());
				neednewtoken=0;
				return pt;
			}
		} 
		return pt;
	}


	public Value addvalues(Value left, Value right)
	{
		Value v;
		double retval;
		ValueType retvaltype;

		retval=left.value+right.value;
		retvaltype=left.type;
		if( left.type == ValueType.SCALAR && right.type == ValueType.INCHES ) 
		{
			retval=72*left.value+right.value;
			retvaltype = ValueType.INCHES;
		}
		
		if( left.type == ValueType.SCALAR && right.type == ValueType.SCALAR ) retvaltype=ValueType.SCALAR;
		if( left.type == ValueType.SCALAR && right.type == ValueType.POINTS ) retvaltype=ValueType.POINTS;
		if( left.type == ValueType.SCALAR && right.type == ValueType.INCHES ) retvaltype=ValueType.INCHES;
		
		if( left.type == ValueType.POINTS && right.type == ValueType.SCALAR ) retvaltype=ValueType.POINTS;
		if( left.type == ValueType.POINTS && right.type == ValueType.POINTS ) retvaltype=ValueType.POINTS;
		if( left.type == ValueType.POINTS && right.type == ValueType.INCHES ) retvaltype=ValueType.POINTS;
		
		if( left.type == ValueType.INCHES && right.type == ValueType.SCALAR ) retvaltype=ValueType.INCHES;
		if( left.type == ValueType.INCHES && right.type == ValueType.POINTS ) retvaltype=ValueType.INCHES;
		if( left.type == ValueType.INCHES && right.type == ValueType.INCHES ) retvaltype=ValueType.INCHES;
		
		
		v=new Value(retval, retvaltype );
		return v;
	}

	public Value subtractvalues(Value left, Value right)
	{
		Value v;
		double retval;
		ValueType retvaltype;

		retval=left.value-right.value;
		retvaltype=left.type;
		if( left.type == ValueType.SCALAR && right.type == ValueType.INCHES ) 
		{
			retval=72*left.value-right.value;
			retvaltype = ValueType.INCHES;
		}
		
		if( left.type == ValueType.SCALAR && right.type == ValueType.SCALAR ) retvaltype=ValueType.SCALAR;
		if( left.type == ValueType.SCALAR && right.type == ValueType.POINTS ) retvaltype=ValueType.POINTS;
		if( left.type == ValueType.SCALAR && right.type == ValueType.INCHES ) retvaltype=ValueType.INCHES;
		
		if( left.type == ValueType.POINTS && right.type == ValueType.SCALAR ) retvaltype=ValueType.POINTS;
		if( left.type == ValueType.POINTS && right.type == ValueType.POINTS ) retvaltype=ValueType.POINTS;
		if( left.type == ValueType.POINTS && right.type == ValueType.INCHES ) retvaltype=ValueType.POINTS;
		
		if( left.type == ValueType.INCHES && right.type == ValueType.SCALAR ) retvaltype=ValueType.INCHES;
		if( left.type == ValueType.INCHES && right.type == ValueType.POINTS ) retvaltype=ValueType.INCHES;
		if( left.type == ValueType.INCHES && right.type == ValueType.INCHES ) retvaltype=ValueType.INCHES;
			
		//if( left.type == ValueType.INCHES && right.type == ValueType.POINTS ) retvaltype = ValueType.INCHES;
		//if( left.type == ValueType.POINTS && right.type == ValueType.INCHES ) retvaltype = ValueType.POINTS;
		

		v=new Value(retval, retvaltype );
		return v;
	}

	public Value dividevalues(Value left, Value right)
	{
		Value v;
		double retval;
		ValueType retvaltype;

		retvaltype=left.type;
		retval=0;
		
		if( left.type == ValueType.SCALAR && right.type == ValueType.SCALAR ) retvaltype=ValueType.SCALAR;
		if( left.type == ValueType.SCALAR && right.type == ValueType.POINTS ) retvaltype=ValueType.POINTS;
		if( left.type == ValueType.SCALAR && right.type == ValueType.INCHES ) retvaltype=ValueType.INCHES;
		
		if( left.type == ValueType.POINTS && right.type == ValueType.SCALAR ) retvaltype=ValueType.POINTS;
		if( left.type == ValueType.POINTS && right.type == ValueType.POINTS ) retvaltype=ValueType.SCALAR;
		if( left.type == ValueType.POINTS && right.type == ValueType.INCHES ) retvaltype=ValueType.SCALAR;
		
		if( left.type == ValueType.INCHES && right.type == ValueType.SCALAR ) retvaltype=ValueType.INCHES;
		if( left.type == ValueType.INCHES && right.type == ValueType.POINTS ) retvaltype=ValueType.SCALAR;
		if( left.type == ValueType.INCHES && right.type == ValueType.INCHES ) retvaltype=ValueType.SCALAR;
		
		if( right.value !=0 ) retval=left.value/right.value;

		retvaltype=left.type;
		v=new Value(retval, retvaltype );
		return v;
	}

	public Value multiplyvalues(Value left, Value right)
	{
		Value v;
		double retval;
		ValueType retvaltype;

		retvaltype=left.type;

		if( left.type == ValueType.SCALAR && right.type == ValueType.SCALAR ) retvaltype=ValueType.SCALAR;
		if( left.type == ValueType.SCALAR && right.type == ValueType.POINTS ) retvaltype=ValueType.POINTS;
		if( left.type == ValueType.SCALAR && right.type == ValueType.INCHES ) retvaltype=ValueType.INCHES;
		
		if( left.type == ValueType.POINTS && right.type == ValueType.SCALAR ) retvaltype=ValueType.POINTS;
		if( left.type == ValueType.POINTS && right.type == ValueType.POINTS ) retvaltype=ValueType.POINTS;
		if( left.type == ValueType.POINTS && right.type == ValueType.INCHES ) retvaltype=ValueType.INCHES;
		
		if( left.type == ValueType.INCHES && right.type == ValueType.SCALAR ) retvaltype=ValueType.INCHES;
		if( left.type == ValueType.INCHES && right.type == ValueType.POINTS ) retvaltype=ValueType.INCHES;
		if( left.type == ValueType.INCHES && right.type == ValueType.INCHES ) retvaltype=ValueType.INCHES;
		
		
		retval=left.value*right.value;

		retvaltype=left.type;
		v=new Value(retval, retvaltype );
		return v;
	}


	private Value evalProd()
	{
		// Ez az a rész, amikor már csak a szám lehet
		// vagy egy új expression
		// vagy egy új típus konverzió
		Value rv;


		rv=new Value(0, ValueType.SCALAR);
		pt=getnexttoken();

		if(debug==1) System.out.println("EvalProd:"+pt);

		switch(pt.type)
		{
		case NUM:
			neednewtoken=1;
			rv = new Value ( Float.parseFloat(pt.text), ValueType.SCALAR);
			break;
		case IN:
			neednewtoken=1;
			rv = new Value( 72 * Float.parseFloat(pt.text.substring(0, pt.text.length() - 2).trim()), ValueType.INCHES);
			break;
		case PT:
			neednewtoken=1;
			rv= new Value ( Float.parseFloat(pt.text.substring(0,pt.text.length() - 2).trim()), ValueType.POINTS) ;
			break;
		case OPENP:
			if(debug==1) System.out.println("\tZárjel nyit:( "+pt);
			neednewtoken=1;
			rv = evalexp();
			if(debug==1) System.out.println("\tZárjel zár:) "+pt);
			pt=getnexttoken();
			if( pt.type == Type.CLOSEP)
			{
				neednewtoken=1;
			}
			else
				if(debug==1) System.out.println("HIBA: nem jött meg a )! ");

			pt=getnexttoken();
			if( pt.type == Type.IN )
			{
				neednewtoken=1;
				rv=new Value( rv.value*72.0 , ValueType.INCHES);
			}
			else
			{
				if( pt.type == Type.PT )
				{
					neednewtoken=1;
					rv= new Value( rv.value, ValueType.POINTS);
				}	
			}

			return rv;
			//break;
		default:
			if(debug==1) System.out.println("HIBA: nem jót kaptunk");
			//rv=0;
		}

		return rv;
	}

	private Value evalTerm()
	{
		// itt van a szorzás osztás
		Value left,right;
		if(debug==1) System.out.println("EvalTerm: left "+pt);
		left = evalProd();

		do
		{	
			if(debug==1) System.out.println("EvalTerm: right "+pt);
			pt=getnexttoken(); 
			switch(pt.type)
			{
			case MUL:
				neednewtoken=1;
				right = evalProd();
				left =  multiplyvalues(left,right);
				break;
			case DIV:
				neednewtoken=1;
				right = evalProd();
				left = dividevalues ( left , right);
				break;
			}
		}
		while ( pt.type == Type.MUL || pt.type==Type.DIV  );
		return left;
	}

	private Value evalexp()
	{
		// itt van a osszeadas kivonas
		Value left,right;

		left=new Value(0,ValueType.SCALAR);
		if( lexer.hasNext() )
		{
			if(debug==1) System.out.println("EvalExp: left "+pt);
			left = evalTerm();	

			do
			{	
				pt=getnexttoken();  
				if(debug==1) System.out.println("EvalExp: right "+pt);
				switch(pt.type)
				{
				case PLUS:
					neednewtoken=1;
					right = evalTerm();

					left = addvalues( left , right);
					break;
				case MINUS:
					neednewtoken=1;
					right = evalTerm();
					left  = subtractvalues( left , right);
					break;
				case EQUAL:
					return left;
				}

			}			
			while ( (pt.type == Type.PLUS || pt.type==Type.MINUS ) && lexer.hasNext() );
		}

		return left;
	}


	// TODO write method spec
	// input: none
	// return: value of the parsed expression
	// modifies: resets the lexer's internal pointer, so it can be reused
	
	public Value evaluate() {
		Value v;
		v=evalexp();
		lexer.idxcurrenttoken=0;
		if(debug==1 ) System.out.println("Parse: visszatérési érték: "+v.toString());
		return v;

	}
}