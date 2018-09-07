/*
Provides getters and setters for House variables
*/
class House {
    //House Variables
    private boolean isRaining;
    private int minTemp, maxTemp, currentTemp;
    private final int MIN_SUNLIGHT, MAX_SUNLIGHT;
    private int currentSunlight;
    private int soilMoisture;
    private double rainStart, rainEnd, totalRainDuration;

    //House Constructor
    House(int minTemp, int maxTemp, int currentTemp, int currentSunlight, int soilMoisture) {
        this.isRaining = false;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.currentTemp = currentTemp;
        this.MIN_SUNLIGHT = 0;
        this.MAX_SUNLIGHT = 100;
        this.currentSunlight = currentSunlight;
        this.soilMoisture = soilMoisture;
        this.rainStart = 0;
        this.rainEnd = 0;
        this.totalRainDuration = 0;
    }

    //Getters and Setters
    boolean isRaining() {
        return isRaining;
    }

    void setRaining(boolean raining) {
        isRaining = raining;
    }

    int getMinTemp() {
        return minTemp;
    }

    int getMaxTemp() {
        return maxTemp;
    }

    int getCurrentTemp() {
        return currentTemp;
    }

    void setCurrentTemp(int currentTemp) {
        this.currentTemp = currentTemp;
    }

    int getMIN_SUNLIGHT() {
        return MIN_SUNLIGHT;
    }

    int getMAX_SUNLIGHT() {
        return MAX_SUNLIGHT;
    }

    int getCurrentSunlight() {
        return currentSunlight;
    }

    void setCurrentSunlight(int currentSunlight) {
        this.currentSunlight = currentSunlight;
    }

    int getSoilMoisture() {
        return soilMoisture;
    }

    void setSoilMoisture(int soilMoisture) {
        this.soilMoisture = soilMoisture;
    }

    double getRainStart() {
        return rainStart;
    }

    void setRainStart(double rainStart) {
        this.rainStart = rainStart;
    }

    double getRainEnd() {
        return rainEnd;
    }

    void setRainEnd(double rainEnd) {
        this.rainEnd = rainEnd;
    }

    double getTotalRainDuration() {
        return totalRainDuration;
    }

    void setTotalRainDuration(double totalRainDuration) {
        this.totalRainDuration = totalRainDuration;
    }
}