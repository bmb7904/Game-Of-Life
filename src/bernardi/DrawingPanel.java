/*
 * Programmer defined class
 * Extends JFrame
 * Takes an array in constructor. Goes through the array, and draws a colored 
 * rectangle with the specified number pixels if the element in array is alive 
 * (=1)
 * Also takes in the number of cells in each row and the number of pixels that 
 * will make up the drawn rectangle
 * Programmer: Brett Bernardi
 */
package bernardi;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class DrawingPanel extends JPanel
{
    // class variable
    public int[][] paintedGrid;
    // this holds the number of rectangles(cells) drawn per line(same verticle  
    // and hoizontal
    public int numOfCells;
    // stores the number of pixels in each side of the square cell
    public int pixelSizeofSquare;
    
    // construtor for DrawingPanel class
    // Has 3 arguments
    public DrawingPanel(int[][] incomingGrid1, int numberOfCells, int pixelsSideCell) {
        this.paintedGrid = incomingGrid1;
        this.numOfCells = numberOfCells;
        this.pixelSizeofSquare = pixelsSideCell;
    }
    
    
    public void paintComponent(Graphics myPen) 
    {
        // don't really know what this does yet. But it is needed!
        super.paintComponent(myPen);
        // creates Graphics 2D object in order to set stroke and make
        // thicker lines
        Graphics2D g2 = (Graphics2D) myPen;
        
        // the conditional in the for loops should calculate the exact number of
        // elements in the array. Thus use < because arrays start 0
        for(int x = 0; x < numOfCells * pixelSizeofSquare ; x = x + pixelSizeofSquare) 
        {
            for(int y = 0; y < numOfCells * pixelSizeofSquare; y = y + pixelSizeofSquare)
            {
                if(paintedGrid[x/pixelSizeofSquare][y/pixelSizeofSquare] == 0) 
                {
                    myPen.setColor(Color.black);
                }
                
                else 
                {
                    myPen.setColor(Color.green);
                }
                
                // we start drawing rectangles at 0,0 (upper right hand corner)
                // and process downwards doing it vertical row by vertical row
                myPen.fillRect(y, x, pixelSizeofSquare, pixelSizeofSquare);
            }
        }
        
        
        // sets the basic stroke to 2 to make drawn lines thicker
        g2.setStroke(new BasicStroke(2)); 
        // sets the g2 object as black
        g2.setColor(Color.BLACK);
        // draws lines in a grid pattern
        // this will creates the same number of cells as rectangles drawn
        
        for(int m = 0; m  <=numOfCells; m ++) 
        {
       
            g2.drawLine(m*pixelSizeofSquare, 0, m*pixelSizeofSquare, this.getHeight());

        }
        
        for(int n = 0; n  <=numOfCells; n ++) 
        {
       
            g2.drawLine(0, n*pixelSizeofSquare, this.getWidth(), n *pixelSizeofSquare);

        }
        
        
        
    }
}
