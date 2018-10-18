import javax.swing.*;
import java.awt.*;

public class SimulatorLayout extends JFrame {
    int labelFontSize = 25;
    int infoFontSize = 20;

    JPanel sunlightPanel = new JPanel(new BorderLayout(5,5));
    JPanel temperaturePanel = new JPanel(new BorderLayout(5,5));
    JPanel soilMoisturePanel = new JPanel(new BorderLayout(5,5));

    JLabel sunlightLabel = new JLabel(" Sunlight Percent ");
    JLabel sunlightNumber = new JLabel("", SwingConstants.CENTER);
    JLabel temperatureLabel = new JLabel(" Temperature ");
    JLabel temperatureNumber = new JLabel("", SwingConstants.CENTER);
    JLabel soilMoistureLabel = new JLabel(" Soil Moisture Percent ");
    JLabel soilMoistureNumber = new JLabel("", SwingConstants.CENTER);

    Font labelFont = new Font("Verdana", Font.BOLD, labelFontSize);
    Font infoFont = new Font("Verdana", Font.PLAIN, infoFontSize);


    public SimulatorLayout() {
        super("Home Simulator System");
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Sunlight panel add and layout
        add(sunlightPanel);
        sunlightPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        sunlightPanel.add(sunlightLabel, BorderLayout.NORTH);
        sunlightPanel.add(sunlightNumber, BorderLayout.CENTER);
        sunlightLabel.setFont(labelFont);
        sunlightNumber.setFont(infoFont);

        //Temperature panel add and layout
        add(temperaturePanel);
        temperaturePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        temperaturePanel.add(temperatureLabel, BorderLayout.NORTH);
        temperaturePanel.add(temperatureNumber, BorderLayout.CENTER);
        temperatureLabel.setFont(labelFont);
        temperatureNumber.setFont(infoFont);

        //Soil Moisture add and layout
        add(soilMoisturePanel);
        soilMoisturePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        soilMoisturePanel.add(soilMoistureLabel, BorderLayout.NORTH);
        soilMoisturePanel.add(soilMoistureNumber, BorderLayout.CENTER);
        soilMoistureLabel.setFont(labelFont);
        soilMoistureNumber.setFont(infoFont);

        setSize(900, 800);
        setVisible(true);
    }
}
