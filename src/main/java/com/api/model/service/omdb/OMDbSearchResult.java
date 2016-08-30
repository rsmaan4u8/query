package com.api.model.service.omdb;

import com.api.model.SearchItem;
import com.api.model.SearchResult;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OMDbSearchResult implements SearchResult {
    private List<OMDbSearchResultEntry> search;
    private int totalResults;

    public List<OMDbSearchResultEntry> getSearch() {
        return search;
    }

    @JsonProperty(value = "Search")
    public void setSearch(List<OMDbSearchResultEntry> search) {
        this.search = search;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    @Override
    public List<? extends SearchItem> getResults() {
        return this.search;
    }

    @Override
    public boolean hasResult() {
        return totalResults > 0;
    }
}
