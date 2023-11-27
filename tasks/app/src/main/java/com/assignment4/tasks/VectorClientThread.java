package com.assignment4.tasks;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class VectorClientThread implements Runnable {

    private final DatagramSocket clientSocket;
    VectorClock vcl;
    byte[] receiveData = new byte[1024];

    int id;
    public VectorClientThread(DatagramSocket clientSocket, VectorClock vcl, int id) {

        this.clientSocket = clientSocket;
        this.vcl = vcl;
        this.id = id;
    }

    @Override
    public void run() {
        String response = "example:[1,1.0.0]"; //update this with the real response string from server
        /*
         * Write your code to receive messgaes from the server and update the vector clock
         */
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

        try {
            clientSocket.receive(receivePacket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        response = new String (receivePacket.getData(), 0, receivePacket.getLength());

        /*
         * you could use "replaceAll("\\p{Punct}", " ").trim().split("\\s+");" for filteing the received message timestamps
         * update clock and increament local clock (tick) for receiving the message
         */

        String[] responseArr = response.replaceAll("\\p{Punct}", " ").trim().split("\\s+");
        int serverClock = Integer.parseInt(responseArr[responseArr.length-1]);

        VectorClock vclServer = new VectorClock(4);
        vclServer.setVectorClock(0, serverClock);

        vcl.updateClock(vclServer);
        vcl.tick(id);

        String[] responseMessageArray = response.split(":");
        System.out.println("Server:" +responseMessageArray[0] +" "+ vcl.showClock());
    }
}
