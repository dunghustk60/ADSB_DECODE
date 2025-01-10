/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adsb_decode;

import java.net.*;
import java.io.*;

public class MulticastSender {
    public static void main(String[] args) {
        try {
            // Define the multicast group address and port
            try ( // Create the 
                    MulticastSocket socket = new MulticastSocket()) {
                                      socket.setTimeToLive(64);

                // Define the multicast group address and port
                InetAddress group = InetAddress.getByName("224.1.1.1"); // Multicast IP address
                int port = 23456;
                // Create the message to send
                String message = "Hello, Multicast!";
                byte[] buffer = message.getBytes();
                // Create the DatagramPacket
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, port);
                // Send the packet
                socket.send(packet);
                System.out.println("Message sent to multicast group!");
                // Close the socket
            } // Multicast IP address
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
