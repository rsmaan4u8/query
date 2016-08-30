package com.api.model.specification;

import com.api.model.NoSuchAPIProviderException;
import com.api.model.SearchProvider;
import com.google.common.base.Strings;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class APISearchSpecificationFactory {

    public static final String PROBLEM = "Problem : ";
    public static final String PROBLEM_PLEASE_SPECIFY_SEARCH_API = PROBLEM + "Please specify API to use ";
    public static final String PROBLEM_MUSIC_SEARCH_VALUE_IS_EMPTY = PROBLEM + "Music search value is empty";
    public static final String PROBLEM_SEARCHING_MOVIE_AND_MUSIC_TOGETHER = PROBLEM + "You cannot search both movie and music at same time." +
            "Please provide either music or movie option .\"";
    public static final String PROBLEM_PROVIDE_EITHER_MOVIE_OR_MUSIC = PROBLEM + "Please provide either movie title or music title to search.\"";
    public static final String PROBLEM_MOVIE_SEARCH_VALUE_IS_EMPTY = PROBLEM + "Movie search value is empty";

    public APISearchSpecification create(String movieTitle, String musicTitle, String searchProviders) {
        List<String> problems = new ArrayList<>();
        if (hasNeitherMovieNorMusic(movieTitle, musicTitle)) {
            problems.add(PROBLEM_PROVIDE_EITHER_MOVIE_OR_MUSIC);
        }
        if (isSearchingBothMovieAndMusicTogether(movieTitle, musicTitle)) {
            problems.add(PROBLEM_SEARCHING_MOVIE_AND_MUSIC_TOGETHER);
        }
        validateEmpty(movieTitle, PROBLEM_MOVIE_SEARCH_VALUE_IS_EMPTY, problems);
        validateEmpty(musicTitle, PROBLEM_MUSIC_SEARCH_VALUE_IS_EMPTY, problems);
        return
                new APISearchSpecification(Optional.ofNullable(movieTitle), Optional.ofNullable(musicTitle),
                        createSearchProviders(searchProviders, problems), problems);
    }

    private boolean isSearchingBothMovieAndMusicTogether(String movieTitle, String musicTitle) {
        return movieTitle != null && musicTitle != null;
    }

    private boolean hasNeitherMovieNorMusic(String movieTitle, String musicTitle) {
        return movieTitle == null && musicTitle == null;
    }

    private List<String> validateEmpty(String value, String invalidMessage, List<String> problems) {
        if (value != null && value.trim().isEmpty()) {
            problems.add(invalidMessage);
        }
        return problems;
    }

    private List<SearchProvider> createSearchProviders(String searchProviders, List<String> problems) {
        List<SearchProvider> providers = new ArrayList<>();
        if (Strings.isNullOrEmpty(searchProviders)) {
            problems.add(PROBLEM_PLEASE_SPECIFY_SEARCH_API +
                    Arrays.asList(SearchProvider.values()).stream().map(v -> v.name()).reduce("", (a, b) -> a + b + " "));
            return providers;
        }
        for (String provider : searchProviders.split(" ")) {
            try {
                providers.add(SearchProvider.create(provider));
            } catch (NoSuchAPIProviderException noSuchAPIProviderException) {
                problems.add(PROBLEM + provider + " not supported " + noSuchAPIProviderException.getMessage());
            }
        }
        return providers;
    }
}
