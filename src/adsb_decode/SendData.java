/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adsb_decode;

import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tranduc
 */
public class SendData implements Runnable {
    
    private Queue<RecordsSent> queueSend;
    public SendData(Queue<RecordsSent> q) {
        queueSend = q;
    }
    
    
    @Override
    public void run() {
         // Task to be executed in the thread
        while(true) {
            
           // System.out.println("Thread is running from the Runnable interface........................... " + Integer.toString(queueSend.size()));
            RecordsSent rs = queueSend.poll();
          //  if(rs!=null) {
          //   System.out.println("Thread is running from the Runnable interface........................... after poll " + Integer.toString(queueSend.size()) + 
           //          " Record size " + Integer.toString(rs.data.length));
         //   }
            
            
            try {
                Thread.sleep(2);
            } catch (InterruptedException ex) {
                Logger.getLogger(SendData.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
