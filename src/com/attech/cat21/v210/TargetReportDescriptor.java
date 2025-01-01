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
public class TargetReportDescriptor implements Serializable{
    private int addressType;
    private int altitudeReportingCapability;
    private boolean isRangeCheck;
    private boolean isReportTypeFromTarget;
    
    private boolean isDifferentialCorrection;
    private boolean isGroundBitSet;
    private boolean isSimulatedTargetReport;
    private boolean isTestTarget;
    private boolean isSelectedAltitudeAvailable;
    private int confidenceLevel;
    private boolean isIndependentPositionCheck;
    private boolean isNoGoBitStatus;
    private boolean isCompactPositionReporting;
    private boolean isLocalDecodingPositionJump;
    private boolean isRangeCheckFail;

    /**
     * @return the addressType
     */
    public int getAddressType() {
        return addressType;
    }

    /**
     * @param addressType the addressType to set
     */
    public void setAddressType(int addressType) {
        this.addressType = addressType;
    }

    /**
     * @return the altitudeReportingCapability
     */
    public int getAltitudeReportingCapability() {
        return altitudeReportingCapability;
    }

    /**
     * @param altitudeReportingCapability the altitudeReportingCapability to set
     */
    public void setAltitudeReportingCapability(int altitudeReportingCapability) {
        this.altitudeReportingCapability = altitudeReportingCapability;
    }

    /**
     * @return the isRangeCheck
     */
    public boolean isIsRangeCheck() {
        return isRangeCheck;
    }

    /**
     * @param isRangeCheck the isRangeCheck to set
     */
    public void setIsRangeCheck(boolean isRangeCheck) {
        this.isRangeCheck = isRangeCheck;
    }

    /**
     * @return the isReportTypeFromTarget
     */
    public boolean isIsReportTypeFromTarget() {
        return isReportTypeFromTarget;
    }

    /**
     * @param isReportTypeFromTarget the isReportTypeFromTarget to set
     */
    public void setIsReportTypeFromTarget(boolean isReportTypeFromTarget) {
        this.isReportTypeFromTarget = isReportTypeFromTarget;
    }

    /**
     * @return the isDifferentialCorrection
     */
    public boolean isIsDifferentialCorrection() {
        return isDifferentialCorrection;
    }

    /**
     * @param isDifferentialCorrection the isDifferentialCorrection to set
     */
    public void setIsDifferentialCorrection(boolean isDifferentialCorrection) {
        this.isDifferentialCorrection = isDifferentialCorrection;
    }

    /**
     * @return the isGroundBitSet
     */
    public boolean isIsGroundBitSet() {
        return isGroundBitSet;
    }

    /**
     * @param isGroundBitSet the isGroundBitSet to set
     */
    public void setIsGroundBitSet(boolean isGroundBitSet) {
        this.isGroundBitSet = isGroundBitSet;
    }

    /**
     * @return the isSimulatedTargetReport
     */
    public boolean isIsSimulatedTargetReport() {
        return isSimulatedTargetReport;
    }

    /**
     * @param isSimulatedTargetReport the isSimulatedTargetReport to set
     */
    public void setIsSimulatedTargetReport(boolean isSimulatedTargetReport) {
        this.isSimulatedTargetReport = isSimulatedTargetReport;
    }

    /**
     * @return the isTestTarget
     */
    public boolean isIsTestTarget() {
        return isTestTarget;
    }

    /**
     * @param isTestTarget the isTestTarget to set
     */
    public void setIsTestTarget(boolean isTestTarget) {
        this.isTestTarget = isTestTarget;
    }

    /**
     * @return the isSelectedAltitudeAvailable
     */
    public boolean isIsSelectedAltitudeAvailable() {
        return isSelectedAltitudeAvailable;
    }

    /**
     * @param isSelectedAltitudeAvailable the isSelectedAltitudeAvailable to set
     */
    public void setIsSelectedAltitudeAvailable(boolean isSelectedAltitudeAvailable) {
        this.isSelectedAltitudeAvailable = isSelectedAltitudeAvailable;
    }

    /**
     * @return the confidenceLevel
     */
    public int getConfidenceLevel() {
        return confidenceLevel;
    }

    /**
     * @param confidenceLevel the confidenceLevel to set
     */
    public void setConfidenceLevel(int confidenceLevel) {
        this.confidenceLevel = confidenceLevel;
    }

    /**
     * @return the isIndependentPositionCheck
     */
    public boolean isIsIndependentPositionCheck() {
        return isIndependentPositionCheck;
    }

    /**
     * @param isIndependentPositionCheck the isIndependentPositionCheck to set
     */
    public void setIsIndependentPositionCheck(boolean isIndependentPositionCheck) {
        this.isIndependentPositionCheck = isIndependentPositionCheck;
    }

    /**
     * @return the isNoGoBitStatus
     */
    public boolean isIsNoGoBitStatus() {
        return isNoGoBitStatus;
    }

    /**
     * @param isNoGoBitStatus the isNoGoBitStatus to set
     */
    public void setIsNoGoBitStatus(boolean isNoGoBitStatus) {
        this.isNoGoBitStatus = isNoGoBitStatus;
    }

    /**
     * @return the isCompactPositionReporting
     */
    public boolean isIsCompactPositionReporting() {
        return isCompactPositionReporting;
    }

    /**
     * @param isCompactPositionReporting the isCompactPositionReporting to set
     */
    public void setIsCompactPositionReporting(boolean isCompactPositionReporting) {
        this.isCompactPositionReporting = isCompactPositionReporting;
    }

    /**
     * @return the isLocalDecodingPositionJump
     */
    public boolean isIsLocalDecodingPositionJump() {
        return isLocalDecodingPositionJump;
    }

    /**
     * @param isLocalDecodingPositionJump the isLocalDecodingPositionJump to set
     */
    public void setIsLocalDecodingPositionJump(boolean isLocalDecodingPositionJump) {
        this.isLocalDecodingPositionJump = isLocalDecodingPositionJump;
    }

    /**
     * @return the isRangeCheckFail
     */
    public boolean isIsRangeCheckFail() {
        return isRangeCheckFail;
    }

    /**
     * @param isRangeCheckFail the isRangeCheckFail to set
     */
    public void setIsRangeCheckFail(boolean isRangeCheckFail) {
        this.isRangeCheckFail = isRangeCheckFail;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(" Address Type:").append(Integer.toString(addressType)).append(" ");
        builder.append(" AltReportingCap:").append(Integer.toString(altitudeReportingCapability)).append(" ");
        builder.append(" Range Check:").append(isRangeCheck).append(" ");
        builder.append(" Report From Target:").append(isReportTypeFromTarget).append(" ");
        builder.append(" Dif Cor:").append(isDifferentialCorrection).append(" ");
        builder.append(" Ground bit Set:").append(isGroundBitSet).append(" ");
        builder.append(" Simulated Target Report:").append(isSimulatedTargetReport).append(" ");
        builder.append(" Test Target:").append(isTestTarget).append(" ");
        builder.append(" Selected Alt Avail:").append(isSelectedAltitudeAvailable).append(" ");
        builder.append(" Confidence Level:").append(Integer.toString(confidenceLevel)).append(" ");
        builder.append(" Independent Position Check:").append(isIndependentPositionCheck).append(" ");
        builder.append(" No-Go Bit Status:").append(isNoGoBitStatus).append(" ");
        builder.append(" Compact Position Reporting:").append(isCompactPositionReporting).append(" ");
        builder.append(" Local Decoding Position Jump:").append(isLocalDecodingPositionJump).append(" ");
        builder.append(" Range Check Fail:").append(isRangeCheckFail).append(" ");
        return builder.toString();
    }
}
