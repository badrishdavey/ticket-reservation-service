package com.walmart.tech.dao.impl;

import com.walmart.tech.dao.TicketServiceDao;
import com.walmart.tech.domain.Seat;
import com.walmart.tech.domain.SeatHold;
import com.walmart.tech.service.ServiceConfigConstant;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Created by bdavay on 12/15/15.
 *
 * This class serves as the data layer to get and persist the data from
 * in-memory store .
 *
 * TODO :// This can be mofidied to use Spring MVC repository to store in some NOSql database like mongodb for low latency reads and writes
 */
public class TicketServiceDaoImpl implements TicketServiceDao {

    final Set<Seat> seats = Collections.synchronizedSet(new TreeSet<Seat>());
    final List<SeatHold> seatsAtHold = Collections.synchronizedList(new ArrayList<SeatHold>());


    public TicketServiceDaoImpl() {
        instantiateSeats();
    }

    /**
     * @param venueLevel
     * @return int
     * <p>
     * This checks if the venueLevel passed in is available and
     * returns the size of the list using Java 8 lambda style functions
     */
    @Override
    public int seatsAvailableByLevel(Optional<Integer> venueLevel) {
        if (venueLevel.isPresent()) {
            List<Seat> as = seats.stream().filter(seat -> seat.isAvailable() && seat.getSeatLevel() == venueLevel.get()).collect(Collectors.toList());
            return as.size();
        }
        return 0;
    }


    /**
     * @param minVenueLevel
     * @param maxVenueLevel
     * @return List<Seat>
     * This defaults the min and max level to lowest and highest levels if the min or max venue level
     * are not passed in.
     * <p>
     * This also filters the seats which are available and within the range of min and max value
     */
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

    /**
     *
     * @param seatHold
     * @return boolean
     *
     * This function basically removes the particular seatHold from the holdList once the hold has been reserved
     */
    @Override
    public boolean reserve(SeatHold seatHold) {
        synchronized (seatsAtHold) {
            return seatsAtHold.remove(seatHold);
        }
    }

    /**
     *
     * @param sh(SeatHold)
     * This adds new seat hold everytime a new hold request
     * is created
     */
    @Override
    public void addSeatHoldToList(SeatHold sh) {
        synchronized (seatsAtHold) {
            seatsAtHold.add(sh);
        }
    }

    /**
     *
     * @param id
     * @param email
     * @return SeatHold
     * This function searches for the hold list for a given hold id
     * and email and returns back the seatHold object
     */
    @Override
    public SeatHold getSeatHoldById(int id, String email) {

        for (SeatHold sh : seatsAtHold) {
            if (sh.getHoldId() == id && sh.getEmail().equalsIgnoreCase(email)) {
                return sh;
            }
        }
        return null;
    }

    /**
     * This method instantiates the TreeSet with all different levels of
     * seat types as one time operation
     *
     * This is called only once by the constructor and never invoked after that.
     */
    private void instantiateSeats() {
        createSeats(1);
        createSeats(2);
        createSeats(3);
        createSeats(4);
    }

    /**
     *
     * @param level
     *
     * This is a utility method which can handle creation of seats for each level
     *
     */
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
