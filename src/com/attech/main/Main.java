package com.attech.main;

import adsb_decode.RecordsSent;
import com.attech.cat21.v210.BinaryMessage;
import com.attech.cat21.v210.Cat21Decoder;
import com.attech.cat21.v210.Cat21Message;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static int dichDung = 0;
    public static int dichSai = 0;
    public static void main(String[] args) {
        int receivePort = 20001; // Port to receive data
        int sendPort = 20552;    // Port to forward data
        String forwardAddress = "192.168.22.158"; // Destination address for forwarding

        DatagramSocket receiveSocket = null;
        DatagramSocket sendSocket = null;

        try {
            // Create sockets for receiving and sending
            receiveSocket = new DatagramSocket(receivePort);
            sendSocket = new DatagramSocket();

            System.out.println("UDP Relay is running...");
            System.out.println("Listening on port: " + receivePort);
            System.out.println("Forwarding to: " + forwardAddress + ":" + sendPort);

            // Buffer for receiving data
            byte[] receiveBuffer = new byte[2048];
            final List<Cat21Message> messages = new ArrayList<>();
            while (true) {
                // Receive packet
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                receiveSocket.receive(receivePacket);

                // Extract data from received packet
                byte[] data = receivePacket.getData();
                int length = receivePacket.getLength();
                InetAddress senderAddress = receivePacket.getAddress();
                int senderPort = receivePacket.getPort();
                
                byte[] asterixData = new byte[length];
                
                System.arraycopy(data, 0, asterixData, 0, length);
                
                
                
                int aaa = Cat21Decoder.decode1(asterixData, messages);
                int dem = 0;
                for (Cat21Message message : messages) {
                    if (message.getTargetAddress() != null) {
                        dem++;
                        checkTranslation(message);
                    }

                }
                messages.clear();
                
                System.out.println("Received packet from " + senderAddress + ":" + senderPort);
                System.out.println("Data length: " +asterixData.length);

                // Forward the packet to the new destination
                DatagramPacket forwardPacket = new DatagramPacket(asterixData, length, InetAddress.getByName(forwardAddress), sendPort);
                sendSocket.send(forwardPacket);

                System.out.println("Forwarded packet to " + forwardAddress + ":" + sendPort);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close sockets
            if (receiveSocket != null && !receiveSocket.isClosed()) {
                receiveSocket.close();
            }
            if (sendSocket != null && !sendSocket.isClosed()) {
                sendSocket.close();
            }
        }
    }
    
    public static void checkTranslation(Cat21Message rcS) throws IOException, InterruptedException {
        try {
            BinaryMessage binaryMessage = new BinaryMessage(rcS);
            //byte goc
            byte[] goc = rcS.getBytes();
            //byte dich
//                byte[] dich = binaryMessage.getBinaryMessage();

            byte[] dich = new byte[goc.length];
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
}
