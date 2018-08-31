import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

/*
Provides menu for running the program
Provides methods to get total metrics
Provides method to simulate time
Provides method to load config file properties
*/

public class HomeSimSystem {
    public static void main(String[] args) {
        int userChoice = 0;
        int currentTime = 0;
        final int START_TIME = 0;
        final int END_TIME = 1441;

        Scanner userInput = new Scanner(System.in);

        while (userChoice != 3) {
            System.out.println("Please enter which choice you want: \n1 - Start Simulation \n2 - Display Config Settings \n3 - Quit");
            userChoice = userInput.nextInt();
            switch (userChoice) {
                case 1:
                    startSimulation();
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
        System.out.println("Thanks for using the home automation simulator!");

    }

    public static void startSimulation() {

    }

    public static void simulateTime() {

    }

    public static void getCurrentTime() {

    }

    public static void getTotalEnergyUsage() {

    }

    public static void getTotalWaterUsage() {

    }

    public static void loadConfigFile() {

    }

    public static void displayConfigData() {
        Properties props = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("config.properties");
            props.load(input);

            props.forEach((key, value) -> System.out.println(key + " = " + value));
            System.out.println();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}