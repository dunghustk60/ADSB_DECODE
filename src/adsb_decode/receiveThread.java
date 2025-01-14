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
public class receiveThread  implements Runnable {

    @Override
    public void run() {
        while(true) {
          System.out.println("Thread is running!");
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(receiveThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
