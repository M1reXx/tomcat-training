package org.mykhailo.twitter.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Ticket extends Entity<Long>{
    private Long price;
    private Long trainId;

    public Ticket(Long id, Long price, Long trainId) {
        super(id);
        this.price = price;
        this.trainId = trainId;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + super.getId() + "," +
                "\"price\":" + price + "," +
                "\"trainId\":" + trainId +
                "}";
    }
}
