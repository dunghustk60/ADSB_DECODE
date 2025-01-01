/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat21.v210;

import java.io.Serializable;

/**
 *
 * @author andh
 */
public class Position implements Serializable{
    private double longtitude;
    private double latitude;

    public double getLongtitude() { return longtitude; }

    public void setLongtitude(double longtitude) { this.longtitude = longtitude; }

    public double getLatitude() { return latitude; }

    public void setLatitude(double latitude) { this.latitude = latitude; }
    
    @Override
    public String toString() {
        //return "Longtitude: " + this.longtitude + " Latitude:" + this.latitude;
        return String.format("%.6f; %.6f", this.latitude, this.longtitude);
    }
}
