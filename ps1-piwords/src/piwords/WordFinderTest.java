package piwords;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class WordFinderTest {
    @Test
    public void basicGetSubstringsTest() {
        String haystack = "abcde";
        String[] needles = {"ab", "abc", "de", "fg"};

        Map<String, Integer> expectedOutput = new HashMap<String, Integer>();
        expectedOutput.put("ab", 0);
        expectedOutput.put("abc", 0);
        expectedOutput.put("de", 3);

        assertEquals(expectedOutput, WordFinder.getSubstrings(haystack,
                                                              needles));
    }

    // TODO: Write more tests (Problem 4.a)
    
    @Test
    public void noneedlesTest() {
        String haystack = "abcde";
        String[] needles = {"ib", "ibc", "ide", "ifg"};

        Map<String, Integer> expectedOutput = new HashMap<String, Integer>();
        expectedOutput.put("", 0);

        assertEquals(expectedOutput, WordFinder.getSubstrings(haystack,
                                                              needles));
    }

    // TODO: Write more tests (Problem 4.a)
    
}
