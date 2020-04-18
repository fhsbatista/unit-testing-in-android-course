package com.techyourchance.unittestingfundamentals.exercise2;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StringDuplicatorTest {

    StringDuplicator sut;

    @Before
    public void setup() {
        sut = new StringDuplicator();
    }

    @Test
    public void stringDuplicator_emptyString_emptyStringReturned() {
        String result = sut.duplicate("");
        assertThat(result, is(""));
    }

    @Test
    public void stringDuplicator_string_sameStringTwiceReturned() {
        String result = sut.duplicate("fernando");
        assertThat(result, is("fernandofernando"));
    }
}