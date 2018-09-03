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

    public void addFixture(Object obj1, Object obj2) {
        fixtureObjects.add(obj1);
        fixtureObjects.add(obj2);
    }

    public void addFixture(Object obj1, Object obj2, Object obj3) {
        fixtureObjects.add(obj1);
        fixtureObjects.add(obj2);
        fixtureObjects.add(obj3);
    }

    public void addAppliance(Object obj){
        applianceObjects.add(obj);
    }

    public void addAppliance(Object obj1, Object obj2){
        applianceObjects.add(obj1);
        applianceObjects.add(obj2);
    }

    public void addAppliance(Object obj1, Object obj2, Object obj3){
        applianceObjects.add(obj1);
        applianceObjects.add(obj2);
        applianceObjects.add(obj3);
    }

    public void addAppliance(Object obj1, Object obj2, Object obj3, Object obj4){
        applianceObjects.add(obj1);
        applianceObjects.add(obj2);
        applianceObjects.add(obj3);
        applianceObjects.add(obj4);

    }

    public void displayObjects() {
        System.out.println(this.currentRoom + ":");
        System.out.println("\tFixtures:");
        for (int i = 0; i < fixtureObjects.size(); ++i)
            System.out.println("\t" + fixtureObjects.get(i));
        if (applianceObjects.size() == 0)
            System.out.println("No Appliances in " + this.currentRoom);
        else System.out.println("\tAppliances:");
        for (int i = 0; i < applianceObjects.size(); ++i)
            System.out.println("\t" + applianceObjects.get(i));
    }
}
