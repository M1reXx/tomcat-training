package org.mykhailo.twitter.TCP;

import org.mykhailo.twitter.repository.TicketRepository;
import org.mykhailo.twitter.repository.TrainRepository;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class Server {
    static void main() {
        //TODO: extract to consts
        TrainRepository trainRepo = TrainRepository.getTrainRepository();
        TicketRepository ticketRepo = TicketRepository.getTicketRepository();

        //TODO: extract to consts
        try (ServerSocket serverSocket = new ServerSocket(7777)) {
            System.out.println("=== Server has been started ===");

            while (true) {
                try (Socket socket = serverSocket.accept();
                     //TODO: extract to consts
                     DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                     DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())) {

                    String request = inputStream.readUTF();
                    System.out.println("Received: " + request);

                    String[] parts = request.split("\\|");
                    String command = parts[0];

                    if (command.equals("BUY")) {
                        try {
                            Long ticketId = Long.parseLong(parts[1]);
                            //TODO: invalid logic with deleting, need to add column to ticket table (true/false)
                            boolean isDeleted = ticketRepo.delete(ticketId);

                            if (isDeleted) {
                                outputStream.writeUTF("Ticket №" + ticketId + " bought successfully!");
                            } else {
                                outputStream.writeUTF("Ticket №" + ticketId + " has invalid id!");
                            }
                        } catch (Exception e) {
                            outputStream.writeUTF("Error! Invalid ticket ID");
                        }
                    } else if (command.equals("VIEW")) {
                        //TODO: add logs
                        try {
                            outputStream.writeUTF(trainRepo.getAll().toString());
                        } catch (SQLException e) {
                            outputStream.writeUTF("Error! SQL exception");
                        }
                    } else {
                        outputStream.writeUTF("Invalid request!");
                    }

                    outputStream.flush();

                } catch (IOException e) {
                    System.out.println("Connection error: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not start server on port 7777");
        }
    }
}