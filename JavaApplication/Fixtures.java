/*
Provides getters and setters for Fixtures variables
*/
public class Fixtures {
    //Fixture Variables
    private String fixtureName;
    private String room;
    private boolean isOn;
    private int onDuration;
    private double wattsPerMin;

    //Fixtures Constructor
    Fixtures(String name, double wattsPerMin, String room) {
        this.fixtureName = name;
        this.room = room;
        this.isOn = false;
        this.onDuration = 0;
        this.wattsPerMin = wattsPerMin;
    }

    //Getters and Setters
    String getFixtureName() {
        return fixtureName;
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
        return "\t" + this.fixtureName + ":\tRoom = " + this.room + " | On = " + this.isOn + " | Total On Duration (Mins) = " +
                this.onDuration + " | Watts used = " + this.wattsPerMin * this.onDuration;
    }
}