/*
Provides getters and setters for Fixtures variables
*/
public class Fixtures {
    private boolean isOn;
    private int autoOffTime;
    private int onDuration;

    public boolean getIsOn() {
        return isOn;
    }

    public void setIsOn(boolean isOn) {
        this.isOn = isOn;
    }

    public int getAutoOffTime() {
        return autoOffTime;
    }

    public void setAutoOffTime(int autoOffTime) {
        this.autoOffTime = autoOffTime;
    }

    public int getOnDuration() {
        return onDuration;
    }

    public void setOnDuration(int onDuration) {
        this.onDuration = onDuration;
    }
}
