package org.mykhailo.twitter.UDP;

import java.io.IOException;
import java.net.*;

public class UDPClient {
    static void main() throws SocketException, UnknownHostException {
        InetAddress localhost = InetAddress.getByName("localhost");
        try (DatagramSocket datagramSocket = new DatagramSocket()) {
            byte[] buffer = "hello world".getBytes();
            DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, localhost, 7777);
            datagramSocket.send(datagramPacket);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
