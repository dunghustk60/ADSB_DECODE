/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat21.util;

/**
 *
 * @author Andh
 */
public class BitwiseUtils {
    
    public static int extractBit(int data, int bitNumber, int numberOfBit) {
     
        bitNumber = 8-(bitNumber+numberOfBit - 1);
        if (bitNumber < 0 || bitNumber > 8) {
            return -1;
        }
        if (numberOfBit <= 0 || bitNumber + numberOfBit > 8) {
            return -1;
        }

        // Neu lay 1 bit thi maskBit la 1
        // Neu lay 2 bit thi maskBit la 11
        // Neu lay 3 bit thi maskBit la 111
        int maskBit = 1;
        for (int i = 1; i < numberOfBit; i++) {
            maskBit = maskBit << 1 |1;
        }
        
        int extractedBit = data >> bitNumber & maskBit;
        return extractedBit;
    }
    
    // TODO removing, using  jointByte(int[] bytes) instead of
    public static int jointByte(byte[] bytes) {
        if (bytes.length == 0) return 0;
        int data = bytes[0];
        for (int i = 1; i<bytes.length; i++)
        {
            data = data << 8 | (int) bytes[i];
        }
        return data;
    }
    
    public static int jointByte(int[] bytes)    {
        if (bytes.length == 0) return 0;
        int data = bytes[0];
        for (int i = 1; i<bytes.length; i++)
        {
            data = data << 8 | (int) bytes[i];
        }
        return data;
    }
    
    public static int setBit(int data, int bitNumber, Boolean bit) {
        int masked = 1 << (8 - bitNumber);
        if (bit) {
            data = data | masked;
        } else {
            if ((data & masked) != 0) {
                data = data ^ masked;
            }
        }
        return data;
    }
    
    public static int convertFrom2sComplementNumber (byte[] bytes) {
        int lat = bytes[0] & 0xFF;
        boolean positive = (int) (lat & 0x80) <= 0;
        int length = bytes.length;
        
        if (positive) {
            for (int i = 1; i < length; i++) lat = lat << 8 | (bytes[i] & 0xFF);
        } else {
            lat = ~lat & 0xFF;
            for (int i = 1; i < length; i++) lat = lat << 8 | (~bytes[i] & 0xFF);
            lat = lat + 0x01;
            lat = -lat;
        }
        return lat;
    }
    
    public static int convertFrom2sComplementNumber7bitPerByte (byte[] bytes) {
        int lat = bytes[0] & 0xFF;
        //boolean positive = (int) (lat & 0x80) > 0 ? false : true;
        boolean positive = (int) (lat & 0x80) <= 0;
        int length = bytes.length;
        
        if (positive) {
            lat = lat >> 1;
            for (int i = 1; i < length; i++) lat = lat << 7 | (bytes[i] >> 1) & 0x7F;
        } else {
            lat = ((~lat) >> 1 & 0x7F);
            for (int i = 1; i < length; i++) lat = (lat << 7) | (((~bytes[i]) >> 1) & 0x7F);
            lat = lat + 0x01;
            lat = -lat;
        }
        return lat;
    }
    
    public static int maskedByte = 0xFF;
}
