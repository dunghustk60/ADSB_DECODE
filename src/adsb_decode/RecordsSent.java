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
    
     // version va data dung de conver
    
     private int verion;    // 1 = 1.3 ; 2 = 2.1 ; 3 = 2.4
     long startTime;
     public int targetAddress;
     public byte data[] = null;         // data asterix cat 21 duoc forwarn tu may thu
     byte[] pos = new byte[6];
     byte[] poshires= new byte[8];
     public String Callsign;
     
    public void add_data(Cat21Message cat21Decoded) {
        byte[] d = cat21Decoded.getBytes();
        this.data = new byte[d.length];
        
        System.arraycopy(cat21Decoded.positionByte, 0, pos, 0, pos.length);
        System.arraycopy(cat21Decoded.hirespositionByte, 0, poshires, 0, poshires.length);
        System.arraycopy(d, 0, this.data , 0, d.length);
        
        startTime = System.nanoTime()/1000;     // Thoi diem add
    }

    /**
     * @return the verion
     */
    public int getVerion() {
        return verion;
    }

    /**
     * @param verion the verion to set
     */
    public void setVerion(int verion) {
        this.verion = verion;
    }
    
    
}
