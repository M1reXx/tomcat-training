package org.mykhailo.twitter.client;

import org.mykhailo.twitter.ui.TerminalUI;

import java.io.*;
import java.net.Socket;

//TODO: add user profile
public class TrainApp {

    private static final String SERVER_HOST = "100.104.241.116";
    private static final int SERVER_PORT = 7777;

    static void main() {
        new TrainApp().run();
    }

    public void run() {
        boolean back = false;

        while (!back) {
            String choice = TerminalUI.menuCreator(
                    "MAIN MENU",
                    "Buy ticket",
                    "View list of tickets",
                    "Exit");

            switch (choice) {
                case "1" -> buyTicket();
                case "2" -> viewTickets();
                case "3" -> back = true;
                default -> TerminalUI.infoUI("Invalid choice!");
            }
        }
    }

    private void buyTicket () {
        String ticketId = TerminalUI.choiceUI("Enter ticket ID", "ID");

        String response = sendRequest("BUY|" + ticketId);

        TerminalUI.infoUI(response);
    }

    private void viewTickets () {
        String response = sendRequest("VIEW");

        System.out.println("┌────────────────────────────────────────┐");
        System.out.printf("│ %-38s │\n", "           AVAILABLE TRAINS");
        System.out.println("├────────────────────────────────────────┤");

        if (response.equals("[]") || response.isEmpty()) {
            System.out.printf("│ %-38s │\n", "       No trains available.");
            System.out.println("└────────────────────────────────────────┘");
        }

        int start = 0;
        while (start < response.length()) {
            int end = Math.min(start + 38, response.length());
            String line = response.substring(start, end);
            System.out.printf("│ %-38s │\n", line);
            start += 38;
        }

        System.out.println("└────────────────────────────────────────┘");

    }

    private String sendRequest (String command){
        try (Socket socket = new Socket()) {
            socket.connect(new java.net.InetSocketAddress(SERVER_HOST, SERVER_PORT), 5000);

            try (DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                 DataInputStream inputStream = new DataInputStream(socket.getInputStream())) {

                outputStream.writeUTF(command);
                outputStream.flush();

                return inputStream.readUTF();
            }
        } catch (IOException e) {
            return "Error! Server connection failed!";
        }
    }
}
