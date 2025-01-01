/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat21.v210;

/**
 *
 * @author root
 */
public class AirborneGroundVector {
    private boolean rangeExceeded;
    private double groundSpeed;
    private double trackAngle;

    /**
     * @return the rangeExceeded
     */
    public boolean isRangeExceeded() {
        return rangeExceeded;
    }

    /**
     * @param rangeExceeded the rangeExceeded to set
     */
    public void setRangeExceeded(boolean rangeExceeded) {
        this.rangeExceeded = rangeExceeded;
    }

    /**
     * @return the groundSpeed
     */
    public double getGroundSpeed() {
        return groundSpeed;
    }

    /**
     * @param groundSpeed the groundSpeed to set
     */
    public void setGroundSpeed(double groundSpeed) {
        this.groundSpeed = groundSpeed;
    }

    /**
     * @return the trackAngle
     */
    public double getTrackAngle() {
        return trackAngle;
    }

    /**
     * @param trackAngle the trackAngle to set
     */
    public void setTrackAngle(double trackAngle) {
        this.trackAngle = trackAngle;
    }
    
    public String toString() {
        //return String.format("RangeExceeded: %s GroundSpeed: %s TrackAngle: %s", this.isRangeExceeded(), this.groundSpeed, this.trackAngle);
        return String.format("GSpeed: %s Angle: %.5f", this.groundSpeed, this.trackAngle);
    }
}
