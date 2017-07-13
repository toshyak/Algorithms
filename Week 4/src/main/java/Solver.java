import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Stack;


/**
 * Created by Alexey on 11.07.2017.
 */
public class Solver {
    private int moves = 0;
    private ArrayList<Board> solution = null;

    public Solver(Board initial) {
        // find a solution to the initial board (using the A* algorithm)
        if (initial == null) throw new IllegalArgumentException("Argument cannot be null!");
        SearchNode initialNode = new SearchNode(initial, 0, null, false);
        MinPQ<SearchNode> pq = new MinPQ<>(initialNode.manhattan());
        pq.insert(initialNode);
        pq.insert(new SearchNode(initial.twin(), 0, null, true));
        boolean found = Boolean.FALSE;
        SearchNode currentNode = null;
        while (!found) {
            currentNode = pq.delMin();
            if (!currentNode.board.isGoal()) {
                for (Board b : currentNode.board.neighbors()) {
                    if (currentNode.parentNode == null || !b.equals(currentNode.parentNode.board)) {
                        pq.insert(new SearchNode(b, currentNode.moves + 1, currentNode, currentNode.isTwin));
                    }
                }
            } else found = Boolean.TRUE;
        }
        if (!currentNode.isTwin) {
            moves = currentNode.moves;
            solution = new ArrayList<>();
            Stack<Board> stack = new Stack<Board>();
            while (currentNode != null) {
                stack.push(currentNode.board);
                currentNode = currentNode.parentNode;
            }
            while (!stack.empty()) {
                solution.add(stack.pop());
            }
        }
    }

    private class SearchNode {
        private Board board;
        private SearchNode parentNode;
        private int moves;
        private boolean isTwin;

        private SearchNode(Board board, int moves, SearchNode parentNode, boolean isTwin) {
            this.board = board;
            this.moves = moves;
            this.parentNode = parentNode;
            this.isTwin = isTwin;
        }

//        private int hammingPriority() {
//            return this.board.hamming() + moves;
//        }

        private int manhattanPriority() {
            return this.board.manhattan() + moves;
        }

//        private Comparator<SearchNode> hamming() {
//            return new hammingComparator();
//        }

        private Comparator<SearchNode> manhattan() {
            return new manhattanComparator();
        }

//        public class hammingComparator implements Comparator<SearchNode> {
//            @Override
//            public int compare(SearchNode node1, SearchNode node2) {
//                if (node1.hammingPriority() == node2.hammingPriority()) return 0;
//                else if (node1.hammingPriority() > node2.hammingPriority()) return 1;
//                else return -1;
//            }
//        }

        public class manhattanComparator implements Comparator<SearchNode> {
            @Override
            public int compare(SearchNode node1, SearchNode node2) {
                if (node1.manhattanPriority() == node2.manhattanPriority()) return 0;
                else if (node1.manhattanPriority() > node2.manhattanPriority()) return 1;
                else return -1;
            }
        }
    }

    public boolean isSolvable() {
        // is the initial board solvable?
        return solution() != null;
    }

    public int moves() {
        // min number of moves to solve initial board; -1 if unsolvable
        if (isSolvable()) return moves;
        else return -1;
    }

    public Iterable<Board> solution() {
        // sequence of boards in a shortest solution; null if unsolvable
        return solution;
    }

    public static void main(String[] args) {
        // create initial board from file
        System.out.println(args[0]);
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

