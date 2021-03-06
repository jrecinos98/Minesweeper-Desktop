package edu.ucsb.cs56.projects.games.minesweeper.main;
import edu.ucsb.cs56.projects.games.minesweeper.gui.MineGUI;
import edu.ucsb.cs56.projects.games.minesweeper.text.TextGame;

/**
 * It allows us to execute either version of the game (Text or GUI) through the generated jar file
 * by specifying an option.
 * MainEntry is assigned as Main-Class in the MANIFEST.MF file by the ant target 'jar'.
 *
 * @author Jose Recinos, Winter 2018
 */
public class MainEntry{
    /**
     * Takes a game mode from command line argument and executes the specified version of the game.
     * @param args Either of two valid options: GUI or Text.
     */
    //A jar can only have one Main-Class in its MANIFEST file. Which means only one version of the game can be executed when the jar is executed.
    //To remedy this we have a main function that determines based on GAMEMODE passed which main function to run.
    public static void main(String[] args){
        String usage="Usage: java -jar Minesweeper.java [GAME MODE]" +
                "\nGAME MODE:" +
                "\n     GUI : Executes GUI version of the game" +
                "\n     Text : Executes the terminal version of the game.";
        if (args.length==0){
            System.err.println("Missing GAMEMODE.\n"+usage);
            System.exit(1);
        }
        else if ("GUI".equals(args[0])){
            MineGUI.main(args);
        }
        else if("Text".equals(args[0])){
            TextGame.main(args);
        }
        else{
            System.out.println(usage);
            System.exit(1);
        }

    }
}