package com.api.model.service.omdb;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OMDbSearchResultEntryTest {

    @Test
    public void testGetProvider_shouldReturnOMDB() throws Exception {
        OMDbSearchResultEntry resultEntry = new OMDbSearchResultEntry();

        assertEquals("OMDB", resultEntry.getProvider());
    }
}