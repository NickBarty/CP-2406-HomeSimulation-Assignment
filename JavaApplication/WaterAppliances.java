/*
Provides getters and setters for WaterAppliances variables
*/
public class WaterAppliances extends Appliances{
    private double waterUsage;

    public WaterAppliances(String name) {
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
