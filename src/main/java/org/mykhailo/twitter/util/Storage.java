package org.mykhailo.twitter.util;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.mykhailo.twitter.entity.Entity;
import org.mykhailo.twitter.entity.Ticket;
import org.mykhailo.twitter.entity.Train;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Storage {

    private static final Map<Class<?>, List<Entity<Long>>> storage = new HashMap<>();

    @Getter
    private static final Storage INSTANCE = new Storage();

    public Map<Class<?>, List<Entity<Long>>> getStorage() {
        return storage;
    }

    static {
        init();
    }

    public static void init() {
        List<Entity<Long>> trains = new ArrayList<>();

        trains.add(new Train(1L, 52L));
        trains.add(new Train(2L, 52L));
        trains.add(new Train(3L, 52L));
        trains.add(new Train(4L, 52L));

        List<Entity<Long>> tickets = new ArrayList<>();

        tickets.add(new Ticket(1L, 52L, 1L));
        tickets.add(new Ticket(2L, 52L, 2L));
        tickets.add(new Ticket(3L, 52L, 3L));
        tickets.add(new Ticket(4L, 52L, 4L));

        storage.put(Train.class, trains);
        storage.put(Ticket.class, tickets);
    }
}
