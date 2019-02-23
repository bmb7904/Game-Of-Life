/*
 * Programmer defined class
 * Extends JFrame
 * Takes an array as parameter. Goes through the array, and draws a colored 
 * rectangle 20 x 20 pixels if the element in array is alive (=1)
 * Programmer: Brett Bernardi
 */
package gameoflifejoption;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class DrawingPanel extends JPanel
{
    // class variable
    public int[][] paintedGrid;
    
    // construtor for DrawingPanel class
    public DrawingPanel(int[][] incomingGrid1) {
        this.paintedGrid = incomingGrid1;
    }
    
    
    public void paintComponent(Graphics myPen) 
    {
        super.paintComponent(myPen);
        // will set the dimensions of the square in pixels
        int pixelSizeofSquare = 10;
        
        // creates Graphics 2D object in order to set stroke and make
        // thicker lines
        Graphics2D g2 = (Graphics2D) myPen;
        
        // I had to hardcode the length and width of the JFrame due to it not
        // working otherwise
        for(int x = 0; x < 600; x = x + pixelSizeofSquare) 
        {
            for(int y = 0; y < 600; y = y + pixelSizeofSquare)
            {
                if(paintedGrid[x/pixelSizeofSquare][y/pixelSizeofSquare] == 0) 
                {
                    myPen.setColor(Color.BLACK);
                }
                
                else 
                {
                    myPen.setColor(Color.GREEN);
                }
                
                myPen.fillRect(y, x, pixelSizeofSquare, pixelSizeofSquare);
            }
        }
        
        
        // sets the basic stroke to 2 to make drawn lines thicker
        g2.setStroke(new BasicStroke(2)); 
        // sets the g2 object as black
        g2.setColor(Color.BLACK);
        // draws lines in a grid pattern
        // this will creates the same number of cells as rectangles drawn
        
        for(int m = 0; m  <=60; m ++) 
        {
       
            g2.drawLine(m*pixelSizeofSquare, 0, m*pixelSizeofSquare, this.getHeight());

        }
        
        for(int n = 0; n  <=60; n ++) 
        {
       
            g2.drawLine(0, n*pixelSizeofSquare, this.getWidth(), n *pixelSizeofSquare);

        }
        
        
        
    }
}
