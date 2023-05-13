package rrhs.track.multiwatch.time;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PaceCalcTest {

    @Test public void testHalfPace() {
        assertEquals(new Pace(2, 20), PaceCalculation.getHalfMilePace(200, 35));
        assertEquals(new Pace(2, 0), PaceCalculation.getHalfMilePace(1600, 240));
    }

    @Test
    public void testMilePace() {
        assertEquals(new Pace(5, 0), PaceCalculation.getMilePace(800, 150));
        assertEquals(new Pace(4, 30), PaceCalculation.getMilePace(2000, 337.5));
    }

    @Test
    public void testTwoMilePace() {
        assertEquals(new Pace(10, 40), PaceCalculation.getTwoMilePace(400, 80));
        assertEquals(new Pace(11, 0), PaceCalculation.getTwoMilePace(5000, 1031.25));
    }

    @Test
    public void test5kPace() {
        assertEquals(new Pace(17, 30), PaceCalculation.get5kPace(1000, 210));
        assertEquals(new Pace(18, 0), PaceCalculation.get5kPace(10000, 2160));
    }

}