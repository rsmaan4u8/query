package com.api.model;

import java.util.List;

/**
 * Contract between controller and service layer. All the services should return
 * implementation of this interface.
 */
public interface SearchResult {
    List<? extends SearchItem> getResults();

    boolean hasResult();
}
