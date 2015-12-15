package com.walmart.tech.service.impl;

import com.walmart.tech.dao.TicketServiceDao;
import com.walmart.tech.dao.impl.TicketServiceDaoImpl;
import com.walmart.tech.domain.Seat;
import com.walmart.tech.domain.SeatHold;
import com.walmart.tech.service.TicketService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by bdavay on 12/15/15.
 */
public class TicketServiceImpl implements TicketService {

    Logger log = Logger.getLogger(TicketServiceImpl.class.getName());


    TicketServiceDao dao = new TicketServiceDaoImpl();

    /**
     * The number of seats in the requested level that are neither held nor reserved
     *
     * @param venueLevel a numeric venue level identifier to limit the search
     * @return the number of tickets available on the provided level
     */
    @Override
    public int numSeatsAvailable(Optional<Integer> venueLevel) {
        try {
            if (venueLevel.isPresent()) {
                return dao.seatsAvailableByLevel(venueLevel);
            } else {
                log.log(Level.SEVERE, "There is no venueLevel provided");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Find and hold the best available seats for a customer
     *
     * @param numSeats      the number of seats to find and hold
     * @param minLevel      the minimum venue level
     * @param maxLevel      the maximum venue level
     * @param customerEmail unique identifier for the customer
     * @return a SeatHold object identifying the specific seats and related
     * <p>
     * information
     */
    @Override
    public SeatHold findAndHoldSeats(int numSeats, Optional<Integer> minLevel, Optional<Integer> maxLevel, String customerEmail) {

        SeatHold seatHoldInfo = null;

        List<Seat> seatsAvailable = dao.seatsAvailableByMinAndMaxLevel(minLevel, maxLevel);
        try {
            if (seatsAvailable.size() >= numSeats) {
                seatHoldInfo = new SeatHold(customerEmail, System.currentTimeMillis(), seatsAvailable.subList(0, numSeats));
                dao.addSeatHoldToList(seatHoldInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return seatHoldInfo;
    }

    /**
     * Commit seats held for a specific customer
     *
     * @param seatHoldId    the seat hold identifier
     * @param customerEmail the email address of the customer to which the seat hold
     *                      <p>
     *                      is assigned
     * @return a reservation confirmation code
     */
    @Override
    public String reserveSeats(int seatHoldId, String customerEmail) {

        if (seatHoldId <= 0 || customerEmail == null) {
            return null;
        }
        try {
            SeatHold hold = dao.getSeatHoldById(seatHoldId, customerEmail);

            if (hold != null && hold.isHoldValid(System.currentTimeMillis())) {
                if (dao.reserve(hold)) {
                    return UUID.randomUUID().toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }
}
