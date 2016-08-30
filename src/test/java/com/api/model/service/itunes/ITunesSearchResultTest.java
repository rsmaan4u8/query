package com.api.model.service.itunes;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ITunesSearchResultTest {

    @Test
    public void testHasResult_whenZeroResult_shouldReturnFalse() throws Exception {
        ITunesSearchResult searchResult = new ITunesSearchResult();
        searchResult.setResultCount(0);

        assertFalse(searchResult.hasResult());
    }

    @Test
    public void testHasResult_whenOneResult_shouldReturnTrue() throws Exception {
        ITunesSearchResult searchResult = new ITunesSearchResult();
        searchResult.setResultCount(1);

        assertTrue(searchResult.hasResult());
    }

    @Test
    public void testHasResult_whenNegativeResult_shouldReturnFalse() throws Exception {
        ITunesSearchResult searchResult = new ITunesSearchResult();
        searchResult.setResultCount(-1);

        assertFalse(searchResult.hasResult());
    }

}