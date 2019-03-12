/* *****************************************************************************
 *  Name: Wei Sun
 *  Date: 3/2/2019
 *  Description: The class Permutation takes an integer k as a command-line argument; 
 *               reads in a sequence of strings from standard input using StdIn.readString(); 
 *               and prints exactly k of them, uniformly at random. 
 *               Print each item from the sequence at most once.
 *
 **************************************************************************** */
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;


public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        int k = Integer.parseInt(args[0]);
        while (!StdIn.isEmpty()) {
            String input = StdIn.readString();
            rq.enqueue(input);
        }
        for (int i = 0; i < k; i++) {
            String output = rq.dequeue();
            StdOut.println(output);
        }
    }
}
