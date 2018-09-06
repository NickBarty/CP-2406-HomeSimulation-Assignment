/*
Provides getters and setters for Room variables
*/

import java.util.ArrayList;
import java.util.List;

public class Room {
    private String currentRoom;
    private List<Object> fixtureObjects = new ArrayList<>();
    private List<Object> applianceObjects = new ArrayList<>();

    public Room(String currentRoom) {
        this.currentRoom = currentRoom;
    }

    public String getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(String currentRoom) {
        this.currentRoom = currentRoom;
    }

    public void addFixture(Object obj) {
        fixtureObjects.add(obj);
    }

    public void addAppliance(Object obj){
        applianceObjects.add(obj);
    }


    public void displayObjects() {
        System.out.println(this.currentRoom + ":");
        System.out.println("\tFixtures:");
        for (Object fixtureObject : fixtureObjects) System.out.println("\t" + fixtureObject);
        if (applianceObjects.size() == 0)
            System.out.println("No Appliances in " + this.currentRoom);
        else System.out.println("\tAppliances:");
        for (Object applianceObject : applianceObjects) System.out.println("\t" + applianceObject);
    }
}
