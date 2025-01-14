/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.main;

import com.attech.cat21.v210.Cat21Decoder;
import com.attech.cat21.v210.Cat21Message;
import static com.attech.main.Main.checkTranslation;
import java.io.File;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

/**
 *
 * @author dung
 */
public class UDPParallelProcessing {

    // Shared BlockingQueues
    private static int count = 0;
    private static int MAX_BYTE_INPACKET = 1024;
    private static final BlockingQueue<byte[]> queueA = new LinkedBlockingQueue<>();
    private static final TreeSet<Cat21Message> queueB = new TreeSet<>(new Cat21Comparator());

    // Configuration parameters from XML
    private static String receiveIP;
    private static int receivePort;
    private static String multicastIP;
    private static int multicastPort;

    public UDPParallelProcessing() {

    }

    static class Cat21Comparator implements Comparator<Cat21Message> {
//        private static final long serialVersionUID = 5109777777L;

        @Override
        public int compare(Cat21Message t1, Cat21Message t2) {
            int result = 0;
//            if (t1.getTimeOfAplicabilityPosition() != null && t2.getTimeOfAplicabilityPosition() != null) {
//                result = Double.compare(t1.getTimeOfAplicabilityPosition(), t2.getTimeOfAplicabilityPosition());
//            }
//            if (t1.getTimeOfAplicabilityVelocity() != null && t2.getTimeOfAplicabilityVelocity() != null && result == 0) {
//                result = Double.compare(t1.getTimeOfAplicabilityVelocity(), t2.getTimeOfAplicabilityVelocity());
//            }
// t1.getTimeOfMessageReceptionOfPosition() != null && t2.getTimeOfMessageReceptionOfPosition() != null &&
            if ( result == 0) {
                result = Double.compare(t1.getTimeOfMessageReceptionOfPosition(), t2.getTimeOfMessageReceptionOfPosition());
            }
            
            
//            if (t1.getTimeOfMessageReceptionOfVelocity() != null && t2.getTimeOfMessageReceptionOfVelocity() != null && result == 0) {
//                result = Double.compare(t1.getTimeOfMessageReceptionOfVelocity(), t2.getTimeOfMessageReceptionOfVelocity());
//            }
            
            if(result == 0){
                result = Integer.compare(t1.getTargetAddress(), t2.getTargetAddress());
            }

//            if (t1.getHighResolutionPosition() != null && t2.getHighResolutionPosition() != null && result == 0) {
//                if (t1.getHighResolutionPosition().getLatitude() != 0 && t2.getHighResolutionPosition().getLatitude() != 0) {
//                    result = Double.compare(t1.getHighResolutionPosition().getLatitude(), t2.getHighResolutionPosition().getLatitude());
//                }
//            }
//            if (t1.getHighResolutionPosition() != null && t2.getHighResolutionPosition() != null && result == 0) {
//                if (t1.getHighResolutionPosition().getLongtitude() != 0 && t2.getHighResolutionPosition().getLongtitude() != 0) {
//                    result = Double.compare(t1.getHighResolutionPosition().getLongtitude(), t2.getHighResolutionPosition().getLongtitude());
//                }
//            }
//
//            if (t1.getPosition() != null && t2.getPosition() != null && result == 0) {
//                if (t1.getPosition().getLatitude() != 0 && t2.getPosition().getLatitude() != 0) {
//                    result = Double.compare(t1.getPosition().getLatitude(), t2.getPosition().getLatitude());
//                }
//            }
//            if (t1.getPosition() != null && t2.getPosition() != null && result == 0) {
//                if (t1.getPosition().getLongtitude() != 0 && t2.getPosition().getLongtitude() != 0) {
//                    result = Double.compare(t1.getPosition().getLongtitude(), t2.getPosition().getLongtitude());
//                }
//            }

            return result;

        }

    }

    // Load configuration from XML
    private static void loadConfig(String configFilePath) throws Exception {
        File file = new File(configFilePath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);

        // Parse XML values
        receiveIP = document.getElementsByTagName("ReceiveIP").item(0).getTextContent();
        receivePort = Integer.parseInt(document.getElementsByTagName("ReceivePort").item(0).getTextContent());
        multicastIP = document.getElementsByTagName("MulticastIP").item(0).getTextContent();
        multicastPort = Integer.parseInt(document.getElementsByTagName("MulticastPort").item(0).getTextContent());
    }

    // Receiver Thread
    static class Receiver implements Runnable {

        @Override
        public void run() {
            try (DatagramSocket socket = new DatagramSocket(receivePort, InetAddress.getByName(receiveIP))) {
                byte[] buffer = new byte[2048];
                DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);

                while (true) {
                    socket.receive(receivePacket); // Receive data

                    int length = receivePacket.getLength();
                    byte[] asterixData = new byte[length];
                    System.arraycopy(receivePacket.getData(), 0, asterixData, 0, length);
                    System.out.println("Received length: " + length);
                    queueA.put(asterixData); // Put into queueA
//                    Thread.sleep(10);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Processor Thread
    static class Processor implements Runnable {

        @Override
        public void run() {
            try {
                final List<Cat21Message> messsages = new ArrayList<>();
                while (true) {
                    int sizeOfQueueA = queueA.size();
                    if (sizeOfQueueA > 0) {
                        byte[] data = queueA.take(); // Take from queueA
                        System.out.println("Processing: ");
                        Cat21Decoder.decode1(data, messsages);
                        // Simulate processing and create a ProcessedData object
                        int sizeOfList = messsages.size();
                        
                        for (Cat21Message processedData : messsages) {
                            if (processedData.getTargetAddress() != null) {
                                boolean t = queueB.add(processedData); // Put into queueB
                                if(t == false){
                                    count++;
                                }
                            }

                        }
                        System.out.println("So tin nhan bi loai " + count);
                        messsages.clear();

                    } else {
                        Thread.sleep(100);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Sender Thread
    static class Sender implements Runnable {

        @Override
        public void run() {
            try (DatagramSocket socket = new DatagramSocket()) {
                InetAddress multicastAddress = InetAddress.getByName(multicastIP);
                InetAddress multicastAddress2 = InetAddress.getByName("192.168.22.174");
                while (true) {
                    int sizeOfQueueB = queueB.size();
                    System.out.println("Size of queue B: " + sizeOfQueueB);
                    /*
                    if (sizeOfQueueB > 20) {

                        List<Byte> list = new ArrayList<>();
                        int temp = 3;
                        while (temp < MAX_BYTE_INPACKET - 200) {
                            Cat21Message msg = queueB.pollFirst(); // Take from queueB
                            if (msg != null) {
                                checkTranslation(msg);

                                int lengTemp = msg.getBytes().length;
                                for (int i = 0; i < lengTemp; i++) {
                                    list.add(msg.getBytes()[i]);
                                }
                                temp += lengTemp;
                            }

                        }
                        byte[] msg = new byte[temp];
                        msg[0] = 21;
                        msg[1] = (byte) (msg.length >> 8);
                        msg[2] = (byte) (msg.length & 0xFF);
                        for (int i = 3; i < list.size(); i++) {
                            msg[i] = list.get(i - 3);
                        }

                        System.out.println("Sending: ");

//                    byte[] buffer = msg.toString().getBytes();
                        DatagramPacket packet = new DatagramPacket(msg, msg.length, multicastAddress, multicastPort);
//                        DatagramPacket packet2 = new DatagramPacket(msg, msg.length, multicastAddress2, multicastPort);
                        socket.send(packet); // Send via multicast
//                        socket.send(packet2);
                        msg = null;
                        list.clear();
                    } else {
                        Thread.sleep(100);
                    }
*/
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    // Main Method

    public static void main(String[] args) {
        try {
            // Load configuration
            loadConfig("config.xml");

            // Start threads
            Thread receiverThread = new Thread(new Receiver());
            Thread processorThread = new Thread(new Processor());
            Thread senderThread = new Thread(new Sender());

            receiverThread.start();
            processorThread.start();
            senderThread.start();

            // Wait for threads to complete (optional)
            receiverThread.join();
            processorThread.join();
            senderThread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
