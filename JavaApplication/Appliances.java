/*
Provides getters and setters for Appliances variables
*/
public class Appliances {
    //Appliance Variables
    private String applianceName;
    private String room;
    private boolean isOn;
    private int onDuration;
    private double wattsPerMin;

    //Appliance Constructor
    Appliances(String name, double wattsPerMin, String room) {
        this.applianceName = name;
        this.room = room;
        this.isOn = false;
        this.onDuration = 0;
        this.wattsPerMin = wattsPerMin;
    }

    //Getters and Setters
    String getApplianceName() {
        return applianceName;
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

    public int getOnDuration() {
        return onDuration;
    }

    public void setOnDuration(int onDuration) {
        this.onDuration = onDuration;
    }

    double getWattsPerMin() {
        return wattsPerMin;
    }

    //Override toString to print object attributes
    @Override
    public String toString() {
        return "\t" + this.applianceName + ":\tRoom = " + this.room + " | On = " + this.isOn + " | Total On Duration (Mins) = " +
                this.onDuration + " | Watts used = " + this.wattsPerMin * this.onDuration;
    }
}