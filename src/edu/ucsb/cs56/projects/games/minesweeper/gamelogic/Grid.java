package edu.ucsb.cs56.projects.games.minesweeper.gamelogic;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;
import java.util.Timer;

import edu.ucsb.cs56.projects.games.minesweeper.constants.Constants;
import edu.ucsb.cs56.projects.games.minesweeper.gamelogic.GridComponent;
import edu.ucsb.cs56.projects.games.minesweeper.gamelogic.PathFinder;


/**
 * The Grid class is the foundation for minesweeper, applies mine locations, checks if something is open,
 * makes flags functional, etc.
 * @author Caleb Nelson
 * @author David Acevedo
 * @version 2015/03/04 for lab07, cs56, W15
 *
 * @author Isaiah Egan
 * @author Austin Hwang
 * @author Sai Srimat
 * @version July 2016 for Legacy Code, cs56, M16
 *
 * @author Ryan Wiener
 */

public class Grid implements Serializable{
	private int seconds;
	private int minutes;
	private int hours;
	private transient Timer timer;
	private GridComponent[][] grid;
	private Constants.GameState gameState;
	private Constants.Difficulty difficulty;
	private int correctMoves;
	private Stack<GridComponent> makeVisible;
	private Dimension corOfClickedMine;



    private static int numMines;
    private static int numFlagged = 0;

	/**
	 * Default constructor for objects of class GUIGrid
	 * same as calling Grid(Constants.Difficulty.EASY)
	 */
	public Grid() { this(Constants.Difficulty.EASY); }

	/**
	 * Constructs grid from a given difficulty
	 * @param difficulty difficulty of the game that the grid will be
	 */
	public Grid(Constants.Difficulty difficulty) {
		deleteSave();
		gameState = Constants.GameState.PLAYING;
		this.difficulty = difficulty;
		grid = new GridComponent[Constants.getGridSize(difficulty)][Constants.getGridSize(difficulty)];
		makeVisible = new Stack<>();
		correctMoves=0;
		setCells();
		if (difficulty == Constants.Difficulty.TEST) {
			prepareTest(grid);
		}
	}

    /**
     * transforms the cell at cor (x,y) into a mine.
     * @param x xCor
     * @param y yCor
     */
	private void makeMine(int x,int y){
	    grid[x][y].makeMine();
    }


    /**
     * Prepares a test grid if the difficulty is TEST.
     * @param grid CellComponent[][] containing all the cells.
     */
    private void prepareTest(GridComponent[][] grid) {
        grid[3][3].makeMine();
        for (int i = 2; i <= 3; i++) {
            for (int j = 2; j <= 3; j++) {
                grid[i][j].iterate();
            }
        }
    }
    /**
     * Resets the grid.
     */
    public void resetGrid(){
        gameState = Constants.GameState.PLAYING;
        correctMoves=0;
        setCells();
        seconds=0;
        minutes=0;
        hours=0;
        numFlagged=0;


    }

    /**
     * Sets all cells in the grid to either bombs or a numerical value.
     */
    public void setCells() {
        Random random = new Random();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                grid[i][j] = new GridComponent();
                grid[i][j].setXCor(i);
                grid[i][j].setYCor(j);
            }
        }
        int bombs= (int)((Math.sqrt(difficulty.ordinal())+ difficulty.ordinal()/2) *grid.length);
        numMines = bombs;
        while (bombs > 0){
            int x = random.nextInt(grid.length);
            int y = random.nextInt(grid.length);
            if (!grid[x][y].getIsMine()){
                grid[x][y].makeMine();
                cellSymbolUpdate(x,y);
                bombs--;
            }

        }
    }

    /**
     * sets symbol of cell
     * @param x xCor
     * @param y yCor
     * @param symbol symbol
     */
    private void setSymbol(int x, int y,char symbol){
        grid[x][y].setSymbol(symbol);
    }
    /**
     * Helper method for test_shuffleMine.
     * @param x xCor
     * @param y yCor
     * @param grid game grid.
     * @return ArrayList containing the cell symbol of all neighbors.
     */
    public static ArrayList<Integer> getSurrounding(int x, int y, Grid grid){
        ArrayList<Integer> neighbors= new ArrayList<Integer>();
        int xStart=x-1;
        int xEnd=x+1;
        int yStart=y-1;
        int yEnd=y+1;
        //if the bomb is on the left edge.
        if (xStart <0){
            xStart=x;
        }
        //if the bomb is in the right edge.
        else if(xEnd > grid.getSize()-1){
            xEnd=x;
        }
        //if the bomb is in the top edge.
        if (yStart < 0){
            yStart=y;
        }
        //if the bomb is in the bottom edge.
        else if(yEnd > grid.getSize()-1){
            yEnd=y;
        }
        for(int k=xStart; k<=xEnd; k++){
            for(int n=yStart; n<=yEnd; n++){
                if(k != x && n!=y ) {
                    neighbors.add(Character.getNumericValue(grid.getCellSymbol(k, n)));
                }
            }
        }
        return neighbors;
    }

    /**
     * Moves a mine to a random available spot on the grid and increments or decrements its neighbors as needed.
     * @param x xCor of the clicked mine
     * @param y yCor of the clicked mine
     * @param grid The game grid.
     * @return Dimension object containing the new location of the mine. Used for test purposes.
     */
    public ArrayList<Integer> shuffleMine(int x, int y, Grid grid){
        ArrayList<Integer> newMineNeighborsBeforeShuffle= new ArrayList<>();
        // This decrements the surrounding cells.
        grid.setSymbol(x,y,'0');
        cellSymbolUpdate(x,y);
        //update the current cell.
        grid.setSymbol(x,y,'.');
        cellSymbolUpdate(x,y);
        Random random = new Random();
        int row=x;
        int col=y;
        int bomb=1;
        //find an empty spot that is not
        while(bomb != 0){
            row = random.nextInt(grid.getSize());
            col = random.nextInt(grid.getSize());
            if((row!=x && col !=y) && !grid.isMine(row,col)) {
                grid.makeMine(row,col);
                corOfClickedMine= new Dimension(row,col);
                //Would only be used for testing.
                //newMineNeighborsBeforeShuffle=getSurrounding(row,col,this);
                cellSymbolUpdate(row, col);
                bomb--;
            }
        }
        return newMineNeighborsBeforeShuffle;

    }
    /**
     * Finds all the bombs around a given cell.
     * @param row The row that the cell is found in.
     * @param column The column where the cell is found in.
     */

    private void cellSymbolUpdate(final int row, final int column){
        //If bomb is not on edge then use passed values.
        int xStart=row-1;
        int xEnd=row+1;
        int yStart=column-1;
        int yEnd=column+1;
        //if the bomb is on the left edge.
        if (xStart <0){
            xStart=row;
        }
        //if the bomb is in the right edge.
        else if(xEnd > grid.length-1){
            xEnd=row;
        }
        //if the bomb is in the top edge.
        if (yStart < 0){
            yStart=column;
        }
        //if the bomb is in the bottom edge.
        else if(yEnd > grid[row].length-1){
            yEnd=column;
        }
        char counter='0';
        for(int k=xStart; k<=xEnd; k++){
            for(int n=yStart; n<=yEnd; n++){
                //Got Lazy. Best approach would be to extract the counting into another function.
                if(grid[k][n].getIsMine() && grid[row][column].getSymbol() == '.'){
                    if (grid[k][n].getIsMine()){
                        counter++;
                    }
                }
                if(!grid[k][n].getIsMine()) {
                    if(grid[row][column].getIsMine()){
                        grid[k][n].iterate();
                    }
                    else if (grid[row][column].getSymbol() == '0'){
                        grid[k][n].decrement();
                    }
                }
            }
        }
        if(grid[row][column].getSymbol() == '.'){
            grid[row][column].setSymbol(counter);
        }

    }


    /**
     * Checks a cell to see if it has been opened
     * @param i row of box cell
     * @param j column of box cell
     * @return boolean indicating whether the cell has been opened or not
     */
    public boolean isOpen(int i, int j) throws IllegalArgumentException {
        if (i >= 0 && i < grid.length && j >= 0 && j < grid.length) {
            return grid[i][j].getIsOpen();
        } else {
            throw new IllegalArgumentException("I don't know where this exists :(");
        }
    }

    /**
     * Checks a cell to see if there is a mine underneath
     * @param i row of box cell
     * @param j column of box cell
     * @return a boolean indicating whether the spot is a mine or not
     */
    public boolean isMine(int i, int j) throws IllegalArgumentException {
        if (i >= 0 && i < grid.length && j >= 0 && j < grid.length) {
            return grid[i][j].getIsMine();
        } else {
            throw new IllegalArgumentException("I don't know where this exists :(");
        }
    }

    /**
     * Check to see if a user placed a flag on that cell
     * @param i row of box cell
     * @param j column of box cell
     * @return a boolean indicating whether a spot has been flagged or not
     */
    public boolean isFlag(int i, int j) throws IllegalArgumentException {
        if (i >= 0 && i < grid.length && j >= 0 && j < grid.length) {
            return grid[i][j].getIsFlagged();
        } else {
            throw new IllegalArgumentException("I don't know where this exists :(");
        }
    }

    /**
     * Get the state of the game
     * @return the gamestate as an enum from Constants.GameState
     */
    public Constants.GameState getGameState() {
        return gameState;
    }

    /**
     * returns a GridComponent located at position (x,y) in the grid.
     * @param x xCor
     * @param y yCor
     * @return GridComponent located at position (x,y)
     */
    public GridComponent getCell(int x, int y){
        return grid[x][y];
    }

    /**
     * Get the symbol of a cell
     * @param i row of box cell
     * @param j column of box cell
     * @return the symbol of the cell
     */
    public char getCellSymbol(int i, int j) {
        return grid[i][j].getSymbol();
    }

    /**
     * Used to get the coordinates of mine if clicked on first turn.
     * Ideally only call this method if the shuffleMine method was called. Has no use otherwise.
     * @return Dimension object containing the location of a mine.
     */
    public Dimension getCorOfClickedMine(){
        if(corOfClickedMine==null){
            return null;
        }
        else{
            Dimension temp= corOfClickedMine;
            corOfClickedMine=null;
            return temp;
        }
    }

    /**
     * get the grid ad a 2D array of chars
     * @return 2D array of chars where each entry represents the symbol of that cell
     */
    public char[][] getG() {
        char[][] g = new char[grid.length][grid.length];
        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < g.length; j++) {
                g[i][j] = grid[i][j].getSymbol();
            }
        }
        return g;
    }

    /**
     * Returns true if makeVisible is empty. False otherwise.
     * @return size of array containing the visible cells
     */
    public boolean isVisibleCellsEmpty(){return makeVisible.empty() ;}

    /**
     * return a reference to a cell that needs to be refreshed.
     * @return a cells that has been marked as open.
     */
	public GridComponent getVisibleCell(){
        return makeVisible.pop();
    }

    /**
     * Adds a Dimension object to makeVisible using the coordinates
     * @param openedCell Reference to a cell that has been marked as opened and needs to be updated in the game.
     */
    public void addVisibleCell(GridComponent openedCell){
	    makeVisible.push(openedCell);
    }

    /**
     * Adds one to correctMoves
     */
    public void incrementCorrectMoves(){
	    correctMoves++;
    }

    /**
     * Subtracts one from correctMoves
     */
    public void decrementCorrectMoves(){
	    correctMoves--;
    }



    /**
     * Places a flag on the cell
     * @param i row of box cell
     * @param j column of box cell
     */
    public void flagBox(int i, int j) {
        if (grid[i][j].getIsFlagged()) {
            //System.out.println("This box is already flagged!");
        } else if (grid[i][j].getIsFlagged()) {
            //System.out.println("You cannot put a flag on an opened box!");
        } else {
            // TODO: places 'F' only after a left click on a nonflag occurs?
            grid[i][j].setFlagged(true);
            numFlagged++;
            if (grid[i][j].getIsMine()) {
                incrementCorrectMoves();
                if (correctMoves >= grid.length * grid.length) {
                    gameState = Constants.GameState.WON;
                    endGame();
                }
            }
        }
    }

    /**
     * Removes a flag on a cell that has one
     * @param i row of box cell
     * @param j column of box cell
     */
    public void deflagBox(int i, int j) {
        if (!grid[i][j].getIsFlagged()) {
            //System.out.println("That box does not have a flag on it!");
        } else {
            grid[i][j].setFlagged(false);
            numFlagged--;
            if (grid[i][j].getIsMine()) {
               decrementCorrectMoves();
            }
        }
    }

    /**
     *
     * @return number of flags placed.
     */

    public int getNumFlagged(){
        return numFlagged;
    }

	/**
	 * Delete the current save file
	 */
	public void deleteSave() {
		File file = new File("MyGame.ser");
		file.delete();
	}

	/**
	 * End game when it is won or lost (not when a user ends it
	 */
	public void endGame() {
		stopTimer();
		deleteSave();
		//exposeMines();
	}

	/**
	 * start timer to keep track of time to finish the game
	 */
	public void startTimer() {
		timer = new Timer();
		timer.scheduleAtFixedRate(new Clock(), 0, 1000);
	}

	/**
	 * stop timer when user has stopped playing the game
	 */
	public void stopTimer() {
		timer.cancel();
		timer.purge();
	}

	/**
	 * get how long the user has been playing for
	 * @return the time the user has been playing for
	 */
	public String getGameTime() {
		return String.format("%02d:%02d:%02d",hours,minutes,seconds);
	}
    public int getNumMines(){
        return numMines;
    }

	/**
	 * Getter for size of the grid
	 * @return the size of the grid (the length of one row or one column)
	 */
	public int getSize() {
		return grid.length;
	}

	/**
	 * Get difficulty of the current game
	 * @return the difficulty as an enum from Constants.Difficulty
	 */
	public Constants.Difficulty getDifficulty() {
		return difficulty;
	}

    /**
     * Returns the grid from the user's previous game
     * @throws IOException if no save file
     * @throws ClassNotFoundException if no save file
     * @return the Grid object with the previous game's data
     */
    public static Grid loadGame() throws IOException, ClassNotFoundException {
        FileInputStream fileStream = new FileInputStream("MyGame.ser");
        ObjectInputStream os = new ObjectInputStream(fileStream);
        Object one;
        one = os.readObject();
        os.close();
        Grid g = (Grid) one;
        g.startTimer();
        return g;
    }

    /**
     * Checks whether a save file exists.
     * @return true if save file exists and false otherwise
     */
    public static boolean saveExist(){

        if (new File("MyGame.ser").isFile()){
            return true;
        }
        return false;

    }

    /**
     * Saves the current game (this) into a serialized file
     */
    public void save() {
        try {
            FileOutputStream fileStream = new FileOutputStream("MyGame.ser");
            ObjectOutputStream os = new ObjectOutputStream(fileStream);
            os.writeObject(this);
            os.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Opens the cell and returns what will be placed there
     * @param i row of box cell
     * @param j column of box cell
     * @return a the symbol of the cell that was just opened or 'e' if not opened
     */
    public char searchBox(int i, int j) {
        char currentCell = 'e';
        if (!grid[i][j].getIsOpen() && !grid[i][j].getIsFlagged() ){
            currentCell = grid[i][j].getSymbol();
            grid[i][j].open();
            if (grid[i][j].getIsMine()) {
                gameState = Constants.GameState.LOST;
                endGame();
            } else {
                if (currentCell == '0') {

                    PathFinder.findCellsToOpen(i, j,this);
                }
                else{
                    incrementCorrectMoves();
                    if (correctMoves >= grid.length * grid.length) {
                        gameState = Constants.GameState.WON;
                        endGame();
                    }

                }

            }
        }

        return currentCell;
    }

    /**
	 * Prints out the game
	 * Used for the text game
	 * @return a text display of the current game
	 */
	@Override
	public String toString() {
		String borders = " ";
		String line = "|";
		String preSpace = "";
		String game = "";
		game += "Difficulty: " + getDifficulty().toString() + '\t';
		game += "Time elapsed: " + String.format("%02d:%02d:%02d",hours,minutes,seconds) + '\n';
		game += '\n';
		for (int i = 1; i < Integer.toString(grid.length).length(); i++) {
			preSpace += " ";
		}
		for (int i = 0; i <= Integer.toString(grid.length).length(); i++) {
			game += " ";
			borders += " ";
		}
		for (int i = 0; i < grid.length; i++) {
			for (int j = Integer.toString(i).length(); j <= Integer.toString(grid.length).length(); j++) {
				game += " ";
				borders += "-";
			}
			game += i;
			for (int j = 0; j < Integer.toString(i).length(); j++) {
				borders += "-";
			}
		}
		game += "\n";
		game += borders;
		game += "\n";
		for (int i = 0; i < grid.length; i++) {
			for (int j = Integer.toString(i).length(); j < Integer.toString(grid.length).length(); j++) {
				game += " ";
			}
			game += i + line;
			game += " ";
			for (int j = 0; j < grid.length; j++) {
				game += preSpace;
				if (grid[i][j].getIsFlagged()) {
					game += Constants.ANSI_RED + grid[i][j] + Constants.ANSI_RESET;
				} else if (grid[i][j].getIsOpen()) {
					if (grid[i][j].getIsMine()) {
						game += Constants.ANSI_RED + grid[i][j] + Constants.ANSI_RESET;
					} else {
						game += Constants.ANSI_BLUE + grid[i][j] + Constants.ANSI_RESET;
					}
				} else {
					game += grid[i][j];
				}
				game += line;
			}
			game += "\n";
		}
		return game;
	}

	/**
	 * Class that extends from TimerTask to keep track of users time on the current game
	 */
	public class Clock extends TimerTask {

		/**
		 * Increment gameTime every second
		 */
		public void run(){
			seconds++;
			if(seconds>=60){
			    seconds=0;
			    minutes++;
            }
            if(minutes>=60){
			    minutes=0;
			    hours++;
            }
            if(hours >=24){
			    hours=0;
			    minutes=0;
			    seconds=0;
			    String Message="WTF";
            }
		}
	}
}
