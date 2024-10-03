package org.tera201.spotifymanagerapp.rest.incoming;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tera201.spotifymanagerapp.rest.DataStorage;
import org.tera201.spotifymanagerapp.rest.SpotifyManagerService;

/**
 * Controller that receives requests from spotify and sends data to {@link SpotifyManagerService}
 */
@RestController
@RequestMapping("/")
@AllArgsConstructor
public class SpotifyAuthController {

    private final SpotifyManagerService spotifyManagerService;
    @Autowired
    private DataStorage dataStorage;

    @GetMapping
    public void getSpotifyCallback(@RequestParam(name = "code") String code,
                                   @RequestParam(name = "state") String state) {
        dataStorage.setCode(code);
        dataStorage.setState(state);
        spotifyManagerService.processUserCode();
    }
}
