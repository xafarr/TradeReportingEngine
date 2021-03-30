package com.xafarr;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

public class EventFilterTest {

    private Event invalidEvent2;
    private Event validEvent1;
    private Event validEvent3;
    private Event validEvent2;
    private Event anagramEvent;
    private Event invalidEvent1;
    private Filter<Event> filter;

    @Before
    public void setup() {
        filter = new EventFilter();
        validEvent1 = new Event("EMU_BANK", "BISON_BANK", "USD", 500.0);
        validEvent2 = new Event("EMU_BANK", "BISON_BANK", "USD", 600.0);
        validEvent3 = new Event("LEFT_BANK", "EMU_BANK", "AUD", 200.0);
        anagramEvent = new Event("LEFT_BANK", "FELT_KANB", "AUD", 100.0);
        invalidEvent1 = new Event("EMU_BANK", "BISON_BANK", "AUD", 600.0);
        invalidEvent2 = new Event("LEFT_BANK", "EMU_BANK", "USD", 600.0);
    }

    @Test
    public void filter_whenAllValid_returnAll() {
        List<Event> events = this.filter.filter(Arrays.asList(validEvent1, validEvent2, validEvent3));
        Assertions.assertThat(events)
                .isNotNull()
                .hasSize(3);
    }

    @Test
    public void filter_whenHasAnagram_thenFilterAnagramAndReturnOnlyValid() {
        List<Event> events = this.filter.filter(Arrays.asList(validEvent1, validEvent2, anagramEvent));
        Assertions.assertThat(events)
                .isNotNull()
                .hasSize(2);
    }

    @Test
    public void filter_whenHasInvalid_thenReturnOnlyValid() {
        List<Event> events = this.filter.filter(Arrays.asList(validEvent1, validEvent2, invalidEvent1, invalidEvent2));
        Assertions.assertThat(events)
                .isNotNull()
                .hasSize(2);
    }
}