/**
 * This class contains the basic definitions of Sprite.
 * @author: @yash.diniz;
**/
package runner;

import java.awt.*;

class Sprite {
    private final Rectangle bounds;   //this object will hold the basic bounds(for collision detection)
    private final Image img;          //this object will hold the image of the sprite
    int spriteX;
    private int spriteY;

    Sprite(Image i, int x, int y, int width, int height) {   //this constructor takes the image for the sprite
        img = i;
        //the cartesian coordinates of the sprite on window.
        bounds = new Rectangle((spriteX=x), (spriteY=y), width, height);
    }
    //function to check and return sprite intersection
    boolean intersects(Sprite y) {
        return bounds.intersects(y.bounds);
    }
    //function draws the bounds of the sprite around
    void drawBounds(Graphics g) {
        g.setColor(Color.red);
        g.drawRect(bounds.x-1, bounds.y-1, bounds.width+1, bounds.height+1);
    }
    //function draws the sprite on screen
    void drawSprite(Graphics g) {
        g.drawImage(img, spriteX, spriteY, null);  //draws image of the sprite 
    }
    //function to update sprite movements
    void updateSprite(float val) {
        spriteX -= val;
        bounds.x-= val; //update bounds as well!
    }
    //function to update sprite positions
    void updateSprite(int spriteX) {
        this.spriteX = spriteX;
        bounds.x = spriteX;//update bounds as well!
    }
    //function to update sprite positions in 2D
    void updateSprite(int spriteX, int spriteY) {
        this.spriteX = spriteX;
        this.spriteY = spriteY;
        bounds.x = spriteX; //update bounds as well!
        bounds.y = spriteY;
    }
}