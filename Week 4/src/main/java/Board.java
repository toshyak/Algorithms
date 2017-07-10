import java.util.Arrays;

/**
 * Created by Alex on 10.07.17.
 */
public class Board {
    private int[][] board;

    public Board(int[][] blocks) {
        // construct a board from an n-by-n array of blocks
        // (where blocks[i][j] = block in row i, column j)
        board = Arrays.copyOf(blocks, blocks.length);
    }

    public int dimension() {
        // board dimension n
        return board.length;
    }

    public int hamming() {
        // number of blocks out of place
        int count = 0;
        if (isGoal()) return count;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (board[i][j] != 0) {
                    if (board[i][j] != i * dimension() + j + 1) count++;
                }
            }
        }
        return count;
    }

    //    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    public boolean isGoal() {
        // is this board the goal board?
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (board[i][j] != i * dimension() + j + 1 && i + j + 2 != 2 * dimension()) return false;
            }
        }
        return true;
    }

    public Board twin() {
        // a board that is obtained by exchanging any pair of blocks
        int[][] twinBoard = Arrays.copyOf(board, dimension());
        if (twinBoard[0][0] == 0 || twinBoard[0][1] == 0) {
            int exch = twinBoard[1][0];
            twinBoard[1][0] = twinBoard[1][1];
            twinBoard[1][1] = exch;
        } else {
            int exch = twinBoard[0][0];
            twinBoard[0][0] = twinBoard[0][1];
            twinBoard[0][1] = exch;
        }
        return new Board(twinBoard);
    }

    //    public boolean equals(Object y)        // does this board equal y?
//    public Iterable<Board> neighbors()     // all neighboring boards
    public String toString() {
        // string representation of this board (in the output format specified below)
        StringBuilder s = new StringBuilder();
        s.append(this.dimension() + "\n");
        for (int[] row : board) {
            for (int column : row) {
                if (column == 0) {
                    s.append("  ");
                } else s.append(column + " ");
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        // unit tests (not graded)
        int[][] blocks = {{4, 3, 2}, {1, 0, 6}, {7, 8, 5}};
        Board board = new Board(blocks);
        System.out.println(board);
        System.out.println(board.hamming());
        System.out.println(board.twin().hamming());
    }
}

