package com.walmart.tech.service;

import com.walmart.tech.domain.SeatHold;
import com.walmart.tech.exception.ServiceException;

import java.util.Optional;

/**
 * Created by bdavay on 12/15/15.
 */
public interface TicketService {

    /**
     * The number of seats in the requested level that are neither held nor reserved
     *
     * @param venueLevel a numeric venue level identifier to limit the search
     * @return the number of tickets available on the provided level
     */

    int numSeatsAvailable(Optional<Integer> venueLevel) throws ServiceException;

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

    SeatHold findAndHoldSeats(int numSeats, Optional<Integer> minLevel,

                              Optional<Integer> maxLevel, String customerEmail) throws ServiceException;

    /**
     * Commit seats held for a specific customer
     *
     * @param seatHoldId    the seat hold identifier
     * @param customerEmail the email address of the customer to which the seat hold
     *                      <p>
     *                      is assigned
     * @return a reservation confirmation code
     */

    String reserveSeats(int seatHoldId, String customerEmail) throws ServiceException;

}