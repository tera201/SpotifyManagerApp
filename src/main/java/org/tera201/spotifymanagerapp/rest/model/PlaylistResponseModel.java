package org.tera201.spotifymanagerapp.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PlaylistResponseModel {
    private Boolean collaborative;
    private String description;
    private String href;
    private String id;
    private String name;
    @JsonProperty("public")
    private Boolean isPublic;
    private String type;
    private String uri;
}
