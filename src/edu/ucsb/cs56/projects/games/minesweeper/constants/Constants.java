package edu.ucsb.cs56.projects.games.minesweeper.constants;

import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Supplies universal enums and finals for classes to share between each other
 * @author Ryan Wiener
 */

public class Constants {

	private Constants() {}

	/**
	 * The game state of the grid
	 */
	public enum GameState {
		PLAYING,
		LOST,
		WON
	}

	/**
	 * The current state of the application for the text game
	 */
	public enum ApplicationState {
		MAINMENU,
		GAME,
		LEADERBOARD
	}

	/**
	 * The difficulty of the game
	 */
	public enum Difficulty {
		TEST,
		EASY,
		MEDIUM,
		HARD,
		HARDCORE,
		EXTREME,
		LEGENDARY,
		LOAD,
	}

	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_RESET = "\u001B[0m";

	private static final Map<Difficulty, Integer> gridSizes = new HashMap<>();
	private static final Map<Difficulty, Dimension> windowSizes = new HashMap<>();

	/**
	 * links the difficulty enum to grid size
	 * @param difficulty Difficulty enum member that you want the grid size of
	 * @return size of grid for the passed in difficulty
	 */
	public static int getGridSize(Difficulty difficulty) {
		if (gridSizes.size() == 0) {
			gridSizes.put(Difficulty.TEST, 4);
			gridSizes.put(Difficulty.EASY, 10);
			gridSizes.put(Difficulty.MEDIUM, 15);
			gridSizes.put(Difficulty.HARD, 20);
			gridSizes.put(Difficulty.HARDCORE, 25);
			gridSizes.put(Difficulty.EXTREME, 30);
			gridSizes.put(Difficulty.LEGENDARY, 35);
		}
		return gridSizes.get(difficulty);
	}

    /**
     * links difficulty enum to a Dimension object that contains the width and height of frame
     * @param difficulty Difficulty enum member that you want the frame size of
     * @return Dimension object containing the width and height for the passed difficulty.
     */
	public static Dimension getWindowSizes(Difficulty difficulty){
	    if(windowSizes.size() == 0){
	        //Obtains the dimensions of the display.
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

            windowSizes.put(Difficulty.EASY,new Dimension(600,600));
            windowSizes.put(Difficulty.MEDIUM, new Dimension(950,900));
            windowSizes.put(Difficulty.HARD, new Dimension(1150,1100));
            windowSizes.put(Difficulty.HARDCORE, new Dimension(1200,1250));
            windowSizes.put(Difficulty.EXTREME, screenSize);
            windowSizes.put(Difficulty.LEGENDARY, screenSize);
        }
	    return windowSizes.get(difficulty);
    }

	private static final PrintStream ERR = System.err;

	/**
	 * disable error outputs from DBConnector if not able to connect to the database
	 */
	public static void disableErrorOutput() {
		System.setErr(new PrintStream(new OutputStream() {
			@Override
			public void write(int i) throws IOException {

			}
		}));
	}

	public static void enableErrorOutput() {
		System.setErr(ERR);
	}
}
