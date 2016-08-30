package com.api.model.service.itunes;

import com.api.model.SearchItem;
import com.api.model.SearchProvider;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ITunesSearchResultEntry implements SearchItem {
    private String trackName;
    private String releaseDate;
    private String artistName;

    public ITunesSearchResultEntry() {
    }

    @Override
    public String getTitle() {
        return this.trackName;
    }

    @Override
    public String getYear() {
        try {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(this.releaseDate).toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear() + "";
        } catch (ParseException e) {
            return "Unknown";
        }
    }

    @Override
    public String getDirector() {
        return this.artistName;
    }

    @Override
    public String getProvider() {
        return SearchProvider.ITUNES.name();
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

}
