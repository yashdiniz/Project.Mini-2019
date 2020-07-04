/**
 * This class defines the game as a whole(the main logic)...
 * @author: @yash.diniz;
**/
package runner;

import javax.swing.*;
import javax.imageio.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.*;

public class Game extends JPanel implements ActionListener {
    private boolean gameOver = false;
    boolean paused = true;    //this flag will store whether game is over, or paused
    private static BufferedImage background;
    //this custom class holds various basic definitions of sprites on screen(remember, all these will be reusable!)
    private static Sprite player;
    javax.swing.Timer refresh;  //refreshes the frame per second at 60fps
    
    private static float acceleration = Config.defaultAcceleration; //rate at which to decrease the delay(thus increaing difficulty)
    private static float enemySpeed = Config.minEnemySpeed;
    
    private static EnemySpawner spawner;   //the spawner will spawn enemies, and check the player's weapon dispatched
    
    //this constructor initialises the JPanel and it's components
    Game() {
        super();    //call parent
        this.setBounds(0,0,Config.frameWidth, Config.frameHeight);
        this.addKeyListener(new RunnerKeyListener(this)); //listens to whichever key is pressed
        this.setFocusable(true); //makes user focus on the window
        try {
            //JOptionPane.showMessageDialog(this, new File(".").getAbsolutePath());
            this.setBackground(new Color(225,225,225));     //sets background of the frame
            
            background = ImageIO.read(new FileInputStream(Config.bkgImagePath));
            //this image buffer will hold all the tiles
            BufferedImage tilesImage = ImageIO.read(new FileInputStream(Config.tilesImagePath));
            //gets a subimage(tile) out of the original image...
            BufferedImage playerImage = tilesImage.getSubimage(Config.playerX, Config.playerY, Config.playerW, Config.playerH);
            BufferedImage fireEnemyImage = tilesImage.getSubimage(Config.fireX, Config.fireY, Config.fireW, Config.fireH);
            BufferedImage waterEnemyImage = tilesImage.getSubimage(Config.waterX, Config.waterY, Config.waterW, Config.waterH);
            BufferedImage fireWeaponImage = tilesImage.getSubimage(Config.afireX, Config.afireY, Config.afireW, Config.afireH);
            BufferedImage waterWeaponImage = tilesImage.getSubimage(Config.awaterX, Config.awaterY, Config.awaterW, Config.awaterH);
            
            //creates the initial sprites out of the images
            player = new Sprite(playerImage, Config.playerXpos, Config.playerYpos, Config.playerW, Config.playerH);
            Sprite fireWeapon = new Sprite(fireWeaponImage, Config.weaponXpos, Config.weaponYpos, Config.afireW, Config.afireH);
            Sprite waterWeapon = new Sprite(waterWeaponImage, Config.weaponXpos, Config.weaponYpos, Config.awaterW, Config.awaterH);
            Sprite fireEnemy = new Sprite(fireEnemyImage, Config.frameWidth, Config.playerYpos, Config.fireW, Config.fireH);
            Sprite waterEnemy = new Sprite(waterEnemyImage, Config.frameWidth, Config.playerYpos, Config.waterW, Config.waterH);
            //initialises the spawner
            spawner = new EnemySpawner(waterWeapon, waterEnemy, fireWeapon, fireEnemy);
            
            refresh = new javax.swing.Timer((int)(Config.delta*1000), this);  //Timer is used to generate actionEvents at delay intervals...
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        playSound(Config.gameStartSound);     //play the game start audio
    }
    @Override
    public void paint(Graphics g) {
        g.drawImage(background, 0, 0, null);     //draws the background
        //player.drawBounds(g);
        player.drawSprite(g);                   //draws the main player
        if(!gameOver && !paused) {
            Sprite enemy = spawner.spawnEnemy();   //spawn a random enemy
            drawEnemy(enemy, g);    //draw enemy on screen
            spawner.drawWeapon(g);   
            
            spawner.checkDeathByWeapon(enemy);   //call function to check if currentWeapon intersected currentEnemy
        }    
        updateGameSpeed(); //accelerate game on every paint...
        ScoreKeeper.drawScore(g);   //draws score on screen
        if(gameOver) {
            g.setFont(Config.largeFont);    //draws game over message
            g.setColor(Color.RED);
            g.drawString(Config.gameOver, Config.gameOverX, Config.gameOverY);
            g.setColor(Color.BLACK);
        }
        if(isGamePaused()) g.drawString(Config.help, Config.helpX, Config.helpY);   //draws game paused message
    } 
    private void updateGameSpeed() {
        if(!gameOver && !paused) {    //meaning,the game is being played
            refresh.start();                        //start timer (if not already started)
            System.out.print("\tEnemy speed: " + enemySpeed);
            //accelerate/decelerate the game based on whether the game is being made easy or tough
            if(enemySpeed >= Config.minEnemySpeed && enemySpeed <= Config.maxEnemySpeed)
                enemySpeed += acceleration;
            else {
                acceleration = -acceleration;   // flip acceleration
                enemySpeed += acceleration; // and bring enemySpeed back into range
            }
        } else refresh.stop();                          //stop timer (if game over)
    }
    private void drawEnemy(Sprite enemy, Graphics g) {
        if(enemy != null) {
            enemy.drawSprite(g);             //paint the enemy
            //enemy.drawBounds(g);
            enemy.updateSprite(enemySpeed);   //update the enemy type when it approaches
            if(enemy.spriteX < -Config.playerW) spawner.SpriteKilled(enemy);    //kill the enemy if it leaves the screen
            if(enemy.intersects(player)) endGame();   //game over if enemy collides with player
        }
    }
    private void endGame(){
        gameOver = true;
        playSound(Config.gameOverSound);  //play the game over sound...
    }
    boolean isGameOver() {
        return gameOver;
    }
    boolean isGamePaused() {
        return paused;
    }
    void spawnWeapon(Config.Weapon w) {
        spawner.spawnWeapon(w);            //spawns the weapon
        playSound(Config.weaponYieldSound); //play the weapon yield sound
    }
    private static synchronized void playSound(String path) {    //downloads audio to ram,clip.start plays it
        //source: https://stackoverflow.com/a/26318
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(path)));
            clip.start();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    void restartGame() {            
        ScoreKeeper.showScoreGUI(this);    //call the database module that manages scores
        repaint();                                  //draws game over on the screen
        ScoreKeeper.resetScore();    //reset highScore for a new game
        gameOver = false;
        spawner.resetSprites();      //resets sprites for the new game
        acceleration = Config.defaultAcceleration;  //reset acceleration 
        enemySpeed = Config.minEnemySpeed;  // reset enemy speed
        playSound(Config.gameStartSound);     //play the game start audio
        refresh.start();
    }
    private static long time = System.nanoTime();
    private static float tempDelta = 1/Config.frameRate;
    @Override
    public void actionPerformed(ActionEvent e) {
        // dynamically adapt delta(and indirectly frame rate) depending on current device performance
        // use the ceiling frame rate set in Config as a limiting factor
        Config.delta = ((System.nanoTime() - time)/1000000000f) < tempDelta*2 // if delta is around recommended levels
                        ? (System.nanoTime() - time)/1000000000f    // recalculate delta 
                        : tempDelta;    // otherwise reset delta if game was paused (preventing compensation for a large delay between frames)
        System.out.print("\rFPS: " + 1/Config.delta);
        repaint();  //repaint upon action, which will be invoked by Timer(in constructor)
        this.requestFocus();                    //request focus to game panel
        time = System.nanoTime();
    }
}