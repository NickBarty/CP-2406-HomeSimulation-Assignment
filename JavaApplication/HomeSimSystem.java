import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class HomeSimSystem {
    //Home Sim System Variables
    private static String csvFile = "config.csv";
    private static String line;
    private static int simSpeed;
    private static int count = 0;
    private static int HOUR_COUNTER = 5;
    private static int MINUTE_COUNTER = 0;
    private static int END_TIME = 1439;

    //Creates a timer, pause logic variable, and GUI frame
    private static Timer timer;
    private static Boolean stopped = true;
    static Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    private static SimulatorLayout simFrame = new SimulatorLayout();

    //Creates array lists to store all labels
    private static ArrayList<JLabel> applianceLabels = new ArrayList<>();
    private static ArrayList<JLabel> waterApplianceLabels = new ArrayList<>();
    private static ArrayList<JLabel> fixtureLabels = new ArrayList<>();
    private static ArrayList<JLabel> waterFixtureLabels = new ArrayList<>();
    private static ArrayList<ArrayList<JLabel>> roomLabelsList = new ArrayList<>();

    //Loads all objects
    private static ArrayList<HouseObjects> appliances = new ArrayList<>();
    private static ArrayList<HouseObjects> fixtures = new ArrayList<>();
    private static ArrayList<HouseWaterObjects> waterFixtures = new ArrayList<>();
    private static ArrayList<HouseWaterObjects> waterAppliances = new ArrayList<>();
    private static ArrayList<Room> rooms = new ArrayList<>();

    //Loads config & trigger values
    private static ArrayList<Integer> configValues = new ArrayList<>();
    private static ArrayList<Integer> triggerValues = new ArrayList<>();

    //makes calls to load data, set the simSpeed and build the room panels
    public static void main(String[] args) {
        loadAllData();
        simSpeed = configValues.get(8);
        buildRoomPanels();
    }

    //Creates a panel for each room and set its size
    private static void buildRoomPanels() {
        //Create JPanels with a layout and jLabels for each room
        for (Room roomName : rooms) {
            //Create JPanel for the current room and set its size
            JPanel panel = new JPanel(new BorderLayout());
            if (dimension.getWidth() < 1600) {
                Dimension panelDimension = new Dimension((int) dimension.getWidth() / 2 - 5, (int) dimension.getHeight() / rooms.size() + 50);
                panel.setPreferredSize(panelDimension);
            } else {
                Dimension panelDimension = new Dimension(805, 165);
                panel.setPreferredSize(panelDimension);
            }

            //Create two more panels that will hold all the JLabels
            JPanel namePanel = new JPanel();
            JPanel infoPanel = new JPanel();

            //Add created panels to room panel
            panel.add(namePanel, BorderLayout.NORTH);
            panel.add(infoPanel, BorderLayout.CENTER);

            //Set the layout of the room panel
            infoPanel.setLayout(new GridLayout(0, 1, 10, 5));

            //Create a JLabel and set its text to the room name, then add it to the room panel
            JLabel roomLabel = new JLabel(" " + roomName.getCurrentRoom() + " ", SwingConstants.CENTER);
            roomLabel.setFont(simFrame.labelFont);
            namePanel.add(roomLabel, BorderLayout.NORTH);

            //Go through every potential object in the house and if its room is the current room panel add it
            for (HouseObjects houseObject : appliances) {
                if (houseObject.getRoom().equals(roomName.getCurrentRoom())) {
                    applianceLabels.add(addRoomLabel(infoPanel));
                }
            }
            for (HouseObjects houseObject : fixtures) {
                if (houseObject.getRoom().equals(roomName.getCurrentRoom())) {
                    fixtureLabels.add(addRoomLabel(infoPanel));
                }
            }
            for (HouseWaterObjects houseWaterObject : waterAppliances) {
                if (houseWaterObject.getRoom().equals(roomName.getCurrentRoom())) {
                    waterApplianceLabels.add(addRoomLabel(infoPanel));
                }
            }
            for (HouseWaterObjects houseWaterObject : waterFixtures) {
                if (houseWaterObject.getRoom().equals(roomName.getCurrentRoom())) {
                    waterFixtureLabels.add(addRoomLabel(infoPanel));
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

            //Set the border of the current room panel and then add it to the main frame
            panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            simFrame.add(panel);
        }
        //Add all the created labels to one list
        roomLabelsList.add(applianceLabels);
        roomLabelsList.add(waterApplianceLabels);
        roomLabelsList.add(fixtureLabels);
        roomLabelsList.add(waterFixtureLabels);

        //Set the event alert and electricity panels to have a default background color
        simFrame.eventAlertPanel.setBackground(Color.GREEN);
        simFrame.eventAlertText.setText("N/A");
        simFrame.electricityPanel.setBackground(new Color(0, 255, 0));
    }

    //Creates and add a jLabel with parameters set to the passed in JPanel then return that label
    private static JLabel addRoomLabel(JPanel jpanel) {
        JLabel jLabel = new JLabel();
        jLabel.setOpaque(true);
        jpanel.add(jLabel);
        jLabel.setFont(simFrame.smallInfoFont);
        return jLabel;
    }

    //Sets necessary variables for main loop and then runs main loop
    private static void runSimulation() {
        //Create house object based on config parameters
        House house = new House(configValues.get(0), configValues.get(1), configValues.get(2), configValues.get(3), configValues.get(4));

        //Initialise and set variables used in timer loop
        final String[] meridian = new String[1];
        final String[] message = new String[1];
        final String[] infoMsg = new String[1];
        final String[] objectTypeString = {""};
        boolean sizeCheck = false;

        //Create string array with strings of the types of objects
        String[] objectStrings = new String[4];
        objectStrings[0] = "Appliance - ";
        objectStrings[1] = "Water Appliance - ";
        objectStrings[2] = "Fixture - ";
        objectStrings[3] = "Water Fixture - ";

        //If the screen width is less than 1600px, set boolean value so the object types are not displayed
        if (dimension.getWidth() < 1600) {
            sizeCheck = true;
        }
        boolean finalSizeCheck = sizeCheck;

        //Run main loop
        ActionListener taskPerformer = e -> {

            //Set all objects in the house to be off
            for (HouseObjects fixture : fixtures) {
                fixture.setIsOn(false);
            }
            for (HouseWaterObjects waterFixture : waterFixtures) {
                waterFixture.setIsOn(false);
            }
            for (HouseObjects appliance : appliances) {
                appliance.setIsOn(false);
            }
            for (HouseWaterObjects waterAppliance : waterAppliances) {
                waterAppliance.setIsOn(false);
            }

            //Advance Time By 1 Minute
            MINUTE_COUNTER++;

            //Calculate time in 12 hour time
            if (MINUTE_COUNTER % 60 < 10) {
                message[0] = "0";
            } else message[0] = "";
            if (HOUR_COUNTER % 13 == 0) {
                HOUR_COUNTER = 1;
            }
            if (MINUTE_COUNTER > 420 && MINUTE_COUNTER < 1140) {
                meridian[0] = "PM";
            } else meridian[0] = "AM";
            if (MINUTE_COUNTER % 60 == 0) {
                ++HOUR_COUNTER;
            }

            //Run the house condition statements and tests
            checkConditions(house);

            //Only run tests for the first 60 minutes so they are not run for the entire time and reduce performance
            if (MINUTE_COUNTER < 60) {
                runTests();
            }

            //Set the JLabel text of every appliance
            for (int i = 0; i < applianceLabels.size(); i++) {
                if (!finalSizeCheck) {
                    objectTypeString[0] = objectStrings[0];
                }
                infoMsg[0] = String.format(" %s%s | On = %s | Mins On = %s | Watts Used = %.0f ",
                        objectTypeString[0],
                        appliances.get(i).getName(),
                        appliances.get(i).getIsOn(),
                        appliances.get(i).getOnDuration(),
                        appliances.get(i).getWattsPerMin() * appliances.get(i).getOnDuration());
                applianceLabels.get(i).setText(infoMsg[0]);
            }
            //Set the JLabel text of every water appliance
            for (int i = 0; i < waterApplianceLabels.size(); i++) {
                if (!finalSizeCheck) {
                    objectTypeString[0] = objectStrings[1];
                }
                infoMsg[0] = String.format(" %s%s | On = %s | Mins On = %s | Watts Used = %.0f | Liters Used = %.2f ",
                        objectTypeString[0],
                        waterAppliances.get(i).getName(),
                        waterAppliances.get(i).getIsOn(),
                        waterAppliances.get(i).getOnDuration(),
                        waterAppliances.get(i).getWattsPerMin() * waterAppliances.get(i).getOnDuration(),
                        waterAppliances.get(i).getLitersPerMin() * waterAppliances.get(i).getOnDuration());
                waterApplianceLabels.get(i).setText(infoMsg[0]);
            }
            //Set the JLabel text of every fixture
            for (int i = 0; i < fixtureLabels.size(); i++) {
                if (!finalSizeCheck) {
                    objectTypeString[0] = objectStrings[2];
                }
                infoMsg[0] = String.format(" %s%s | On = %s | Mins On = %s | Watts Used = %.0f ",
                        objectTypeString[0],
                        fixtures.get(i).getName(),
                        fixtures.get(i).getIsOn(),
                        fixtures.get(i).getOnDuration(),
                        fixtures.get(i).getWattsPerMin() * fixtures.get(i).getOnDuration());
                fixtureLabels.get(i).setText(infoMsg[0]);
            }
            //Set the JLabel text of every water fixture
            for (int i = 0; i < waterFixtureLabels.size(); i++) {
                if (!finalSizeCheck) {
                    objectTypeString[0] = objectStrings[3];
                }
                infoMsg[0] = String.format(" %s%s | On = %s | Mins On = %s | Watts Used = %.0f | Liters Used = %.2f ",
                        objectTypeString[0],
                        waterFixtures.get(i).getName(),
                        waterFixtures.get(i).getIsOn(),
                        waterFixtures.get(i).getOnDuration(),
                        waterFixtures.get(i).getWattsPerMin() * waterFixtures.get(i).getOnDuration(),
                        waterFixtures.get(i).getLitersPerMin() * waterFixtures.get(i).getOnDuration());
                waterFixtureLabels.get(i).setText(infoMsg[0]);
            }

            //Display house metrics
            simFrame.sunlightNumber.setText(Integer.toString(house.getCurrentSunlight()) + "%");
            simFrame.temperatureNumber.setText(Integer.toString(house.getCurrentTemp()) + "°");
            simFrame.soilMoistureNumber.setText(Integer.toString(house.getSoilMoisture()) + "%");
            simFrame.timeNumber.setText(HOUR_COUNTER + ":" + message[0] + MINUTE_COUNTER % 60 + " " + meridian[0]);

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

            //Set colour of the sunlight panel based on the sunlight level
            simFrame.sunlightPanel.setBackground(new Color(225, 225, 225 - house.getCurrentSunlight() * 2));

            //Set the cold color of the temperature panel based on the temperature
            if (house.getCurrentTemp() <= 25) {
                simFrame.temperaturePanel.setBackground(new Color(0, 75 + house.getCurrentTemp() * 5, 255));
            }

            //Set the hot color of the temperature panel based on the temperature
            if (house.getCurrentTemp() > 25) {
                simFrame.temperaturePanel.setBackground(new Color(255, 255 - house.getCurrentTemp() * 5, 0));
            }

            //Set the color of the soil moisture panel based on the soil moisture
            simFrame.soilMoisturePanel.setBackground(new Color(255 - house.getSoilMoisture() * 2, 255, 255 - house.getSoilMoisture() * 2));

            //Display the non-essential metrics values
            displayElectricityCost();
            displayWaterUsage();
            displayTotalRainDuration(house);

            //Prevent simulation from running longer than 'one day'
            count++;
            if (count >= END_TIME) {
                timer.stop();
            }
        };
        //Create timer for main loop and start it
        timer = new Timer(simSpeed, taskPerformer);
        timer.start();
    }

    //Turns objects on or off based on the set conditions
    private static void checkConditions(House house) {
        //Trigger random events
        triggerRandomEvents(house);

        //Set sunlight based on current house values
        setSunlight(house);

        //Set soil moisture every specified minutes if sunlight is below specified value (config value based)
        if (MINUTE_COUNTER % configValues.get(6) == 0) {
            setSoilMoisture(house, triggerValues.get(6));
        }

        //Set temperature every specified minutes (from config file) based on current time
        if (MINUTE_COUNTER % configValues.get(5) == 0) {
            setTemperature(house);
        }

        //Stop Raining if its been raining for longer than set rain duration
        if (house.isRaining() && MINUTE_COUNTER > (house.getRainStart() + house.getRainEnd())) {
            house.setRaining(false);
        }

        //Increase soil moisture by 1 when raining every number of specified (trigger value) minutes
        if (house.isRaining() && house.getSoilMoisture() < 100 && MINUTE_COUNTER % triggerValues.get(5) == 0) {
            house.setSoilMoisture(house.getSoilMoisture() + 1);
        }

        //Reduce sunlight during rain, down to specified (trigger value) light level
        if (house.isRaining() && house.getCurrentSunlight() > triggerValues.get(0)) {
            house.setCurrentSunlight(house.getCurrentSunlight() - 2);
        }

        //Turn sprinklers on if sunlight isn't max, it't not raining and soil moisture is not max
        for (HouseWaterObjects waterFixture : waterFixtures) {
            if (waterFixture.toString().contains("Sprinklers") && !house.isRaining() && house.getCurrentSunlight() != house.getMAX_SUNLIGHT() && house.getSoilMoisture() < 100) {
                turnHouseObjectOn(waterFixture);
                house.setSoilMoisture(house.getSoilMoisture() + 1);
            }
        }

        //Turn on some fixtures based on trigger values
        for (HouseObjects fixture : fixtures) {
            //Turn lights on when below specified trigger sunlight level
            if (fixture.toString().contains("Light") && house.getCurrentSunlight() < triggerValues.get(4)) {
                turnHouseObjectOn(fixture);
            }
            //Turn aircon on above a specified trigger value
            if (fixture.toString().contains("Aircon") && house.getCurrentTemp() > triggerValues.get(3)) {
                turnHouseObjectOn(fixture);
            }
            //Turn fans on between specified trigger values
            if (fixture.toString().contains("Fan") && house.getCurrentTemp() > triggerValues.get(1) && house.getCurrentTemp() < triggerValues.get(2)) {
                turnHouseObjectOn(fixture);
            }
        }
    }

    //Turns objects on that aren't normally used to show they work
    private static void runTests() {
        /*
        =========================
        RUN TESTS USING FIXTURES & APPLIANCES
        =========================
        */

        //Run un-used Appliances
        for (HouseObjects appliance : appliances) {
            //Run TV for 60 mins
            if (appliance.toString().contains("TV") && appliance.getOnDuration() < 60) {
                turnHouseObjectOn(appliance);
            }
            //Run Microwave for 20 mins
            if (appliance.toString().contains("Microwave") && appliance.getOnDuration() < 20) {
                turnHouseObjectOn(appliance);
            }
            //Run Oven for 60 mins
            if (appliance.toString().contains("Oven") && appliance.getOnDuration() < 60) {
                turnHouseObjectOn(appliance);
            }
            //Run Car for 60 mins
            if (appliance.toString().contains("Car") && appliance.getOnDuration() < 60) {
                turnHouseObjectOn(appliance);
            }
        }

        //Run un-used Water Appliances
        for (HouseWaterObjects waterAppliance : waterAppliances) {
            //Run Jug for 20 mins
            if (waterAppliance.toString().contains("Jug") && waterAppliance.getOnDuration() < 20) {
                turnHouseObjectOn(waterAppliance);
            }
            //Run Coffee Machine for 20 mins
            if (waterAppliance.toString().contains("Coffee Machine") && waterAppliance.getOnDuration() < 20) {
                turnHouseObjectOn(waterAppliance);
            }
        }

        //Run un-used Fixtures
        for (HouseObjects fixture : fixtures) {
            //Run garage door once (2 mins)
            if (fixture.toString().contains("Garage Door") && fixture.getOnDuration() < 2) {
                turnHouseObjectOn(fixture);
            }
        }

        /*
        =========================
        FINISHED TESTS RUNNING FIXTURES & APPLIANCES
        =========================
        */
    }

    //Takes an object, turns it on and its its on duration to 1 above its current
    private static void turnHouseObjectOn(HouseObjects object) {
        object.setIsOn(true);
        object.setOnDuration(object.getOnDuration() + 1);
    }

    //Sets the sunlight based on the current time
    private static void setSunlight(House house) {
        //Set sunlight in the morning
        if (house.getCurrentSunlight() < 100 && MINUTE_COUNTER > 20 && MINUTE_COUNTER < 720) {
            house.setCurrentSunlight(house.getCurrentSunlight() + 1);
        }

        //Set sunlight in the afternoon
        if (MINUTE_COUNTER > 720 && house.getCurrentSunlight() > house.getMIN_SUNLIGHT()) {
            house.setCurrentSunlight(house.getCurrentSunlight() - 1);
        }
    }

    //Sets the temperature based on the current time and temperature
    private static void setTemperature(House house) {
        //Create a random number to fluctuate temperature with
        double x = Math.random();
        //6 AM - 5 PM increase temp
        if (MINUTE_COUNTER >= 60 && MINUTE_COUNTER < 720 && house.getCurrentTemp() < house.getMaxTemp()) {
            house.setCurrentTemp(house.getCurrentTemp() + 1);
        }

        //5 PM onwards lower temp
        if (MINUTE_COUNTER >= 720 && house.getCurrentTemp() > house.getMinTemp()) {
            house.setCurrentTemp(house.getCurrentTemp() - 1);
        }

        //Reduce temperature to max slowly if heatwave has taken it over max
        if (house.getCurrentTemp() > house.getMaxTemp()) {
            house.setCurrentTemp(house.getCurrentTemp() - 1);
        }

        //Fluctuate temperature up or down
        if (x <= 0.45 && house.getCurrentTemp() < house.getMaxTemp()) {
            house.setCurrentTemp(house.getCurrentTemp() + 1);
        } else {
            house.setCurrentTemp(house.getCurrentTemp() - 1);
        }
    }

    //Reduces the current soil moisture if sunlight is above a triggering level
    private static void setSoilMoisture(House house, int sunlightLevelChange) {
        if (house.getCurrentSunlight() > sunlightLevelChange && house.getSoilMoisture() > 0) {
            house.setSoilMoisture(house.getSoilMoisture() - 1);
        }
    }

    //Makes random events occur
    private static void triggerRandomEvents(House house) {
        //Initialise and set variables
        int randomness = configValues.get(11);
        int rainMin = configValues.get(12);
        int rainMax = configValues.get(13);
        double randNum = Math.random();
        double randChooseNum = Math.random();
        String message;
        String infoMessage;
        String meridian;

        //12 Hour time conversion
        if (MINUTE_COUNTER % 60 < 10) {
            message = "0";
        } else message = "";
        if (MINUTE_COUNTER > 420 && MINUTE_COUNTER < 1140) {
            meridian = "PM";
        } else meridian = "AM";

        //If random number less than or is 0.4 attempt to make it rain
        if (randChooseNum <= 0.4) {
            //Start Raining & set needed values
            if (!house.isRaining() && randNum <= (randomness / 100.00) - 0.007) {
                house.setRaining(true);
                house.setRainEnd(Math.random() * ((rainMax - rainMin) + 1) + rainMin);
                house.setRainStart(MINUTE_COUNTER);
                house.setTotalRainDuration(house.getTotalRainDuration() + house.getRainEnd());
                if (house.getCurrentTemp() > house.getMinTemp()) {
                    house.setCurrentTemp(house.getCurrentTemp() - 5);
                }
                infoMessage = String.format("Raining (%.0f Mins): %d:%s%d %s", house.getRainEnd(), HOUR_COUNTER, message, MINUTE_COUNTER % 60, meridian);
                simFrame.eventAlertText.setText(infoMessage);
                simFrame.eventAlertPanel.setBackground(Color.CYAN);
            }
        }

        //If random number is above 0.4 attempt to make a heatwave
        //Heatwaves only occur between 8AM (180) and 5PM (720)
        if (!house.isRaining() && randChooseNum > 0.4 && MINUTE_COUNTER >= 180 && MINUTE_COUNTER <= 720 && randNum <= (randomness / 100.00 - 0.007)) {
            simFrame.eventAlertText.setText("Heat wave: " + HOUR_COUNTER + ":" + message + MINUTE_COUNTER % 60 + " " + meridian);
            simFrame.eventAlertPanel.setBackground(Color.ORANGE);
            house.setCurrentTemp(house.getCurrentTemp() + 10);
        }
    }

    //Displays total electricity cost for the day
    private static void displayElectricityCost() {
        //Initialise and set variables
        int centsPerKw = configValues.get(14);
        double totalWatts = 0;
        double costPerKw = centsPerKw / 100.0;
        double cost;

        //Calculate the sum of watts used for every object
        for (HouseObjects appliance : appliances) {
            totalWatts += appliance.getWattsPerMin() * appliance.getOnDuration();
        }
        for (HouseWaterObjects waterAppliance : waterAppliances) {
            totalWatts += waterAppliance.getWattsPerMin() * waterAppliance.getOnDuration();
        }
        for (HouseObjects fixture : fixtures) {
            totalWatts += fixture.getWattsPerMin() * fixture.getOnDuration();
        }
        for (HouseWaterObjects waterFixture : waterFixtures) {
            totalWatts += waterFixture.getWattsPerMin() * waterFixture.getOnDuration();
        }

        //Calculate current kW value and cost
        double totalKw = totalWatts / 1000.00;
        cost = totalKw * costPerKw;
        String message = String.format(" %.2f Kw | $%.2f ", totalKw, cost);

        //Display values
        simFrame.electricityNumber.setText(message);

        //Set color of the electricity panel based on cost
        int colorCost = (int) cost;
        if (colorCost >= 8) {
            colorCost = 7;
        }
        simFrame.electricityPanel.setBackground(new Color(colorCost * 35, 255 - colorCost * 35, 2));
    }

    //Displays total water usage for the day
    private static void displayWaterUsage() {
        //Initialise and set variable
        double totalWaterUsage = 0;

        //Calculate the sum of liters of water used for every water fixture/appliance
        for (HouseWaterObjects waterAppliance : waterAppliances) {
            totalWaterUsage += waterAppliance.getLitersPerMin() * waterAppliance.getOnDuration();
        }
        for (HouseWaterObjects waterFixture : waterFixtures) {
            totalWaterUsage += waterFixture.getLitersPerMin() * waterFixture.getOnDuration();
        }

        //Display current value of total liters used for the day
        String message = String.format(" %.2fL", totalWaterUsage);
        simFrame.waterNumber.setText(message);

        //Set color of the water panel based on liters used
        int waterUsageColor = (int) totalWaterUsage;
        if (waterUsageColor > 255) {
            waterUsageColor = 255;
        }
        simFrame.waterPanel.setBackground(new Color(255 - waterUsageColor, 255, 255));
    }

    //Displays total duration of rain for day
    private static void displayTotalRainDuration(House house) {
        //Set the rain counter (used for smooth numbering in GUI)
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

        //Set color of the water panel based on liters used
        int rainColor = 0;
        if (house.getRainCounter() > 255) {
            simFrame.rainTimePanel.setBackground(new Color(rainColor, 255, 255));
        } else {
            simFrame.rainTimePanel.setBackground(new Color(255 - house.getRainCounter(), 255, 255));
        }
    }

    //Displays config values
    static void displayConfigData() {
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
            //Create JLabel with created html string and display it
            JLabel message = new JLabel(sb.toString());
            message.setFont(new Font("Verdana", Font.PLAIN, 20));
            JOptionPane.showMessageDialog(null, message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Load all the objects, rooms, and config/trigger values
    private static void loadAllData() {
        //Try load the csv file
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            //Read each line of the csv file
            while ((line = br.readLine()) != null) {
                String[] property = line.split(",");

                //Add appliances to appliance list
                if (line.startsWith("Appliance")) {
                    HouseObjects houseObject = new HouseObjects(property[1], Double.parseDouble(property[2]), property[3]);
                    appliances.add(houseObject);
                }
                //Add fixtures to fixture list
                if (line.startsWith("Fixture")) {
                    HouseObjects houseObject = new HouseObjects(property[1], Double.parseDouble(property[2]), property[3]);
                    fixtures.add(houseObject);
                }
                //Add water appliances to water appliance list
                if (line.startsWith("WaterAppliance")) {
                    HouseWaterObjects houseWaterObject = new HouseWaterObjects(property[1], Integer.parseInt(property[2]), Double.parseDouble(property[3]), property[4]);
                    waterAppliances.add(houseWaterObject);
                }
                //Add water fixture to water fixture list
                if (line.startsWith("WaterFixture")) {
                    HouseWaterObjects houseWaterObject = new HouseWaterObjects(property[1], Integer.parseInt(property[2]), Double.parseDouble(property[3]), property[4]);
                    waterFixtures.add(houseWaterObject);
                }
                //Add rooms to room list
                if (line.startsWith("Room")) {
                    Room room = new Room(property[1]);
                    rooms.add(room);
                }
                //Add config values to config value list
                if (line.startsWith("ConfigValue")) {
                    configValues.add(Integer.parseInt(property[2]));
                }
                //Add trigger values to trigger value list
                if (line.startsWith("TriggerValue")) {
                    triggerValues.add(Integer.parseInt(property[2]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Runs the simulation
    static void run() {
        if (stopped) {
            runSimulation();
            stopped = false;
        } else {
            timer.start();
            simFrame.timePanel.setBackground(Color.WHITE);
        }
    }

    //Pauses the simulation
    static void pause() {
        if (stopped) {
            JOptionPane.showMessageDialog(null, "Simulator must be running to pause");
        } else {
            timer.stop();
            simFrame.timePanel.setBackground(Color.RED);
            stopped = false;
        }
    }

    //Sets the speed of the simulation
    static void setSpeed() {
        if (stopped) {
            simSpeed = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the new simulation speed\n(Number of milliseconds per simulated minute)"));
        } else {
            simSpeed = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the new simulation speed\n(Number of milliseconds per simulated minute)"));
            timer.setDelay(simSpeed);
        }
    }

    //Disables the display of the non essential metrics
    static void disableMetricsDisplay() {
        simFrame.electricityPanel.setVisible(false);
        simFrame.waterPanel.setVisible(false);
        simFrame.rainTimePanel.setVisible(false);
        simFrame.eventAlertPanel.setVisible(false);
    }

    //Enables the display of the non essential metrics
    static void enableMetricsDisplay() {
        simFrame.electricityPanel.setVisible(true);
        simFrame.waterPanel.setVisible(true);
        simFrame.rainTimePanel.setVisible(true);
        simFrame.eventAlertPanel.setVisible(true);
    }

    //Displays information about the simulation speed
    static void displaySimSpeed() {
        //Initialize and set variables used to calculate simulator duration
        double runTime = (simSpeed / 1000.0) * END_TIME;
        double simMinutes = (1000.0 / simSpeed);
        double simHours = 60 / simMinutes;

        //Set and display the html string
        JLabel label = new JLabel(String.format("<html>Simulation speed = <b>%d</b><br>Minutes per second = <b>%.2f</b>" +
                "<br>Seconds per simulated hour = <b>%.2f</b><br>Total run time = <b>%.2f seconds</b></html>", simSpeed, simMinutes, simHours, runTime));
        label.setFont(new Font("Verdana", Font.PLAIN, 30));
        JOptionPane.showMessageDialog(null, label);
    }
}