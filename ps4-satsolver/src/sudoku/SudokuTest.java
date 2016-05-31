package sudoku;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import sudoku.Sudoku.ParseException;


public class SudokuTest {
    

    // make sure assertions are turned on!  
    // we don't want to run test cases without assertions too.
    // see the handout to find out how to turn them on.
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }

    @Test
    public void t1()
    {
    	Sudoku s;
    	Boolean testok=false;
    	try
    	{
    		s=new Sudoku(2);
    		testok=true;
    	}
    	catch(RuntimeException r)
    	{
    		testok=false;
    	}    	
    	assertTrue(testok);
    }
    
    @Test
    public void t1empty()
    {
    	Sudoku s;
    	Boolean testok=false;
    	try
    	{
    		s=new Sudoku(2);
    		testok=true;
    	}
    	catch(RuntimeException r)
    	{
    		testok=false;
    	}    	
    	assertTrue(testok);
    }
    
    @Test
    public void t2simpleOK()
    {
    	Sudoku s;
    	Boolean testok=false;
    	
    	try
    	{
    	s=new Sudoku(2, new int[][] { 
            new int[] { 0, 1, 0, 4 }, 
            new int[] { 0, 0, 0, 0 }, 
            new int[] { 2, 0, 3, 0 }, 
            new int[] { 0, 0, 0, 0 }, 
    	});
    		testok=true;
    	}
    	catch(RuntimeException r)
    	{
    		testok=false;
    	}    	
    	assertTrue(testok);
    }
    
    @Test
    public void t3rowproblem()
    {
    	Sudoku s;
    	Boolean testok=false;
    	
    	try
    	{
    	s=new Sudoku(2, new int[][] { 
            new int[] { 0, 1, 1, 4 }, 
            new int[] { 0, 0, 0, 0 }, 
            new int[] { 2, 0, 3, 0 }, 
            new int[] { 0, 0, 0, 0 }, 
    	});
    		testok=true;
    	}
    	catch(RuntimeException r)
    	{
    		testok=false;
    	}    	
    	assertFalse(testok);
    }
    
    @Test
    public void t3colproblem()
    {
    	Sudoku s;
    	Boolean testok=false;
    	
    	try
    	{
    	s=new Sudoku(2, new int[][] { 
            new int[] { 0, 1, 3, 4 }, 
            new int[] { 0, 0, 0, 0 }, 
            new int[] { 2, 0, 3, 0 }, 
            new int[] { 0, 0, 0, 0 }, 
    	});
    		testok=true;
    	}
    	catch(RuntimeException r)
    	{
    		testok=false;
    	}    	
    	assertFalse(testok);
    }
    
    @Test
    public void t4fromfile1() throws IOException, ParseException
    {
    	Sudoku s;
    	Boolean testok=false;
    	
    	try
    	{
    	s=new Sudoku(3);
    	s.fromFile(3,"samples/sudoku_easy.txt"); 
    	testok=true;
    	}
    	catch(RuntimeException r)
    	{
    		testok=false;
    	}    	
    	assertTrue(testok);
    }
  
    @Test
    public void t5fromfile2() throws IOException, ParseException
    {
    	Sudoku s;
    	Boolean testok=false;
    	
    	try
    	{
    	s=new Sudoku(3);
    	s.fromFile(3,"samples/sudoku_easy2.txt"); 
    	testok=true;
    	}
    	catch(RuntimeException r)
    	{
    		testok=false;
    	}    	
    	assertTrue(testok);
    }
   
    @Test
    public void t6fromfile2() throws IOException, ParseException
    {
    	Sudoku s;
    	Boolean testok=false;
    	
    	try
    	{
    	s=new Sudoku(2);
    	s.fromFile(2,"samples/sudoku_4x4.txt"); 
    	testok=true;
    	}
    	catch(RuntimeException r)
    	{
    		testok=false;
    	}    	
    	assertTrue(testok);
    }
}