/*
Provides getters and setters for WaterAppliances variables
*/
public class WaterAppliances extends Appliances {
    //Water Appliance Variables
    private int onDuration;
    private double litersPerMin;

    //Water Appliance Constructor
    WaterAppliances(String name, double wattsPerMin, double litersPerMin, String room) {
        super(name, wattsPerMin, room);
        this.onDuration = 0;
        this.litersPerMin = litersPerMin;
    }

    //Getters and Setters

    //Override to get this specific objects duration
    @Override
    public int getOnDuration() {
        return onDuration;
    }

    //Override to set this specific objects duration
    @Override
    public void setOnDuration(int onDuration) {
        this.onDuration = onDuration;
    }

    double getLitersPerMin() {
        return litersPerMin;
    }

    //Override toString to print object attributes
    @Override
    public String toString() {
        return "\t" + super.getApplianceName() + ":\tRoom = " + super.getRoom() + " | On = " + super.getIsOn() +
                " | Total On Duration (Mins) = " + this.onDuration + " | Watts used = " + this.getOnDuration() * super.getWattsPerMin() +
                " | Liters Of Water used = " + this.getLitersPerMin() * this.onDuration;
    }
}