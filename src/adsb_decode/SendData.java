/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adsb_decode;

import com.attech.cat21.v210.BinaryMessage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tranduc
 */
public class SendData implements Runnable {

    final int MAX_BYTE_INPACKET = 1000;
    int len = 0;
    int dichDung = 0;
    int dichSai = 0;
    int countMessage = 0;
    UDPSender udpSend = null;
    //private List<RecordsSent> queueSend;

    BlockingQueue<RecordsSent> queueSend = null;
    public SendData(BlockingQueue<RecordsSent> q) {

        queueSend = q;
        udpSend = new UDPSender();
    }

    //--------------------------------------------------------------
    // Function to convert an integer to a two-byte array
    //--------------------------------------------------------------
    public byte[] intToTwoOctetBytes(int value) {
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
        byte[] tmp = new byte[data.length + 3];
        tmp[0] = 21;
        byte[] len = intToTwoOctetBytes(data.length + 3);
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
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
        Files.write(path, byteArray, StandardOpenOption.APPEND);
    }

    public void checkNullData() {
        for (int j = 0; j < queueSend.size(); j++) {
            RecordsSent rcS = queueSend.peek();
            if(rcS.data == null){
                System.out.println(" nul;fdgsdgdsfg");
            }
        }
                
                
    }    
    
    public void gopGoiphatdivaxoa(int i) throws IOException, InterruptedException {
//        try {
            byte[] msg = new byte[len + 3];
            msg[0] = 21;
            msg[1] = (byte) (msg.length >> 8);
            msg[2] = (byte) (msg.length & 0xFF);
            int temp = 3;
            checkNullData();
            for (int j = 0; j < i; j++) {
                countMessage++;
//                RecordsSent rcS = queueSend.get(j);

//                BinaryMessage binaryMessage = new BinaryMessage(rcS.getMsg());

//                byte[] goc = rcS.data;
////                byte[] dich = binaryMessage.getBinaryMessage();
//                byte[] dich = new byte[rcS.data.length];
//                int t = binaryMessage.getSoLuongHeader();
//                for(int k = 0; k < t; k++){
//                    dich[k] = binaryMessage.getBinaryMessage()[k];
//                }
//                for(int k = 0; k < binaryMessage.getHeader().length; k++){
//                    if(binaryMessage.getHeader()[k]){
//                        System.arraycopy(binaryMessage.getBytes2D()[k], 0, dich, t, binaryMessage.getBytes2D()[k].length);
//                        t+=binaryMessage.getBytes2D()[k].length;
//                    }
//                }
//                if (goc.length == dich.length) {
//                    int dau = 0;
//                    for (int z = 0; z < goc.length; z++) {
//                        if (goc[i] != dich[i]) {
//                            dau = 1;
//                            dichSai++;
//                            System.out.println("Dich sai content----------------------------------------------------" + dichSai);
//                            break;
//                        }
//
//                    }
//                    if (dau == 0) {
//                        dichDung++;
//                        System.out.println("True ------------------" +dichDung);
//                    }
//                } else {
//                    dichSai++;
//                    System.out.println("Dich sai length " + dichSai);
//                }
//                
//                int lengTemp = rcS.data.length;
//                System.arraycopy(rcS.data, 0, msg, temp, lengTemp);
//                temp += lengTemp;
            }

//            udpSend.sendUDPPacket(msg, "192.168.22.174", 20552);
            udpSend.sendUDPPacket(msg, "192.168.22.174", 20552);

            for (int j = 0; j < i; j++) {
                queueSend.remove(0);
            }
//        } 
//        catch (Exception e) {
//            System.err.println("error : " + e.getMessage());
//        }

        len = 0;

    }
    
    public void layindexcuoicanphat1() throws InterruptedException {
        RecordsSent rcS = queueSend.take();
        if (rcS.data == null) {
            System.out.println(" nul;fdgsdgdsfg");
        } else {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Data length = " + Integer.toString(rcS.data.length));
        }
        
        /*
        int j = queueSend.size();
        for (int i = 0; i < j ; i++) {
            RecordsSent rcS = queueSend.get(i);
            if(rcS.data == null){
                System.out.println(" nul;fdgsdgdsfg");
            }
        }
        for (int i = 0; i < j ; i++) {
            queueSend.remove(0);
        }
        */
    }

    
    
    
    public void send() throws InterruptedException, IOException {
        List<Byte> list = new ArrayList<>();
        int temp = 3;
        
        while(temp < MAX_BYTE_INPACKET - 200){
            RecordsSent rcS = queueSend.take();
            countMessage++;
            int lengTemp = rcS.data.length;
            for(int i = 0; i < lengTemp; i++){
                list.add(rcS.data[i]);
            }
            
//            System.arraycopy(rcS.data, 0, msg, temp, lengTemp);
            temp += lengTemp;
        }
        byte[] msg = new byte[temp];
        msg[0] = 21;
        msg[1] = (byte) (msg.length >> 8);
        msg[2] = (byte) (msg.length & 0xFF);
        for(int i = 3; i < list.size(); i++){
            msg[i] = list.get(i-3);
        }
        udpSend.sendUDPPacket(msg, "192.168.22.174", 20552);
//        int i = 0;
//        for(RecordsSent rcS : queueSend){
//            len += rcS.data.length;
//            i++;
//            if(len >= MAX_BYTE_INPACKET - 100) {
//                break;
//            }
//        }
        
        // XEM LAIJ
        //len -= queueSend.get(i).data.length;
        //  i = la phan tu cuoi can gop

//        System.out.println("i = " + Integer.toString(i) + " Len " + Integer.toString(len));

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

        while (true) {

//            System.out.println("DOAN NAY DUNG SE CONVERT VA PHAT CHO CLIENT");
            int sz = queueSend.size();
            if (sz > 30) {
                try {
                    layindexcuoicanphat1();
//                    send();
//                try {
//                    gopGoiphatdivaxoa(count);
//
//                    // int version = rs.getVerion();
//                    // byte[] _data = rs.data;
//                } catch (IOException ex) {
//                    Logger.getLogger(SendData.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(SendData.class.getName()).log(Level.SEVERE, null, ex);
//                }
    } catch (InterruptedException ex) {
        Logger.getLogger(SendData.class.getName()).log(Level.SEVERE, null, ex);
    }
            }

            try {
                Thread.sleep(5);
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
