package com.api.controller;

import com.api.model.SearchResult;
import com.api.model.service.APISearchService;
import com.api.model.specification.APISearchSpecification;
import com.api.model.specification.APISearchSpecificationFactory;
import com.api.view.ConsoleBasedProblemsView;
import com.api.view.ConsoleBasedResultsView;
import com.api.view.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Stream;

@Controller
public class APISearchController {

    @Autowired
    private List<APISearchService> searchServices;

    @Autowired
    private APISearchSpecificationFactory factory;

    public View performSearch(String movieTitle, String musicTitle, String searchProviders) {
        APISearchSpecification specification = factory.create(movieTitle, musicTitle, searchProviders);
        if (specification.hasProblems()) {
            return new ConsoleBasedProblemsView(specification.getProblems());
        }
        Stream<SearchResult> searchResultStream =
                searchServices.parallelStream()
                        .filter(service -> specification.getSearchProviders().contains(service.getSearchProvider()))
                        .map(service -> service.search(specification));
        return new ConsoleBasedResultsView(searchResultStream);
    }
}
