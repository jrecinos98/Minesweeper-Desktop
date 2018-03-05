package edu.ucsb.cs56.projects.games.minesweeper.frames;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.Dimension;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.Box;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;



import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


import java.awt.event.*;


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

    private Color lowOpGrey = new Color(192,192,192,160);
    private Color white= new Color(255,255,255);

    /**
	 * Default Constructor for main menu
	 * @throws HeadlessException if no display
	 */
	public MainMenu() throws HeadlessException {
		setTitle("MineSweeper");
		setSize(1000, 800);
		setResizable(false);
		music();
		//super();

		panel2 = new JPanel();
		panel2.setOpaque(false);
		BoxLayout boxLayout= new BoxLayout(panel2, BoxLayout.Y_AXIS);
		panel2.setLayout(boxLayout);
		panel2.setBorder(new EmptyBorder(new Insets(200,200,200,200)));



		ImageIcon icon = new ImageIcon();
		icon = getBackgroundImage("/images/background.png");
		lblBackgroundImage.setLayout(new FlowLayout());
		lblBackgroundImage.setIcon(icon);




		//add(new ContentPanel());
		
		setVisible(true);
		setLocation(200, 200);

		//Container menu = getContentPane();
		//menu.setLayout(new GridLayout(7, 0)); //our 2 section grid layout for our main menu
		load = new JButton("Load Last Game");
        load.setBackground(lowOpGrey);
        // load.setForeground(white);

		easyGame = new JButton("New Easy Game");
		easyGame.setBackground(lowOpGrey);

		medGame = new JButton("New Medium Game");
		medGame.setBackground(lowOpGrey);

		hardGame = new JButton("New Hard Game");
		hardGame.setBackground(lowOpGrey);

		highScore = new JButton("Leaderboards");
		highScore.setBackground(lowOpGrey);

		help = new JButton("Help");
		help.setBackground(lowOpGrey);

		quitMine = new JButton("Quit Minesweeper");
		quitMine.setBackground(lowOpGrey);


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





        panel2.add(easyGame);

		panel2.add(Box.createVerticalStrut(15));

        panel2.add(medGame);

        panel2.add(Box.createVerticalStrut(15));

        panel2.add(hardGame);

        panel2.add(Box.createVerticalStrut(15));

        panel2.add(load);

        panel2.add(Box.createVerticalStrut(15));

		panel2.add(highScore);

        panel2.add(Box.createVerticalStrut(15));

		panel2.add(help);

        panel2.add(Box.createVerticalStrut(15));

		panel2.add(quitMine);
	
		add(panel2);

		lblBackgroundImage.add(panel2);

		add(lblBackgroundImage);

	
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}




 /*
		AudioPlayer MGP = AudioPlayer.player;
		AudioStream BGM;
		AudioData MD;

		ContinuousAudioDataStream loop = null;

		try{
			InputStream test = new FileInputStream("/sounds/BackgroundMusic.wav");
			BGM = new AudioStream(test);
			AudioPlayer.player.start(BGM);
			MD = BGM.getData();
			loop = new ContinuousAudioDataStream(MD);
		}
		catch(FileNotFoundException e){
			System.out.print(e.toString());
		}
	*/
	

	public void playSound(String dir) {
	if (dir != null) {
	    try {
		File resource = new File("resources" + dir);
		AudioInputStream audioInputStream;
		if (resource.exists()) {
		  audioInputStream = AudioSystem.getAudioInputStream(resource.getAbsoluteFile());
		  } else {
		  audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource(dir));
		  }
		  Clip clip = AudioSystem.getClip();
		  clip.open(audioInputStream);
		  clip.loop(Clip.LOOP_CONTINUOUSLY);
	    }
	 
	    catch (UnsupportedAudioFileException|LineUnavailableException| IOException e) {
		e.printStackTrace();
	    }
	 
	}
    }
  public void music(){
    	String musicName = "/sounds/BackgroundMusic.wav";
    	//if(game.getApplicationState() == Constants.ApplicationState.MAINMENU){
    	playSound(musicName);
    	//}

}


    private ImageIcon getBackgroundImage(String dir){
        File local = new File("resources"+dir);
        ImageIcon icon;
        if (!local.exists()) {
            icon = new ImageIcon(getClass().getResource(dir));
        } else {
            icon = new ImageIcon(local.getPath());
        }
        Image img = icon.getImage();
        Image newimg = img.getScaledInstance(1000, 800,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        return new ImageIcon(newimg);
    }
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
