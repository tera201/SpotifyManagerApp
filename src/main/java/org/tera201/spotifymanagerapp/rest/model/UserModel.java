package org.tera201.spotifymanagerapp.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserModel {
    @JsonProperty("display_name")
    private String displayName;
    private String href;
    private String id;
    private String uri;
    private String country;
    private String product;
    private String email;
}
