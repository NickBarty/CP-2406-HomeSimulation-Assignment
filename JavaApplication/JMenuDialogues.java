import javax.swing.*;
import java.awt.*;
//Provide information output dialogues for the GUI
class JMenuDialogues {
    //Displays About information
    static void displayAbout(){
        JLabel message = new JLabel("<html><center><b>About Info</b></center>Home simulator version: <b>V0.36</b><br>" +
                "Programmer: <b>Nicholas Barty</b></html>");
        message.setFont(new Font("Verdana", Font.PLAIN, 20));
        JOptionPane.showMessageDialog(null,message);
    }

    //Displays the user guide
    static void displayUserGuide(){
        JLabel message = new JLabel("<html><center><b>User Guide</b></center>- This is a home simulation simulator<br><br>" +
                "- The house is loaded based on the <b>'config.csv'</b> file <br><br>" +
                "- Find current configuration under: <b>File -> Current Configuration</b><br><br>" +
                "- Display of some metrics can be toggled under: <b>Simulation -> Show/Hide Info</b><br><br>" +
                "- Run/Pause/Stop can be found under: <b>Simulation -> Run/Pause/Stop</b><br><br>" +
                "- About information can be found under: <b>Help -> About</b><br><br>" +
                "- Shortcut keys can be found in brackets <b>()</b><br><br>" +
                "<center><b>Shortcut Keys:</b></center>" +
                "<b>(1)</b> Run Simulator<br>" +
                "<b>(2)</b> Pause Simulator<br>" +
                "<b>(3)</b> Stop Simulator<br>" +
                "<b>(4)</b> Show/Hide Metrics<br>" +
                "<b>(5)</b> Set Simulation Speed<br>" +
                "<b>(6)</b> Display About Information<br>" +
                "<b>(7)</b> Display This Guide<br>" +
                "<b>(8)</b> Display Sim Speed Metrics<br>" +
                "<b>(9)</b> Display Loaded Config Data<br>" +
                "<b>(0)</b> Exit Program</html>");
        message.setFont(new Font("Verdana", Font.PLAIN, 20));
        JOptionPane.showMessageDialog(null,message);
    }
}