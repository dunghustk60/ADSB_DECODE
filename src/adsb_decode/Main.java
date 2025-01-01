/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adsb_decode;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tranduc
 */
public class Main {
    public static void main(String[] args) {
          // Create an instance of MyRunnable
        receiveThread receiverthread = new receiveThread();

        // Create a thread and pass the runnable instance to it
        Thread thread = new Thread(receiverthread);

        // Start the thread
        thread.start();
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
