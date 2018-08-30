/*
Provides getters and setters for House variables
*/
public class House {
    public int minTemp;
    public int maxTemp;
    public int currentTemp;
    public int minSunlight = 0;
    public int maxSunlight = 100;
    public int currentSunlight;
    public int soilMoisture;

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

    public int getMinSunlight() {
        return minSunlight;
    }

    public int getMaxSunlight() {
        return maxSunlight;
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
