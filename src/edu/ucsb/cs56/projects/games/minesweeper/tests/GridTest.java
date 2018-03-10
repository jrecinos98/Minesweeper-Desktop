package edu.ucsb.cs56.projects.games.minesweeper.tests;

import org.junit.Test;

import edu.ucsb.cs56.projects.games.minesweeper.constants.Constants;
import edu.ucsb.cs56.projects.games.minesweeper.gamelogic.Grid;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import static org.junit.Assert.assertEquals;

/**
 * Test class for Grid
 * @author David Acevedo
 * @version 2015/03/04 for lab07, cs56, W15
 * @see Grid
 */

public class GridTest {

	//Test is mine Exception.
    //TestLoadGame

	/**
	 * Test case default constructor of the Grid class
	 * @see Grid#Grid()
	 */
	@Test
	public void test_Grid_Easy() {
		int count = 0;
		Grid test = new Grid();
		int s = test.getSize();
		for (int i = 0; i < s; i++) {
			for (int j = 0; j < s; j++) {
				if (test.getG()[i][j] == 'X') {
					count++;
				}
			}
		}
		assertEquals(10, count);
	}

	/**
	 * Test medium difficulty constructor of Grid class
	 * @see Grid#Grid(Constants.Difficulty)
	 */
	@Test
	public void test_Grid_Medium() {
		int count = 0;
		Grid test = new Grid(Constants.Difficulty.MEDIUM);
		int s = test.getSize();
		for (int i = 0; i < s; i++) {
			for (int j = 0; j < s; j++) {
				if (test.getG()[i][j] == 'X') {
					count++;
				}
			}
		}
		assertEquals(36, count);
	}


	/**
	 * Test hard difficulty constructor of Grid class
	 * @see Grid#Grid(Constants.Difficulty)
	 */
	@Test
	public void test_Grid_Hard() {
		int count = 0;
		Grid test = new Grid(Constants.Difficulty.HARD);
		int s = test.getSize();
		for (int i = 0; i < s; i++) {
			for (int j = 0; j < s; j++) {
				if (test.getG()[i][j]== 'X') {
					count++;
				}
			}
		}
		assertEquals(54, count);
	}
	/**
	 * Test hardcore difficulty constructor of Grid class
	 * @see Grid#Grid(Constants.Difficulty)
	 */
	@Test
	public void test_Grid_Hardcore() {
		int count = 0;
		Grid test = new Grid(Constants.Difficulty.HARDCORE);
		int s = test.getSize();
		for (int i = 0; i < s; i++) {
			for (int j = 0; j < s; j++) {
				if (test.getG()[i][j] == 'X') {
					count++;
				}
			}
		}
		assertEquals(100, count);
	}
	/**
	 * Test extreme difficulty constructor of Grid class
	 * @see Grid#Grid(Constants.Difficulty)
	 */
	@Test
	public void test_Grid_Extreme() {
		int count = 0;
		Grid test = new Grid(Constants.Difficulty.EXTREME);
		int s = test.getSize();
		for (int i = 0; i < s; i++) {
			for (int j = 0; j < s; j++) {
				if (test.getG()[i][j]== 'X') {
					count++;
				}
			}
		}
		assertEquals(127, count);
	}
	/**
	 * Test legendary difficulty constructor of Grid class
	 * @see Grid#Grid(Constants.Difficulty)
	 */
	@Test
	public void test_Grid_Legendary() {
		int count = 0;
		Grid test = new Grid(Constants.Difficulty.LEGENDARY);
		int s = test.getSize();
		for (int i = 0; i < s; i++) {
			for (int j = 0; j < s; j++) {
				if (test.getG()[i][j]== 'X') {
					count++;
				}
			}
		}
		assertEquals(190, count);
	}

	//Test is almost done. Needs so improvements.
/*
    /**
     * Test case for shuffleMine method in the Grid class

    @Test
    public void test_shuffleMine(){

    	Grid test;
        int counter =0;
        while(counter < 25){
        	//System.out.println(counter);
			Stack<Integer> oldMineNeighborsBeforeShuffle;
			Stack<Integer> oldMineNeighborsAfterShuffle;
			Stack<Integer> newNeighborsBeforeShuffle;
			Stack<Integer> newNeighborsAfterShuffle;
            test =  new Grid(Constants.Difficulty.EASY);
            Random r= new Random();
            int x= r.nextInt(test.getSize());
            int y= r.nextInt(test.getSize());
            int numOfOldNeighbors;
            int numOfNewNeighbors;
            if(test.getCellSymbol(x,y)=='X'){
				//get the old mine neighbors before shuffling
				System.out.println("oldMineBefore:");
				oldMineNeighborsBeforeShuffle = Grid.getSurrounding(x, y, test);

				//the total cells surrounding the mine before shuffle.
				numOfOldNeighbors = oldMineNeighborsBeforeShuffle.size();
				//Shuffles the mine to another location. Return the value of the new neighbors before a change occurred.
				newNeighborsBeforeShuffle = test.shuffleMine(x, y,test);
				System.out.println("oldMineAfter:");
				//Obtain the old neighbors as they have been modified.
				oldMineNeighborsAfterShuffle = Grid.getSurrounding(x, y, test);
				System.out.println("newMineAfter:");
				//the total cells surrounding the mine in its new location.
				numOfNewNeighbors=newNeighborsBeforeShuffle.size();

				//Contains new coordinates of mine.
				Dimension newMineLocation= test.getCorOfClickedMine();

				//Obtain the new modified neighbors
				newNeighborsAfterShuffle=Grid.getSurrounding((int)newMineLocation.getWidth(),(int)newMineLocation.getHeight(),test);


				//Check that the decrement was done correctly
                while(!oldMineNeighborsBeforeShuffle.empty()){
					//System.out.println("Before: "+oldMineNeighborsBeforeShuffle.get(numOfNewNeighbors-i));
					//System.out.println("After: "+ (oldMineNeighborsAfterShuffle.get(numOfNewNeighbors-i)+1));
                	assertEquals(oldMineNeighborsBeforeShuffle.pop().doubleValue(),oldMineNeighborsAfterShuffle.pop().doubleValue()+1.0,0);
				}
				//Check that the increment was done correctly.
				while(!newNeighborsAfterShuffle.empty()){
                	//System.out.println("Before: "+newNeighborsBeforeShuffle.get(numOfNewNeighbors-j));
                	//System.out.println("After: "+ (newNeighborsAfterShuffle.get(numOfNewNeighbors-j)-1));
                	assertEquals((double)newNeighborsBeforeShuffle.pop().doubleValue(),newNeighborsAfterShuffle.pop().doubleValue()-1.0,0);
				}
				//Lastly make sure that the cell where the mine was and the new location have been updated
                assertEquals(true,(test.getCellSymbol(x,y)!='X' && test.getCellSymbol((int)newMineLocation.getWidth(),(int)newMineLocation.getHeight())=='X'));

                //increment counter.
                counter++;
            }
        }
    }
    */

	/**
	 * Test case for isOpen method of the Grid class
	 * @see Grid#isOpen
	 */
	@Test
	public void test_isOpen1() {
		Grid g1 = new Grid();
		assertEquals(false, g1.isOpen(0, 1));
	}


	/**
	 * Test case for isOpen method of the Grid class
	 * @see Grid#isOpen(int, int)
	 */
	//Test for exception
	@Test
	public void test_isOpen2() {
		Grid g1 = new Grid();
		assertEquals(false, g1.isOpen(0, 1));
	}

	/**
	 * Test case for isFlag method of the Grid class
	 * @see Grid#isFlag(int, int)
	 */
	@Test
	public void test_isFlag1() {
		Grid g1 = new Grid();
		assertEquals(false, g1.isFlag(9, 9));
	}

	/**
	 * Test case for isFlag method of the Grid class
	 * @see Grid#isFlag(int, int)
	 */
	@Test
	public void test_isFlag2() {
		Grid g1 = new Grid();
		assertEquals(false, g1.isFlag(9, 9));
	}

	/**
	 * Test case for flagBox method of the Grid class
	 * @see Grid#flagBox(int, int)
	 */
	@Test
	public void test_flagBox() {
		boolean correct = false;
		Grid test = new Grid();
		test.flagBox(0, 6);
		test.flagBox(1, 2);
		test.flagBox(2, 7);
		if (test.isFlag(0, 6) && test.isFlag(1, 2) && test.isFlag(2, 7)) {
			correct = true;
		}
		assertEquals(true, correct);
	}

	/**
	 * Test case for deflagBox method of the Grid class
	 * @see Grid#deflagBox(int, int)
	 */
	@Test
	public void test_deflagBox() {
		boolean correct = false;
		Grid test = new Grid();
		test.flagBox(0, 6);
		test.flagBox(1, 2);
		test.flagBox(2, 7);
		test.deflagBox(0, 6);
		test.deflagBox(1, 2);
		test.deflagBox(2, 7);
		if (!test.isFlag(0, 6) && !test.isFlag(1, 2) && !test.isFlag(2, 7)) {
			correct = true;
		}
		assertEquals(true, correct);
	}

	/**
	 * Test case for gameStatus method of the Grid class
	 * @see Grid#getGameState()
	 */
	@Test
	public void test_getGameState() {
		Grid g1 = new Grid();
		assertEquals(Constants.GameState.PLAYING, g1.getGameState());
	}

    /**
     * Test that e
     */
}
