package com.walmart.tech.service;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by bdavay on 12/15/15.
 */

@Test
public class TestCaseSample {

    @BeforeClass
    public void setUp() {
        // code that will be invoked when this test is instantiated
    }

    @Test
    public void testTest() {
        Assert.assertEquals(true, true);
    }
}




