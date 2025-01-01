/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adsb_decode;

import com.attech.cat21.v210.Cat21Message;

/**
 *
 * @author tranduc
 */
public class RecordsSent {
    
     
     long startTime;
     public int targetAddress;
     public byte data[] = null;
     byte[] pos = new byte[6];
     byte[] poshires= new byte[8];
     public String Callsign;
     
    public void add_data(Cat21Message cat21Decoded) {
        byte[] d = cat21Decoded.getBytes();
        data = new byte[d.length];
        
        System.arraycopy(cat21Decoded.positionByte, 0, pos, 0, pos.length);
        System.arraycopy(cat21Decoded.hirespositionByte, 0, poshires, 0, poshires.length);
        
        startTime = System.nanoTime()/1000;     // Thoi diem add
    }
    
    
}
