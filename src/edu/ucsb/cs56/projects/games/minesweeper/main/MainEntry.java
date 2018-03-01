package edu.ucsb.cs56.projects.games.minesweeper.main;
import edu.ucsb.cs56.projects.games.minesweeper.gui.MineGUI;
import edu.ucsb.cs56.projects.games.minesweeper.text.TextGame;

public class MainEntry{
    public static void main(String[] args){
        if (args.length==0){
            System.out.println("Usage: java -jar Minesweeper.java [OPTION]" +
                    "\nOPTIONS:" +
                    "\n     GUI : Executes GUI version of the game" +
                    "\n     Text : Executes the Text version of the game.");
        }
        else if ("GUI".equals(args[0])){
            MineGUI.main(args);
        }
        else if("Text".equals(args[0])){
            TextGame.main(args);
        }

    }
}