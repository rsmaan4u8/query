package com.api.model.specification;

import com.api.model.SearchProvider;
import org.junit.Test;

import static com.api.model.specification.APISearchSpecificationFactory.*;
import static org.junit.Assert.*;

public class APISearchSpecificationFactoryTest {
    private APISearchSpecificationFactory factory = new APISearchSpecificationFactory();

    @Test
    public void testCreate_whenNeitherMovieTitleNorMusicTitle_shouldCreateSpecWithProblem() throws Exception {
        APISearchSpecification specification = factory.create(null, null, "itunes");

        assertEquals(1, specification.getProblems().size());
        assertEquals(PROBLEM_PROVIDE_EITHER_MOVIE_OR_MUSIC, specification.getProblems().get(0));
    }

    @Test
    public void testCreate_whenBothMovieTitleAndMusicTitle_shouldCreateSpecWithProblem() throws Exception {
        APISearchSpecification specification = factory.create("movie", "music", "itunes");

        assertEquals(1, specification.getProblems().size());
        assertEquals(PROBLEM_SEARCHING_MOVIE_AND_MUSIC_TOGETHER, specification.getProblems().get(0));
    }

    @Test
    public void testCreate_whenMovieTitleEmpty_shouldCreateSpecWithProblem() throws Exception {
        APISearchSpecification specification = factory.create("", null, "itunes");

        assertEquals(1, specification.getProblems().size());
        assertEquals(PROBLEM_MOVIE_SEARCH_VALUE_IS_EMPTY, specification.getProblems().get(0));
    }

    @Test
    public void testCreate_whenMusicTitleEmpty_shouldCreateSpecWithProblem() throws Exception {
        APISearchSpecification specification = factory.create(null, "", "itunes");

        assertEquals(1, specification.getProblems().size());
        assertEquals(PROBLEM_MUSIC_SEARCH_VALUE_IS_EMPTY, specification.getProblems().get(0));
    }

    @Test
    public void testCreate_whenAPIEmpty_shouldCreateSpecWithProblem() throws Exception {
        APISearchSpecification specification = factory.create(null, "music", "");

        assertEquals(1, specification.getProblems().size());
        assertEquals(PROBLEM_PLEASE_SPECIFY_SEARCH_API + "ITUNES OMDB ", specification.getProblems().get(0));
    }

    @Test
    public void testCreate_whenInvalidAPI_shouldCreateSpecWithProblem() throws Exception {
        APISearchSpecification specification = factory.create(null, "music", "UNSUPPORTED_API");

        assertEquals(1, specification.getProblems().size());
        assertEquals(PROBLEM + "UNSUPPORTED_API not supported " + SearchProvider.SEARCH_IN_ITUNES_OR_OMDB, specification.getProblems().get(0));
    }

    @Test
    public void testCreate_whenValidMusicTitle_shouldCreateSpecWithoutProblem() throws Exception {
        APISearchSpecification specification = factory.create(null, "music", "itunes");

        assertFalse(specification.hasProblems());
        assertEquals("music", specification.getMusicTitle().get());
    }

    @Test
    public void testCreate_whenValidMovieTitle_shouldCreateSpecWithoutProblem() throws Exception {
        APISearchSpecification specification = factory.create("movie", null, "itunes");

        assertFalse(specification.hasProblems());
        assertEquals("movie", specification.getMovieTitle().get());
    }

    @Test
    public void testCreate_whenValidMovieTitleWithMultipleAPIS_shouldCreateSpecWithoutProblem() throws Exception {
        APISearchSpecification specification = factory.create("movie", null, "itunes omdb");

        assertFalse(specification.hasProblems());
        assertEquals("movie", specification.getMovieTitle().get());
        assertTrue(specification.getSearchProviders().contains(SearchProvider.ITUNES));
        assertTrue(specification.getSearchProviders().contains(SearchProvider.OMDB));
    }

    @Test
    public void testCreate_whenInvalidAPIWithValidMovieTitleWithMultipleAPIS_shouldCreateSpecWithoutProblem() throws Exception {
        APISearchSpecification specification = factory.create("movie", null, "itunes omdb invalid");

        assertTrue(specification.hasProblems());
        assertEquals("movie", specification.getMovieTitle().get());
        assertTrue(specification.getSearchProviders().contains(SearchProvider.ITUNES));
        assertTrue(specification.getSearchProviders().contains(SearchProvider.OMDB));
        assertEquals(PROBLEM + "invalid not supported " + SearchProvider.SEARCH_IN_ITUNES_OR_OMDB, specification.getProblems().get(0));
    }

}