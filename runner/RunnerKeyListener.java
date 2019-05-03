/**
 * This contains the keyListener, which listens to the keys tapped by the user when the game runs.
 * @author: @yash.diniz;
 **/
package runner;

import java.awt.event.*;

public class RunnerKeyListener implements java.awt.event.KeyListener {
    Game g;
    boolean paused = false;
    RunnerKeyListener(Game game) {
        g = game;
    }
    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case Config.waterKey: {
                if(!g.isGamePaused()) g.spawnWeapon(Config.Weapon.water);   //dispatch water weapon...
            } break;
            case Config.fireKey: {
                if(!g.isGamePaused()) g.spawnWeapon(Config.Weapon.fire);   //dispatch fire weapon...
            } break;
            case Config.newGameKey: {
                if(!g.isGamePaused() && g.isGameOver()) g.restartGame();
            } break;
            case Config.pauseKey: {
                if(!g.isGameOver() && !g.isGamePaused()) {
                    g.paused = true;
                    g.refresh.stop();
                    g.repaint();
                } else {
                    g.paused = false;
                    g.refresh.start();
                }
            } break;
            default: System.out.println("Key pressed: " + e.getKeyChar());
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        //we will not make use of KeyReleased() event in this game...
    }
    @Override
    public void keyTyped(KeyEvent e) {
        //we will not make use of KeyTyped() event in this game...
    }
}
