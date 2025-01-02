/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adsb_decode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tranduc
 */
public class Main {
    
    
    public static void Read() {
        String filePath = "config.cfg"; // Replace with your file path
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Read each line one by one
            while ((line = br.readLine()) != null) {
                System.out.println(line); // Process the line (e.g., print to console)
            }
        } catch (IOException e) {
        }
    }
    
    public static void main(String[] args) {
        // Create an instance of MyRunnable
        receiveThread receiverthread = new receiveThread();
        // Create a thread and pass the runnable instance to it
        Thread thread = new Thread(receiverthread);
        // Start the thread
        thread.start();
        
        ADSB_Receiver adsb_receiver_thread =  new ADSB_Receiver("Thread 20000","10.10.1.6",20000);
        Thread threadrcv = new Thread(adsb_receiver_thread);
        threadrcv.start();

        /*
        ADSB_UDPReceiver adsb_receiver_thread1 =  new ADSB_UDPReceiver("Thread 30000","10.10.1.10",30000);
        Thread threadrcv1 = new Thread(adsb_receiver_thread1);
        threadrcv1.start();
        */
        
        while(true) {
          System.out.println("MAIN is running!");
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(receiveThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
