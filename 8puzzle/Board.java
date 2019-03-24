/* ************************************************************************
 *
 * Name: Wei Sun
 * Date: 3/19/2019
 *
 *********************************************************************** */
import java.lang.Math;
import java.util.ArrayList;

public class Board {

    private final int[][] tiles;
    private final int n;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {          
        if (blocks == null) throw new java.lang.IllegalArgumentException("Null blocks");
        n = blocks.length;
        tiles = clone(blocks);
    }         

    // board dimension n
    public int dimension() {
        return n;
    }                

    // number of blocks out of place
    public int hamming() {
        int count = 0;
        int index = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                index++;
                if (tiles[i][j] == 0) continue;
                if (tiles[i][j] != index) {
                    count++;
                }
            }
        }
        return count;
    }                  

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int count = 0;
        int index = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                index++;
                if (tiles[i][j] == 0) continue;
                int item = tiles[i][j];
                if (item == index) continue;
                int x = (item - 1) / n;
                int y = (item - 1) % n;
                count = count + Math.abs(x - i) + Math.abs(y - j);
            }
        }
        return count;
    }                

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }               

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int[][] tmp = clone(tiles);
        int rowA = 0, colA = 0, rowB = 0, colB = 0;
        outerloop:
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tmp[i][j] != 0) {
                    rowA = i;
                    colA = j;
                    break outerloop;
                }
            }
        }
        outerloop:
        for (int r = n - 1; r >= 0; r--) {
            for (int c = n - 1; c >= 0; c--) {
                if (tmp[r][c] != 0) {
                    rowB = r;
                    colB = c;
                    break outerloop;
                }
            }
        }
        swap(tmp, rowA, colA, rowB, colB);
        return new Board(tmp);
    }                    

    private void swap(int[][] blocks, int rowA, int colA, int rowB, int colB) {
        int tmp = blocks[rowA][colA];
        blocks[rowA][colA] = blocks[rowB][colB];
        blocks[rowB][colB] = tmp;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (this.n != that.n) return false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] != that.tiles[i][j]) return false;
            }
        }
        return true;
    }       

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<>();
        outerloop:
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    if (i > 0) {
                        int[][] upper = clone(tiles);
                        swap(upper, i, j, i - 1, j);
                        neighbors.add(new Board(upper));
                    }
                    if (i < n - 1) {
                        int[][] below = clone(tiles);
                        swap(below, i, j, i + 1, j);
                        neighbors.add(new Board(below));
                    }
                    if (j > 0) {
                        int[][] left = clone(tiles);
                        swap(left, i, j, i, j - 1);
                        neighbors.add(new Board(left));
                    }
                    if (j < n - 1) {
                        int[][] right = clone(tiles);
                        swap(right, i, j, i, j + 1);
                        neighbors.add(new Board(right));
                    }
                    break outerloop; 
                }
            }
        }
        return neighbors;
    }    

    private int[][] clone(int[][] blocks) {
        int[][] tmp = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tmp[i][j] = blocks[i][j];
            }
        }
        return tmp;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }              

    // unit tests (not graded)
    public static void main(String[] args) {
    } 
}
