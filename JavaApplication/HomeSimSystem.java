import java.awt.*;
import java.io.*;
import java.util.*;

/*
Provides menu for running the program
Provides methods to get total metrics
Provides method to simulate time
Provides methods to load config file properties
*/

public class HomeSimSystem {
    //Home Sim System Variables
    private static String csvFile = "config.csv";
    private static String line;
    private static final int START_TIME = 0;
    private static final int END_TIME = 1439;

    public static void main(String[] args) {
        //Menu system to start simulator, show config variables, or quit
        int userChoice = 0;
        Scanner userInput = new Scanner(System.in);
        while (userChoice != 3) {
            System.out.println("Please enter which choice you want: \n1 - Start Simulation \n2 - Display Config Settings \n3 - Quit");
            userChoice = userInput.nextInt();
            switch (userChoice) {
                case 1:
                    runSimulation(START_TIME, END_TIME);
                    userChoice = 3;
                    break;
                case 2:
                    displayConfigData();
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Invalid Input");
                    break;
            }
        }
        System.out.println("\nThanks for using the home automation simulator!");

    }

    //Do anything based on the simulator running
    @SuppressWarnings("SameParameterValue")
    private static void runSimulation(int startTime, int endTime) {
        //Create simulator frame and move it to center of screen
        SimulatorLayout simFrame = new SimulatorLayout();
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - simFrame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - simFrame.getHeight()) / 2);
        simFrame.setLocation(x, y);

        //Generate objects
        ArrayList<Fixtures> fixtures = loadFixtures();
        ArrayList<WaterFixtures> waterFixtures = loadWaterFixtures();
        ArrayList<Appliances> appliances = loadAppliances();
        ArrayList<WaterAppliances> waterAppliances = loadWaterAppliances();
        ArrayList<Room> rooms = loadRooms();

        //Add all objects to their respective rooms
        populateRooms(fixtures, waterFixtures, appliances, waterAppliances, rooms);

        //Get config values
        ArrayList<Integer> configValues = loadConfigValues();

        //Get trigger values
        ArrayList<Integer> triggerValues = loadTriggerValues();

        //Create house object based on config parameters
        House house = new House(configValues.get(0), configValues.get(1), configValues.get(2), configValues.get(3), configValues.get(4));

        //Initialise and set variables used in loop
        String meridian = "AM";
        String message = "0";
        int startHour = 5;
        int simSpeed = configValues.get(8);

        //Initialize and set variables used to calculate simulator duration
        double runTime = (simSpeed / 1000.0) * endTime;
        double simMinutes = (1000.0 / simSpeed);
        double simHours = 60 / simMinutes;

        System.out.printf("Starting simulator with sim speed of: %d \n\t- %.2f minutes per second. (%.2f seconds per hour) \n\t- Total run time = %.2f seconds\n", simSpeed, simMinutes, simHours, runTime);
        System.out.printf("\nStart Time: %d:%s%d %s \nStart Sunlight: %d%% \nStart Temperature: %d° \nStart Soil Moisture: %d%%\n",
                startHour, message, startTime % 60, meridian, house.getCurrentSunlight(), house.getCurrentTemp(), house.getSoilMoisture());
        System.out.println();

        //Loop until start time is less than the end time
        while (startTime < endTime) {
            try {
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
                startTime++;

                //Calculate time in 12 hour time
                if (startTime % 60 < 10) {
                    message = "0";
                } else message = "";
                if (startHour % 13 == 0) {
                    startHour = 1;
                }
                if (startTime > 420 && startTime < 1140) {
                    meridian = "PM";
                } else meridian = "AM";
                if (startTime % 60 == 0) {
                    ++startHour;
                }

                //Check all of the house condition statements and change any that need to be changed
                checkConditions(startTime, fixtures, waterFixtures, configValues, triggerValues, house);

                /*
                =========================
                START TESTS USING FIXTURES & APPLIANCES
                =========================
                */
                if (startTime < 70) {
                    //Run un-used Appliances
                    for (Appliances appliance : appliances) {
                        //Run TV for 60 mins
                        if (appliance.toString().contains("TV") && appliance.getOnDuration() < 60) {
                            appliance.setIsOn(true);
                            appliance.setOnDuration(appliance.getOnDuration() + 1);
                        }
                        //Run Microwave for 10 mins
                        if (appliance.toString().contains("Microwave") && appliance.getOnDuration() < 10) {
                            appliance.setIsOn(true);
                            appliance.setOnDuration(appliance.getOnDuration() + 1);
                        }
                        //Run Oven for 40 mins
                        if (appliance.toString().contains("Oven") && appliance.getOnDuration() < 40) {
                            appliance.setIsOn(true);
                            appliance.setOnDuration(appliance.getOnDuration() + 1);
                        }
                        //Run Car for 60 mins
                        if (appliance.toString().contains("Car") && appliance.getOnDuration() < 60) {
                            appliance.setIsOn(true);
                            appliance.setOnDuration(appliance.getOnDuration() + 1);
                        }
                    }
                    //Run un-used Water Appliances
                    for (WaterAppliances waterAppliance : waterAppliances) {
                        //Run Jug for 20 mins
                        if (waterAppliance.toString().contains("Jug") && waterAppliance.getOnDuration() < 20) {
                            waterAppliance.setIsOn(true);
                            waterAppliance.setOnDuration(waterAppliance.getOnDuration() + 1);
                        }
                        //Run Coffee Machine for 20 mins
                        if (waterAppliance.toString().contains("Coffee Machine") && waterAppliance.getOnDuration() < 20) {
                            waterAppliance.setIsOn(true);
                            waterAppliance.setOnDuration(waterAppliance.getOnDuration() + 1);
                        }
                    }
                    //Run un-used Fixtures
                    for (Fixtures fixture : fixtures) {
                        //Run garage door once (1 min)
                        if (fixtures.toString().contains("Garage Door") && fixture.getOnDuration() < 1) {
                            fixture.setIsOn(true);
                            fixture.setOnDuration(fixture.getOnDuration() + 1);
                        }
                    }

                }
                /*
                =========================
                FINISHED TESTS USING FIXTURES & APPLIANCES
                =========================
                */

                //Print Status Of all objects based on config for how often to print
                if (configValues.get(7) == 1 && startTime % configValues.get(10) == 0) {
                    System.out.println();
                    for (Room room : rooms) {
                        room.displayObjects();
                    }
                    System.out.println("\n\n\n");
                }

                //Print house metrics based on config for how often to update
                if (startTime % configValues.get(9) == 0) {
                    simFrame.sunlightNumber.setText(Integer.toString(house.getCurrentSunlight()) + "%");
                    simFrame.temperatureNumber.setText(Integer.toString(house.getCurrentTemp()) + "°");
                    simFrame.soilMoistureNumber.setText(Integer.toString(house.getSoilMoisture()) + "%");
                    System.out.printf("\rCurrent Time: %d:%s%d %s | Current Sunlight: %d%% | Current Temperature: %d° | Current Soil Moisture: %d%%",
                            startHour, message, startTime % 60, meridian, house.getCurrentSunlight(), house.getCurrentTemp(), house.getSoilMoisture());
                }

                //Set colours for the information metrics
                simFrame.sunlightPanel.setBackground(new Color(225,225, 225 - house.getCurrentSunlight() * 2));
                if (house.getCurrentTemp() <= 25){
                    simFrame.temperaturePanel.setBackground(new Color(0, 125 + house.getCurrentTemp() *4, 255));
                }

                if (house.getCurrentTemp() > 25){
                    simFrame.temperaturePanel.setBackground(new Color(255, 255 - house.getCurrentTemp() * 4, 0));
                }
                simFrame.soilMoisturePanel.setBackground(new Color(255 - house.getSoilMoisture() * 2, 255, 255 - house.getSoilMoisture() * 2));

                //Pause program for a specified amount of time
                Thread.sleep(simSpeed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //Print status of all objects when simulator finishes if they are not printed while simulator is running
        if (configValues.get(7) != 1) {
            System.out.println();
            for (Room room : rooms) {
                room.displayObjects();
            }
        }

        //Display the total electricity cost, water usage and total rain duration for the simulated day
        displayElectricityCost(fixtures, waterFixtures, appliances, waterAppliances, configValues.get(14));
        displayWaterUsage(waterFixtures, waterAppliances);
        displayTotalRainDuration(house);
    }

    private static void checkConditions(int startTime, ArrayList<Fixtures> fixtures, ArrayList<WaterFixtures> waterFixtures, ArrayList<Integer> configValues, ArrayList<Integer> triggerValues, House house) {
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

        //Trigger random events based on current time and a percent chance from the config file
        triggerRandomEvents(startTime, house, configValues.get(11), configValues.get(12), configValues.get(13));

        //Stop Raining if its been raining for longer than set rain duration
        if (house.isRaining() && startTime > (house.getRainStart() + house.getRainEnd())) {
            house.setRaining(false);
            System.out.println("\nIt has stopped raining\n");
        }

        //Increase soil moisture by 1 when raining every number of specified (trigger value) minutes
        if (house.isRaining() && house.getSoilMoisture() < 100 && startTime % triggerValues.get(5) == 0) {
            house.setSoilMoisture(house.getSoilMoisture() + 1);
        }

        //Reduce sunlight during rain, down to specified (trigger value) light level
        if (house.isRaining() && house.getCurrentSunlight() > triggerValues.get(0)) {
            house.setCurrentSunlight(house.getCurrentSunlight() - 2);
        }

        //Turn sprinklers on if sunlight isn't too high, it't not raining and soil moisture is not max
        if (!house.isRaining() && house.getCurrentSunlight() < house.getMAX_SUNLIGHT() && house.getSoilMoisture() < 100) {
            for (WaterFixtures waterFixtures1 : waterFixtures) {
                if (waterFixtures1.toString().contains("Sprinklers")) {
                    waterFixtures1.setIsOn(true);
                    waterFixtures1.setOnDuration(waterFixtures1.getOnDuration() + 1);
                    house.setSoilMoisture(house.getSoilMoisture() + 1);
                }
            }
        }

        //Turn fans on between specified trigger values
        if (house.getCurrentTemp() > triggerValues.get(1) && house.getCurrentTemp() < triggerValues.get(2)) {
            for (Fixtures fixture : fixtures) {
                if (fixture.toString().contains("Fan")) {
                    fixture.setIsOn(true);
                    fixture.setOnDuration(fixture.getOnDuration() + 1);
                }
            }
        }

        //Turn aircon on above a specified trigger value
        if (house.getCurrentTemp() > triggerValues.get(3)) {
            for (Fixtures fixture : fixtures) {
                if (fixture.toString().contains("Aircon")) {
                    fixture.setIsOn(true);
                    fixture.setOnDuration(fixture.getOnDuration() + 1);
                }
            }
        }

        //Turn lights on when below specified trigger sunlight level
        if (house.getCurrentSunlight() < triggerValues.get(4)) {
            for (Fixtures fixture : fixtures) {
                if (fixture.toString().contains("Light")) {
                    fixture.setIsOn(true);
                    fixture.setOnDuration(fixture.getOnDuration() + 1);
                }
            }
        }
    }

    //Give all of the rooms in the house their respective appliances/fixtures
    private static void populateRooms(ArrayList<Fixtures> fixtures, ArrayList<WaterFixtures> waterFixtures, ArrayList<Appliances> appliances, ArrayList<WaterAppliances> waterAppliances, ArrayList<Room> rooms) {
        for (Fixtures fixture1 : fixtures) {
            for (Room room : rooms) {
                if (fixture1.toString().contains(room.getCurrentRoom())) {
                    room.addFixture(fixture1);
                }
            }
        }
        for (WaterFixtures waterFixture : waterFixtures) {
            for (Room room : rooms) {
                if (waterFixture.toString().contains(room.getCurrentRoom())) {
                    room.addFixture(waterFixture);
                }
            }
        }
        for (Appliances appliance : appliances) {
            for (Room room : rooms) {
                if (appliance.toString().contains(room.getCurrentRoom())) {
                    room.addAppliance(appliance);
                }
            }
        }
        for (WaterAppliances waterAppliance : waterAppliances) {
            for (Room room : rooms) {
                if (waterAppliance.toString().contains(room.getCurrentRoom())) {
                    room.addAppliance(waterAppliance);
                }
            }
        }
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
    private static void triggerRandomEvents(int startTime, House house, int randomnessValue, int rainMin, int rainMax) {
        //Initialise and set variables
        double randNum = Math.random();
        double randChooseNum = Math.random();

        //If random number less than or is 0.3 attempt to make it rain
        if (randChooseNum <= 0.3) {
            //Start Raining, do events
            if (!house.isRaining() && randNum <= (randomnessValue / 100.00) - 0.007) {
                house.setRaining(true);
                house.setRainEnd(Math.random() * ((rainMax - rainMin) + 1) + rainMin);
                house.setRainStart(startTime);
                house.setTotalRainDuration(house.getTotalRainDuration() + house.getRainEnd());
                if (house.getCurrentTemp() > house.getMinTemp()) {
                    house.setCurrentTemp(house.getCurrentTemp() - 5);
                }
                System.out.println(String.format("\nIt has started raining (%.0f Mins)\n", house.getRainEnd()));
            }
        }

        //If random number is above 0.3 attempt to make a heatwave
        //Heatwave only occurs between 8AM (180) and 5PM (720)
        if (!house.isRaining() && randChooseNum > 0.3 && startTime >= 180 && startTime <= 720 && randNum <= (randomnessValue / 100.00 - 0.007)) {
            System.out.println("\nA Heat wave has occurred\n");
            house.setCurrentTemp(house.getCurrentTemp() + 10);
        }
    }

    //Display total electricity cost for the day
    private static void displayElectricityCost(ArrayList<Fixtures> fixtures, ArrayList<WaterFixtures> waterFixtures, ArrayList<Appliances> appliances, ArrayList<WaterAppliances> waterAppliances, int centsPerKw) {
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
        System.out.printf("Total cost of electricity: %.2fkW at $%.2f per kW = $%.2f \n", totalKw, costPerKw, cost);
    }

    //Display total water usage for the day
    private static void displayWaterUsage(ArrayList<WaterFixtures> waterFixtures, ArrayList<WaterAppliances> waterAppliances) {
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
        System.out.println("Total Liters Of Water Used: " + totalWaterUsage);
    }

    //Display total duration of rain for day
    private static void displayTotalRainDuration(House house) {
        //Initialise and set variable
        double hours;

        //Calculate total mins of raining to hours & mins
        if ((house.getTotalRainDuration() / 60) - 1 < 0) {
            hours = 0;
        } else hours = (house.getTotalRainDuration() / 60);
        double mins = house.getTotalRainDuration() % 60;

        //Print values of hours and mins rain duration
        System.out.printf("It Rained for a total of %.0f Hours and %.0f Minutes", hours, mins);
    }

    //Display config values
    private static void displayConfigData() {
        //Read in config data from csv file and print it
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] property = line.split(",");
                if (line.startsWith("ConfigValue")) {
                    System.out.print(property[1] + " : " + property[2]);
                    System.out.println();
                }
            }
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Load config values
    private static ArrayList<Integer> loadConfigValues() {
        ArrayList<Integer> configList = new ArrayList<>();

        //Read in config data from csv file and add it to array list
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] property = line.split(",");
                if (line.startsWith("ConfigValue")) {
                    configList.add(Integer.parseInt(property[2]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Return array list of config values
        return configList;
    }

    //Load trigger values
    private static ArrayList<Integer> loadTriggerValues() {
        ArrayList<Integer> triggerList = new ArrayList<>();

        //Read in trigger data from csv file and add it to array list
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] property = line.split(",");
                if (line.startsWith("TriggerValue")) {
                    triggerList.add(Integer.parseInt(property[2]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Return array list of trigger values
        return triggerList;
    }

    //Load room values
    private static ArrayList<Room> loadRooms() {
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