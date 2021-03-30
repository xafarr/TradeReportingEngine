package com.xafarr;

import java.util.List;
import java.util.stream.Collectors;

public class EventFilter implements Filter<Event> {

    public static final String EMU_BANK = "EMU_BANK";
    public static final String BISON_BANK = "BISON_BANK";
    public static final String AUD = "AUD";
    public static final String USD = "USD";

    @Override
    public List<Event> filter(List<Event> items) {
        return items.parallelStream()
                .filter(this::isValidEvent)
                .collect(Collectors.toList());
    }

    private boolean isValidEvent(Event event) {
        return !isAnagram(event.getBuyer(), event.getSeller()) &&
                ((event.getSeller().equals(EMU_BANK) && event.getCurrency().equals(AUD)) ||
                        (event.getSeller().equals(BISON_BANK) && event.getCurrency().equals(USD)));
    }

    private boolean isAnagram(String buyer, String seller) {
        return getSortedCharList(buyer).equals(getSortedCharList(seller));
    }

    private List<Integer> getSortedCharList(String name) {
        return name.chars().sorted().boxed().collect(Collectors.toList());
    }
}
