/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adsb_decode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Queue;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tranduc
 */
public class SendData implements Runnable {
    
    final int MAX_BYTE_INPACKET = 1000;  
    public static int len = 0;
    
    UDPSender udpSend = null;
    private List<RecordsSent> queueSend;
    
    public SendData(List<RecordsSent> q) {
      
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
    private byte[] buildCat21single(byte[] data) {
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
    //
    //  DUNG BAT DAU TU DAY
    //
    //--------------------------------------------------------------
      public static void writeByteArrayToRCDFile(byte[] byteArray, String filePath) throws IOException {
        // Create a file output stream
        Path path = Paths.get(filePath);
        if(!Files.exists(path)){
            Files.createFile(path);
        }
        Files.write(path,byteArray, StandardOpenOption.APPEND);
    }
      
     public void gopGoiphatdivaxoa(int i) throws IOException, InterruptedException {
        try {
            byte[] msg = new byte[len + 3];
            msg[0] = 21;
            msg[1] = (byte) (msg.length >> 8);
            msg[2] = (byte) (msg.length & 0xFF);
            int temp = 3;
            for (int j = 0; j < i; j++) {
                RecordsSent rcS = queueSend.get(j);
                int lengTemp = rcS.data.length;
                System.arraycopy(rcS.data, 0, msg, temp, lengTemp);
                temp += lengTemp;
            }

//            udpSend.sendUDPPacket(msg, "192.168.22.174", 20552);
        udpSend.sendUDPPacket(msg, "192.168.22.158",20552);

            for (int j = 0; j < i; j++) {
                queueSend.remove(j);
            }
        } catch (Exception e) {
            System.err.println("error : " + e.getMessage());
        }

        len = 0;

     } 
   
      
    public int layindexcuoicanphat() {
        
        int i;
//        for(RecordsSent rcS : queueSend){
//            len += rcS.data.length;
//            i++;
//            if(len >= MAX_BYTE_INPACKET - 100) {
//                break;
//            }
//        }
        for ( i = 0 ; i < queueSend.size() ; i ++ ) {
            len += queueSend.get(i).data.length;
            if(len >= MAX_BYTE_INPACKET - 100) {
                break;
            }
          //  System.arraycopy(i, i, i, i, i);
        }
        len -= queueSend.get(i).data.length;
       //  i = la phan tu cuoi can gop
        
        System.out.println("i = " + Integer.toString(i) + " Len " + Integer.toString(len));
        
        return i;
        //lay 1 phan tu ra
        //        xem do dai da qua MAX
        //                chua get tiep xem qua MAX Chaw
                                
        /*
        System.out.println("Phat hien co " + Integer.toString(queueSend.size()) + " Records can phat di");
        RecordsSent rs = get(0);

        try {
            byte[] cat21 = buildCat21single(rs.data);
            udpSend.sendUDPPacket(cat21, "192.168.22.170", 20552);
            System.out.println("Thread SendData is running from the Runnable interface........................... after poll " + Integer.toString(queueSend.size())
                    + " Record size " + Integer.toString(rs.data.length));
        } catch (IOException ex) {
            Logger.getLogger(SendData.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
    }
      
      
      
    @Override
    
    public void run() {
        
        while(true) {
            
//            System.out.println("DOAN NAY DUNG SE CONVERT VA PHAT CHO CLIENT");
            if(queueSend.size() > 100 ) {
                int count = layindexcuoicanphat();
                try {
                    gopGoiphatdivaxoa(count);
                    
                    
                    // int version = rs.getVerion();
                    // byte[] _data = rs.data;
                } catch (IOException ex) {
                    Logger.getLogger(SendData.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(SendData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(SendData.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    
    
    /*
    public void run1() {
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
              
            }
            
            try {
                Thread.sleep(2);
            } catch (InterruptedException ex) {
                Logger.getLogger(SendData.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    */
}
