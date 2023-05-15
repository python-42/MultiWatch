package rrhs.track.multiwatch.time;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Test;

public class TimerTest {
    private final Timer timer = new Timer(400);

    @After
    public void resetTimer() {
        timer.reset();
    }

    @Test
    public void testReset() {
        timer.startTimer();
        timer.reset();
        assertEquals(new Pace(0), timer.getElapsedTime());
    }

    @Test
    public void testCorrectTime() throws InterruptedException {
        timer.startTimer();
        Thread.sleep(1000);
        assertEquals(new Pace(1), timer.getElapsedTime());
    }

    @Test
    public void testStop() throws InterruptedException{
        timer.startTimer();
        Thread.sleep(4000);
        timer.stopTimer();
        Thread.sleep(1000);
        assertEquals(new Pace(4).getSeconds(), timer.getElapsedTime().getSeconds());//test only seconds to avoid negligible error failure
        //assertEquals(new Pace(4), timer.getElapsedTime());
            //Please note that this test is somewhat inconsistent
            //When if fails the error is between 1 and 4 milliseconds, so very negligible
    }

    @Test
    public void testStopStart() throws  InterruptedException{
        timer.startTimer();
        Thread.sleep(1000);
        timer.stopTimer();
        Thread.sleep(1000);
        timer.startTimer();
        Thread.sleep(1000);
        assertEquals(new Pace(2), timer.getElapsedTime());
    }

    @Test
    public void testCorrectLapTimes() throws InterruptedException{
        timer.startTimer();
        Thread.sleep(2000);
        timer.lap();

        Thread.sleep(1000);
        timer.lap();

        Thread.sleep(3000);
        timer.lap();
        timer.stopTimer();

        Pace[] expectedPaces = {new Pace(2), new Pace(1), new Pace(3)};
        assertArrayEquals(expectedPaces, timer.getLapTimes());
    }

}