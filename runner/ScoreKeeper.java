/**
 * This code runs the basic logic of keeping score. Also updates DB with scores.
 * @author : @yash.diniz;
**/
package runner;
import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class ScoreKeeper {
    public static int score = 0;
    
    static void resetScore() {
        score = 0;
    }
    static int getScore() {  //to access the score by calling method 
        return score;
    }
    static int updateScore(int s) {
        return score += s;
    }
    static int setScore(int s) {
        return score = s;
    }
    static void drawScore(Graphics g) {    //draws the score onto the game panel
        g.setFont(Config.smallFont);
        g.drawString(Config.scoreString + score, Config.scoreX, Config.scoreY);
    }
    static void showScoreGUI(Component g) {  //when the game ends,the database module is called to update the scores
        String str = "Your current score: " + score + "\n";    //string containing the database result...
        try {
            Class.forName("com.mysql.jdbc.Driver");    //import required JDBC driver
            Connection c = DriverManager.getConnection(Config.scoreDB, Config.DBuser, Config.DBpass);
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT * from " + Config.scoreTable + " order by " + Config.scoreField + " desc LIMIT 10");
            str += "High Scores: \n";
            while(r.next()) {
                str += r.getString(Config.nameField);
                str += "       ";
                str += r.getString(Config.scoreField);
                str += "\n";
            }
        } catch(ClassNotFoundException | SQLException e) {
            System.out.println("Database connection error...");
            e.printStackTrace();
        }
        str += "\nEnter your name: ";
        String name = JOptionPane.showInputDialog(g, str, "Scores", JOptionPane.INFORMATION_MESSAGE);
        if(name != null && !name.trim().equals(""))
        try {
            name = name.trim().substring(0, name.length()<10?name.length():9);
            Class.forName("com.mysql.jdbc.Driver");    //import required JDBC driver
            Connection c = DriverManager.getConnection(Config.scoreDB, Config.DBuser, Config.DBpass);
            PreparedStatement s = c.prepareStatement("SELECT * from " + Config.scoreTable + " where " + Config.nameField + " = ?");
            s.setString(1,name);
            ResultSet r = s.executeQuery();
            if(!r.next()) {
                s = c.prepareStatement("INSERT INTO " + Config.scoreTable + " VALUES ( ? , ? )");
                s.setString(1,name);
                s.setInt(2,score);
                s.execute();
            } else {
                s = c.prepareStatement("UPDATE " + Config.scoreTable + " SET " + Config.scoreField + " = ? where " + Config.nameField + " = ? AND " + Config.scoreField + " < ?");
                s.setInt(1,score);
                s.setString(2,name);
                s.setInt(3,score);
                s.execute();
            }
        } catch(ClassNotFoundException | SQLException e) {
            System.out.println("Database connection error...");
            e.printStackTrace();
        }
    }
}