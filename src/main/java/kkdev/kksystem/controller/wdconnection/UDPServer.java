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
import java.net.SocketException;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import kkdev.kksystem.controller.watchdog.Main;

/**
 *
 * @author blinov_is
 */
public class UDPServer {

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
                    serverSocket = new DatagramSocket(39876);
                    byte[] receiveData = new byte[1024];
                    byte[] sendData;// = new byte[4];
                    while (!Stop) {
                        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                        serverSocket.receive(receivePacket);

                        sendData = new byte[4];
                        sendData[0] = 7;
                        sendData[1] = 17;
                        sendData[2] = Main.CurrentSystemState.GetCurrentStateB();
                        sendData[3] = Main.CurrentSystemState.GetCurrentStateB();

                        InetAddress IPAddress = receivePacket.getAddress();
                        int port = receivePacket.getPort();

                        DatagramPacket sendPacket
                                = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                        serverSocket.send(sendPacket);
                        Thread.sleep(1000);
                    }
                    serverSocket.close();
                } catch (IOException ex) {
                    Logger.getLogger(UDPServer.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(UDPServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        myThready.start();	//Запуск потока
    }
}
