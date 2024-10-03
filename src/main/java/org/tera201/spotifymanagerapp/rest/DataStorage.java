package org.tera201.spotifymanagerapp.rest;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class DataStorage {
    private String code;
    private String state;
    private String accessToken;
    private String userId;
}
