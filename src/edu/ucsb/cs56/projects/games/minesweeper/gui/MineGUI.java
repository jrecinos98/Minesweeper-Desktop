package edu.ucsb.cs56.projects.games.minesweeper.gui;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import edu.ucsb.cs56.projects.games.minesweeper.constants.Constants;
import edu.ucsb.cs56.projects.games.minesweeper.database.DBConnector;
import edu.ucsb.cs56.projects.games.minesweeper.frames.GameFrame;
import edu.ucsb.cs56.projects.games.minesweeper.frames.HelpScreen;
import edu.ucsb.cs56.projects.games.minesweeper.frames.LeaderboardFrame;
import edu.ucsb.cs56.projects.games.minesweeper.frames.MainMenu;
import edu.ucsb.cs56.projects.games.minesweeper.gamelogic.Grid;

/**
 * MineGUI.java is a base that calls all GUI objects and handles tasks
 * such as pausing the game, creating the GUI, making the escape key functional,
 * and allowing for a new game.
 *
 * @author David Acevedo
 * @version 2015/03/04 for lab07, cs56, W15
 *
 * @author Ryan Wiener
 */

public class MineGUI {

	private static MainMenu mainMenu;
	private static GameFrame gameFrame;
	private static HelpScreen helpScreen;
	private static LeaderboardFrame leaderboardFrame;

	/**
	 * main function called upon start up
	 * @param args args passed into program call
	 */
	public static void main (String[] args) {
	    DBConnector.init();
	    mainMenu = new MainMenu();
	    helpScreen = new HelpScreen();
	    leaderboardFrame = new LeaderboardFrame();
	}

	/**
	 * creates a new game and displays the game frame
	 * @param difficulty the difficulty of the game to be played
	 */
	public static void newGame(Constants.Difficulty difficulty) {
		if (gameFrame != null) {
			gameFrame.dispose();
		}
		try {
			gameFrame = new GameFrame(difficulty);
			mainMenu.setVisible(false);
			leaderboardFrame.setVisible(false);
		} catch (IOException | ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "There is no previous game to load", "No previous game", JOptionPane.DEFAULT_OPTION);
		}
	}

	/**
	 * return to main menu from either the help screen or game frame
	 */
	public static void goToMainMenu() {
		if (gameFrame != null) {
			gameFrame.dispose();
			gameFrame = null;
		}
		leaderboardFrame.setVisible(false);
		helpScreen.setVisible(false);
		mainMenu.setVisible(true);
	}

	/**
	 * set visibility of help screen
	 * @param visible boolean indicating whether help screen should be visible or not
	 */
	public static void setHelpScreenVisible(boolean visible) {
		helpScreen.setVisible(visible);
	}

	/**
	 * set visibility of leaderboard screen
	 * @param visible boolean indicating whether help screen should be visible or not
	 */
	public static void setLeaderboardVisible(boolean visible) {
		leaderboardFrame.refresh();
		leaderboardFrame.setVisible(visible);
	}

	/**
	 * Display prompt asking user to confirm that they want to quit
	 */
	public static void quitPrompt() {
		JFrame currFrame = getCurrentFrame();
		int response = JOptionPane.showConfirmDialog(currFrame, "Are you sure you want to quit the game?", "Quit?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (response == JOptionPane.YES_OPTION) {
			if (currFrame instanceof GameFrame) {
				((GameFrame) currFrame).getGrid().save();
			}
			System.out.println("Closing...");
			System.exit(0);
		} else {
		}
	}

	/**
	 * Display prompt asking user whether they want to overwrite their previous game with a new one
	 * @return boolean indicating true if user wants to overwrite and false otherwise
	 */
	public static boolean overwriteSavePrompt() {
		JFrame currFrame = getCurrentFrame();
		if(!Grid.saveExist()){
		    return true;
        }
		int response = JOptionPane.showConfirmDialog(currFrame, "Are you sure you want to do this? This will delete previous save data", "Overwriting Save", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (response == JOptionPane.YES_OPTION) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * get current frame that the user is looking at
	 * @return the current fram that the user has open (if help screen is open it will always be returned even if not selected
	 */
	public static JFrame getCurrentFrame() {
		if (helpScreen.isVisible()) {
			return helpScreen;
		} else if (leaderboardFrame.isVisible()) {
			return leaderboardFrame;
		} else if (gameFrame != null && gameFrame.isVisible()) {
			return gameFrame;
		} else {
			return mainMenu;
		}
	}
}
