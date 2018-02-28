package edu.ucsb.cs56.projects.games.minesweeper.gamelogic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import edu.ucsb.cs56.projects.games.minesweeper.constants.Constants;

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

	private int gameTime;
	private transient Timer timer;
	private GridComponent[][] grid;
	private Constants.GameState gameState;
	private Constants.Difficulty difficulty;
	private int correctMoves;

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
		correctMoves = 0;
		grid = new GridComponent[Constants.getGridSize(difficulty)][Constants.getGridSize(difficulty)];
		setZero();
		if (difficulty == Constants.Difficulty.TEST) {
			grid[3][3].makeMine();
			for (int i = 2; i <= 3; i++) {
				for (int j = 2; j <= 3; j++) {
					grid[i][j].iterate();
				}
			}
		}
		for (int i = 0; i < difficulty.ordinal() * grid.length; i++) {
			setMine();
		}
		//timer shouldnt start until the first tile is tapped.
		startTimer();
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
	public int getGameTime() {
		return gameTime;
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
	 * Sets all cells in the grid to zero.
	 */
	public void setZero() {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				grid[i][j] = new GridComponent();
			}
		}
	}

	/**
	 * Finds a random Empty cell and makes it a Mine
	 */
	public void setMine() {
		int spotX = (int) (grid.length * grid.length * Math.random());
		int a = spotX / grid.length;
		int b = spotX % grid.length;
		if (grid[a][b].makeMine()) {
			for (int i = a - 1; i <= a + 1; i++) {
				for (int j = b - 1; j <= b + 1; j++) {
					if (i >= 0 && i < grid.length && j >= 0 && j < grid.length) {
						grid[i][j].iterate();
					}
				}
			}
		} else {
			setMine();
		}
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
		game += "Time elapsed: " + gameTime + '\n';
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
	 * Opens the cell and returns what will be placed there
	 * @param i row of box cell
	 * @param j column of box cell
	 * @return a the symbol of the cell that was just opened or 'e' if not opened
	 */
	public char searchBox(int i, int j) {
		char spot = 'e';
		if (i >= 0 && i < grid.length && j >= 0 && j < grid.length) {
			// set variable to an object in the grid
			if (grid[i][j].getIsFlagged()) {
				//System.out.println("You cannot search a flagged box!");
			} else if (grid[i][j].getIsOpen()) {
				//System.out.println("You cannot search an opened box!");
			} else {
				spot = grid[i][j].getSymbol();
				grid[i][j].open();
				if (grid[i][j].getIsMine()) {
					gameState = Constants.GameState.LOST;
					endGame();
					exposeMines();
				} else {
					correctMoves++;
					if (correctMoves >= grid.length * grid.length) {
						gameState = Constants.GameState.WON;
						endGame();
					} else if (grid[i][j].getSymbol() == '0') {
						findAllZeros(i, j);
					}
				}
			}
		}
		return spot;
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
			if (grid[i][j].getIsMine()) {
				correctMoves++;
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
			if (grid[i][j].getIsMine()) {
				correctMoves--;
			}
		}
	}

	/**
	 * Looks for surrounding numbers near the cell and opens them, repeats when find another zero
	 * @param row row of box cell
	 * @param col column of box cell
	 */
    public void findAllZeros(int row, int col) { //TODO: throw exception
	Queue<Integer> bfs = new LinkedList<Integer>();
	if (grid[row][col].getSymbol() == '0') {
	    bfs.add(row * grid.length + col);
	    while (!bfs.isEmpty()) {
		row = bfs.peek() / grid.length;
		col = bfs.poll() % grid.length;
		if (grid[row][col].getSymbol() == '0') {
		    for (int i = row - 1; i <= row + 1; i++) {
			for (int j = col - 1; j <= col + 1; j++) {
			    if (i >= 0 && i < grid.length && j >= 0 && j < grid.length && !grid[i][j].getIsFlagged() && !grid[i][j].getIsMine() && !grid[i][j].getIsOpen()) {
				correctMoves++;
				if (correctMoves >= grid.length * grid.length) {
				    gameState = Constants.GameState.WON;
				    endGame();
				}
				if (grid[i][j].getSymbol() == '0') {
				    bfs.add(i * grid.length + j);
				}
				grid[i][j].open();
			    }
			}
		    }
		}
	    }
	}
    }

	/**
	 * Display where all the mines were after a user lost
	 */
	private void exposeMines() {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				if (grid[i][j].getIsMine()) {
					grid[i][j].open();
				}
			}
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
	 * Get the symbol of a cell
	 * @param i row of box cell
	 * @param j column of box cell
	 * @return the symbol of the cell
	 */
	public char getCell(int i, int j) {
		return grid[i][j].getSymbol();
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
	 * Open the cell and all surrounding cells
	 * @param row row of box cell
	 * @param col column of box cell
	 * Will only work if the correct number of flags are adjacent to the space being clicked on
	 * @return boolean indicating whether the move was allowed or not
	 */
	public boolean searchSurrounding(int row, int col) {
		int numFlags = 0;
		for (int i = row - 1; i <= row + 1; i++) {
			for (int j = col - 1; j <= col + 1; j++) {
				if ((i >= 0 && i < grid.length) && (j >= 0 && j < grid.length)) {
					if (grid[i][j].getIsFlagged()) {
						numFlags++;
					}
				}
			}
		}
		if (Integer.toString(numFlags).equals(Character.toString(grid[row][col].getSymbol())) && !grid[row][col].getIsFlagged()) {
			for (int i = row - 1; i <= row + 1; i++) {
				for (int j = col - 1; j <= col + 1; j++) {
					if ((i >= 0 && i < grid.length) && (j >= 0 && j < grid.length)) {
						searchBox(i, j);
					}
				}
			}
			return true;
		}
		return false;
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
	 * Returns true if a saved file exists, false otherwise
	 * @throws IOException if no save file
	 * @throws ClassNotFoundException if no save file
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
	 * Class that extends from TimerTask to keep track of users time on the current game
	 */
	public class Clock extends TimerTask {

		/**
		 * Increment gameTime every second
		 */
		public void run(){
			gameTime++;
		}
	} // class Clock
}
