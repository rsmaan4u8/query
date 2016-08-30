package com.api.controller;

import com.api.model.SearchProvider;
import com.api.model.SearchResult;
import com.api.model.service.APISearchService;
import com.api.model.specification.APISearchSpecification;
import com.api.model.specification.APISearchSpecificationFactory;
import com.api.view.ConsoleBasedProblemsView;
import com.api.view.ConsoleBasedResultsView;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class APISearchControllerTest {
    @Mock
    private APISearchService apiSearchService1;

    @Mock
    private APISearchService apiSearchService2;

    @Mock
    private List<APISearchService> searchServices;

    @Mock
    private APISearchSpecificationFactory factory;

    @InjectMocks
    private APISearchController controller = new APISearchController();

    @Before
    public void before() {
    }

    @Test
    public void testPerformSearch_whenInvalidInput_shouldReturnProblemsView() throws Exception {
        String movieTitle = "movieTitle";
        String musicTitle = "musicTitle";
        String searchProviders = "itunes";
        List<String> problems = Lists.newArrayList("Some problem");

        APISearchSpecification apiSearchSpecification = mock(APISearchSpecification.class);

        when(factory.create(movieTitle, musicTitle, searchProviders)).thenReturn(apiSearchSpecification);
        when(apiSearchSpecification.hasProblems()).thenReturn(true);
        when(apiSearchSpecification.getProblems()).thenReturn(problems);

        ConsoleBasedProblemsView actual = (ConsoleBasedProblemsView) controller.performSearch(movieTitle, musicTitle, searchProviders);

        assertEquals(problems, actual.getMessages());
    }

    @Test
    public void testPerformSearch_whenValidInput_shouldReturnResult() throws Exception {
        String movieTitle = "movieTitle";
        String musicTitle = null;
        String searchProviders = "itunes omdb";

        APISearchSpecification apiSearchSpecification = mock(APISearchSpecification.class);
        Stream<APISearchService> parallelStream = Lists.newArrayList(apiSearchService1, apiSearchService2).parallelStream();
        SearchResult searchResult1 = mock(SearchResult.class);
        SearchResult searchResult2 = mock(SearchResult.class);

        when(factory.create(movieTitle, musicTitle, searchProviders)).thenReturn(apiSearchSpecification);
        when(apiSearchSpecification.hasProblems()).thenReturn(false);
        when(apiSearchSpecification.getSearchProviders()).thenReturn(Lists.newArrayList(SearchProvider.ITUNES, SearchProvider.OMDB));
        when(searchServices.parallelStream()).thenReturn(parallelStream);
        when(apiSearchService1.getSearchProvider()).thenReturn(SearchProvider.ITUNES);
        when(apiSearchService1.search(apiSearchSpecification)).thenReturn(searchResult1);
        when(apiSearchService2.getSearchProvider()).thenReturn(SearchProvider.OMDB);
        when(apiSearchService2.search(apiSearchSpecification)).thenReturn(searchResult2);

        ConsoleBasedResultsView resultsView = (ConsoleBasedResultsView) controller.performSearch(movieTitle, musicTitle, searchProviders);

        List<SearchResult> searchResultList = resultsView.getSearchResultStream().collect(Collectors.toList());

        assertEquals(2, searchResultList.size());
        assertTrue(searchResultList.contains(searchResult1));
        assertTrue(searchResultList.contains(searchResult2));
    }

    @Test
    public void testPerformSearch_whenOnlyOneAPIInInput_shouldSearchOnlyInAskedAPI() throws Exception {
        String movieTitle = "movieTitle";
        String musicTitle = null;
        String searchProviders = "itunes";

        APISearchSpecification apiSearchSpecification = mock(APISearchSpecification.class);
        Stream<APISearchService> parallelStream = Lists.newArrayList(apiSearchService1, apiSearchService2).parallelStream();
        SearchResult searchResult1 = mock(SearchResult.class);
        SearchResult searchResult2 = mock(SearchResult.class);

        when(factory.create(movieTitle, musicTitle, searchProviders)).thenReturn(apiSearchSpecification);
        when(apiSearchSpecification.hasProblems()).thenReturn(false);
        when(apiSearchSpecification.getSearchProviders()).thenReturn(Lists.newArrayList(SearchProvider.ITUNES));
        when(searchServices.parallelStream()).thenReturn(parallelStream);
        when(apiSearchService1.getSearchProvider()).thenReturn(SearchProvider.ITUNES);
        when(apiSearchService1.search(apiSearchSpecification)).thenReturn(searchResult1);
        when(apiSearchService2.getSearchProvider()).thenReturn(SearchProvider.OMDB);
        when(apiSearchService2.search(apiSearchSpecification)).thenReturn(searchResult2);

        ConsoleBasedResultsView resultsView = (ConsoleBasedResultsView) controller.performSearch(movieTitle, musicTitle, searchProviders);

        List<SearchResult> searchResultList = resultsView.getSearchResultStream().collect(Collectors.toList());

        assertEquals(1, searchResultList.size());
        assertTrue(searchResultList.contains(searchResult1));
        verify(apiSearchService2, never()).search(any());
    }

}