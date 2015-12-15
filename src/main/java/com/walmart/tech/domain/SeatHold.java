package com.walmart.tech.domain;

import com.walmart.tech.service.ServiceConfigConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bdavay on 12/15/15.
 */
public class SeatHold {
    List<Seat> seats = new ArrayList<Seat>();
    private String email;
    private long holdCreatedTime;
    private int holdId;

    /**
     * @param email
     * @param holdCreatedTime
     * @param seats           This constructor basically registers the seat hold information for a customer
     *                        It marks all the available flags for the list of seats as false until the hold time
     */
    public SeatHold(String email, long holdCreatedTime, List<Seat> seats) {
        this.email = email;
        this.holdCreatedTime = holdCreatedTime;
        this.seats = seats;

        for (Seat a : seats) {
            a.setAvailable(false);
        }
        this.holdId = ServiceConfigConstant.HOLD_ID_GENERATOR.getAndIncrement();
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getHoldCreatedTime() {
        return holdCreatedTime;
    }

    public void setHoldCreatedTime(long holdCreatedTime) {
        this.holdCreatedTime = holdCreatedTime;
    }

    public int getHoldId() {
        return holdId;
    }

    public void setHoldId(int holdId) {
        this.holdId = holdId;
    }

    public List<Seat> getSeats() {
        return seats;

    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public boolean isHoldValid(Long currentTime) {
        return (currentTime - holdCreatedTime) <= ServiceConfigConstant.HOLD_5_SECS;
    }

}