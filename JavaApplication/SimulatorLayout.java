import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulatorLayout extends JFrame implements ActionListener {
    int labelFontSize = 25;
    int infoFontSize = 20;
    int smallInfoFontSize = 15;

    JPanel sunlightPanel = new JPanel(new BorderLayout(5,5));
    JPanel temperaturePanel = new JPanel(new BorderLayout(5,5));
    JPanel soilMoisturePanel = new JPanel(new BorderLayout(5,5));

    JMenuBar mainBar = new JMenuBar();
    JMenu menu1 = new JMenu("Run Operations");
    JMenu menu2 = new JMenu("Config");
    JMenuItem stop = new JMenuItem("Stop");
    JMenuItem viewConfig = new JMenuItem("View Config");

    JLabel sunlightLabel = new JLabel(" Sunlight Percent ");
    JLabel sunlightNumber = new JLabel("", SwingConstants.CENTER);
    JLabel temperatureLabel = new JLabel(" Temperature ");
    JLabel temperatureNumber = new JLabel("", SwingConstants.CENTER);
    JLabel soilMoistureLabel = new JLabel(" Soil Moisture Percent ");
    JLabel soilMoistureNumber = new JLabel("", SwingConstants.CENTER);

    Font labelFont = new Font("Verdana", Font.BOLD, labelFontSize);
    Font infoFont = new Font("Verdana", Font.PLAIN, infoFontSize);
    Font smallInfoFont = new Font("Verdana", Font.PLAIN, smallInfoFontSize);


    public SimulatorLayout() {
        super("Home Simulator System");
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setJMenuBar(mainBar);

        mainBar.add(menu1);
        mainBar.add(menu2);
        menu1.add(stop);
        menu2.add(viewConfig);
        viewConfig.addActionListener(this);
        stop.addActionListener(this);

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


        setSize(1280, 720);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == stop){
            System.exit(0);
        }

        if (source == viewConfig){
            HomeSimSystem.displayConfigData();
        }
    }
}
