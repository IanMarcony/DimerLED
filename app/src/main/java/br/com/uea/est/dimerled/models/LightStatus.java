package br.com.uea.est.dimerled.models;

public class LightStatus {
    private boolean isLightOn;
    private float intensity;

    public LightStatus() {
    }

    public LightStatus(boolean isLightOn, float intensity) {
        this.isLightOn = isLightOn;
        this.intensity = intensity;
    }

    public boolean isLightOn() {
        return isLightOn;
    }

    public void setLightOn(boolean lightOn) {
        isLightOn = lightOn;
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    @Override
    public String toString() {
        return "LightStatus{" +
                "isLightOn=" + isLightOn +
                ", intensity=" + intensity +
                '}';
    }
}
