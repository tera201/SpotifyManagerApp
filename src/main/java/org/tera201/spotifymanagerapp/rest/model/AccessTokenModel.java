package org.tera201.spotifymanagerapp.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AccessTokenModel {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("expires_in")
    private String expiresIn;
    @JsonProperty("refresh_token")
    private String refreshToken;
    private String scope;
}
