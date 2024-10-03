package org.tera201.spotifymanagerapp.rest.model;

import lombok.Data;

import java.util.List;

@Data
public class GetPlaylistsResponseModel {
    private String href;
    private int limit;
    private int offset;
    private String next;
    private String previous;
    private int total;
    private List<PlaylistResponseModel> items;
}
