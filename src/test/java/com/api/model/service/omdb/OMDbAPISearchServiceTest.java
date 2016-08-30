package com.api.model.service.omdb;

import com.api.model.SearchItem;
import com.api.model.SearchProvider;
import com.api.model.SearchResult;
import com.api.model.specification.APISearchSpecification;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static com.api.model.service.omdb.OMDbAPISearchService.OMDBAPI_BASE_URL;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OMDbAPISearchServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private OMDbAPISearchService service = new OMDbAPISearchService();

    @Test
    public void testSearch_whenSpecHasMusic_shouldSearchWithMusicAndDecorateWithYear() throws Exception {
        APISearchSpecification specification = mock(APISearchSpecification.class);
        OMDbSearchResultEntry searchResultEntry = mock(OMDbSearchResultEntry.class);
        List<? extends SearchItem> searchItems = spy(Lists.newArrayList(searchResultEntry));
        OMDbSearchResult omDbSearchResult = mock(OMDbSearchResult.class);
        OMDbSearchResultEntry omDbSearchResultEntryForIMDBID = mock(OMDbSearchResultEntry.class);

        when(specification.getMusicTitle()).thenReturn(Optional.ofNullable("MUSIC_TITLE"));
        when(specification.getMovieTitle()).thenReturn(Optional.ofNullable(null));
        when(restTemplate.getForObject(OMDBAPI_BASE_URL + "?s=MUSIC_TITLE&r=json", OMDbSearchResult.class))
                .thenReturn(omDbSearchResult);
        when(omDbSearchResult.hasResult()).thenReturn(true);
        doReturn(searchItems).when(omDbSearchResult).getResults();
        when(searchResultEntry.getImdbID()).thenReturn("IMDB_ID");
        when(restTemplate.getForObject(OMDBAPI_BASE_URL + "?i=IMDB_ID", OMDbSearchResultEntry.class))
                .thenReturn(omDbSearchResultEntryForIMDBID);
        when(omDbSearchResultEntryForIMDBID.getDirector()).thenReturn("DIRECTOR");

        SearchResult actual = service.search(specification);

        assertEquals(omDbSearchResult, actual);
        verify(searchResultEntry, times(1)).setDirector("DIRECTOR");
        verify(searchItems, times(1)).parallelStream();
        verify(restTemplate, times(1)).getForObject(OMDBAPI_BASE_URL + "?s=MUSIC_TITLE&r=json", OMDbSearchResult.class);
        verify(restTemplate, times(1)).getForObject(OMDBAPI_BASE_URL + "?i=IMDB_ID", OMDbSearchResultEntry.class);
    }

    @Test
    public void testSearch_whenSpecHasMovie_shouldSearchWithMovieAndDecorateWithYear() throws Exception {
        APISearchSpecification specification = mock(APISearchSpecification.class);
        OMDbSearchResultEntry searchResultEntry = mock(OMDbSearchResultEntry.class);
        List<? extends SearchItem> searchItems = spy(Lists.newArrayList(searchResultEntry));
        OMDbSearchResult omDbSearchResult = mock(OMDbSearchResult.class);
        OMDbSearchResultEntry omDbSearchResultEntryForIMDBID = mock(OMDbSearchResultEntry.class);

        when(specification.getMusicTitle()).thenReturn(Optional.ofNullable(null));
        when(specification.getMovieTitle()).thenReturn(Optional.ofNullable("MOVIE_TITLE"));
        when(restTemplate.getForObject(OMDBAPI_BASE_URL + "?s=MOVIE_TITLE&r=json", OMDbSearchResult.class))
                .thenReturn(omDbSearchResult);
        when(omDbSearchResult.hasResult()).thenReturn(true);
        doReturn(searchItems).when(omDbSearchResult).getResults();
        when(searchResultEntry.getImdbID()).thenReturn("IMDB_ID");
        when(restTemplate.getForObject(OMDBAPI_BASE_URL + "?i=IMDB_ID", OMDbSearchResultEntry.class))
                .thenReturn(omDbSearchResultEntryForIMDBID);
        when(omDbSearchResultEntryForIMDBID.getDirector()).thenReturn("MOVIE DIRECTOR");

        SearchResult actual = service.search(specification);

        assertEquals(omDbSearchResult, actual);
        verify(searchResultEntry, times(1)).setDirector("MOVIE DIRECTOR");
        verify(searchItems, times(1)).parallelStream();
        verify(restTemplate, times(1)).getForObject(OMDBAPI_BASE_URL + "?s=MOVIE_TITLE&r=json", OMDbSearchResult.class);
        verify(restTemplate, times(1)).getForObject(OMDBAPI_BASE_URL + "?i=IMDB_ID", OMDbSearchResultEntry.class);
    }

    @Test
    public void testSearch_whenSearchHasNoResult_shouldNotDecorate() throws Exception {
        APISearchSpecification specification = mock(APISearchSpecification.class);
        OMDbSearchResultEntry searchResultEntry = mock(OMDbSearchResultEntry.class);
        List<? extends SearchItem> searchItems = spy(Lists.newArrayList(searchResultEntry));
        OMDbSearchResult omDbSearchResult = mock(OMDbSearchResult.class);

        when(specification.getMusicTitle()).thenReturn(Optional.ofNullable(null));
        when(specification.getMovieTitle()).thenReturn(Optional.ofNullable("MOVIE_TITLE"));
        when(restTemplate.getForObject(OMDBAPI_BASE_URL + "?s=MOVIE_TITLE&r=json", OMDbSearchResult.class))
                .thenReturn(omDbSearchResult);
        when(omDbSearchResult.hasResult()).thenReturn(false);

        SearchResult actual = service.search(specification);

        verify(restTemplate, times(1)).getForObject(any(String.class), any());
        assertEquals(omDbSearchResult, actual);
    }

    @Test
    public void testGetSearchProvider_shouldReturnOMDB() throws Exception {
        assertEquals(SearchProvider.OMDB, service.getSearchProvider());
    }
}