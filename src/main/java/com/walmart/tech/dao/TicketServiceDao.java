package com.walmart.tech.dao;

import com.walmart.tech.domain.Seat;
import com.walmart.tech.domain.SeatHold;

import java.util.List;
import java.util.Optional;

/**
 * Created by bdavay on 12/15/15.
 */
public interface TicketServiceDao {

    int seatsAvailableByLevel(Optional<Integer> venueLevel);

    List<Seat> seatsAvailableByMinAndMaxLevel(Optional<Integer> minVenueLevel, Optional<Integer> maxLevel);

    boolean reserve(SeatHold seatHold);

    void addSeatHoldToList(SeatHold sh);

    SeatHold getSeatHoldById(int id, String email);

}
