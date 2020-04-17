package com.techyourchance.unittestingfundamentals.exercise1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class NegativeNumberValidatorTest {

    NegativeNumberValidator sut;

    @Before
    public void setup() {
        sut = new NegativeNumberValidator();
    }

    @Test
    public void test1() {
        boolean result = sut.isNegative(3);
        Assert.assertThat(result, is(false));
    }

    @Test
    public void test2() {
        boolean result = sut.isNegative(0);
        Assert.assertThat(result, is(false));
    }

    @Test
    public void test3() {
        boolean result = sut.isNegative(-2);
        Assert.assertThat(result, is(true));
    }

}