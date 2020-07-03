/*
 * This class holds all of the configurable data,
 * for easier modification of stuff.
*/
package runner;

import java.awt.event.*;
import java.awt.*;

class Config {
    private static final String relativeRoot = "";
    
    static final Font largeFont = new Font(relativeRoot + "res/VeraMono.ttf",Font.BOLD,20);
    static final Font smallFont = new Font(relativeRoot + "res/VeraMono.ttf",Font.BOLD,14);
    
    //these constants hold the database connection and Table information
    static final String scoreDB = "jdbc:mysql://localhost:3306/RunnerScoreDB";
    static final String DBuser = "root";
    static final String DBpass = "";
    static final String scoreTable = "scores";
    static final String nameField = "name";
    static final String scoreField = "score";
    
    //this constant holds the name of the game...
    static final String gameName = "Logic Maze Runner";
    //these constants hold the frame width and height
    static final int frameWidth = 700, frameHeight = 270;
    //these constants holds the help message to be shown at bottom of game
    static final String help = "PAUSED. Press A to throw water weapon, and F to throw fire weapon.";
    static final int helpX = (Config.frameWidth/2)-250, helpY = 50;
    //these constants hold the details of the game over message
    static final String gameOver = "GAME OVER. Press N for new game.";
    static final int gameOverX = (Config.frameWidth/2)-175, gameOverY = 50;
    //these constants hold the details of the high score message
    static final String scoreString = "HIGH: ";
    static final int scoreX = 10, scoreY = 20;
    //these constants hold the details for the tiles
    static final String tilesImagePath = relativeRoot + "res/tileslow.png";//../res/tiles.png";
    static final String bkgImagePath = relativeRoot + "res/bkgnd.png";//"../res/bkgnd.png";
    
    static final long randomSeed = 1234l;    //the seed of the randomiser
    
    //these constants will hold the tile X,Y,width and heights of various sprites
    static final int playerX = 0, playerY = 0, playerW = 98, playerH = 150;
    static final int fireX = 105, fireY = 0, fireW = 110, fireH = 150;
    static final int waterX = 216, waterY = 0, waterW = 110, waterH = 150;
    static final int afireX = 330, afireY = 0, afireW = 38, afireH = 150;
    static final int awaterX = 370, awaterY = 0, awaterW = 38, awaterH = 150;
    
    // These values will hold a constant ceiling frame rate...
    private static final float frameRate = 60;
    static final float delta = 1/frameRate; // Actually supposed to be dynamic, but being naive for now
    
    // these values hold the acceleration (in pixels per second per second)
    private static final float acceleration = 15;
    static final float defaultAcceleration = delta * acceleration;
    
    //these constants hold the speed range of enemy movement (in pixels per second)
    static final float minEnemySpeed = 240;
    static final float maxEnemySpeed = 480;
    
    //this constant holds the speed of weapon movement (in pixels per second)
    static final float weaponSpeed = -480;   //against the normal movement, hence negative
    
    //this variable holds the player position
    static final int playerXpos = 20, playerYpos = 80;
    static final int weaponXpos = (playerXpos + playerW), weaponYpos = (playerYpos);
    
    enum Weapon {fire, water};  //choosing the weapon of your choice
    
    static final int waterKey = KeyEvent.VK_A;
    static final int fireKey = KeyEvent.VK_F;
    static final int newGameKey = KeyEvent.VK_N;
    static final int pauseKey = KeyEvent.VK_SPACE;
    
    //these constants hold the reward points for specific actions
    static final int killScore = 10;
    static final int weaponWaste = -2;
    
    //these constants will hold paths to the audio files to be played during the game...
    static final String gameOverSound = relativeRoot + "res/gameover.wav";
    static final String gameStartSound = relativeRoot + "res/gamestart.wav";
    static final String weaponYieldSound = relativeRoot + "res/WeaponYield.wav";
}