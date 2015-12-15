package com.walmart.tech.service;

import com.walmart.tech.domain.SeatHold;
import com.walmart.tech.service.impl.TicketServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

/**
 * Created by bdavay on 12/15/15.
 */

public class TicketServiceImplTest {

    TicketServiceImpl ticketService = null;

    @Before
    public void setUp() {
        ticketService = new TicketServiceImpl();
    }

    @Test
    public void testNumSeatsAvailable() throws Exception {
        Assert.assertEquals(1250, ticketService.numSeatsAvailable(Optional.of(1)));
        Assert.assertEquals(2000, ticketService.numSeatsAvailable(Optional.of(2)));
        Assert.assertEquals(1500, ticketService.numSeatsAvailable(Optional.of(3)));
        Assert.assertEquals(1500, ticketService.numSeatsAvailable(Optional.of(4)));

    }

    @Test
    public void testFindAndHoldSeats() throws Exception {
        SeatHold seathHold = ticketService.findAndHoldSeats(50, Optional.of(1), Optional.of(2), "bdavay@walmart.com");
        Assert.assertEquals(50, seathHold.getSeats().size());

    }

    @Test
    public void testReserveSeats() throws Exception {
        SeatHold seathHold = ticketService.findAndHoldSeats(50, Optional.of(1), Optional.of(2), "bdavay@walmart.com");
        Assert.assertEquals(50, seathHold.getSeats().size());
        //Reserve the seats which were in hold
        String confirmationCode = ticketService.reserveSeats(seathHold.getHoldId(), "bdavay@walmart.com");
        Assert.assertNotNull(confirmationCode);

        //If we try to reserve again using the hold id and email id it should send us null and not try to
        //reserve the same
        confirmationCode = ticketService.reserveSeats(seathHold.getHoldId(), "bdavay@walmart.com");
        Assert.assertNull(confirmationCode);

    }
}