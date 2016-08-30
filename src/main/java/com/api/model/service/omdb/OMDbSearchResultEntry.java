package com.api.model.service.omdb;

import com.api.model.SearchItem;
import com.api.model.SearchProvider;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OMDbSearchResultEntry implements SearchItem {
    private String title;
    private String year;
    private String imdbID;
    private String director;

    public OMDbSearchResultEntry() {
    }

    @Override
    @JsonProperty(value = "Title")
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    @JsonProperty(value = "Year")
    public String getYear() {
        return this.year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    @JsonProperty(value = "Director")
    public String getDirector() {
        return this.director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    @JsonProperty(value = "imdbID")
    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    @Override
    public String getProvider() {
        return SearchProvider.OMDB.name();
    }
}
