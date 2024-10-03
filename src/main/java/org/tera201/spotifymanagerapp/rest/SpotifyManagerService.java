package org.tera201.spotifymanagerapp.rest;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.tera201.spotifymanagerapp.rest.outgoing.UserService;

/**
 * Service for redirecting request, checking params and formatting response
 */
@Log4j2
@Service
@AllArgsConstructor
public class SpotifyManagerService {

    private final UserService userService;

    public void processUserCode() {
        userService.getAccessToken();
    }
}
