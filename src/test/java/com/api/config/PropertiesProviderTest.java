package com.api.config;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PropertiesProviderTest {

    @Test
    public void testGetAPIProviders_whenAPISet_shouldReturn() throws Exception {
        final String apiValue = "API_VALUE";

        System.setProperty("api", apiValue);

        assertEquals(apiValue, new PropertiesProvider().getAPIProviders());
    }

    @Test
    public void testGetAPIProviders_whenAPINotSet_shouldReturnNull() throws Exception {
        System.clearProperty("api");

        assertNull(new PropertiesProvider().getAPIProviders());
    }

    @Test
    public void testGetMovieTitle_whenMovieSet_shouldReturn() throws Exception {
        final String movieTitle = "MOVIE_TITLE";

        System.setProperty("movie", movieTitle);

        assertEquals(movieTitle, new PropertiesProvider().getMovieTitle());

    }

    @Test
    public void testGetMovieTitle_whenMovieNotSet_shouldReturnNull() throws Exception {
        System.clearProperty("movie");

        assertNull(new PropertiesProvider().getMovieTitle());
    }

    @Test
    public void testGetMusicTitle_whenMusicSet_shouldReturn() throws Exception {
        final String musicTitle = "MUSIC_TITLE";

        System.setProperty("music", musicTitle);

        assertEquals(musicTitle, new PropertiesProvider().getMusicTitle());

    }

    @Test
    public void testGetMusicTitle_whenMusicNotSet_shouldReturnNull() throws Exception {
        System.clearProperty("music");

        assertNull(new PropertiesProvider().getMusicTitle());
    }
}