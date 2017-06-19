/**
 * Created by Alex on 16.06.17.
 */
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF model;
    private int numberOfOpenSites = 0;
    private int openSites [];
    private int size;
    private int fakeTop;
    private int fakeBottom;

    public Percolation(int n) {
        // create n-by-n grid, with all sites blocked
        if (n<= 0){
            throw new java.lang.IllegalArgumentException("Grid size should be more then 0!");
        }
        size = n;
        model = new WeightedQuickUnionUF(size*size + 2);
        openSites = new int [size*size];
        for (int i=0; i<size*size; i++){
            openSites[i] = 0;
        }
        fakeTop = size * size;
        fakeBottom = size * size + 1;

    }
    private int sitePosition(int row, int col){
        return (row -1)*size + (col -1);
    }

    public    void open(int row, int col)  {
        // open site (row, col) if it is not open already
        validateBounds(row, col);
        int sitePosition = sitePosition(row, col);
        if (isOpen(row, col)) return;
        openSites[sitePosition] = 1;
        numberOfOpenSites++;
        if (row == 1){
            model.union(sitePosition, fakeTop);
        }
        else if (isOpen(row-1, col)){
            model.union(sitePosition, sitePosition(row-1, col));
        }
        if (row == size){
            model.union(sitePosition, fakeBottom);
        }
        else if (isOpen(row+1, col)){
            model.union(sitePosition, sitePosition(row+1,col));
        }
        if (col > 1 && isOpen(row, col-1)){
            model.union(sitePosition, sitePosition(row, col-1));
        }
        if (col < size && isOpen(row, col+1)){
            model.union(sitePosition, sitePosition(row, col+1));
        }
    }


    public boolean isOpen(int row, int col) {
        // is site (row, col) open?
        validateBounds(row, col);
        return openSites[sitePosition(row,col)]!=0;
    }
    public boolean isFull(int row, int col) {
        // is site (row, col) full?
        validateBounds(row, col);
        return model.connected(sitePosition(row, col),fakeTop );
    }
    private void validateBounds(int row, int col){
        if (row <=0 || row >size || col <= 0 || col > size){
            throw new IndexOutOfBoundsException("Row or column index is out of bound!");
        }
    }

    public     int numberOfOpenSites() {
        // number of open sites
        return numberOfOpenSites;
    }

    public boolean percolates() {
        // does the system percolate?
        return model.connected(fakeTop, fakeBottom);
    }

    public static void main(String[] args) {
        // test client (optional)
        int size = 15;
        Percolation model = new Percolation(size);
        while (!model.percolates()){
            model.open(StdRandom.uniform(1, size+1), StdRandom.uniform(1,size+1));
        }
        System.out.println(model.numberOfOpenSites());
    }

}
