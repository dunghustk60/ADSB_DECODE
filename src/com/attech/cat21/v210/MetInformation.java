/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat21.v210;

/**
 *
 * @author andh
 */
public class MetInformation {
    private boolean isHasWindSpeed;
    private boolean isHasWindDirection;
    private boolean isHasTemperature;
    private boolean isHasTurbulence;
    
    private int turbulence;
    private int temperature;
    private int windDirection;
    private int windSpeed;

    /**
     * @return the isHasWindSpeed
     */
    public boolean isIsHasWindSpeed() {
        return isHasWindSpeed;
    }

    /**
     * @param isHasWindSpeed the isHasWindSpeed to set
     */
    public void setIsHasWindSpeed(boolean isHasWindSpeed) {
        this.isHasWindSpeed = isHasWindSpeed;
    }

    /**
     * @return the isHasWindDirection
     */
    public boolean isIsHasWindDirection() {
        return isHasWindDirection;
    }

    /**
     * @param isHasWindDirection the isHasWindDirection to set
     */
    public void setIsHasWindDirection(boolean isHasWindDirection) {
        this.isHasWindDirection = isHasWindDirection;
    }

    /**
     * @return the isHasTemperature
     */
    public boolean isIsHasTemperature() {
        return isHasTemperature;
    }

    /**
     * @param isHasTemperature the isHasTemperature to set
     */
    public void setIsHasTemperature(boolean isHasTemperature) {
        this.isHasTemperature = isHasTemperature;
    }

    /**
     * @return the isHasTurbulence
     */
    public boolean isIsHasTurbulence() {
        return isHasTurbulence;
    }

    /**
     * @param isHasTurbulence the isHasTurbulence to set
     */
    public void setIsHasTurbulence(boolean isHasTurbulence) {
        this.isHasTurbulence = isHasTurbulence;
    }

    /**
     * @return the turbulence
     */
    public int getTurbulence() {
        return turbulence;
    }

    /**
     * @param turbulence the turbulence to set
     */
    public void setTurbulence(int turbulence) {
        this.turbulence = turbulence;
    }

    /**
     * @return the temperature
     */
    public int getTemperature() {
        return temperature;
    }

    /**
     * @param temperature the temperature to set
     */
    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    /**
     * @return the windDirection
     */
    public int getWindDirection() {
        return windDirection;
    }

    /**
     * @param windDirection the windDirection to set
     */
    public void setWindDirection(int windDirection) {
        this.windDirection = windDirection;
    }

    /**
     * @return the windSpeed
     */
    public int getWindSpeed() {
        return windSpeed;
    }

    /**
     * @param windSpeed the windSpeed to set
     */
    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Temp: %s ", this.getTemperature()));
        builder.append(String.format("Turbulence: %s ", this.getTurbulence()));
        builder.append(String.format("WindDirection: %s ", this.getWindDirection()));
        builder.append(String.format("WindSpeed: %s ", this.getWindSpeed()));
        builder.append(String.format("HasTemperature: %s ", this.isIsHasTemperature()));
        builder.append(String.format("HasTurbulence: %s ", this.isIsHasTurbulence()));
        builder.append(String.format("HasWindDirection: %s ", this.isIsHasWindDirection()));
        builder.append(String.format("HasWindSpeed: %s ", this.isIsHasWindSpeed()));
        return builder.toString();
    }
}
