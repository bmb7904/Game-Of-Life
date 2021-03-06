/*
****MAKE SURE *** I REPEAT *** MAKE SURE THAT ROWS AND COLUMNS ARE THE SAME LENGTH
* IF THEY ARE DIFFERENT LENGTHS, THE ENTIRE PROGRAM WILL BE FUCKED. MAKE SURE THAT THE ORIGINAL GRID IS A SQUARE
* PROGRAMMER: BRETT BERNARDI
* Date Modified: February 23, 2019
* Version 3
 */
package bernardi;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GameOfLife {

    public static void main(String[] args) {
        // stores the number of pixels in each side of the square cell
        // should not go under 5 -- 10 seems to be optimal
        int pixelSizeCell = 10;
        // stores the number of cells in each vertical and horizontal line
        // they they are the same
        // This should not go above 80 -- otherwise it will not fit vertically
        // 80 seems to be optimal
        int lengthGrid = 80;
        // number of generations -- initalized to 1
        int generationNum = 1;
        // user input  -- sets the percentage or initial alive cells
        float percentAlive;
        // stores value for do-while loop - either 0 ot 1
        int continueValue;

        // Will be the JFrame where each generation is painted on
        JFrame mainWindow = new JFrame("Game Of Life");
        // sets to exit on close of main game window
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // mainWindow sets always on top;
        mainWindow.setAlwaysOnTop(true);
        
        // HEAVILY OPTIMIZED -- DO NOT CHANGE UNLESS YOU HAVE GOOD REASON TO !
        // the +15 and +40 were found through trial and error
        // You should change the window size through altering the lengthGrid and
        // pixelSizeCell variables ONLY,
        mainWindow.setSize((pixelSizeCell * lengthGrid) + 15,(pixelSizeCell * lengthGrid) + 40);
        
        // sets x,y coordinates of the main game window
        mainWindow.setLocation(0, 0);
        // creates JFrame object that all JOptionPane boxes are a subset of
        JFrame optionPane = new JFrame();
        // sets JFrame ofject and its children to alwasy be on top
        optionPane.setAlwaysOnTop(true);
        optionPane.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // sets x,y coordinates for optionPane jFrame object
        optionPane.setLocation(900,400);
        // sets the optionPane JFrame to visible in order to assign properties 
        // to its children the JOptionPane boxes
        // ...at least, it works and that's my current hypothesis of how it works
        optionPane.setVisible(true);


        
        // shows welcome message
        JOptionPane.showMessageDialog(optionPane, 
                "Welcome! This is the Game Of Life. You will be asked for the proportion of living cells"
                + " to start with. \nMake sure to enter a decimal between 0 - 1.\n" 
                + "Alive cells will be colored green and dead cells will be colored black.\n" 
                + "Visit https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life for more information",
                "WELCOME TO THE GAME OF LIFE!",
                1
        );
        
        // stores the probability of a cell being alive as String
        String percentAliveString = JOptionPane.showInputDialog(optionPane,
                "Enter a decimal (from 0 to 1)-- this is the probability of a cell being alive",
                "Proportion of cells alive",
                3
        );
        
        // converts user input of proportion alive from String to float
        percentAlive = Float.parseFloat(percentAliveString);
        

        // creates 1st generation grid with createGrid function and user inputted parameters.
        // stores this 1st grid as a varaible tempGrid
        // lengthGrid is the number of cells in each horizontal and vertical row
        // which are equal (it's a square)
        int tempGrid[][] = createGrid(lengthGrid, percentAlive);
        
        //creates new object from DrawingPanel class(entends JFrame)
        // Ths constructor takes the already generated array 
        DrawingPanel myPanel = new DrawingPanel(tempGrid,lengthGrid,pixelSizeCell);
        // adds myPanel to the JFrame mainWindow - this is the 1st generation
        mainWindow.add(myPanel);
        
        //sets the JFrame(mainWindow) title and generation Number
        mainWindow.setTitle("The Game Of Life -- Generation #" + generationNum);
        
        // sets the JFrame visible
        mainWindow.setVisible(true);

        // do loop which will execute at least once and subsquently as long as 
        // continue value == 0
        do {
            // updates tempGrid by passiing it itself through the nextGeneration function 
            
            // JOptionPane that prompts the user to keep going
            continueValue = JOptionPane.showConfirmDialog(optionPane, 
                    "Would you like to create another generaton?", 
                    "Keep going?", 
                    JOptionPane.YES_NO_OPTION,
                    3
            );
            
            // breaks out immediately if user clicks no. Fixes problem of having
            // 1 more generation than called for
            if (continueValue == 1) {
                break;
            }
            
            // updates generation Number
            generationNum ++;
            
            // updates tempGrid by passiing itself through the nextGeneration function 
            // this creates the new generation based on the current generation
            tempGrid = nextGeneration(tempGrid);

            //removes current generation drawn in JFrame
            mainWindow.remove(myPanel);
            // creates new DrawingPanel object with new generation array
            DrawingPanel myPanel2 = new DrawingPanel(tempGrid,lengthGrid,pixelSizeCell);
            //adds new drawn generation to JFrame
            mainWindow.add(myPanel2);
            //updates title
            mainWindow.setTitle("The Game Of Life -- Generation #" + generationNum);
            // set's it visible
            mainWindow.setVisible(true);
            
            

        } while (continueValue == 0);
        
        // shows ending message
        JOptionPane.showMessageDialog(optionPane,
                "Thanks for playing! \n"
                + "You created " + generationNum + " generations. \n" +
                "Programmer:  Brett Bernardi \n" 
                + "2019",
                "GOOD-BYE!",
                1
        );
        
        // exits out of program -- this is needed. Won't stop running otherwise
        System.exit(0);

    }// end main

    
    // This function will take the current generation array and apply the rules 
    // of the Game Of Life to it and create and return a new 2 dimensional array
    // as the next generation
    static int[][] nextGeneration(int[][] nextGrid) {
        // next generation grid that will be returned at end
        int future[][] = new int[nextGrid.length][nextGrid.length];

        // these 2 loops will examine each cell individually 
        for (int i = 0; i < nextGrid.length; i++) {
            for (int j = 0; j < nextGrid.length; j++) {
                int lifeSum = 0;

                // if any cell on the edge is considered
                if ((i == 0 || i == (nextGrid.length - 1)) || (j == 0 || j == (nextGrid.length - 1))) {
                    // then that cell is set to DEAD. -- this creates a lifeless
                    // barrier around the edge of the grid
                    future[i][j] = 0;
                } // end if statement
                else {
                    for (int m = -1; m <= 1; m++) {
                        for (int n = -1; n <= 1; n++) {
                            lifeSum += nextGrid[i + m][j + n];
                        }
                    }

                    // subtracts value of current cell so as to not affect lifeSum
                    // because it was counted above
                    lifeSum = lifeSum - nextGrid[i][j];

                    // if the cell is dead
                    if (nextGrid[i][j] == 0) {
                        // becomes alive with 3 alive neighbors
                        if (lifeSum == 3) {
                            future[i][j] = 1;
                        } else // stays dead
                        {
                            future[i][j] = 0;
                        }

                    } // if the cell is alive
                    else {
                        // dies of lonliness
                        if (lifeSum <= 1) {
                            future[i][j] = 0;
                        } //stays alive -- can only have 2 or 3 alive neighbors
                        else if (lifeSum < 4) {
                            future[i][j] = 1;
                        } // dies of overpopulation -- has 4 or more neighbors
                        else {
                            future[i][j] = 0;
                        }
                    }

                }// end else

            }// end second outer loop - j
        }// end most outer loop -- i

        // retuns new grid
        return future;
    } // ends nextGeneration method

    // takes a two-dimensional array as a parameter (incomingGrid) and prints 
    // out the array in a square pattern with "*" = alive and "-" = dead.
    // no return aka void
    // This is not used in this version (v3) -- not needed
    // commented out for now
/*    static void printGrid(int incomingGrid[][]) {
        System.out.println();
        for (int p = 0; p < incomingGrid.length; p++) {
            for (int q = 0; q < incomingGrid.length; q++) {
                if (incomingGrid[p][q] == 0) {
                    System.out.print("-");
                } else if (incomingGrid[p][q] == 1) {
                    System.out.print("*");
                }
            } // end inner loop

            System.out.println();

        } // end outer loop

        // some blank lines
        System.out.println();
        System.out.println();
        System.out.println();

    } // ends printGrid free function */

    // creates initial grid using the parameters sent to it
    // these are the number of cells in each vertical and horizontal line
    // and the proportion of cells alive 
    // this creatues only the 1st generation array grid
    static int[][] createGrid(int sideLength, float percentAlive) {
        // creates new grid and allocates memory by setting size of grid 
        int grid[][] = new int[sideLength][sideLength];

        for (int m = 0; m < sideLength; m++) {
            for (int n = 0; n < sideLength; n++) {
                if (m == 0 || m == (sideLength - 1) || n == 0 || n == (sideLength - 1)) {
                    grid[m][n] = 0;
                } else {
                    if (Math.random() < percentAlive) {
                        grid[m][n] = 1;
                    } else {
                        grid[m][n] = 0;
                    }
                }// end else
            }// end inner loop
        }// end outer loop

        // returns the generated grid
        return grid;

    }// end createGrid free function

} // end class JavaApplication19
