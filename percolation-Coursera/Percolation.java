/* *****************************************************************************
 *  Name: Wei Sun
 *  Date: 2/24/2019
 *  Description: The Percolation class for determining if a n by n grid 
 *               is percolated. Using backwash_uf to prevent backwash 
 *               in visualizer.  
 **************************************************************************** */
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF backwash_uf;
    private final int top;
    private final int bottom;
    private final int n;
    private final boolean[] open;
    private int numberOfOpenSites;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n <= 0");
        }
        int size = (n + 2) * (n + 2);
        this.n = n;
        uf = new WeightedQuickUnionUF(size);
        backwash_uf = new WeightedQuickUnionUF(size);
        open = new boolean[size];        

        top = 0;
        for (int j = 1; j <= n; j++) {
            uf.union(top, getIndex(1, j));
            backwash_uf.union(top, getIndex(1, j));
        }

        bottom = size - 1;
        for (int j = 1; j <= n; j++) {
            uf.union(bottom, getIndex(n, j));
        }
    }

    private void isValid(int row, int col) {
        // Here n equals to the instance variale this.n
        if (row < 1 || row > n) {
            throw new IllegalArgumentException("row index out of bounds");
        }
        
        if (col < 1 || col > n) {
            throw new IllegalArgumentException("col index out of bounds");
        }
    }

    private int getIndex(int row, int col) {
        return row * (n + 2) + col;
    }

    public void open(int row, int col) {
        isValid(row, col);
        int pos = getIndex(row, col);
        if (!open[pos]) {
            open[pos] = true;
            numberOfOpenSites++;

            tryUnion(row, col, row + 1, col);
            tryUnion(row, col, row - 1, col);
            tryUnion(row, col, row, col + 1);
            tryUnion(row, col, row, col - 1);
        }
    }

    private void tryUnion(int row, int col, int nei_row, int nei_col) {
        if (nei_row > 0 && nei_row <= n && nei_col > 0 && nei_col <= n) {
            int pos = getIndex(row, col);
            int nei_pos = getIndex(nei_row, nei_col);
            if (open[nei_pos]) {
                uf.union(pos, nei_pos);
                backwash_uf.union(pos, nei_pos);
            }
        }
    }

    public boolean isOpen(int row, int col) {
        isValid(row, col);
        int pos = row * (this.n + 2) + col;
        return open[pos];
    }

    public boolean isFull(int row, int col) {
        isValid(row, col);
        int pos = row * (this.n + 2) + col;
        return isOpen(row, col) && backwash_uf.connected(top, pos);
    }

    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    public boolean percolates() {
        if (this.n == 1) {
            return isOpen(1, 1);
        }
        return uf.connected(top, bottom);
    }

    public static void main(String[] args) {
        
    }
}
