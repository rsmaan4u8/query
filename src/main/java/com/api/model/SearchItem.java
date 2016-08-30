package com.api.model;

/**
 * This interface ensure that each item in the search result provides
 * Title, Year, Director and API provider name.
 */
public interface SearchItem {
    public String getTitle();

    public String getYear();

    public String getDirector();

    public String getProvider();
}
