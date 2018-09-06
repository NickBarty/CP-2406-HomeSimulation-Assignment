/*
Provides getters and setters for House variables
*/
public class House {
    private int minTemp;
    private int maxTemp;
    private int currentTemp;
    private int MIN_SUNLIGHT;
    private int MAX_SUNLIGHT;
    private int currentSunlight;
    private int soilMoisture;

    public House(int minTemp, int maxTemp, int currentTemp, int currentSunlight, int soilMoisture) {
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.currentTemp = currentTemp;
        this.MIN_SUNLIGHT = 0;
        this.MAX_SUNLIGHT = 100;
        this.currentSunlight = currentSunlight;
        this.soilMoisture = soilMoisture;
    }

    public int getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(int minTemp) {
        this.minTemp = minTemp;
    }

    public int getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(int maxTemp) {
        this.maxTemp = maxTemp;
    }

    public int getCurrentTemp() {
        return currentTemp;
    }

    public void setCurrentTemp(int currentTemp) {
        this.currentTemp = currentTemp;
    }

    public int getMIN_SUNLIGHT() {
        return MIN_SUNLIGHT;
    }

    public int getMAX_SUNLIGHT() {
        return MAX_SUNLIGHT;
    }

    public int getCurrentSunlight() {
        return currentSunlight;
    }

    public void setCurrentSunlight(int currentSunlight) {
        this.currentSunlight = currentSunlight;
    }

    public int getSoilMoisture() {
        return soilMoisture;
    }

    public void setSoilMoisture(int soilMoisture) {
        this.soilMoisture = soilMoisture;
    }
}
