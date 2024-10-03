package org.tera201.spotifymanagerapp.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddTracksRequest {
    private List<String> uris;
    private int position;
}
