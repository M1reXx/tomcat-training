package org.mykhailo.twitter;

import org.mykhailo.twitter.entity.Entity;
import org.mykhailo.twitter.repositoryMAP.TicketRepositoryMAP;
import org.mykhailo.twitter.util.Storage;

import java.util.List;
import java.util.Map;

public class Main {
    static void main() {
        Storage instance = Storage.getINSTANCE();
        Map<Class<?>, List<Entity<Long>>> storage = instance.getStorage();

        TicketRepositoryMAP ticketRepositoryMAP = TicketRepositoryMAP.getINSTANCE();

        Entity<Long> byId = ticketRepositoryMAP.getById(1L);
        System.out.println("Ticket: " + byId);
    }
}