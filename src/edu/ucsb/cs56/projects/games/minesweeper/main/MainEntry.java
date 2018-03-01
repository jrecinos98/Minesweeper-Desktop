package edu.ucsb.cs56.projects.games.minesweeper.main;
import edu.ucsb.cs56.projects.games.minesweeper.gui.MineGUI;
import edu.ucsb.cs56.projects.games.minesweeper.text.TextGame;

/**
 * It allows us to execute either version of the game (Text or GUI) through the generated jar file
 * by specifying an option.
 * MainEntry is assigned as Main-Class in the MANIFEST.MF file by the ant target 'jar'.
 *
 * @author Jose Recinos.
 */
public class MainEntry{
    /**
     * Takes a game mode from command line argument and executes the specified version of the game.
     * @param args. Two valid options: GUI or Text.
     */
    public static void main(String[] args){
        String usage="Usage: java -jar Minesweeper.java [GAMEMODE]" +
                "\nGAMEMODE:" +
                "\n     GUI : Executes GUI version of the game" +
                "\n     Text : Executes the terminal version of the game.";
        if (args.length==0){
            System.out.println("Missing GAMEMODE.\n"+usage);
        }
        else if ("GUI".equals(args[0])){
            MineGUI.main(args);
        }
        else if("Text".equals(args[0])){
            TextGame.main(args);
        }
        else{
            System.out.println(usage);
        }

    }
}