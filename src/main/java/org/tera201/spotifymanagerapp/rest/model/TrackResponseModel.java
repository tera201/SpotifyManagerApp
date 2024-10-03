package org.tera201.spotifymanagerapp.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TrackResponseModel {
    private String href;
    private String id;
    private String name;
    private List<Artist> artists;
    private int popularity;
    @JsonProperty("preview_url")
    private String previewUrl;
    private String type;
    private String uri;

    @Data
    public static class Artist {
        private String href;
        private String id;
        private String name;
        private String type;
        private String uri;
    }
}
