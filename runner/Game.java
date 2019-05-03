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
    boolean gameOver = false, paused = false;    //this flag will store whether game is over
    static BufferedImage background, playerImage, fireEnemyImage, waterEnemyImage, fireWeaponImage, waterWeaponImage; //stores all images
    static BufferedImage tilesImage;    //this image buffer will hold all the tiles
    //this custom class holds various basic definitions of sprites on screen(remember, all these will be reusable!)
    static Sprite player, waterEnemy, fireEnemy, waterWeapon, fireWeapon;
    javax.swing.Timer refresh;  //refreshes the frame per second at 60fps
    
    private static float delay = Config.defaultDelay;  //default delay value when game starts
    private static float acceleration = Config.defaultAcceleration; //rate at which to decrease the delay(thus increaing difficulty)
    
    private static EnemySpawner spawner;   //bject,the spawner will spawn enemies, and check the player's weapon dispatched
    
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
            tilesImage = ImageIO.read(new FileInputStream(Config.tilesImagePath));
            //gets a subimage(tile) out of the original image...
            playerImage = tilesImage.getSubimage(Config.playerX, Config.playerY, Config.playerW, Config.playerH);
            fireEnemyImage = tilesImage.getSubimage(Config.fireX, Config.fireY, Config.fireW, Config.fireH);
            waterEnemyImage = tilesImage.getSubimage(Config.waterX, Config.waterY, Config.waterW, Config.waterH);
            fireWeaponImage = tilesImage.getSubimage(Config.afireX, Config.afireY, Config.afireW, Config.afireH);
            waterWeaponImage = tilesImage.getSubimage(Config.awaterX, Config.awaterY, Config.awaterW, Config.awaterH);
            
            //creates the initial sprites out of the images
            player = new Sprite(playerImage, Config.playerXpos, Config.playerYpos, Config.playerW, Config.playerH);
            fireWeapon = new Sprite(fireWeaponImage, Config.weaponXpos, Config.weaponYpos, Config.afireW, Config.afireH);
            waterWeapon = new Sprite(waterWeaponImage, Config.weaponXpos, Config.weaponYpos, Config.awaterW, Config.awaterH);
            fireEnemy = new Sprite(fireEnemyImage, Config.frameWidth, Config.playerYpos, Config.fireW, Config.fireH);
            waterEnemy = new Sprite(waterEnemyImage, Config.frameWidth, Config.playerYpos, Config.waterW, Config.waterH);
            //initialises the spawner
            spawner = new EnemySpawner(waterWeapon, waterEnemy, fireWeapon, fireEnemy);
            
            refresh = new javax.swing.Timer((int)delay, this);  //this will be used to repaint by repeatedly causing actions
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
        player.drawSprite(g);                   //draws the main sprite
        
        Sprite enemy = spawner.spawnEnemy();   //spawn a random sprite
        drawEnemy(enemy, g);    //perform basic enemy draw
        spawner.drawWeapon(g);
        
        spawner.checkDeathByWeapon(enemy);   //call function to check if currentWeapon intersected currentEnemy
        
        updateGameSpeed(); //accelerate game on every paint...
        if(gameOver) {
            g.setFont(Config.largeFont);
            g.setColor(Color.RED);
            g.drawString(Config.gameOver, Config.gameOverX, Config.gameOverY);
            g.setColor(Color.BLACK);
        }
        ScoreKeeper.drawScore(g);
        if(isGamePaused()) g.drawString(Config.help, Config.helpX, Config.helpY);
    }
    boolean rising = false;
    private void updateGameSpeed() {
        if(!gameOver && !paused) {    //meaning,the game is being played
            refresh.start();                        //start timer (if not already started)
            System.out.println("Delay: " + delay);
            //accelerate/decelerate the game based on whether the game is being made easy or tough
            if((Math.floor(delay) >= Config.easeOfGame && !rising) || (Math.floor(delay) <= Config.diffOfGame && rising))
                refresh.setDelay((int)(delay-=acceleration));   // speed up the game
            else {
                rising = !rising;
                acceleration = -acceleration;      //begin decelerating(until upper limit)
            }
        } else {
            refresh.stop();                          //stop timer (if game over)
            rising = false;
        }
    }
    private void drawEnemy(Sprite enemy, Graphics g) {
        if(enemy != null) {
            enemy.drawSprite(g);             //paint the enemy
            //enemy.drawBounds(g);
            enemy.updateSprite((float)Config.enemySpeed);   //update the enemy type when it approaches
            if(enemy.spriteX < -Config.playerW) spawner.SpriteKilled(enemy);    //kill the enemy if it leaves the screen
            if(enemy.intersects(player)) endGame();   //game over if enemy collides with player
        }
    }
    void endGame(){
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
    static synchronized void playSound(String path) {    //downloads audio to ram,clip.start plays it
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
        delay = Config.defaultDelay;    //reset delay
        acceleration = Config.defaultAcceleration;  //reset acceleration 
        playSound(Config.gameStartSound);     //play the game start audio
        refresh.start();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();  //repaint upon action, which will be invoked by Timer(in constructor)
        this.requestFocus();                    //request focus to game panel
    }
}