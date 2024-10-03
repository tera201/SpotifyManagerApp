package org.tera201.spotifymanagerapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.tera201.spotifymanagerapp.config.AppProperties;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;

@Component
class DesktopOpener {

    @Autowired
    protected AppProperties appProperties;

    private static final String SCOPE = "user-read-private user-read-email";

    @EventListener(ApplicationReadyEvent.class)
    public void init() throws URISyntaxException, IOException {
        System.setProperty("java.awt.headless", "false");
        String spotifyAuthUrl = buildSpotifyAuthUrl();
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(new URI(spotifyAuthUrl));
        }
    }

    private static String generateRandomString(int length) {
        String possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder text = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            text.append(possible.charAt(random.nextInt(possible.length())));
        }
        return text.toString();
    }

    private String buildSpotifyAuthUrl() throws URISyntaxException {
        String state = generateRandomString(16);
        URI uri = new URI("https", "accounts.spotify.com", "/authorize",
                "response_type=code" +
                        "&client_id=" + appProperties.getClient() +
                        "&scope=" + SCOPE +
                        "&redirect_uri=" + appProperties.getRedirectUrl() +
                        "&state=" + state, null);
        return uri.toString();
    }
}
