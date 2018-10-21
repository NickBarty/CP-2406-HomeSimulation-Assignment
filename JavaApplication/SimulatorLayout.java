import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//Sets the layout for the GUI and sets event listeners
public class SimulatorLayout extends JFrame implements ActionListener, KeyListener {
    //Create first row of GUI panels
    JPanel timePanel = new JPanel(new BorderLayout(5, 5));
    JPanel sunlightPanel = new JPanel(new BorderLayout(5, 5));
    JPanel temperaturePanel = new JPanel(new BorderLayout(5, 5));
    JPanel soilMoisturePanel = new JPanel(new BorderLayout(5, 5));

    //Create number labels for first row
    JLabel timeNumber = new JLabel("", SwingConstants.CENTER);
    JLabel sunlightNumber = new JLabel("", SwingConstants.CENTER);
    JLabel temperatureNumber = new JLabel("", SwingConstants.CENTER);
    JLabel soilMoistureNumber = new JLabel("", SwingConstants.CENTER);

    //Create second row of GUI panels
    JPanel electricityPanel = new JPanel(new BorderLayout(5, 5));
    JPanel waterPanel = new JPanel(new BorderLayout(5, 5));
    JPanel rainTimePanel = new JPanel(new BorderLayout(5, 5));
    JPanel eventAlertPanel = new JPanel(new BorderLayout(5, 5));

    //Create number labels for second row
    JLabel electricityNumber = new JLabel("", SwingConstants.CENTER);
    JLabel waterNumber = new JLabel("", SwingConstants.CENTER);
    JLabel rainTimeNumber = new JLabel("", SwingConstants.CENTER);
    JLabel eventAlertText = new JLabel("", SwingConstants.CENTER);

    //Create fonts for GUI
    Font labelFont = new Font("Verdana", Font.BOLD, 25);
    Font smallInfoFont = new Font("Verdana", Font.PLAIN, 15);

    //Create menu items for menu 1
    private JMenuItem viewConfig = new JMenuItem("Current Configuration (8)");
    private JMenuItem exit = new JMenuItem("Exit (9)");

    //Create menu items for menu 2
    private JMenuItem run = new JMenuItem("Run (1)");
    private JMenuItem pause = new JMenuItem("Pause (2)");
    private JMenuItem showHideInfo = new JMenuItem("Show/Hide Info (3)");
    private JMenuItem changeSimSpeed = new JMenuItem("Change Sim Speed (4)");

    //Create menu items for menu 3
    private JMenuItem about = new JMenuItem("About (5)");
    private JMenuItem userGuide = new JMenuItem("User Guide (6)");
    private JMenuItem viewSimMetrics = new JMenuItem("Sim-speed Metrics (7)");

    //Create counter to allow show/hide functionality
    private int showHideCounter = 0;

    SimulatorLayout() {
        //Set the name of the window
        super("Home Simulator System");

        //Create menu bar and its menus
        JMenuBar mainBar = new JMenuBar();
        JMenu menu1 = new JMenu("File");
        JMenu menu2 = new JMenu("Simulation");
        JMenu menu3 = new JMenu("Help");

        //Create title labels for first row
        JLabel timeLabel = new JLabel(" Time ", SwingConstants.CENTER);
        JLabel sunlightLabel = new JLabel(" Sunlight Percent ", SwingConstants.CENTER);
        JLabel temperatureLabel = new JLabel(" Temperature ", SwingConstants.CENTER);
        JLabel soilMoistureLabel = new JLabel(" Soil Moisture Percent ", SwingConstants.CENTER);

        //Create title labels for second row
        JLabel electricityLabel = new JLabel(" Electricity Used ", SwingConstants.CENTER);
        JLabel waterLabel = new JLabel(" Water Used", SwingConstants.CENTER);
        JLabel rainTimeLabel = new JLabel(" Total Rain Time ", SwingConstants.CENTER);
        JLabel eventAlertLabel = new JLabel(" Last Event To Occur ", SwingConstants.CENTER);

        //Create font for numbers
        Font numbersFont = new Font("Verdana", Font.PLAIN, 20);

        //Set the layout, what size info panels should be, and default close operation
        setLayout(new FlowLayout());
        Dimension metricDimensions = new Dimension();
        if (HomeSimSystem.dimension.getWidth() < 1600) {
            metricDimensions.setSize((int) HomeSimSystem.dimension.getWidth() / 4 - 5, 75);
        } else {
            metricDimensions.setSize(400, 75);
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add a key listener to the frame and set the menu bar
        addKeyListener(this);
        setJMenuBar(mainBar);

        //Add menu 1, its menu items, and the required action listeners
        mainBar.add(menu1);
        menu1.add(viewConfig);
        menu1.add(exit);
        viewConfig.addActionListener(this);
        exit.addActionListener(this);

        //Add menu 2, its menu items, and the required action listeners
        mainBar.add(menu2);
        menu2.add(run);
        menu2.add(pause);
        menu2.add(showHideInfo);
        menu2.add(changeSimSpeed);
        run.addActionListener(this);
        pause.addActionListener(this);
        showHideInfo.addActionListener(this);
        changeSimSpeed.addActionListener(this);

        //Add menu 3, its menu items, and the required action listeners
        mainBar.add(menu3);
        menu3.add(about);
        menu3.add(userGuide);
        menu3.add(viewSimMetrics);
        about.addActionListener(this);
        userGuide.addActionListener(this);
        viewSimMetrics.addActionListener(this);


        //Add the time panel and set the layout
        add(timePanel);
        timeLabel.setFont(labelFont);
        timeNumber.setFont(numbersFont);
        timePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        timePanel.setPreferredSize(metricDimensions);
        timePanel.add(timeLabel, BorderLayout.NORTH);
        timePanel.add(timeNumber, BorderLayout.CENTER);

        //Add the sunlight panel and set the layout
        add(sunlightPanel);
        sunlightLabel.setFont(labelFont);
        sunlightNumber.setFont(numbersFont);
        sunlightPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        sunlightPanel.setPreferredSize(metricDimensions);
        sunlightPanel.add(sunlightLabel, BorderLayout.NORTH);
        sunlightPanel.add(sunlightNumber, BorderLayout.CENTER);

        //Add the temperature panel and set the layout
        add(temperaturePanel);
        temperatureLabel.setFont(labelFont);
        temperatureNumber.setFont(numbersFont);
        temperaturePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        temperaturePanel.setPreferredSize(metricDimensions);
        temperaturePanel.add(temperatureLabel, BorderLayout.NORTH);
        temperaturePanel.add(temperatureNumber, BorderLayout.CENTER);

        //Add the soil moisture panel and set the layout
        add(soilMoisturePanel);
        soilMoistureLabel.setFont(labelFont);
        soilMoistureNumber.setFont(numbersFont);
        soilMoisturePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        soilMoisturePanel.setPreferredSize(metricDimensions);
        soilMoisturePanel.add(soilMoistureLabel, BorderLayout.NORTH);
        soilMoisturePanel.add(soilMoistureNumber, BorderLayout.CENTER);

        //Add the electricity panel and set the layout
        add(electricityPanel);
        electricityLabel.setFont(labelFont);
        electricityNumber.setFont(numbersFont);
        electricityPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        electricityPanel.setPreferredSize(metricDimensions);
        electricityPanel.add(electricityLabel, BorderLayout.NORTH);
        electricityPanel.add(electricityNumber, BorderLayout.CENTER);


        //Add the water panel and set the layout
        add(waterPanel);
        waterLabel.setFont(labelFont);
        waterNumber.setFont(numbersFont);
        waterPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        waterPanel.setPreferredSize(metricDimensions);
        waterPanel.add(waterLabel, BorderLayout.NORTH);
        waterPanel.add(waterNumber, BorderLayout.CENTER);


        //Add the rain time panel and set the layout
        add(rainTimePanel);
        rainTimeLabel.setFont(labelFont);
        rainTimeNumber.setFont(numbersFont);
        rainTimePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        rainTimePanel.setPreferredSize(metricDimensions);
        rainTimePanel.add(rainTimeLabel, BorderLayout.NORTH);
        rainTimePanel.add(rainTimeNumber, BorderLayout.CENTER);


        //Add the event alert panel and set the layout
        add(eventAlertPanel);
        eventAlertLabel.setFont(labelFont);
        eventAlertText.setFont(numbersFont);
        eventAlertPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        eventAlertPanel.setPreferredSize(metricDimensions);
        eventAlertPanel.add(eventAlertLabel, BorderLayout.NORTH);
        eventAlertPanel.add(eventAlertText, BorderLayout.CENTER);

        //Set the window to be visible
        setVisible(true);
    }


    @Override
    //Set events for button presses
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        //View the loaded config data
        if (source == viewConfig) {
            HomeSimSystem.displayConfigData();
        }

        //Exit the program
        if (source == exit) {
            System.exit(0);
        }

        //Toggle the visibility state of the non essential metrics
        if (source == showHideInfo && showHideCounter % 2 == 0) {
            HomeSimSystem.disableMetricsDisplay();
            showHideCounter++;
        } else {
            HomeSimSystem.enableMetricsDisplay();
            showHideCounter++;
        }

        //Run the simulator from the current time
        if (source == run) {
            HomeSimSystem.run();
        }

        //Pause the simulator at the current time
        if (source == pause) {
            HomeSimSystem.pause();
        }

        //Change the simSpeed to the specified speed
        if (source == changeSimSpeed) {
            HomeSimSystem.setSpeed();
        }

        //Display the about information
        if (source == about) {
            JMenuDialogues.displayAbout();
        }

        //Display the user guide
        if (source == userGuide) {
            JMenuDialogues.displayUserGuide();
        }

        //Display information about the simulation speed
        if (source == viewSimMetrics) {
            HomeSimSystem.displaySimSpeed();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();

        //Run the simulator from the current time
        if (c == '1') {
            HomeSimSystem.run();
        }

        //Pause the simulator at the current time
        if (c == '2') {
            HomeSimSystem.pause();
        }

        //Toggle the visibility state of the non essential metrics
        if (c == '3' && showHideCounter % 2 == 0) {
            HomeSimSystem.disableMetricsDisplay();
            showHideCounter++;
        } else {
            HomeSimSystem.enableMetricsDisplay();
            showHideCounter++;
        }

        //Change the simSpeed to the specified speed
        if (c == '4') {
            HomeSimSystem.setSpeed();
        }

        //Display the about information
        if (c == '5') {
            JMenuDialogues.displayAbout();
        }

        //Display the user guide
        if (c == '6') {
            JMenuDialogues.displayUserGuide();
        }

        //Display information about the simulation speed
        if (c == '7') {
            HomeSimSystem.displaySimSpeed();
        }

        //View the loaded config data
        if (c == '8') {
            HomeSimSystem.displayConfigData();
        }

        //Exit the program
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