/*
Provides getters and setters for Fixtures variables
*/
public class Fixtures{
    private String name;
    private boolean isOn;
    private int autoOffTime;
    private int electricityUsage;

    public Fixtures(String name) {
        this.name = name;
        this.isOn = false;
        this.autoOffTime = 0;
        this.electricityUsage = 0;
    }

    public int getElectricityUsage() {
        return electricityUsage;
    }

    public void setElectricityUsage(int electricityUsage) {
        this.electricityUsage = electricityUsage;
    }

    public boolean getIsOn() {
        return isOn;
    }

    public void setIsOn(boolean isOn) {
        this.isOn = isOn;
    }

    public int getAutoOffTime() {
        return autoOffTime;
    }

    public void setAutoOffTime(int autoOffTime) {
        this.autoOffTime = autoOffTime;
    }

    @Override
    public String toString(){
        return "\t" + this.name + ": \t(On: " + this.isOn + ") (Auto Off Delay: " + this.autoOffTime + ") (Electricity Usage: " + this.electricityUsage +")";
    }
}
