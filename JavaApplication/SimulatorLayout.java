import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimulatorLayout extends JFrame implements ActionListener {
    int labelFontSize = 25;
    int infoFontSize = 20;
    int smallInfoFontSize = 15;

    JPanel timePanel = new JPanel(new BorderLayout(5, 5));
    JPanel sunlightPanel = new JPanel(new BorderLayout(5, 5));
    JPanel temperaturePanel = new JPanel(new BorderLayout(5, 5));
    JPanel soilMoisturePanel = new JPanel(new BorderLayout(5, 5));
    JPanel electricityPanel = new JPanel(new BorderLayout(5, 5));
    JPanel waterPanel = new JPanel(new BorderLayout(5, 5));

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
    JLabel timeLabel = new JLabel(" Time ");
    JLabel timeNumber = new JLabel("", SwingConstants.CENTER);
    JLabel electricityLabel = new JLabel(" Electricity ", SwingConstants.CENTER);
    JLabel electricityNumber = new JLabel("", SwingConstants.CENTER);
    JLabel waterLabel = new JLabel(" Water ", SwingConstants.CENTER);
    JLabel waterNumber = new JLabel("", SwingConstants.CENTER);

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

        //Time panel add and layout
        add(timePanel);
        timePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        timePanel.add(timeLabel, BorderLayout.NORTH);
        timePanel.add(timeNumber, BorderLayout.CENTER);
        timeLabel.setFont(labelFont);
        timeNumber.setFont(infoFont);

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

        //Electricity panel add and layout
        add(electricityPanel);
        electricityPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        electricityPanel.add(electricityLabel, BorderLayout.NORTH);
        electricityPanel.add(electricityNumber, BorderLayout.CENTER);
        electricityLabel.setFont(labelFont);
        electricityNumber.setFont(infoFont);

        //Water panel add and layout
        add(waterPanel);
        waterPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        waterPanel.add(waterLabel, BorderLayout.NORTH);
        waterPanel.add(waterNumber, BorderLayout.CENTER);
        waterLabel.setFont(labelFont);
        waterNumber.setFont(infoFont);

        timePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                timePanel.setBackground(Color.GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                timePanel.setBackground(Color.white);
            }
        });

        setSize(920, 900);
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == stop) {
            System.exit(0);
        }

        if (source == viewConfig) {
            HomeSimSystem.displayConfigData();
        }
    }
}
