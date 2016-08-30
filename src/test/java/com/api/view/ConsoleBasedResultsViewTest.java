package com.api.view;

import com.api.model.SearchItem;
import com.api.model.SearchResult;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static com.api.view.ConsoleBasedResultsView.NO_RESULT_FOUND_TRY_SEARCHING_SOME_OTHER_TITLE;
import static com.api.view.ConsoleBasedResultsView.SEARCH_RESULT_STREAM_SHOULD_NOT_BE_NULL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ConsoleBasedResultsViewTest {

    @Test
    public void testConstructor_shouldDoNullCheckOnResultList() {
        try {
            new ConsoleBasedResultsView(null);
            assertTrue("Should not reach here", false);
        } catch (AssertionError ae) {
            assertEquals(SEARCH_RESULT_STREAM_SHOULD_NOT_BE_NULL, ae.getMessage());
        }
    }

    @Test
    public void testRender_whenHasResults_shouldConcatResultsAndReturnString() {
        SearchResult searchResult1 = mock(SearchResult.class);
        SearchResult searchResult2 = mock(SearchResult.class);
        SearchItem searchItem1 = mock(SearchItem.class);
        SearchItem searchItem2 = mock(SearchItem.class);
        List<? extends SearchItem> searchItems1 = Lists.newArrayList(searchItem1);
        List<? extends SearchItem> searchItems2 = Lists.newArrayList(searchItem2);

        when(searchResult1.hasResult()).thenReturn(true);
        doReturn(searchItems1).when(searchResult1).getResults();

        when(searchResult2.hasResult()).thenReturn(true);
        doReturn(searchItems2).when(searchResult2).getResults();

        when(searchItem1.getDirector()).thenReturn("DIR 1");
        when(searchItem1.getYear()).thenReturn("YEAR 1");
        when(searchItem1.getTitle()).thenReturn("TITLE 1");
        when(searchItem1.getProvider()).thenReturn("PROV 1");

        when(searchItem2.getDirector()).thenReturn("DIR 2");
        when(searchItem2.getYear()).thenReturn("YEAR 2");
        when(searchItem2.getTitle()).thenReturn("TITLE 2");
        when(searchItem2.getProvider()).thenReturn("PROV 2");

        String actual = new ConsoleBasedResultsView(Lists.newArrayList(searchResult1, searchResult2).stream()).render();

        String expected = "Provider : PROV 1 Title : TITLE 1 Year : YEAR 1 Director : DIR 1" +
                System.lineSeparator() +
                "Provider : PROV 2 Title : TITLE 2 Year : YEAR 2 Director : DIR 2";

        assertEquals(expected, actual);
    }

    @Test
    public void testRender_whenThereIsNoResultForSomeAPI_shouldFilterWithHasResultAndReturnString() {
        SearchResult searchResult1 = mock(SearchResult.class);
        SearchResult searchResult2 = mock(SearchResult.class);
        SearchItem searchItem1 = mock(SearchItem.class);
        SearchItem searchItem2 = mock(SearchItem.class);
        List<? extends SearchItem> searchItems1 = Lists.newArrayList(searchItem1);
        List<? extends SearchItem> searchItems2 = Lists.newArrayList(searchItem2);

        when(searchResult1.hasResult()).thenReturn(false);
        doReturn(searchItems1).when(searchResult1).getResults();

        when(searchResult2.hasResult()).thenReturn(true);
        doReturn(searchItems2).when(searchResult2).getResults();

        when(searchItem2.getDirector()).thenReturn("DIR 2");
        when(searchItem2.getYear()).thenReturn("YEAR 2");
        when(searchItem2.getTitle()).thenReturn("TITLE 2");
        when(searchItem2.getProvider()).thenReturn("PROV 2");

        String actual = new ConsoleBasedResultsView(Lists.newArrayList(searchResult1, searchResult2).stream()).render();

        String expected = "Provider : PROV 2 Title : TITLE 2 Year : YEAR 2 Director : DIR 2";

        assertEquals(expected, actual);
        verify(searchResult1, never()).getResults();
    }

    @Test
    public void testRender_whenNorResult_shouldReturnMessage() {
        SearchResult searchResult1 = mock(SearchResult.class);
        SearchResult searchResult2 = mock(SearchResult.class);

        when(searchResult1.hasResult()).thenReturn(false);

        when(searchResult2.hasResult()).thenReturn(false);

        String actual = new ConsoleBasedResultsView(Lists.newArrayList(searchResult1, searchResult2).stream()).render();

        assertEquals(NO_RESULT_FOUND_TRY_SEARCHING_SOME_OTHER_TITLE, actual);
    }

}