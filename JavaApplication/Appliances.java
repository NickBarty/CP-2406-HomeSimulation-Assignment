/*
Provides getters and setters for Appliances variables
*/
public class Appliances {
    private String name;
    private String room;
    private boolean isOn;
    private double wattsPerMin;
    private int onDuration;

    public Appliances(String name, double wattsPerMin, String room) {
        this.name = name;
        this.room = room;
        this.isOn = false;
        this.wattsPerMin = wattsPerMin;
        this.onDuration = 0;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public boolean getIsOn() {
        return isOn;
    }

    public void setIsOn(boolean isOn) {
        this.isOn = isOn;
    }

    public double getWattsPerMin() {
        return wattsPerMin;
    }

    public void setWattsPerMin(int wattsPerMin) {
        this.wattsPerMin = wattsPerMin;
    }

    public int getOnDuration() {
        return onDuration;
    }

    public void setOnDuration(int onDuration) {
        this.onDuration = onDuration;
    }

    @Override
    public String toString(){
        return "\t" + this.name + ":\tRoom = " + this.room + " | On = " + this.isOn + " | Total On Duration (Mins) = " + this.onDuration + " | Watts Per Minute = " + this.wattsPerMin;
    }
}
