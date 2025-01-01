/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat21.v210;

/**
 *
 * @author andh
 */
public class AirSpeed {
    private boolean unit;
    private double value;
    
    public AirSpeed() {
    }

    /** UNIT **/
    public boolean getUnit() {
        return unit;
    }

    public void setUnit(boolean unit) {
        this.unit = unit;
    }

   /** VALUE **/
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
    
    @Override
    public String toString() {
        return "Unit:" + this.unit + " Speed:" + this.value;
    }
}
