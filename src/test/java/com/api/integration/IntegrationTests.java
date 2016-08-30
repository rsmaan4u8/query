package com.api.integration;

import com.api.AppRunner;
import com.api.controller.APISearchController;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

public class IntegrationTests {
    private final String lineSeparator = System.getProperty("line.separator");
    private APISearchController controller;

    private void clearAllSearchProperties() {
        System.clearProperty("api");
        System.clearProperty("movie");
        System.clearProperty("music");
    }

    @Test
    public void testMain_whenNoAPIGiven_shouldPrintProblem() throws Exception {
        final ByteArrayOutputStream output = captureOutput();

        clearAllSearchProperties();

        AppRunner.main(new String[]{});

        String expected = "Problem : Please specify API to use ITUNES OMDB";

        assertThat(output.toString(), containsString(expected));
    }

    @Test
    public void testMain_whenNoTitleGiven_shouldPrintProblem() throws Exception {
        final ByteArrayOutputStream output = captureOutput();

        clearAllSearchProperties();
        System.setProperty("api", "omdb");

        AppRunner.main(new String[]{});

        String expected = "Problem : Please provide either movie title or music title to search";

        assertThat(output.toString(), containsString(expected));
    }

    @Test
    public void testMain_whenMovieTitleIsEmpty_shouldPrintProblem() throws Exception {
        final ByteArrayOutputStream output = captureOutput();

        clearAllSearchProperties();
        System.setProperty("api", "omdb");
        System.setProperty("movie", "");

        AppRunner.main(new String[]{});

        String expected = "Problem : Movie search value is empty";

        assertThat(output.toString(), containsString(expected));
    }

    @Test
    public void testMain_whenMusicTitleIsEmpty_shouldPrintProblem() throws Exception {
        final ByteArrayOutputStream output = captureOutput();

        clearAllSearchProperties();
        System.setProperty("api", "omdb");
        System.setProperty("music", "");

        AppRunner.main(new String[]{});

        String expected = "Problem : Music search value is empty";

        assertThat(output.toString(), containsString(expected));
    }

    @Test
    public void testMain_whenBothMusicAndMovieSearchOptionsAreGiven_shouldPrintProblem() throws Exception {
        final ByteArrayOutputStream output = captureOutput();

        clearAllSearchProperties();
        System.setProperty("api", "omdb");
        System.setProperty("movie", "Indiana Jones");
        System.setProperty("music", "Bob Marley");

        AppRunner.main(new String[]{});

        String expected = "Problem : You cannot search both movie and music at same time.Please provide either music or movie option";

        assertThat(output.toString(), containsString(expected));
    }

    @Test
    public void testMain_whenSearchMovieIndianaJonesInItunes_shouldPrint4Movies() throws Exception {
        final ByteArrayOutputStream output = captureOutput();

        clearAllSearchProperties();
        System.setProperty("api", "itunes");
        System.setProperty("movie", "Indiana Jones");

        AppRunner.main(new String[]{});

        String expected1 = "Provider : ITUNES Title : Indiana Jones and the Raiders of the Lost Ark Year : 1981 Director : Steven Spielberg";
        String expected2 = "Provider : ITUNES Title : Indiana Jones and the Last Crusade Year : 1989 Director : Steven Spielberg";
        String expected3 = "Provider : ITUNES Title : Indiana Jones and the Kingdom of the Crystal Skull Year : 2008 Director : Steven Spielberg";
        String expected4 = "Provider : ITUNES Title : Indiana Jones and the Temple of Doom Year : 1984 Director : Steven Spielberg";

        String actual = output.toString();

        assertThat(actual, containsString(expected1));
        assertThat(actual, containsString(expected2));
        assertThat(actual, containsString(expected3));
        assertThat(actual, containsString(expected4));
    }


    @Test
    public void testMain_whenSearchMusicBobMarley_shouldFindMusic() throws Exception {
        final ByteArrayOutputStream output = captureOutput();

        clearAllSearchProperties();
        System.setProperty("api", "OMDB ITUNES");
        System.setProperty("music", "Bob Marley");

        AppRunner.main(new String[]{});

        String expected1 = "ITUNES Title : Is This Love Year : 1984 Director : Bob Marley & The Wailers";
        String expected2 = "ITUNES Title : One Love / People Get Ready Year : 1984 Director : Bob Marley & The Wailers";
        String expected3 = "ITUNES Title : Jamming Year : 1984 Director : Bob Marley & The Wailers";
        String expected4 = "ITUNES Title : No Woman, No Cry Year : 1984 Director : Bob Marley & The Wailers";

        String expected5 = "OMDB Title : Classic Albums: Bob Marley & the Wailers - Catch a Fire Year : 1999 Director : Jeremy Marre";
        String expected6 = "OMDB Title : Bob Marley: The Legend Live Year : 2003 Director : Don Gazzanaga";
        String expected7 = "OMDB Title : Bob Marley Year : 1981 Director : Stefan Paul";
        String expected8 = "OMDB Title : Bob Marley and the Wailers: Live! At the Rainbow Year : 1991 Director : Keef";

        String actual = output.toString();

        assertThat(actual, containsString(expected1));
        assertThat(actual, containsString(expected2));
        assertThat(actual, containsString(expected3));
        assertThat(actual, containsString(expected4));

        assertThat(actual, containsString(expected5));
        assertThat(actual, containsString(expected6));
        assertThat(actual, containsString(expected7));
        assertThat(actual, containsString(expected8));
    }

    private ByteArrayOutputStream captureOutput() {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        return output;
    }
}
