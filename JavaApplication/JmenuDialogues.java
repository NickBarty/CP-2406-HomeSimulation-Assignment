import javax.swing.*;
import java.awt.*;

public class JmenuDialogues {
    public static void displayAbout(){
        JLabel message = new JLabel("<html>About Info:<br>Home simulator version: <b>V0.27</b><br>" +
                "Programmer: <b>Nicholas Barty</b></html>");
        message.setFont(new Font("Verdana", Font.PLAIN, 20));
        JOptionPane.showMessageDialog(null,message);
    }

    public static void displayUserGuide(){
        JLabel message = new JLabel("<html>User Guide:<br>- This is a home simulation simulator<br>" +
                "- The house is loaded based on the <b>'config.csv'</b> file <br>" +
                "- Find current configuration under: <b>File -> Current Configuration</b><br>" +
                "- Display of some metrics can be toggled under: <b>Simulation -> Show/Hide Info</b><br>" +
                "- Run/Pause/Stop can be found under: <b>Simulation -> Run/Pause/Stop</b><br>" +
                "- About information can be found under: <b>Help -> About</b></html>");
        message.setFont(new Font("Verdana", Font.PLAIN, 20));
        JOptionPane.showMessageDialog(null,message);
    }
}
