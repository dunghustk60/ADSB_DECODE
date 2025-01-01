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
public class QualityIndicator implements Serializable {
    private int nACForVelocity;
    private int nIC;
    
    // Sub field 01
    private Boolean nICForBarometricAltitude;
    private Short sIL;
    private Short nACForPosition;
    
    // Sub field 02
    private Boolean silSupplement;
    private Short systemDesignAssuranceLevel;
    private Short geometricAltAcc;
    
    // Sub field 03
    private Short positionIntegrityCategory;

    

    /**
     * @param positionIntegrityCategory the positionIntegrityCategory to set
     */
    public void setPositionIntegrityCategory(Short positionIntegrityCategory) {
        this.positionIntegrityCategory = positionIntegrityCategory;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(" NUCr/NACv: ").append(nACForVelocity).append(" ");
        builder.append(" NUCp/NIC: ").append(nIC).append(" ");
        builder.append(" NICbaro: ").append(nICForBarometricAltitude).append(" ");
        builder.append(" SIL: ").append(sIL).append(" ");
        builder.append(" NACp: ").append(nACForPosition).append(" ");
        builder.append(" SILsup: ").append(silSupplement).append(" ");
        builder.append(" SDA: ").append(systemDesignAssuranceLevel).append(" ");
        builder.append(" GVA: ").append(geometricAltAcc).append(" ");
        builder.append(" PIC: ").append(positionIntegrityCategory).append(" ");
        return builder.toString();
    }

    /**
     * @return the nACForVelocity
     */
    public int getnACForVelocity() {
        return nACForVelocity;
    }

    /**
     * @param nACForVelocity the nACForVelocity to set
     */
    public void setnACForVelocity(int nACForVelocity) {
        this.nACForVelocity = nACForVelocity;
    }

    /**
     * @return the nIC
     */
    public int getnIC() {
        return nIC;
    }

    /**
     * @param nIC the nIC to set
     */
    public void setnIC(int nIC) {
        this.nIC = nIC;
    }

    /**
     * @return the nICForBarometricAltitude
     */
    public Boolean getnICForBarometricAltitude() {
        return nICForBarometricAltitude;
    }

    /**
     * @param nICForBarometricAltitude the nICForBarometricAltitude to set
     */
    public void setnICForBarometricAltitude(Boolean nICForBarometricAltitude) {
        this.nICForBarometricAltitude = nICForBarometricAltitude;
    }

    /**
     * @return the sIL
     */
    public Short getsIL() {
        return sIL;
    }

    /**
     * @param sIL the sIL to set
     */
    public void setsIL(Short sIL) {
        this.sIL = sIL;
    }

    /**
     * @return the nACForPosition
     */
    public Short getnACForPosition() {
        return nACForPosition;
    }

    /**
     * @param nACForPosition the nACForPosition to set
     */
    public void setnACForPosition(Short nACForPosition) {
        this.nACForPosition = nACForPosition;
    }

    /**
     * @return the silSupplement
     */
    public Boolean getSilSupplement() {
        return silSupplement;
    }

    /**
     * @param silSupplement the silSupplement to set
     */
    public void setSilSupplement(Boolean silSupplement) {
        this.silSupplement = silSupplement;
    }

    /**
     * @return the systemDesignAssuranceLevel
     */
    public Short getSystemDesignAssuranceLevel() {
        return systemDesignAssuranceLevel;
    }

    /**
     * @param systemDesignAssuranceLevel the systemDesignAssuranceLevel to set
     */
    public void setSystemDesignAssuranceLevel(Short systemDesignAssuranceLevel) {
        this.systemDesignAssuranceLevel = systemDesignAssuranceLevel;
    }

    /**
     * @return the geometricAltAcc
     */
    public Short getGeometricAltAcc() {
        return geometricAltAcc;
    }

    /**
     * @param geometricAltAcc the geometricAltAcc to set
     */
    public void setGeometricAltAcc(Short geometricAltAcc) {
        this.geometricAltAcc = geometricAltAcc;
    }

    /**
     * @return the positionIntegrityCategory
     */
    public Short getPositionIntegrityCategory() {
        return positionIntegrityCategory;
    }
}
