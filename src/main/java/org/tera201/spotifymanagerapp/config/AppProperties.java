package org.tera201.spotifymanagerapp.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Class with creds and host url
 */
@Component
@Data
public final class AppProperties {

    @Value("${spotify.client}")
    private String client;

    @Value("${spotify.secret}")
    private String secret;

    @Value("${server.port}")
    private String port;

    @Value("${spotify.redirectUrl}")
    private String redirectUrl;
}