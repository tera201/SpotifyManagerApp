package org.tera201.spotifymanagerapp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@ConfigurationProperties(prefix = "spotify")
public class SpotifyConfig {
    private String client;
    private String secret;
    private String redirectUrl;
    private List<String> scope;

    public String getScopeAsString() {
        return String.join(" ", scope);
    }

}
