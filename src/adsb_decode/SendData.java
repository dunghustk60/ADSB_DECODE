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



    public void checkTranslation(RecordsSent rcS) throws IOException, InterruptedException {
        try {
            BinaryMessage binaryMessage = new BinaryMessage(rcS.getMsg());
            //byte goc
            byte[] goc = rcS.data;
            //byte dich
//                byte[] dich = binaryMessage.getBinaryMessage();

            byte[] dich = new byte[rcS.data.length];
            int t = binaryMessage.getSoLuongHeader();
            for (int k = 0; k < t; k++) {
                dich[k] = binaryMessage.getBinaryMessage()[k];
            }
            for (int k = 0; k < binaryMessage.getHeader().length; k++) {
                if (binaryMessage.getHeader()[k]) {
                    System.arraycopy(binaryMessage.getBytes2D()[k], 0, dich, t, binaryMessage.getBytes2D()[k].length);
                    t += binaryMessage.getBytes2D()[k].length;
                }
            }
          
            if (goc.length == dich.length) {
                int dau = 0;
                for (int z = 0; z < goc.length; z++) {
                    if (goc[z] != dich[z]) {
                        dau = 1;
                        dichSai++;
                        System.out.println("Dich sai content----------------------------------------------------" + dichSai);
                        break;
                    }
                }
                if (dau == 0) {
                    dichDung++;
                    System.out.println("True ------------------" + dichDung);
                }
            } else {
                dichSai++;
                System.out.println("Dich sai length " + dichSai);
            }
        } catch (Exception e) {

        }

    }

    public void checkNull() throws InterruptedException {
        for (RecordsSent rcS : queueSend) {
            if (rcS.data == null) {
                System.out.println(" nul;fdgsdgdsfg");
            } else {
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Data length = " + Integer.toString(rcS.data.length));
            }
        }
//        RecordsSent rcS = queueSend.take();
        
    }

    public void send() throws InterruptedException, IOException {
        List<Byte> list = new ArrayList<>();
        int temp = 3;
        while (temp < MAX_BYTE_INPACKET - 200) {
            RecordsSent rcS = queueSend.take();
            checkTranslation(rcS);
            countMessage++;
            System.out.println(" So luong messages: "+countMessage);
            int lengTemp = rcS.data.length;
            for (int i = 0; i < lengTemp; i++) {
                list.add(rcS.data[i]);
            }
            temp += lengTemp;
        }
        byte[] msg = new byte[temp];
        msg[0] = 21;
        msg[1] = (byte) (msg.length >> 8);
        msg[2] = (byte) (msg.length & 0xFF);
        for (int i = 3; i < list.size(); i++) {
            msg[i] = list.get(i - 3);
        }
        udpSend.sendUDPPacket(msg, "192.168.22.158", 20552);
    }

    @Override

    public void run() {

        while (true) {

            
            int sz = queueSend.size();
//            System.out.println("queue size = "+sz);
            if (sz > 3) {
                try {
//                    checkNull();
                    send();
                

                } catch (InterruptedException ex) {
                    Logger.getLogger(SendData.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
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
