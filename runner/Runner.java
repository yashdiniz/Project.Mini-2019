/**
 * This contains the initial logic for the LogicRunner game.
 * @author: @yash.diniz;
 **/
package runner;
import javax.swing.*;

public class Runner {
    public static void main(String[] args) {
        JFrame f = new JFrame(Config.gameName);
        f.setResizable(false);      //do not allow the window to be resized
        f.setSize(Config.frameWidth, Config.frameHeight);        //setting a rectangular display
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //cause it to ease closing
        
        //f.setLayout(null);
        Game g = new Game();
        f.add(g);   //add the Game panel into the frame
        JPanel p = new JPanel();
        p.add(new JLabel(Config.help));  //show the user the basic help message...
        f.add(p);
        f.setVisible(true);
    }
}
