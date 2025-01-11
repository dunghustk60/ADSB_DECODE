/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adsb_decode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
public class Main {
    
//    static final List<RecordsSent> queueSend = new ArrayList<>();
    static final List<RecordsSent> recordsSentList = new ArrayList<>();
    static final BlockingQueue<RecordsSent> queueSend = new LinkedBlockingDeque<>();
    
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
        
//        Read();
//                
//        receiveThread receiverthread = new receiveThread();
//        Thread thread = new Thread(receiverthread);
//        thread.start();
        
        ADSB_Receiver adsb_receiver_thread =  new ADSB_Receiver("Thread 20000","192.168.22.158",20000,1,queueSend,recordsSentList);
        adsb_receiver_thread.SetMulticastParam("224.1.1.1", 23456, "localhost");
        
        Thread threadrcv = new Thread(adsb_receiver_thread);
        threadrcv.start();

        /*
        ADSB_UDPReceiver adsb_receiver_thread1 =  new ADSB_UDPReceiver("Thread 30000","10.10.1.10",30000);
        Thread threadrcv1 = new Thread(adsb_receiver_thread1);
        threadrcv1.start();
        */
        
        /*
        while(true) {
          System.out.println("MAIN is running!");
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(receiveThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        */
    }
}
