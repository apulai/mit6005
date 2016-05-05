package calculator;

import calculator.Type;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Calculator lexical analyzer.
 */
public class Lexer {

	/**
	 * Token in the stream.
	 */
	public static class Token {
		final Type type;
		final String text;

		Token(Type type, String text) {
			this.type = type;
			this.text = text;
		}

		Token(Type type) {
			this(type, null);
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
		
		String getText()
		{
			return this.text;
		}

		Type getType()
		{
			return this.type;
		}

		
	}

	@SuppressWarnings("serial")
	static class TokenMismatchException extends Exception {
	}

	final List<Token> myexpression;
	char[] tokenchr;
	int idxcurrenttoken=0;
	Token t;
	int debug=0;
	String pattern = "\\d+(\\.\\d*)?(in|pt)*|in|pt|\\+|-|\\*|/|\\(|\\)";
	//String pattern1 = "\\d+(\\.\\d*)?|in|pt|\\+|-|\\*|/|\\(|\\)";
	// TODO write method spec
	public Lexer(String input) {


		myexpression = new ArrayList<Token>();

		// Kitörlünk miden whitespace-t és utána belerakjuk egy karakter tömbbe.
		input= input.toLowerCase().replaceAll("\\s", "");
		tokenchr=input.toCharArray();
		if( debug == 1 ) System.out.println(Arrays.toString(tokenchr));

		// Create a Pattern object  
		Pattern r = Pattern.compile(pattern);

		// Now create matcher object.
		Matcher m = r.matcher(input);
		int count = 0;
		int tokenlen = 0;
		int lastend=0;

		while(m.find()) {
			count++;
			tokenchr=input.subSequence(m.start(), m.end()).toString().toCharArray();
			tokenlen=m.end()-m.start();
			
			if( lastend != m.start()) 
				System.out.println("BAD INPUT AT "+lastend+ ":"+ m.start()+" "+ input.substring(lastend, m.start()));
			lastend=m.end();
			
			if( debug == 1 ) System.out.println("Match number "+count+" start(): "+m.start()+" end(): "+m.end());
			if( debug == 1 ) System.out.println("Token(): "+new String(tokenchr));

			if( tokenlen == 1 ){

				switch(tokenchr[0])
				{
				case '+':
					t=new Token(Type.PLUS, "+");
					myexpression.add(t);
					continue;
				case '-':
					t=new Token(Type.MINUS, "-");
					myexpression.add(t);
					continue;
				case '*':
					t=new Token(Type.MUL, "*");
					myexpression.add(t);
					continue;
				case '/':
					t=new Token(Type.DIV, "/");
					myexpression.add(t);
					continue;
				case '(':
					t=new Token(Type.OPENP, "(");
					myexpression.add(t);
					continue;
				case ')':
					t=new Token(Type.CLOSEP, ")");
					myexpression.add(t);
					continue;
				case '=':
					t=new Token(Type.EQUAL, "=");
					myexpression.add(t);
					continue;
				}
			}
			// nem egykarakteres
			// akkor lehet szám, IN vagy pt
				if( input.subSequence(m.start(), m.end()).toString().endsWith("in") )
				{
					//Na akkor ez egy INCH típusú
					if( debug == 1 ) System.out.println("INCH: "+new String(tokenchr));
					t=new Token(Type.IN, new String(tokenchr));
					myexpression.add(t);
				}
				else
				{
					if( input.subSequence(m.start(), m.end()).toString().endsWith("pt") )
					{
						//Na akkor ez egy PT típusú
						if( debug == 1 ) System.out.println("PT: "+new String(tokenchr));
						t=new Token(Type.PT, new String(tokenchr));
						myexpression.add(t);
					}
					else
					{
						//Na akkor ez egy szám
						if( debug == 1 ) System.out.println("NUM: "+new String(tokenchr));
						t=new Token(Type.NUM, new String(tokenchr));
						myexpression.add(t);
					}
					
				}

		}

	}

	public boolean hasNext()
	{
		if( idxcurrenttoken < myexpression.size() ) return true;
		return false;
	}

	public Token next()
	{
		idxcurrenttoken++;
		return myexpression.get(idxcurrenttoken-1);
	}
	
	String getCurrentTokenText()
	{
		return myexpression.get(idxcurrenttoken).getText();
	}
	
	Type getCurrentTokenType()
	{
		return myexpression.get(idxcurrenttoken).getType();
	}
	
	
}

/* ROssz lexer: egyáltalán nem használta a regext pattern lehetõséget
 * public Lexer(String input) {


		myexpression = new ArrayList<Token>();

		// Kitörlünk miden whitespace-t és utána belerakjuk egy karakter tömbbe.
		in= input.toLowerCase().replaceAll("\\s", "").toCharArray();

		if( debug == 1 ) System.out.println(Arrays.toString(in));
		//Most betûnként végigmegyünk rajta

		nextlexem=0;
		currentlexem=0;
		i=0;
		sb = new StringBuilder();
		while (i < in.length)
		{	
			switch(in[i])
			{
			case '+':
				if( nextlexem != 0 ) nextlexem++;
				t=new Token(Type.PLUS, "+");
				if( debug == 1 ) System.out.println("PLUS: "+nextlexem);
				sb=new StringBuilder();
				myexpression.add(nextlexem,t);
				nextlexem++;
				break;
			case '-':
				if( nextlexem != 0 ) nextlexem++;
				t=new Token(Type.MINUS, "-");
				if( debug == 1 ) System.out.println("MINUS: "+nextlexem);
				sb=new StringBuilder();
				myexpression.add(nextlexem,t);
				nextlexem++;
				break;
			case '*':
				if( nextlexem != 0 ) nextlexem++;
				t=new Token(Type.MUL, "*");
				if( debug == 1 ) System.out.println("MUL: "+nextlexem);
				sb=new StringBuilder();
				myexpression.add(nextlexem,t);
				nextlexem++;
				break;
			case '/':
				if( nextlexem != 0 ) nextlexem++;
				t=new Token(Type.DIV, "/");
				if( debug == 1 ) System.out.println("DIV: "+nextlexem);
				sb=new StringBuilder();
				myexpression.add(nextlexem,t);
				nextlexem++;
				break;
			case '(':
				if( nextlexem != 0 ) nextlexem++;
				t=new Token(Type.OPENP, "(");
				if( debug == 1 ) System.out.println("OPENP: "+nextlexem);
				sb=new StringBuilder();
				myexpression.add(nextlexem,t);
				nextlexem++;
				break;
			case ')':
				if( nextlexem != 0 ) nextlexem++;
				t=new Token(Type.CLOSEP, "(");
				if( debug == 1 ) System.out.println("CLOSEP: "+nextlexem);
				sb=new StringBuilder();
				myexpression.add(nextlexem,t);
				nextlexem++;
				break;
			case '.':
				sb=sb.append(in[i]);
				t=new Token(Type.NUM, new String(sb));
				if( debug == 1 ) System.out.println("NUM: "+nextlexem+" i:"+i+" Sb:"+sb.toString());
				myexpression.add(nextlexem,t);
				break;
			}

			//megpróbáljuk felismerni a számokat
			// szám | szám in | szám pt formában
			if( Character.isDigit(in[i]) )
			{
				sb=sb.append(in[i]);
				t=new Token(Type.NUM, new String(sb));
				if( debug == 1 ) System.out.println("NUM: "+nextlexem+" i:"+i+" Sb:"+sb.toString());
				myexpression.add(nextlexem,t);
			}
			else
			{
				if( i < in.length-1)
				{ 
					if( in[i] == 'i' && in[i+1]== 'n' )
					{
						//Na akkor ez egy INCH típusú
						if( debug == 1 ) System.out.println("INCH: "+nextlexem+" i:"+i+" Sb:"+sb.toString());
						t=new Token(Type.IN, new String(sb));
						myexpression.add(nextlexem,t);
						i++;
					}
					else
					{
						if( in[i] == 'p' && in[i+1]== 't' )
						{
							//Na akkor ez egy PT típusú
							if( debug == 1 ) System.out.println("PT: "+nextlexem+" i:"+i+" Sb:"+sb.toString());
							t=new Token(Type.PT, new String(sb));
							myexpression.add(nextlexem,t);
							i++;
						}
					}
					// Ha idejön a vezérlés, akkor hülyeséget írtak be!
				}	 
				// vagy ha idejön, akkor is!
			}

		i++;	
		//while vege
		}
		// TODO implement for Problem 2

		// Ez még hasznos lesz, ezért belerakunk egy EOF-ot a végére
		if( nextlexem != 0 ) nextlexem++;
		t=new Token(Type.EOF, "EOF");
		myexpression.add(nextlexem,t);

		System.out.println( myexpression.toString() ); 
	}
 * 
 */

