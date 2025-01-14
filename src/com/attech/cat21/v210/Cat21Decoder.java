/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat21.v210;

import com.attech.cat21.util.BitwiseUtils;
import com.attech.cat21.util.CharacterMap;
import java.nio.Buffer;
// import com.attech.common.message.Message;
// import com.attech.common.message.PositionWGS84;
import java.nio.ByteBuffer;
import java.util.List;


public class Cat21Decoder {

    private static final char[] characters = CharacterMap.getCharacterMap();
    private static final byte[] masked = new byte[]{(byte) 0x80, (byte) 0x40, (byte) 0x20, (byte) 0x10, (byte) 0x08, (byte) 0x04, (byte) 0x02};
    private static final short ITEM_NUMBER = 49;
    private static boolean tracing = false;
   

    //-------------------------------------------------
    //
    //
    //-------------------------------------------------
    public static int decode1(byte[] bytes, List<Cat21Message> messages) {
        if (!validateIndex(0, bytes.length, 3) || (bytes[0] & 0xFF) != 21) return -1;
        final int length = (bytes[1] & 0xFF) << 8 | (bytes[2] & 0xFF);
        if (length != bytes.length) {
            System.out.println(String.format("Warning: Expected length is different from length. (exp: %s)(buffer: %s)", length, bytes.length));
        }

        int index = 3;          // byte 3
        int count = 0;
        int total = 0;
        int stt = 1;
        System.out.println("----------DECODE NEW packet--------------"); 
        while (index < length) {
            final Cat21Message message = new Cat21Message();
            System.out.printf("DECODE time [" + Integer.toString(stt++) + "] - Data bytes: %s, index: %s --- ",bytes.length, index);
            count = decode2(bytes, index, message);
            if (count <= 0) break;
            messages.add(message);
            index += count;
            total += count;
            trace("count byte: " + count);
        }
        System.out.println(">>>>>>>>>> DECODE NEW packet FINISHED ------------ " + "Total " + Integer.toString(stt-1) + " Records"); 
        return total;
    }
    
    //-----------------------------------------------------
    //
    //
    //-----------------------------------------------------
    // Method to check if a specific bit is set to 1
    public static boolean isBitSet(byte value, int bitPosition) {
        // Create a mask with only the bit at bitPosition set to 1
        byte mask = (byte) (1 << bitPosition); // Left shift 1 by the bitPosition

        // Apply the bitwise AND operation between value and mask
        return (value & mask) != 0; // If the result is non-zero, the bit is set
    }
    //-----------------------------------------------------
    //
    //
    //-----------------------------------------------------
      public static int DecodeHeader(byte[] asterixData,int index,boolean [] header) {
              
          int count = 0;
          byte[] b = new byte[2];
          b[0] = asterixData[1];
          b[1] = asterixData[2];
          int LEN = b[0] << 8 | (b[1] & 0xFF);

          // byte c = asterixData[3];
          System.out.println(Integer.toString(index) + " " + Integer.toString(asterixData[0]) + " " + Integer.toString(LEN) + " " + Byte.toString(asterixData[3]) + " " + Byte.toString(asterixData[4]));

          int i = index;
          byte c;
          //#1
          c = asterixData[i++];       // First byte
          String s = String.format("%8s", Integer.toBinaryString(c & 0xFF)).replace(' ', '0');
          System.out.println(s);
          // Check which bits are set to 1
          for (int ii = 7; ii > 0; ii--) {           // 7 to 1
              boolean isSet = isBitSet(c, ii);
              int pos = 7 - ii;
              header[pos] = isSet;
              //    System.out.println("Bit " + ii + " is set to " + (isSet ? "1" : "0"));
          }
          count++;

          
          //#2
          if (isBitSet(c, 0)) {        // Xem con nua khong
              //     System.out.println("Continue");
              c = asterixData[i++];
              s = String.format("%8s", Integer.toBinaryString(c & 0xFF)).replace(' ', '0');
              System.out.println(s);  // i = 4
              for (int ii = 7; ii > 0; ii--) {           // 7 to 1
                  boolean isSet = isBitSet(c, ii);
                  int pos = 7 - ii + 7;
                  header[pos] = isSet;
                  //    System.out.println("Bit " + ii + " is set to " + (isSet ? "1" : "0"));
              }
                count++;
          }

          //#3
          if (isBitSet(c, 0)) {
              c = asterixData[i++];
              s = String.format("%8s", Integer.toBinaryString(c & 0xFF)).replace(' ', '0');
              System.out.println(s);  // i = 4
              for (int ii = 7; ii > 0; ii--) {           // 7 to 1
                  boolean isSet = isBitSet(c, ii);
                  int pos = 7 - ii + 14;
                  header[pos] = isSet;
                  //    System.out.println("Bit " + ii + " is set to " + (isSet ? "1" : "0"));
              }
              count++;
          }
        
        
        //#4
        if(isBitSet(c, 0)) {
            c = asterixData[i++];
            s = String.format("%8s", Integer.toBinaryString(c & 0xFF)).replace(' ', '0');
              System.out.println(s);  // i = 4
              for (int ii = 7; ii > 0; ii--) {           // 7 to 1
                  boolean isSet = isBitSet(c, ii);
                  int pos = 7 - ii + 21; 
                  header[pos] = isSet;
                  //    System.out.println("Bit " + ii + " is set to " + (isSet ? "1" : "0"));
              }
              count++;
        }
        
        //#5
        if(isBitSet(c, 0)) {
            c = asterixData[i++];
            s = String.format("%8s", Integer.toBinaryString(c & 0xFF)).replace(' ', '0');
              System.out.println(s);  // i = 4
              for (int ii = 7; ii > 0; ii--) {           // 7 to 1
                  boolean isSet = isBitSet(c, ii);
                  int pos = 7 - ii + 28; 
                  header[pos] = isSet;
                  //    System.out.println("Bit " + ii + " is set to " + (isSet ? "1" : "0"));
              }
              count++;
        }

        //#6
        if(isBitSet(c, 0)) {
            c = asterixData[i++];
            s = String.format("%8s", Integer.toBinaryString(c & 0xFF)).replace(' ', '0');
              System.out.println(s);  // i = 4
              for (int ii = 7; ii > 0; ii--) {           // 7 to 1
                  boolean isSet = isBitSet(c, ii);
                  int pos = 7 - ii + 35; 
                  header[pos] = isSet;
                  //    System.out.println("Bit " + ii + " is set to " + (isSet ? "1" : "0"));
              }
              count++;
        }
        
          //#7
          boolean b7 = false;
          if (isBitSet(c, 0)) {
              c = asterixData[i++];
              s = String.format("%8s", Integer.toBinaryString(c & 0xFF)).replace(' ', '0');
              System.out.println(s);  // i = 4
              for (int ii = 7; ii > 0; ii--) {           // 7 to 1
                  boolean isSet = isBitSet(c, ii);
                  int pos = 7 - ii + 42; 
                  header[pos] = isSet;
                  //    System.out.println("Bit " + ii + " is set to " + (isSet ? "1" : "0"));
              }
              count++;
              b7 = true;
              
          } 
          
          //#8
          if (isBitSet(c, 0) && b7) {
              c = asterixData[i++];
              s = String.format("%8s", Integer.toBinaryString(c & 0xFF)).replace(' ', '0');
              System.out.println(s);  // i = 4
              for (int ii = 7; ii > 0; ii--) {           // 7 to 1
                  boolean isSet = isBitSet(c, ii);
                  int pos = 7 - ii + 49; 
                  header[pos] = isSet;
                  //    System.out.println("Bit " + ii + " is set to " + (isSet ? "1" : "0"));
              }
              count++;
          } else {
                System.out.println("Decode hearder end");
          }

          
        
       // i++;
        int sic = asterixData[i] & 0xFF;
        i++;
        int sac = asterixData[i] & 0xFF;
        System.out.println("SIC/SAC " + Integer.toString(sic) + " " + Integer.toString(sac));
 
        //String s = toTwosComplement(b1);
        //System.out.println(s);
        if(count < 6) {
             return count;

        }
        return count;
    }
    
    public static int decode2(byte[] bytes, Integer index, Cat21Message message) {
//        int start = index;
        // index la byte dau tien
        int index_mark;
//        try {
            int start = index;
            final boolean [] header = new boolean[ITEM_NUMBER];
            for (int i = 0; i < ITEM_NUMBER; i++) {
                header[i] = false;    
            }
            int count = getHeader(bytes, index, header);        // count se sau header
            if (count == 0) return -1;
            index += count;                                     // data

            for (int i = 0; i < ITEM_NUMBER; i++) {             // kiem tra xem truong nao co trong data
                if (!header[i]) continue;                       // neu khong co
                switch (i) {                                    // neu co

                    case 0: // DataSource Indentification
                        
                        final DataSourceID sourceId = new DataSourceID();
                        count = getDataSourceID(bytes, index, sourceId);
                        if (count <= 0) break;
                        int sac = sourceId.getSac();
                        int sic = sourceId.getSic(); 
                        message.setSac(sac);
                        message.setSic(sic);
                        System.out.println("DataSource Indentification SIC/SAC = " + Integer.toString(message.getSic()) + " " + Integer.toString(message.getSac()));
                        index += count;
                        break;

                    case 1: // Target Report Descriptor
                        // System.out.println("Target Report Descriptor");
                        final TargetReportDescriptor targetDesciptor = new TargetReportDescriptor();
                        count = getTargetReportDescriptor(bytes, index, targetDesciptor);
                        if (count <= 0) break;
                        index += count;
                        message.setTargetDescriptor(targetDesciptor);
                        break;

                    case 2: // Track Number
                        // System.out.println("Track Number");
                        final IValue value = new IValue();
                        count = getTrackNumber(bytes, index, value);
                        if (count <= 0) break;
                        index += count;
                        message.setTrackNumber(value.getValue());
                        break;

                    case 3: // Service Identification
                        // System.out.println("Service Identification");
                        final IValue serviceID = new IValue();
                        count = getServiceInformation(bytes, index, serviceID);
                        if (count <= 0) break;
                        index += count;
                        message.setServiceIdentification(serviceID.getValue());
                        break;

                    case 4: // Time of Applicability for Position
                        System.out.println("Time of Applicability for Position");
                        final DValue timeOfAplicabilityPos = new DValue();
                        count = getTime(bytes, index, timeOfAplicabilityPos);
                        if (count <= 0) break;
                        index += count;
                        message.setTimeOfAplicabilityPosition(timeOfAplicabilityPos.getValue());
                        break;

                    case 5: // I021/130 Position in WGS-84 co-ordinates 6
                        // System.out.println("Position in WGS-84 co-ordinates");
                        index_mark = index;
                        final Position position = new Position();
                        count = getPosition(bytes, index, position);
                        if (count <= 0) break;
                        index += count;
                        message.setPosition(position);
                        System.out.println("======> Pos " + Double.toString(position.getLatitude()) + " " + Double.toString(position.getLongtitude()));
                        System.arraycopy(bytes, index_mark, message.positionByte, 0, 6);
                        
                        break;

                    case 6: // I021/131 Position in WGS-84 co-ordinates, high res. 8
                        // System.out.println("Position in WGS-84 co-ordinates, high res.");
                        index_mark = index;
                        final Position highResPosition = new Position();
                        count = getHighResolutionPosition(bytes, index, highResPosition);
                        if (count <= 0) break;
                        index += count;
                        message.setHighResolutionPosition(highResPosition);
                        System.arraycopy(bytes, index_mark, message.hirespositionByte, 0, 8);
                        break;

                    case 7: // Time of Applicability for Velocity
                         
                        final DValue timeOfApplicabVelo = new DValue();
                        count = getTime(bytes, index, timeOfApplicabVelo);
                        if (count <= 0) break;
                        index += count;
                        message.setTimeOfAplicabilityVelocity(timeOfApplicabVelo.getValue());
                        System.out.println("Time of Applicability for Velocity. " + Double.toString(timeOfApplicabVelo.getValue()));
                        break;

                    case 8: // Air Speed
                        // System.out.println("Air Speed");
                        final AirSpeed airspeed = new AirSpeed();
                        count = getAirSpeed(bytes, index, airspeed);
                        if (count <= 0) break;
                        index += count;
                        message.setAirSpeed(airspeed);
                        break;

                    case 9: // True Air Speed
                        // System.out.println("True Air Speed");
                        final IValue trueAirSpeed = new IValue();
                        count = getTrueAirSpeed(bytes, index, trueAirSpeed);
                        if (count <= 0) break;
                        index += count;
                        message.setTrueAirSpeed(trueAirSpeed);
                        break;

                    case 10: // Target Address
                        // System.out.println("Target Address");
                        final IValue address = new IValue();
                        count = getTargetAddress(bytes, index, address);
                        if (count <= 0) break;
                        index += count;
                        message.setTargetAddress(address.getValue());
                        //System.out.println("TARGET ICAO 24 BIT ADDRESS " + ));
                        System.out.printf("TARGET ICAO 24 BIT ADDRESS: %X%n", address.getValue());

                        break;

                    case 11: // Time of Message Reception of Position
                        final DValue timeOfApplicabPos =new DValue();
                        count = getTime(bytes, index, timeOfApplicabPos);
                        if (count <= 0) break;
                        index += count;
                        message.setTimeOfMessageReceptionOfPosition(timeOfApplicabPos.getValue());
                        System.out.println("Time of Message Reception of Position " + Double.toString(timeOfApplicabPos.getValue()));

                        break;

                    case 12: // Time of Message Reception of Position-High Precision
                        
                        final HighResolutionTimeSecond highResTime = new HighResolutionTimeSecond();
                        count = getTimeOfHighResolution(bytes, index, highResTime);
                        if (count <= 0) break;
                        index += count;
                        message.setTimeOfMessageReceptionOfPositionHightPrecisions(highResTime);
                        System.out.println("Time of Message Reception of Position-High Precision" + Double.toString(highResTime.getValue()));
                        break;

                    case 13: // Time of Message Reception of Velocity
                        
                        final DValue timeReceivedVelo = new DValue();
                        count = getTime(bytes, index, timeReceivedVelo);
                        if (count <= 0) break;
                        index += count;
                        message.setTimeOfMessageReceptionOfVelocity(timeReceivedVelo.getValue());
                        System.out.println("Time of Message Reception of Velocity " + Double.toString(timeReceivedVelo.getValue()));
                        break;

                    case 14: // Time of Message Reception of Velocity-High Precision
                        final HighResolutionTimeSecond highResTimeOfReceivedVelo = new HighResolutionTimeSecond();
                        count = getTimeOfHighResolution(bytes, index, highResTimeOfReceivedVelo);
                        if (count <= 0) break;
                        index += count;
                        message.setTimeOfMessageReceptionOfVelocityHightPrecision(highResTimeOfReceivedVelo);
                        break;

                    case 15: // Geometric Height
                        // System.out.println("Geometric Height");
                        final DValue geometricHeight = new DValue();
                        count = getGeometricHeight(bytes, index, geometricHeight);
                        if (count <= 0) break;
                        index += count;
                        message.setGeometricHeight(geometricHeight.getValue());
                        break;

                    case 16: // Quality Indicators
                        // System.out.println("Quality Indicators");
                        final QualityIndicator indicator = new QualityIndicator();
                        count = getQualityIndicators(bytes, index, indicator);
                        if (count <= 0) break;
                        index += count;
                        message.setQualityIndicator(indicator);
                        break;

                    case 17: // MOPS Version
                        // System.out.println("MOPS Version");
                        final MOPSVersion mposVersion = new MOPSVersion();
                        count = getMOPSVersion(bytes, index, mposVersion);
                        if (count <= 0) break;
                        index += count;
                        message.setMopsVersion(mposVersion);
                        break;

                    case 18: // Mode 3/A Code
                        // System.out.println("Mode 3/A Code");
                        final IValue code3A = new IValue();
                        count = getCode3A(bytes, index, code3A);
                        if (count <= 0) break;
                        index += count;
                        message.setMode3a(code3A.getValue());
                        break;

                    case 19: // Roll Angle
                        // System.out.println("Roll Angle");
                        final DValue rollAngle = new DValue();
                        count = getRollAngle(bytes, index, rollAngle);
                        if (count <= 0) break;
                        index += count;
                        message.setRollAgle(rollAngle.getValue());
                        break;

                    case 20: // Flight Level
                        // System.out.println("Flight Level");
                        final FValue flightLevel = new FValue();
                        count = getFlighLevel(bytes, index, flightLevel);
                        if (count <= 0) break;
                        index += count;
                        message.setFlightLevel(flightLevel.getValue());
                        break;

                    case 21: // Magnetic Heading
                        // System.out.println("Magnetic Heading");
                        final DValue magneticHeading = new DValue();
                        count = getMagneticHeading(bytes, index, magneticHeading);
                        if (count <= 0) break;
                        index += count;
                        message.setMagneticHeading(magneticHeading.getValue());
                        break;

                    case 22: // Target Status
                        // System.out.println("Target Status");
                        final TargetStatus targetStatus = new TargetStatus();
                        count = getTargetStatus(bytes, index, targetStatus);
                        if (count <= 0) break;
                        index += count;
                        message.setTargetStatus(targetStatus);
                        break;

                    case 23: // Barometric Vertical Rate
                        // System.out.println("Barometric Vertical Rate");
                        final DValue baroVerticalRate = new DValue();
                        count = getBarometricVerticalRate(bytes, index, baroVerticalRate);
                        if (count <= 0) break;
                        index += count;
                        message.setBarometricVerticalRate(baroVerticalRate);
                        break;

                    case 24: // Geometric Vertical Rate
                        // System.out.println("Geometric Vertical Rate");
                        final DValue geometricVerticalRate = new DValue();
                        count = getBarometricVerticalRate(bytes, index, geometricVerticalRate);
                        if (count <= 0) break;
                        index += count;
                        message.setGeometricVerticalRate(geometricVerticalRate);
                        break;

                    case 25: // Airborne Ground Vector
                        // System.out.println("Airborne Ground Vector");
                        final AirborneGroundVector airbornGroundVector = new AirborneGroundVector();
                        count = getAirborneGroundVector(bytes, index, airbornGroundVector);
                        if (count <= 0) break;
                        index += count;
                        message.setAirborneGroundVector(airbornGroundVector);
                        break;

                    case 26: // Track Angle Rate
                        // System.out.println("Track Angle Rate");
                        final DValue trackAngleRate = new DValue();
                        count = getTrackAngleRate(bytes, index, trackAngleRate);
                        if (count <= 0) break;
                        index += count;
                        message.setTrackAngleRate(trackAngleRate.getValue());
                        break;

                    case 27: // Time of Report Transmission Data Item I021/077, Time of ASTERIX Report Transmission
                        // System.out.println("Time of Report Transmission");
                        final DValue tranmissionTime = new DValue();
                        count = getTime(bytes, index, tranmissionTime);
                        if (count <= 0) break;
                        index += count;
                        message.setTimeOfReportTranmission(tranmissionTime.getValue());
                        System.out.println("Time of Report Transmission " + Double.toString(tranmissionTime.getValue()));
                        break;

                    case 28: // Target Identification
                        // System.out.println("Target Identification");
                        final SValue callsign = new SValue();
                        count = getTargetIden(bytes, index, callsign);
                        if (count <= 0) break;
                        index += count;
                        String cs = callsign.toString();
                        System.out.println("Target Callsign = " + cs);
                        message.setCallSign(callsign.toString());
                        break;

                    case 29: // Emitter Category
                        // System.out.println("Emitter Category");
                        if (!validateIndex(index, bytes.length, 1)) break;
                        message.setEmitterCategory((short) (bytes[index++] & 0xFF));
                        break;

                    case 30: // Met Information
                        // System.out.println("Met Information");
                        final MetInformation metInfo = new MetInformation();
                        count = getMetInformation(bytes, index, metInfo);
                        if (count <= 0) break;
                        index += count;
                        message.setMetInformation(metInfo);
                        break;

                    case 31: // Selected Altitude
                        // System.out.println("Selected Altitude");
                        final SelectedAltitude selAltitude = new SelectedAltitude();
                        count = getSelectedAltitude(bytes, index, selAltitude);
                        if (count <= 0) break;
                        index += count;
                        message.setSelectedAltitude(selAltitude);
                        break;

                    case 32: // Final State Selected Altitude
                        // System.out.println("Final State Selected Altitude");
                        final FinalStateSelectedAltitude finalStateSelAltitude = new FinalStateSelectedAltitude();
                        count = getFinalStateSeletectAltitude(bytes, index, finalStateSelAltitude);
                        if (count <= 0) break;
                        index += count;
                        message.setFinalStateSelectedAltitude(finalStateSelAltitude);
                        break;

                    case 33: // Trajectory Intent
                        // System.out.println("Trajectory Intent");
                        final TrajectoryIntent intent = new TrajectoryIntent();
                        count = getTrajectoryIntent(bytes, index, intent);
                        if (count <= 0) break;
                        index += count;
                        message.setTracjectoryIntent(intent);
                        break;

                    case 34: // Service Management
                        // System.out.println("Service Management");
                        if (!validateIndex(index, bytes.length, 1)) break;
                        message.setServiceManagement((bytes[index++] & 0xFF) * 0.5f);
                        break;

                    case 35: // Aircraft Operational Status
                        // System.out.println("Aircraft Operational Status");
                        final AircraftOperationalStatus aircraftOperationStatus = new AircraftOperationalStatus();
                        count = getAircraftOperationStatus(bytes, index, aircraftOperationStatus);
                        if (count <= 0) break;
                        index += count;
                        message.setAircraftOperationStatus(aircraftOperationStatus);
                        break;

                    case 36: // Surface Capabilities and Characteristics
                        // System.out.println("Surface Capabilities and Characteristics");
                        final SurfaceCapabilitiesAndCharacterics sufaceCapability = new SurfaceCapabilitiesAndCharacterics();
                        count = getSurfaceCapabilitiesAndCharacterics(bytes, index, sufaceCapability);
                        if (count <= 0) break;
                        index += count;
                        message.setSurfaceCapabilitiesAndCharacterics(sufaceCapability);
                        break;

                    case 37: // Message Amplitude
                        // System.out.println("Message Amplitude");
                        if (!validateIndex(index, bytes.length, 1)) break;
                        message.setMessageAmplitude(bytes[index++] & 0xFF);
                        break;

                    case 38: // Mode S MB Data
                        // System.out.println("Mode S MB Data");
                        final ModeSMBData modeSData = new ModeSMBData();
                        count = getModeSMDData(bytes, index, modeSData);
                        if (count <= 0) break;
                        index += count;
                        message.setModeSMBData(modeSData);
                        break;

                    case 39: // ACAS Resolution Advisory Report
                        // System.out.println("ACAS Resolution Advisory Report");
                        final ASCASResolutionAdvisoryReport report = new ASCASResolutionAdvisoryReport();
                        count = getASCASResolutionAdvisorReport(bytes, index, report);
                        if (count <= 0) break;
                        index += count;
                        message.setaCASResolutionAdvisoryReport(report);
                        break;

                    case 40: // Receiver ID
                        // System.out.println("Receiver ID");
                        if (!validateIndex(index, bytes.length, 1)) break;
                        message.setReceiverId((short) (bytes[index++] * 0xFF));
                        break;

                    case 41: // Data Ages
                        // System.out.println("Data Ages");
                        final DataAges dataAges = new DataAges();
                        count = getDataAges(bytes, index, dataAges);
                        if (count <= 0) break;
                        index += count;
                        message.setDataAges(dataAges);
                        break;

                    case 42: // Not Used
                        break;
                    case 43: // Not Used
                        break;
                    case 44: // Not Used
                        break;
                    case 45: // Not Used
                        break;
                    case 46: // Not Used
                        break;
                    case 47: // Reserved Expansion Field
                        int reservedExpansionFieldLength = bytes[index] & 0xFF;
                        index += reservedExpansionFieldLength;
                        if(reservedExpansionFieldLength > 0){
                            byte[] rEF = new byte[reservedExpansionFieldLength];
                            rEF[0] = (byte)(reservedExpansionFieldLength & 0xFF);
                            System.arraycopy(bytes, index-reservedExpansionFieldLength, rEF, 0, reservedExpansionFieldLength);
                            message.setReservedExpansionFieldLength(rEF);
                        }
                        break;
                    case 48: // Special Purpose Field

                        int specialPurposeFieldLength = bytes[index] & 0xFF;
                        index += specialPurposeFieldLength;
                        if(specialPurposeFieldLength > 0){
                            byte[] sPF = new byte[specialPurposeFieldLength & 0xFF];
                            sPF[0] = (byte) specialPurposeFieldLength;
                            System.arraycopy(bytes, index-specialPurposeFieldLength+1, sPF, 1, specialPurposeFieldLength-1);
                            
                            message.setSpecialPurposeFieldLength(sPF);
                        }
                        break;
                    default:
                        break;
                }
            }
            
            final int contentLen = index - start;
            final byte[] contentsByte = new byte[contentLen];
            System.out.printf("$$ DEBUG DECODE: bytes:%s, start:%s, contentsByte:%s, contentLen:%s%n",bytes.length, start, contentsByte.length, contentLen);
            if (start + contentLen > bytes.length)
                return -1;
            System.arraycopy(bytes, start, contentsByte, 0, contentLen);        // Copy
            
            //contentsByte[0] = 21;
            message.setBytes(contentsByte);
            //return contentLen;
            
            //final int contentLen = index - start;
            /*
            final int lngth = contentLen + 3;
            // System.out.printf("Decode from %d - %d length: %d%n", start, index, contentLen);
            final byte[] contentsByte = new byte[lngth];
            System.arraycopy(bytes, start, contentsByte, 3, contentLen);
            contentsByte[0] = 21;
            contentsByte[1] = (byte) (lngth >> 8);
            contentsByte[2] = (byte) (lngth & 0xFF);
            message.setBytes(contentsByte);
            */
            // Tong so byte da giai ma
            System.out.println("Tong so byte da giai ma " + Integer.toString(contentLen));
            return contentLen;
//        }
        /*
        catch (Exception ex) {
            //System.out.printf("DEBUG DECODE: bytes:%s, start:%s, contentsByte:%s, 1, contentLen:%s%n",bytes, start, contentsByte, contentLen);
            System.out.println("Error when decode!");
            return -1;
        }
        */
//        finally{
//            final int contentLen = index - start;
//            final int lngth = contentLen + 3;
//            final byte[] contentsByte = new byte[lngth];
//            System.arraycopy(bytes, start, contentsByte, 3, contentLen);
//            contentsByte[0] = 21;
//            contentsByte[1] = (byte) (lngth >> 8);
//            contentsByte[2] = (byte) (lngth & 0xFF);
//            message.setBytes(contentsByte);
//            return contentLen;
//        }
    }
    
    public synchronized static void setTrace(boolean value) {
        tracing = value;
    }
    
    public static void trace(String message) {
        if (!tracing) return;
        System.out.println(message);
    }
    
    private static int getHeader(byte[] bytes, int index, boolean [] header) {
        // boolean [] header = new boolean[ITEM_NUMBER];
 //       boolean isExtend = true;
 //       int bit;
        int headerIndex = 0;
        int le = bytes.length;
        headerIndex = DecodeHeader(bytes,index,header);
        /*
        while (isExtend) {
            
            System.out.println("Do dai " + Integer.toString(le) + " , " + Integer.toString(index));
            byte currentByte = bytes[index];
            for (int i = 1; i < 8; i++) {
                bit = currentByte & masked[i - 1];
                if (bit == 0) continue;
                int ind = headerIndex * 7 + i - 1;
                if (ind >= ITEM_NUMBER) {
                    System.out.println(String.format("Error while reading header: index is out of boundary of array size. (index: %s) (size: %s)", ind, ITEM_NUMBER));
                    return -1;
                }
                header[ind] = true;
            }

            bit = currentByte & 0x01;
            isExtend = (bit != 0);
            index++;
            headerIndex++; // Increasing readed bytes
        }
*/
        return headerIndex;
    }

    private static int getTrackAngleRate(byte[] bytes, int index, DValue angleRate) {
        if (!validateIndex(index, bytes.length, 2)) return -1;
        double value = (double) (ByteBuffer.wrap(new byte[]{0x00, 0x00, bytes[index++], bytes[index++]}).getInt() / 32);
        angleRate.setValue(value);
        return 2;
    }
    
    private static int skipTrackAngleRate(byte[] bytes, int index) {
        if (!validateIndex(index, bytes.length, 2)) return -1;
        return 2;
    }
    
    private static int getCode3A(byte[] bytes, Integer startIndex, IValue value) {
        if (!validateIndex(startIndex, bytes.length, 2)) return -1;
        int val = ByteBuffer.wrap(new byte[]{0x00, 0x00, bytes[startIndex++], bytes[startIndex++]}).getInt();
        String sValue = Integer.toOctalString(val);
        val = Integer.parseInt(sValue);
        value.setValue(val);
        return 2;
    }

    private static int getServiceInformation(byte[] bytes, int index, IValue serviceinfo) {
        if (!validateIndex(index, bytes.length, 1)) return -1;
        int value = bytes[index++] & 0xFF;
        serviceinfo.setValue(value);
        return 1;
    }
    
    private static int skipServiceInformation(byte[] bytes, int index) {
        if (!validateIndex(index, bytes.length, 1)) return -1;
        return 1;
    }
    
    private static int getDataSourceID(byte[] bytes, int index, DataSourceID sourceId) {
        if (!validateIndex(index, bytes.length, 2)) return -1;
        int sac = bytes[index++] & 0xFF;
        int sic = bytes[index++] & 0xFF;        
        sourceId.setSac(sac);
        sourceId.setSic(sic);
      //  System.out.println("DataSource Indentification SIC/SAC = " + Integer.toString(sic) + " " + Integer.toString(sac));
        return 2;       // byte next
    }
    
    private static int getTargetReportDescriptor(byte[] bytes, Integer index, TargetReportDescriptor targerDescriptor) {
        
        if (!validateIndex(index, bytes.length, 1)) return -1;
        byte byteHigh = bytes[index++];
        
        int value = byteHigh >> 5 & 0x07;
        targerDescriptor.setAddressType(value);
        System.out.printf("**************Padded Hexadecimal: %08x%n", value);
        
        value = byteHigh >> 3 & 0x03;
        targerDescriptor.setAltitudeReportingCapability(value);
        
        boolean bValue = (byteHigh & 0x04) > 0;
        targerDescriptor.setIsRangeCheck(bValue);
        
        bValue = (byteHigh & 0x02) > 0;
        targerDescriptor.setIsReportTypeFromTarget(bValue);
        
        // Check extention
        boolean isEnd = (byteHigh & 0x01) == 0;
        if (isEnd) return 1;
        
        // Extention 01
        if (!validateIndex(index, bytes.length, 1)) return -1;
        byteHigh = bytes[index++];
        //index++;
        
        bValue = (byteHigh & 0x80) > 0;
        targerDescriptor.setIsDifferentialCorrection(bValue);
        
        bValue = (byteHigh & 0x40) > 0;
        targerDescriptor.setIsGroundBitSet(bValue);
        
        bValue = (byteHigh & 0x20) > 0;
        targerDescriptor.setIsSimulatedTargetReport(bValue);
        
        bValue = (byteHigh & 0x10) > 0;
        targerDescriptor.setIsTestTarget(bValue);
        
        bValue = (byteHigh & 0x08) > 0;
        targerDescriptor.setIsSelectedAltitudeAvailable(bValue);
        
        value = byteHigh >> 1 & 0x03;
        targerDescriptor.setConfidenceLevel(value);
        
        // Check extention
        isEnd = (byteHigh & 0x01) == 0;
        if (isEnd) return 2;
        
        // Extention 02
        if (!validateIndex(index, bytes.length, 1)) return -1;
        byteHigh = bytes[index++];
        //index++;
        
        bValue = (byteHigh & 0x20) > 0;
        targerDescriptor.setIsDifferentialCorrection(bValue);
        
        bValue = (byteHigh & 0x10) > 0;
        targerDescriptor.setIsNoGoBitStatus(bValue);
        
        bValue = (byteHigh & 0x08) > 0;
        targerDescriptor.setIsCompactPositionReporting(bValue);
        
        bValue = (byteHigh & 0x04) > 0;
        targerDescriptor.setIsLocalDecodingPositionJump(bValue);
        
        bValue = (byteHigh & 0x02) > 0;
        targerDescriptor.setIsRangeCheckFail(bValue);
        // Check extention
        isEnd = (byteHigh & 0x01) == 0;
        if (isEnd) return 3;
        // add 27 Dec 2024
        int r = 3;
//        while(true){
//            r++;
//            isEnd = (bytes[index++] & 0x01) == 0;
//            if(isEnd) break;
//        }
        return r;
        
    }
    
    private static int skipTargetReportDescriptor(byte[] bytes, int index) {
        
        if (!validateIndex(index, bytes.length, 1)) return -1;
        byte byteHigh = bytes[index++];
        boolean isEnd = (byteHigh & 0x01) == 0;
        if (isEnd) return 1;
        
        // Extention 01
        if (!validateIndex(index, bytes.length, 1)) return -1;
        byteHigh = bytes[index++];
        // Check extention
        isEnd = (byteHigh & 0x01) == 0;
        if (isEnd) return 2;
        
        // Extention 02
        if (!validateIndex(index, bytes.length, 1)) return -1;
        byteHigh = bytes[index++];
        return 3;
    }
    
    private static int getTrackNumber(byte[] bytes, int index, IValue value) {
        if (!validateIndex(index, bytes.length, 2)) return -1;
        int number = ByteBuffer.wrap(new byte[]{0x00, 0x00, bytes[index++], bytes[index++]}).getInt();
        value.setValue(number);
        return 2;
    }
    
    private static int skipTrackNumber(byte[] bytes, int index) {
        if (!validateIndex(index, bytes.length, 2)) return -1;
        return 2;
    }
    
    private static int getTime(byte[] bytes, int index, DValue time) {
        if (!validateIndex(index, bytes.length, 3)) return -1;
        int value = ByteBuffer.wrap(new byte[]{0x00, bytes[index++], bytes[index++], bytes[index++]}).getInt();
        double timeVal = (double) value / 128;
        time.setValue(timeVal);
        return 3;
    }
    
    private static int skipTime(byte[] bytes, int index) {
        if (!validateIndex(index, bytes.length, 3)) return -1;
        return 3;
    }
    
    private static int getTimeOfHighResolution(byte[] bytes, int index, HighResolutionTimeSecond time) {
        // HighResolutionTimeSecond time = new HighResolutionTimeSecond();
        if (!validateIndex(index, bytes.length, 4)) return -1;

        byte byteHigh = bytes[index++];
        short value = (short) (byteHigh >> 6 & 0x03);
        time.setFullSecondIndication(value);
//        long iValue = ((byteHigh & 0x3f) << 24);
//        iValue |=  ((bytes[index++]&0xFF)<<16);
//        iValue |=  ((bytes[index++]&0xFF)<<8);
//        iValue |=  ((bytes[index++]&0xFF));
        int iValue = ByteBuffer.wrap(new byte[]{(byte) (byteHigh & 0x3f), bytes[index++], bytes[index++], bytes[index++]}).getInt();
        time.setValue(iValue *Math.pow(2, -30)); 
        // Dung fix 0.9313 to Math.pow(2,-30)
        return 4;
    }
    
    private static int skipTimeOfHighResolution(byte[] bytes, int index) {
        // HighResolutionTimeSecond time = new HighResolutionTimeSecond();
        if (!validateIndex(index, bytes.length, 4)) return -1;
        return 4;
    }
    
    private static int getPosition(byte[] bytes, int index, Position position) {
        // Position position = new Position();
        if (!validateIndex(index, bytes.length, 6)) {
            return -1;
        }

        byte[] latBytes = new byte[]{bytes[index++], bytes[index++], bytes[index++]};
        int value = BitwiseUtils.convertFrom2sComplementNumber(latBytes);
        position.setLatitude(value * 2.145767 * 0.00001);

        latBytes = new byte[]{bytes[index++], bytes[index++], bytes[index++]};
        value = BitwiseUtils.convertFrom2sComplementNumber(latBytes);
        position.setLongtitude(value * 2.145767 * 0.00001);
        return 6;
    }
    
    private static int getHighResolutionPosition(byte[] bytes, int index, Position position) {
        if (!validateIndex(index, bytes.length, 8)) return -1;

        int value = ((bytes[index++] & 0xFF) << 24) | ((bytes[index++] & 0xFF) << 16) | ((bytes[index++] & 0xFF) << 8) | (bytes[index++] & 0xFF);
        position.setLatitude(value * 0.00000016764);
        value = ((bytes[index++] & 0xFF) << 24) | ((bytes[index++] & 0xFF) << 16) | ((bytes[index++] & 0xFF) << 8) | (bytes[index++] & 0xFF);
        position.setLongtitude(value * 0.00000016764);

        return 8;
    }
    
    private static int getAirSpeed(byte[] bytes, int index, AirSpeed airspeed) {
        
        if (!validateIndex(index, bytes.length, 2)) return -1;

        byte byteHight = bytes[index++];
        int speedUnit = byteHight & 0x80;
        AirSpeed airSpeed = new AirSpeed();
        airSpeed.setUnit(speedUnit > 0);

        // this.setUnit(BitwiseUtils.extractBit(byteHight, 8, 1));
        int speedValue = byteHight & 0x7F;
        speedValue = speedValue << 8 | bytes[index++];
        double speed = speedValue * (speedUnit > 0 ? Math.pow(2, -14) : 0.001);
        airSpeed.setValue(speed);
        return 2;
    }
    
    private static int skipAirSpeed(byte[] bytes, int index) {
        if (!validateIndex(index, bytes.length, 2)) return -1;
        return 2;
    }
    
    private static int getTrueAirSpeed(byte[] bytes, int index, IValue value) {
        // IValue value = new IValue();
        if (!validateIndex(index, bytes.length, 2)) return -1;

        byte byteHigh = bytes[index++];
        
        boolean isRangeExceeded = (byteHigh & 0x80) > 0;
        value.setIsRangeExceeded(isRangeExceeded);
        
        int speedValue = byteHigh & 0x7F;
        // Dung fix Jan 13 2025
        speedValue = (((speedValue << 8)& 0xFFFF) | (bytes[index++]&0xFF));
        value.setValue(speedValue);
        
        return 2;
    }
    
    private static int skipTrueAirSpeed(byte[] bytes, int index) {
        if (!validateIndex(index, bytes.length, 2)) return -1;
        return 2;
    }
    
    private static int getTargetAddress(byte[] bytes, int index, IValue address) {
        if (!validateIndex(index, bytes.length, 3)) return -1;
        int value = (bytes[index++] & 0xFF) << 16 | (bytes[index++] & 0xFF) << 8 | (bytes[index++] & 0xFF);
        address.setValue(value);
        return 3;
    }    
    
    private static int getGeometricHeight(byte[] bytes, int index, DValue height) {
        if (!validateIndex(index, bytes.length, 2)) return -1;
        int value = BitwiseUtils.convertFrom2sComplementNumber(new byte[]{bytes[index++], bytes[index++]});
        double dVal = value * 6.25;
        height.setValue(dVal);
        return 2;
    }
    
    private static int skipGeometricHeight(byte[] bytes, int index) {
        if (!validateIndex(index, bytes.length, 2)) return -1;
        return 2;
    }
    
    private static int getMOPSVersion(byte[] bytes, int index, MOPSVersion mopsVersion) {
        // MOPSVersion mopsVersion = new MOPSVersion();
        if (!validateIndex(index, bytes.length, 1)) return -1;

        int byteHigh = bytes[index++];
        
        boolean bValue = (byteHigh & 0x40) > 0;
        mopsVersion.setVersionNotSupported(bValue);
        
        short iValue = (short) (byteHigh >> 3 & 0x07);
        mopsVersion.setVersionNumber(iValue);
        
        iValue = (short) (byteHigh & 0x07);
        mopsVersion.setLinkTechnologyType(iValue);
        
        return 1;
    }
    
    private static int skipMOPSVersion(byte[] bytes, int index) {
        // MOPSVersion mopsVersion = new MOPSVersion();
        if (!validateIndex(index, bytes.length, 1)) return -1;
        return 1;
    }
    
    private static int getQualityIndicators(byte[] bytes, int index, QualityIndicator indicator) {

        // QualityIndicator indicator = new QualityIndicator();
        if (!validateIndex(index, bytes.length, 1)) return -1;
        
        byte byte01 = bytes[index++];  // BYTE 1
        short value = (short) ((byte01 >> 5) & 0x07);
        indicator.setnACForVelocity(value);
        
        value = (short) ((byte01 >> 1) & 0x0F);
        indicator.setnIC(value);
        
        boolean extention = (byte01 & 0x01) > 0;
        if (!extention) return 1;
        
        // Sub field 01
        if (!validateIndex(index, bytes.length, 1)) return -1;
        byte01 = bytes[index++]; // BYTE 2
        boolean bValue = ((byte01 >> 7) & 0x01) > 0;
        indicator.setnICForBarometricAltitude(bValue);
        
        value = (short) ((byte01 >> 5) & 0x03);
        indicator.setsIL(value);
        
        value = (short) ((byte01 >> 1) & 0x0F);
        indicator.setnACForPosition(value);
        
        extention = (byte01 & 0x01) > 0;
        if (!extention) return 2;
        
        // Sub field 02
        if (!validateIndex(index, bytes.length, 1)) return -1;
        byte01 = bytes[index++]; // BYTE 3
        bValue = ((byte01 >> 5) & 0x01) > 0;
        indicator.setSilSupplement(bValue);
        
        value = (short) ((byte01 >> 3) & 0x03);
        indicator.setSystemDesignAssuranceLevel(value);
        
        value = (short) ((byte01 >> 1) & 0x03);
        indicator.setGeometricAltAcc(value);
        
        extention = (byte01 & 0x01) > 0;
        if (!extention) return 3;
        
        // Sub field 03
        if (!validateIndex(index, bytes.length, 1)) return -1;
        byte01 = bytes[index++]; // BYTE 4
        
        value = (short) ((byte01 >> 4) & 0x0F);
        // Dung fix Jan 11 2025
        indicator.setPositionIntegrityCategory(value);
        
        extention = (byte01 & 0x01) > 0;
        int count = 4;
        while (extention) {
            if (!validateIndex(index, bytes.length, 1)) return -1;
            extention = (bytes[index++] & 0x01) == 0;
            count++;
        }

        return count;
    }
    
    private static int skipQualityIndicators(byte[] bytes, int index) {

        // QualityIndicator indicator = new QualityIndicator();
        if (!validateIndex(index, bytes.length, 1)) return -1;
        
        byte byte01 = bytes[index++];  // BYTE 1
        boolean extention = (byte01 & 0x01) > 0;
        if (!extention) return 1;
        
        // Sub field 01
        if (!validateIndex(index, bytes.length, 1)) return -1;
        byte01 = bytes[index++]; // BYTE 2
        extention = (byte01 & 0x01) > 0;
        if (!extention) return 2;
        
        // Sub field 02
        if (!validateIndex(index, bytes.length, 1)) return -1;
        byte01 = bytes[index++]; // BYTE 3
        extention = (byte01 & 0x01) > 0;
        if (!extention) return 3;
        
        // Sub field 03
        if (!validateIndex(index, bytes.length, 1)) return -1;
        byte01 = bytes[index++]; // BYTE 4
        extention = (byte01 & 0x01) > 0;
        int count = 4;
        while (extention) {
            if (!validateIndex(index, bytes.length, 1)) return -1;
            extention = (bytes[index++] & 0x01) == 0;
            count++;
        }

        return count;
    }
    
    private static int getRollAngle(byte[] bytes, int index, DValue rollAngle) {
        if (!validateIndex(index, bytes.length, 2)) return -1;
        byte[] bts = new byte[]{bytes[index++], bytes[index++]};
        int intValue = BitwiseUtils.convertFrom2sComplementNumber(bts);
        double val = intValue * 0.01;
        rollAngle.setValue(val);
        return 2;
    }
    
    private static int skipRollAngle(byte[] bytes, int index) {
        if (!validateIndex(index, bytes.length, 2)) return -1;
        return 2;
    }
    
    private static int getFlighLevel(byte[] bytes, Integer startIndex, FValue flightLevel)  {
        if (!validateIndex(startIndex, bytes.length, 2)) return -1;
        byte[] bts = new byte[]{bytes[startIndex++], bytes[startIndex++]};
        int value = BitwiseUtils.convertFrom2sComplementNumber(bts);
        float val = (float) value / 4;
        flightLevel.setValue(val);
        return 2;
    }
    
    private static int getTargetStatus(byte [] bytes, Integer startIndex, TargetStatus targetStatus) {
        if (!validateIndex(startIndex, bytes.length, 1)) return -1;
        
        // TargetStatus targetStatus = new TargetStatus();
        byte byteHigh = bytes[startIndex++];
       
        boolean bValue = (byteHigh & 0x80) > 0;
        targetStatus.setIntentChangeFlag(bValue);
        
        bValue = (byteHigh & 0x40) > 0;
        targetStatus.setlNAVMode(bValue);
        
        //Dung add Jan 13 2025
        bValue = (byteHigh & 0x20) > 0;
        targetStatus.setMilitaryEmergency(bValue);
        
        short sValue = (short) (byteHigh >> 2 & 0x07);
        targetStatus.setPriorityStatus(sValue);
        // Dung fix Jan 13 2025
        sValue = (short) (byteHigh & 0x03);
        targetStatus.setSurveillanceStatus(sValue);
        
        return 1;
    }
    
    private static int skipTargetStatus(byte [] bytes, Integer startIndex) {
        if (!validateIndex(startIndex, bytes.length, 1)) return -1;
        return 1;
    }
    
    private static int getBarometricVerticalRate(byte [] bytes, int index, DValue dValue) {
        // DValue dValue = new DValue();
        if (!validateIndex(index, bytes.length, 2)) return -1;
        byte byteHigh = bytes[index++];
        boolean isRangeExceeded = (byteHigh & 0x80) > 0;
        dValue.setIsRangeExceeded(isRangeExceeded);
        
        int iValue = 0;
//        boolean positive = (int) (byteHigh & 0x40) <= 0;
//        
//        if (positive) {
//            iValue = (byteHigh & 0x7F) << 8 | bytes[index++] & 0xFF;
//        } else {
//            iValue = (~(byteHigh & 0x7F) << 8 | ~bytes[index++]) & 0xFF;
//            iValue = iValue + 0x01;
//            iValue = -iValue;
//        }
        // Dung comment Jan 11 2025
        iValue = ByteBuffer.wrap(new byte[]{0,0,(byte) (byteHigh & 0x7f), bytes[index++]}).getInt();
       
        // System.out.println(">> " + Integer.toBinaryString(byteHigh & 0xFF) + " - " + Integer.toBinaryString(bytes[index] & 0xFF) + " : " + iValue);
        dValue.setValue((double) iValue* 6.25);
        return 2;
    }
    
    private static int skipBarometricVerticalRate(byte [] bytes, int index) {
        // DValue dValue = new DValue();
        if (!validateIndex(index, bytes.length, 2)) return -1;
        return 2;
    }
    
    private static int getAirborneGroundVector(byte[] bytes, int index, AirborneGroundVector airbone) {
        // AirborneGroundVector airbone = new AirborneGroundVector();
        if (!validateIndex(index, bytes.length, 4)) return -1;

        byte byteHigh = bytes[index++];
        boolean isRangeExceeded = (byteHigh & 0x80) > 0;
        airbone.setRangeExceeded(isRangeExceeded);
        
        int iValue = ByteBuffer.wrap(new byte[] { 0x00, 0x00, (byte)(byteHigh & 0x7F), bytes[index++] }).getInt();
        airbone.setGroundSpeed(iValue* 0.22);
        
//        iValue = (bytes[index++]&0xFF);
//        iValue = iValue << 8;
//        iValue |= (bytes[index++]&0xFF);
        iValue = ByteBuffer.wrap(new byte[] { 0x00, 0x00, bytes[index++], bytes[index++] }).getInt();
        airbone.setTrackAngle(iValue* 0.0055);
        // Dung fix 0.0055 to 360/2^16 = 360/65536 Jan 11 2025
        return 4;
    }
    
    private static int skipAirborneGroundVector(byte[] bytes, int index) {
        // AirborneGroundVector airbone = new AirborneGroundVector();
        if (!validateIndex(index, bytes.length, 4)) return -1;
        return 4;
    }
    
    private static int getSelectedAltitude(byte[] bytes, int index, SelectedAltitude altitude) {
        // SelectedAltitude altitude = new SelectedAltitude();
        if (!validateIndex(index, bytes.length, 2)) return -1;
        byte byteHigh = bytes[index++];
        boolean bValue = (byteHigh & 0x80) > 0;
        altitude.setIsSourceAvailability(bValue);
        
        short sValue = (short) (byteHigh >> 5 & 0x03);
        altitude.setSource(sValue);
        
        int iValue = BitwiseUtils.convertFrom2sComplementNumber(new byte[] { (byte)(byteHigh & 0x1F), bytes[index++]}) * 25;
        altitude.setAltitude(iValue);
        return 2;
    }
    
    private static int skipSelectedAltitude(byte[] bytes, int index) {
        // SelectedAltitude altitude = new SelectedAltitude();
        if (!validateIndex(index, bytes.length, 2)) return -1;
        return 2;
    }
    
    private static int getMetInformation(byte[] bytes, int index, MetInformation metInfo) {
        
        if (!validateIndex(index, bytes.length, 1)) return -1;
        int counting = 1;
        
        byte header = bytes[index++];
        // get header
        metInfo.setIsHasWindSpeed((header & 0x80)  > 0);
        metInfo.setIsHasWindDirection((header & 0x40)  > 0);
        metInfo.setIsHasTemperature((header & 0x20)  > 0);
        metInfo.setIsHasTurbulence((header & 0x10)  > 0);
        
        if (metInfo.isIsHasWindSpeed()) {
            if (!validateIndex(index, bytes.length, 2)) return -1;
            int value = bytes[index++] & 0xFF;
            value = value << 8 | bytes[index++];
            metInfo.setWindSpeed(value);
            counting += 2;
        }
        
        if (metInfo.isIsHasWindDirection()) {
            if (!validateIndex(index, bytes.length, 2)) return -1;
            int value = bytes[index++] & 0xFF;
            value = value << 8 | bytes[index++];
            metInfo.setWindDirection(value);
            counting += 2;
        }

        if (metInfo.isIsHasTemperature()) {
            if (!validateIndex(index, bytes.length, 2)) return -1;
            byte[] tempBytes = new byte[]{bytes[index++], bytes[index++]};
            int value = BitwiseUtils.convertFrom2sComplementNumber(tempBytes);
            metInfo.setTemperature((int) (value * 0.25f));
            counting += 2;
        }
        
        if (metInfo.isIsHasTurbulence()) {
            if (!validateIndex(index, bytes.length, 1)) return -1;
            metInfo.setTurbulence(bytes[index++] & 0xFF);
            counting += 1;
        }
        
        return counting;
    }
    
    private static int skipMetInformation(byte[] bytes, int index) {
        
        if (!validateIndex(index, bytes.length, 1)) return -1;
        int counting = 1;
        byte header = bytes[index++];
        
        // get header
        boolean hasWindSpeed = (header & 0x80)  > 0;
        boolean hasWindDirection = ((header & 0x40)  > 0);
        boolean hasTemperature = ((header & 0x20)  > 0);
        boolean hasTurbulence = ((header & 0x10)  > 0);
        
        if (hasWindSpeed) counting += 2;
        if (hasWindDirection) counting += 2;
        if (hasTemperature) counting += 2;
        if (hasTurbulence)counting += 1;
        return counting;
    }
    
    private static int getFinalStateSeletectAltitude(byte[] bytes, Integer startIndex, FinalStateSelectedAltitude altitude) {
        if (!validateIndex(startIndex, bytes.length, 2)) return -1;
        byte byteHigh = bytes[startIndex++];
        boolean bValue = (byteHigh & 0x80) > 0;
        altitude.setIsManageVerticalModeActive(bValue);
        
        bValue = (byteHigh & 0x40) > 0;
        altitude.setIsAltitudeHoldModeActive(bValue);
        
        bValue = (byteHigh & 0x20) > 0;
        altitude.setIsApproachModeActive(bValue);
                
        int iValue = BitwiseUtils.convertFrom2sComplementNumber(new byte[] { (byte)(byteHigh & 0x1F), bytes[startIndex++]}) * 25;
        altitude.setAltitude(iValue);
        return 2;
    }
    
    private static int skipFinalStateSeletectAltitude(byte[] bytes, int index) {
        if (!validateIndex(index, bytes.length, 2)) return -1;
        return 2;
    }
    
    private static int getTrajectoryIntent(byte[] bytes, int index, TrajectoryIntent trajectory) {
        if (!validateIndex(index, bytes.length, 1)) return -1;
        byte data = bytes[index++];
        trajectory.setIsHasSubField1((data & 0x80) > 0);
        trajectory.setIsHasSubField2((data & 0x40) > 0);
        int counting = 1;

        boolean extention = (data & 0x01) > 0;
        if (extention == false) {
            return counting;
        }

        if (trajectory.isIsHasSubField1()) {
            data = bytes[index++];
            counting++;
            trajectory.setIsDataAvailable((data & 0x80) > 0);
            trajectory.setIsDataValid((data & 0x40) > 0);
            extention = (data & 0x01) > 0;
            
        }

        if (extention) {
            trajectory.setRepetitionFactor(bytes[index++] & 0xFF);
            counting++;

            data = bytes[index++];
            counting++;
            trajectory.setIsTcpNumberAvailable((data & 0x80) > 0);
            trajectory.setIsTcpCompliance((data & 0x40) > 0);
            trajectory.setTcpNumber(data & 0x3F);

            // get Altitude 2 byte in 2 's complement
            byte[] altitudeBytes = new byte[]{bytes[index++], bytes[index++]};
            counting += 2;
            int value = BitwiseUtils.convertFrom2sComplementNumber(altitudeBytes);
            trajectory.setAltitude(value * 10);

            // latitude
            byte[] latitudeBytes = new byte[]{bytes[index++], bytes[index++], bytes[index++]};
            counting += 3;
            value = BitwiseUtils.convertFrom2sComplementNumber(latitudeBytes);
            trajectory.setLatitude((float) (value * 2.145767 * 0.00001));

            // longtitude
            byte[] longtitudeBytes = new byte[]{bytes[index++], bytes[index++], bytes[index++]};
            counting += 3;
            value = BitwiseUtils.convertFrom2sComplementNumber(longtitudeBytes);
            trajectory.setLongtitle((float) (value * 2.145767 * 0.00001));

            data = bytes[index++];
            counting++;
            trajectory.setPointType(data >> 4 & 0x0f);
            trajectory.setTurnDirection(data >> 2 & 0x03);
            trajectory.setIsTurnRadiusAvailable((data & 0x02) > 0);
            trajectory.setIsTimeOverPointAvailable((data & 0x01) > 0);

            value = bytes[index++] & 0xFF;
            value = value << 8 | (bytes[index++] & 0xFF);
            value = value << 8 | (bytes[index++] & 0xFF);
            counting+=3;
            trajectory.setTimeOverPoint(value);


            value = bytes[index++] & 0xFF;
            value = value << 8 | (bytes[index++] & 0xFF);
            counting+=2;
            trajectory.setTcpTurnRadius(value * 0.01f);
        }
        return counting;
    }
    
    private static int skipTrajectoryIntent(byte[] bytes, int index) {
        if (!validateIndex(index, bytes.length, 1)) return -1;
        byte data = bytes[index++];
        int counting = 1;
        boolean isHasSubField1 = ((data & 0x80) > 0);
        boolean isHasSubField2= ((data & 0x40) > 0);
        

        boolean extention = (data & 0x01) > 0;
        if (extention == false) return counting;

        if (isHasSubField1) {
            data = bytes[index++];
            extention = (data & 0x01) > 0;
            counting++;
        }

        if (extention) counting+=16;
        return counting;
    }
    
    private static int getAircraftOperationStatus(byte[] bytes, Integer startIndex, AircraftOperationalStatus operationStatus) {
        // AircraftOperationalStatus operationStatus = new AircraftOperationalStatus();
        if (!validateIndex(startIndex, bytes.length, 1)) return -1;
        
        byte byteHigh = bytes[startIndex++];
        boolean bValue = (byteHigh & 0x80) > 0;
        operationStatus.setIsResolutionAdvisoryActive(bValue);
        
        int iValue = byteHigh >> 5 & 0x03;
        operationStatus.setTargetTrajectoryChangeReportCapability(iValue);
        
        bValue = (byteHigh & 0x10) > 0;
        operationStatus.setIsTargetStateReportCapability(bValue);
        
        bValue = (byteHigh & 0x08) > 0;
        operationStatus.setIsAirReferencedVelocityReportCapability(bValue);
        
        bValue = (byteHigh & 0x04) > 0;
        operationStatus.setIsCockpitDisplayOfTrafficInformationAirborne(bValue);
        
        bValue = (byteHigh & 0x02) > 0;
        operationStatus.setIsTCASSystemStatus(bValue);
        
        bValue = (byteHigh & 0x01) > 0;
        operationStatus.setIsSingleAntenna(bValue);
        
        return 1;
    }
    
    private static int skipAircraftOperationStatus(byte[] bytes, int index) {
        // AircraftOperationalStatus operationStatus = new AircraftOperationalStatus();
        if (!validateIndex(index, bytes.length, 1)) return -1;
        return 1;
    }
    
    private static int getSurfaceCapabilitiesAndCharacterics(byte [] bytes, int index, SurfaceCapabilitiesAndCharacterics surfaceCapobilityAndCharaterics) {
        // SurfaceCapabilitiesAndCharacterics surfaceCapobilityAndCharaterics = new SurfaceCapabilitiesAndCharacterics();
        if (!validateIndex(index, bytes.length, 1)) return -1;
        byte byteHigh= bytes[index++];
        
        boolean bValue = (byteHigh & 0x20) > 0;
        surfaceCapobilityAndCharaterics.setPositionOffSetApplied(bValue);
        
        bValue = (byteHigh & 0x10) > 0;
        surfaceCapobilityAndCharaterics.setCockpitDisplayOfTrafficInformationSurface(bValue);
        
        bValue = (byteHigh & 0x08) > 0;
        surfaceCapobilityAndCharaterics.setB2low(bValue);
        
        bValue = (byteHigh & 0x04) > 0;
        surfaceCapobilityAndCharaterics.setReceivingATCServices(bValue);
        
        bValue = (byteHigh & 0x02) > 0;
        surfaceCapobilityAndCharaterics.setIndent(bValue);
        
        bValue = (byteHigh & 0x01) > 0;
        if (!bValue) return 1;
        
        if (!validateIndex(index, bytes.length, 1)) return -1;
        byteHigh = bytes[index++];
        
        int iValue = byteHigh & 0x0F;
        surfaceCapobilityAndCharaterics.setLengthWidth(iValue);
        return 2;
    }
    
    private static int skipSurfaceCapabilitiesAndCharacterics(byte [] bytes, int index) {
        // SurfaceCapabilitiesAndCharacterics surfaceCapobilityAndCharaterics = new SurfaceCapabilitiesAndCharacterics();
        if (!validateIndex(index, bytes.length, 1)) return -1;
        return 2;
    }
    
    private static int getModeSMDData(byte [] bytes, int index, ModeSMBData modeSData) {
        // ModeSMBData modeSData = new ModeSMBData();
        if (!validateIndex(index, bytes.length, 9)) return -1;
        modeSData.setRepetitionFactor((short) (bytes[index++] & 0xFF));
        modeSData.setMessage(new byte[]{
            bytes[index++], 
            bytes[index++], 
            bytes[index++], 
            bytes[index++], 
            bytes[index++], 
            bytes[index++], 
            bytes[index++]});
        
        byte byteHigh = bytes[index++];
        short sValue = (short) (byteHigh >> 4 & 0x0F);
        modeSData.setBDS1(sValue);
        
        sValue = (short) (byteHigh & 0x0F);
        modeSData.setBDS2(sValue);
        
        return 9;
    }
    
    private static int skipModeSMDData(byte [] bytes, int index) {
        // ModeSMBData modeSData = new ModeSMBData();
        if (!validateIndex(index, bytes.length, 9)) return -1;
        return 9;
    }
    
    private static int getASCASResolutionAdvisorReport(byte[] bytes, int index, ASCASResolutionAdvisoryReport ascasResolutionAvisoryReport) {
        // ASCASResolutionAdvisoryReport ascasResolutionAvisoryReport = new ASCASResolutionAdvisoryReport();
        if (!validateIndex(index, bytes.length, 7)) return -1;
        
        byte byteHigh = bytes[index++];
        short sValue = (short) (byteHigh >> 3 & 0x1F);
        ascasResolutionAvisoryReport.setMessageType(sValue);
        
        sValue = (short) (byteHigh & 0x07);
        ascasResolutionAvisoryReport.setMessageSubType(sValue);
        
        byteHigh = bytes[index++];
        byte byteLow = bytes[index++];
//        int a = ByteBuffer.wrap(new byte[]{0,0,byteHigh, (byte)((byteLow >> 2)&0x3F)}).getInt();
// Dung comment Jan 13 2025
        sValue = (short) (((byteHigh<<6)&0x3FFF) | ((byteLow >> 2)&0x3F));
        ascasResolutionAvisoryReport.setActiveResolutionAdvisories(sValue);
        
        byteHigh = bytes[index++];
        
        sValue = (short) (((byteLow & 0x03) << 2) | ((byteHigh >> 6) & 0x03));
        ascasResolutionAvisoryReport.setRACRecord(sValue);
        
        boolean bValue = (byteHigh & 0x20) > 0;
        ascasResolutionAvisoryReport.setrATerminated(bValue);
        
        bValue = (byteHigh & 0x10) > 0;
        ascasResolutionAvisoryReport.setMultipleThreatEncounter(bValue);
        
        sValue = (short) (byteHigh >> 2 & 0x03);
        ascasResolutionAvisoryReport.setThreatTypeIndicator(sValue);
        
        int iValue = ByteBuffer.wrap(new byte[]{(byte) (byteHigh & 0x03), bytes[index++], bytes[index++], bytes[index++]}).getInt();
        ascasResolutionAvisoryReport.setThreatIdentityData(iValue);
        
        return 7;
    }
    
    private static int skipASCASResolutionAdvisorReport(byte[] bytes, int index) {
        // ASCASResolutionAdvisoryReport ascasResolutionAvisoryReport = new ASCASResolutionAdvisoryReport();
        if (!validateIndex(index, bytes.length, 7)) return -1;
        return 7;
    }
    
    private static int getMagneticHeading(byte[] bytes, Integer index, DValue magneticHeading) {
        if (!validateIndex(index, bytes.length, 2)) return -1;
        int heading = bytes[index++] & 0xFF;
        heading = heading << 8 | (bytes[index++] & 0xFF);
        double value = heading * 360 / Math.pow(2, 16);
        magneticHeading.setValue(value);
        return 2;
    }
    
    private static int skipMagneticHeading(byte[] bytes, Integer index) {
        if (!validateIndex(index, bytes.length, 2)) return -1;
        return 2;
    }
    
    private static int getTargetIden(byte[] bytes, int index, SValue callSign) {
        
        if (!validateIndex(index, bytes.length, 6)) return -1;
        
        StringBuilder builder = new StringBuilder();
        byte[] byts = new byte[]{bytes[index++], bytes[index++], bytes[index++]};
        builder.append(getCode(byts));

        byts = new byte[]{bytes[index++], bytes[index++], bytes[index++]};
        builder.append(getCode(byts));

        callSign.setValue(builder.toString());
        return 6;
    }
    
    private static int getDataAges(byte[] bytes, Integer startIndex, DataAges dataAges) {
        final int maxValue = 28;
        int bIndex = 0;
        boolean [] bValues = new boolean[maxValue];
        boolean isEnd = false;
        int counting = 0;
        
        while (!isEnd){
//            if (!validateIndex(startIndex, bytes.length, 1)) return -1;
            byte byteHigh = bytes[startIndex++];
            counting++;
            if (bIndex >= maxValue) break;
            bValues[bIndex++] = (byteHigh & 0x80) > 0;
            
            if (bIndex >= maxValue) break;
            bValues[bIndex++] = (byteHigh & 0x40) > 0;
            // Dung write Jan 12 2025
            if(counting == 4) break;
            
            if (bIndex >= maxValue) break;
            bValues[bIndex++] = (byteHigh & 0x20) > 0;
            
            if (bIndex >= maxValue) break;
            bValues[bIndex++] = (byteHigh & 0x10) > 0;
            
            if (bIndex >= maxValue) break;
            bValues[bIndex++] = (byteHigh & 0x08) > 0;
            
            if (bIndex >= maxValue) break;
            bValues[bIndex++] = (byteHigh & 0x04) > 0;
            
            if (bIndex >= maxValue) break;
            bValues[bIndex++] = (byteHigh & 0x02) > 0;
            
            isEnd = (byteHigh & 0x01) == 0;
        } 
        
        for (int i = 0; i < maxValue; i++) {
//            if (!validateIndex(startIndex, bytes.length, 1)) return -1;
            if (!bValues[i]) continue;
            switch(i) {
                case 0: 
                    dataAges.setAircraftOperationalStatusAge((bytes[startIndex++] & 0xFF) * 0.1);
                    counting++;
                    break;
                case 1: 
                    dataAges.setTargetReportDescriptorAge((bytes[startIndex++] & 0xFF) * 0.1);
                    counting++;
                    break;
                case 2: 
                    dataAges.setMode3ACodeAge((bytes[startIndex++] & 0xFF) * 0.1);
                    counting++;
                    break;
                case 3: 
                    dataAges.setQualityIndicatorsAge((bytes[startIndex++] & 0xFF) * 0.1);
                    counting++;
                    break;
                case 4: 
                    dataAges.setTrajectoryIntentAge((bytes[startIndex++] & 0xFF) * 0.1);
                    counting++;
                    break;
                case 5: 
                    dataAges.setMessageAmplitudeAge((bytes[startIndex++] & 0xFF) * 0.1);
                    counting++;
                    break;
                case 6: 
                    dataAges.setGeometricHeightAge((bytes[startIndex++] & 0xFF) * 0.1);
                    counting++;
                    break;
                case 7: 
                    dataAges.setFlightLevelAge((bytes[startIndex++] & 0xFF) * 0.1);
                    counting++;
                    break;
                case 8: 
                    dataAges.setIntermediateStateSelectedAltitudeAge((bytes[startIndex++] & 0xFF) * 0.1);
                    counting++;
                    break;
                case 9: 
                    dataAges.setFinalStateSelectedAltitudeAge((bytes[startIndex++] & 0xFF) * 0.1);
                    counting++;
                    break;
                case 10: 
                    dataAges.setAirSpeedAge((bytes[startIndex++] & 0xFF) * 0.1);
                    counting++;
                    break;
                case 11: 
                    dataAges.setTrueAirSpeedAge((bytes[startIndex++] & 0xFF) * 0.1);
                    counting++;
                    break;
                case 12: 
                    dataAges.setMagneticHeadingAge((bytes[startIndex++] & 0xFF) * 0.1);
                    counting++;
                    break;
                case 13: 
                    dataAges.setBarometricVerticalRateAge((bytes[startIndex++] & 0xFF) * 0.1);
                    counting++;
                    break;
                case 14: 
                    dataAges.setGeometricVerticalRateAge((bytes[startIndex++] & 0xFF) * 0.1);
                    counting++;
                    break;
                case 15: 
                    dataAges.setGroundVectorAge((bytes[startIndex++] & 0xFF) * 0.1);
                    counting++;
                    break;
                case 16: 
                    dataAges.setTrackAngleRateAge((bytes[startIndex++] & 0xFF) * 0.1);
                    counting++;
                    break;
                case 17: 
                    dataAges.setTargetIdentificationAge((bytes[startIndex++] & 0xFF) * 0.1);
                    counting++;
                    break;
                case 18: 
                    dataAges.setTargetStatusAge((bytes[startIndex++] & 0xFF) * 0.1);
                    counting++;
                    break;
                case 19: 
                    dataAges.setMetInformationAge((bytes[startIndex++] & 0xFF) * 0.1);
                    counting++;
                    break;
                case 20: 
                    dataAges.setRollAngleAge((bytes[startIndex++] & 0xFF) * 0.1);
                    counting++;
                    break;
                case 21: 
                    dataAges.setaCASResolutionAdvisoryAge((bytes[startIndex++] & 0xFF) * 0.1);
                    counting++;
                    break;
                case 22: 
                    dataAges.setSurfaceCapabilitiesAndCharacteristics((bytes[startIndex++] & 0xFF) * 0.1);
                    counting++;
                    break;
            }
        }
        return counting;
    }
    
    private static int skipDataAges(byte[] bytes, int index) {
        final int maxValue = 28;
        int bIndex = 0;
        boolean [] bValues = new boolean[maxValue];
        boolean isEnd = false;
        int counting = 0;
        
        while (!isEnd){
            if (!validateIndex(index, bytes.length, 1)) return -1;
            byte byteHigh = bytes[index++];
            counting++;
            if (bIndex >= maxValue) break;
            bValues[bIndex++] = (byteHigh & 0x80) > 0;
            
            if (bIndex >= maxValue) break;
            bValues[bIndex++] = (byteHigh & 0x40) > 0;
            
            if (bIndex >= maxValue) break;
            bValues[bIndex++] = (byteHigh & 0x20) > 0;
            
            if (bIndex >= maxValue) break;
            bValues[bIndex++] = (byteHigh & 0x10) > 0;
            
            if (bIndex >= maxValue) break;
            bValues[bIndex++] = (byteHigh & 0x08) > 0;
            
            if (bIndex >= maxValue) break;
            bValues[bIndex++] = (byteHigh & 0x04) > 0;
            
            if (bIndex >= maxValue) break;
            bValues[bIndex++] = (byteHigh & 0x02) > 0;
            
            isEnd = (byteHigh & 0x01) == 0;
        } 
        
        for (int i = 0; i < maxValue; i++) {
            if (!validateIndex(index, bytes.length, 1)) return -1;
            if (!bValues[i]) continue;
            if (i <= 22) counting++;
        }
        return counting;
    }
    
    private static char getChar(int i) {
        
        if (i <=26) return (char) (i+64);
        if (i==32) return ' ';
        return (char) i;
    }
    
    private static boolean validateIndex(int current, int length, int numOfByte) {
        if (current + numOfByte > length) {
            //System.out.println(String.format("Error while reading message: Index is out of boundary of array size (index: %s)(size: %s)", (current + numOfByte), length));
            return false;
        }
        return true;
    }
    
    private static String getCode(byte[] byts) {
        StringBuilder builder = new StringBuilder();
        
        int code = byts[0] >> 2 & 0x3F;
        if (code != 0) builder.append(getChar(code));

        code = (byts[0] & 0x03) << 4 | (byts[1] >> 4 & 0x0F);
        if (code != 0) builder.append(getChar(code));
        
        code = (byts[1] & 0x0F) << 2 | (byts[2] >> 6 & 0x03);
        if (code != 0) builder.append(getChar(code));

        code = byts[2] & 0x3F;
        if (code != 0) builder.append(getChar(code));
        return builder.toString();
    }
}
