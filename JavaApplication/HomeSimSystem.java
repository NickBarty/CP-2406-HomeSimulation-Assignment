import java.io.*;
import java.util.*;

/*
Provides menu for running the program
Provides methods to get total metrics
Provides method to simulate time
Provides method to load config file properties
*/

public class HomeSimSystem {
    public static void main(String[] args) {
        int userChoice = 0;
        final int START_TIME = 0;
        final int END_TIME = 1439;

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
        //Manual creation until creation from csv is implemented

        //Room Creation
        Room livingRoom = new Room("Living Room");
        Room bedRoom = new Room("Bed Room");
        Room bedRoom2 = new Room("Bed Room 2");
        Room kitchen = new Room("Kitchen");
        Room garage = new Room("Garage");
        Room garden = new Room("Garden");

        //Fixture Creation
        Fixtures light = new Fixtures("Light");
        Fixtures aircon = new Fixtures("Aircon");
        Fixtures fan = new Fixtures("Fan");

        WaterFixtures sprinklers = new WaterFixtures("Sprinklers");

        //Appliance Creation
        Appliances tv = new Appliances("TV");
        Appliances microwave = new Appliances("Microwave");
        Appliances oven = new Appliances("Oven");
        Appliances car = new Appliances("Car");

        WaterAppliances jug = new WaterAppliances("Jug");
        WaterAppliances coffeeMachine = new WaterAppliances("Coffee Machine");

        //Assign Objects to rooms
        livingRoom.addFixture(light, aircon, fan);
        livingRoom.addAppliance(tv);
        bedRoom.addFixture(light, aircon, fan);
        bedRoom2.addFixture(light, fan);
        kitchen.addFixture(light);
        kitchen.addAppliance(microwave, jug, coffeeMachine, oven);
        garage.addFixture(light);
        garage.addAppliance(car);
        garden.addFixture(light, sprinklers);

        //Tests
//        livingRoom.displayObjects();
//        System.out.println();
//        bedRoom.displayObjects();
//        System.out.println();
//        bedRoom2.displayObjects();
//        System.out.println();
//        kitchen.displayObjects();
//        System.out.println();
//        garage.displayObjects();
//        System.out.println();
//        garden.displayObjects();

        //Get config variables
        List options = loadConfigFile();

        //Create house object based on config parameters
        House house = new House(Integer.parseInt(options.get(2).toString().replaceAll("\\D+", "")),
                Integer.parseInt(options.get(0).toString().replaceAll("\\D+", "")),
                Integer.parseInt(options.get(1).toString().replaceAll("\\D+", "")),
                0, 100, 0,
                Integer.parseInt(options.get(4).toString().replaceAll("\\D+", "")));

        //Initialize and set variables
        int meridianCheck = 0;
        String meridian = "AM";
        String message = "0";
        int startHour = 5;
        int simSpeed = Integer.parseInt(options.get(3).toString().replaceAll("\\D+", ""));

        System.out.println("\nThe Current Time is " + startHour + ":" + message + startTime % 60 + " " +
                meridian + "\nThe current Sunlight is: " + house.getCurrentSunlight() +
                "\nThe current Temperature is: " + house.getCurrentTemp());
        System.out.println();
        //Loop until start time is less than the end time, performing checks
        while (startTime < endTime) {
            try {
                //Advance Time By 1 Minute
                startTime++;
                //Time Calculations
                if (startTime % 60 < 10) {
                    message = "0";
                } else message = "";
                if (startHour % 13 == 0) {
                    startHour = 1;
                    meridianCheck++;
                }
                if (meridianCheck % 2 == 0) {
                    meridian = "AM";
                } else meridian = "PM";

                if (startTime <= 120 && startTime > 20) {
                    house.setCurrentSunlight(house.getCurrentSunlight() + 1);
                }
                //Print Status Of Everything On The Hour
                if (startTime % 60 == 0) {
                    ++startHour;
                    livingRoom.displayObjects();
                    System.out.println();
                    bedRoom.displayObjects();
                    System.out.println();
                    bedRoom2.displayObjects();
                    System.out.println();
                    kitchen.displayObjects();
                    System.out.println();
                    garage.displayObjects();
                    System.out.println();
                    garden.displayObjects();
                    System.out.println("The Current Time is " + startHour + ":" + message + startTime % 60 + " " +
                            meridian + "\nThe current Sunlight is: " + house.getCurrentSunlight() +
                            "\nThe current Temperature is: " + house.getCurrentTemp());
                    System.out.println();
                }
                Thread.sleep(simSpeed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public static void getTotalEnergyUsage() {

    }

    public static void getTotalWaterUsage() {

    }

    public static List loadConfigFile() {
        String fileName = "config.csv";
        File file = new File(fileName);
        List<List<String>> lines = new ArrayList<>();
        Scanner inputStream;

        try {
            inputStream = new Scanner(file);
            while (inputStream.hasNext()) {
                String line = inputStream.next();
                String[] values = line.split(",");
                lines.add(Arrays.asList(values));
            }
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static void displayConfigData() {
        String csvFile = "config.csv";
        BufferedReader br = null;
        String line;
        String csvSplitBy = ",";

        try {
            br = new BufferedReader(new FileReader(csvFile));
            for (int i = 0; i < 5; ++i) {
                line = br.readLine();
                String[] property = line.split(csvSplitBy);
                System.out.print(property[0] + " : " + property[1]);
                System.out.println();
            }
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}