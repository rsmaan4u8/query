package com.api.model.service.itunes;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ITunesSearchResultEntryTest {

    @Test
    public void testGetYear_whenValidDate_shouldReturnYear() throws Exception {
        ITunesSearchResultEntry resultEntry = new ITunesSearchResultEntry();
        resultEntry.setReleaseDate("2006-03-01T08:00:00Z");

        assertEquals("2006", resultEntry.getYear());
    }

    @Test
    public void testGetYear_whenValidDateAnotherCase_shouldReturnYear() throws Exception {
        ITunesSearchResultEntry resultEntry = new ITunesSearchResultEntry();
        resultEntry.setReleaseDate("1901-01-01T07:00:00Z");

        assertEquals("1901", resultEntry.getYear());
    }

    @Test
    public void testGetYear_whenInValidDate_shouldReturnUnknown() throws Exception {
        ITunesSearchResultEntry resultEntry = new ITunesSearchResultEntry();
        resultEntry.setReleaseDate("INVALID");

        assertEquals("Unknown", resultEntry.getYear());
    }

    @Test
    public void testGetProvider_shouldReturnItunes() throws Exception {
        ITunesSearchResultEntry resultEntry = new ITunesSearchResultEntry();

        assertEquals("ITUNES", resultEntry.getProvider());
    }

}