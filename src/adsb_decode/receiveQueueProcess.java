/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adsb_decode;


import com.attech.cat21.util.BitwiseUtils;
import com.attech.cat21.v210.Cat21Decoder;
import com.attech.cat21.v210.Cat21Message;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tranduc
 */
public class receiveQueueProcess implements Runnable {
    BlockingQueue<byte[]> queueRecceive;// = new LinkedBlockingDeque<>();
    
    final List<Cat21Message> messages;// = new ArrayList<>();
//    static List<RecordsSent> queueSend = new ArrayList<>();
    BlockingQueue<RecordsSent> queueSend;// = null;
    List<RecordsSent> recordsSentList;// = new ArrayList<>();
              
    
    public receiveQueueProcess(BlockingQueue<byte[]> q, BlockingQueue<RecordsSent> qs, List<RecordsSent> recordsSentList) {
        queueRecceive = q;
        queueSend = qs;
        messages = new ArrayList<>();
        this.recordsSentList = recordsSentList;
    }
    
    // Kiem tra xem record data da co trong list chua
    public  boolean TestDataSentInList(byte data[]) {
        boolean found = false;
        
        // Xoa nhung cai da cu di truoc
        for(int i = 0 ; i < recordsSentList.size() ; i ++) {
            long start = recordsSentList.get(i).startTime;
            long endTime = System.nanoTime()/1000;
            long elapsedTime = endTime - start;
            if( elapsedTime > 15000000 ) {
                recordsSentList.remove(i);
                System.out.println("REMOVE==========================================================================================================");
            } 
        }
        
        // Kiem tra
        // Can loai bo SIC/SAC
        for(int i = 0 ; i < recordsSentList.size() ; i ++) {
            RecordsSent rcs = recordsSentList.get(i);
//            byte[] pos = rcs.pos;
//            byte[] poshisres = rcs.poshires;
//            byte[] dt = rcs.data;
            found = Arrays.equals(data, recordsSentList.get(i).data);
            if(found)
                break;
        }
        
        return found;
    }
     //---------------------------------------------------------
    //
    //
    //---------------------------------------------------------
    
    public  void testPos(byte[] bytes) {
        int index = 0;
     
        byte[] latBytes = new byte[]{bytes[index++], bytes[index++], bytes[index++]};
        int value = BitwiseUtils.convertFrom2sComplementNumber(latBytes);
        double lat = value * 2.145767 * 0.00001;

        latBytes = new byte[]{bytes[index++], bytes[index++], bytes[index++]};
        value = BitwiseUtils.convertFrom2sComplementNumber(latBytes);
        double lon = value * 2.145767 * 0.00001;
    }
        
    public  void testPoshi(byte[] bytes) {
        int index = 0;
    
        int value = ((bytes[index++] & 0xFF) << 24) | ((bytes[index++] & 0xFF) << 16) | ((bytes[index++] & 0xFF) << 8) | (bytes[index++] & 0xFF);
        double lat = value * 0.00000016764;
        
        value = ((bytes[index++] & 0xFF) << 24) | ((bytes[index++] & 0xFF) << 16) | ((bytes[index++] & 0xFF) << 8) | (bytes[index++] & 0xFF);
        double lon = value * 0.00000016764;
    
    }
    //-------------------------------------------------------
    //
    //
    //
    //------------------------------------------------------- 
    public  void ProcessMessage() throws InterruptedException {

        //recordssentList.clear();
        System.out.println("++++++> Xy ly dien trong queue so messages=" + Integer.toString(messages.size()));

        // messages chua cac record da duoc decode
        for (int i = 0; i < messages.size(); i++) {
            Cat21Message cat21Decoded = messages.get(i);

            //System.out.println("CALL SIGN = " + cat21.getCallSign() + " ADDRESS " + Integer.toString(cat21.getTargetAddress())) ;
            RecordsSent rcs = new RecordsSent();
            if (cat21Decoded.getCallSign() != null) {
                rcs.Callsign = cat21Decoded.getCallSign();
            }

            byte data[] = cat21Decoded.getBytes();       // Data record received
            byte[] pos = cat21Decoded.positionByte;
            byte[] poshires = cat21Decoded.hirespositionByte;
            testPos(pos);
            testPoshi(poshires);

            System.out.println("Kiem tra xem data co duoc phat di khong " + Integer.toString(data.length) + ", Kiem tra co trong Records da phat " + Integer.toString(recordsSentList.size()));
            // Neu chua co trong list
             
            if (!TestDataSentInList(data)) {
                System.out.println("CHUA CO NEN SEND TO CLIENT");
                rcs.add_data(cat21Decoded);
                rcs.setVerion(3);
                recordsSentList.add(rcs);
                queueSend.put(rcs);
                System.out.println("put to queue send");
            } else {
                String tmp = rcs.Callsign  + " -> DA CO NEN KHONG SEND TO CLIENT ";
                WireToFile(tmp + "\r\n");
                
                System.out.println(tmp);
            }

        }

    }
    
    private void WireToFile(String s) {
        String file ="adsb.log";
        try ( BufferedWriter write = new BufferedWriter(new FileWriter(file,true))) {
            write.write(s);
        
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    //-------------------------------------------------------
    //
    //
    //
    //------------------------------------------------------- 
    @Override
    public void run() {
        while(true) {
            if (!queueRecceive.isEmpty()) {
                System.out.println("=====> Receiver queue has elements : " + Integer.toString(queueRecceive.size()));
                while(!queueRecceive.isEmpty()) {
                    //messages.clear();
                    byte[] data = null;
                    try {
                        data = queueRecceive.take();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(receiveQueueProcess.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println("%%%%%%> Byte data length = " + Integer.toString(data.length));
                    messages.clear();
                    int aaa = Cat21Decoder.decode1(data, messages);
                    try {
                        ProcessMessage();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(receiveQueueProcess.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                //int aaa = Cat21Decoder.decode1(asterixData, messages);
                //ProcessMessage();
                //System.out.println("=====> Sau khi decode CAT 21, tong decode bytes =" + Integer.toString(aaa) + " so messages=" + Integer.toString(messages.size()) + " TARGET LIST" + Integer.toString(recordssentList.size()) );
                try {
                 System.out.println("=====> Receiver queue has NO elements ");
                 Thread.sleep(200);
                } catch (InterruptedException ex) {
                    Logger.getLogger(receiveQueueProcess.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
    }
    
}
