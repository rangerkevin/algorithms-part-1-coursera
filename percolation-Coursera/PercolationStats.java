import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/* *
 * Performing a series of computational experiments for the monte carlo simulation.
 * @author: Wei Sun
 * @date  : 2/25/2019
* */

public class PercolationStats {

    private double[] fractions;
    private int n;
    private int trials;
    private double mean;
    private double stddev;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException("n <= 0");
        }
        if (trials <= 0) {
            throw new java.lang.IllegalArgumentException("trials <= 0");
        }

        this.n = n;
        this.trials = trials;
        fractions = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            int numOfOpenSites = 0;
            while (!perc.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                if (!perc.isOpen(row, col)) {
                    perc.open(row, col);
                    numOfOpenSites++;
                } 
            }
            fractions[i] = ((double) numOfOpenSites) / (n * n); 
        }
    }   

    // sample mean of percolation threshold
    public double mean() {
        mean = StdStats.mean(fractions);
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        stddev = StdStats.stddev(fractions);
        return stddev;
    }                       

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean - 1.96 * stddev / Math.sqrt(trials);
    }                 

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean + 1.96 * stddev / Math.sqrt(trials);
    }                 

    // test client (described below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);
        StdOut.println("mean                    = "  + stats.mean());
        StdOut.println("stddev                  = "  + stats.stddev());
        StdOut.println("95% confidence interval = [" + stats.confidenceLo() + ", " 
                                                     + stats.confidenceHi() + "]");
    }       
}
