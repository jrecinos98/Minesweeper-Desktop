package edu.ucsb.cs56.projects.games.minesweeper.gamelogic;
import edu.ucsb.cs56.projects.games.minesweeper.gamelogic.GridComponent;
import edu.ucsb.cs56.projects.games.minesweeper.gamelogic.Grid;

import java.awt.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.Stack;


public class PathFinder{
    private static Stack<GridComponent> stack;
    /**
     * Searches for all the cells that need to be visible.
     * @param k Row of the current cell
     * @param n Column of the current cell
     * @param grid Grid object that contains all of the cells.
     */
    public static void findEmpty(int k, int n, Grid grid){
        stackPush(grid.getCell(k,n));
        GridComponent temp;
        //System.out.println("X:"+k+ "  Y:"+n);
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
    private static void stackPush(GridComponent grid){
        if(stack == null) {
            stack = new Stack<GridComponent>();
        }
        stack.push(grid);
    }

    /*
            if (k - 1 >= 0) {
                if (!cell[k - 1][n].getIsMarked()) {
                    if ((cell[k - 1][n].getSymbol()) == empty) {
                        findEmpty(k - 1, n, cell);
                    } else {
                        cell[k - 1][n].open();
                        Grid.addVisibleCell(k - 1, n);
                        Grid.incrementCorrectMoves();
                    }

                }
            }
            if (n - 1 >= 0) {
                if (!cell[k][n - 1].getIsMarked()) {
                    if ((cell[k][n - 1].getSymbol()) == empty) {
                        findEmpty(k, n - 1, cell);
                    } else {
                        cell[k][n - 1].open();
                        Grid.addVisibleCell(k, n - 1);
                        Grid.incrementCorrectMoves();
                    }


                }
            }
            if (n + 1 <= cell[k].length - 1) {
                if (!cell[k][n + 1].getIsMarked()) {
                    if ((cell[k][n + 1].getSymbol()) == empty) {
                        findEmpty(k, n + 1, cell);
                    } else {
                        cell[k][n + 1].open();
                        Grid.addVisibleCell(k, n + 1);
                        Grid.incrementCorrectMoves();
                    }

                }
            }
            if (k + 1 <= cell.length - 1) {
                if (!cell[k + 1][n].getIsMarked()) {
                    if ((cell[k + 1][n].getSymbol()) == empty) {
                        findEmpty(k + 1, n, cell);
                    } else {
                        cell[k + 1][n].open();
                        Grid.addVisibleCell(k + 1, n);
                        Grid.incrementCorrectMoves();
                    }


                }
            }*/
}
