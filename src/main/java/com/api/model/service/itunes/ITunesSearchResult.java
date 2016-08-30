package com.api.model.service.itunes;

import com.api.model.SearchResult;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ITunesSearchResult implements SearchResult {
    private List<ITunesSearchResultEntry> results;
    private int resultCount;

    public List<ITunesSearchResultEntry> getResults() {
        return results;
    }

    public void setResults(List<ITunesSearchResultEntry> results) {
        this.results = results;
    }

    @Override
    public boolean hasResult() {
        return resultCount > 0;
    }

    public int getResultCount() {
        return resultCount;
    }

    public void setResultCount(int resultCount) {
        this.resultCount = resultCount;
    }
}
