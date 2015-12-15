package com.walmart.tech.service;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by bdavay on 12/15/15.
 */
public interface ServiceConfigConstant {

    String ORCHESTRA = "Orchestra";
    String MAIN = "Main";
    String BALCONY1 = "Balcony 1";
    String BALCONY2 = "Balcony 2";

    int LEVEL1_ROWS = 25;
    int LEVEL2_ROWS = 20;
    int LEVEL3_ROWS = 15;
    int LEVEL4_ROWS = 15;

    int LEVEL1_SEATS_PER_ROW = 50;
    int LEVEL2_SEATS_PER_ROW = 100;
    int LEVEL3_SEATS_PER_ROW = 100;
    int LEVEL4_SEATS_PER_ROW = 100;

    int HOLD_1_HOUR = 1 * 60 * 60 * 1000; // 1 hour hold in millis

    AtomicInteger HOLD_ID_GENERATOR = new AtomicInteger(1);


}
