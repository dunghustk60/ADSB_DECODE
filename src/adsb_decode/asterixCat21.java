/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adsb_decode;

/**
 *
 * @author tranduc
 */
public class asterixCat21 {
   
    
    public asterixCat21() {
    }
    //-----------------------------------------------------
    //
    //
    //-----------------------------------------------------
    public static String toTwosComplement(int number) {
        // Get the binary string representation of the number
        String binary = Integer.toBinaryString(number);
        // If the number is negative, we need to handle the sign extension
        if (number < 0) {
            // For negative numbers, make sure it represents a 32-bit integer
            return String.format("%32s", binary).replace(' ', '0');
        }
        // For positive numbers, just pad to 32 bits (if needed)
        return String.format("%32s", binary).replace(' ', '0');
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
    public void Detectbit(byte[] asterixData) {
    
        
            
    }
    //-----------------------------------------------------
    //
    //
    //-----------------------------------------------------    
    public void Decode(byte[] asterixData,int length) {
              
        byte[] b = new byte[2];
        b[0] = asterixData[1];
        b[1] = asterixData[2];
        int LEN = b[0] << 8 | (b[1] & 0xFF);

       // byte c = asterixData[3];
        
        System.out.println(Integer.toString(length) + " " + Integer.toString(asterixData[0]) + " " + Integer.toString(LEN) + " " + Byte.toString(asterixData[4]) + " " + Byte.toString(asterixData[4]));
      
        
        int  i = 3;
        byte c;
        c = asterixData[i++];
        System.out.println(String.format("1- %8s", Integer.toBinaryString(c & 0xFF)).replace(' ', '0'));
                // Check which bits are set to 1
        for (int ii = 7; ii >=0 ; ii--) {
            boolean isSet = isBitSet(c, ii);
            System.out.println("Bit " + ii + " is set to " + (isSet ? "1" : "0"));
        }
        
        if( (c & 0x01) == 0x01) {
            System.out.println("Continue");
            c = asterixData[i++];
            System.out.println(String.format("2- %8s", Integer.toBinaryString(c & 0xFF)).replace(' ', '0'));
        }
        
        if( (c & 0x01) == 0x01) {
        c = asterixData[i++];
        System.out.println(String.format("3- %8s", Integer.toBinaryString(c & 0xFF)).replace(' ', '0'));
        }
        
        if( (c & 0x01) == 0x01) {
            c = asterixData[i++];
            System.out.println(String.format("4- %8s", Integer.toBinaryString(c & 0xFF)).replace(' ', '0'));
        }
        
        if( (c & 0x01) == 0x01) {
            c = asterixData[i++];
            System.out.println(String.format("5- %8s", Integer.toBinaryString(c & 0xFF)).replace(' ', '0'));
        }

        if( (c & 0x01) == 0x01) {
            c = asterixData[i++];
            System.out.println(String.format("6- %8s", Integer.toBinaryString(c & 0xFF)).replace(' ', '0'));
        }
        
        if( (c & 0x01) == 0x01) {
            c = asterixData[i++];
            System.out.println(String.format("7- %8s", Integer.toBinaryString(c & 0xFF)).replace(' ', '0'));
        }
        
        if( (c & 0x01) == 0x01) {
            c = asterixData[i++];
            System.out.println(String.format("8- %8s", Integer.toBinaryString(c & 0xFF)).replace(' ', '0'));
        }
        
       // i++;
        int b1 = (byte)asterixData[i++] & (byte)0xff;
        int b2 = asterixData[i] & 0xFF;
        System.out.println("SIC/SAC " + Integer.toString(b1) + " " + Integer.toString(b1));
 
        //String s = toTwosComplement(b1);
        //System.out.println(s);
        
    }
}
