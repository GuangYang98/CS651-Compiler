// IsPrime . java
public class IsPrime {
    // Returns true if n is prime , and false otherwise .
    public static boolean isPrime (int n) {
        if (n < 2) {
            return false ;
        }
        for (int i = 2; i <= n / i; i ++) {
            if (n % i == 0) {
                return false ;
            }
        }
        return true ;
    }
    // Entry point .
    public static void main ( String [] args ) {
        int n = Integer . parseInt ( args [0]);
        boolean result = isPrime (n);
        System.out.print(n);
        if ( result ) {
            System . out . println (" is a prime number ");
        }
        else {
            System . out . println (" is not a prime number ");
        }
    }
}