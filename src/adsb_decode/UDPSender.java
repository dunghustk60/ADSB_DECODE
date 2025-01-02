/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adsb_decode;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.io.IOException;

public class UDPSender {

    static DatagramSocket socket;
    // Function to send a UDP packet
    public UDPSender() {
        try {
            // Create a DatagramSocket to send the packet
             socket = new DatagramSocket();

            // Convert the message into a byte array
            //byte[] buffer = message.getBytes();

           

            
            // Close the socket after sending the packet
            //socket.close();
        } catch (IOException e) {
            System.err.println("Error sending UDP packet.");
        }
    }

     public  void sendUDPPacket(byte[] buffer, String host, int port) throws IOException {
         // Create the DatagramPacket to hold the data, address, and port
          // Get the destination address (e.g., localhost or any IP address)
            InetAddress destinationAddress = InetAddress.getByName(host);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, destinationAddress, port);

            // Send the packet
            socket.send(packet);
            System.out.println("Message sent to " + host + ":" + port);

     }
    
     /*
    
    public static void main(String[] args) {
        // Example usage of sendUDPPacket function
        String message = "Hello, UDP Server!";
        String host = "localhost"; // Target host (or IP address)
        int port = 9876; // Target port

        sendUDPPacket(message, host, port);
    }
*/      
}
