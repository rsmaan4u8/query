package com.api.config;

import org.springframework.stereotype.Component;

@Component
public class PropertiesProvider {
    public String getAPIProviders() {
        return getProperty("api");
    }

    public String getMovieTitle() {
        return getProperty("movie");
    }

    public String getMusicTitle() {
        return getProperty("music");
    }

    private String getProperty(String property) {
        return System.getProperty(property);
    }

}
