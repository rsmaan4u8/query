package com.api.model.service.omdb;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OMDbSearchResultTest {

    @Test
    public void testHasResult_whenZeroResult_shouldReturnFalse() throws Exception {
        OMDbSearchResult searchResult = new OMDbSearchResult();
        searchResult.setTotalResults(0);

        assertFalse(searchResult.hasResult());
    }

    @Test
    public void testHasResult_whenOneResult_shouldReturnTrue() throws Exception {
        OMDbSearchResult searchResult = new OMDbSearchResult();
        searchResult.setTotalResults(1);

        assertTrue(searchResult.hasResult());
    }

    @Test
    public void testHasResult_whenNegativeResult_shouldReturnFalse() throws Exception {
        OMDbSearchResult searchResult = new OMDbSearchResult();
        searchResult.setTotalResults(-1);

        assertFalse(searchResult.hasResult());
    }
}