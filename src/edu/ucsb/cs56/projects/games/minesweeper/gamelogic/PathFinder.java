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
     * @param cell GridComponent[][] that contains all of the cells in the grid.
     */
    public static void findEmpty(int k, int n, GridComponent[][] cell){
        stackPush(cell[k][n]);
        //char empty= '0';
        GridComponent temp;
        int counter=0;
        System.out.println("X:"+k+ "  Y:"+n);
        while(!stack.empty()) {
            temp=stack.pop();
            temp.open();
            Grid.addVisibleCell(temp.getX(), temp.getY());
            //if(temp.getSymbol()!='0')
            counter++;
            System.out.println(counter);
            //System.out.println("X: "+temp.getX()+"  Y: "+temp.getY() + " Symbol: "+temp.getSymbol());
            Grid.incrementCorrectMoves();
            if (temp.getSymbol() == '0') {
                surroundingCells(cell, temp.getX(), temp.getY());
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
    }
    private static void surroundingCells(GridComponent[][] cells, final int row, final int column){
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
        else if(xEnd > cells.length-1){
            xEnd=row;
        }
        //if the cell is in the top edge.
        if (yStart < 0){
            yStart=column;
        }
        //if the cell is in the bottom edge.
        else if(yEnd > cells[row].length-1){
            yEnd=column;
        }
        for(int k=xStart; k<=xEnd; k++){
            for(int n=yStart; n<=yEnd; n++){
                if(!cells[k][n].getIsMarked() && cells[k][n].getSymbol()=='0') {
                    stack.push(cells[k][n]);
                }
                else if (!cells[k][n].getIsMarked()){
                    cells[k][n].open();
                    Grid.addVisibleCell(k,n);
                    Grid.incrementCorrectMoves();
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
