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

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Log4j2
@Service
@AllArgsConstructor
public class SpotifyManagerService {

    private final ConfigurableApplicationContext context;
    private final UserService userService;
    private final PlaylistService playlistService;
    private final SearchService searchService;

    public void processUserCode() {
        FileService fileService = new FileService();
        Map<String, List<String>> files = fileService.processAllFilesInPlaylists();
        userService.getAccessToken();
        int nThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(nThreads);
        for (Map.Entry<String, List<String>> entry : files.entrySet()) {
            executor.submit(() -> {
                String playlistId = createPlaylistIfNotExist(entry.getKey());
                List<CompletableFuture<String>> futures = entry.getValue().stream()
                        .map(value -> CompletableFuture.supplyAsync(() -> processLine(value, entry.getKey())))
                        .toList();
                List<String> tracksUri = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                        .thenApply(_ -> futures.stream()
                                .map(CompletableFuture::join) // join для получения результата
                                .collect(Collectors.toList()))
                        .join();
                addNewTracks(tracksUri, playlistId);
            });
        }
        executor.shutdown();
        context.close();
    }

    private String processLine(String value, String playlistName) {
        String[] parts = value.split(" - ");
        if (parts.length >= 2) {
            String track = parts[0];
            String artist = parts[1];
            return findTrackUri(track, artist);
        } else {
            System.err.println("Ошибка при разбиении строки: " + playlistName);
            return null;
        }
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

    public String findTrackUri(String track, String author) {
        SearchResponseModel search = searchService.search(track, author);
        Optional<TrackResponseModel> trackObj = search.getTracks().getItems().stream().findFirst();
        if (trackObj.isPresent()) {
            return trackObj.get().getUri();
        }
        return "";
    }

    public void addNewTracks(List<String> tracksUri, String playlistId) {
        List<String> playlistTracksUri = playlistService.getPlaylistItems(playlistId).getItems().stream().map(it -> it.getTrack().getUri()).toList();
        tracksUri.removeAll(playlistTracksUri);
        if (!tracksUri.isEmpty()) {
            partitionList(tracksUri, 50).reversed().forEach(it ->
                    playlistService.addItemsToPlaylist(playlistId, it));
        }
    }

    public static List<List<String>> partitionList(List<String> list, int size) {
        List<List<String>> partitioned = new ArrayList<>();
        for (int i = 0; i < list.size(); i += size) {
            partitioned.add(list.subList(i, Math.min(i + size, list.size())));
        }
        return partitioned;
    }
}
