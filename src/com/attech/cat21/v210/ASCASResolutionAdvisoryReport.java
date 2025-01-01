/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat21.v210;

/**
 *
 * @author andh
 */
public class ASCASResolutionAdvisoryReport {
    private short messageType;
    private short messageSubType;
    private short activeResolutionAdvisories;
    private short rACRecord;
    private boolean rATerminated;
    private boolean multipleThreatEncounter;
    private short threatTypeIndicator;
    private int threatIdentityData;
    
    public ASCASResolutionAdvisoryReport() {
    }

    /**
     * @return the messageType
     */
    public short getMessageType() {
        return messageType;
    }

    /**
     * @param messageType the messageType to set
     */
    public void setMessageType(short messageType) {
        this.messageType = messageType;
    }

    /**
     * @return the messageSubType
     */
    public short getMessageSubType() {
        return messageSubType;
    }

    /**
     * @param messageSubType the messageSubType to set
     */
    public void setMessageSubType(short messageSubType) {
        this.messageSubType = messageSubType;
    }

    /**
     * @return the activeResolutionAdvisories
     */
    public short getActiveResolutionAdvisories() {
        return activeResolutionAdvisories;
    }

    /**
     * @param activeResolutionAdvisories the activeResolutionAdvisories to set
     */
    public void setActiveResolutionAdvisories(short activeResolutionAdvisories) {
        this.activeResolutionAdvisories = activeResolutionAdvisories;
    }

    /**
     * @return the rACRecord
     */
    public short isRACRecord() {
        return rACRecord;
    }

    /**
     * @param rACRecord the rACRecord to set
     */
    public void setRACRecord(short rACRecord) {
        this.rACRecord = rACRecord;
    }

    /**
     * @return the rATerminated
     */
    public boolean isrATerminated() {
        return rATerminated;
    }

    /**
     * @param rATerminated the rATerminated to set
     */
    public void setrATerminated(boolean rATerminated) {
        this.rATerminated = rATerminated;
    }

    /**
     * @return the multipleThreatEncounter
     */
    public boolean getMultipleThreatEncounter() {
        return multipleThreatEncounter;
    }

    /**
     * @param multipleThreatEncounter the multipleThreatEncounter to set
     */
    public void setMultipleThreatEncounter(boolean multipleThreatEncounter) {
        this.multipleThreatEncounter = multipleThreatEncounter;
    }

    /**
     * @return the threatTypeIndicator
     */
    public short getThreatTypeIndicator() {
        return threatTypeIndicator;
    }

    /**
     * @param threatTypeIndicator the threatTypeIndicator to set
     */
    public void setThreatTypeIndicator(short threatTypeIndicator) {
        this.threatTypeIndicator = threatTypeIndicator;
    }

    /**
     * @return the threatIdentityData
     */
    public int getThreatIdentityData() {
        return threatIdentityData;
    }

    /**
     * @param threatIdentityData the threatIdentityData to set
     */
    public void setThreatIdentityData(int threatIdentityData) {
        this.threatIdentityData = threatIdentityData;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Active Resolution Advisories: ").append(this.getActiveResolutionAdvisories()).append(" ");
        builder.append("MessageSubType: ").append(this.getMessageSubType()).append(" ");
        builder.append("Multiple Threat Encounter: ").append(this.getMultipleThreatEncounter()).append(" ");
        builder.append("Threat Identity Data: ").append(this.getThreatIdentityData()).append(" ");
        builder.append("Threat Type Indicator: ").append(this.getThreatTypeIndicator()).append(" ");
        builder.append("RAC Record: ").append(this.isRACRecord()).append(" ");
        builder.append("RA Terminated: ").append(this.isrATerminated()).append(" ");
        return builder.toString();
    }
    
    
}
