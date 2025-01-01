/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adsb_decode;

import com.attech.cat21.util.BitwiseUtils;
import com.attech.cat21.v210.Cat21Decoder;
import com.attech.cat21.v210.Cat21Message;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ADSB_UDPReceiver {
    
    final static Queue<RecordsSent> queueSend = new LinkedList<>();
    
    final static Queue<byte[]> queueRecceive = new LinkedList<>();
    
    final static List<RecordsSent> recordssentList = new ArrayList<>();
   
    final static List<Cat21Message> messages = new ArrayList<>();
    
    //---------------------------------------------------------
    //
    //
    //---------------------------------------------------------

    // Kiem tra xem record data da co trong list chua
    public static boolean TestDataSentInList(byte data[]) {
        boolean found = false;
        
        // Xoa nhung cai da cu di truoc
        for(int i = 0 ; i < recordssentList.size() ; i ++) {
            long start = recordssentList.get(i).startTime;
            long endTime = System.nanoTime()/1000;
            long elapsedTime = endTime - start;
            if( elapsedTime > 5000000 ) {
                recordssentList.remove(i);
                System.out.println("REMOVE==========================================================================================================");
            } 
        }
        
        // Kiem tra
        // Can loai bo SIC/SAC
        for(int i = 0 ; i < recordssentList.size() ; i ++) {
            RecordsSent rcs = recordssentList.get(i);
            byte[] pos = rcs.pos;
            byte[] poshisres = rcs.poshires;
            byte[] dt = rcs.data;
            found = Arrays.equals(data, recordssentList.get(i).data);
            if(found)
                break;
        }
        
        return found;
    }
    
    //---------------------------------------------------------
    //
    //
    //---------------------------------------------------------
    
    public static void testPos(byte[] bytes) {
        int index = 0;
     
        byte[] latBytes = new byte[]{bytes[index++], bytes[index++], bytes[index++]};
        int value = BitwiseUtils.convertFrom2sComplementNumber(latBytes);
        double lat = value * 2.145767 * 0.00001;

        latBytes = new byte[]{bytes[index++], bytes[index++], bytes[index++]};
        value = BitwiseUtils.convertFrom2sComplementNumber(latBytes);
        double lon = value * 2.145767 * 0.00001;
    }
        
    public static void testPoshi(byte[] bytes) {
        int index = 0;
    
        int value = ((bytes[index++] & 0xFF) << 24) | ((bytes[index++] & 0xFF) << 16) | ((bytes[index++] & 0xFF) << 8) | (bytes[index++] & 0xFF);
        double lat = value * 0.00000016764;
        
        value = ((bytes[index++] & 0xFF) << 24) | ((bytes[index++] & 0xFF) << 16) | ((bytes[index++] & 0xFF) << 8) | (bytes[index++] & 0xFF);
        double lon = value * 0.0000001676;
    
    }
    
    /*
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
            
            byte data[]= cat21Decoded.getBytes();       // Data record received
            byte[] pos = cat21Decoded.positionByte;
            byte[] poshires = cat21Decoded.hirespositionByte;
            testPos(pos);
            testPoshi(poshires);
            
            System.out.println("Kiem tra xem data co duoc phat di khong " + Integer.toString(data.length) + ", Kiem tra co trong Records da phat " + Integer.toString(recordssentList.size()));
            // Neu chua co trong list
            if(!TestDataSentInList(data)) {
                System.out.println("CHUA CO NEN SEND TO CLIENT");
                rcs.add_data(cat21Decoded);
                recordssentList.add(rcs);
                queueSend.offer(rcs);
            }   
            
        }

    }
    */
    //---------------------------------------------------------
    //
    //
    //---------------------------------------------------------
    
    public static void CheckSend(String[] args) {
        
    }
    
    //---------------------------------------------------------
    //
    //  MAIN run from here
    //
    //---------------------------------------------------------
    public static void main(String[] args) {
        int debug = 0;
        String ip = "10.10.1.10";
        //String ip = "192.168.22.178";
        DatagramSocket socket = null;
        int port = 20000;  // Cổng lắng nghe
        asterixCat21 cat21 = new asterixCat21();
        
        SendData senddata = new SendData(queueSend);
        Thread threadTx = new Thread(senddata);
        threadTx.start();
        
        receiveQueueProcess receiveProcess = new receiveQueueProcess(queueRecceive);
        Thread threadRx = new Thread(receiveProcess);
        threadRx.start();
        
        try {
            // Tạo một DatagramSocket và gắn với cổng 20000
            //socket = new DatagramSocket(port, InetAddress.getByName("192.168.22.178"));

                      
            socket = new DatagramSocket(port, InetAddress.getByName(ip));
            
            byte[] receiveData = new byte[4096];  // Buffer để nhận dữ liệu
            byte[] asterixData; 
            System.out.println("Server đang lắng nghe tại " + ip +":"+ port);
            
            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                // Nhận gói dữ liệu UDP
                socket.receive(receivePacket);

                byte[] receivedData = receivePacket.getData();
                
                int length = receivePacket.getLength();
                // XOA SAU KHI DUNG
                asterixData = new byte[length];
                System.arraycopy(receivedData, 0, asterixData, 0, length);
                queueRecceive.offer(asterixData);
                
                //cat21.Decode(receivedData,length);
                
// DUNG DE DEBUG                
                debug ++;
                if(debug == 10) {
                    debug = 0;
                }
                
                //System.out.println("=====> Receiver queue has elements : " + Integer.toString(queueRecceive.size()) );
                /*
                messages.clear();
                int aaa = Cat21Decoder.decode1(asterixData, messages);
                ProcessMessage();
                System.out.println("=====> Sau khi decode CAT 21, tong decode bytes =" + Integer.toString(aaa) + " so messages=" + Integer.toString(messages.size()) + " TARGET LIST" + Integer.toString(recordssentList.size()) );
                */
                

//                ByteArrayInputStream byteStream = new ByteArrayInputStream(b);
//                DataInputStream dataStream = new DataInputStream(byteStream);
//                int len = dataStream.readInt();
//                        
//                 System.out.println(Integer.toString(length) + " " + Integer.toString(asterixData[0]) + " " + Integer.toString(len));
                // Bạn có thể xử lý dữ liệu tại đây
            }
        } catch (IOException e) {
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();  // Đảm bảo đóng socket khi hoàn thành
            }
        }
    }
}

/* EOF */