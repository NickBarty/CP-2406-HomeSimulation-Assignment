/*
Provides getters and setters for Room variables
*/

public class Room {
    //Room Variables
    private String currentRoom;

    //Room Constructor
    public Room(String currentRoom) {
        this.currentRoom = currentRoom;
    }

    //Getters and Setters
    String getCurrentRoom() {
        return currentRoom;
    }
}