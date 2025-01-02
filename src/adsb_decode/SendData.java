/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adsb_decode;

import java.io.IOException;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tranduc
 */
public class SendData implements Runnable {
    
    UDPSender udpSend = null;
    private Queue<RecordsSent> queueSend;
    
    public SendData(Queue<RecordsSent> q) {
        queueSend = q;
        udpSend = new UDPSender();
    }
    
    //--------------------------------------------------------------
    // Function to convert an integer to a two-byte array
    //--------------------------------------------------------------
    public  byte[] intToTwoOctetBytes(int value) {
        // Create a byte array to hold the 2 bytes (16 bits)
        byte[] result = new byte[2];

        // Extract the high byte (first 8 bits)
        result[0] = (byte) (value >> 8);

        // Extract the low byte (last 8 bits)
        result[1] = (byte) (value & 0xFF);

        return result;
    }
    //--------------------------------------------------------------
    //
    //--------------------------------------------------------------
    private byte[] buildCat21(byte[] data) {
        byte[] tmp = new byte[data.length+3];
        tmp[0] = 21;
        byte[] len = intToTwoOctetBytes(data.length+3);
        tmp[1] = len[0];
        tmp[2] = len[1];
        System.arraycopy(data, 0, tmp, 3, data.length);
        return tmp;
    }
    
    //--------------------------------------------------------------
    //
    //--------------------------------------------------------------
    @Override
    public void run() {
         // Task to be executed in the thread
        while(true) {
            
           // System.out.println("Thread is running from the Runnable interface........................... " + Integer.toString(queueSend.size()));
            RecordsSent rs = queueSend.poll();
            if(rs!=null) {
                try {
                    byte[] cat21 = buildCat21(rs.data);
                    udpSend.sendUDPPacket(cat21, "192.168.22.170",20552);
                    System.out.println("Thread SendData is running from the Runnable interface........................... after poll " + Integer.toString(queueSend.size()) +
                            " Record size " + Integer.toString(rs.data.length));
                } catch (IOException ex) {
                    Logger.getLogger(SendData.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                    Logger.getLogger(SendData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            try {
                Thread.sleep(2);
            } catch (InterruptedException ex) {
                Logger.getLogger(SendData.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
