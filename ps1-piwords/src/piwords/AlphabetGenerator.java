package piwords;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class AlphabetGenerator {
    /**
     * Given a numeric base, return a char[] that maps every digit that is
     * representable in that base to a lower-case char.
     * 
     * This method will try to weight each character of the alphabet
     * proportional to their occurrence in words in a training set.
     * 
     * This method should do the following to generate an alphabet:
     *   1. Count the occurrence of each character a-z in trainingData.
     *   2. Compute the probability of each character a-z by taking
     *      (occurrence / total_num_characters).
     *   3. The output generated in step (2) is a PDF of the characters in the
     *      training set. Convert this PDF into a CDF for each character.
     *   4. Multiply the CDF value of each character by the base we are
     *      converting into.
     *   5. For each index 0 <= i < base,
     *      output[i] = (the first character whose CDF * base is > i)
     * 
     * A concrete example:
     * 	 0. Input = {"aaaaa..." (302 "a"s), "bbbbb..." (500 "b"s),
     *               "ccccc..." (198 "c"s)}, base = 93
     *   1. Count(a) = 302, Count(b) = 500, Count(c) = 193
     *   2. Pr(a) = 302 / 1000 = .302, Pr(b) = 500 / 1000 = .5,
     *      Pr(c) = 198 / 1000 = .198
     *   3. CDF(a) = .302, CDF(b) = .802, CDF(c) = 1
     *   4. CDF(a) * base = 28.086, CDF(b) * base = 74.586, CDF(c) * base = 93
     *   5. Output = {"a", "a", ... (28 As, indexes 0-27),
     *                "b", "b", ... (47 Bs, indexes 28-74),
     *                "c", "c", ... (18 Cs, indexes 75-92)}
     * 
     * The letters should occur in lexicographically ascending order in the
     * returned array.
     *   - {"a", "b", "c", "c", "d"} is a valid output.
     *   - {"b", "c", "c", "d", "a"} is not.
     *   
     * If base >= 0, the returned array should have length equal to the size of
     * the base.
     * 
     * If base < 0, return null.
     * 
     * If a String of trainingData has any characters outside the range a-z,
     * ignore those characters and continue.
     * 
     * @param base A numeric base to get an alphabet for.
     * @param trainingData The training data from which to generate frequency
     *                     counts. This array is not mutated.
     * @return A char[] that maps every digit of the base to a char that the
     *         digit should be translated into.
     */
    public static char[] generateFrequencyAlphabet(int base,
                                                   String[] trainingData) {
        // TODO: Implement (Problem 5.b)
        int i,j,k,osszes_betu;
        char c, retval[];
        float f_prsum;
        int start, stop;
        
        Map< Character , Integer> map_char_db = new TreeMap< Character , Integer>();
        Map< Character , Float> map_char_pr = new TreeMap< Character , Float>();
        Map< Character , Float> map_char_cdr = new TreeMap< Character , Float>();
        
        
        osszes_betu=0;
        
        for(i=0; i< trainingData.length; i++)
        {
        	// szepen vegiglepkedunk a trainingdata[i] stringen
        	for(j=0; j< trainingData[i].length(); j++)
        	{
        		// szepen végig megyünk egy string összes betûjén
        		c= trainingData[i].charAt(j);
        		if( map_char_db.containsKey(c) )
        		{
        			// van már benne ilyen
        			k=map_char_db.get(c);
        			k++;
        			osszes_betu++;
        			map_char_db.put(c, k);
        		}
        		else
        		{
        			map_char_db.put(c,1);
        			osszes_betu++;
        		}		
        	}
 
        }
        // szepen megszámoltuk a betûket
       	//System.out.println("osszes betu: "+ osszes_betu);
    	//System.out.println("Hash: "+map_char_db.toString());
    	
    	// Ha üres lenne, akkor megállunk
    	if( map_char_db.isEmpty() ) return null;
    	
    	// Egyébként bejárjuk
    	
    	Iterator<Map.Entry<Character, Integer>> entries = map_char_db.entrySet().iterator();
    	f_prsum = (float) 0.0;
    	while (entries.hasNext()) {
    	    Map.Entry<Character, Integer> entry = entries.next();
    	    map_char_pr.put(entry.getKey(), (float) entry.getValue() / (float) osszes_betu);
    	    f_prsum = f_prsum + (float) entry.getValue() / (float) osszes_betu;
    	    map_char_cdr.put(entry.getKey(), (float) f_prsum);
    	    
    	    // System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
    	}
    	entries.remove();
    	//System.out.println("Hash_ps: "+map_char_pr.toString());
    	//System.out.println("Hash_cdr: "+map_char_cdr.toString());
    	
    	// megvan a pr map
    	// megvan a cdr map
    	// most le kell generalni az abc-t
    	
    	start=0;
    	stop=0;
    	retval = new char[base];
    	Iterator<Map.Entry<Character, Float>> entries2 = map_char_cdr.entrySet().iterator();
    	while (entries2.hasNext()) {
    	    Map.Entry<Character, Float> entry = entries2.next();
    	    stop=(int) ( (float) entry.getValue()* (float) base);
    	    c = entry.getKey();
    	    // System.out.println("betut irom: " + c);
    	    for ( i = start; i < stop; i++ )
    	    {
    	    	retval[i]=c;
    	    }
    	    start=stop;
    	    
    	    // System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
    	}
    	System.out.println("Freq_alpha: "+ Arrays.toString(retval) );
    	
    	
    	
    	return retval;
    }
}
