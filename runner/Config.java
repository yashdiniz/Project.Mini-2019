/*
 * This class holds all of the configurable data,
 * for easier modification of stuff.
*/
package runner;

import java.awt.event.*;
import java.awt.*;

public class Config {
    public static final String relativeRoot = "src/";
    
    public static final Font largeFont = new Font(relativeRoot + "res/VeraMono.ttf",Font.BOLD,20);
    public static final Font smallFont = new Font(relativeRoot + "res/VeraMono.ttf",Font.BOLD,14);
    
    //these constants hold the database connection and Table information
    public static final String scoreDB = "jdbc:mysql://localhost:3306/RunnerScoreDB";
    public static final String DBuser = "root";
    public static final String DBpass = "";
    public static final String scoreTable = "scores";
    public static final String nameField = "name";
    public static final String scoreField = "score";
    
    //this constant holds the name of the game...
    public static final String gameName = "Logic Maze Runner";
    //these constants hold the frame width and height
    public static final int frameWidth = 700, frameHeight = 270;
    //these constants holds the help message to be shown at bottom of game
    public static final String help = "PAUSED. Press A to throw water weapon, and F to throw fire weapon.";
    public static final int helpX = (Config.frameWidth/2)-250, helpY = 50;
    //these constants hold the details of the game over message
    public static final String gameOver = "GAME OVER. Press N for new game.";
    public static final int gameOverX = (Config.frameWidth/2)-250, gameOverY = 50;
    //these constants hold the details of the high score message
    public static final String scoreString = "HIGH: ";
    public static final int scoreX = 20, scoreY = Config.frameHeight-35;
    //these constants hold the details for the tiles
    public static final String tilesImagePath = relativeRoot + "res/tileslow.png";//../res/tiles.png";
    public static final String bkgImagePath = relativeRoot + "res/bkgnd.png";//"../res/bkgnd.png";
    
    public static final long randomSeed = 1234l;    //the seed of the randomiser
    
    //these constants will hold the tile X,Y,width and heights of various sprites
    public static final int playerX = 0, playerY = 0, playerW = 98, playerH = 150;    
    public static final int fireX = 105, fireY = 0, fireW = 110, fireH = 150;
    public static final int waterX = 216, waterY = 0, waterW = 110, waterH = 150;
    public static final int afireX = 330, afireY = 0, afireW = 38, afireH = 150;
    public static final int awaterX = 370, awaterY = 0, awaterW = 38, awaterH = 150;
    
    //these constants hold the basic frame delays and accelration...
    public static final float defaultAcceleration = 0.007f;
    public static final float defaultDelay = 16;
    public static final float easeOfGame = 1;
    public static final float diffOfGame = 15;
    
    //this constant holds the speed of enemy movement
    public static final int enemySpeed = 4;
    
    //this constant holds the speed of weapon movement
    public static final int weaponSpeed = -8;   //against the normal movement, hence negative
    
    //this variable holds the player position
    public static final int playerXpos = 20, playerYpos = 80;
    public static final int weaponXpos = (playerXpos + playerW), weaponYpos = (playerYpos);
    
    enum Weapon {fire, water};  //choosing the weapon of your choice
    
    public static final int waterKey = KeyEvent.VK_A;
    public static final int fireKey = KeyEvent.VK_F;
    public static final int newGameKey = KeyEvent.VK_N;
    public static final int pauseKey = KeyEvent.VK_SPACE;
    
    //these constants hold the reward points for specific actions
    public static final int killScore = 10;
    public static final int weaponWaste = -2;
    
    //these constants will hold paths to the audio files to be played during the game...
    public static final String gameOverSound = relativeRoot + "res/gameover.wav";
    public static final String gameStartSound = relativeRoot + "res/gamestart.wav";
    public static final String weaponYieldSound = relativeRoot + "res/WeaponYield.wav";
}