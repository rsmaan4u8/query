package com.api.model;

public enum SearchProvider {

    ITUNES,
    OMDB;

    public static final String SEARCH_IN_ITUNES_OR_OMDB = "You can only search in itunes or omdb";

    public static SearchProvider create(String name) throws NoSuchAPIProviderException {
        for (SearchProvider value : SearchProvider.values()) {
            if (value.name().equalsIgnoreCase(name))
                return value;
        }
        throw new NoSuchAPIProviderException(SEARCH_IN_ITUNES_OR_OMDB);
    }
}
