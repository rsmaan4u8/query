package com.api.model.service.itunes;

import com.api.model.SearchProvider;
import com.api.model.SearchResult;
import com.api.model.service.AbstractAPISearchService;
import com.api.model.specification.APISearchSpecification;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ITunesAPISearchService extends AbstractAPISearchService {

    public static final String ITUNES_BASE_URL = "https://itunes.apple.com/search";

    @Override
    public SearchResult search(APISearchSpecification specification) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(ITUNES_BASE_URL);

        specification.getMovieTitle().ifPresent(s -> uriComponentsBuilder.queryParam("media", "movie"));
        specification.getMovieTitle().ifPresent(s -> uriComponentsBuilder.queryParam("term", s));

        specification.getMusicTitle().ifPresent(s -> uriComponentsBuilder.queryParam("media", "music"));
        specification.getMusicTitle().ifPresent(s -> uriComponentsBuilder.queryParam("term", s));

        return get(uriComponentsBuilder.build().toUriString(), ITunesSearchResult.class);
    }

    public SearchProvider getSearchProvider() {
        return SearchProvider.ITUNES;
    }
}
