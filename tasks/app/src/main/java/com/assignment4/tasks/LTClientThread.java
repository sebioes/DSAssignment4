package com.assignment4.tasks;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class LTClientThread implements Runnable {

    private final DatagramSocket clientSocket;
    LamportTimestamp lc;

    byte[] receiveData = new byte[1024];

    public LTClientThread(DatagramSocket clientSocket, LamportTimestamp lc) {
        this.clientSocket = clientSocket;
        this.lc = lc;
    }

    @Override
    public void run() {
        String response = null;

        while(true) {

            try {

            // create empty datagram packet
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            // receive data
            clientSocket.receive(receivePacket);

            // process the received message
            response = new String (receivePacket.getData(), 0, receivePacket.getLength());
            String[] receivedMessage = response.split(":");
            String receivedMessageBody = receivedMessage[0];
            int receivedTimestamp = Integer.parseInt(receivedMessage[1]);

            // update the clock
            lc.updateClock(receivedTimestamp);

            System.out.println("Server:" + receivedMessageBody + ":" + lc.getCurrentTimestamp());

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        }
    }
}
