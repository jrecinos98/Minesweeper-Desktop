package edu.ucsb.cs56.projects.games.minesweeper.gamelogic;
import edu.ucsb.cs56.projects.games.minesweeper.gamelogic.GridComponent;
import edu.ucsb.cs56.projects.games.minesweeper.gamelogic.Grid;
import java.util.Stack;

/**
 * PathFinder is an abstract class with a single public method called findEmpty.
 * findEmpty is used to find and open all the cells that should be revealed after the user clicks on an empty cell.
 * The cell located in grid[row][col] must be empty (the cell symbol is '0'). This is pushed onto the stack, added to makeVisible and we search
 * its neighboring cells. Any neighbor empty cells are pushed onto the stack and any cells containing a numerical value are opened added to makeVisible,
 * but not pushed to the stack. Repeat until the stack is empty.
 *
 * @author Jose Recinos, Winter 2018.
 */
public abstract class PathFinder{
    private static Stack<GridComponent> stack;
    /**
     * Precondition: The cell at specified row and column must be an empty cell.
     * PostCondition: The location of the cells that have been marked as open have been added to
     * the array of visible cells in the grid.
     * @param row Row of the current cell
     * @param col Column of the current cell
     * @param grid Grid object that contains all of the cells in the current game.
     */
    public static void findCellsToOpen(int row, int col, Grid grid){
        stackPush(grid.getCell(row,col));
        GridComponent temp;
        while(!stack.empty()) {
            temp=stack.pop();
            temp.open();
            grid.addVisibleCell(temp.getX(), temp.getY());
            if (temp.getSymbol() == '0') {
                surroundingCells(grid, temp.getX(), temp.getY());
            }
        }
    }
    private static void surroundingCells(Grid grid, final int row, final int column){
        //If cell is not on edge then use passed values.
        int xStart=row-1;
        int xEnd=row+1;
        int yStart=column-1;
        int yEnd=column+1;
        //if the cell is on the left edge.
        if (xStart <0){
            xStart=row;
        }
        //if the cell is in the right edge.
        else if(xEnd > grid.getSize()-1){
            xEnd=row;
        }
        //if the cell is in the top edge.
        if (yStart < 0){
            yStart=column;
        }
        //if the cell is in the bottom edge.
        else if(yEnd > grid.getSize()-1){
            yEnd=column;
        }
        for(int k=xStart; k<=xEnd; k++){
            for(int n=yStart; n<=yEnd; n++){
                if(!grid.getCell(k,n).getIsMarked() && grid.getCell(k,n).getSymbol()=='0') {
                    if(stack.search(grid.getCell(k,n)) == -1) {
                        stack.push(grid.getCell(k, n));
                    }
                }
                else if (!grid.getCell(k,n).getIsMarked()){
                    grid.getCell(k,n).open();
                    grid.addVisibleCell(k,n);
                }
            }
        }

    }
    private static void stackPush(GridComponent cell){
        if(stack == null) {
            stack = new Stack<GridComponent>();
        }
        stack.push(cell);
    }

}
