package com.api.model.specification;

import com.api.model.SearchProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class APISearchSpecification {
    private Optional<String> movieTitle;
    private Optional<String> musicTitle;
    private List<SearchProvider> searchProviders;
    private List<String> problems = new ArrayList<>();

    public APISearchSpecification(Optional<String> movieTitle, Optional<String> musicTitle, List<SearchProvider> searchProviders, List<String> problems) {
        this.movieTitle = movieTitle;
        this.musicTitle = musicTitle;
        this.searchProviders = searchProviders;
        this.problems = problems;
    }

    public boolean hasProblems() {
        return problems.size() > 0;
    }

    public Optional<String> getMovieTitle() {
        return movieTitle;
    }


    public Optional<String> getMusicTitle() {
        return musicTitle;
    }

    public List<SearchProvider> getSearchProviders() {
        return searchProviders;
    }

    public List<String> getProblems() {
        return problems;
    }


}
