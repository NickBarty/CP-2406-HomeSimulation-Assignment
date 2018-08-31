/*
Provides getters and setters for Appliances variables
*/
public class Appliances {
    private boolean isOn;
    private int electricityUsage;
    private int onDuration;

    public boolean getIsOn() {
        return isOn;
    }

    public void setIsOn(boolean isOn) {
        this.isOn = isOn;
    }

    public int getElectricityUsage() {
        return electricityUsage;
    }

    public void setElectricityUsage(int electricityUsage) {
        this.electricityUsage = electricityUsage;
    }

    public int getOnDuration() {
        return onDuration;
    }

    public void setOnDuration(int onDuration) {
        this.onDuration = onDuration;
    }
}
