//Provides getters and setters for House water object variables

public class HouseWaterObjects extends HouseObjects{
    //House water object Variables
    private double litersPerMin;

    //House water Object Constructor
    HouseWaterObjects(String name, double wattsPerMin, double litersPerMin, String room) {
        super(name, wattsPerMin, room);
        this.litersPerMin = litersPerMin;
    }

    //Getters and Setters
    double getLitersPerMin() {
        return litersPerMin;
    }

    //Override toString to print object attributes
    @Override
    public String toString() {
        return "\t" + super.getName() + ":\tRoom = " + super.getRoom() + " | On = " + super.getIsOn() +
                " | Total On Duration (Mins) = " + super.onDuration + " | Watts used = " + super.getOnDuration() * super.getWattsPerMin() +
                " | Liters Of Water used = " + this.getLitersPerMin() * super.onDuration;
    }
}