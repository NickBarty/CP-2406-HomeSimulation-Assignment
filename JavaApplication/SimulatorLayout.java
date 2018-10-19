import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimulatorLayout extends JFrame implements ActionListener {
    JPanel timePanel = new JPanel(new BorderLayout(5, 5));
    JPanel sunlightPanel = new JPanel(new BorderLayout(5, 5));
    JPanel temperaturePanel = new JPanel(new BorderLayout(5, 5));
    JPanel soilMoisturePanel = new JPanel(new BorderLayout(5, 5));
    JPanel electricityPanel = new JPanel(new BorderLayout(5, 5));
    JPanel waterPanel = new JPanel(new BorderLayout(5, 5));
    JPanel rainTimePanel = new JPanel(new BorderLayout(5, 5));
    JPanel eventAlertPanel = new JPanel(new BorderLayout(5, 5));

    JMenuBar mainBar = new JMenuBar();
    JMenu menu1 = new JMenu("File");
    JMenuItem viewConfig = new JMenuItem("Current Configuration");
    JMenuItem exit = new JMenuItem("Exit");

    JMenu menu2 = new JMenu("Simulation");
    JMenuItem showHideInfo = new JMenuItem("Show/Hide Info");
    JMenuItem run = new JMenuItem("Run");//TODO
    JMenuItem pause = new JMenuItem("Pause");//TODO
    JMenuItem stop = new JMenuItem("Stop");//TODO

    JMenu menu3 = new JMenu("Help");
    JMenuItem about = new JMenuItem("About");
    JMenuItem userGuide = new JMenuItem("User Guide");
    JMenuItem viewSimMetrics = new JMenuItem("View Sim Metrics");


    JLabel sunlightLabel = new JLabel(" Sunlight Percent ", SwingConstants.CENTER);
    JLabel sunlightNumber = new JLabel("", SwingConstants.CENTER);
    JLabel temperatureLabel = new JLabel(" Temperature ", SwingConstants.CENTER);
    JLabel temperatureNumber = new JLabel("", SwingConstants.CENTER);
    JLabel soilMoistureLabel = new JLabel(" Soil Moisture Percent ", SwingConstants.CENTER);
    JLabel soilMoistureNumber = new JLabel("", SwingConstants.CENTER);
    JLabel timeLabel = new JLabel(" Time ", SwingConstants.CENTER);
    JLabel timeNumber = new JLabel("", SwingConstants.CENTER);
    JLabel electricityLabel = new JLabel(" Electricity ", SwingConstants.CENTER);
    JLabel electricityNumber = new JLabel("", SwingConstants.CENTER);
    JLabel waterLabel = new JLabel(" Water ", SwingConstants.CENTER);
    JLabel waterNumber = new JLabel("", SwingConstants.CENTER);
    JLabel rainTimeLabel = new JLabel(" Total Rain Time ", SwingConstants.CENTER);
    JLabel rainTimeNumber = new JLabel("", SwingConstants.CENTER);
    JLabel eventAlertLabel = new JLabel(" Last Event To Occur ", SwingConstants.CENTER);
    JLabel eventAlertText = new JLabel("", SwingConstants.CENTER);

    Font labelFont = new Font("Verdana", Font.BOLD, 25);
    Font infoFont = new Font("Verdana", Font.PLAIN, 20);
    Font smallInfoFont = new Font("Verdana", Font.PLAIN, 15);

    int showHideCounter = 0;

    Dimension metricDimensions = new Dimension(400,75);


    public SimulatorLayout() {
        super("Home Simulator System");
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setJMenuBar(mainBar);

        mainBar.add(menu1);
        menu1.add(viewConfig);
        menu1.add(exit);
        viewConfig.addActionListener(this);
        exit.addActionListener(this);

        mainBar.add(menu2);
        menu2.add(showHideInfo);
        menu2.add(run);
        menu2.add(pause);
        menu2.add(stop);
        showHideInfo.addActionListener(this);
        run.addActionListener(this);
        pause.addActionListener(this);
        stop.addActionListener(this);

        mainBar.add(menu3);
        menu3.add(about);
        menu3.add(userGuide);
        menu3.add(viewSimMetrics);
        about.addActionListener(this);
        userGuide.addActionListener(this);
        viewSimMetrics.addActionListener(this);


        //Time panel add and layout
        add(timePanel);
        timePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        timePanel.add(timeLabel, BorderLayout.NORTH);
        timePanel.add(timeNumber, BorderLayout.CENTER);
        timeLabel.setFont(labelFont);
        timeNumber.setFont(infoFont);
        timePanel.setPreferredSize(metricDimensions);

        //Sunlight panel add and layout
        add(sunlightPanel);
        sunlightPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        sunlightPanel.add(sunlightLabel, BorderLayout.NORTH);
        sunlightPanel.add(sunlightNumber, BorderLayout.CENTER);
        sunlightLabel.setFont(labelFont);
        sunlightNumber.setFont(infoFont);
        sunlightPanel.setPreferredSize(metricDimensions);

        //Temperature panel add and layout
        add(temperaturePanel);
        temperaturePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        temperaturePanel.add(temperatureLabel, BorderLayout.NORTH);
        temperaturePanel.add(temperatureNumber, BorderLayout.CENTER);
        temperatureLabel.setFont(labelFont);
        temperatureNumber.setFont(infoFont);
        temperaturePanel.setPreferredSize(metricDimensions);

        //Soil Moisture add and layout
        add(soilMoisturePanel);
        soilMoisturePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        soilMoisturePanel.add(soilMoistureLabel, BorderLayout.NORTH);
        soilMoisturePanel.add(soilMoistureNumber, BorderLayout.CENTER);
        soilMoistureLabel.setFont(labelFont);
        soilMoistureNumber.setFont(infoFont);
        soilMoisturePanel.setPreferredSize(metricDimensions);

        //Electricity panel add and layout
        add(electricityPanel);
        electricityPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        electricityPanel.add(electricityLabel, BorderLayout.NORTH);
        electricityPanel.add(electricityNumber, BorderLayout.CENTER);
        electricityLabel.setFont(labelFont);
        electricityNumber.setFont(infoFont);
        electricityPanel.setPreferredSize(metricDimensions);


        //Water panel add and layout
        add(waterPanel);
        waterPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        waterPanel.add(waterLabel, BorderLayout.NORTH);
        waterPanel.add(waterNumber, BorderLayout.CENTER);
        waterLabel.setFont(labelFont);
        waterNumber.setFont(infoFont);
        waterPanel.setPreferredSize(metricDimensions);


        //Rain time panel add and layout
        add(rainTimePanel);
        rainTimePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        rainTimePanel.add(rainTimeLabel, BorderLayout.NORTH);
        rainTimePanel.add(rainTimeNumber, BorderLayout.CENTER);
        rainTimeLabel.setFont(labelFont);
        rainTimeNumber.setFont(infoFont);
        rainTimePanel.setPreferredSize(metricDimensions);


        //Event alert panel add and layout
        add(eventAlertPanel);
        eventAlertPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        eventAlertPanel.add(eventAlertLabel, BorderLayout.NORTH);
        eventAlertPanel.add(eventAlertText, BorderLayout.CENTER);
        eventAlertLabel.setFont(labelFont);
        eventAlertText.setFont(infoFont);
        eventAlertPanel.setPreferredSize(metricDimensions);


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

        setSize(1650, 800);
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == viewConfig) {
            HomeSimSystem.displayConfigData();
        }

        if (source == exit) {
            System.exit(0);
        }

        if (source == showHideInfo && showHideCounter % 2 == 0) {
            HomeSimSystem.disableMetricsDisplay();
            showHideCounter++;
        } else {
            HomeSimSystem.enableMetricsDisplay();
            showHideCounter++;
        }

        if (source == run){

        }

        if (source == pause){

        }

        if (source == stop){

        }

        if (source == about){
            JmenuDialogues.displayAbout();
        }

        if (source == userGuide){
            JmenuDialogues.displayUserGuide();
        }

        if (source == viewSimMetrics) {
            HomeSimSystem.displaySimSpeed();
        }


    }
}
