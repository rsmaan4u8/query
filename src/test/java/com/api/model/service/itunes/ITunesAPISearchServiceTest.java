package com.api.model.service.itunes;

import com.api.model.SearchProvider;
import com.api.model.SearchResult;
import com.api.model.specification.APISearchSpecification;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static com.api.model.service.itunes.ITunesAPISearchService.ITUNES_BASE_URL;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ITunesAPISearchServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ITunesAPISearchService service = new ITunesAPISearchService();

    @Test
    public void testSearch_whenSpecHasMusic_shouldSearchWithMusic() throws Exception {

        APISearchSpecification specification = mock(APISearchSpecification.class);
        ITunesSearchResult iTunesSearchResult = mock(ITunesSearchResult.class);

        when(specification.getMusicTitle()).thenReturn(Optional.ofNullable("MUSIC_TITLE"));
        when(specification.getMovieTitle()).thenReturn(Optional.ofNullable(null));
        when(restTemplate.getForObject(ITUNES_BASE_URL + "?media=music&term=MUSIC_TITLE", ITunesSearchResult.class))
                .thenReturn(iTunesSearchResult);

        SearchResult actual = service.search(specification);

        assertEquals(iTunesSearchResult, actual);
    }

    @Test
    public void testSearch_whenSpecHasMovie_shouldSearchWithMovie() throws Exception {

        APISearchSpecification specification = mock(APISearchSpecification.class);
        ITunesSearchResult iTunesSearchResult = mock(ITunesSearchResult.class);

        when(specification.getMusicTitle()).thenReturn(Optional.ofNullable(null));
        when(specification.getMovieTitle()).thenReturn(Optional.ofNullable("MOVIE TITLE"));
        when(restTemplate.getForObject(ITUNES_BASE_URL + "?media=movie&term=MOVIE TITLE", ITunesSearchResult.class))
                .thenReturn(iTunesSearchResult);

        SearchResult actual = service.search(specification);

        assertEquals(iTunesSearchResult, actual);
    }

    @Test
    public void testGetSearchProvider_shouldReturnITunes() throws Exception {
        assertEquals(SearchProvider.ITUNES, service.getSearchProvider());
    }
}