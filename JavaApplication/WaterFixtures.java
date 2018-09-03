/*
Provides getters and setters for WaterFixtures variables
*/
public class WaterFixtures extends Fixtures {
    private double waterUsage;

    public WaterFixtures(String name) {
        super(name);
        this.waterUsage = 0;
    }

    public double getWaterUsage() {
        return waterUsage;
    }

    public void setWaterUsage(double waterUsage) {
        this.waterUsage = waterUsage;
    }
}
