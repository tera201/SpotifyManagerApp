package org.tera201.spotifymanagerapp.rest.model;

import lombok.Data;

import java.util.List;

@Data
public class SearchResponseModel {

    private Tracks tracks;

    @Data
    public static class Tracks {
        private String href;
        private int limit;
        private int offset;
        private int total;
        private String next;
        private String previous;
        private List<TrackResponseModel> items;

    }
}