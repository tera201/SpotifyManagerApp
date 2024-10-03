package org.tera201.spotifymanagerapp.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TracksPlaylistResponseModel {
    private String href;
    private int limit;
    private int offset;
    private int total;
    private String next;
    private String previous;
    private List<Item> items;

    @Data
    public static class Item {
        @JsonProperty("added_at")
        private String addedAt;
        @JsonProperty("is_local")
        private Boolean isLocal;
        private TrackResponseModel track;
    }
}
