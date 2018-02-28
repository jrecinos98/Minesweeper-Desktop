package edu.ucsb.cs56.projects.games.minesweeper.frames;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;



import java.awt.Container;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
//import java.awt.image.*;
import javax.swing.*;
import javax.imageio.ImageIO;
/*
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel; //*
import javax.swing.JLabel;
import javax.swing.ImageIo;
*/

import edu.ucsb.cs56.projects.games.minesweeper.constants.Constants;
import edu.ucsb.cs56.projects.games.minesweeper.gui.MineGUI;

/**
 * Main Menu JFrame
 * @author Ryan Wiener
 * @author Kate Perkins
 */



public class MainMenu extends JFrame {
	private JButton quitMine;
	private JButton easyGame;
	private JButton medGame;
	private JButton hardGame;
	private JButton load; //loads game
	private JButton help;    //Main Menu Help Button
	private JButton highScore; // this label status displays the local high score.
	
	private JPanel panel2;
	private JLabel lblBackgroundImage = new JLabel();
	/**
	 * Default Constructor for main menu
	 * @throws HeadlessException if no display
	 */
	public MainMenu() throws HeadlessException {
		setTitle("MineSweeper");
		setSize(1000, 800);
		//super();

		panel2 = new JPanel();
		panel2.setOpaque(false);
		panel2.setLayout(new FlowLayout());

		


		ImageIcon imageIcon = new ImageIcon("screenshots/MINESWEEPERMenuScreen.png"); // load the image to a imageIcon
		Image image = imageIcon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(1000, 800,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageIcon = new ImageIcon(newimg); 
		lblBackgroundImage.setLayout(new FlowLayout());
		lblBackgroundImage.setIcon(imageIcon);






		//add(new ContentPanel());
		
		setVisible(true);
		setLocation(200, 200);
		//Container menu = getContentPane();
		//menu.setLayout(new GridLayout(7, 0)); //our 2 section grid layout for our main menu
		quitMine = new JButton("Quit Minesweeper");
		easyGame = new JButton("New Easy Game");
		medGame = new JButton("New Medium Game");
		hardGame = new JButton("New Hard Game");
		help = new JButton("Help");
		load = new JButton("Load Last Game");
		highScore = new JButton("Leaderboards");


		easyGame.addActionListener((ActionEvent e) -> {
			if (MineGUI.overwriteSavePrompt()) {
				MineGUI.newGame(Constants.Difficulty.EASY);
			}
		});
		medGame.addActionListener((ActionEvent e) -> {
			if (MineGUI.overwriteSavePrompt()) {
				MineGUI.newGame(Constants.Difficulty.MEDIUM);
			}
		});
		hardGame.addActionListener((ActionEvent e) -> {
			if (MineGUI.overwriteSavePrompt()) {
				MineGUI.newGame(Constants.Difficulty.HARD);
			}
		});
		help.addActionListener((ActionEvent e) -> { MineGUI.setHelpScreenVisible(true); });
		load.addActionListener((ActionEvent e) -> { MineGUI.newGame(Constants.Difficulty.LOAD); });
		quitMine.addActionListener((ActionEvent e) -> { MineGUI.quitPrompt(); });
		highScore.addActionListener((ActionEvent e) -> {MineGUI.setLeaderboardVisible(true); });
		




		panel2.add(quitMine);
	
		panel2.add(easyGame);
		
		panel2.add(medGame);
		
		panel2.add(hardGame);
	
		panel2.add(load);
	
		panel2.add(help);
	
		panel2.add(highScore);
	
		add(panel2);

		lblBackgroundImage.add(panel2);

		add(lblBackgroundImage);

		//menu.add(panel);

		//ImageIcon icon= new ImageIcon("screenshots/MINESWEEPERMenuScreen.png");
		//JLabel backgroundPhoto= new JLabel();
		//backgroundPhoto.setIcon(icon);

		
		//JLabel contentPane = new JLabel();
		//contentPane.setIcon(new ImageIo("screenshots/MINESWEEPERMenuScreen.png"));
		//contentPane.setLayout(new BorderLayout());
		//menu.setContentPane(contentPane);
/*
		menu.add(easyGame);
		menu.add(medGame);
		menu.add(hardGame);
		menu.add(load);
		menu.add(help);
		menu.add(quitMine);
		menu.add(highScore); // add new highScore feature to frame.
		*/
		setVisible(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}


/*
	private Image getScaledImage(Image srcImg, int w, int h){
    	BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    	Graphics2D g2 = resizedImg.createGraphics();

    	g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    	g2.drawImage(srcImg, 0, 0, w, h, null);
    	g2.dispose();

    	return resizedImg;
	}

*/



	public int getEasyGameX() {
		return easyGame.getX();
	}

	public int getEasyGameY() {
		return easyGame.getY();
	}

	public int getMedGameX() {
		return medGame.getX();
	}

	public int getMedGameY() {
		return medGame.getY();
	}

	public int getHardGameX() {
		return hardGame.getX();
	}

	public int getHardGameY() {
		return hardGame.getY();
	}

	public int getLoadGameX() {
		return load.getX();
	}

	public int getLoadGameY() {
		return load.getY();
	}

	public int getHelpX() {
		return help.getX();
	}

	public int getHelpY() {
		return help.getY();
	}

	public int getLeaderBoardX() { return highScore.getX(); }

	public int getLeaderBoardY() { return highScore.getY(); }

}
