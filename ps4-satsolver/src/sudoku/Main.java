package sudoku;

import java.io.IOException;

import sat.SATSolver;
import sat.env.Environment;
import sat.formula.Formula;
import sudoku.Sudoku.ParseException;

public class Main {


    /*
     * Uncomment line(s) below to test your implementation! 
     */
    public static void main (String[] args) throws IOException, ParseException {
//        timedSolve (new Sudoku(2));
    	
       timedSolveFromFile(2, "samples/sudoku_4x4.txt");
       System.out.println("-----");
       timedSolve (new Sudoku(2, new int[][] { 
                    new int[] { 0, 3, 2, 0 }, 
                    new int[] { 2, 4, 1, 3 }, 
                    new int[] { 3, 1, 4, 2 }, 
                    new int[] { 4, 0, 0, 0 }, 
        }));
       System.out.println("-----");
       timedSolve (new Sudoku(2, new int[][] { 
           new int[] { 0, 3, 2, 0 }, 
           new int[] { 2, 0, 1, 3 }, 
           new int[] { 3, 1, 4, 2 }, 
           new int[] { 4, 0, 0, 0 }, 
       }));
       System.out.println("-----");
      timedSolveFromFile(3, "samples/sudoku_easy.txt");
      
      System.out.println("-----");
      timedSolveFromFile(3, "samples/sudoku_hard.txt");        
    }

    /**
     * Solve a puzzle and display the solution and the time it took.
     * @param sudoku
     */
    private static void timedSolve (Sudoku sudoku) {
        long started = System.nanoTime();

        System.out.println ("Creating SAT formula...");
        Formula f = sudoku.getProblem();
        
        System.out.println ("Solving problem size: "+f.getSize());
        System.out.println ("Solving..."+f.toString());
        Environment e = SATSolver.solve(f);
        
        System.out.println ("Interpreting solution...");
        Sudoku solution = sudoku.interpretSolution(e);
        
        System.out.println ("Solution is: \n");
        System.out.println (solution);    

        long time = System.nanoTime();
        long timeTaken = (time - started);
        System.out.println ("Time:" + timeTaken/1000000 + "ms");
    }

    /**
     * Solve a puzzle loaded from a file and display the solution and the time it took.
     * @param dim  dimension of puzzle
     * @param filename  name of puzzle file to load
     */
    private static void timedSolveFromFile(int dim, String filename) {
        try {
            timedSolve (Sudoku.fromFile (dim, filename));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }        
    }
}
