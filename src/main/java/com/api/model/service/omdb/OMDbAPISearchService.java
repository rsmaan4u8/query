package com.api.model.service.omdb;

import com.api.model.SearchProvider;
import com.api.model.SearchResult;
import com.api.model.service.AbstractAPISearchService;
import com.api.model.specification.APISearchSpecification;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OMDbAPISearchService extends AbstractAPISearchService {

    public static final String OMDBAPI_BASE_URL = "http://www.omdbapi.com";

    @Override
    public SearchResult search(APISearchSpecification specification) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(OMDBAPI_BASE_URL);
        specification.getMovieTitle().ifPresent(s -> uriComponentsBuilder.queryParam("s", s));
        specification.getMusicTitle().ifPresent(s -> uriComponentsBuilder.queryParam("s", s));
        uriComponentsBuilder.queryParam("r", "json");
        OMDbSearchResult searchResult = (OMDbSearchResult) get(uriComponentsBuilder.build().toUriString(), OMDbSearchResult.class);

        if (searchResult.hasResult()) {
            List<OMDbSearchResultEntry> decoratedSearchItems = searchResult.getResults().parallelStream()
                    .map(sR -> {
                        OMDbSearchResultEntry omDbSearchResultEntry = (OMDbSearchResultEntry) sR;
                        String imdbID = omDbSearchResultEntry.getImdbID();
                        omDbSearchResultEntry.setDirector(getRestTemplate()
                                .getForObject(OMDBAPI_BASE_URL + "?i=" + imdbID, OMDbSearchResultEntry.class)
                                .getDirector());
                        return omDbSearchResultEntry;
                    }).collect(Collectors.toList());
            searchResult.setSearch(decoratedSearchItems);
            return searchResult;
        } else {
            return searchResult;
        }
    }

    @Override
    public SearchProvider getSearchProvider() {
        return SearchProvider.OMDB;
    }


}
