
import java.util.Arrays;
import java.util.Stack;

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
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (board[i][j] != 0) {
                    if (board[i][j] != i * dimension() + j + 1) count++;
                }
            }
        }
        return count;
    }

    public int manhattan() {
        // sum of Manhattan distances between blocks and goal
        int count = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (board[i][j] != 0 && board[i][j] != i * dimension() + j + 1) {
                    int goal_i = (board[i][j] - 1) / dimension();
                    int goal_j = (board[i][j] - 1) % dimension();
                    count += Math.abs(i - goal_i) + Math.abs(j - goal_j);
                }
            }
        }
        return count;
    }

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

    public boolean equals(Object y) {
        // does this board equal y?
        if (y == this) return true;
        if (y == null) return false;
        if (!(y instanceof Board)) return false;
        if (!Arrays.deepEquals(board, ((Board) y).board)) return false;
        return true;
    }

    private int[][] swap(int[][] board, int x0, int x1, int y0, int y1) {
        //swap x and y elements in board
        if (x0 >= 0 && x0 < board.length && x1 >= 0 && x1 < board.length &&
                y0 >= 0 && y0 < board.length && y1 >= 0 && y1 < board.length) {
            int[][] newBoard = new int[board.length][];
            for (int i = 0; i < board.length; i++) {
                newBoard[i] = Arrays.copyOf(board[i], board[i].length);
            }
            newBoard[x0][x1] = board[y0][y1];
            newBoard[y0][y1] = board[x0][x1];
            return newBoard;
        } else return null;
    }

    public Iterable<Board> neighbors() {
        // all neighboring boards
        Stack<Board> stack = new Stack<>();
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (board[i][j] == 0) {
                    if (i + 1 < dimension()) stack.add(new Board(swap(board, i, j, i + 1, j)));
                    if (i - 1 >= 0) stack.add(new Board(swap(board, i, j, i - 1, j)));
                    if (j + 1 < dimension()) stack.add(new Board(swap(board, i, j, i, j + 1)));
                    if (j - 1 >= 0) stack.add(new Board(swap(board, i, j, i, j - 1)));
                    break;
                }
            }
        }
        return stack;
    }

    public String toString() {
        // string representation of this board (in the output format specified below)
        StringBuilder s = new StringBuilder();
        s.append(this.dimension() + "\n");
        for (int[] row : board) {
            for (int column : row) {
                s.append(column + " ");
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        // unit tests (not graded)
        int[][] blocks = {{8, 1, 3}, {4, 2, 0}, {7, 6, 5}};
        Board board = new Board(blocks);
        System.out.println(board);
        for (Board b : board.neighbors()) {
            System.out.println(b);
        }
        System.out.println();
        System.out.println(board.hamming());
        System.out.println(board.manhattan());
        System.out.println(board.twin().hamming());
    }
}

