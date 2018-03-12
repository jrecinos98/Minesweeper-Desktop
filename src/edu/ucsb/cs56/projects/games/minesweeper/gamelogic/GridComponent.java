package edu.ucsb.cs56.projects.games.minesweeper.gamelogic;

import java.io.Serializable;

/**
 * A class to represent a cell in the minesweeper grid
 * @author Ryan Wiener
 */

public class GridComponent implements Serializable {

	private boolean isOpen;
	private boolean isFlagged;
	private char symbol;
	private int xCor;
	private int yCor;

	/**
	 * Creates default grid element
	 * sets open to false, flagged to false ans symbol to '0'
	 */
	public GridComponent() {
		isOpen = false;
		isFlagged = false;
		symbol = '0';
	}

	/**
	 * increment the symbol of the cell.
	 * Won't work if a mine
	 */
	public void iterate() {
		if (symbol != 'X') {
			symbol++;
		}
	}

    /**
     * decrement the symbol of the cell.
     */
	public void decrement(){
	    if(symbol != '0'){
	        symbol--;
        }
    }

	/**
	 * sets the xCor of cell on the grid
	 * @param x xCor
	 */
	public void setXCor(int x){
	    xCor=x;
    }

    /**
     * sets the yCor of cell on the grid
     * @param y yCor
     */
    public void setYCor(int y){
	    yCor=y;
    }

    /**
     *returns xCor
     * @return xCor
     */
    public int getX(){
	    return xCor;
    }

    /**
     * return yCor
     * @return yCor
     */
    public int getY(){
	    return yCor;
    }
	/**
	 * Make this cell a mine
	 * Won't work if already a mine
	 * @return boolean indicating whether the function was successful
	 */
	public boolean makeMine() {
		if (symbol == 'X') {
			return false;
		}
		symbol = 'X';
		return true;
	}

	/**
	 * open the cell
	 * Won't work if flagged
	 */
	public void open() {
		if (!isFlagged) {
			isOpen = true;
		}
	}

	/**
	 * set the flagged boolean of the cell
	 * Won't work if already opened
	 * @param val value to set flagged as
	 */
	public void setFlagged(boolean val) {
		if (!isOpen) {
			isFlagged = val;
		}
	}

	/**
	 * get whether cell is open
	 * @return boolean indicating whether cell has been opened
	 */
	public boolean getIsOpen() {
		return isOpen;
	}

	/**
	 * Get whether cell is flagged
	 * @return boolean indicating whether cell is flagged
	 */
	public boolean getIsFlagged() {
		return isFlagged;
	}
    /**
     * @return If the cell is flagged or open then return true (meaning it has been traversed before). Else return false.
     */
    public boolean getIsMarked(){
        if (getIsFlagged() || getIsOpen()){
            return true;
        }
        else
            return false;
    }

	/**
	 * Get whether cell is a mine
	 * @return boolean indicating whether cell is a mine
	 */
	public boolean getIsMine() {
		return symbol == 'X';
	}

	/**
	 * get symbol of cell (will give symbol even if opened or flagged)
	 * @return symbol of cell
	 */
	public char getSymbol() {
		return symbol;
	}

    /**
     * Changes the symbol of the cell
     * @param symbol Symbol to be changed to.
     */
	public void setSymbol(char symbol){
	    this.symbol=symbol;
    }

	/**
	 * Get string representation of cell
	 * @return Symbol if opened, "F" if flagged or "?" if neither opened nor flagged
	 */
	@Override
	public String toString() {
		if (isOpen) {
			return Character.toString(symbol);
		} else if (isFlagged) {
			return "F";
		} else {
			return "?";
		}
	}
}
