package org.mykhailo.twitter.entity;

import lombok.Setter;

@Setter
public class Entity<ID> {
    private ID id;

    public Entity(ID id) {
        this.id = id;
    }

    public ID getId() {
        this.id = id;

        return id;
    }

}
