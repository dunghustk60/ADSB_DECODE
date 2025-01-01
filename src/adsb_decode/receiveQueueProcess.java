/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adsb_decode;

import static adsb_decode.ADSB_UDPReceiver.TestDataSentInList;
import static adsb_decode.ADSB_UDPReceiver.messages;
import static adsb_decode.ADSB_UDPReceiver.queueSend;
import static adsb_decode.ADSB_UDPReceiver.recordssentList;
import static adsb_decode.ADSB_UDPReceiver.testPos;
import static adsb_decode.ADSB_UDPReceiver.testPoshi;
import com.attech.cat21.v210.Cat21Decoder;
import com.attech.cat21.v210.Cat21Message;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tranduc
 */
public class receiveQueueProcess implements Runnable {
    Queue<byte[]> queueRecceive = new LinkedList<>();
    final static List<Cat21Message> messages = new ArrayList<>();
    
    public receiveQueueProcess(Queue<byte[]> q) {
        queueRecceive = q;
    }
    //-------------------------------------------------------
    //
    //
    //
    //------------------------------------------------------- 
    public static void ProcessMessage() {

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

            System.out.println("Kiem tra xem data co duoc phat di khong " + Integer.toString(data.length) + ", Kiem tra co trong Records da phat " + Integer.toString(recordssentList.size()));
            // Neu chua co trong list
            if (!TestDataSentInList(data)) {
                System.out.println("CHUA CO NEN SEND TO CLIENT");
                rcs.add_data(cat21Decoded);
                recordssentList.add(rcs);
                queueSend.offer(rcs);
            }

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
                    byte[] data = queueRecceive.poll();
                    System.out.println("%%%%%%> Byte data length = " + Integer.toString(data.length));
                     messages.clear();
                    int aaa = Cat21Decoder.decode1(data, messages);
                    ProcessMessage();
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
