package org.tera201.spotifymanagerapp.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistCreateRequest {
    private String name;
    private String description;
    @JsonProperty("public")
    private Boolean isPublic;
}
