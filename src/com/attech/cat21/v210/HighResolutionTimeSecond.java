/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat21.v210;

/**
 *
 * @author andh
 */
public class HighResolutionTimeSecond {
    private short fullSecondIndication;
    private double value;

    /**
     * @return the fullSecondIndication
     */
    public short getFullSecondIndication() {
        return fullSecondIndication;
    }

    /**
     * @param fullSecondIndication the fullSecondIndication to set
     */
    public void setFullSecondIndication(short fullSecondIndication) {
        this.fullSecondIndication = fullSecondIndication;
    }

    /**
     * @return the value
     */
    public double getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(double value) {
        this.value = value;
    }
    
    public String toString() {
        return this.value + "";
    }
    
}
