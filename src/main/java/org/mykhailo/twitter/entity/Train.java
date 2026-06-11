package org.mykhailo.twitter.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Train extends Entity<Long> {
    private Long seatCount;

    public Train(Long id, Long seatCount) {
        super(id);
        this.seatCount = seatCount;
    }

    @Override
    public String toString() {
        return "Train{" +
                ", Seat count=" + seatCount +
                '}';
    }
}
