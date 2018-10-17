import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SimulatorLayout extends JFrame {

    JPanel sunlightPanel = new JPanel(new BorderLayout());
    JPanel temperaturePanel = new JPanel(new BorderLayout());
    JPanel soilMoisturePanel = new JPanel(new BorderLayout());

    JLabel sunlightPercentLabel = new JLabel("Sunlight Percent:");
    JLabel sunlightPercentNumber = new JLabel("%");
    JLabel temperatureDegreesLabel = new JLabel("Temperature:");
    JLabel temperatureDegreesNumber = new JLabel("°");
    JLabel soilMoisturePercentLabel = new JLabel("Soil Moisture Percent:");
    JLabel soilMoisturePercentNumber = new JLabel("%");


    public SimulatorLayout() {
        super("Home Sim Simulator");
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(sunlightPanel);
        add(temperaturePanel);
        add(soilMoisturePanel);

        sunlightPanel.setBackground(Color.YELLOW);
        temperaturePanel.setBackground(Color.ORANGE);
        soilMoisturePanel.setBackground(Color.CYAN);

        sunlightPanel.add(sunlightPercentLabel, BorderLayout.NORTH);
        sunlightPanel.add(sunlightPercentNumber, BorderLayout.CENTER);
        temperaturePanel.add(temperatureDegreesLabel, BorderLayout.NORTH);
        temperaturePanel.add(temperatureDegreesNumber, BorderLayout.CENTER);
        soilMoisturePanel.add(soilMoisturePercentLabel, BorderLayout.NORTH);
        soilMoisturePanel.add(soilMoisturePercentNumber, BorderLayout.CENTER);

        setSize(1280, 720);
        setVisible(true);
    }
}
