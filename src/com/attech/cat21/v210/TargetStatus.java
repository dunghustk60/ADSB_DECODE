/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat21.v210;

/**
 *
 * @author andh
 */
public class TargetStatus {

    private boolean intentChangeFlag;
    private boolean lNAVMode;
    private short priorityStatus;
    private short surveillanceStatus;

    /**
     * @return the intentChangeFlag
     */
    public boolean isIntentChangeFlag() {
        return intentChangeFlag;
    }

    /**
     * @param intentChangeFlag the intentChangeFlag to set
     */
    public void setIntentChangeFlag(boolean intentChangeFlag) {
        this.intentChangeFlag = intentChangeFlag;
    }

    /**
     * @return the lNAVMode
     */
    public boolean islNAVMode() {
        return lNAVMode;
    }

    /**
     * @param lNAVMode the lNAVMode to set
     */
    public void setlNAVMode(boolean lNAVMode) {
        this.lNAVMode = lNAVMode;
    }

    /**
     * @return the priorityStatus
     */
    public short getPriorityStatus() {
        return priorityStatus;
    }

    /**
     * @param priorityStatus the priorityStatus to set
     */
    public void setPriorityStatus(short priorityStatus) {
        this.priorityStatus = priorityStatus;
    }

    /**
     * @return the surveillanceStatus
     */
    public short getSurveillanceStatus() {
        return surveillanceStatus;
    }

    /**
     * @param surveillanceStatus the surveillanceStatus to set
     */
    public void setSurveillanceStatus(short surveillanceStatus) {
        this.surveillanceStatus = surveillanceStatus;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(" IntentChangeFlag: ").append(intentChangeFlag).append(" ");
        builder.append(" LNAV Mode: ").append(lNAVMode).append(" ");
        builder.append(" Priority Status: ").append(priorityStatus).append(" ");
        builder.append(" Surveillance Status: ").append(surveillanceStatus).append(" ");
        return builder.toString();
    }
}
