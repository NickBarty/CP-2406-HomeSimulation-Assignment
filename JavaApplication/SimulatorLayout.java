import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimulatorLayout extends JFrame implements ActionListener, KeyListener {
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
    JMenuItem viewConfig = new JMenuItem("Current Configuration (8)");
    JMenuItem exit = new JMenuItem("Exit (9)");

    JMenu menu2 = new JMenu("Simulation");
    JMenuItem run = new JMenuItem("Run (1)");
    JMenuItem pause = new JMenuItem("Pause (2)");
    JMenuItem showHideInfo = new JMenuItem("Show/Hide Info (3)");
    JMenuItem changeSimSpeed = new JMenuItem("Change Sim Speed (4)");

    JMenu menu3 = new JMenu("Help");
    JMenuItem about = new JMenuItem("About (5)");
    JMenuItem userGuide = new JMenuItem("User Guide (6)");
    JMenuItem viewSimMetrics = new JMenuItem("Sim-speed Metrics (7)");


    JLabel sunlightLabel = new JLabel(" Sunlight Percent ", SwingConstants.CENTER);
    JLabel sunlightNumber = new JLabel("", SwingConstants.CENTER);
    JLabel temperatureLabel = new JLabel(" Temperature ", SwingConstants.CENTER);
    JLabel temperatureNumber = new JLabel("", SwingConstants.CENTER);
    JLabel soilMoistureLabel = new JLabel(" Soil Moisture Percent ", SwingConstants.CENTER);
    JLabel soilMoistureNumber = new JLabel("", SwingConstants.CENTER);
    JLabel timeLabel = new JLabel(" Time ", SwingConstants.CENTER);
    JLabel timeNumber = new JLabel("", SwingConstants.CENTER);
    JLabel electricityLabel = new JLabel(" Electricity Used ", SwingConstants.CENTER);
    JLabel electricityNumber = new JLabel("", SwingConstants.CENTER);
    JLabel waterLabel = new JLabel(" Water Used", SwingConstants.CENTER);
    JLabel waterNumber = new JLabel("", SwingConstants.CENTER);
    JLabel rainTimeLabel = new JLabel(" Total Rain Time ", SwingConstants.CENTER);
    JLabel rainTimeNumber = new JLabel("", SwingConstants.CENTER);
    JLabel eventAlertLabel = new JLabel(" Last Event To Occur ", SwingConstants.CENTER);
    JLabel eventAlertText = new JLabel("", SwingConstants.CENTER);

    Font labelFont = new Font("Verdana", Font.BOLD, 25);
    Font infoFont = new Font("Verdana", Font.PLAIN, 20);
    Font smallInfoFont = new Font("Verdana", Font.PLAIN, 15);

    int showHideCounter = 0;




    public SimulatorLayout() {
        super("Home Simulator System");
        setLayout(new FlowLayout());
        Dimension metricDimensions = new Dimension((int)HomeSimSystem.dimension.getWidth()/4 - 5, 75);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(this);
        setJMenuBar(mainBar);

        mainBar.add(menu1);
        menu1.add(viewConfig);
        menu1.add(exit);
        viewConfig.addActionListener(this);
        exit.addActionListener(this);

        mainBar.add(menu2);
        menu2.add(run);
        menu2.add(pause);
        menu2.add(showHideInfo);
        menu2.add(changeSimSpeed);
        run.addActionListener(this);
        pause.addActionListener(this);
        showHideInfo.addActionListener(this);
        changeSimSpeed.addActionListener(this);

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

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        setLocation(x, y);
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

        if (source == run) {
            HomeSimSystem.run();

        }

        if (source == pause) {
            HomeSimSystem.pause();
        }

        if (source == changeSimSpeed) {
            HomeSimSystem.setSpeed();
        }

        if (source == about) {
            JmenuDialogues.displayAbout();
        }

        if (source == userGuide) {
            JmenuDialogues.displayUserGuide();
        }

        if (source == viewSimMetrics) {
            HomeSimSystem.displaySimSpeed();
        }


    }

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (c == '1') {
            HomeSimSystem.run();
        }
        if (c == '2') {
            HomeSimSystem.pause();
        }
        if (c == '3' && showHideCounter % 2 == 0) {
            HomeSimSystem.disableMetricsDisplay();
            showHideCounter++;
        } else {
            HomeSimSystem.enableMetricsDisplay();
            showHideCounter++;
        }
        if (c == '4') {
            HomeSimSystem.setSpeed();
        }
        if (c == '5') {
            JmenuDialogues.displayAbout();
        }
        if (c == '6') {
            JmenuDialogues.displayUserGuide();
        }
        if (c == '7') {
            HomeSimSystem.displaySimSpeed();
        }
        if (c == '8') {
            HomeSimSystem.displayConfigData();
        }
        if (c == '9') {
            System.exit(0);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
