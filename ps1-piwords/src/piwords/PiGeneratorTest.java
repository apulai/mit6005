package piwords;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class PiGeneratorTest {
    @Test
    public void basicPowerModTest() {
        // 5^7 mod 23 = 17
        assertEquals(17, PiGenerator.powerMod(5, 7, 23));
    }
    
    @Test
    public void eqzeroPowerModTest() {  
    // 2^3 mod 2 = 0
    assertEquals(0, PiGenerator.powerMod(2,3, 2)) ;
    }
    
    @Test
    public void invailidargPowerModTest()
    {  
        // 2^3 mod 2 = 0
        assertEquals(-1, PiGenerator.powerMod(-1,3, 2)) ;
        }

    @Test
    public void pi2nddigitTest()
    {
    	// 243F6A8885A308D313198A2E
    	int[] retval;
    	int[] compare = new int[]{2,4,3,15,6,10,8,8,8,5,10,3,0};
    	retval=PiGenerator.computePiInHex(13);
    	//int[] compare = new int[]{2,4,3,15,6};
    	// retval=PiGenerator.computePiInHex(5);
    	System.out.println(Arrays.toString(retval));
    	assertArrayEquals(compare,retval);
    }
    // TODO: Write more tests (Problem 1.a, 1.c)
}
