import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Created by Alex on 19.06.17.
 */
public class PercolationStats {
    private double [] results;
    private int trials;

    public PercolationStats(int n, int trials) {
        // perform trials independent experiments on an n-by-n grid
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Grid size and trials should be more then 0!");
        }
        this.trials = trials;
        results = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation model = new Percolation(n);
            while (!model.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                if (!model.isOpen(row, col)) {
                    model.open(row, col);
                }
            }
            results[i] = ((double) model.numberOfOpenSites() / (n * n));
        }
    }

    public double mean() {
        // sample mean of percolation threshold
        return StdStats.mean(results);

    }

    public double stddev() {
        // sample standard deviation of percolation threshold
        return StdStats.stddev(results);
    }

    public double confidenceLo() {
        // low  endpoint of 95% confidence interval
        return mean() - ((1.96 * stddev()) / Math.sqrt(this.trials));
    }

    public double confidenceHi() {
        // high endpoint of 95% confidence interval
        return mean() + ((1.96 * stddev()) / Math.sqrt(this.trials));
    }

    public static void main(String[] args) {
        // test client
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);
        System.out.println("mean                     = " + stats.mean());
        System.out.println("stddev                   = " + stats.stddev());
        System.out.println("95% confidence interval  = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
    }
}
