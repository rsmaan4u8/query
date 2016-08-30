package com.api.view;

import com.api.model.SearchItem;
import com.api.model.SearchResult;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConsoleBasedResultsView implements View {
    public static final String SEARCH_RESULT_STREAM_SHOULD_NOT_BE_NULL = "searchResultStream should not be null";
    public static final String NO_RESULT_FOUND_TRY_SEARCHING_SOME_OTHER_TITLE = "No result found. Try searching some other title.";
    private Stream<SearchResult> searchResultStream;

    public ConsoleBasedResultsView(Stream<SearchResult> searchResultStream) {
        assert searchResultStream != null : SEARCH_RESULT_STREAM_SHOULD_NOT_BE_NULL;
        this.searchResultStream = searchResultStream;
    }

    @Override
    public String render() {
        String result = searchResultStream.filter(searchResult1 -> searchResult1.hasResult())
                .map(searchResult -> {
                    return searchResult.getResults().stream().map(searchItem -> print(searchItem))
                            .collect(Collectors.joining(System.lineSeparator()));
                }).collect(Collectors.joining(System.lineSeparator()));
        return result.isEmpty() ? NO_RESULT_FOUND_TRY_SEARCHING_SOME_OTHER_TITLE : result;
    }

    private String print(SearchItem searchItem) {
        return String.format("Provider : %s Title : %s Year : %s Director : %s", searchItem.getProvider(), searchItem.getTitle(), searchItem.getYear(), searchItem.getDirector());
    }

    public Stream<SearchResult> getSearchResultStream() {
        return searchResultStream;
    }
}
