/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat21.v210;

/**
 *
 * @author andh
 */
public class FinalStateSelectedAltitude {
    private boolean isManageVerticalModeActive;
    private boolean isAltitudeHoldModeActive;
    private boolean isApproachModeActive;
    private int altitude;

    /**
     * @return the isManageVerticalModeActive
     */
    public boolean getIsManageVerticalModeActive() {
        return isManageVerticalModeActive;
    }

    /**
     * @param isManageVerticalModeActive the isManageVerticalModeActive to set
     */
    public void setIsManageVerticalModeActive(boolean isManageVerticalModeActive) {
        this.isManageVerticalModeActive = isManageVerticalModeActive;
    }

    /**
     * @return the isAltitudeHoldModeActive
     */
    public boolean getIsAltitudeHoldModeActive() {
        return isAltitudeHoldModeActive;
    }

    /**
     * @param isAltitudeHoldModeActive the isAltitudeHoldModeActive to set
     */
    public void setIsAltitudeHoldModeActive(boolean isAltitudeHoldModeActive) {
        this.isAltitudeHoldModeActive = isAltitudeHoldModeActive;
    }

    /**
     * @return the isApproachModeActive
     */
    public boolean getIsApproachModeActive() {
        return isApproachModeActive;
    }

    /**
     * @param isApproachModeActive the isApproachModeActive to set
     */
    public void setIsApproachModeActive(boolean isApproachModeActive) {
        this.isApproachModeActive = isApproachModeActive;
    }

    /**
     * @return the altitude
     */
    public int getAltitude() {
        return altitude;
    }

    /**
     * @param altitude the altitude to set
     */
    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }
    
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Altitude: ").append(this.getAltitude()).append(" ");
        builder.append("Hold Mode Active: ").append(this.getIsAltitudeHoldModeActive()).append(" ");
        builder.append("Approach Mode Active: ").append(this.getIsApproachModeActive()).append(" ");
        builder.append("Manage Vertical Mode Active: ").append(this.getIsManageVerticalModeActive()).append(" ");
        return builder.toString();
    }
}
