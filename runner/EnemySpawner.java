/**
 * This module spawns a random enemy, and checks for the weapon dispatched by the user.
 * Much like the machine of the game.
 * NOTE: Only one sprite can be spawned at a time, and only one weapon can be dispatched at a time!
 * @author: yash.diniz;
**/
package runner;
import java.util.*;
import java.awt.*;

public class EnemySpawner {
    Sprite waterWeapon, waterEnemy, fireWeapon, fireEnemy;
    static Sprite currentEnemy; //current enemy chosen by the randomiser
    static Sprite currentWeapon;
    final static Random r = new Random();
    
    EnemySpawner(Sprite waterWeapon, Sprite waterEnemy, Sprite fireWeapon, Sprite fireEnemy) { //takes the sprites and initialises them
        this.waterEnemy = waterEnemy;
        this.waterWeapon = waterWeapon;
        this.fireEnemy = fireEnemy;
        this.fireWeapon = fireWeapon;
        
        if(Config.randomSeed!=0) r.setSeed(Config.randomSeed);   //sets the seed for the Spawner
    }
    static boolean spriteSpawned = false;   //this boolean will make sure only one sprite is spawned at a time...
    
    boolean isSpriteSpawned() {
        return spriteSpawned;
    }
    //this function spawns an enemy, and updates variables...
    Sprite spawnEnemy() {
        if(!spriteSpawned) {    //if sprite not already spawned
            spriteSpawned = true;
            currentEnemy = r.nextBoolean()?waterEnemy:fireEnemy;    //will spawn an enemy based on random boolean
        }
        return currentEnemy;
    }
    //this function should be invoked when the enemy is killed
    void SpriteKilled(Sprite enemy) {
        resetSprites();
    }
    //this function, when invoked will reset data of all weapon sprites...
    void resetSprites() {
        resetEnemy();
        resetWeapons();
    }
    private void resetEnemy() {
        currentEnemy = null;
        spriteSpawned = false;  //sprite no longer spawned
        fireEnemy.updateSprite(Config.frameWidth);
        waterEnemy.updateSprite(Config.frameWidth);
    }
    private void resetWeapons() {
        currentWeapon = null;
        fireWeapon.updateSprite(Config.weaponXpos);
        waterWeapon.updateSprite(Config.weaponXpos);
    }
    //this function spawns a weapon, based on user value...
    Sprite spawnWeapon(Config.Weapon w) {
        Sprite weapon = (Config.Weapon.fire == w)?fireWeapon:waterWeapon;  //if else to generate either weapon based on key pressed
        ScoreKeeper.updateScore(Config.weaponWaste);    //decrements the score on weapon dispatch
        weapon.updateSprite(Config.weaponXpos);
        return currentWeapon = weapon;
    }
    void drawWeapon(Graphics g) {
        if(currentWeapon != null) {
            currentWeapon.drawSprite(g);
            //currentWeapon.drawBounds(g);
        }
    }
    //this function kills a sprite if current weapon collides with enemy
    void checkDeathByWeapon(Sprite enemy) {
        if(currentWeapon!=null) {
            currentWeapon.updateSprite((float)Config.weaponSpeed);  //update the weapon to move...
            if((waterEnemy.intersects(fireWeapon) || fireEnemy.intersects(waterWeapon))
                    && !(waterEnemy.intersects(waterWeapon) && fireEnemy.intersects(fireWeapon)))
                //if enemy and weapon are of opposite types
                if(enemy.intersects(currentWeapon)) { //if enemy hurt by weapon
                    SpriteKilled(enemy);
                    ScoreKeeper.updateScore(Config.killScore);      //update the score on every kill
                }
            else if(currentWeapon.spriteX > Config.frameWidth) resetWeapons();
        }
    }
}