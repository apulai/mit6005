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

import sat.env.Environment;
import sat.env.Variable;
import sat.formula.Formula;

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

	/**
	 * @return a SAT problem corresponding to the puzzle, using variables with
	 *         names of the form occupies(i,j,k) to indicate that the kth symbol
	 *         occupies the entry in row i, column j
	 */
	public Formula getProblem() {

		// TODO: implement this.
		throw new RuntimeException("not yet implemented.");
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
		throw new RuntimeException("not yet implemented.");
	}

}
