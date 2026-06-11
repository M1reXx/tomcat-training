package org.mykhailo.twitter;

import org.mykhailo.twitter.entity.Ticket;

import org.mykhailo.twitter.entity.Train;
import org.mykhailo.twitter.repository.TicketRepository;
import org.mykhailo.twitter.repository.TrainRepository;

import java.sql.SQLException;
import java.util.ArrayList;

public class JDBCRunner {
    static void main() throws SQLException {
        TrainRepository trainRepo = TrainRepository.getTrainRepository();
        TicketRepository ticketRepo = TicketRepository.getTicketRepository();

        System.out.println("Trains: " + trainRepo.getAll());
        System.out.println("Tickets: " + ticketRepo.getAll());



        Train createTrain = new Train(1L, 99L);

        Ticket ticketForUpdate = new Ticket(1L, 777L, 1L);
        ticketRepo.update(ticketForUpdate);

        // ticketRepo.delete(1L);
        // trainRepo.delete(1L);

        System.out.println("Done.");
    }
}