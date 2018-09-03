/*
Provides getters and setters for Appliances variables
*/
public class Appliances {
    private String name;
    private boolean isOn;
    private int electricityUsage;
    private int onDuration;

    public Appliances(String name) {
        this.name = name;
        this.isOn = false;
        this.electricityUsage = 0;
        this.onDuration = 0;
    }

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

    @Override
    public String toString(){
        return "\t" +this.name + ": \t(On: " + this.isOn + ") (On Time Left: " + this.onDuration + ") (Electricity Usage: " + this.electricityUsage + ")";
    }
}
