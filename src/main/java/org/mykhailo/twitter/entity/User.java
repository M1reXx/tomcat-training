package org.mykhailo.twitter.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private Long ticketsId;

    public User(Long id, String firstName, String lastName, Long ticketsId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.ticketsId = ticketsId;
    }

}
