package com.techyourchance.unittestingfundamentals.exercise3;

import com.techyourchance.unittestingfundamentals.example3.Interval;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class IntervalsAdjacencyDetectorTest {

    IntervalsAdjacencyDetector sut;

    @Before
    public void setup() {
        sut = new IntervalsAdjacencyDetector();
    }

    @Test
    public void isAdjacent_interval1BeforeInterval2AndAdjacent_trueReturned() {
        Interval interval1 = new Interval(0, 5);
        Interval interval2 = new Interval(5, 10);
        boolean result = sut.isAdjacent(interval1, interval2);
        assertThat(result, is(true));
    }

    @Test
    public void isAdjacent_interval1BeforeInterval2AndNotAdjacent_falseReturned() {
        Interval interval1 = new Interval(0, 5);
        Interval interval2 = new Interval(6, 10);
        boolean result = sut.isAdjacent(interval1, interval2);
        assertThat(result, is(false));
    }

    @Test
    public void isAdjacent_interval2BeforeInterval1AndAdjacent_trueReturned() {
        Interval interval1 = new Interval(0, 5);
        Interval interval2 = new Interval(-10, 0);
        boolean result = sut.isAdjacent(interval1, interval2);
        assertThat(result, is(true));
    }

    @Test
    public void isAdjacent_interval2BeforeInterval1AndNotAdjacent_falseReturned() {
        Interval interval1 = new Interval(5, 19);
        Interval interval2 = new Interval(-5, 4);
        boolean result = sut.isAdjacent(interval1, interval2);
        assertThat(result, is(false));
    }

    @Test
    public void isAdjacent_interval1InsideInterval2_falseReturned() {
        Interval interval1 = new Interval(5, 19);
        Interval interval2 = new Interval(1, 25);
        boolean result = sut.isAdjacent(interval1, interval2);
        assertThat(result, is(false));
    }

    @Test
    public void isAdjacent_interval2InsideInterval1_falseReturned() {
        Interval interval1 = new Interval(5, 19);
        Interval interval2 = new Interval(7, 10);
        boolean result = sut.isAdjacent(interval1, interval2);
        assertThat(result, is(false));
    }

    @Test
    public void isAdjacent_interval1AndInterval2AreTheSame_falseReturned() {
        Interval interval1 = new Interval(0, 3);
        Interval interval2 = new Interval(0, 3);
        boolean result = sut.isAdjacent(interval1, interval2);
        assertThat(result, is(false));
    }

    //[0...5] [6...10]

    //when interval1 is before and adjacent to interval2, true returned
    //when interval2 is before and adjacent to interval1, true returned
    //when interval1 is before but not adjacent to interval2, false returned
    //when interval2 is before but not adjacent to interval 1, false returned
    //when interval1 is inside interval2 and adjacent on its end, false returned





}