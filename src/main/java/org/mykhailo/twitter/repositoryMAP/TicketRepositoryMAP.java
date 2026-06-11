package org.mykhailo.twitter.repositoryMAP;

import lombok.Getter;
import org.mykhailo.twitter.entity.Entity;
import org.mykhailo.twitter.entity.Ticket;
import org.mykhailo.twitter.util.Storage;

import java.util.List;
import java.util.Map;


public class TicketRepositoryMAP {

    @Getter
    private static final TicketRepositoryMAP INSTANCE = new TicketRepositoryMAP();

    private final Map<Class<?>, List<Entity<Long>>> storage = Storage.getINSTANCE().getStorage();

    private TicketRepositoryMAP() {}

    public Entity<Long> getById(Long id) {
        List<Entity<Long>> objects = storage.get(Ticket.class);

        for (Entity<Long> longEntity : objects) {
            if (longEntity.getId().equals(id)) {
                return longEntity;
            }
        }
        return null;
    }

    public void save(Ticket ticket) {
        List<Entity<Long>> objects = storage.get(Ticket.class);
        if (objects != null) {
            objects.add(ticket);
        }
    }

    public boolean deleteById(Long id) {
        List<Entity<Long>> objects = storage.get(Ticket.class);
        if (objects == null) {
            return false;
        }

        for (int i = 0; i < objects.size(); i++) {
            if (objects.get(i).getId().equals(id)) {
                objects.remove(i);
                return true;
            }
        }
        return false;
    }
}
