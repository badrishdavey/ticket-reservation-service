package com.walmart.tech.dao.impl;

import com.walmart.tech.dao.TicketServiceDao;
import com.walmart.tech.domain.Seat;
import com.walmart.tech.domain.SeatHold;
import com.walmart.tech.service.ServiceConfigConstant;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Created by bdavay on 12/15/15.
 */
public class TicketServiceDaoImpl implements TicketServiceDao {

    final Set<Seat> seats = Collections.synchronizedSet(new TreeSet<Seat>());
    final List<SeatHold> seatsAtHold = Collections.synchronizedList(new ArrayList<SeatHold>());


    public TicketServiceDaoImpl() {
        instantiateSeats();
    }

    @Override
    public int seatsAvailableByLevel(Optional<Integer> venueLevel) {
        if (venueLevel.isPresent()) {
            List<Seat> as = seats.stream().filter(seat -> seat.isAvailable() && seat.getSeatLevel() == venueLevel.get()).collect(Collectors.toList());
            return as.size();
        }
        return 0;
    }


    @Override
    public List<Seat> seatsAvailableByMinAndMaxLevel(Optional<Integer> minVenueLevel, Optional<Integer> maxVenueLevel) {
        List<Seat> as = null;
        final int minLevel = !minVenueLevel.isPresent() ? 1 : minVenueLevel.get();
        final int maxLevel = !maxVenueLevel.isPresent() ? 4 : maxVenueLevel.get();
        synchronized (seats) {
            as = seats.stream().filter(seat -> seat.isAvailable() && seat.getSeatLevel() >= minLevel &&
                    seat.getSeatLevel() <= maxLevel).collect(Collectors.toList());

        }
        return as;
    }


    @Override
    public boolean reserve(SeatHold seatHold) {
        synchronized (seatsAtHold) {
            return seatsAtHold.remove(seatHold);
        }
    }


    @Override
    public void addSeatHoldToList(SeatHold sh) {
        seatsAtHold.add(sh);
    }

    @Override
    public SeatHold getSeatHoldById(int id, String email) {

        for (SeatHold sh : seatsAtHold) {
            if (sh.getHoldId() == id && sh.getEmail().equalsIgnoreCase(email)) {
                return sh;
            }
        }
        return null;
    }


    private void instantiateSeats() {
        createSeats(1);
        createSeats(2);
        createSeats(3);
        createSeats(4);
    }

    private void createSeats(int level) {
        int maxSeats = 0;
        int currentSeats = seats.size();
        if (level == 1) {
            maxSeats = ServiceConfigConstant.LEVEL1_ROWS * ServiceConfigConstant.LEVEL1_SEATS_PER_ROW;
        } else if (level == 2) {
            maxSeats = ServiceConfigConstant.LEVEL2_ROWS * ServiceConfigConstant.LEVEL2_SEATS_PER_ROW;
        } else if (level == 3) {
            maxSeats = ServiceConfigConstant.LEVEL3_ROWS * ServiceConfigConstant.LEVEL3_SEATS_PER_ROW;
        } else if (level == 4) {
            maxSeats = ServiceConfigConstant.LEVEL4_ROWS * ServiceConfigConstant.LEVEL4_SEATS_PER_ROW;
        }
        for (int i = 1; i <= maxSeats; i++) {
            seats.add(new Seat(currentSeats + i, level, true));
        }
    }
}
