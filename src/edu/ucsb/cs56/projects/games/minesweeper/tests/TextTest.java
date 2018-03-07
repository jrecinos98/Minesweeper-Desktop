package edu.ucsb.cs56.projects.games.minesweeper.tests;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.ucsb.cs56.projects.games.minesweeper.constants.Constants;
import edu.ucsb.cs56.projects.games.minesweeper.gamelogic.Grid;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;


public class TextTest {
    //Output Streams
    private static ByteArrayOutputStream output;
    private static  PrintStream printer;

    //Input Stream
    private ByteArrayInputStream in;

    private String[] invalidInput= {"0","-1","foo", "TestsSuck", "100000", };

    @BeforeClass
    public static void initializeStreams(){
        //The output will be redirected to this.
        output = new ByteArrayOutputStream();
        printer = new PrintStream(output);
        //Conserve the previous output stream. (Might not be needed)
        //oldStream = System.out;
        System.setOut(printer);
    }
    @Test
    public void valid_input_mainMenu_test(){
        for(String i:invalidInput){
             in = new ByteArrayInputStream(i.getBytes());
             System.setIn(in);
             assertEquals("Please select a valid input\n", output.toString());
        }


    }
    //invalid menu input.
    //test if the console is cleared.
    //Test setupdifficulty.
    //Test getMoveResponse();
    //Good idea to refactor toString() in Grid.java
    //test handlemove
    //maybe test handleEndgame
    //maybe the leaderboard response
    //Find textMaxLengthWords
    //Test PrintTermwithSpaces
    //Test getIntinput.
    //Maybe refresh console.
}
