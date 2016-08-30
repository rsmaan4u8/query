package com.api.model.service;

import com.api.model.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public abstract class AbstractAPISearchService implements APISearchService {

    @Autowired
    private RestTemplate restTemplate;

    public SearchResult get(String url, Class<? extends SearchResult> responseType) {
        SearchResult searchResult = restTemplate.getForObject(url, responseType);
        return searchResult;
    }


    protected RestTemplate getRestTemplate() {
        return restTemplate;
    }

}
