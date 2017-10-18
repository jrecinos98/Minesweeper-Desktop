package edu.ucsb.cs56.projects.games.minesweeper;
import java.awt.GridLayout;
import javax.swing.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event. ActionEvent;
import java.awt.Font;
import java.awt.Color;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.awt.Component;
import javax.swing.plaf.basic.BasicComboPopup;

/**
 * An Swing component for playing MineSweeper

   @author Daniel Reta
   @author David Acevedo
   @author Caleb Nelson
   @author Alex Thielk
   @version 2016/02/019 for lab07, cs56, W16
   @see Grid
 */
public class MineComponent extends JComponent 
{
    private Grid game;
    private int size;
    private int status; //allows the MineGUI class to know when the game is done
	private MineGUI start;
    private Color zero;
    private Color number;
    private JButton[][] buttons;

    /** Constructor
	
	@param game an object that implements the Inferface interface to keep track
	            of the moves in each game, ensuring the rules are followed and detecting
	            when someone has won.
	@param md an object that implements the MessageDestination interface.  This is just
	           a place to send any messages that need to be communicated to the user.
		   Making this separate allows a user of this components to decide to
		   send those messages to the console, or to a variety of different
		   swing Widgets, or even to a web page, as needed.
	@param start a MineGUI object so we can use the MineComponent class to modify the 
			GUI on the JFrame
    */
       
    public MineComponent(Grid game, MineGUI start) {
		super(); // is this line necessary?  what does it do?
		this.start = start;
		status = 0;
		zero = new Color (0, 0, 128);
		number = new Color (0, 100, 0);
		this.game = game;  // the Interface game
		this.size = game.getSize();
		buttons = new JButton[size][size];

		// note columns ignored when rows are set
		// number of columns is implicit from the number of things added

		this.setLayout(new GridLayout(this.size ,0));
		for (int i = 0; i < this.size; i++) {
		    for (int j = 0; j < this.size; j++) {
				String label=String.format("%d",i * this.size + j);
				JButton jb = new JButton(label);
				buttons[i][j] = jb;
				jb.addMouseListener(new ButtonListener(i * this.size + j));
				jb.setFont(new Font("sansserif",Font.BOLD,10));
				jb.setText("");
				jb.addComponentListener(new sizeListener());
				this.add(jb);
		    }
		}
    }
    /**
     * inner class, reponds to resizing of component to resize font
     *
     */
    class sizeListener implements ComponentListener{

		@Override
		public void componentHidden(ComponentEvent e) {
		}

		@Override
		public void componentMoved(ComponentEvent e) {
		}

		@Override
		public void componentResized(ComponentEvent e) {
			int size = e.getComponent().getSize().height / 2;
			if (e.getComponent().getSize().height / 2 > e.getComponent().getSize().width / 4) {
			    size = e.getComponent().getSize().width / 4;
			}
			e.getComponent().setFont(new Font("sansserif", Font.BOLD, size));
		}

		@Override
		public void componentShown(ComponentEvent e) {
			// TODO Auto-generated method stub
		}
    }
    /**
     * Inner Class, responds to the event source.
    */

    class ButtonListener extends MouseAdapter{
		private int num;

		public ButtonListener(int i) {
		    super();
		    this.num = i;
		}

		/**
		   Places player's symbol on button, checks for a winner or tie

		   @param event when a button is clicked

		 */

		//status = 0;

		public void mouseReleased(MouseEvent event) {
			Clip clip;
			String soundName;
			AudioInputStream audioInputStream;
		    if (game.gameStatus(status) == 0) {
				//if you left click and the button is available (not a flag and not already opened)
		    	if(event.getButton() == MouseEvent.BUTTON1 && !game.isFlag(num) && !game.isOpen(num)){
		    		char box = game.searchBox(num);
		    		if (box == 'X') {
		    			soundName = "resources/sounds/explosion.wav";
		    		} else {
		    			soundName = "resources/sounds/clicked.wav";
		    		}
    	            playSound(soundName);
    	            refresh();
		    		status = game.gameStatus(status);
					if (status == -1) {
						// TODO: display mines
						exposeMines();
						start.stopTimer();
						int response = JOptionPane.showOptionDialog(null, "You lose! Press 'Reset Game' to start a new game.", "Defeat!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Main Menu", "Reset Game"}, "default");
						if (response == JOptionPane.YES_OPTION) {
							start.saveHighest(start.User, start.globalTE, start.mc.getGrid().getSize());
							start.goToMainMenu();
						} else {
							start.saveHighest(start.User, start.globalTE, start.mc.getGrid().getSize());
							start.resetGame();
						}
					} else if (status == 1) {
						soundName= "resources/sounds/win.wav";
						playSound(soundName);
						start.stopTimer();
						int response = JOptionPane.showOptionDialog(null, "You win! Press 'Reset Game' to start a new game.", "Victory!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Main Menu", "Reset Game"}, "default");
						if (response == JOptionPane.YES_OPTION) {
    	                    start.saveHighest(start.User,start.globalTE,start.mc.getGrid().getSize());
							start.goToMainMenu();
						} else if (response == JOptionPane.INFORMATION_MESSAGE) {
    	                    start.saveHighest(start.User,start.globalTE,start.mc.getGrid().getSize());
							start.resetGame();
						} else {
    	                    //do nothing
    	                }
					}
		    	} else if (event.getButton() == MouseEvent.BUTTON1 && (game.isFlag(num) | game.isOpen(num))) {
					// If you left click and the button is a flag or has been opened
    	            game.searchBox(num);
    	            soundName = "resources/sounds/userError.wav";
    	            playSound(soundName);

    	        } else if (event.getButton() == MouseEvent.BUTTON3) {
					// If you right click
		    		if (game.isFlag(num)) {
						game.deflagBox(num);
		    			JButton jb = buttons[num/size][num%size];
		    			//jb.setFont(new Font("sansserif",Font.BOLD,12));
		    			jb.setForeground(Color.BLACK);
		    			jb.setText("");
		    		} else if (!game.isOpen(num)) {
    	                soundName = "resources/sounds/place_flag.wav";
    	                playSound(soundName);
		    			game.flagBox(num);
		    			JButton jb = buttons[num/size][num%size];
		    			jb.setFont(new Font("sansserif",Font.BOLD,15));
		    			jb.setForeground(Color.RED);
		    			jb.setText("F");
		    		} else {
    	                game.flagBox(num);
    	                soundName = "resources/sounds/userError.wav";
    	                playSound(soundName);
    	            }
		    		int status = game.gameStatus(0);
		    		if (status == 1) {
						soundName= "resources/sounds/win.wav";
						playSound(soundName);
						exposeMines();
						start.stopTimer();
						int response = JOptionPane.showOptionDialog(null, "You win! Press 'Reset Game' to start a new game.", "Victory!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Main Menu", "Reset Game"}, "default");
						if (response == JOptionPane.YES_OPTION) {
							start.saveHighest(start.User,start.globalTE,start.mc.getGrid().getSize());
							start.goToMainMenu();
						} else if (response == JOptionPane.INFORMATION_MESSAGE) {
                    	    start.saveHighest(start.User,start.globalTE,start.mc.getGrid().getSize());
							start.resetGame();
						} else {
                    	    // do nothing
                    	}
	    			}
	    		} else if (event.getButton() == MouseEvent.BUTTON1 && game.isOpen(num)){
					soundName = "resources/sounds/userError.wav";
                	playSound(soundName);
            	}
	    	}
		}
	}

    public void playSound(String dir){
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(dir).getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
	}

    public void refresh() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				JButton jb = buttons[i][j];
				if (game.getCell(i * size + j) != '?') {
					int fontSize = jb.getSize().height / 2;
					if (jb.getSize().height / 2>jb.getSize().width / 4) {
					    fontSize = jb.getSize().width / 4;
					}
					jb.setFont(new Font("sansserif", Font.BOLD, fontSize));
					if (game.getCell(i * size + j) == 48) {
						jb.setForeground(zero);
					} else if (game.getCell(i * size + j) == 70) {
						jb.setForeground(Color.RED);
					} else if (game.getCell(i * size + j) == 88) {
						jb.setForeground(Color.BLACK);
					} else {
						jb.setForeground(number);
					}
					jb.setText(Character.toString(game.getCell(i*size+j)));
				}
			}
		}
    }

	void exposeMines() {
		Component[] listOfButtons = start.mc.getComponents();
		for (int i = 0; i < listOfButtons.length; i++) {
			if (game.isMine(i)) {
				JButton mineButton = (JButton) listOfButtons[i];
				mineButton.setFont(new Font("sansserif", Font.BOLD, 10));
				mineButton.setText("X");
			}
		}
		start.mc.revalidate();
		start.mc.repaint();
	}

    int getStatus(){
		return status;
	}

	Grid getGrid(){
		return game;
	}
}
