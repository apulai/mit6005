package piwords;

public class PiGenerator {
    /**
     * Returns precision hexadecimal digits of the fractional part of pi.
     * Returns digits in most significant to least significant order.
     * 
     * If precision < 0, return null.
     * 
     * @param precision The number of digits after the decimal place to
     *                  retrieve.
     * @return precision digits of pi in hexadecimal.
     */
    public static int[] computePiInHex(int precision) {
        // TODO: Implement (Problem 1.d)
        int retval[] = new int[precision];
    	int i = 1;
    	for( i=1; i<=precision; i++)
    	{
    		retval[i-1]= PiGenerator.piDigit(i);
    	}
       return retval;
    }

    /**
     * Computes a^b mod m
     * 
     * If a < 0, b < 0, or m < 0, return -1.
     * 
     * @param a
     * @param b
     * @param m
     * @return a^b mod m
     */
    public static int powerMod(int a, int b, int m) {
        // TODO: Implement (Problem 1.b)
    	long x = 0;
    	long y = 0;
    	int n;
    	int i;
    	int res;
    	if( a< 0 || b < 0 || m < 0 ) return -1;
    	
    	//function modular_pow(base, exponent, modulus) 
    	//if modulus = 1 then return 0 
    	// Assert :: (modulus - 1) * (modulus - 1) does not overflow base 
    	// result := 1 
    	// base := base mod modulus 
    	// while exponent > 0 
    	// if (exponent mod 2 == 1): result := (result * base) mod modulus 
    	// exponent := exponent >> 1 
    	// base := (base * base) mod modulus 
    	// return result
    	
    	
        if( m==1 ) return 0;
        res = 1;
        a = a % m;
        while ( b > 0)
        {
        	if ( b % 2 == 1)
        	{
        		res = ( res * a ) % m;
        	}
        	b = b >> 1;
        	a = ( a * a ) % m;
        }
        return res;
        
        /* Naiv elképzelés
    	n=b;
    	x = a;
    	y = 1;
    	if( n != 0)
    	{
    		while ( n > 1)
    		{
    			if( 0 == n % 2)
    			{
    				x=x*x;
    				n=n/2;
    			}
    			else
    			{
    				y=x*y;
    				x=x*x;
    				n=(n-1)/2;
    			}
    		}
    		x=x*y;
    	}
    	else
    	{
    	 x=1;
    	}
    	
    	// System.out.println("x A exp B:"+ x + " power.mod" + (long) Math.pow(a,b)) ;
    	x=x%(long)m;
        return (int)x;
        */
    }
    
    /**
     * Computes the nth digit of Pi in base-16.
     * 
     * If n < 0, return -1.
     * 
     * @param n The digit of Pi to retrieve in base-16.
     * @return The nth digit of Pi in base-16.
     */
    public static int piDigit(int n) {
        if (n < 0) return -1;
        
        n -= 1;
        double x = 4 * piTerm(1, n) - 2 * piTerm(4, n) -
                   piTerm(5, n) - piTerm(6, n);
        x = x - Math.floor(x);
        
        return (int)(x * 16);
    }
    
    private static double piTerm(int j, int n) {
        // Calculate the left sum
        double s = 0;
        for (int k = 0; k <= n; ++k) {
            int r = 8 * k + j;
            s += powerMod(16, n-k, r) / (double) r;
            s = s - Math.floor(s);
        }
        
        // Calculate the right sum
        double t = 0;
        int k = n+1;
        // Keep iterating until t converges (stops changing)
        while (true) {
            int r = 8 * k + j;
            double newt = t + Math.pow(16, n-k) / r;
            if (t == newt) {
                break;
            } else {
                t = newt;
            }
            ++k;
        }
        
        return s+t;
    }
}
