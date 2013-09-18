package framework.utills;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 18/09/13
 * Time: 19:11
 * To change this template use File | Settings | File Templates.
 */
public class RandomUtils {

    private static Random rand = new Random();

    /**
     * Returns a psuedo-random number between min and max, inclusive.
     * The difference between min and max can be at most
     * <code>Integer.MAX_VALUE - 1</code>.
     *
     * @param min Minimim value
     * @param max Maximim value.  Must be greater than min.
     * @return Integer between min and max, inclusive.
     * @see java.util.Random#nextInt(int)
     */
    public static int randomInRange(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }

    public static boolean randomBoolean(){
        return rand.nextBoolean();
    }
}
