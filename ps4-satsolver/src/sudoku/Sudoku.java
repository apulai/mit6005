/**
 * Author: dnj, Hank Huang
 * Date: March 7, 2009
 * 6.005 Elements of Software Construction
 * (c) 2007-2009, MIT 6.005 Staff
 */
package sudoku;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import sat.env.Bool;
import sat.env.Environment;
import sat.env.Variable;
import sat.formula.Clause;
import sat.formula.Formula;
import sat.formula.Literal;
import sat.formula.NegLiteral;
import sat.formula.PosLiteral;

/**
 * Sudoku is an immutable abstract datatype representing instances of Sudoku.
 * Each object is a partially completed Sudoku puzzle.
 */
public class Sudoku {
	// Rep invariant:
	//      each number must be smaller than dim*dim (in practice less than 10)
	//		each number can be present in each row only once
	//		each number can be present in each column only once
	//		each number can be present only once in every "subsquar" (not checked yet)
	//

	// dimension: standard puzzle has dim 3
	private final int dim;
	// number of rows and columns: standard puzzle has size 9
	private final int size;
	// known values: square[i][j] represents the square in the ith row and jth
	// column,
	// contains -1 if the digit is not present, else i>=0 to represent the digit
	// i+1
	// (digits are indexed from 0 and not 1 so that we can take the number k
	// from square[i][j] and
	// use it to index into occupies[i][j][k])
	private final int[][] square;
	// occupies [i,j,k] means that kth symbol occupies entry in row i, column j
	private final Variable[][][] occupies;

	// Rep invariant
	// TODO: write your rep invariant here
	private void checkRep() {
		// TODO: implement this.

		// Ne legyen benne negativ szam, csak a -1
		// Ne legyen benne nagyobb szam, mint dim*dim

		int i,j,val,maxval;

		//System.out.println("Sudoku: CheckRep: Test1 running...");
		maxval=dim*dim;

		// Ezt vedd ki

		for(i=0;i<this.size;i++)
		{
			for(j=0;j<this.size;j++)
			{
				val=square[i][j];
				if( val < -1 || val > maxval) throw new RuntimeException("CheckRep: Test1: Hibas ertek !");
			}
		}


		// minden sorban 1 szam csak 1szer szerepelhet
		//System.out.println("Sudoku: CheckRep: Test2 running...");
		for(i=0;i<this.size;i++)
		{
			ArrayList <Integer> szamok;

			szamok=new ArrayList();

			for(j=0;j<this.size;j++)
			{		
				val=square[i][j];
				//System.out.println(val+": "+szamok.indexOf(val));
				//System.out.print(val+",");
				if( val != -1)
				{
					if( szamok.indexOf(val) == -1 )
					{
						// Nincs benne, hozzaadjuk

						szamok.add(val);
					}
					else
					{
						if(szamok.indexOf(val)>=0 )
							throw new RuntimeException("CheckRep: Test2: Egy sorban tobbszor van ua az ertek !");
					}	
				}
			}
			//System.out.println("");
		}


		// minden oszlopban 1 szam csak 1szer szerepelhet
		//System.out.println("Sudoku: CheckRep: Test3 running...");
		for(j=0;j<this.size;j++)
		{
			ArrayList <Integer> szamok;

			szamok=new ArrayList();

			for(i=0;i<this.size;i++)
			{		
				val=square[i][j];
				//System.out.println(val+": "+szamok.indexOf(val));
				if( val != -1)
				{
					if( szamok.indexOf(val) == -1 )
					{
						// Nincs benne, hozzaadjuk
						szamok.add(val);
					}
					else
					{
						if(szamok.indexOf(val)>=0 )
							throw new RuntimeException("CheckRep: Test3: Egy oszlopban tobbszor van ua az ertek !");
					}	
				}
			}
		}
		//System.out.println("Sudoku: CheckRep: completed...");
	}


	/**
	 * create an empty Sudoku puzzle of dimension dim.
	 * 
	 * @param dim
	 *            size of one block of the puzzle. For example, new Sudoku(3)
	 *            makes a standard Sudoku puzzle with a 9x9 grid.
	 */
	public Sudoku(int dim) {
		// TODO: implement this.
		int i,j;

		this.dim=dim;
		this.size=dim*dim;
		this.occupies=new Variable[this.size][this.size][this.size];
		this.square=new int[this.size][this.size];


		for(i=0;i<this.size;i++)
		{
			for(j=0;j<this.size;j++)
			{
				square[i][j]=-1;
			}
		}
		this.checkRep();
		//System.out.println(this.toString());
	}

	/**
	 * create Sudoku puzzle
	 * 
	 * @param square
	 *            digits or blanks of the Sudoku grid. square[i][j] represents
	 *            the square in the ith row and jth column, contains 0 for a
	 *            blank, else i to represent the digit i. So { { 0, 0, 0, 1 }, {
	 *            2, 3, 0, 4 }, { 0, 0, 0, 3 }, { 4, 1, 0, 2 } } represents the
	 *            dimension-2 Sudoku grid: 
	 *            
	 *            ...1 
	 *            23.4 
	 *            ...3
	 *            41.2
	 * 
	 * @param dim
	 *            dimension of puzzle Requires that dim*dim == square.length ==
	 *            square[i].length for 0<=i<dim.
	 */
	public Sudoku(int dim, int[][] square) {
		// TODO: implement this.

		// TODO: implement this.
		int i,j,val;

		this.dim=dim;
		this.size=dim*dim;	
		this.occupies=new Variable[this.size][this.size][this.size];
		this.square=new int[this.size][this.size];

		for(i=0;i<this.size;i++)
		{
			for(j=0;j<this.size;j++)
			{
				this.square[i][j]=square[i][j]-1;
			}
		}
		this.checkRep();
		//System.out.println(this.toString());
	}


	/**
	 * Reads in a file containing a Sudoku puzzle.
	 * 
	 * @param dim
	 *            Dimension of puzzle. Requires: at most dim of 3, because
	 *            otherwise need different file format
	 * @param filename
	 *            of file containing puzzle. The file should contain one line
	 *            per row, with each square in the row represented by a digit,
	 *            if known, and a period otherwise. With dimension dim, the file
	 *            should contain dim*dim rows, and each row should contain
	 *            dim*dim characters.
	 * @return Sudoku object corresponding to file contents
	 * @throws IOException
	 *             if file reading encounters an error
	 * @throws ParseException
	 *             if file has error in its format
	 */
	public static Sudoku fromFile(int dim, String filename) throws IOException,
	ParseException {
		Sudoku s;
		int i,j,size;
		File f;
		FileInputStream in;
		InputStreamReader reader;
		Charset encoding;

		s=new Sudoku(dim);
		f=new File(filename);

		size=dim*dim;
		encoding = Charset.defaultCharset();
		File file = new File(filename);

		{
			in = new FileInputStream(file);
			reader = new InputStreamReader(in, encoding);
			// buffer for efficiency
			Reader buffer = new BufferedReader(reader);
			int r=0;
			i=0;
			j=0;
			while ((r = reader.read()) != -1) 
			{
				char ch = (char) r;
				//System.out.println("Do something with " + ch);


				switch( ch )
				{
				case '\n':
				case '\r':
					continue;
				case '.':
					s.square[i][j]=-1;
					j++;
					if(j==size) { j=0; i++; } 
					break;
				default:
					s.square[i][j]=new Integer(ch-'0')-1;
					j++;
					if(j==size) { j=0; i++; }
					break;
				}

			}
		}


		//System.out.println(s.toString());
		s.checkRep();
		return s;
	}

	/**
	 * Exception used for signaling grammatical errors in Sudoku puzzle files
	 */
	@SuppressWarnings("serial")
	public static class ParseException extends Exception {
		public ParseException(String msg) {
			super(msg);
		}
	}

	/**
	 * Produce readable string representation of this Sukoku grid, e.g. for a 4
	 * x 4 sudoku problem: 
	 *   12.4 
	 *   3412 
	 *   2.43 
	 *   4321
	 * 
	 * @return a string corresponding to this grid
	 */
	public String toString() {

		StringBuilder sb;
		int i,j,val;

		sb=new StringBuilder(this.size*this.size);

		for(i=0;i<this.size;i++)
		{
			for(j=0;j<this.size;j++)
			{
				if( square[i][j]==-1 )
				{
					// empty
					sb=sb.append('.');
				}
				else
				{
					sb=sb.append(square[i][j]+1);
				}

			}
			sb=sb.append(System.lineSeparator());
		}
		return sb.toString();
	}

	public Formula interpret_starting_grid()
	{
		int i,j;
		Clause retcal;
		Formula retformula;

		//System.out.println(this.toString());
		retformula=new Formula();
		retcal = new Clause();
		for(i=0;i<this.size;i++)
		{
			for(j=0;j<this.size;j++)
			{
				if(this.square[i][j]!=-1)
				{
					StringBuilder sb= new StringBuilder();
					sb=sb.append("v");
					sb=sb.append(i+1);
					sb=sb.append(j+1);
					sb=sb.append(this.square[i][j]+1);
					//System.out.println("Starting grid: "+sb.toString());
					retcal=new Clause(PosLiteral.make(new Variable(sb.toString())));
					retformula=retformula.addClause(retcal);
				}
				else
				{
					//System.out.println("Skip:"+(i+1)+(j+1));
				}

			}
		}
		return retformula;

	}

	public Formula interpret_atmost_1digit_persq()
	{
		int i,j,k1,k2;
		Clause retcal;
		Formula retform;

		retform = new Formula();


		for(i=0;i<this.size;i++)
		{
			for(j=0;j<this.size;j++)
			{
				for(k1=0;k1<this.size-1;k1++)
				{
					for(k2=k1+1;k2<this.size;k2++)
					{
						retcal = new Clause();

						StringBuilder sb1= new StringBuilder();
						sb1=sb1.append("v");
						sb1=sb1.append(i+1);
						sb1=sb1.append(j+1);
						sb1=sb1.append(k1+1);

						StringBuilder sb2= new StringBuilder();
						sb2=sb2.append("v");
						sb2=sb2.append(i+1);
						sb2=sb2.append(j+1);
						sb2=sb2.append(k2+1);

						//System.out.println("Starting grid: "+sb.toString());
						retcal=retcal.add(NegLiteral.make(new Variable(sb1.toString())));
						retcal=retcal.add(NegLiteral.make(new Variable(sb2.toString())));	
						retform=retform.addClause(retcal);
					}	
				}

			}
		}
		return retform;
	}	

	public Formula interpret_row_atmostonce()
	{
		int i,j1,j2,k;
		Clause retcal;
		Formula retformula;

		retcal = new Clause();
		retformula = new Formula();

		for(i=0;i<this.size;i++)
		{
			for(k=0;k<this.size;k++)
			{
				for(j1=0;j1<this.size-1;j1++)
				{

					StringBuilder sb1= new StringBuilder();
					sb1=sb1.append("v");
					sb1=sb1.append(i+1);
					sb1=sb1.append(j1+1);
					sb1=sb1.append(k+1);


					for( j2=j1+1; j2<this.size; j2++)
					{
						retcal = new Clause();
						retcal=retcal.add(NegLiteral.make(new Variable(sb1.toString())));
						StringBuilder sb2= new StringBuilder();
						sb2=sb2.append("v");
						sb2=sb2.append(i+1);
						sb2=sb2.append(j2+1);
						sb2=sb2.append(k+1);
						retcal=retcal.add(NegLiteral.make(new Variable(sb2.toString())));
						//System.out.println("neg"+sb1.toString());
						//System.out.println("neg"+sb2.toString());
						//System.out.println("row:"+retcal.toString());
					}
					retformula=retformula.addClause(retcal);
				}

			}
		}
		return retformula;
	}

	public Formula interpret_row_atleastonce()
	{
		int i,j1,j2,k;
		Clause retcal;
		Formula retformula;

		retcal = new Clause();
		retformula = new Formula();

		for(i=0;i<this.size;i++)
		{
			for(k=0;k<this.size;k++)
			{
				retcal = new Clause();
				for(j1=0;j1<this.size;j1++)
				{

					StringBuilder sb1= new StringBuilder();
					sb1=sb1.append("v");
					sb1=sb1.append(i+1);
					sb1=sb1.append(j1+1);
					sb1=sb1.append(k+1);

					retcal=retcal.add(PosLiteral.make(new Variable(sb1.toString())));
					//System.out.println("atleastonce"+sb1.toString());
					//System.out.println("atleastonce"+sb2.toString());
					//System.out.println("atleastoncerow:"+retcal.toString());

				}
				retformula=retformula.addClause(retcal);
			}

			//System.out.println("atleastoncerow from:"+retformula.toString());

		}
		return retformula;
	}

	public Formula interpret_col_atmostonce()
	{
		int j,i1,i2,k;
		Clause retcal;
		Formula retformula;

		retcal = new Clause();
		retformula = new Formula();

		for(j=0;j<this.size;j++)
		{
			for(k=0;k<this.size;k++)
			{
				for(i1=0;i1<this.size-1;i1++)
				{

					StringBuilder sb1= new StringBuilder();
					sb1=sb1.append("v");
					sb1=sb1.append(i1+1);
					sb1=sb1.append(j+1);
					sb1=sb1.append(k+1);

					for( i2=i1+1; i2<this.size; i2++)
					{
						retcal = new Clause();
						retcal=retcal.add(NegLiteral.make(new Variable(sb1.toString())));
						StringBuilder sb2= new StringBuilder();
						sb2=sb2.append("v");
						sb2=sb2.append(i2+1);
						sb2=sb2.append(j+1);
						sb2=sb2.append(k+1);
						retcal=retcal.add(NegLiteral.make(new Variable(sb2.toString())));
						//System.out.println("neg"+sb1.toString());
						//System.out.println("neg"+sb2.toString());
						//System.out.println("col:"+retcal.toString());
					}
					retformula=retformula.addClause(retcal);
				}

			}
		}
		return retformula;
	}

	public Formula interpret_col_atleastonce()
	{
		int i,j1,j2,k;
		Clause retcal;
		Formula retformula;

		retcal = new Clause();
		retformula = new Formula();

		for(j1=0;j1<this.size;j1++)
		{
			for(k=0;k<this.size;k++)
			{
				retcal = new Clause();
				for(i=0;i<this.size;i++)
				{

					StringBuilder sb1= new StringBuilder();
					sb1=sb1.append("v");
					sb1=sb1.append(i+1);
					sb1=sb1.append(j1+1);
					sb1=sb1.append(k+1);

					retcal=retcal.add(PosLiteral.make(new Variable(sb1.toString())));
					//System.out.println("atleastonce"+sb1.toString());
					//System.out.println("atleastonce"+sb2.toString());
					//System.out.println("atleastoncerow:"+retcal.toString());

				}
				retformula=retformula.addClause(retcal);
			}

			//System.out.println("atleastoncerow from:"+retformula.toString());

		}
		return retformula;
	}

	public Formula interpret_block_atleastonce()
	{
		int dim1,dim2,i,j,k;
		Clause retcal;
		Formula retformula;

		retcal = new Clause();
		retformula = new Formula();


		for(dim1=0; dim1<this.dim; dim1++)
		{
			for(dim2=0; dim2<this.dim; dim2++)
			{
				//System.out.println("Blokk eleje");

				for(k=0;k<this.size;k++)
				{
					retcal = new Clause();
					for(i=dim1*this.dim;i<(1+dim1)*this.dim;i++)
					{			
						for(j=dim2*this.dim;j<(1+dim2)*this.dim;j++)
						{

							StringBuilder sb1= new StringBuilder();
							sb1=sb1.append("v");
							sb1=sb1.append(i+1);
							sb1=sb1.append(j+1);
							sb1=sb1.append(k+1);

							retcal=retcal.add(PosLiteral.make(new Variable(sb1.toString())));
							//System.out.print(sb1.toString());
						}
					}
					//System.out.println();
					//System.out.println("Blokk vege");
					retformula=retformula.addClause(retcal);
				}
			}
			//System.out.println();
		}
		//System.out.println("Atleastonce in block:"+retformula.getSize()+"--"+retformula.toString());

		return retformula;
	}

	public Formula interpret_block_atmostonce()
	{
		int dim1,dim2,i,i2,j,j2,k;
		int num_of_pairs=0;
		int debug = 0;
		Clause retcal;
		Formula retformula;

		retcal = new Clause();
		retformula = new Formula();

		for(k=0;k<this.size;k++)
		{

			if( debug==1) System.out.println("\nÚj k kezdodik k:"+k);
			for(dim1=0; dim1<this.dim; dim1++)
			{
				for(dim2=0; dim2<this.dim; dim2++)
				{
					if( debug==1) System.out.println("\nÚj Blokk kezdodik dim1:"+dim1+" dim2:"+dim2+" k:"+k);
					for(i=dim1*this.dim;i<(1+dim1)*this.dim;i++)
					{			
						for(j=dim2*this.dim;j<(1+dim2)*this.dim;j++)
						{
							retcal = new Clause();

							StringBuilder sb1= new StringBuilder();
							sb1=sb1.append("v");
							sb1=sb1.append(i+1);
							sb1=sb1.append(j+1);
							sb1=sb1.append(k+1);

							retcal=retcal.add(NegLiteral.make(new Variable(sb1.toString())));
							//System.out.print(sb1.toString());
							for(i2=dim1*this.dim;i2<(1+dim1)*this.dim;i2++)
							{			
								for(j2=dim2*this.dim;j2<(1+dim2)*this.dim;j2++)
								{
									if( i*this.dim+j >= i2*this.dim + j2 ) continue;
									retcal = new Clause();
									retcal=retcal.add(NegLiteral.make(new Variable(sb1.toString())));
									StringBuilder sb2= new StringBuilder();
									sb2=sb2.append("v");
									sb2=sb2.append(i2+1);
									sb2=sb2.append(j2+1);
									sb2=sb2.append(k+1);

									retcal=retcal.add(NegLiteral.make(new Variable(sb2.toString())));
									retformula=retformula.addClause(retcal);
									if( debug == 1) System.out.print("(n"+sb1.toString()+",n"+sb2.toString()+")");
									num_of_pairs++;
								}
							}
							if( debug == 1) System.out.println("uj sor");
						}
					}
					//System.out.println(" ");
				}
			}
			//System.out.println();
		}
		//System.out.println("atmostonce in block: pairs:"+num_of_pairs+" formulasize"+retformula.getSize()+"--"+retformula.toString());

		return retformula;
	}


	/**
	 * @return a SAT problem corresponding to the puzzle, using variables with
	 *         names of the form occupies(i,j,k) to indicate that the kth symbol
	 *         occupies the entry in row i, column j
	 */
	public Formula getProblem() {

		// TODO: implement this.
		Formula f_fixed, f_1digit, f_rows1,f_rows2, f_cols1, f_cols2, f_block1, f_block2;
		int debug=0;

		System.out.println(this.toString());

		f_fixed=interpret_starting_grid();
		if( debug > 1) System.out.println("Starting grid:"+f_fixed.toString());
		if( debug == 1) System.out.println("Starting grid:"+f_fixed.getSize());


		f_1digit=interpret_atmost_1digit_persq();
		if( debug > 1) System.out.println("1digit per sq:"+f_1digit.toString());
		if( debug == 1) System.out.println("1 digit per sq:"+f_1digit.getSize());

		f_rows1=interpret_row_atleastonce();
		if( debug > 1) System.out.println("Rows at least once:"+f_rows1.toString());
		if( debug == 1) System.out.println("Rows at least once:"+f_rows1.getSize());

		f_rows2=interpret_row_atmostonce();
		if( debug > 1) System.out.println("Rows at most once:"+f_rows2.toString());
		if( debug == 1) System.out.println("Rows at most once:"+f_rows2.getSize());

		f_cols1=interpret_col_atleastonce();
		if( debug > 1) System.out.println("Cols at least once:"+f_cols1.toString());
		if( debug == 1) System.out.println("Cols at least once:"+f_cols1.getSize());

		f_cols2=interpret_col_atmostonce();
		if( debug > 1) System.out.println("Cols at most once:"+f_cols2.toString());
		if( debug == 1) System.out.println("Cols at most once:"+f_cols2.getSize());

		f_block1=interpret_block_atleastonce();
		if( debug > 1) System.out.println("Block at least once:"+f_block1.toString());
		if( debug == 1) System.out.println("Block at least once:"+f_block1.getSize());

		f_block2=interpret_block_atmostonce();
		if( debug > 1) System.out.println("Block at most once:"+f_block2.toString());
		if( debug == 1) System.out.println("Block at most once:"+f_block2.getSize());

		return (f_fixed.and(f_1digit.and(f_rows1.and(f_rows2.and(f_cols1.and(f_cols2.and(f_block2.and(f_block1))))))));
		//return (f_fixed.and(f_rows1.and(f_rows2.and(f_cols1.and(f_cols2.and(f_block2.and(f_block1)))))));

		//return (f_fixed.and(f_rows1.and(f_rows2.and(f_cols1.and(f_cols2)))));

		//throw new RuntimeException("not yet implemented.");
	}

	/**
	 * Interpret the solved SAT problem as a filled-in grid.
	 * 
	 * @param e
	 *            Assignment of variables to values that solves this puzzle.
	 *            Requires that e came from a solution to this.getProblem().
	 * @return a new Sudoku grid containing the solution to the puzzle, with no
	 *         blank entries.
	 */
	public Sudoku interpretSolution(Environment e) {

		// TODO: implement this.
		int i,j,k;
		int sq[][];

		if( e == null)
		{
			System.out.println("Interpret Solution: null environment: cannot solve");
			return new Sudoku(this.dim);
		}

		sq=new int[this.dim*this.dim][this.dim*this.dim];
		for(i=0;i<this.size;i++)
		{
			for(j=0;j<this.size;j++)
			{
				for(k=0;k<this.size;k++)
				{
					StringBuilder sb1= new StringBuilder();
					Variable v;
					sb1=sb1.append("v");
					sb1=sb1.append(i+1);
					sb1=sb1.append(j+1);
					sb1=sb1.append(k+1);
					v=new Variable(sb1.toString());
					if( e.get(v)== Bool.TRUE )
					{
						//System.out.print("("+sb1.toString()+")");
						sq[i][j]=k+1;
					}

				}

			}
			//System.out.println("");
		}

		return new Sudoku(dim,sq);

		//throw new RuntimeException("not yet implemented.");
	}

}
