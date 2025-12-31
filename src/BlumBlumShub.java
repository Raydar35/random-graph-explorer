import java.math.BigInteger;
import java.util.Random;

/**
 * Blum-Blum-Shub (BBS) pseudorandom number generator.
 * <p>
 * This implementation generates bits by iterating x_{i+1} = x_i^2 mod n where n = p*q
 * with p and q primes congruent to 3 mod 4. It exposes convenience methods to obtain
 * single bits and integers in a range.
 */
public class BlumBlumShub {

    private BigInteger n;
    private BigInteger state;

    /**
     * Construct a BBS generator with given primes p, q, and initial seed.
     *
     * @param p prime congruent to 3 mod 4
     * @param q prime congruent to 3 mod 4
     * @param seed initial seed (should be co-prime to p and q)
     */
    public BlumBlumShub(BigInteger p, BigInteger q, BigInteger seed) {
        n = p.multiply(q);
        state = seed.mod(n);
    }

    /**
     * Generate the next pseudorandom bit.
     *
     * @return 0 or 1
     */
    public int nextBit() {
        state = state.modPow(BigInteger.valueOf(2), n);
        return state.testBit(0) ? 1 : 0;
    }

    /**
     * Generate a pseudorandom integer in the inclusive range [min, max].
     *
     * @param min lower bound (inclusive)
     * @param max upper bound (inclusive)
     * @return pseudorandom integer between min and max
     */
    public int nextInt(int min, int max) {
        int range = max - min + 1;
        int value;

        do {
            int bits = 0;
            for (int i = 0; i < 15; i++) {
                bits = (bits << 1) | nextBit();
            }
            value = bits % range;
        } while (value < 0);

        return min + value;
    }

    /**
     * Create a BBS instance with randomly generated suitable primes and seed.
     *
     * @param bits bit length for generated primes and seed
     * @return a BlumBlumShub instance
     */
    public static BlumBlumShub createRandomBBS(int bits) {
        Random rnd = new Random();
        BigInteger p = getSuitablePrime(rnd, bits);
        BigInteger q = getSuitablePrime(rnd, bits);
        BigInteger seed;
        do {
            seed = new BigInteger(bits, rnd);
        } while (seed.equals(BigInteger.ZERO) || seed.equals(BigInteger.ONE)
                || !seed.gcd(p).equals(BigInteger.ONE)
                || !seed.gcd(q).equals(BigInteger.ONE));
        return new BlumBlumShub(p, q, seed);
    }

    // Choose a probable prime congruent to 3 mod 4
    private static BigInteger getSuitablePrime(Random rnd, int bits) {
        BigInteger prime;
        do {
            prime = BigInteger.probablePrime(bits, rnd);
        } while (!prime.mod(BigInteger.valueOf(4)).equals(BigInteger.valueOf(3)));
        return prime;
    }
}
