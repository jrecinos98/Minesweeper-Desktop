package edu.ucsb.cs56.projects.games.minesweeper.frames;

import java.awt.Color;

import java.awt.Image;

import java.awt.FlowLayout;
import java.awt.Insets;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;


import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.Box;

import java.io.File;
import java.io.IOException;


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
	private JButton hardcoreGame;
	private JButton extremeGame;
	private JButton legendaryGame;
	private JButton load; //loads game
	private JButton help;    //Main Menu Help Button
	private JButton highScore; // this label status displays the local high score.
    private Color lowOpGrey = new Color(192,192,192,160);

    /**
	 * Default Constructor for main menu
	 * @throws HeadlessException if no display
	 */
	public MainMenu() throws HeadlessException {
		setTitle("MineSweeper");
		setSize(1000, 800);
		setResizable(false);

        JPanel panel2 = new JPanel();
		panel2.setOpaque(false);
		BoxLayout boxLayout= new BoxLayout(panel2, BoxLayout.Y_AXIS);
		panel2.setLayout(boxLayout);
		panel2.setBorder(new EmptyBorder(new Insets(200,200,200,200)));



		ImageIcon icon = getBackgroundImage("/images/background.png");
        JLabel lblBackgroundImage = new JLabel();
        lblBackgroundImage.setLayout(new FlowLayout());
		lblBackgroundImage.setIcon(icon);
        playSound("/sounds/BackgroundMusic.au");





		load = new JButton("Load Last Game");
        load.setBackground(lowOpGrey);


		easyGame = new JButton("New Easy Game");
		easyGame.setBackground(lowOpGrey);

		medGame = new JButton("New Medium Game");
		medGame.setBackground(lowOpGrey);

		hardGame = new JButton("New Hard Game");
		hardGame.setBackground(lowOpGrey);

		hardcoreGame = new JButton("New Hardcore Game");
		hardcoreGame.setBackground(lowOpGrey);

		extremeGame = new JButton("New Extreme Game");
		extremeGame.setBackground(lowOpGrey);

		legendaryGame = new JButton("New Legendary Game");
		legendaryGame.setBackground(lowOpGrey);

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
		hardcoreGame.addActionListener((ActionEvent e) -> {
			if (MineGUI.overwriteSavePrompt()) {
				MineGUI.newGame(Constants.Difficulty.HARDCORE);
			}
		});
		extremeGame.addActionListener((ActionEvent e) -> {
			if (MineGUI.overwriteSavePrompt()) {
				MineGUI.newGame(Constants.Difficulty.EXTREME);
			}
		});
		legendaryGame.addActionListener((ActionEvent e) -> {
			if (MineGUI.overwriteSavePrompt()) {
				MineGUI.newGame(Constants.Difficulty.LEGENDARY);
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

        panel2.add(hardcoreGame);

        panel2.add(Box.createVerticalStrut(15));

        panel2.add(extremeGame);

        panel2.add(Box.createVerticalStrut(15));

        panel2.add(legendaryGame);

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

        MineGUI.centerWindow(this);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void playSound(String dir) {
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
		  FloatControl gainControl= (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
          gainControl.setValue(-5.0f);
		  clip.loop(Clip.LOOP_CONTINUOUSLY);
		  audioInputStream.close();
	    }
	 
	    catch (UnsupportedAudioFileException|LineUnavailableException| IOException e) {
		e.printStackTrace();
	    }
	 
	    }
    }

    /**
     * Loads background image.
     * @param dir path of the image
     * @return IMage Icon containing the directory path.
     */
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

	public int getHardcoreGameX() {
		return hardcoreGame.getX();
	}

	public int getHardcoreGameY() {
		return hardcoreGame.getY();
	}

	public int getExtremeGameX() {
		return extremeGame.getX();
	}

	public int getExtremeGameY() {
		return extremeGame.getY();
	}

	public int getLegendaryGameX() {
		return legendaryGame.getX();
	}

	public int getLegendaryGameY() {
		return legendaryGame.getY();
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
