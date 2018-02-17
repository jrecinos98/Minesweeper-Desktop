

(a) This project is minesweeper. There are two available options to play the game. We have the GUI version and the command line version of the game. The main objective of the game is to mark all the mines throughout the grid and to uncover all other cells.

(b) As a user I can select the difficulty of the game, load a last game, get help, and check the leaderboards from the main menu. In the game I can click on a cell to unveil the tile and any other empty tile in its proximity. I can see the timer running and I can also select the help button to get instructions of how to play the game.

(c) The game at this point is fully playable and functions properly. Depending on what game mode we choose we can play through GUI or through terminal by typing in the instructions of what we want to do (row number and column number)

(d)As a user I would prefer if the game was more aesthetically pleasing such as smoother looking buttons and more engaging main menu. Furthermore, I would like it if instead of a plain number there was an image that resembles the more traditional minesweeper whenever a cell is clicked. As a user I would also prefer if the help section included actual images with more step-by-step instructions to understand how to play the game. Lastly it would be more fun if different game modes were available (as opposed to more difficulties).

(e) The README.md is very clear and descriptive. It contain all the information that a player would need to get the game started. To further improve the README.md we could add images and a tutorial of how to play the game.

(f)The build.xml file is built on Ant. It contains some obsolete attributes that could be removed without affecting its functionality. It seems to be working correctly to run the game, clean project, compile and generate a javadoc. Currently, there are two ant commands that create two different jars so this can be improved by combining into one jar.

(g)There are enough issues to achive the 1000 point goal. Al the issues are clear in what should be accomplished to earn the points.

(h)
https://github.com/ucsb-cs56-projects/cs56-games-minesweeper/issues/74
https://github.com/ucsb-cs56-projects/cs56-games-minesweeper/issues/76

(i) All the classes and file names refelect what their purpose is. There are no ambiguous variable names.It seems clear how all the classes work with one another. Overall the code is well structured and documented. All the frames are arranged in a separate directory for example. If were to give this project to someone else we would recommend for them to look through the subdirectories in src. The way they are organized and separated from one another makes it easy to get up to speed on what each file does and how it all work. Also i would reccomend reading through the README.md file as it contains detailed info about the project.

(j) There are plenty of JUnitTests. There are tests that verify that the grid is created correctly and test the GUI of the application. The command line game mode lacks any JUnit tests and these can be implemented.
