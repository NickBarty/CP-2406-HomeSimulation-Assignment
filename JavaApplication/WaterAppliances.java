/*
Provides getters and setters for WaterAppliances variables
*/
public class WaterAppliances extends Appliances{
    private double litersPerMin;
    private int onDuration;

    public WaterAppliances(String name, double wattsPerMin, double litersPerMin, String room) {
        super(name, wattsPerMin, room);
        this.litersPerMin = litersPerMin;
        this.onDuration = 0;
    }

    @Override
    public int getOnDuration() {
        return onDuration;
    }

    @Override
    public void setOnDuration(int onDuration) {
        this.onDuration = onDuration;
    }

    public double getLitersPerMin() {
        return litersPerMin;
    }

    public void setLitersPerMin(double litersPerMin) {
        this.litersPerMin = litersPerMin;
    }
}
