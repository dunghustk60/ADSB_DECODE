 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat21.v210;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author root
 */
public class Cat21Message {
    
    
    
    /*
     * Header Byte 01
     * 01 I021/010 Data Source Identification [2]
     * 02 I021/040 Target Report Descriptor [1+]
     * 03 I021/161 Track Number [2]
     * 04 I021/015 Service Identification [1]
     * 05 I021/071 Time of Applicability for Position [3]
     * 06 I021/130 Position in WGS-84 co-ordinates [6]
     * 07 I021/131 Position in WGS-84 co-ordinates, high res.  8
     */
    private Integer sic, sac;  // DataSourceIdentification
    private TargetReportDescriptor targetDescriptor;
    private Integer trackNumber;
    private Integer serviceIdentification;
    private Double timeOfAplicabilityPosition;
    private Position position;
    private Position hightResolutionPosition;
    
    public byte[] positionByte = new byte[6];
    public byte[] hirespositionByte = new byte[8];
    
    /*
     * Header Byte 02
     * 08 I021/072 Time of Applicability for Velocity [3]
     * 09 I021/150 Air Speed [2]
     * 10 I021/151 True Air Speed [2]
     * 11 I021/080 Target Address [3]
     * 12 I021/073 Time of Message Reception of Position [3]
     * 13 I021/074 Time of Message Reception of Position-High Precision [4]
     * 14 I021/075 Time of Message Reception of Velocity [3]
     */
    private Double timeOfAplicabilityVelocity;
    private AirSpeed airSpeed;
    private IValue trueAirSpeed;
    private Integer targetAddress;
    private Double timeOfMessageReceptionOfPosition;
    private HighResolutionTimeSecond timeOfMessageReceptionOfPositionHightPrecisions;
    private Double timeOfMessageReceptionOfVelocity;
    
    /*
     * Header Byte 03
     * 15 I021/076 Time of Message Reception of Velocity-High Precision [4]
     * 16 I021/140 Geometric Height [2]
     * 17 I021/090 Quality Indicators [1+]
     * 18 I021/210 MOPS Version [1]
     * 19 I021/070 Mode 3/A Code [2]
     * 20 I021/230 Roll Angle [2]
     * 21 I021/145 Flight Level [2]
     */
    private HighResolutionTimeSecond timeOfMessageReceptionOfVelocityHightPrecision;
    private Double geometricHeight;
    private QualityIndicator qualityIndicator;
    private MOPSVersion mopsVersion;
    private Integer mode3a;
    private Double rollAgle;
    private Float flightLevel;
    
    /*
     * Header Byte 04
     * 22 I021/152 Magnetic Heading [2]
     * 23 I021/200 Target Status 1[]
     * 24 I021/155 Barometric Vertical Rate [2]
     * 25 I021/157 Geometric Vertical Rate [2]
     * 26 I021/160 Airborne Ground Vector [4]
     * 27 I021/165 Track Angle Rate [2]
     * 28 I021/077 Time of Report Transmission [3]
     */
    private Double magneticHeading;
    private TargetStatus targetStatus;
    private DValue barometricVerticalRate;
    private DValue geometricVerticalRate;
    private AirborneGroundVector airborneGroundVector;
    private Double trackAngleRate;
    private Double timeOfReportTranmission;
    
    /*
     * Header Byte 05
     * 29 I021/170 Target Identification [6]
     * 30 I021/020 Emitter Category [1]
     * 31 I021/220 Met Information [1+]
     * 32 I021/146 Selected Altitude [2]
     * 33 I021/148 Final State Selected Altitude [2]
     * 34 I021/110 Trajectory Intent [1+]
     * 35 I021/016 Service Management [1]
     */
    private String callSign;
    private Short emitterCategory;
    private MetInformation metInformation;
    private SelectedAltitude selectedAltitude;
    private FinalStateSelectedAltitude finalStateSelectedAltitude;
    private TrajectoryIntent tracjectoryIntent;
    private Float serviceManagement;
    
    /*
     * Header Byte 06
     * 36 I021/008 Aircraft Operational Status [1]
     * 37 I021/271 Surface Capabilities and Characteristics [1+]
     * 38 I021/132 Message Amplitude [1]
     * 39 I021/250 Mode S MB Data [1+N*8]
     * 40 I021/260 ACAS Resolution Advisory Report [7]
     * 41 I021/400 Receiver ID [1]
     * 42 I021/295 Data Ages [1+]
     */
    private AircraftOperationalStatus aircraftOperationStatus;
    private SurfaceCapabilitiesAndCharacterics surfaceCapabilitiesAndCharacterics;
    private Integer messageAmplitude;
    private ModeSMBData modeSMBData;
    private ASCASResolutionAdvisoryReport aCASResolutionAdvisoryReport;
    private Short receiverId;
    private DataAges dataAges;
    
    /*
     * Header Byte 06
     * 43 - Not Used -
     * 44 - Not Used -
     * 45 - Not Used -
     * 46 - Not Used -
     * 47 - Not Used -
     * 48 RE Reserved Expansion Field [1+]
     * 49 SP Special Purpose Field [1+]
     */
        
    private byte[] data = null;
    
    public Cat21Message() {
    }
    
    public Cat21Message(byte[] bytes) {
        this.data = new byte[bytes.length];
        System.arraycopy(bytes, 0, data, 0, bytes.length);
       // this.data = bytes;
    }
    
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        
        /*
         * Header Byte 01
         * 01 I021/010 Data Source Identification [2]
         * 02 I021/040 Target Report Descriptor [1+]
         * 03 I021/161 Track Number [2]
         * 04 I021/015 Service Identification [1]
         * 05 I021/071 Time of Applicability for Position [3]
         * 06 I021/130 Position in WGS-84 co-ordinates [6]
         * 07 I021/131 Position in WGS-84 co-ordinates, high res.  8
         */
        builder.append(String.format("%s, %s", this.getSac(), this.getSic()));
        builder.append(String.format(", %s", this.targetDescriptor));
        builder.append(String.format(", %s", this.trackNumber));
        builder.append(String.format(", %s", this.serviceIdentification));
        builder.append(String.format(", %s", this.timeOfAplicabilityPosition));
        builder.append(String.format(", %s", this.position));
        builder.append(String.format(", %s", this.hightResolutionPosition));
        
        /*
          * private Double timeOfAplicabilityVelocity;
            private AirSpeed airSpeed;
            private IValue trueAirSpeed;
            private Integer targetAddress;
            private Double timeOfMessageReceptionOfPosition;
            private HighResolutionTimeSecond timeOfMessageReceptionOfPositionHightPrecisions;
            private Double timeOfMessageReceptionOfVelocity;
        */
        
        builder.append(String.format(", %s", this.timeOfAplicabilityVelocity));
        builder.append(String.format(", %s", this.airSpeed));
        builder.append(String.format(", %s", this.trueAirSpeed));
        builder.append(String.format(", %s", this.targetAddress));
        builder.append(String.format(", %s", this.timeOfMessageReceptionOfPosition));
        builder.append(String.format(", %s", this.timeOfMessageReceptionOfPositionHightPrecisions));
        builder.append(String.format(", %s", this.timeOfMessageReceptionOfVelocity));
        
        /*
         * private HighResolutionTimeSecond timeOfMessageReceptionOfVelocityHightPrecision;
            private Double geometricHeight;
            private QualityIndicator qualityIndicator;
            private MOPSVersion mopsVersion;
            private Integer mode3a;
            private Double rollAgle;
            private Integer flightLevel;
         */
        builder.append(String.format(", %s", this.timeOfMessageReceptionOfVelocityHightPrecision));
        builder.append(String.format(", %s", this.geometricHeight));
        builder.append(String.format(", %s", this.qualityIndicator));
        builder.append(String.format(", %s", this.mopsVersion));
        builder.append(String.format(", %s", this.mode3a));
        builder.append(String.format(", %s", this.rollAgle));
        builder.append(String.format(", %s", this.flightLevel));
        
        /*
        private Double magneticHeading;
        private TargetStatus targetStatus;
        private DValue barometricVerticalRate;
        private DValue geometricVerticalRate;
        private AirborneGroundVector airborneGroundVector;
        private Double trackAngleRate;
        private Double timeOfReportTranmission;
        */
        
        builder.append(String.format(", %s", this.magneticHeading));
        builder.append(String.format(", %s", this.targetStatus));
        builder.append(String.format(", %s", this.barometricVerticalRate));
        builder.append(String.format(", %s", this.geometricVerticalRate));
        builder.append(String.format(", %s", this.airborneGroundVector));
        builder.append(String.format(", %s", this.trackAngleRate));
        builder.append(String.format(", %s", this.timeOfReportTranmission));
        
        /*
         *  private TargetIdentification targetIdentification;
         *  private Short emitterCategory;
         *  private MetInformation metInformation;
         *  private SelectedAltitude selectedAltitude;
         *  private FinalStateSelectedAltitude finalStateSelectedAltitude;
         *  private TrajectoryIntent tracjectoryIntent;
         *  private Float serviceManagement;
         * 
         */
        
        builder.append(String.format(", %s", this.callSign));
        builder.append(String.format(", %s", this.emitterCategory));
        builder.append(String.format(", %s", this.metInformation));
        builder.append(String.format(", %s", this.selectedAltitude));
        builder.append(String.format(", %s", this.finalStateSelectedAltitude));
        builder.append(String.format(", %s", this.tracjectoryIntent));
        builder.append(String.format(", %s", this.serviceManagement));
        
        /*
         private AircraftOperationalStatus aircraftOperationStatus;
         private SurfaceCapabilitiesAndCharacterics surfaceCapabilitiesAndCharacterics;
         private Integer messageAmplitude;
         private ModeSMBData modeSMBData;
         private ASCASResolutionAdvisoryReport aCASResolutionAdvisoryReport;
         private Short receiverId;
         private DataAges dataAges;
         */
        
        builder.append(String.format(", %s", this.aircraftOperationStatus));
        builder.append(String.format(", %s", this.surfaceCapabilitiesAndCharacterics));
        builder.append(String.format(", %s", this.messageAmplitude));
        builder.append(String.format(", %s", this.modeSMBData));
        builder.append(String.format(", %s", this.aCASResolutionAdvisoryReport));
        builder.append(String.format(", %s", this.receiverId));
        builder.append(String.format(", %s", this.dataAges));
        
        return builder.toString();
    }
    
    public void print() {
        
        System.out.println(String.format("%s, %s", this.getSac(), this.getSic()));
        if (this.timeOfAplicabilityPosition != null) System.out.println(String.format("timeOfAplicabilityPosition: %s", this.timeOfAplicabilityPosition));
        if (this.hightResolutionPosition != null) System.out.println(String.format("hightResolutionPosition %s", this.hightResolutionPosition));
        if (this.targetAddress != null) System.out.println(String.format("Target address: %s", this.targetAddress));
        if (this.timeOfMessageReceptionOfPosition != null) System.out.println(String.format("timeOfMessageReceptionOfPosition %s", this.timeOfMessageReceptionOfPosition));
        if (this.flightLevel != null) System.out.println(String.format("flightLevel: %s", this.flightLevel));
        if (this.airborneGroundVector != null) System.out.println(String.format("airborneGroundVector: %s", this.airborneGroundVector));
        if (this.timeOfReportTranmission != null) System.out.println(String.format("timeOfReportTranmission %s", this.timeOfReportTranmission));
        if (this.callSign != null) System.out.println(String.format("callSign: %s", this.callSign));
    }
    
    public boolean hasPosition() {
        return !(position == null);
    }

    public boolean hasFlightLevel() {
        return !(flightLevel == null);
    }

    public boolean valid3DPosition() {
        return !(position == null || flightLevel == null);
    }
    
    public static String getHeading() {
        
        StringBuilder builder = new StringBuilder();
        builder.append("SAC, SIC, Target Descriptor");
        builder.append(", Track #");
        builder.append(", Service ID");
        builder.append(", Time Of Aplicability Position");
        builder.append(", Position");
        builder.append(", High Resolution Position");
        
        /*
          * private Double timeOfAplicabilityVelocity;
            private AirSpeed airSpeed;
            private IValue trueAirSpeed;
            private Integer targetAddress;
            private Double timeOfMessageReceptionOfPosition;
            private HighResolutionTimeSecond timeOfMessageReceptionOfPositionHightPrecisions;
            private Double timeOfMessageReceptionOfVelocity;
        */
        builder.append(", Time Of Aplicability Velocity");
        builder.append(", AirSpeed");       
        builder.append(", True AirSpeed");
        builder.append(", Address");
        builder.append(", Time Of Received Position");
        builder.append(", Time Of Received Position High Precisions");
        builder.append(", Time Of Received Velocity");
        
        /*
         * private HighResolutionTimeSecond timeOfMessageReceptionOfVelocityHightPrecision;
            private Double geometricHeight;
            private QualityIndicator qualityIndicator;
            private MOPSVersion mopsVersion;
            private Integer mode3a;
            private Double rollAgle;
            private Integer flightLevel;
         */
        builder.append(", Time Of Received Velocity High Precisions");
        builder.append(", Geometric Height");
        builder.append(", Quality Indicator");
        builder.append(", MOPS Version");
        builder.append(", Mode 3A");
        builder.append(", Role Angle");
        builder.append(", Flight Level");
        
        /*
        private Double magneticHeading;
        private TargetStatus targetStatus;
        private DValue barometricVerticalRate;
        private DValue geometricVerticalRate;
        private AirborneGroundVector airborneGroundVector;
        private Double trackAngleRate;
        private Double timeOfReportTranmission;
        */
        
        builder.append(", Magnetic Heading");
        builder.append(", Target status");
        builder.append(", Barometric Vertical Rate");
        builder.append(", Geometric Vertical Rate");
        builder.append(", Airborne Ground Vector");
        builder.append(", Track Angle");
        builder.append(", Time Of Report Tranmission");
        
        /*
         *  private TargetIdentification targetIdentification;
         *  private Short emitterCategory;
         *  private MetInformation metInformation;
         *  private SelectedAltitude selectedAltitude;
         *  private FinalStateSelectedAltitude finalStateSelectedAltitude;
         *  private TrajectoryIntent tracjectoryIntent;
         *  private Float serviceManagement;
         * 
         */
        builder.append(", CallSign");
        builder.append(", Emit Category");
        builder.append(", Met Information");
        builder.append(", Selected Altitude");
        builder.append(", Final State Selected Altitude");
        builder.append(", Tracjectory Intent");
        builder.append(", Service Management");

        /*
         private AircraftOperationalStatus aircraftOperationStatus;
         private SurfaceCapabilitiesAndCharacterics surfaceCapabilitiesAndCharacterics;
         private Integer messageAmplitude;
         private ModeSMBData modeSMBData;
         private ASCASResolutionAdvisoryReport aCASResolutionAdvisoryReport;
         private Short receiverId;
         private DataAges dataAges;
         */
        builder.append(", Aircraft Operation Status");
        builder.append(", Surface Capabilities And Characterics");
        builder.append(", Message Amplitude");
        builder.append(", ModeS MB Data");
        builder.append(", ACAS Resolution Advisory Report");
        builder.append(", Receiver Id");
        builder.append(", Data Ages");
        
        return builder.toString();
    }

    public Integer getSic() { return sic; }
    public void setSic(Integer sic) { this.sic = sic; }

    public Integer getSac() { return sac; }
    public void setSac(Integer sac) { this.sac = sac; }

    public TargetReportDescriptor getTargetDescriptor() { return targetDescriptor; }
    public void setTargetDescriptor(TargetReportDescriptor targetDescriptor) { this.targetDescriptor = targetDescriptor; }

    public Integer getTrackNumber() { return trackNumber; }
    public void setTrackNumber(Integer trackNumber) { this.trackNumber = trackNumber; }

    public Integer getServiceIdentification() { return serviceIdentification; }
    public void setServiceIdentification(Integer serviceIdentification) { this.serviceIdentification = serviceIdentification; }

    public Double getTimeOfAplicabilityPosition() { return timeOfAplicabilityPosition; }
    public void setTimeOfAplicabilityPosition(Double timeOfAplicabilityPosition) { this.timeOfAplicabilityPosition = timeOfAplicabilityPosition; }

    public Position getPosition() { return position; }
    public void setPosition(Position position) { this.position = position; }

    public Position getHighResolutionPosition() { return hightResolutionPosition; }
    public void setHighResolutionPosition(Position hightResolutionPosition) { this.hightResolutionPosition = hightResolutionPosition;}

    public Double getTimeOfAplicabilityVelocity() { return timeOfAplicabilityVelocity; }
    public void setTimeOfAplicabilityVelocity(Double timeOfAplicabilityVelocity) { this.timeOfAplicabilityVelocity = timeOfAplicabilityVelocity; }

    public AirSpeed getAirSpeed() { return airSpeed; }
    public void setAirSpeed(AirSpeed airSpeed) { this.airSpeed = airSpeed; }

    public IValue getTrueAirSpeed() { return trueAirSpeed; }
    public void setTrueAirSpeed(IValue trueAirSpeed) { this.trueAirSpeed = trueAirSpeed; }
  
    public Integer getTargetAddress() {
        return targetAddress;
    }
    public void setTargetAddress(Integer targetAddress) {
        this.targetAddress = targetAddress;
    }

    public Double getTimeOfMessageReceptionOfPosition() {
        return timeOfMessageReceptionOfPosition;
    }
    public void setTimeOfMessageReceptionOfPosition(Double timeOfMessageReceptionOfPosition) {
        this.timeOfMessageReceptionOfPosition = timeOfMessageReceptionOfPosition;
    }

    public HighResolutionTimeSecond getTimeOfMessageReceptionOfPositionHightPrecisions() {
        return timeOfMessageReceptionOfPositionHightPrecisions;
    }
    public void setTimeOfMessageReceptionOfPositionHightPrecisions(HighResolutionTimeSecond timeOfMessageReceptionOfPositionHightPrecisions) {
        this.timeOfMessageReceptionOfPositionHightPrecisions = timeOfMessageReceptionOfPositionHightPrecisions;
    }

    public Double getTimeOfMessageReceptionOfVelocity() {
        return timeOfMessageReceptionOfVelocity;
    }
    public void setTimeOfMessageReceptionOfVelocity(Double timeOfMessageReceptionOfVelocity) {
        this.timeOfMessageReceptionOfVelocity = timeOfMessageReceptionOfVelocity;
    }

    public HighResolutionTimeSecond getTimeOfMessageReceptionOfVelocityHightPrecision() {
        return timeOfMessageReceptionOfVelocityHightPrecision;
    }
    public void setTimeOfMessageReceptionOfVelocityHightPrecision(HighResolutionTimeSecond timeOfMessageReceptionOfVelocityHightPrecision) {
        this.timeOfMessageReceptionOfVelocityHightPrecision = timeOfMessageReceptionOfVelocityHightPrecision;
    }

    public Double getGeometricHeight() {
        return geometricHeight;
    }
    public void setGeometricHeight(Double geometricHeight) {
        this.geometricHeight = geometricHeight;
    }

    public QualityIndicator getQualityIndicator() {
        return qualityIndicator;
    }
    public void setQualityIndicator(QualityIndicator qualityIndicator) {
        this.qualityIndicator = qualityIndicator;
    }

    public MOPSVersion getMopsVersion() {
        return mopsVersion;
    }
    public void setMopsVersion(MOPSVersion mopsVersion) {
        this.mopsVersion = mopsVersion;
    }

    public Integer getMode3a() {
        return mode3a;
    }
    public void setMode3a(Integer mode3a) {
        this.mode3a = mode3a;
    }

    public Double getRollAgle() {
        return rollAgle;
    }
    public void setRollAgle(Double rollAgle) {
        this.rollAgle = rollAgle;
    }

    public Float getFlightLevel() {
        return flightLevel;
    }
    public void setFlightLevel(Float flightLevel) {
        this.flightLevel = flightLevel;
    }

    public Double getMagneticHeading() {
        return magneticHeading;
    }
    public void setMagneticHeading(Double magneticHeading) {
        this.magneticHeading = magneticHeading;
    }

    public TargetStatus getTargetStatus() {
        return targetStatus;
    }
    public void setTargetStatus(TargetStatus targetStatus) {
        this.targetStatus = targetStatus;
    }

    public DValue getBarometricVerticalRate() {
        return barometricVerticalRate;
    }
    public void setBarometricVerticalRate(DValue barometricVerticalRate) {
        this.barometricVerticalRate = barometricVerticalRate;
    }

    public DValue getGeometricVerticalRate() {
        return geometricVerticalRate;
    }
    public void setGeometricVerticalRate(DValue geometricVerticalRate) {
        this.geometricVerticalRate = geometricVerticalRate;
    }

    public AirborneGroundVector getAirborneGroundVector() {
        return airborneGroundVector;
    }
    public void setAirborneGroundVector(AirborneGroundVector airborneGroundVector) {
        this.airborneGroundVector = airborneGroundVector;
    }

    public Double getTrackAngleRate() {
        return trackAngleRate;
    }
    public void setTrackAngleRate(Double trackAngleRate) {
        this.trackAngleRate = trackAngleRate;
    }

    public Double getTimeOfReportTranmission() {
        return timeOfReportTranmission;
    }
    public void setTimeOfReportTranmission(Double timeOfReportTranmission) {
        this.timeOfReportTranmission = timeOfReportTranmission;
    }

    public String getCallSign() {
        return callSign;
    }
    public void setCallSign(String callSign) {
        this.callSign = callSign;
    }

    public Short getEmitterCategory() {
        return emitterCategory;
    }
    public void setEmitterCategory(Short emitterCategory) {
        this.emitterCategory = emitterCategory;
    }

    public MetInformation getMetInformation() {
        return metInformation;
    }
    public void setMetInformation(MetInformation metInformation) {
        this.metInformation = metInformation;
    }

    public SelectedAltitude getSelectedAltitude() {
        return selectedAltitude;
    }
    public void setSelectedAltitude(SelectedAltitude selectedAltitude) {
        this.selectedAltitude = selectedAltitude;
    }

    public FinalStateSelectedAltitude getFinalStateSelectedAltitude() {
        return finalStateSelectedAltitude;
    }
    public void setFinalStateSelectedAltitude(FinalStateSelectedAltitude finalStateSelectedAltitude) {
        this.finalStateSelectedAltitude = finalStateSelectedAltitude;
    }

    public TrajectoryIntent getTracjectoryIntent() {
        return tracjectoryIntent;
    }
    public void setTracjectoryIntent(TrajectoryIntent tracjectoryIntent) {
        this.tracjectoryIntent = tracjectoryIntent;
    }

    public Float getServiceManagement() {
        return serviceManagement;
    }
    public void setServiceManagement(Float serviceManagement) {
        this.serviceManagement = serviceManagement;
    }

    public AircraftOperationalStatus getAircraftOperationStatus() {
        return aircraftOperationStatus;
    }
    public void setAircraftOperationStatus(AircraftOperationalStatus aircraftOperationStatus) {
        this.aircraftOperationStatus = aircraftOperationStatus;
    }

    public SurfaceCapabilitiesAndCharacterics getSurfaceCapabilitiesAndCharacterics() {
        return surfaceCapabilitiesAndCharacterics;
    }
    public void setSurfaceCapabilitiesAndCharacterics(SurfaceCapabilitiesAndCharacterics surfaceCapabilitiesAndCharacterics) {
        this.surfaceCapabilitiesAndCharacterics = surfaceCapabilitiesAndCharacterics;
    }

    public Integer getMessageAmplitude() {
        return messageAmplitude;
    }
    public void setMessageAmplitude(Integer messageAmplitude) {
        this.messageAmplitude = messageAmplitude;
    }

    public ModeSMBData getModeSMBData() {
        return modeSMBData;
    }
    public void setModeSMBData(ModeSMBData modeSMBData) {
        this.modeSMBData = modeSMBData;
    }

    public ASCASResolutionAdvisoryReport getaCASResolutionAdvisoryReport() {
        return aCASResolutionAdvisoryReport;
    }
    public void setaCASResolutionAdvisoryReport(ASCASResolutionAdvisoryReport aCASResolutionAdvisoryReport) {
        this.aCASResolutionAdvisoryReport = aCASResolutionAdvisoryReport;
    }

    public Short getReceiverId() {
        return receiverId;
    }
    public void setReceiverId(Short receiverId) {
        this.receiverId = receiverId;
    }

    public DataAges getDataAges() {
        return dataAges;
    }
    public void setDataAges(DataAges dataAges) {
        this.dataAges = dataAges;
    }

    public byte[] getBytes() {
        return data;
    }
    public void setBytes(byte[] bytes) {
        this.data = new byte[bytes.length];
        System.arraycopy(bytes, 0, data, 0, bytes.length);
        //this.data = bytes;
    }
    
    public Object[] getValueArray() throws IllegalArgumentException, IllegalAccessException{
        List<Object> list = new ArrayList<>();
        for (Field field: this.getClass().getDeclaredFields()){
            if (field.getName().equals("targetAddress")){
                list.add(this.targetAddress != null ? Integer.toHexString(this.targetAddress).toUpperCase() : "");
                continue;
            }
            if (field.getName().equals("bytes")){
                list.add("");
                continue;
            }
            //targetDescriptor
            if (field.getName().equals("targetDescriptor")){
                list.add("");
                continue;
            }
            //aircraftOperationStatus
            if (field.getName().equals("aircraftOperationStatus")){
                list.add("");
                continue;
            }
            list.add(field.get(this));
        }
        return list.toArray();
    }
}
