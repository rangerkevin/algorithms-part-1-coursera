/* ************************************************************************
 *
 * Name: Wei Sun
 * Date: 3/19/2019
 *
 *********************************************************************** */
import java.lang.Math;
import java.util.LinkedList;
import java.util.Deque;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private MinPQ<SearchNode> pq;
    private boolean isSolvable = false;
    private int totalMoves;
    private SearchNode goalNode;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new java.lang.IllegalArgumentException("Null block");
        pq = new MinPQ<>();
        pq.insert(new SearchNode(initial, 0, null));

        while (!pq.isEmpty()) {
            SearchNode currNode = pq.delMin();
            Board currBoard = currNode.getBoard();

            int moves = currNode.getMoves();
            SearchNode prevNode = currNode.getPredecessor();
            Board prevBoard = (prevNode == null) ? null : prevNode.getBoard();

            if (currBoard.isGoal()) {
                isSolvable = true;
                totalMoves = moves;
                goalNode = currNode;
                break;
            }
    
            if (currBoard.hamming() == 2 && currBoard.twin().isGoal()) {
                break;
            }

            for (Board neighbor : currBoard.neighbors()) {
                if (prevBoard != null && neighbor.equals(prevBoard)) continue;
                pq.insert(new SearchNode(neighbor, moves + 1, currNode));
            }
        }
    }

    private class SearchNode implements Comparable<SearchNode> {
        private int moves;
        private Board board;
        private SearchNode predecessor;

        public SearchNode(Board board, int moves, SearchNode predecessor) {
            this.moves = moves;
            this.board = board;
            this.predecessor = predecessor;
        }

        private int priority() {
            return moves + board.manhattan();
        }

        @override
        public int compareTo(SearchNode that) {
            return this.priority() - that.priority();
        }

        public int getMoves() {
            return moves;
        }

        public Board getBoard() {
            return board;
        }

        public SearchNode getPredecessor() {
            return predecessor;
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return isSolvable;
    }           

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return (isSolvable()) ? totalMoves : -1;
    }                    

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        Deque<Board> deque = new LinkedList<>();
        SearchNode node = goalNode;
        
        while (node != null) {
            Board board = node.getBoard();
            deque.addFirst(board);
            SearchNode prev = node.getPredecessor();
            node = prev;
        }
        return deque;
    }     

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    } 
}
