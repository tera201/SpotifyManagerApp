package org.tera201.spotifymanagerapp.rest;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;
import org.tera201.spotifymanagerapp.rest.model.GetPlaylistsResponseModel;
import org.tera201.spotifymanagerapp.rest.model.PlaylistResponseModel;
import org.tera201.spotifymanagerapp.rest.model.SearchResponseModel;
import org.tera201.spotifymanagerapp.rest.model.TrackResponseModel;
import org.tera201.spotifymanagerapp.rest.outgoing.PlaylistService;
import org.tera201.spotifymanagerapp.rest.outgoing.SearchService;
import org.tera201.spotifymanagerapp.rest.outgoing.UserService;

import java.util.Optional;

/**
 * Service for redirecting request, checking params and formatting response
 */
@Log4j2
@Service
@AllArgsConstructor
public class SpotifyManagerService {

    private final ConfigurableApplicationContext context;
    private final UserService userService;
    private final PlaylistService playlistService;
    private final SearchService searchService;

    public void processUserCode() {
        userService.getAccessToken();
        String playlistId = createPlaylistIfNotExist("TEST2");
        context.close();
    }

    public String createPlaylistIfNotExist(String playlistName) {
        GetPlaylistsResponseModel responseModel = playlistService.getUsersPlaylists();
        String playlistId;
        Optional<PlaylistResponseModel> playlist = responseModel.getItems().stream().filter(it -> it.getName().equals(playlistName)).findFirst();
        if (playlist.isPresent()) {
            playlistId = playlist.get().getId();
        } else  {
            playlistId = playlistService.createPlaylist(playlistName, "", false).getId();
        }
        return playlistId;
    }

    public String findTrackId(String track, String author) {
        SearchResponseModel search = searchService.search(track, author);
        Optional<TrackResponseModel> trackObj = search.getTracks().getItems().stream().findFirst();
        if (trackObj.isPresent()) {
            return trackObj.get().getId();
        }
        return "";
    }
}
