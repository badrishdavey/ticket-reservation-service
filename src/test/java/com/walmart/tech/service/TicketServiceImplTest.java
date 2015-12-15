package com.walmart.tech.service;

import com.walmart.tech.domain.SeatHold;
import com.walmart.tech.service.impl.TicketServiceImpl;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Optional;
import java.util.logging.Logger;

/**
 * Created by bdavay on 12/15/15.
 */

public class TicketServiceImplTest {

    Logger log = Logger.getLogger(TicketServiceImpl.class.getName());

    TicketServiceImpl ticketService = null;

    @BeforeClass
    public void setUp() {
        ticketService = new TicketServiceImpl();
    }

    @Test(priority = 1)
    public void testNumSeatsAvailable() throws Exception {
        Assert.assertEquals(ticketService.numSeatsAvailable(Optional.of(1)), 1250);
        Assert.assertEquals(ticketService.numSeatsAvailable(Optional.of(2)), 2000);
        Assert.assertEquals(ticketService.numSeatsAvailable(Optional.of(3)), 1500);
        Assert.assertEquals(ticketService.numSeatsAvailable(Optional.of(4)), 1500);

    }

    @Test(priority = 2)
    public void testFindAndHoldSeats() throws Exception {
        SeatHold seathHold = ticketService.findAndHoldSeats(50, Optional.of(1), Optional.of(2), "bdavay@walmart.com");
        Assert.assertEquals(seathHold.getSeats().size(), 50);

    }

    @Test(priority = 3)
    public void testReserveSeats() throws Exception {
        SeatHold seathHold = ticketService.findAndHoldSeats(50, Optional.of(1), Optional.of(2), "bdavay@walmart.com");
        Assert.assertEquals(seathHold.getSeats().size(), 50);
        //Reserve the seats which were in hold
        String confirmationCode = ticketService.reserveSeats(seathHold.getHoldId(), "bdavay@walmart.com");
        Assert.assertNotNull(confirmationCode);

        //If we try to reserve again using the hold id and email id it should send us null and not try to
        //reserve the same
        confirmationCode = ticketService.reserveSeats(seathHold.getHoldId(), "bdavay@walmart.com");
        Assert.assertNull(confirmationCode);

    }

    @Test(priority = 4)
    public void testHoldTime() throws Exception {
        SeatHold seathHold = ticketService.findAndHoldSeats(50, Optional.of(1), Optional.of(2), "bdavay@walmart.com");
        Assert.assertEquals(seathHold.getSeats().size(), 50);

        Thread.sleep(6000);
        // The hold time is set to 5 secs for testing purpose and once it crosses it cannot be reserved and returns null confirmation code
        String confirmationCode = ticketService.reserveSeats(seathHold.getHoldId(), "bdavay@walmart.com");
        Assert.assertNull(confirmationCode);

    }


    @Test(priority = 5)
    public void testReserveSeatsAndCheckSeatsCountDrop() throws Exception {
        SeatHold seathHold = ticketService.findAndHoldSeats(50, Optional.of(1), Optional.of(2), "bdavay@walmart.com");
        Assert.assertEquals(seathHold.getSeats().size(), 50);
        //Reserve the seats which were in hold
        String confirmationCode = ticketService.reserveSeats(seathHold.getHoldId(), "bdavay@walmart.com");
        Assert.assertNotNull(confirmationCode);
        log.info("Confirmation Code : " + confirmationCode);

        //Expected seat counts drop by 50 for level 1
        Assert.assertEquals(ticketService.numSeatsAvailable(Optional.of(1)), 1050);

    }


    @Test(priority = 6)
    public void testReserveSeatsGivingMinValueOnly() throws Exception {
        SeatHold seathHold = ticketService.findAndHoldSeats(50, Optional.of(3), Optional.<Integer>empty(), "bdavay@walmart.com");
        Assert.assertEquals(seathHold.getSeats().size(), 50);
        //Reserve the seats which were in hold
        String confirmationCode = ticketService.reserveSeats(seathHold.getHoldId(), "bdavay@walmart.com");
        Assert.assertNotNull(confirmationCode);

        //Expected seat counts drop by 50 for level 1
        Assert.assertEquals(ticketService.numSeatsAvailable(Optional.of(3)), 1450);

    }


    @Test(priority = 7, threadPoolSize = 5, invocationCount = 5, timeOut = 10000)
    public void testReserveSeatsGivingMinValueOnlyMultiThread() throws Exception {
        SeatHold seathHold = ticketService.findAndHoldSeats(50, Optional.of(3), Optional.<Integer>empty(), "bdavay@walmart.com");
        Assert.assertEquals(seathHold.getSeats().size(), 50);
        //Reserve the seats which were in hold
        String confirmationCode = ticketService.reserveSeats(seathHold.getHoldId(), "bdavay@walmart.com");
        Assert.assertNotNull(confirmationCode);

    }

    @Test(priority = 8)
    public void testCheckRemainingSeats() {
        //Expected seat counts drop by 50 for level 1
        Assert.assertEquals(ticketService.numSeatsAvailable(Optional.of(3)), 1200);
    }

}