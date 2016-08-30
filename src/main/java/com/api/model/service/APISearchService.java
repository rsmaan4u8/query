package com.api.model.service;

import com.api.model.SearchProvider;
import com.api.model.SearchResult;
import com.api.model.specification.APISearchSpecification;

/**
 * For each public API search implementation this interface should be implemented.
 * Search should be performed by passing search specification.
 */
public interface APISearchService {
    SearchResult search(APISearchSpecification specification);

    SearchProvider getSearchProvider();
}
