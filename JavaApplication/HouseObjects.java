//Provides getters and setters for house objects

public class HouseObjects {
    //House Object Variables
    private String objectName;
    private String room;
    private boolean isOn;
    int onDuration;
    private double wattsPerMin;

    //House Object Constructor
    HouseObjects(String name, double wattsPerMin, String room) {
        this.objectName = name;
        this.room = room;
        this.isOn = false;
        this.onDuration = 0;
        this.wattsPerMin = wattsPerMin;
    }

    //Getters and Setters
    String getName() {
        return objectName;
    }

    String getRoom() {
        return room;
    }

    boolean getIsOn() {
        return isOn;
    }

    void setIsOn(boolean isOn) {
        this.isOn = isOn;
    }

    int getOnDuration() {
        return onDuration;
    }

    void setOnDuration(int onDuration) {
        this.onDuration = onDuration;
    }

    double getWattsPerMin() {
        return wattsPerMin;
    }

    //Override toString to print object attributes
    @Override
    public String toString() {
        return "\t" + this.objectName + ":\tRoom = " + this.room + " | On = " + this.isOn + " | Total On Duration (Mins) = " +
                this.onDuration + " | Watts used = " + this.wattsPerMin * this.onDuration;
    }
}