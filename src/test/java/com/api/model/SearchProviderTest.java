package com.api.model;

import org.junit.Test;

import static com.api.model.SearchProvider.SEARCH_IN_ITUNES_OR_OMDB;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class SearchProviderTest {

    @Test
    public void testCreate_shouldCreateForAllEnumValues() throws Exception {
        assertEquals(SearchProvider.ITUNES, SearchProvider.create("itunes"));
        assertEquals(SearchProvider.OMDB, SearchProvider.create("omdb"));
    }

    @Test
    public void testCreate_whenUpperCaseLowerCase_shouldCreate() throws Exception {
        assertEquals(SearchProvider.ITUNES, SearchProvider.create("iTuNes"));
        assertEquals(SearchProvider.ITUNES, SearchProvider.create("ITUNES"));
        assertEquals(SearchProvider.OMDB, SearchProvider.create("OmdB"));
        assertEquals(SearchProvider.OMDB, SearchProvider.create("OMDB"));
    }

    @Test
    public void testCreate_whenInvalidProvider_shouldThrowNoSuchAPIProvider() throws Exception {
        try {
            SearchProvider.create("INVALID");
            fail("Should throw NoSuchAPIProviderException.");
        } catch (NoSuchAPIProviderException ex) {
            assertEquals(SEARCH_IN_ITUNES_OR_OMDB, ex.getMessage());
        }
    }

}