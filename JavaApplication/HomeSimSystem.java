import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class HomeSimSystem {
    //Home Sim System Variables
    private static String csvFile = "config.csv";
    private static String line;
    private static int count = 0;
    private static int TIME_COUNTER = 0;
    private static int END_TIME = 1439;

    //Create GUI frame, timer and pause logic variable
    public static Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    public static SimulatorLayout simFrame = new SimulatorLayout();
    public static Timer timer;
    public static Boolean stopped = true;

    //Create array lists to store all labels
    public static ArrayList<JLabel> applianceLabels = new ArrayList<>();
    public static ArrayList<JLabel> waterApplianceLabels = new ArrayList<>();
    public static ArrayList<JLabel> fixtureLabels = new ArrayList<>();
    public static ArrayList<JLabel> waterFixtureLabels = new ArrayList<>();
    public static ArrayList<ArrayList<JLabel>> roomLabelsList = new ArrayList<>();

    //Load all objects
    public static ArrayList<Appliances> appliances = loadAppliances();
    public static ArrayList<Fixtures> fixtures = loadFixtures();
    public static ArrayList<WaterFixtures> waterFixtures = loadWaterFixtures();
    public static ArrayList<WaterAppliances> waterAppliances = loadWaterAppliances();
    public static ArrayList<Room> rooms = loadRooms();

    //Load config & trigger values
    public static ArrayList<Integer> configValues = loadValues("ConfigValue");
    public static ArrayList<Integer> triggerValues = loadValues("TriggerValue");


    public static void main(String[] args) {
        buildRoomPanels();
    }

    @SuppressWarnings({"SameParameterValue", "Duplicates"})
    //Give all of the rooms in the house their respective appliances/fixtures
    public static void buildRoomPanels() {
        //Create JPanels with a layout and jLabels for each room
        for (Room roomName : rooms) {
            JPanel panel = new JPanel(new BorderLayout());
            if (dimension.getWidth() < 1600) {
                Dimension panelDimension = new Dimension((int) dimension.getWidth() / 2 - 5, (int) dimension.getHeight() / rooms.size() + 50);
                panel.setPreferredSize(panelDimension);
            } else {
                Dimension panelDimension = new Dimension(805, 165);
                panel.setPreferredSize(panelDimension);

            }
            JPanel namePanel = new JPanel();
            JPanel infoPanel = new JPanel();
            panel.add(namePanel, BorderLayout.NORTH);
            panel.add(infoPanel, BorderLayout.CENTER);
            infoPanel.setLayout(new GridLayout(0, 1, 10, 5));
            JLabel roomLabel = new JLabel(" " + roomName.getCurrentRoom() + " ", SwingConstants.CENTER);
            roomLabel.setFont(simFrame.labelFont);
            namePanel.add(roomLabel, BorderLayout.NORTH);

            for (int i = 0; i < appliances.size() + waterAppliances.size() + fixtures.size() + waterFixtures.size(); i++) {
                if (i < appliances.size() && appliances.get(i).getRoom().equals(roomName.getCurrentRoom())) {
                    addRoomLabel(infoPanel, applianceLabels);
                }
                if (i < waterAppliances.size() && waterAppliances.get(i).getRoom().equals(roomName.getCurrentRoom())) {
                    addRoomLabel(infoPanel, waterApplianceLabels);
                }
                if (i < fixtures.size() && fixtures.get(i).getRoom().equals(roomName.getCurrentRoom())) {
                    addRoomLabel(infoPanel, fixtureLabels);
                }
                if (i < waterFixtures.size() && waterFixtures.get(i).getRoom().equals(roomName.getCurrentRoom())) {
                    addRoomLabel(infoPanel, waterFixtureLabels);
                }
            }

            //Create simulator frame and move it to center of screen (or full screen if screen is too small)
            if (dimension.getWidth() < 1600) {
                simFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            } else {
                if (rooms.size() % 2 == 0) {
                    simFrame.setSize(1650, 200 + (rooms.size() / 2 * 200));
                } else {
                    simFrame.setSize(1650, 200 + (rooms.size() + 1) / 2 * 200);
                }
            }
            int x = (int) ((dimension.getWidth() - simFrame.getWidth()) / 2);
            int y = (int) ((dimension.getHeight() - simFrame.getHeight()) / 2);
            simFrame.setLocation(x, y);

            panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            simFrame.add(panel);
        }
        simFrame.eventAlertPanel.setBackground(Color.GREEN);
        simFrame.eventAlertText.setText("N/A");
        simFrame.electricityPanel.setBackground(new Color(0, 255, 0));
    }

    public static void addRoomLabel(JPanel infoPanel, ArrayList<JLabel> applianceLabels) {
        JLabel jLabel = new JLabel();
        jLabel.setOpaque(true);
        applianceLabels.add(jLabel);
        infoPanel.add(jLabel);
        jLabel.setFont(simFrame.smallInfoFont);
    }

    //Do anything based on the simulator running
    public static void runSimulation() {
        //Create house object based on config parameters
        House house = new House(configValues.get(0), configValues.get(1), configValues.get(2), configValues.get(3), configValues.get(4));

        //Initialise and set variables used in timer loop
        final String[] meridian = new String[1];
        final String[] message = new String[1];
        final int[] startHour = {5};
        int simSpeed = configValues.get(8);

        final String[] infoMsg = new String[1];
        //Loop until start time is less than the end time
        String[] objectStrings = new String[4];
        objectStrings[0] = "Appliance - ";
        objectStrings[1] = "Water Appliance - ";
        objectStrings[2] = "Fixture - ";
        objectStrings[3] = "Water Fixture - ";

        boolean sizeCheck = false;
        if (dimension.getWidth() < 1600) {
            sizeCheck = true;
        }
        final String[] objectTypeString = {""};

        boolean finalSizeCheck = sizeCheck;

        roomLabelsList.add(applianceLabels);
        roomLabelsList.add(waterApplianceLabels);
        roomLabelsList.add(fixtureLabels);
        roomLabelsList.add(waterFixtureLabels);


        ActionListener taskPerformer = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Set all objects in the house to be off
                for (Fixtures fixtures1 : fixtures) {
                    fixtures1.setIsOn(false);
                }
                for (WaterFixtures waterFixtures1 : waterFixtures) {
                    waterFixtures1.setIsOn(false);
                }
                for (Appliances appliances1 : appliances) {
                    appliances1.setIsOn(false);
                }
                for (WaterAppliances waterAppliances1 : waterAppliances) {
                    waterAppliances1.setIsOn(false);
                }

                //Advance Time By 1 Minute
                TIME_COUNTER++;

                //Calculate time in 12 hour time
                if (TIME_COUNTER % 60 < 10) {
                    message[0] = "0";
                } else message[0] = "";
                if (startHour[0] % 13 == 0) {
                    startHour[0] = 1;
                }
                if (TIME_COUNTER > 420 && TIME_COUNTER < 1140) {
                    meridian[0] = "PM";
                } else meridian[0] = "AM";
                if (TIME_COUNTER % 60 == 0) {
                    ++startHour[0];
                }

                //Check all of the house condition statements and change any that need to be changed
                checkConditions(TIME_COUNTER, fixtures, waterFixtures, house, startHour[0]);

                //Go through each object in the house and set the Jlabel text of it
                for (int z = 0; z < applianceLabels.size() + waterApplianceLabels.size() + fixtureLabels.size() + waterFixtureLabels.size(); z++) {
                    //Set for appliances
                    if (z < applianceLabels.size()) {
                        if (!finalSizeCheck) {
                            objectTypeString[0] = objectStrings[0];
                        }
                        infoMsg[0] = String.format(" %s%s | On = %s | Mins On = %s | Watts Used = %.0f ",
                                objectTypeString[0],
                                appliances.get(z).getApplianceName(),
                                appliances.get(z).getIsOn(),
                                appliances.get(z).getOnDuration(),
                                appliances.get(z).getWattsPerMin() * appliances.get(z).getOnDuration());
                        applianceLabels.get(z).setText(infoMsg[0]);
                    }
                    //Set for water appliances
                    if (z < waterApplianceLabels.size()) {
                        if (!finalSizeCheck) {
                            objectTypeString[0] = objectStrings[1];
                        }
                        infoMsg[0] = String.format(" %s%s | On = %s | Mins On = %s | Watts Used = %.0f | Liters Used = %.2f ",
                                objectTypeString[0],
                                waterAppliances.get(z).getApplianceName(),
                                waterAppliances.get(z).getIsOn(),
                                waterAppliances.get(z).getOnDuration(),
                                waterAppliances.get(z).getWattsPerMin() * waterAppliances.get(z).getOnDuration(),
                                waterAppliances.get(z).getLitersPerMin() * waterAppliances.get(z).getOnDuration());
                        waterApplianceLabels.get(z).setText(infoMsg[0]);
                    }
                    //Set for fixtures
                    if (z < fixtureLabels.size()) {
                        if (!finalSizeCheck) {
                            objectTypeString[0] = objectStrings[2];
                        }
                        infoMsg[0] = String.format(" %s%s | On = %s | Mins On = %s | Watts Used = %.0f ",
                                objectTypeString[0],
                                fixtures.get(z).getFixtureName(),
                                fixtures.get(z).getIsOn(),
                                fixtures.get(z).getOnDuration(),
                                fixtures.get(z).getWattsPerMin() * fixtures.get(z).getOnDuration());
                        fixtureLabels.get(z).setText(infoMsg[0]);
                    }
                    //Set for water fixtures
                    if (z < waterFixtureLabels.size()) {
                        if (!finalSizeCheck) {
                            objectTypeString[0] = objectStrings[3];
                        }
                        infoMsg[0] = String.format(" %s%s | On = %s | Mins On = %s | Watts Used = %.0f | Liters Used = %.2f ",
                                objectTypeString[0],
                                waterFixtures.get(z).getFixtureName(),
                                waterFixtures.get(z).getIsOn(),
                                waterFixtures.get(z).getOnDuration(),
                                waterFixtures.get(z).getWattsPerMin() * waterFixtures.get(z).getOnDuration(),
                                waterFixtures.get(z).getLitersPerMin() * waterFixtures.get(z).getOnDuration());
                        waterFixtureLabels.get(z).setText(infoMsg[0]);
                    }
                }

                //Set indicator highlights for on or off
                for (ArrayList<JLabel> jLabels : roomLabelsList) {
                    for (JLabel jLabel : jLabels) {
                        if (jLabel.getText().contains("true")) {
                            jLabel.setBackground(Color.GREEN);
                        } else {
                            jLabel.setBackground(new Color(255, 190, 200));
                        }
                    }
                }


                /*
                =========================
                RUN TESTS USING FIXTURES & APPLIANCES
                =========================
                */
                if (TIME_COUNTER < 70) {
                    //Run un-used Appliances
                    for (Appliances appliance : appliances) {
                        //Run TV for 60 mins
                        if (appliance.toString().contains("TV") && appliance.getOnDuration() < 60) {
                            turnApplianceOn(appliance);
                        }
                        //Run Microwave for 10 mins
                        if (appliance.toString().contains("Microwave") && appliance.getOnDuration() < 10) {
                            turnApplianceOn(appliance);
                        }
                        //Run Oven for 40 mins
                        if (appliance.toString().contains("Oven") && appliance.getOnDuration() < 40) {
                            turnApplianceOn(appliance);
                        }
                        //Run Car for 60 mins
                        if (appliance.toString().contains("Car") && appliance.getOnDuration() < 60) {
                            turnApplianceOn(appliance);
                        }
                    }
                    //Run un-used Water Appliances
                    for (WaterAppliances waterAppliance : waterAppliances) {
                        //Run Jug for 20 mins
                        if (waterAppliance.toString().contains("Jug") && waterAppliance.getOnDuration() < 20) {
                            turnWaterApplianceOn(waterAppliance);
                        }
                        //Run Coffee Machine for 20 mins
                        if (waterAppliance.toString().contains("Coffee Machine") && waterAppliance.getOnDuration() < 20) {
                            turnWaterApplianceOn(waterAppliance);
                        }
                    }
                    //Run un-used Fixtures
                    for (Fixtures fixture : fixtures) {
                        //Run garage door once (1 min)
                        if (fixture.toString().contains("Garage Door") && fixture.getOnDuration() < 1) {
                            turnFixtureOn(fixture);
                        }
                    }

                }
                /*
                =========================
                FINISHED TESTS RUNNING FIXTURES & APPLIANCES
                =========================
                */

                //Print house metrics based on config for how often to update
                if (TIME_COUNTER % configValues.get(9) == 0) {
                    simFrame.sunlightNumber.setText(Integer.toString(house.getCurrentSunlight()) + "%");
                    simFrame.temperatureNumber.setText(Integer.toString(house.getCurrentTemp()) + "°");
                    simFrame.soilMoistureNumber.setText(Integer.toString(house.getSoilMoisture()) + "%");
                    simFrame.timeNumber.setText(startHour[0] + ":" + message[0] + TIME_COUNTER % 60 + " " + meridian[0]);
                }

                //Set colours for the information metrics
                simFrame.sunlightPanel.setBackground(new Color(225, 225, 225 - house.getCurrentSunlight() * 2));
                if (house.getCurrentTemp() <= 25) {
                    simFrame.temperaturePanel.setBackground(new Color(0, 125 + house.getCurrentTemp() * 4, 255));
                }

                if (house.getCurrentTemp() > 25) {
                    simFrame.temperaturePanel.setBackground(new Color(255, 255 - house.getCurrentTemp() * 4, 0));
                }
                simFrame.soilMoisturePanel.setBackground(new Color(255 - house.getSoilMoisture() * 2, 255, 255 - house.getSoilMoisture() * 2));

                displayElectricityCost(fixtures, waterFixtures, appliances, waterAppliances, configValues.get(14));
                displayWaterUsage(waterFixtures, waterAppliances);
                displayTotalRainDuration(house);
                //Delay program for a specified amount of time

                count++;
                if (count >= END_TIME) {
                    timer.stop();
                }
            }
        };
        timer = new Timer(simSpeed, taskPerformer);
        timer.start();
    }

    public static void run() {
        if (stopped) {
            runSimulation();
            stopped = false;
        } else {
            timer.start();
            simFrame.timePanel.setBackground(Color.WHITE);
        }
    }

    public static void pause() {
        if (stopped) {
            JOptionPane.showMessageDialog(null, "Simulator must be running to pause");
        } else {
            timer.stop();
            simFrame.timePanel.setBackground(Color.RED);
            stopped = false;
        }
    }

    public static void setSpeed() {
        if (stopped) {
            JOptionPane.showMessageDialog(null, "Simulator must be running to change speed");
        } else {
            int speed = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the new simulation speed"));
            timer.setDelay(speed);
        }
    }


    public static void disableMetricsDisplay() {
        simFrame.electricityPanel.setVisible(false);
        simFrame.waterPanel.setVisible(false);
        simFrame.rainTimePanel.setVisible(false);
        simFrame.eventAlertPanel.setVisible(false);
    }

    public static void enableMetricsDisplay() {
        simFrame.electricityPanel.setVisible(true);
        simFrame.waterPanel.setVisible(true);
        simFrame.rainTimePanel.setVisible(true);
        simFrame.eventAlertPanel.setVisible(true);
    }

    public static int displaySimSpeed() {
        int simSpeed = configValues.get(8);

        //Initialize and set variables used to calculate simulator duration
        double runTime = (simSpeed / 1000.0) * END_TIME;
        double simMinutes = (1000.0 / simSpeed);
        double simHours = 60 / simMinutes;

        JLabel label = new JLabel(String.format("<html>Simulation speed from config = <b>%d</b><br>Minutes per second = <b>%.2f</b>" +
                "<br>Seconds per simulated hour = <b>%.2f</b><br>Total run time = <b>%.2f seconds</b></html>", simSpeed, simMinutes, simHours, runTime));
        label.setFont(new Font("Verdana", Font.PLAIN, 30));
        JOptionPane.showMessageDialog(null, label);
        return simSpeed;
    }

    private static void checkConditions(int startTime, ArrayList<Fixtures> fixtures,
                                        ArrayList<WaterFixtures> waterFixtures, House house, int startHour) {
        //Trigger random events based on current time and a percent chance from the config file
        triggerRandomEvents(startTime, house, configValues.get(11), configValues.get(12), configValues.get(13), startHour);

        //Set sunlight based on current house values
        setSunlight(startTime, house);

        //Set soil moisture every specified minutes (from config file) if sunlight is below specified value
        if (startTime % configValues.get(6) == 0) {
            setSoilMoisture(house, triggerValues.get(6));
        }

        //Set temperature every specified minutes (from config file) based on current time
        if (startTime % configValues.get(5) == 0) {
            setTemperature(startTime, house);
        }

        //Stop Raining if its been raining for longer than set rain duration
        if (house.isRaining() && startTime > (house.getRainStart() + house.getRainEnd())) {
            house.setRaining(false);
        }

        //Increase soil moisture by 1 when raining every number of specified (trigger value) minutes
        if (house.isRaining() && house.getSoilMoisture() < 100 && startTime % triggerValues.get(5) == 0) {
            house.setSoilMoisture(house.getSoilMoisture() + 1);
        }

        //Reduce sunlight during rain, down to specified (trigger value) light level
        if (house.isRaining() && house.getCurrentSunlight() > triggerValues.get(0)) {
            house.setCurrentSunlight(house.getCurrentSunlight() - 2);
        }

        for (WaterFixtures waterFixtures1 : waterFixtures) {
            //Turn sprinklers on if sunlight isn't too high, it't not raining and soil moisture is not max
            if (waterFixtures1.toString().contains("Sprinklers") && !house.isRaining() && house.getCurrentSunlight() != house.getMAX_SUNLIGHT() && house.getSoilMoisture() < 100) {
                turnWaterFixtureOn(waterFixtures1);
                house.setSoilMoisture(house.getSoilMoisture() + 1);
            }
        }
        for (Fixtures fixture : fixtures) {
            //Turn lights on when below specified trigger sunlight level
            if (fixture.toString().contains("Light") && house.getCurrentSunlight() < triggerValues.get(4)) {
                turnFixtureOn(fixture);
            }
            //Turn aircon on above a specified trigger value
            if (fixture.toString().contains("Aircon") && house.getCurrentTemp() > triggerValues.get(3)) {
                turnFixtureOn(fixture);
            }
            //Turn fans on between specified trigger values
            if (fixture.toString().contains("Fan") && house.getCurrentTemp() > triggerValues.get(1) && house.getCurrentTemp() < triggerValues.get(2)) {
                turnFixtureOn(fixture);
            }
        }
    }

    private static void turnApplianceOn(Appliances appliance) {
        appliance.setIsOn(true);
        appliance.setOnDuration(appliance.getOnDuration() + 1);
    }

    private static void turnWaterApplianceOn(WaterAppliances waterAppliance) {
        waterAppliance.setIsOn(true);
        waterAppliance.setOnDuration(waterAppliance.getOnDuration() + 1);
    }

    private static void turnFixtureOn(Fixtures fixture) {
        fixture.setIsOn(true);
        fixture.setOnDuration(fixture.getOnDuration() + 1);
    }

    private static void turnWaterFixtureOn(WaterFixtures waterFixture) {
        waterFixture.setIsOn(true);
        waterFixture.setOnDuration(waterFixture.getOnDuration() + 1);
    }

    //Set the sunlight based on the current time
    private static void setSunlight(int startTime, House house) {
        if (house.getCurrentSunlight() < 100 && startTime > 20 && startTime < 720) {
            house.setCurrentSunlight(house.getCurrentSunlight() + 1);
        }
        if (startTime > 720 && house.getCurrentSunlight() > house.getMIN_SUNLIGHT()) {
            house.setCurrentSunlight(house.getCurrentSunlight() - 1);
        }

    }

    //Set the temperature based on the current time and temperature
    private static void setTemperature(int startTime, House house) {
        double x = Math.random();
        //6 AM - 5 PM increase temp
        if (startTime >= 60 && startTime < 720 && house.getCurrentTemp() < house.getMaxTemp()) {
            house.setCurrentTemp(house.getCurrentTemp() + 1);
        }

        //5 PM onwards lower temp
        if (startTime >= 720 && house.getCurrentTemp() > house.getMinTemp()) {
            house.setCurrentTemp(house.getCurrentTemp() - 1);
        }

        //Reduce temperature to max slowly if heatwave has taken it over max
        if (house.getCurrentTemp() > house.getMaxTemp()) {
            house.setCurrentTemp(house.getCurrentTemp() - 1);
        }

        //Fluctuate temperature up or down
        if (x <= 0.4 && house.getCurrentTemp() < house.getMaxTemp()) {
            house.setCurrentTemp(house.getCurrentTemp() + 1);
        }

        if (x > 0.6 && house.getCurrentTemp() > house.getMinTemp()) {
            house.setCurrentTemp(house.getCurrentTemp() - 1);
        }

    }

    //Set the soil moisture based on the start time and how often a change should happen
    private static void setSoilMoisture(House house, int sunlightLevel) {
        //Reduce the current soil moisture if sunlight is above a triggering level
        if (house.getCurrentSunlight() > sunlightLevel && house.getSoilMoisture() > 0) {
            house.setSoilMoisture(house.getSoilMoisture() - 1);
        }
    }

    //Make random events occur
    private static void triggerRandomEvents(int startTime, House house, int randomnessValue, int rainMin,
                                            int rainMax, int startHour) {
        //Initialise and set variables
        double randNum = Math.random();
        double randChooseNum = Math.random();
        String message;
        String infoMessage;
        String meridian;

        if (startTime % 60 < 10) {
            message = "0";
        } else message = "";
        if (startTime > 420 && startTime < 1140) {
            meridian = "PM";
        } else meridian = "AM";

        //If random number less than or is 0.3 attempt to make it rain
        if (randChooseNum <= 0.9) {
            //Start Raining, do events
            if (!house.isRaining() && randNum <= (randomnessValue / 100.00) - 0.007) {
                house.setRaining(true);
                house.setRainEnd(Math.random() * ((rainMax - rainMin) + 1) + rainMin);
                house.setRainStart(startTime);
                house.setTotalRainDuration(house.getTotalRainDuration() + house.getRainEnd());
                if (house.getCurrentTemp() > house.getMinTemp()) {
                    house.setCurrentTemp(house.getCurrentTemp() - 5);
                }
                infoMessage = String.format("Raining (%.0f Mins): %d:%s%d %s", house.getRainEnd(), startHour, message, startTime % 60, meridian);
                simFrame.eventAlertText.setText(infoMessage);
                simFrame.eventAlertPanel.setBackground(Color.CYAN);
            }
        }

        //If random number is above 0.3 attempt to make a heatwave
        //Heatwave only occurs between 8AM (180) and 5PM (720)
        if (!house.isRaining() && randChooseNum > 0.3 && startTime >= 180 && startTime <= 720 && randNum <= (randomnessValue / 100.00 - 0.007)) {
            simFrame.eventAlertText.setText("Heat wave: " + startHour + ":" + message + startTime % 60 + " " + meridian);
            simFrame.eventAlertPanel.setBackground(Color.ORANGE);
            house.setCurrentTemp(house.getCurrentTemp() + 10);
        }
    }

    //Display total electricity cost for the day
    private static void displayElectricityCost
    (ArrayList<Fixtures> fixtures, ArrayList<WaterFixtures> waterFixtures, ArrayList<Appliances> appliances, ArrayList<WaterAppliances> waterAppliances,
     int centsPerKw) {
        //Initialise and set variables
        double totalWatts = 0;
        double costPerKw = centsPerKw / 100.0;
        double cost;

        //Calculate the sum of watts used for every object
        for (Fixtures fixture : fixtures) {
            totalWatts += fixture.getWattsPerMin() * fixture.getOnDuration();
        }
        for (WaterFixtures waterFixture : waterFixtures) {
            totalWatts += waterFixture.getWattsPerMin() * waterFixture.getOnDuration();
        }
        for (Appliances appliance : appliances) {
            totalWatts += appliance.getWattsPerMin() * appliance.getOnDuration();
        }
        for (WaterAppliances waterAppliance : waterAppliances) {
            totalWatts += waterAppliance.getWattsPerMin() * waterAppliance.getOnDuration();
        }

        //Calculate kW value and cost, print values
        double totalKw = totalWatts / 1000.00;
        cost = totalKw * costPerKw;
        String message = String.format(" %.2f Kw | $%.2f ", totalKw, cost);
        simFrame.electricityNumber.setText(message);
        int colorCost = (int) cost;
        if (colorCost >= 8) {
            colorCost = 7;
        }
        simFrame.electricityPanel.setBackground(new Color(colorCost * 35, 255 - colorCost * 35, 2));

    }

    //Display total water usage for the day
    private static void displayWaterUsage
    (ArrayList<WaterFixtures> waterFixtures, ArrayList<WaterAppliances> waterAppliances) {
        //Initialise and set variable
        double totalWaterUsage = 0;

        //Calculate the sum of liters of water used for every water fixture/appliance
        for (WaterFixtures waterFixture : waterFixtures) {
            totalWaterUsage += waterFixture.getLitersPerMin() * waterFixture.getOnDuration();
        }
        for (WaterAppliances waterAppliance : waterAppliances) {
            totalWaterUsage += waterAppliance.getLitersPerMin() * waterAppliance.getOnDuration();
        }

        //Print value of total liters used for the day
        String message = String.format(" %.2fL", totalWaterUsage);
        simFrame.waterNumber.setText(message);
        int waterUsageColor = (int) totalWaterUsage;
        if (waterUsageColor > 255) {
            waterUsageColor = 255;
        }
        simFrame.waterPanel.setBackground(new Color(255 - waterUsageColor, 255, 255));
    }

    //Display total duration of rain for day
    private static void displayTotalRainDuration(House house) {
        if (house.getRainCounter() < house.getTotalRainDuration()) {
            house.setRainCounter(house.getRainCounter() + 1);
        }
        //Print values of hours and mins rain duration
        if (house.getRainCounter() > 60) {
            String message = String.format(" Rained for: %d Hours & %d Mins ", house.getRainCounter() / 60, house.getRainCounter() % 60);
            simFrame.rainTimeNumber.setText(message);
        } else {
            String message = String.format(" Rained for: %d Hours & %d Mins ", 0, house.getRainCounter() % 60);
            simFrame.rainTimeNumber.setText(message);
        }
        int rainColor = 0;
        if (house.getRainCounter() > 255) {
            simFrame.rainTimePanel.setBackground(new Color(rainColor, 255, 255));
        } else {
            simFrame.rainTimePanel.setBackground(new Color(255 - house.getRainCounter(), 255, 255));
        }
    }

    //Display config values
    public static void displayConfigData() {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");

        //Read in config data from csv file and print it
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] property = line.split(",");
                if (line.startsWith("ConfigValue")) {
                    sb.append(property[1]).append(" : <b>").append(property[2]).append("</b><br>");
                }
            }
            sb.append("</html>");
            JLabel message = new JLabel(sb.toString());
            message.setFont(new Font("Verdana", Font.PLAIN, 20));
            JOptionPane.showMessageDialog(null, message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Load config values
    private static ArrayList<Integer> loadValues(String valueToLoad) {
        ArrayList<Integer> valuesList = new ArrayList<>();

        //Read in config data from csv file and add it to array list
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] property = line.split(",");
                if (line.startsWith(valueToLoad)) {
                    valuesList.add(Integer.parseInt(property[2]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Return array list values
        return valuesList;
    }

    //Load room values
    public static ArrayList<Room> loadRooms() {
        ArrayList<Room> roomList = new ArrayList<>();

        //Read in room data from csv file and add it to array list
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] property = line.split(",");
                if (line.startsWith("Room")) {
                    Room room = new Room(property[1]);
                    roomList.add(room);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Return array list of rooms
        return roomList;
    }

    //Load fixture values
    private static ArrayList<Fixtures> loadFixtures() {
        ArrayList<Fixtures> fixtureList = new ArrayList<>();

        //Read in fixture data from csv file and add it to array list
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] property = line.split(",");
                if (line.startsWith("Fixture")) {
                    Fixtures fixture = new Fixtures(property[1], Double.parseDouble(property[2]), property[3]);
                    fixtureList.add(fixture);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Return array list of fixtures
        return fixtureList;
    }

    //Load water fixture values
    private static ArrayList<WaterFixtures> loadWaterFixtures() {
        ArrayList<WaterFixtures> waterFixtureList = new ArrayList<>();

        //Read in water fixture data from csv file and add it to array list
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] property = line.split(",");
                if (line.startsWith("WaterFixture")) {
                    WaterFixtures waterFixture = new WaterFixtures(property[1], Double.parseDouble(property[2]), Double.parseDouble(property[3]), property[4]);
                    waterFixtureList.add(waterFixture);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Return array list of water fixtures
        return waterFixtureList;
    }

    //Load appliance values
    private static ArrayList<Appliances> loadAppliances() {
        ArrayList<Appliances> applianceList = new ArrayList<>();

        //Read in appliance data from csv file and add it to array list
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] property = line.split(",");
                if (line.startsWith("Appliance")) {
                    applianceList.add(new Appliances(property[1], Double.parseDouble(property[2]), property[3]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Return array list of appliances
        return applianceList;
    }

    //Load water appliances
    private static ArrayList<WaterAppliances> loadWaterAppliances() {
        ArrayList<WaterAppliances> waterApplianceList = new ArrayList<>();

        //Read in water appliance data from csv file and add it to array list
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] property = line.split(",");
                if (line.startsWith("WaterAppliance")) {
                    WaterAppliances waterAppliance = new WaterAppliances(property[1], Integer.parseInt(property[2]), Double.parseDouble(property[3]), property[4]);
                    waterApplianceList.add(waterAppliance);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Return array list of water appliances
        return waterApplianceList;
    }
}