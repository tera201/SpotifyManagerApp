package org.tera201.spotifymanagerapp.rest;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.tera201.spotifymanagerapp.rest.model.GetPlaylistsResponseModel;
import org.tera201.spotifymanagerapp.rest.model.PlaylistResponseModel;
import org.tera201.spotifymanagerapp.rest.outgoing.PlaylistService;
import org.tera201.spotifymanagerapp.rest.outgoing.UserService;

import java.util.Optional;

/**
 * Service for redirecting request, checking params and formatting response
 */
@Log4j2
@Service
@AllArgsConstructor
public class SpotifyManagerService {

    private final UserService userService;
    private final PlaylistService playlistService;

    public void processUserCode() {
        userService.getAccessToken();
        createPlaylistIfNotExist("TEST2");
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
}
