/*
Provides getters and setters for WaterFixtures variables
*/
public class WaterFixtures extends Fixtures {
    private double litersPerMin;
    private int onDuration;


    public WaterFixtures(String name, double wattsPerMin, double litersPerMin, String room) {
        super(name, wattsPerMin, room);
        this.onDuration = 0;
        this.litersPerMin = litersPerMin;
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
