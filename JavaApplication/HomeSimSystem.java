import java.io.*;
import java.util.*;

/*
Provides menu for running the program
Provides methods to get total metrics
Provides method to simulate time
Provides method to load config file properties
*/

public class HomeSimSystem {
    private static String csvFile = "config.csv";
    private static String line;
    private static final int START_TIME = 0;
    private static final int END_TIME = 1439;


    public static void main(String[] args) {
        int userChoice = 0;
        Scanner userInput = new Scanner(System.in);
        while (userChoice != 3) {
            System.out.println("Please enter which choice you want: \n1 - Start Simulation \n2 - Display Config Settings \n3 - Quit");
            userChoice = userInput.nextInt();
            switch (userChoice) {
                case 1:
                    startSimulation(START_TIME, END_TIME);
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

    public static void startSimulation(int startTime, int endTime) {
        //Generate objects
        ArrayList<Fixtures> fixtures = loadFixtures();
        ArrayList<WaterFixtures> waterFixtures = loadWaterFixtures();
        ArrayList<Appliances> appliances = loadAppliances();
        ArrayList<WaterAppliances> waterAppliances = loadWaterAppliances();
        ArrayList<Room> rooms = loadRooms();

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

        //Get config variables
        ArrayList<Integer> configOptions = loadConfigFile();

        //Create house object based on config parameters
        House house = new House(configOptions.get(0), configOptions.get(1), configOptions.get(2), configOptions.get(3), configOptions.get(4));

        //Initialize and set variables
        String meridian = "AM";
        String message = "0";
        int startHour = 5;
        int simSpeed = configOptions.get(5);
        double runTime = (simSpeed / 1000.0) * endTime;
        double simMinutes = (1000.0 / simSpeed);
        double simHours = 60 / simMinutes;
        System.out.printf("Starting simulator with sim speed of: %d \n\t- %.2f minutes per second. (%.2f seconds per hour) \n\t- Total run time = %.2f seconds\n", simSpeed, simMinutes, simHours, runTime);
        try {
            for (int i = 5; i > 0; --i) {
                System.out.print("\rThe simulator will start in: " + i);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\nThe Current Time is " + startHour + ":" + message + startTime % 60 + " " +
                meridian + "\nThe current Sunlight is: " + house.getCurrentSunlight() + "%" +
                "\nThe current Temperature is: " + house.getCurrentTemp() + "°");
        System.out.println();
        //Loop until start time is less than the end time, performing checks
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
                //Time Calculations
                if (startTime % 60 < 10) {
                    message = "0";
                } else message = "";
                if (startHour % 12 == 0) {
                    startHour = 0;
                }
                if (startTime > 420 && startTime < 1140) {
                    meridian = "PM";
                } else meridian = "AM";
                displaySunlight(startTime, house);
                displayTemperature(startTime, house);
                //Set aircon on temp based on config
                if (house.getCurrentTemp() > 28) {
                    for (Fixtures fixture : fixtures) {
                        if (fixture.toString().contains("Aircon")) {
                            fixture.setIsOn(true);
                            fixture.setOnDuration(fixture.getOnDuration() + 1);
                        }
                    }
                }
                //Print Status Of Everything On The Hour
                if (startTime % 60 == 0) {
                    ++startHour;
                    for (Room room : rooms) {
                        room.displayObjects();
                    }
                    System.out.println("The Current Time is " + startHour + ":" + message + startTime % 60 + " " +
                            meridian + "\nThe current Sunlight is: " + house.getCurrentSunlight() + "%" +
                            "\nThe current Temperature is: " + house.getCurrentTemp() + "°");
                    System.out.println();
                }
                Thread.sleep(simSpeed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        displayElectricityCost(fixtures, waterFixtures, appliances, waterAppliances);
        displayWaterUsage(waterFixtures, waterAppliances);
    }

    private static void displaySunlight(int startTime, House house) {
        if (startTime <= 120 && startTime > 20) {
            house.setCurrentSunlight(house.getCurrentSunlight() + 1);
        }
        if (startTime > 720 && house.getCurrentSunlight() > house.getMIN_SUNLIGHT()) {
            house.setCurrentSunlight(house.getCurrentSunlight() - 1);
        }

    }

    private static void displayTemperature(int startTime, House house) {
        double x = Math.random();
        if (startTime >= 60 && startTime < 720 && startTime % 20 == 0 && house.getCurrentTemp() < house.getMaxTemp()) {
            house.setCurrentTemp(house.getCurrentTemp() + 1);
        }
        //9 at night
        if (startTime >= 720 && startTime % 20 == 0 && house.getCurrentTemp() > house.getMinTemp()) {
            house.setCurrentTemp(house.getCurrentTemp() - 1);
        }
        if (startTime % 120 == 0 && x <= 0.5 && house.getCurrentTemp() < house.getMaxTemp()) {
            house.setCurrentTemp(house.getCurrentTemp() + 1);
        }
        if (startTime % 120 == 0 && x > 0.5 && house.getCurrentTemp() > house.getMinTemp()) {
            house.setCurrentTemp(house.getCurrentTemp() - 1);
        }

    }

    private static void displayWaterUsage(ArrayList<WaterFixtures> waterFixtures, ArrayList<WaterAppliances> waterAppliances) {
        double totalWaterUsage = 0;
        for (WaterFixtures waterFixture : waterFixtures) {
            totalWaterUsage += waterFixture.getLitersPerMin() * waterFixture.getOnDuration();
        }
        for (WaterAppliances waterAppliance : waterAppliances) {
            totalWaterUsage += waterAppliance.getLitersPerMin() * waterAppliance.getOnDuration();
        }
        System.out.println("Total liters used: " + totalWaterUsage);
    }

    private static void displayElectricityCost(ArrayList<Fixtures> fixtures, ArrayList<WaterFixtures> waterFixtures, ArrayList<Appliances> appliances, ArrayList<WaterAppliances> waterAppliances) {
        double totalWatts = 0;
        double costPerKw = 0.3;
        double cost;
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
        cost = (totalWatts / 1000) * costPerKw;
        System.out.println("Total cost of electricity for the day: $" + cost);
    }


    public static ArrayList<Integer> loadConfigFile() {
        ArrayList<Integer> configList = new ArrayList<>();
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
        return configList;
    }

    public static ArrayList<Fixtures> loadFixtures() {
        ArrayList<Fixtures> fixtureList = new ArrayList<>();
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
        return fixtureList;
    }

    public static ArrayList<WaterFixtures> loadWaterFixtures() {
        ArrayList<WaterFixtures> waterFixtureList = new ArrayList<>();
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
        return waterFixtureList;
    }

    public static ArrayList<Appliances> loadAppliances() {
        ArrayList<Appliances> applianceList = new ArrayList<>();
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
        return applianceList;
    }

    public static ArrayList<WaterAppliances> loadWaterAppliances() {
        ArrayList<WaterAppliances> waterApplianceList = new ArrayList<>();
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
        return waterApplianceList;
    }

    public static ArrayList<Room> loadRooms() {
        ArrayList<Room> roomList = new ArrayList<>();
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
        return roomList;
    }

    public static void displayConfigData() {
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
}

