package org.mykhailo.twitter.UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer {
    static void main() throws IOException {
        try (DatagramSocket datagramSocket = new DatagramSocket(7777)) {
            byte[] buffer = new byte[512];
            DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
            datagramSocket.receive(datagramPacket);
            System.out.println(new String(buffer));
        }
    }
}
