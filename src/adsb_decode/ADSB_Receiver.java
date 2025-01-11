/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adsb_decode;

import com.attech.cat21.v210.Cat21Message;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ADSB_Receiver implements Runnable {
  
    final Queue<byte[]> queueRecceive = new LinkedList<>();
   
    final List<Cat21Message> messages = new ArrayList<>();
    
    //List<RecordsSent> queueSend = new ArrayList<>();
    
    //List<RecordsSent> queueSend = null;
    BlockingQueue<RecordsSent> queueSend = null;
    List<RecordsSent> recordsSentList = null;
      
    int mode;
    
    String ip = "10.10.1.6";
    int port = 20000; 
       
    private MulticastSocket msocket;
    private DatagramSocket socket = null;
    
    Thread threadTx = null;
    Thread threadRx = null;
    
//---------------------------------------------------------
    //
    //
    //---------------------------------------------------------
    public ADSB_Receiver(String thname, String ipadd, int pport,int mode,  BlockingQueue<RecordsSent> qS, List<RecordsSent> rSL) {
        this.ip = ipadd;
        this.port = pport;
        this.mode = mode;
        
        this.queueSend = qS;
        recordsSentList = rSL;
        
        SendData senddata = new SendData(qS);
        threadTx = new Thread(senddata);
      
       
        receiveQueueProcess receiveProcess = new receiveQueueProcess(queueRecceive,qS,rSL);
        threadRx = new Thread(receiveProcess);
        
        
        
    }
    
    String mcastadd = "239.1.1.123";
    int mport = 20000;
    String bindIpAddress = "10.10.1.6";

    public void SetMulticastParam(String maddr,int mp,String bindip) {
        this.mcastadd = maddr;
        this.mport = mp;
        this.bindIpAddress = bindip;
        
    }
    
    
    
    //---------------------------------------------------------
    //
    //
    //---------------------------------------------------------
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
    //  MAIN run from here
    //
    //---------------------------------------------------------
  /*
    public  void main1(String[] args) {
        int debug = 0;
        String ip = "10.10.1.10";
        int port = 20000;  // Cổng lắng nghe
        
        //String ip = "192.168.22.178";
        DatagramSocket socket = null;
        
        asterixCat21 cat21 = new asterixCat21();
        
        SendData senddata = new SendData(queueSend);
        Thread threadTx = new Thread(senddata);
        threadTx.start();
        
        receiveQueueProcess receiveProcess = new receiveQueueProcess(queueRecceive,queueSend);
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
                ///*
                //messages.clear();
                //int aaa = Cat21Decoder.decode1(asterixData, messages);
                //ProcessMessage();
                //System.out.println("=====> Sau khi decode CAT 21, tong decode bytes =" + Integer.toString(aaa) + " so messages=" + Integer.toString(messages.size()) + " TARGET LIST" + Integer.toString(recordssentList.size()) );
                //* /
                

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
*/
    /*
    private void receivingUDPMulticast() throws UnknownHostException, IOException {
     
        String mcastadd = this.sensor.getReceivingMulticastAddress();
        int port = this.sensor.getReceivingPort();
        
         // Multicast group and port
            String bindIpAddress = this.sensor.getReceivingBindIp();
          
            // IP address of the network interface (e.g., em3)
            //String bindIpAddress = "10.24.24.201"; // Replace with the IP of em3
            
            logger.info("DIA CHI MCAST = " + mcastadd + " PORT + " + Integer.toString(port) + " NIC: " +  bindIpAddress);

            // Create a MulticastSocket and bind it to the specific interface (IP address)
            InetAddress localAddress = InetAddress.getByName(bindIpAddress);
            msocket = new MulticastSocket(port);
            msocket.setInterface(localAddress); // Bind to the NIC by IP

            // Join the multicast group
            InetAddress group = InetAddress.getByName(mcastadd);
            msocket.joinGroup(group);
        
             final byte[] buffer = new byte[this.bufferSize];
            final DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        
        byte[] data;
        int length = 0;
     
            final List<Cat21Message> messages = new ArrayList<>();
        
                messages.clear();
                this.msocket.receive(packet);
                
                if ((packet.getData()[0] & 0xFF) != 21 && packet == null) {
                    continue;
                }
        
                length = packet.getLength();
                data = new byte[length];
                System.arraycopy(packet.getData(), 0, data, 0, length);
        

        
            }
        
        this.msocket.close();
    }
    */
    //--------------------------------------------------------------------
    //
    //
    //--------------------------------------------------------------------
    public void receiveUnicast() {
        int debug = 0;
      
        
        //String ip = "192.168.22.178";
        
        
        //asterixCat21 cat21 = new asterixCat21();
        
        SendData senddata = new SendData(queueSend);
        Thread threadTx = new Thread(senddata);
        threadTx.start();
        
        receiveQueueProcess receiveProcess = new receiveQueueProcess(queueRecceive,queueSend,recordsSentList);
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
//                int length = (receivePacket.getData()[1] & 0xFF) << 8 | (receivePacket.getData()[2] & 0xFF);
                int length = receivePacket.getLength();
                // XOA SAU KHI DUNG
                asterixData = new byte[length];
                System.arraycopy(receivedData, 0, asterixData, 0, length);
                queueRecceive.offer(asterixData);
//                if (length > 0) {
//                    asterixData = new byte[length];
//                    System.arraycopy(receivedData, 0, asterixData, 0, length);
//                    queueRecceive.offer(asterixData);
//                }
                
                
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
    //--------------------------------------------------------------------
    //
    //
    //--------------------------------------------------------------------
    public void receiveMultiCast() {
        int debug = 0;
             
        System.out.println("DIA CHI MCAST = " + mcastadd + " PORT + " + Integer.toString(mport) + " NIC: " +  bindIpAddress);
        
        try {
            // Create a MulticastSocket and bind it to the specific interface (IP address)
            InetAddress localAddress = InetAddress.getByName(bindIpAddress);
            msocket = new MulticastSocket(mport);
            msocket.setInterface(localAddress);                 // Bind to the NIC by IP
            InetAddress group = InetAddress.getByName(mcastadd);
            // Join the multicast group
            msocket.joinGroup(group);

        } catch (IOException ex) {
            Logger.getLogger(ADSB_Receiver.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
                       
            byte[] receiveData = new byte[4096];  // Buffer để nhận dữ liệu
            byte[] asterixData; 
            System.out.println("Server đang lắng nghe tại Multicast " + mcastadd +":" + mport);
            
            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                // Nhận gói dữ liệu MULTICAST
                msocket.receive(receivePacket);

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
            if (msocket != null && !socket.isClosed()) {
                msocket.close();  // Đảm bảo đóng socket khi hoàn thành
            }
        }
    }
    
    //--------------------------------------------------------------------
    //
    //
    //--------------------------------------------------------------------
    
    @Override
    public void run() {
        threadTx.start();
        threadRx.start();
        
        if(this.mode == 1) {
            receiveUnicast();
        } else {
            receiveMultiCast();
        }
    }
}


/* EOF */