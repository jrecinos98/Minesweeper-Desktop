package edu.ucsb.cs56.projects.games.minesweeper.gamelogic;
import edu.ucsb.cs56.projects.games.minesweeper.gamelogic.GridComponent;
import edu.ucsb.cs56.projects.games.minesweeper.gamelogic.Grid;


public class PathFinder {
    /**
     * Searches for all the cells that need to be visible.
     * @param k Row of the current cell
     * @param n Column of the current cell
     * @param cell GridComponent[][] that contains all of the cells in the grid.
     */
    public static void findEmpty(int k, int n, GridComponent[][] cell){
        cell[k][n].open();
        Grid.addVisibleCell(k,n);
        Grid.incrementCorrectMoves();
        char empty= '0';
        if (k-1>=0) {
            if (!cell[k - 1][n].getIsMarked()) {
                if ((cell[k - 1][n].getSymbol()) == empty ){
                    findEmpty(k - 1, n, cell);
                }
                else {
                    cell[k - 1][n].open();
                    Grid.addVisibleCell(k-1,n);
                    Grid.incrementCorrectMoves();
                }

            }
        }
        if(n-1>=0){
            if (!cell[k][n-1].getIsMarked()){
                if((cell[k][n-1].getSymbol()) == empty) {
                    findEmpty(k, n - 1, cell);
                }
                else {
                    cell[k][n-1].open();
                    Grid.addVisibleCell(k,n-1);
                    Grid.incrementCorrectMoves();
                }


            }
        }
        if (n+1 <= cell[k].length-1){
            if (!cell[k][n+1].getIsMarked()){
                if ((cell[k][n+1].getSymbol()) == empty ) {
                    findEmpty(k, n + 1, cell);
                }
                else {
                    cell[k][n+1].open();
                    Grid.addVisibleCell(k,n+1);
                    Grid.incrementCorrectMoves();
                }

            }
        }
        if (k+1 <= cell.length-1){
            if (!cell[k+1][n].getIsMarked()){
                if ((cell[k+1][n].getSymbol()) == empty){
                    findEmpty(k+1,n,cell);
                }
                else {
                    cell[k+1][n].open();
                    Grid.addVisibleCell(k+1,n);
                    Grid.incrementCorrectMoves();
                }


            }
        }
    }
}
