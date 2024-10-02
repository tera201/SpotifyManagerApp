package org.tera201.spotifymanagerapp.rest;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/**
 * Service for redirecting request, checking params and formatting response
 */
@Log4j2
@Service
@AllArgsConstructor
public class SpotifyManagerService {

    public void processUserCode(String code, String state) {
        System.out.println("Code: " + code + " State: " + state);
    }
}
