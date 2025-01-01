/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat21.v210;

/**
 *
 * @author root
 */
public class SelectedAltitude {
    private boolean isSourceAvailability;
    private short source;
    private int altitude;

    /**
     * @return the isSourceAvailability
     */
    public boolean isIsSourceAvailability() {
        return isSourceAvailability;
    }

    /**
     * @param isSourceAvailability the isSourceAvailability to set
     */
    public void setIsSourceAvailability(boolean isSourceAvailability) {
        this.isSourceAvailability = isSourceAvailability;
    }

    /**
     * @return the source
     */
    public short getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(short source) {
        this.source = source;
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
        return "Source: " + this.source + " Altitude: " + this.altitude;
    }
    
}
