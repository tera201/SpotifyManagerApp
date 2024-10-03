package org.tera201.spotifymanagerapp.rest.outgoing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.tera201.spotifymanagerapp.rest.DataStorage;
import org.tera201.spotifymanagerapp.rest.model.*;

import java.util.List;


@Service
public class PlaylistService extends BaseService {
    @Autowired
    private DataStorage dataStorage;


    private static final String URL = "https://api.spotify.com/v1/users/%s/playlists";
    private static final String MY_PLAYLISTS_URL = "https://api.spotify.com/v1/me/playlists";
    private static final String PLAYLIST_URL = "https://api.spotify.com/v1/playlists/%s";
    private static final String TRACK_URL = PLAYLIST_URL + "/tracks";

    public void getPlaylist() {}

    public GetPlaylistsResponseModel getUsersPlaylists() {
        return getRequestBearer(URL.formatted(dataStorage.getUserId()), null, GetPlaylistsResponseModel.class).getBody();
    }

    public void getCurrentUserPlaylists() {
        getRequestBearer(MY_PLAYLISTS_URL, null, String.class);
    }

    public void addItemsToPlaylist(String playlistId, List<String> items) {
        AddTracksRequest tracksRequest = new AddTracksRequest(items, 0);
        postRequestBearer(TRACK_URL.formatted(playlistId), tracksRequest, String.class, MediaType.APPLICATION_JSON);
    }

    public TracksPlaylistResponseModel getPlaylistItems(String playlistId) {
        return getRequestBearer(TRACK_URL.formatted(playlistId), null, TracksPlaylistResponseModel.class).getBody();
    }

    public PlaylistResponseModel createPlaylist(String playlistName, String description, Boolean isPublic) {
        PlaylistCreateRequest playlist = new PlaylistCreateRequest(playlistName, description, isPublic);
        return postRequestBearer(URL.formatted(dataStorage.getUserId()), playlist, PlaylistResponseModel.class, MediaType.APPLICATION_JSON).getBody();
    }
}
