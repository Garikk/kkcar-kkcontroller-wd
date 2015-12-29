/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.controller.wdconnection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author blinov_is
 */
public class WatchDogUDPConnection {
    private static final int WD_UDPPORT=39876;
    private static boolean Stop = false;

    public void Stop() {
        Stop = true;
    }

    public static void StartUDPServer() {

        Thread myThready = new Thread(new Runnable() {
            @Override
            public void run() {
                DatagramSocket serverSocket;
                try {
                    serverSocket = new DatagramSocket(WD_UDPPORT);
                    byte[] receiveData = new byte[4];
                    byte[] sendData;// = new byte[4];
                    while (!Stop) {
                        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                        serverSocket.receive(receivePacket);

                        sendData = new byte[4];
                        sendData[0] = 7;
                        sendData[1] = 17;
                        sendData[2] = WatchDogService.getInstance().getCurrentSystemState().GetCurrentStateB();
                        sendData[3] = WatchDogService.getInstance().getCurrentSystemState().GetCurrentStateB();

                        InetAddress IPAddress = receivePacket.getAddress();
                        int port = receivePacket.getPort();
                        String modifiedSentence = new String(receivePacket.getData());
                        if (modifiedSentence!=null)
                            System.out.println("INTERCON : " + modifiedSentence);
                        
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                        serverSocket.send(sendPacket);
                        Thread.sleep(1000);
                    }
                    serverSocket.close();
                } catch (IOException ex) {
                    Logger.getLogger(WatchDogUDPConnection.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(WatchDogUDPConnection.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        myThready.start();	//Запуск потока
    }
}
