/*
Provides getters and setters for Fixtures variables
*/
public class Fixtures{
    private String name;
    private String room;
    private boolean isOn;
    private int onDuration;
    private double wattsPerMin;

    public Fixtures(String name, double wattsPerMin, String room) {
        this.name = name;
        this.isOn = false;
        this.onDuration = 0;
        this.wattsPerMin = wattsPerMin;
        this.room = room;
    }

    public double getWattsPerMin() {
        return wattsPerMin;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setWattsPerMin(int wattsPerMin) {
        this.wattsPerMin = wattsPerMin;
    }

    public boolean getIsOn() {
        return isOn;
    }

    public void setIsOn(boolean isOn) {
        this.isOn = isOn;
    }

    public int getOnDuration() {
        return onDuration;
    }

    public void setOnDuration(int onDuration) {
        this.onDuration = onDuration;
    }

    @Override
    public String toString(){
        return "\t" + this.name + ":\tRoom = " + this.room +" | On = " + this.isOn + " | Total On Duration (Mins) = " + this.onDuration + " | Watts Per Minute = " + this.wattsPerMin;
    }
}
