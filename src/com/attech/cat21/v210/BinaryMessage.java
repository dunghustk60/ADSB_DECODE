/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.attech.cat21.v210;

//import com.attech.asterix.cat21.entities.TimeOfDay;
//import com.attech.asterix.cat21.util.BitwiseUtils;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.time.Clock;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dung
 */
public class BinaryMessage {
    private int NUMBER_OF_MAX_ITEM = 49;
    private byte binaryMessage[];
    private Cat21Message cat21Message;

    private byte[][] bytes2D = new byte[NUMBER_OF_MAX_ITEM][];
    private boolean[] header = new boolean[NUMBER_OF_MAX_ITEM];
    private int soLuongHeader = 0;

    public int getSoLuongHeader() {
        return soLuongHeader;
    }

    public void setSoLuongHeader(int soLuongHeader) {
        this.soLuongHeader = soLuongHeader;
    }
    
    

    public boolean checkField1() {
        if ((this.cat21Message.getSic() != null && this.cat21Message.getSac() != null)
                || this.cat21Message.getTargetDescriptor() != null || this.cat21Message.getTrackNumber() != null
                || this.cat21Message.getServiceIdentification() != null || this.cat21Message.getTimeOfAplicabilityPosition() != null
                || this.cat21Message.getPosition() != null || this.cat21Message.getHighResolutionPosition() != null) {
            soLuongHeader++;
            return true;
        }
        return false;
    }

    public boolean checkField2() {
        if (this.cat21Message.getTimeOfAplicabilityVelocity() != null
                || this.cat21Message.getAirSpeed() != null || this.cat21Message.getTrueAirSpeed() != null
                || this.cat21Message.getTargetAddress() != null || this.cat21Message.getTimeOfMessageReceptionOfPosition() != null
                || this.cat21Message.getTimeOfMessageReceptionOfPositionHightPrecisions() != null || this.cat21Message.getTimeOfMessageReceptionOfVelocity() != null) {
            soLuongHeader++;
            return true;
        }
        return false;
    }

    public boolean checkField3() {
        if (this.cat21Message.getTimeOfMessageReceptionOfVelocityHightPrecision() != null
                || this.cat21Message.getGeometricHeight() != null || this.cat21Message.getQualityIndicator() != null
                || this.cat21Message.getMopsVersion() != null || this.cat21Message.getMode3a() != null
                || this.cat21Message.getRollAgle() != null || this.cat21Message.getFlightLevel() != null) {
            soLuongHeader++;
            return true;
        }
        return false;
    }

    public boolean checkField4() {
        if (this.cat21Message.getMagneticHeading() != null
                || this.cat21Message.getTargetStatus() != null || this.cat21Message.getBarometricVerticalRate() != null
                || this.cat21Message.getGeometricVerticalRate() != null || this.cat21Message.getAirborneGroundVector() != null
                || this.cat21Message.getTrackAngleRate() != null || this.cat21Message.getTimeOfReportTranmission() != null) {
            soLuongHeader++;
            return true;
        }
        return false;
    }

    public boolean checkField5() {
        if (this.cat21Message.getCallSign() != null
                || this.cat21Message.getEmitterCategory() != null || this.cat21Message.getMetInformation() != null
                || this.cat21Message.getSelectedAltitude() != null || this.cat21Message.getFinalStateSelectedAltitude() != null
                || this.cat21Message.getTracjectoryIntent() != null || this.cat21Message.getServiceManagement() != null) {
            soLuongHeader++;
            return true;
        }
        return false;
    }

    public boolean checkField6() {
        if (this.cat21Message.getAircraftOperationStatus() != null
                || this.cat21Message.getSurfaceCapabilitiesAndCharacterics() != null || this.cat21Message.getMessageAmplitude() != null
                || this.cat21Message.getModeSMBData() != null || this.cat21Message.getaCASResolutionAdvisoryReport() != null
                || this.cat21Message.getReceiverId() != null || this.cat21Message.getDataAges() != null) {
            soLuongHeader++;
            return true;
        }
        return false;
    }

    public boolean checkField7() {
        if (this.cat21Message.getReservedExpansionFieldLength() != null || this.cat21Message.getSpecialPurposeFieldLength() != null) {
            soLuongHeader++;
            return true;
        }
        return false;
    }

    public byte[] encrypt() {

        List<Byte> bytes = new ArrayList<>();
        byte h1 = 0x00;
        if (checkField1()) {
            if (this.cat21Message.getSic() != null && this.cat21Message.getSac() != null) {
                byte[] bts = encodeSicSac(this.cat21Message.getSic().byteValue(), this.cat21Message.getSac().byteValue());
                bytes.add(bts[0]);
                bytes.add(bts[1]);
                h1 |= 0x80;
                bytes2D[0] = bts;
                header[0] = true;

            }
            // 1 ------------------------------------------------- I021/010 Data Source Identification.  --> done
            if (this.cat21Message.getTargetDescriptor() != null) {
                byte[] bts = encodeTargetReportDescriptor(this.cat21Message.getTargetDescriptor());
                for (int i = 0; i < bts.length; i++) {
                    bytes.add(bts[i]);
                }
                h1 |= 0x40;
                bytes2D[1] = bts;
                header[1] = true;
            }
            // 2 --------------------------------------------------- I021/040 Target Report Descripto ---> done
            if (this.cat21Message.getTrackNumber() != null) {
                byte[] bts = encodeTrackNumber(this.cat21Message.getTrackNumber().shortValue());
                for (int i = 0; i < bts.length; i++) {
                    bytes.add(bts[i]);
                }
                h1 |= 0x20;
                bytes2D[2] = bts;
                header[2] = true;
            }
            // 3 ----------------------------------------------- I021/161 Track Number ---> done
            if (this.cat21Message.getServiceIdentification() != null) {
                bytes.add((byte) (this.cat21Message.getServiceIdentification() & 0xFF));
                h1 |= 0x10;
                bytes2D[3] = new byte[]{(byte) (this.cat21Message.getServiceIdentification() & 0xFF)};
                header[3] = true;
            }

            // 4 ----------------------------------------------- I021/015 Service Identification ---> done
            if (this.cat21Message.getTimeOfAplicabilityPosition() != null) {
                byte[] bts = encodePositiveDoubleToByte(this.cat21Message.getTimeOfAplicabilityPosition(), 128d, (byte) 3);
                for (int i = 0; i < bts.length; i++) {
                    bytes.add(bts[i]);
                }
                h1 |= 0x08;
                bytes2D[4] = bts;
                header[4] = true;
            }

            // 5 ------------------------------------------------ I021/071 Time of Applicability for Position  ---> done
            if (this.cat21Message.getPosition() != null) {
                byte[] bts = encodePosition(this.cat21Message.getPosition());
                for (int i = 0; i < bts.length; i++) {
                    bytes.add(bts[i]);
                }
                h1 |= 0x04;
                bytes2D[5] = bts;
                header[5] = true;

            }

            // 6 -------------------------------------------------- I021/130 Position in WGS-84 co-ordinates ---> done
            if (this.cat21Message.getHighResolutionPosition() != null) {
                byte[] bts = highResolutionPosition(this.cat21Message.getHighResolutionPosition());
//                byte[] bts1 = highResolutionPosition1(this.cat21Message.getHighResolutionPosition());
                for (int i = 0; i < bts.length; i++) {
                    bytes.add(bts[i]);
                }
                h1 |= 0x02;
                bytes2D[6] = bts;
                header[6] = true;
            }

            // 7 --------------------------------------------------- I021/131 Position in WGS-84 co-ordinates, high res  ----> done
        }else{
            return null;
        }

        byte h2 = 0x00;
        if (checkField2()) {
            h1 |= 0x01;
            bytes.add(0, h1);

            if (this.cat21Message.getTimeOfAplicabilityVelocity() != null) {
                byte[] bts = encodePositiveDoubleToByte(this.cat21Message.getTimeOfAplicabilityVelocity(), 128d, (byte) 3);
                for (int i = 0; i < bts.length; i++) {
                    bytes.add(bts[i]);
                }
                h2 |= 0x80;
                bytes2D[7] = bts;
                header[7] = true;
            }

            // 8 -------------- I021/072 Time of Applicability for Velocity ----> done
            if (this.cat21Message.getAirSpeed() != null) {
                byte[] bts = encodeAirSpeed(this.cat21Message.getAirSpeed());
                for (int i = 0; i < bts.length; i++) {
                    bytes.add(bts[i]);
                }
                h2 |= 0x40;
                bytes2D[8] = bts;
                header[8] = true;
            }

            // 9 ---------------  I021/150 Air Speed -----> done
            if (this.cat21Message.getTrueAirSpeed() != null) {
                byte[] bts = encodeTrueAirSpeed(this.cat21Message.getTrueAirSpeed());
                for (int i = 0; i < bts.length; i++) {
                    bytes.add(bts[i]);
                }
                h2 |= 0x20;
                bytes2D[9] = bts;
                header[9] = true;

            }
            // 10 ------------------------------- I021/151 True Air Speed --------> done
            if (this.cat21Message.getTargetAddress() != null) {
                byte[] bts = encodeAddress(this.cat21Message.getTargetAddress()); // incorrect
                for (int i = 0; i < bts.length; i++) {
                    bytes.add(bts[i]);
                }
                h2 |= 0x10;
                bytes2D[10] = bts;
                header[10] = true;
            }

            // 11 --------------------------------- I021/080 Target Address ------> done
            if (this.cat21Message.getTimeOfMessageReceptionOfPosition() != null) {
                byte[] bts = encodePositiveDoubleToByte(this.cat21Message.getTimeOfMessageReceptionOfPosition(), 128d, (byte) 3);
                for (int i = 0; i < bts.length; i++) {
                    bytes.add(bts[i]);
                }
                h2 |= 0x08;
                bytes2D[11] = bts;
                header[11] = true;
            }

            // 12 --------------------------- I021/073 Time of Message Reception of Position ----> done 
            if (this.cat21Message.getTimeOfMessageReceptionOfPositionHightPrecisions() != null) {
                byte[] bts = encodeTimeOfMessageReceptionOfPositionHighPrecision(this.cat21Message.getTimeOfMessageReceptionOfPositionHightPrecisions());
                for (int i = 0; i < bts.length; i++) {
                    bytes.add(bts[i]);
                }
                h2 |= 0x04;
                bytes2D[12] = bts;
                header[12] = true;

            }

            // 13 ---------------------------- I021/074 Time of Message Reception of Position-High Precision ----------> done
            if (this.cat21Message.getTimeOfMessageReceptionOfVelocity() != null) {
                byte[] bts = encodePositiveDoubleToByte(this.cat21Message.getTimeOfMessageReceptionOfVelocity(), 128d, (byte) 3);
                for (int i = 0; i < bts.length; i++) {
                    bytes.add(bts[i]);
                }
                h2 |= 0x02;
                bytes2D[13] = bts;
                header[13] = true;
            }
            // 14 ----------------------------- I021/075 Time of Message Reception of Velocity ---------> done

        }else{
            bytes.add(0, h1);

        }

        byte h3 = 0x00;

        if (checkField3()) {

            h2 |= 0x01;
            bytes.add(1, h2);

            if (this.cat21Message.getTimeOfMessageReceptionOfVelocityHightPrecision() != null) {
                byte[] bts = encodeTimeOfMessageReceptionOfPositionHighPrecision(this.cat21Message.getTimeOfMessageReceptionOfVelocityHightPrecision());
                for (int i = 0; i < bts.length; i++) {
                    bytes.add(bts[i]);
                }
                h3 |= 0x80;
                bytes2D[14] = bts;
                header[14] = true;

            }

            // 15 --------------------------- Data Item I021/076, Time of Message Reception of Velocity–High Precision ------> done
            if (this.cat21Message.getGeometricHeight() != null) {
                byte[] bts = encodeDToBytes(this.cat21Message.getGeometricHeight(), 0.16d, (byte) 2);
                for (int i = 0; i < bts.length; i++) {
                    bytes.add(bts[i]);
                }
                h3 |= 0x40;
                bytes2D[15] = bts;
                header[15] = true;
            }

            // 16 ----------------------------- Data Item I021/140, Geometric Height ----------> done
            if (this.cat21Message.getQualityIndicator() != null) {
                byte[] bts = encodeQualityIndicator(this.cat21Message.getQualityIndicator());
                for (int i = 0; i < bts.length; i++) {
                    bytes.add(bts[i]);
                }
                h3 |= 0x20;
                bytes2D[16] = bts;
                header[16] = true;
            }

            // 17 ----------------------------------- Data Item I021/090, Quality Indicators ---------> done
            if (this.cat21Message.getMopsVersion() != null) {
                byte[] bts = encodeMOPSVersion(this.cat21Message.getMopsVersion());
                for (int i = 0; i < bts.length; i++) {
                    bytes.add(bts[i]);
                }
                h3 |= 0x10;
                bytes2D[17] = bts;
                header[17] = true;

            }

            // 18 ----------------------------------------- I021/210 MOPS Version -------> done
            if (this.cat21Message.getMode3a() != null) {
                byte[] bts = new byte[2];
                int intV = Integer.parseInt(this.cat21Message.getMode3a().toString(), 8);
                bts[0] = (byte) ((intV >> 8) & 0x0F);
                bts[1] = (byte) (intV & 0xFF);
                for (int i = 0; i < bts.length; i++) {
                    bytes.add(bts[i]);
                }
                h3 |= 0x08;
                bytes2D[18] = bts;
                header[18] = true;
            }
            // 19 --------------------------------------- Mode 3/A Code --------------------> done

            if (this.cat21Message.getRollAgle() != null) {
                byte[] bts = encodeDToBytes(this.cat21Message.getRollAgle(), 100d, (byte) 2);
                for (int i = 0; i < bts.length; i++) {
                    bytes.add(bts[i]);
                }
                h3 |= 0x04;
                bytes2D[19] = bts;
                header[19] = true;
            }
            // 20 -------------------------------------------- I021/230 Roll Angle ---------------> done

            if (this.cat21Message.getFlightLevel() != null) {
                byte[] bts = encodeFToBytes(this.cat21Message.getFlightLevel(), 4f, (byte) 2);
                for (int i = 0; i < bts.length; i++) {
                    bytes.add(bts[i]);
                }
                h3 |= 0x02;
                bytes2D[20] = bts;
                header[20] = true;
            }
            //21 ------------------------------ I021/230 Roll Angle -----------------------------------> done 

        }else{
            bytes.add(1, h2);
        }

        byte h4 = 0x00;

        if (checkField4()) {
            h3 |= 0x01;

            bytes.add(2, h3);

            if (this.cat21Message.getMagneticHeading() != null) {
                byte[] bts = encodePositiveDoubleToByte(this.cat21Message.getMagneticHeading(), 65536d / 360, (byte) 2);
                for (int i = 0; i < bts.length; i++) {
                    bytes.add(bts[i]);
                }
                h4 |= 0x80;
                bytes2D[21] = bts;
                header[21] = true;
            }
            //22 ------------------------------ I021/152 Magnetic Heading ------------> done

            if (this.cat21Message.getTargetStatus() != null) {
                byte[] bts = encodeTargetStatus(this.cat21Message.getTargetStatus());
                for (int i = 0; i < bts.length; i++) {
                    bytes.add(bts[i]);
                }
                h4 |= 0x40;
                bytes2D[22] = bts;
                header[22] = true;

            }
            //23 ------------------------------ Data Item I021/200, Target Status -----> done
            if (this.cat21Message.getBarometricVerticalRate() != null) {
                byte[] bts = encodeVerticalRate(this.cat21Message.getBarometricVerticalRate());
                for (int i = 0; i < bts.length; i++) {
                    bytes.add(bts[i]);
                }
                h4 |= 0x20;
                bytes2D[23] = bts;
                header[23] = true;

            }
            //24 ------------------------------ I021/155 Barometric Vertical Rate --------> done
            if (this.cat21Message.getGeometricVerticalRate() != null) {
                byte[] bts = encodeVerticalRate(this.cat21Message.getGeometricVerticalRate());
                for (int i = 0; i < bts.length; i++) {
                    bytes.add(bts[i]);
                }
                h4 |= 0x10;
                bytes2D[24] = bts;
                header[24] = true;

            }
            //25 ------------------------------ I021/157 Geometric Vertical Rate -----> done

            if (this.cat21Message.getAirborneGroundVector() != null) {
                byte[] bts = encodeAirborneGroundVector(this.cat21Message.getAirborneGroundVector());
                for (int i = 0; i < bts.length; i++) {
                    bytes.add(bts[i]);
                }
                h4 |= 0x08;
                bytes2D[25] = bts;
                header[25] = true;

            }
            //26 ------------------------------ Data Item I021/160, Airborne Ground Vector -----> done
            if (this.cat21Message.getTrackAngleRate() != null) {
                byte[] bts = encodeDToBytes(this.cat21Message.getTrackAngleRate(), 32, 2);
                bts[0] |= (bts[0] & 0x03);
                for (int i = 0; i < bts.length; i++) {
                    bytes.add(bts[i]);
                }
                h4 |= 0x04;
                bytes2D[26] = bts;
                header[26] = true;
            }
            //27 ------------------------------  Data Item I021/165, Track Angle Rate ----> done
            if (this.cat21Message.getTimeOfReportTranmission() != null) {
//            byte[] bts = encodeFToBytes(this.cat21Message.getTimeOfReportTranmission(), 128f, (byte) 3);
                byte[] bts = encodePositiveDoubleToByte(this.cat21Message.getTimeOfReportTranmission(), 128d, (byte) 3);
                for (int i = 0; i < bts.length; i++) {
                    bytes.add(bts[i]);
                }
                h4 |= 0x02;
                bytes2D[27] = bts;
                header[27] = true;
            }
            //28 ------------------------------ Data Item I021/077, Time of ASTERIX Report Transmission. done
        }else{
            bytes.add(2, h3);
        }

        byte h5 = 0x00;
        if (checkField5()) {
            h4 |= 0x01;
            
            bytes.add(3, h4);
            
            if (this.cat21Message.getCallSign() != null) {
                byte[] bts = encodeCallSign(this.cat21Message.getCallSign());
                for (int i = 0; i < bts.length; i++) {
                    bytes.add(bts[i]);
                }
                h5 |= 0x80;
                bytes2D[28] = bts;
                header[28] = true;
            }
            //29 ------------------------------ I021/170 Target Identification --------------> done
            if (this.cat21Message.getEmitterCategory() != null) {
                bytes.add((byte) (this.cat21Message.getEmitterCategory() & 0xFF));
                h5 |= 0x40;
                bytes2D[29] = new byte[]{(byte) (this.cat21Message.getEmitterCategory() & 0xFF)};
                header[29] = true;
            }

            // 30 ------------------------------------ I021/020 Emitter Category ------------------> done
            if (this.cat21Message.getMetInformation() != null) {
                byte[] bts = encodeMetInformation(this.cat21Message.getMetInformation());
                for (int i = 0; i < bts.length; i++) {
                    bytes.add(bts[i]);
                }
                h5 |= 0x20;
                bytes2D[30] = bts;
                header[30] = true;
            }
            // 31 ------------------------------------ I021/220 Met Information ---------------------> done
            if (this.cat21Message.getSelectedAltitude() != null) {
                byte[] bts = encodeSelectedAltitude(this.cat21Message.getSelectedAltitude());
                for (int i = 0; i < bts.length; i++) {
                    bytes.add(bts[i]);
                }
                h5 |= 0x10;
                bytes2D[31] = bts;
                header[31] = true;

            }
            // 32 ------------------------------------------------ I021/146 Selected Altitude ------->  done
            if (this.cat21Message.getFinalStateSelectedAltitude() != null) {
                byte[] bts = encodeFinalStateSelectedAltitude(this.cat21Message.getFinalStateSelectedAltitude());
                for (int i = 0; i < bts.length; i++) {
                    bytes.add(bts[i]);
                }
                h5 |= 0x08;
                bytes2D[32] = bts;
                header[32] = true;

            }
            // 33 ------------------------------------------------ I021/148 Final State Selected Altitude ------------>   done
            if (this.cat21Message.getTracjectoryIntent() != null) {
                byte[] bts = encodeTrajectoryIntent(this.cat21Message.getTracjectoryIntent());
                for (int i = 0; i < bts.length; i++) {
                    bytes.add(bts[i]);
                }
                h5 |= 0x04;
                bytes2D[33] = bts;
                header[33] = true;
            }
            // 34 ------------------------------------------------ I021/110 Trajectory Intent -------> done
            if (this.cat21Message.getServiceManagement() != null) {
                byte[] bts = encodeServiceManagement(this.cat21Message.getServiceManagement());
                for (int i = 0; i < bts.length; i++) {
                    bytes.add(bts[i]);
                }
                h5 |= 0x02;
                bytes2D[34] = bts;
                header[34] = true;
            }
            // 35 --------------------------------- I021/016 Service Management ------------> done
        }else{
            bytes.add(3, h4);
        }

        byte h6 = 0x00;
        if (checkField6()) {
            h5 |= 0x01;

            bytes.add(4, h5);

            if (this.cat21Message.getAircraftOperationStatus() != null) {
                byte[] bts = encodeAircraftOperationStatus(this.cat21Message.getAircraftOperationStatus());
                for (int i = 0; i < bts.length; i++) {
                    bytes.add(bts[i]);
                }
                h6 |= 0x80;
                bytes2D[35] = bts;
                header[35] = true;

            }
//         36 ---------------------------------- I021/008 Aircraft Operational Status ---------> done
            if (this.cat21Message.getSurfaceCapabilitiesAndCharacterics() != null) {
                byte[] bts = encodeSurfaceCapabilitiesAndCharacterics(this.cat21Message.getSurfaceCapabilitiesAndCharacterics());
                for (int i = 0; i < bts.length; i++) {
                    bytes.add(bts[i]);
                }
                h6 |= 0x40;
                bytes2D[36] = bts;
                header[36] = true;
            }
            // 37 ------------------------------------- I021/271 Surface Capabilities and Characteristics  --------> done
            if (this.cat21Message.getMessageAmplitude() != null) {
                byte[] bts = encodeMessageAmplitude(this.cat21Message.getMessageAmplitude().byteValue());
                for (int i = 0; i < bts.length; i++) {
                    bytes.add(bts[i]);
                }
                h6 |= 0x20;
                bytes2D[37] = bts;
                header[37] = true;
            }

            // 38------------------------------ I021/132 Message Amplitude ------------> done
            if (this.cat21Message.getModeSMBData() != null) {
                byte[] bts = encodeModeSMBData(this.cat21Message.getModeSMBData());
                for (int i = 0; i < bts.length; i++) {
                    bytes.add(bts[i]);
                }
                h6 |= 0x10;
                bytes2D[38] = bts;
                header[38] = true;
            }
            // 39 ---------------- I021/295 Data Ages ------------> done
            if (this.cat21Message.getaCASResolutionAdvisoryReport() != null) {
                byte[] bts = encodeACASResolutionAdvisoryReport(this.cat21Message.getaCASResolutionAdvisoryReport());
                for (int i = 0; i < bts.length; i++) {
                    bytes.add(bts[i]);
                }
                h6 |= 0x08;
                bytes2D[39] = bts;
                header[39] = true;

            }
            // 40 ---------------- I021/260 ACAS Resolution Advisory Report 
            if (this.cat21Message.getReceiverId() != null) {
                bytes.add((byte) (this.cat21Message.getReceiverId() & 0xFF));
                h6 |= 0x04;
                bytes2D[40] = new byte[]{(byte) (this.cat21Message.getReceiverId() & 0xFF)};
                header[40] = true;
            }
            // 41 ------------------------------ I021/400 Receiver ID ----------------------> done
            if (this.cat21Message.getDataAges() != null) {
                byte[] bts = encodeDataAges(this.cat21Message.getDataAges());
                for (int i = 0; i < bts.length; i++) {
                    bytes.add(bts[i]);
                }
                h6 |= 0x02;
                bytes2D[41] = bts;
                header[41] = true;
                // 42 ------------------------------------- I021/295 Data Ages. done

            }
           

        }else{
            bytes.add(4, h5);
        }
        byte h7 = (byte) 0x00;
        if (checkField7()) {
            h6 |= 0x01;
            bytes.add(5, h6);
            
            
            if(this.cat21Message.getReservedExpansionFieldLength() != null){
                bytes.add((byte)(this.cat21Message.getReservedExpansionFieldLength().byteValue()));
                h7 |= 0x04;
                
                bytes2D[47] = new byte[]{this.cat21Message.getReservedExpansionFieldLength().byteValue()};
                header[47] = true;
            }
            if(this.cat21Message.getSpecialPurposeFieldLength() != null){
                bytes.add((byte)(this.cat21Message.getSpecialPurposeFieldLength().byteValue()));
                h7 |= 0x02;
                
                bytes2D[48] = new byte[]{this.cat21Message.getSpecialPurposeFieldLength().byteValue()};
                header[48] = true;
                
            }
            
            bytes.add(6, h7);
        }else{
            bytes.add(5, h6);
        }
        

//        int length = bytes.size() + 7 + 3;
//        byte d1 = (byte) (length >> 8 & 0xFF);
//        byte d2 = (byte) (length & 0xFF);
//        byte[] content = new byte[length];
        byte[] content = new byte[bytes.size()];
//        content[0] = 21;
//        content[1] = d1;
//        content[2] = d2;   
//        content[3] = h1;
//        content[4] = h2;
//        content[5] = h3;
//        content[6] = h4;
//        content[7] = h5;
//        content[8] = h6;
//        content[9] = h7;
//        for (int i = 10; i < content.length; i++) {
//            content[i] = bytes.get(i-10);
//        }
        for (int i = 0; i < content.length; i++) {
            content[i] = bytes.get(i);
        }
        return content;
    }

    public int getNUMBER_OF_MAX_ITEM() {
        return NUMBER_OF_MAX_ITEM;
    }

    public void setNUMBER_OF_MAX_ITEM(int NUMBER_OF_MAX_ITEM) {
        this.NUMBER_OF_MAX_ITEM = NUMBER_OF_MAX_ITEM;
    }

    public byte[][] getBytes2D() {
        return bytes2D;
    }

    public void setBytes2D(byte[][] bytes2D) {
        this.bytes2D = bytes2D;
    }

    public boolean[] getHeader() {
        return header;
    }

    public void setHeader(boolean[] header) {
        this.header = header;
    }
    
    

    public static byte[] encodeACASResolutionAdvisoryReport(ASCASResolutionAdvisoryReport aSCASResolutionAdvisoryReport) {
        byte bytes[] = new byte[7];
        bytes[0] |= (aSCASResolutionAdvisoryReport.getMessageType() & 0x0F) << 4;
        bytes[0] |= (aSCASResolutionAdvisoryReport.getMessageSubType() & 0x0F);

        bytes[1] |= (aSCASResolutionAdvisoryReport.getActiveResolutionAdvisories() >> 6) & 0xFF;

        bytes[2] |= (aSCASResolutionAdvisoryReport.getActiveResolutionAdvisories() & 0x3F);
        bytes[2] |= (aSCASResolutionAdvisoryReport.isRACRecord() >> 2) & 0x03;

        bytes[3] |= (aSCASResolutionAdvisoryReport.isRACRecord() & 0x03) << 6;
        bytes[3] |= ((aSCASResolutionAdvisoryReport.isrATerminated() == true ? 1 : 0) & 0x01) << 5;
        bytes[3] |= ((aSCASResolutionAdvisoryReport.getMultipleThreatEncounter() == true ? 1 : 0) & 0x01) << 4;
        bytes[3] |= (aSCASResolutionAdvisoryReport.getThreatTypeIndicator() & 0x03) << 2;
        bytes[3] |= (aSCASResolutionAdvisoryReport.getThreatIdentityData() >> 24) & 0x03;

        bytes[4] |= (aSCASResolutionAdvisoryReport.getThreatIdentityData() >> 16) & 0xFF;
        bytes[5] |= (aSCASResolutionAdvisoryReport.getThreatIdentityData() >> 8) & 0xFF;
        bytes[6] |= aSCASResolutionAdvisoryReport.getThreatIdentityData() & 0xFF;
        return bytes;
    }

    public static byte[] encodeModeSMBData(ModeSMBData modeSMBData) {
        ArrayList<Byte> list = new ArrayList<>();
        list.add((byte) (modeSMBData.getRepetitionFactor() & 0xFF));
        for (int i = 0; i <= modeSMBData.getRepetitionFactor(); i++) {
            for (Byte b : modeSMBData.getMessage()) {
                list.add(b);
            }
            byte encodedByte9 = 0;
            encodedByte9 |= (modeSMBData.getBDS1() & 0x0F) << 4;
            encodedByte9 |= (modeSMBData.getBDS2() & 0x0F);
            list.add(encodedByte9);
        }
        byte[] bytes = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) {
            bytes[i] = list.get(i);
        }
        return bytes;
    }

    public static byte[] encodeAircraftOperationStatus(AircraftOperationalStatus aircraftOperationalStatus) {
        byte encodedByte = 0;
        encodedByte |= ((aircraftOperationalStatus.isIsResolutionAdvisoryActive() == true ? 1 : 0) & 0x01) << 7;
        encodedByte |= (aircraftOperationalStatus.getTargetTrajectoryChangeReportCapability() & 0x03) << 5;
        encodedByte |= ((aircraftOperationalStatus.isIsTargetStateReportCapability() == true ? 1 : 0) & 0x01) << 4;
        encodedByte |= ((aircraftOperationalStatus.isIsAirReferencedVelocityReportCapability() == true ? 1 : 0) & 0x01) << 3;
        encodedByte |= ((aircraftOperationalStatus.isIsCockpitDisplayOfTrafficInformationAirborne() == true ? 1 : 0) & 0x01) << 2;
        encodedByte |= ((aircraftOperationalStatus.isIsTCASSystemStatus() == true ? 1 : 0) & 0x01) << 1;
        encodedByte |= (aircraftOperationalStatus.isIsSingleAntenna() == true ? 1 : 0) & 0x01;
        return new byte[]{encodedByte};
    }

    public static byte[] encodeSurfaceCapabilitiesAndCharacterics(SurfaceCapabilitiesAndCharacterics surfaceCapabilitiesAndCharacterics) {
        ArrayList<Byte> list = new ArrayList<>();
        list.add((byte) 0);
        if (surfaceCapabilitiesAndCharacterics.getLengthWidth() != null) {
            byte encodedByte1 = 0;
            byte encodedByte2 = 0;
            encodedByte1 |= ((surfaceCapabilitiesAndCharacterics.isPositionOffSetApplied() == true ? 1 : 0) & 0x01) << 5;
            encodedByte1 |= ((surfaceCapabilitiesAndCharacterics.isCockpitDisplayOfTrafficInformationSurface() == true ? 1 : 0) & 0x01) << 4;
            encodedByte1 |= ((surfaceCapabilitiesAndCharacterics.isB2low() == true ? 1 : 0) & 0x01) << 3;
            encodedByte1 |= ((surfaceCapabilitiesAndCharacterics.isReceivingATCServices() == true ? 1 : 0) & 0x01) << 2;
            encodedByte1 |= ((surfaceCapabilitiesAndCharacterics.isIndent() == true ? 1 : 0) & 0x01) << 1;

            encodedByte1 |= 0x01;
            list.add(encodedByte1);

            encodedByte2 |= (surfaceCapabilitiesAndCharacterics.getLengthWidth() & 0x0F) << 4;
            list.add(encodedByte2);
        }
        byte[] bytes = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) {
            bytes[i] = list.get(i);
        }
        return bytes;
    }

    public static byte[] encodeTrajectoryIntent(TrajectoryIntent trajectoryIntent) {
        // fix code
        ArrayList<Byte> list = new ArrayList<>();
        list.add((byte) 0);

        if (trajectoryIntent.isIsHasSubField1()) {
            list.set(0, (byte) 0x81); // 1
            byte encodedByte1 = 0;
            encodedByte1 |= ((trajectoryIntent.isIsDataAvailable() == true ? 1 : 0) & 0x01) << 7;
            encodedByte1 |= ((trajectoryIntent.isIsDataValid() == true ? 1 : 0) & 0x01) << 6;

            if (trajectoryIntent.isIsHasSubField2()) {
                encodedByte1 |= 0x01;
            }
            list.add(encodedByte1); // 2
        }
        if (trajectoryIntent.isIsHasSubField2()) {
            list.set(0, (byte) 0xC1); // modify 1

            list.add((byte) (trajectoryIntent.getRepetitionFactor() & 0xFF)); // 3
            int numberOfRepetition = (trajectoryIntent.getRepetitionFactor() & 0xFF);
            ArrayList<Byte> subList = new ArrayList<>();

            byte encodedByte3 = 0;
            encodedByte3 |= ((trajectoryIntent.isIsTcpNumberAvailable() == true ? 1 : 0) & 0x01) << 7;
            encodedByte3 |= ((trajectoryIntent.isIsTcpCompliance() == true ? 1 : 0) & 0x01) << 6;
            encodedByte3 |= (trajectoryIntent.getTcpNumber() & 0x3F) << 6;
            subList.add(encodedByte3); // 4

            byte[] bts = encodeFToBytes(trajectoryIntent.getAltitude(), 0.1f, 2);
            subList.add(bts[0]);
            subList.add(bts[1]);

            byte[] bts1 = encodeDToBytes(trajectoryIntent.getLatitude(), 100000 / 2.145767d, 3);
            subList.add(bts1[0]);
            subList.add(bts1[1]);
            subList.add(bts1[2]);

            byte[] bts2 = encodeDToBytes(trajectoryIntent.getLongtitle(), 100000 / 2.145767d, 3);
            subList.add(bts2[0]);
            subList.add(bts2[1]);
            subList.add(bts2[2]);

            byte encodedByte12 = 0;
            encodedByte12 |= (trajectoryIntent.getPointType() & 0x0F) << 4;
            encodedByte12 |= (trajectoryIntent.getTurnDirection() & 0x03) << 2;
            encodedByte12 |= ((trajectoryIntent.isIsTurnRadiusAvailable() == true ? 1 : 0) & 0x01) << 1;
            encodedByte12 |= (trajectoryIntent.isIsTimeOverPointAvailable() == true ? 1 : 0) & 0x01;
            subList.add(encodedByte12); // add 13th byte

            byte encodedByte13 = 0;
            encodedByte13 |= (trajectoryIntent.getTimeOverPoint() >> 16) & 0xFF;
            subList.add(encodedByte13); // add 14th byte

            byte encodedByte14 = 0;
            encodedByte14 |= (trajectoryIntent.getTimeOverPoint() >> 8) & 0xFF;
            subList.add(encodedByte14); // add 15th byte

            byte encodedByte15 = 0;
            encodedByte15 |= trajectoryIntent.getTimeOverPoint() & 0xFF;
            subList.add(encodedByte15); // add 16th byte

            byte[] bts3 = encodeDToBytes(trajectoryIntent.getTcpTurnRadius(), 100d, 2);
            subList.add(bts3[0]);
            subList.add(bts3[1]);

            for (int i = 0; i <= numberOfRepetition; i++) {
                list.addAll(subList);
            }

        }
        byte[] bytes = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) {
            bytes[i] = list.get(i);
        }
        return bytes;
    }

    public static byte[] encodeFinalStateSelectedAltitude(FinalStateSelectedAltitude finalStateSelectedAltitude) {
        byte[] bytes = encodeFToBytes(finalStateSelectedAltitude.getAltitude(), 1 / 25, 2);
        bytes[0] = (byte) (bytes[0] & 0x1F);
        bytes[0] |= ((finalStateSelectedAltitude.getIsManageVerticalModeActive() == true ? 1 : 0) & 0x01) << 7;
        bytes[0] |= ((finalStateSelectedAltitude.getIsAltitudeHoldModeActive() == true ? 1 : 0) & 0x01) << 6;
        bytes[0] |= ((finalStateSelectedAltitude.getIsApproachModeActive() == true ? 1 : 0) & 0x01) << 5;

        return bytes;
    }

    public static byte[] encodeSelectedAltitude(SelectedAltitude selectedAltitude) {
        byte[] bytes = encodeFToBytes(selectedAltitude.getAltitude(), 1 / 25, 2);
        bytes[0] = (byte) (bytes[0] & 0x1F);
        bytes[0] |= (selectedAltitude.getSource() & 0x03) << 5;
        bytes[0] |= ((selectedAltitude.isIsSourceAvailability() == true ? 1 : 0) & 0x01) << 7;

        return bytes;
    }

    public static byte[] encodeMetInformation(MetInformation metInformation) {
        ArrayList<Byte> list = new ArrayList<>();
        list.add((byte) 0);
        if (metInformation.isIsHasWindSpeed()) {
            list.set(0, (byte) (0x01 << 7));
            byte encodedByte1 = (byte) ((metInformation.getWindSpeed() >> 8) & 0xFF);
            byte encodedByte2 = (byte) (metInformation.getWindSpeed() & 0xFF);
            list.add(encodedByte1);
            list.add(encodedByte2);
        }
        if (metInformation.isIsHasWindDirection()) {
            list.set(0, (byte) ((list.get(0) | 0xC0)));
            byte encodedByte3 = (byte) ((metInformation.getWindDirection() >> 8) & 0xFF);
            byte encodedByte4 = (byte) (metInformation.getWindDirection() & 0xFF);
            list.add(encodedByte3);
            list.add(encodedByte4);
        }
        if (metInformation.isIsHasTemperature()) {
            list.set(0, (byte) ((list.get(0) | 0xE0)));

            // LSB = 0.25 
            byte[] bts = encodeFToBytes(metInformation.getTemperature(), 4, 2);
            list.add(bts[0]);
            list.add(bts[1]);
        }
        if (metInformation.isIsHasTurbulence()) {
            list.set(0, (byte) ((list.get(0) | 0xF0)));
            byte encodedByte7 = (byte) (metInformation.getTurbulence() & 0xFF);
        }
        byte[] bytes = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) {
            bytes[i] = list.get(i);
        }
        return bytes;
    }

    public static byte[] encodeAirborneGroundVector(AirborneGroundVector airborneGroundVector) {
        byte[] bytes = new byte[4];
        short shortV = (short) Math.round(airborneGroundVector.getTrackAngle() * 65536 / 360);
        bytes[3] |= shortV & 0xFF;
        bytes[2] |= (shortV >> 8) & 0xFF;
        short shortV1 = (short) Math.round(airborneGroundVector.getGroundSpeed() * 1 / 0.22); // LSB = 2^-14, or 0.22
        bytes[1] |= shortV1 & 0xFF;
        bytes[0] |= (shortV1 >> 8) & 0x7F;
        if (airborneGroundVector.isRangeExceeded()) {
            bytes[0] |= 0x80;
        }
        return bytes;
    }

    public static byte[] encodeTargetStatus(TargetStatus targetStatus) {
        byte encodedByte = 0;
        encodedByte |= ((byte) (targetStatus.isIntentChangeFlag() == true ? 1 : 0) & 0x01) << 7;
        encodedByte |= ((byte) (targetStatus.islNAVMode() == true ? 1 : 0) & 0x01) << 6;
        encodedByte |= ((byte) (targetStatus.isMilitaryEmergency() == true ? 1 : 0) & 0x01) << 5;
        encodedByte |= (byte) (targetStatus.getPriorityStatus() & 0x07) << 2;
        encodedByte |= targetStatus.getSurveillanceStatus() & 0x03;
        return new byte[]{encodedByte};
    }

    public static byte[] encodeVerticalRate(DValue geometricVerticalRate) {
        byte[] bytes = encodeDToBytes(geometricVerticalRate.getValue(), 0.16f, 2);
        if (geometricVerticalRate.isIsRangeExceeded()) {
            bytes[0] |= 0x80;
        } else {
            bytes[0] |= (bytes[0] & 0x7F);
        }
        return bytes;

//        short shortV = (short) Math.round(geometricVerticalRate.getValue()*0.16);
//        byte encodedByte1 = 0;
//        encodedByte1 |= (byte) ((shortV >> 8) & 0x7F);
//        if(geometricVerticalRate.getRangeExceeded()) encodedByte1 |= 0x80;
//        byte encodedByte2 = 0;
//        encodedByte2 |= (byte) (shortV & 0xFF);
//        return new byte[]{encodedByte1,encodedByte2};
    }

    public static byte[] encodeTimeOfMessageReceptionOfPositionHighPrecision(HighResolutionTimeSecond highResolutionTimeSecond) {
        byte[] encodedBytes = new byte[4];
        int scaledV = (int) (highResolutionTimeSecond.getValue() * 1073741824);
        encodedBytes[3] = (byte) (scaledV & 0xFF);
        encodedBytes[2] = (byte) ((scaledV >> 8) & 0xFF);
        encodedBytes[1] = (byte) ((scaledV >> 16) & 0xFF);
        encodedBytes[0] = (byte) ((scaledV >> 24) & 0x7F);
        encodedBytes[0] |= (highResolutionTimeSecond.getFullSecondIndication() << 6);
        return encodedBytes;
    }

    public static byte[] encodeTrueAirSpeed(IValue trueAirSpeed) {
        byte[] encodedBytes = new byte[2];
        encodedBytes[0] = (byte) ((trueAirSpeed.getValue() >> 8) & 0x7F);
        encodedBytes[1] = (byte) (trueAirSpeed.getValue() & 0xFF);
        if (trueAirSpeed.isIsRangeExceeded()) {
            encodedBytes[0] |= 0x80;
        }
        return encodedBytes;
    }

    public static byte[] encodeAirSpeed(AirSpeed airSpeed) {
        byte[] bytes = new byte[2];
        if (airSpeed.getUnit() == true) {
            short shortV = (short) Math.round(airSpeed.getValue() * 1000f);
            bytes[0] |= (shortV >> 8) & 0x7F;
            bytes[0] |= 0x80;
            bytes[1] |= shortV & 0xFF;

            return bytes; //LSB = 0.001 
        } else {
            short shortV = (short) Math.round(airSpeed.getValue() * (1 << 14));
            bytes[0] |= (shortV >> 8) & 0x7F;
            bytes[1] |= shortV & 0xFF;
            return bytes; // LSB = 2^(-14)
        }
    }

    public static byte[] encodeTrackNumber(Short trackNumber) {
        byte encodedByte1 = 0;
        byte encodedByte2 = 0;
        if (trackNumber != null) {
            encodedByte1 |= (trackNumber >> 8) & 0x0F;
            encodedByte2 = (byte) (trackNumber & 0xFF);
        }
        return new byte[]{encodedByte1, encodedByte2};
    }

    public static byte encodeDigits(int digit1, int digit2, int digit3) {
        byte encodedByte = 0;

        // Pack the digits into the encoded byte  
        encodedByte |= (digit1 & 0x07) << 5; // 3 bits for digit1  
        encodedByte |= (digit2 & 0x0F) << 1; // 4 bits for digit2  
        encodedByte |= (digit3 & 0x01);      // 1 bit for digit3  

        return encodedByte;
    }

    public static byte[] encodeQualityIndicator(QualityIndicator qualityIndicator) {
        byte encodedByte1 = 0;
        encodedByte1 |= (qualityIndicator.getnACForVelocity() & 0x07) << 5; // 3 bits for NACv
        encodedByte1 |= (qualityIndicator.getnIC() & 0x0F) << 1; // 4 bits for NIC
        if (qualityIndicator.checkFieldExtension1()) {
            encodedByte1 |= 0x01;
        } // 1 bit for field extension
        else {
            return new byte[]{encodedByte1};
        }

        byte encodedByte2 = 0;
        if (qualityIndicator.getnICForBarometricAltitude() != null) {
            encodedByte2 |= ((qualityIndicator.getnICForBarometricAltitude() == true ? 1 : 0) & 0x01) << 7;
        }
        if (qualityIndicator.getsIL() != null) {
            encodedByte2 |= (qualityIndicator.getsIL() & 0x03) << 5;
        }
        if (qualityIndicator.getnACForPosition() != null) {
            encodedByte2 |= (qualityIndicator.getnACForPosition() & 0x0F) << 1;
        }

        if (qualityIndicator.checkFieldExtension2()) {
            encodedByte2 |= 0x01;
        } else {
            return new byte[]{encodedByte1, encodedByte2};
        }

        byte encodedByte3 = 0;
        if (qualityIndicator.getSilSupplement() != null) {
            encodedByte3 |= ((qualityIndicator.getSilSupplement() == true ? 1 : 0) & 0x01) << 5;
        }
        if (qualityIndicator.getSystemDesignAssuranceLevel() != null) {
            encodedByte3 |= (qualityIndicator.getSystemDesignAssuranceLevel() & 0x03) << 3;
        }
        if (qualityIndicator.getGeometricAltAcc() != null) {
            encodedByte3 |= (qualityIndicator.getGeometricAltAcc() & 0x03) << 1;
        }

        if (qualityIndicator.checkFieldExtension3()) {
            encodedByte3 |= 0x01;
        } else {
            return new byte[]{encodedByte1, encodedByte2, encodedByte3};
        }

        byte encodedByte4 = 0;
        encodedByte4 |= (qualityIndicator.getPositionIntegrityCategory() & 0x0F) << 4;
        return new byte[]{encodedByte1, encodedByte2, encodedByte3, encodedByte4};
    }

    public static byte[] encodeDataAges(DataAges dataAges) {
        ArrayList<Byte> list = new ArrayList<>();
        ArrayList<Byte> list1 = new ArrayList<>();
        list.add((byte) 0);

        if (dataAges.getAircraftOperationalStatusAge() != null) {
            list.set(0, (byte) 0x80);
            byte byte1 = 0;
            byte1 = encodeDoubleToByte(dataAges.getAircraftOperationalStatusAge(), 10f);
            list1.add(byte1);
        }

        if (dataAges.getTargetReportDescriptorAge() != null) {
            list.set(0, (byte) (list.get(0) | 0x40));
            byte byte2 = 0;
            byte2 = encodeDoubleToByte(dataAges.getTargetReportDescriptorAge(), 10f);
            list1.add(byte2);
        }

        if (dataAges.getMode3ACodeAge() != null) {
            list.set(0, (byte) (list.get(0) | 0x20));
            byte byte3 = 0;
            byte3 = encodeDoubleToByte(dataAges.getMode3ACodeAge(), 10f);
            list1.add(byte3);
        }

        if (dataAges.getQualityIndicatorsAge() != null) {
            list.set(0, (byte) (list.get(0) | 0x10));
            byte byte4 = 0;
            byte4 = encodeDoubleToByte(dataAges.getQualityIndicatorsAge(), 10f);
            list1.add(byte4);
        }

        if (dataAges.getTrajectoryIntentAge() != null) {
            list.set(0, (byte) (list.get(0) | 0x08));
            byte byte5 = 0;
            byte5 = encodeDoubleToByte(dataAges.getTrajectoryIntentAge(), 10f);
            list1.add(byte5);
        }
        if (dataAges.getMessageAmplitudeAge() != null) {
            list.set(0, (byte) (list.get(0) | 0x04));
            byte byte6 = 0;
            byte6 = encodeDoubleToByte(dataAges.getMessageAmplitudeAge(), 10f);
            list1.add(byte6);
        }
        if (dataAges.getGeometricHeightAge() != null) {
            list.set(0, (byte) (list.get(0) | 0x02));
            byte byte7 = 0;
            byte7 = encodeDoubleToByte(dataAges.getGeometricHeightAge(), 10f);
            list1.add(byte7);
        }
        if (dataAges.checkFieldExtension1()) {
            list.set(0, (byte) (list.get(0) | 0x01));
        } else {
            list.addAll(list1);
            byte[] bytes = new byte[list.size()];
            for (int i = 0; i < list.size(); i++) {
                bytes[i] = list.get(i);
            }
            return bytes;
        }

        list.add((byte) 0);
        if (dataAges.getFlightLevelAge() != null) {
            list.set(1, (byte) 0x80);
            byte byte8 = 0;
            byte8 = encodeDoubleToByte(dataAges.getFlightLevelAge(), 10f);
            list1.add(byte8);
        }

        if (dataAges.getIntermediateStateSelectedAltitudeAge() != null) {
            list.set(1, (byte) (list.get(1) | 0x40));
            byte byte9 = 0;
            byte9 = encodeDoubleToByte(dataAges.getIntermediateStateSelectedAltitudeAge(), 10f);
            list1.add(byte9);
        }

        if (dataAges.getFinalStateSelectedAltitudeAge() != null) {
            list.set(1, (byte) (list.get(1) | 0x20));
            byte byte10 = 0;
            byte10 = encodeDoubleToByte(dataAges.getFinalStateSelectedAltitudeAge(), 10f);
            list1.add(byte10);
        }

        if (dataAges.getAirSpeedAge() != null) {
            list.set(1, (byte) (list.get(1) | 0x10));
            byte byte11 = 0;
            byte11 = encodeDoubleToByte(dataAges.getAirSpeedAge(), 10);
            list1.add(byte11);
        }

        if (dataAges.getTrajectoryIntentAge() != null) {
            list.set(1, (byte) (list.get(1) | 0x08));
            byte byte12 = 0;
            byte12 = encodeDoubleToByte(dataAges.getTrajectoryIntentAge(), 10);
            list1.add(byte12);
        }

        if (dataAges.getMagneticHeadingAge() != null) {
            list.set(1, (byte) (list.get(1) | 0x04));
            byte byte13 = 0;
            byte13 = encodeDoubleToByte(dataAges.getMagneticHeadingAge(), 10);
            list1.add(byte13);
        }

        if (dataAges.getBarometricVerticalRateAge() != null) {
            list.set(1, (byte) (list.get(1) | 0x02));
            byte byte14 = 0;
            byte14 = encodeDoubleToByte(dataAges.getBarometricVerticalRateAge(), 10);
            list1.add(byte14);
        }
        if (dataAges.checkFieldExtension2()) {
            list.set(1, (byte) (list.get(1) | 0x01));
        } else {
            list.addAll(list1);
            byte[] bytes = new byte[list.size()];
            for (int i = 0; i < list.size(); i++) {
                bytes[i] = list.get(i);
            }
            return bytes;
        }
        list.add((byte) 0);

        if (dataAges.getGeometricVerticalRateAge() != null) {
            list.set(2, (byte) 0x80);
            byte byte15 = 0;
            byte15 = encodeDoubleToByte(dataAges.getGeometricVerticalRateAge(), 10);
            list1.add(byte15);
        }

        if (dataAges.getGroundVectorAge() != null) {
            list.set(2, (byte) (list.get(2) | 0x40));
            byte byte16 = 0;
            byte16 = encodeDoubleToByte(dataAges.getGroundVectorAge(), 10);
            list1.add(byte16);
        }

        if (dataAges.getTrackAngleRateAge() != null) {
            list.set(2, (byte) (list.get(2) | 0x20));
            byte byte17 = 0;
            byte17 = encodeDoubleToByte(dataAges.getTrackAngleRateAge(), 10);
            list1.add(byte17);
        }

        if (dataAges.getTargetIdentificationAge() != null) {
            list.set(2, (byte) (list.get(2) | 0x10));
            byte byte18 = 0;
            byte18 = encodeDoubleToByte(dataAges.getTargetIdentificationAge(), 10);
            list1.add(byte18);
        }

        if (dataAges.getTargetStatusAge() != null) {
            list.set(2, (byte) (list.get(2) | 0x08));
            byte byte19 = 0;
            byte19 = encodeDoubleToByte(dataAges.getTargetStatusAge(), 10);
            list1.add(byte19);
        }

        if (dataAges.getMetInformationAge() != null) {
            list.set(2, (byte) (list.get(2) | 0x04));
            byte byte20 = 0;
            byte20 = encodeDoubleToByte(dataAges.getMetInformationAge(), 10);
            list1.add(byte20);
        }

        if (!(dataAges.getRollAngleAge() == null)) {
            list.set(2, (byte) (list.get(2) | 0x02));
            byte byte21 = 0;
            byte21 = encodeDoubleToByte(dataAges.getRollAngleAge(), 10);
            list.add(byte21);
        }
        if (dataAges.checkFieldExtension3()) {
            list.set(2, (byte) (list.get(2) | 0x01));
        } else {
            list.addAll(list1);
            byte[] bytes = new byte[list.size()];
            for (int i = 0; i < list.size(); i++) {
                bytes[i] = list.get(i);
            }
            return bytes;
        }
        list.add((byte) 0);

        if (!(dataAges.getaCASResolutionAdvisoryAge() == null)) {
            list.set(3, (byte) 0x80);
            byte byte22 = 0;
            byte22 = encodeDoubleToByte(dataAges.getaCASResolutionAdvisoryAge(), 10);
            list1.add(byte22);
        }

        if (!(dataAges.getSurfaceCapabilitiesAndCharacteristics() == null)) {
            list.set(3, (byte) (list.get(3) | 0x40));
            byte byte23 = 0;
            byte23 = encodeDoubleToByte(dataAges.getSurfaceCapabilitiesAndCharacteristics(), 10);
            list1.add(byte23);
        }
        list.set(3, (byte) (list.get(3) | 0x01));

        list.addAll(list1);
        byte[] bytes = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) {
            bytes[i] = list.get(i);
        }
        return bytes;
    }

    public static byte[] encodeMessageAmplitude(Byte messageAmplitude) {
        byte byte1 = (byte) (messageAmplitude & 0xFF);
        return new byte[]{byte1};
    }

    public static byte[] encodeServiceManagement(Float serviceManagement) {
        short shortV = (short) (2 * serviceManagement);
        byte array[] = new byte[1];
        array[0] = (byte) (shortV & 0xFF);

        return array;
    }

    public static byte[] encodeMOPSVersion(MOPSVersion mOPSVersion) {
        byte encodedByte1 = 0;

        encodedByte1 |= ((mOPSVersion.isVersionNotSupported() == true ? 1 : 0) & 0x01) << 6;
        encodedByte1 |= (mOPSVersion.getVersionNumber() & 0x07) << 3;
        encodedByte1 |= (mOPSVersion.getLinkTechnologyType() & 0x07);
        return new byte[]{encodedByte1};
    }

    public static byte encodeFloatToByte(float value, float scale) {
        // Scale the value and round it to the nearest integer  
        int scaledValue = Math.round(value * scale);
        // Check for overflow  
        byte encodedByte = 0;
        // Check for overflow based on the number of bytes  
        int maxValue = (1 << 8) - 1;  // max positive value for given number of bytes  
        int minValue = -(1 << 8);  // min value for two's complement  

//         If scaledValue is out of bounds, you need to handle this as per your requirement  
        if (scaledValue < minValue || scaledValue > maxValue) {
            System.out.println("value: " + scaledValue);
            throw new IllegalArgumentException("Scaled value is out of bounds");
        }
        encodedByte = (byte) (scaledValue & 0xFF);
        return encodedByte;
        // Create a byte array to hold the 3 bytes  
    }

    public static byte encodeDoubleToByte(double value, double scale) {
        // Scale the value and round it to the nearest integer  
        long scaledValue = Math.round(value * scale);
        // Check for overflow  
        byte encodedByte = 0;
        // Check for overflow based on the number of bytes  
        int maxValue = (1 << 8) - 1;  // max positive value for given number of bytes  
        int minValue = -(1 << 8);  // min value for two's complement  

//         If scaledValue is out of bounds, you need to handle this as per your requirement  
        if (scaledValue < minValue || scaledValue > maxValue) {
            System.out.println("value: " + scaledValue);
            throw new IllegalArgumentException("Scaled value is out of bounds");
        }
        encodedByte = (byte) (scaledValue & 0xFF);
        return encodedByte;
        // Create a byte array to hold the 3 bytes  
    }

//    
    public static byte[] encodePositiveFloatToByte(float value, float scale, byte numberOfBytes) {
        // Scale the value and round it to the nearest integer  
        int scaledValue = Math.round(value * scale);

        // Check for overflow  
        byte[] bytes = new byte[numberOfBytes];

        // Check for overflow based on the number of bytes  
        int maxValue = (1 << (8 * numberOfBytes)) - 1;  // max positive value for given number of bytes  
        int minValue = -(1 << (8 * numberOfBytes));  // min value for two's complement  

        // If scaledValue is out of bounds, you need to handle this as per your requirement  
        if (scaledValue < minValue || scaledValue > maxValue) {
            throw new IllegalArgumentException("Scaled value is out of bounds");
        }
        for (int i = 0; i < numberOfBytes; i++) {
            bytes[i] = (byte) ((scaledValue >> 8 * (numberOfBytes - i - 1)) & 0xFF);
        }

        return bytes;
        // Create a byte array to hold the 3 bytes  
    }

    public static byte[] encodePositiveDoubleToByte(double value, double scale, byte numberOfBytes) {
        // Scale the value and round it to the nearest integer  
        long scaledValue = Math.round(value * scale);

        // Check for overflow  
        byte[] bytes = new byte[numberOfBytes];

        // Check for overflow based on the number of bytes  
        int maxValue = (1 << (8 * numberOfBytes)) - 1;  // max positive value for given number of bytes  
        int minValue = -(1 << (8 * numberOfBytes));  // min value for two's complement  

        // If scaledValue is out of bounds, you need to handle this as per your requirement  
        if (scaledValue <= 0 || scaledValue > maxValue) {
            throw new IllegalArgumentException("Scaled value is out of bounds");
        }
        for (int i = 0; i < numberOfBytes; i++) {
            bytes[i] = (byte) ((scaledValue >> 8 * (numberOfBytes - i - 1)) & 0xFF);
        }

        return bytes;
        // Create a byte array to hold the 3 bytes  
    }

//    public static byte[] getTimeOfDay(long timeOfDay) {
//        TimeOfDay t  =  new TimeOfDay();
//        t.setValue(timeOfDay);
//        int ellapseTime = Math.round(t.getValue() * 128 / 1000);
//        byte byte1 = (byte) (ellapseTime >> 16 & BitwiseUtils.maskedByte);
//        byte byte2 = (byte) (ellapseTime >> 8 & BitwiseUtils.maskedByte);
//        byte byte3 = (byte) (ellapseTime & BitwiseUtils.maskedByte);
//        return new byte[]{byte1, byte2, byte3};
//    }
    public static byte[] encodeSicSac(byte sic, byte sac) {
        return new byte[]{(byte) (sac & 0xFF), (byte) (sic & 0xFF)};
    }

    private byte[] encodeTargetReportDescriptor(TargetReportDescriptor targetReportDescriptor) {
        byte encodedByte1 = 0;
        encodedByte1 |= (targetReportDescriptor.getAddressType() & 0x07) << 5;
        encodedByte1 |= (targetReportDescriptor.getAltitudeReportingCapability() & 0x03) << 3;
        encodedByte1 |= ((targetReportDescriptor.isIsRangeCheck() == true ? 1 : 0) & 0x01) << 2;
        encodedByte1 |= ((targetReportDescriptor.isIsReportTypeFromTarget() == true ? 1 : 0) & 0x01) << 1;

        if (targetReportDescriptor.checkFieldExtension1()) {
            encodedByte1 |= 0x01;
        } else {
            return new byte[]{encodedByte1};
        }

        byte encodedByte2 = 0;
        encodedByte2 |= ((targetReportDescriptor.isIsDifferentialCorrection() == true ? 1 : 0) & 0x01) << 7;
        encodedByte2 |= ((targetReportDescriptor.isIsGroundBitSet() == true ? 1 : 0) & 0x01) << 6;
        encodedByte2 |= ((targetReportDescriptor.isIsSimulatedTargetReport() == true ? 1 : 0) & 0x01) << 5;
        encodedByte2 |= ((targetReportDescriptor.isIsTestTarget() == true ? 1 : 0) & 0x01) << 4;
        encodedByte2 |= ((targetReportDescriptor.isIsSelectedAltitudeAvailable() == true ? 1 : 0) & 0x01) << 3;
        encodedByte2 |= (targetReportDescriptor.getConfidenceLevel() & 0x02) << 1;

        if (targetReportDescriptor.checkFieldExtension2()) {
            encodedByte2 |= 0x01;
        } else {
            return new byte[]{encodedByte1, encodedByte2};
        }

        byte encodedByte3 = 0;
        encodedByte3 |= ((targetReportDescriptor.isIsIndependentPositionCheck() == true ? 1 : 0) & 0x01) << 5;
        encodedByte3 |= ((targetReportDescriptor.isIsNoGoBitStatus() == true ? 1 : 0) & 0x01) << 4;
        encodedByte3 |= ((targetReportDescriptor.isIsCompactPositionReporting() == true ? 1 : 0) & 0x01) << 3;
        encodedByte3 |= ((targetReportDescriptor.isIsLocalDecodingPositionJump() == true ? 1 : 0) & 0x01) << 2;
        encodedByte3 |= ((targetReportDescriptor.isIsRangeCheckFail() == true ? 1 : 0) & 0x01) << 1;

//        encodedByte3 |= 0x01;
        return new byte[]{encodedByte1, encodedByte2, encodedByte3};
    }

    public static byte[] highResolutionPosition1(Position position) {
        byte[] byteLat = encodeLocation4Byte(position.getLatitude());
        byte[] byteLong = encodeLocation4Byte(position.getLongtitude());
        return new byte[]{byteLat[0], byteLat[1], byteLat[2], byteLat[3], byteLong[0], byteLong[1], byteLong[2], byteLong[3]};
    }

    public static byte[] highResolutionPosition(Position position) {
//        byte[] byteLat = encodeDoubleToByte(position.getLatitude(), 10000000/1.6764, (byte)4);
//        byte[] byteLong = encodeDoubleToByte(position.getLongtitude(),10000000/1.6764, (byte)4);
        byte[] byteLat = encodeToBytes(position.getLatitude(), 10000000 / 1.6764, (byte) 4);
        byte[] byteLong = encodeToBytes(position.getLongtitude(), 10000000 / 1.6764, (byte) 4);

        return new byte[]{byteLat[0], byteLat[1], byteLat[2], byteLat[3], byteLong[0], byteLong[1], byteLong[2], byteLong[3]};
    }

    public static byte[] encodePosition(Position position) {
//        byte[] byteLat = encodeDoubleToByte(position.getLatitude(),100000/2.145767,(byte)3);
//        byte[] byteLong = encodeDoubleToByte(position.getLongtitude(),100000/2.145767,(byte)3);
        byte[] byteLat = encodeToBytes(position.getLatitude(), 100000 / 2.145767, (byte) 3);
        byte[] byteLong = encodeToBytes(position.getLongtitude(), 100000 / 2.145767, (byte) 3);
        return new byte[]{byteLat[0], byteLat[1], byteLat[2], byteLong[0], byteLong[1], byteLong[2]};
    }

    public static byte[] encodeCallSign(String callSign) {
        int[] chars = new int[8];
        for (int i = 0; i < callSign.length(); i++) {
            int index = callSign.charAt(i);
            if (index >= 65) {
                index -= 64;
            }
            chars[i] = index;
        }
        byte b1 = (byte) ((chars[0] << 2) | (chars[1] >> 4 & 0x03));
        byte b2 = (byte) ((chars[1] << 4) | (chars[2] >> 2 & 0x0F));
        byte b3 = (byte) ((chars[2] << 6) | (chars[3] & 0x3F));
        byte b4 = (byte) ((chars[4] << 2) | (chars[5] >> 4 & 0x03));
        byte b5 = (byte) ((chars[5] << 4) | (chars[6] >> 2 & 0x0F));
        byte b6 = (byte) ((chars[6] << 6) | (chars[7] & 0x3F));

        return new byte[]{b1, b2, b3, b4, b5, b6};
    }

    public static byte[] encodeAddress(int value) {
//        int value = Integer.parseInt(targetAddress, 16);
        return new byte[]{(byte) (value >> 16 & 0xFF), (byte) (value >> 8 & 0xFF), (byte) (value & 0xFF)};
    }

    public static byte[] encodeTrueAirSpeed(int trueAirSpeed) {
        if (trueAirSpeed > 32767) {
            return new byte[]{(byte) 0xFF, (byte) 0xFF};
        }
        byte b1 = (byte) (trueAirSpeed >> 8 & 0x7F);
        byte b2 = (byte) (trueAirSpeed & 0xFF);
        return new byte[]{b1, b2};
    }

    public static byte[] encodeFlightLevel(float level) {
        short value = (short) (level * 4);
        return new byte[]{(byte) ((value >> 8) & 0xff), (byte) (value & 0xff)};
    }

    public static byte[] encodeMagneticHeading(double heading) {
        int value = (int) (heading / (360 / Math.pow(2, 16)));
        return new byte[]{(byte) (value & 0xff), (byte) ((value >> 8) & 0xff)};
    }

    public static byte[] encodeAirBorne(AirborneGroundVector airbone) {
        int val1 = airbone.isRangeExceeded() ? 0x80 : 0x00;
        int value = (int) (airbone.getGroundSpeed() / 0.22);
        byte b1 = (byte) (val1 | (value >> 8 & 0x7F));
        byte b2 = (byte) (value & 0xFF);
        // return new byte[] {b1, b2};

        int speed = (int) (airbone.getTrackAngle() / 0.0055);
        byte b3 = (byte) (speed >> 8 & 0xFF);
        byte b4 = (byte) (speed & 0xFF);
        return new byte[]{b1, b2, b3, b4};
    }

    public static byte[] encodeLocation4Byte(double p) {
        ByteBuffer b = ByteBuffer.allocate(4);
        int v = (int) Math.round(p / 0.00000016764);
        b.putInt(v);
        return b.array();
    }

    public static byte[] encodeToBytes(double p, double scaled, int numBytes) {
        // Ensure numBytes is within valid bounds (1 to 8 for encoding a double)
        if (numBytes < 1 || numBytes > 8) {
            throw new IllegalArgumentException("Number of bytes must be between 1 and 8.");
        }

        // Scale the double and round it to fit within the desired byte size
        long scaledValue = Math.round(p * scaled);  // Adjust scaling as needed
        ByteBuffer buffer = ByteBuffer.allocate(8);  // 8 bytes for a long value
        buffer.putLong(scaledValue);  // Store the scaled value as a long

        // Return the specified number of bytes from the most significant part of the buffer
        byte[] fullArray = buffer.array();
        byte[] result = new byte[numBytes];
        System.arraycopy(fullArray, 8 - numBytes, result, 0, numBytes);
        return result;
    }

    public static byte[] encodeFToBytes(float p, float scaled, int numBytes) {
        // Ensure numBytes is within valid bounds (1 to 8 for encoding a double)
        if (numBytes < 1 || numBytes > 8) {
            throw new IllegalArgumentException("Number of bytes must be between 1 and 8.");
        }

        // Scale the double and round it to fit within the desired byte size
        long scaledValue = Math.round(p * scaled);  // Adjust scaling as needed
        ByteBuffer buffer = ByteBuffer.allocate(8);  // 8 bytes for a long value
        buffer.putLong(scaledValue);  // Store the scaled value as a long

        // Return the specified number of bytes from the most significant part of the buffer
        byte[] fullArray = buffer.array();
        byte[] result = new byte[numBytes];
        System.arraycopy(fullArray, 8 - numBytes, result, 0, numBytes);
        return result;
    }

    public static byte[] encodeDToBytes(double p, double scaled, int numBytes) {
        // Ensure numBytes is within valid bounds (1 to 8 for encoding a double)
        if (numBytes < 1 || numBytes > 8) {
            throw new IllegalArgumentException("Number of bytes must be between 1 and 8.");
        }

        // Scale the double and round it to fit within the desired byte size
        long scaledValue = Math.round(p * scaled);  // Adjust scaling as needed
        ByteBuffer buffer = ByteBuffer.allocate(8);  // 8 bytes for a long value
        buffer.putLong(scaledValue);  // Store the scaled value as a long

        // Return the specified number of bytes from the most significant part of the buffer
        byte[] fullArray = buffer.array();
        byte[] result = new byte[numBytes];
        System.arraycopy(fullArray, 8 - numBytes, result, 0, numBytes);
        return result;
    }

    public static byte[] encodeLocation3Byte(double p) {
        ByteBuffer b = ByteBuffer.allocate(4);
        int v = (int) Math.round(p / 0.00002145767);
        b.putInt(v);
        byte[] fullArray = b.array();
        byte[] result = new byte[3];
        System.arraycopy(fullArray, 4 - 3, result, 0, 3);

        return result;
    }

    public BinaryMessage() {
    }

    public BinaryMessage(Cat21Message cat21Message) {
        this.cat21Message = cat21Message;
        this.binaryMessage = encrypt();
    }

    public byte[] getBinaryMessage() {
        return binaryMessage;
    }

    public void setBinaryMessage(byte[] binaryMessage) {
        this.binaryMessage = binaryMessage;
    }

    public Cat21Message getCat21Message() {
        return cat21Message;
    }

    public void setCat21Message(Cat21Message cat21Message) {
        this.cat21Message = cat21Message;
    }

}
