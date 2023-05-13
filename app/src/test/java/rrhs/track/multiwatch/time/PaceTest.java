package rrhs.track.multiwatch.time;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PaceTest {
    Pace pace;

    @Test
    public void testSecondsConstructor() {
        pace = new Pace(0.52);//only milliseconds
        assertEquals(520, pace.getMilliseconds());
        assertEquals(0, pace.getSeconds());
        assertEquals(0, pace.getMinutes());
        assertEquals(0.52, pace.getTotalTimeSeconds(), 0.001);

        pace = new Pace(35);//only seconds
        assertEquals(0, pace.getMilliseconds());
        assertEquals(35, pace.getSeconds());
        assertEquals(0, pace.getMinutes());
        assertEquals(35, pace.getTotalTimeSeconds(), 0.001);

        pace = new Pace(120);//only minutes
        assertEquals(0, pace.getMilliseconds());
        assertEquals(0, pace.getSeconds());
        assertEquals(2, pace.getMinutes());
        assertEquals(120, pace.getTotalTimeSeconds(), 0.001);

        pace = new Pace(35.1);//seconds and milliseconds
        assertEquals(100, pace.getMilliseconds());
        assertEquals(35, pace.getSeconds());
        assertEquals(0, pace.getMinutes());
        assertEquals(35.1, pace.getTotalTimeSeconds(), 0.001);

        pace = new Pace(123) ;//seconds and minutes
        assertEquals(0, pace.getMilliseconds());
        assertEquals(3, pace.getSeconds());
        assertEquals(2, pace.getMinutes());
        assertEquals(123, pace.getTotalTimeSeconds(), 0.001);

        pace = new Pace(60.3);//minutes and milliseconds
        assertEquals(300, pace.getMilliseconds());
        assertEquals(0, pace.getSeconds());
        assertEquals(1, pace.getMinutes());
        assertEquals(60.3, pace.getTotalTimeSeconds(), 0.001);

        pace = new Pace(195.7);//all
        assertEquals(700, pace.getMilliseconds());
        assertEquals(15, pace.getSeconds());
        assertEquals(3, pace.getMinutes());
        assertEquals(195.7, pace.getTotalTimeSeconds(), 0.001);
    }

}