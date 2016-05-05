package piwords;

import static org.junit.Assert.*;

import org.junit.Test;

public class BaseTranslatorTest {
    @Test
    public void basicBaseTranslatorTest() {
        // Expect that .01 in base-2 is .25 in base-10
        // (0 * 1/2^1 + 1 * 1/2^2 = .25)
        int[] input = {0, 1};
        int[] expectedOutput = {2, 5};
        assertArrayEquals(expectedOutput,
                          BaseTranslator.convertBase(input, 2, 10, 2));
    }

    // TODO: Write more tests (Problem 2.a)

   @Test
   public void zeroBaseTranslatorTest() {
       // Expect that .00 in base-2 is .00 in base-10
       
       int[] input = {0, 0};
       int[] expectedOutput = {0, 0};
       assertArrayEquals(expectedOutput,
                         BaseTranslator.convertBase(input, 2, 10, 2));
   }

   @Test
   public void oneoneBaseTranslatorTest() {
       // Expect that .11 in base-2 is .75 in base-10
       
       int[] input = {1, 1, 0, 0};
       int[] expectedOutput = {7, 5,0, 0};
       assertArrayEquals(expectedOutput,
                         BaseTranslator.convertBase(input, 2, 10, 4));
   }
       
   
   @Test
   public void t126BaseTranslatorTest() {
       // Expect that .11 in base-2 is .75 in base-10
       //0,038461538

       int[] input = { 0, 3, 8, 4, 6, 1, 5, 3, 8};
       int[] expectedOutput = { 1 , 0, 0, 0, 0, 0, 0, 0, 0};
       assertArrayEquals(expectedOutput,
                         BaseTranslator.convertBase(input, 10, 26, 9));
       
   

}
   
   
       @Test
       public void pi26BaseTranslatorTest() {
           // Expect that .11 in base-2 is .75 in base-10
           
           int[] input = {1, 4, 1, 6};
           int[] expectedOutput = { 3 , 17, 18};
           assertArrayEquals(expectedOutput,
                             BaseTranslator.convertBase(input, 10, 26, 3));
           
       
   
   }
//BASE 16:
//3.243F6 A8885 A308D 31319 8A2E0 37073 44A40 93822 299F3 1D008 2EFA9 8EC4E 6C894 52821 E638D 01377 BE546 6CF34 E90C6 C

//BASE26
//http://www.cadaeic.net/picode.htm
//
//D.DRSQLOLYRTRODNLHNQTGKUDQGTUIRXNEQBCKBSZIVQQVGDMELM
//  UEXROIQIYALVUZVEBMIJPQQXLKPLRNCFWJPBYMGGOHJMMQISMS...
       
       @Test
       public void egyhuszonhatodBaseTranslatorTest() {
           // Expect that .11 in base-2 is .75 in base-10
           
           int[] input = {1, 0, 0, 0};
           int[] expectedOutput = { 0 , 3, 8, 4};
           assertArrayEquals(expectedOutput,
                             BaseTranslator.convertBase(input, 26, 10, 3));
           
       
   
   }
}
