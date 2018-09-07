/*
Provides getters and setters for Room variables
*/

import java.util.ArrayList;
import java.util.List;

public class Room {
    //Room Variables
    private String currentRoom;
    private ArrayList<Object> fixtureObjects = new ArrayList<>();
    private ArrayList<Object> applianceObjects = new ArrayList<>();

    //Room Constructor
    public Room(String currentRoom) {
        this.currentRoom = currentRoom;
    }

    //Getters and Setters
    String getCurrentRoom() {
        return currentRoom;
    }

    void addFixture(Object obj) {
        fixtureObjects.add(obj);
    }

    void addAppliance(Object obj) {
        applianceObjects.add(obj);
    }

    void displayObjects() {
        System.out.println(this.currentRoom + ":");
        if (fixtureObjects.size() == 0) {
            System.out.println("No Fixtures in " + this.currentRoom + "\n");
        } else {
            System.out.println("\tFixtures:");
            for (Object fixtureObject : fixtureObjects) {
                System.out.println("\t" + fixtureObject);
            }
        }

        if (applianceObjects.size() == 0) {
            System.out.println("No Appliances in " + this.currentRoom + "\n");
        } else System.out.println("\tAppliances:");
        for (Object applianceObject : applianceObjects) {
            System.out.println("\t" + applianceObject);
        }
    }
}