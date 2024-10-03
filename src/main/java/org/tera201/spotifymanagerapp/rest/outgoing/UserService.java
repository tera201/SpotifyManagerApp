package org.tera201.spotifymanagerapp.rest.outgoing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.tera201.spotifymanagerapp.config.AppProperties;
import org.tera201.spotifymanagerapp.rest.DataStorage;
import org.tera201.spotifymanagerapp.rest.model.AccessTokenModel;

@Service
public class UserService extends BaseService {
    private static final String TOKEN_URL = "https://accounts.spotify.com/api/token";
    private static final String USERID_URL =  "https://api.spotify.com/v1/me";

    @Autowired
    protected AppProperties appProperties;
    @Autowired
    private DataStorage dataStorage;

    public void getAccessToken() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", dataStorage.getCode());
        params.add("redirect_uri", appProperties.getRedirectUrl());
        params.add("grant_type", "authorization_code");

        ResponseEntity<AccessTokenModel> response = postRequestBasic(TOKEN_URL, params, AccessTokenModel.class);
        dataStorage.setAccessToken(response.getBody().getAccessToken());
        getUserId();
    }

    public void getUserId() {
        ResponseEntity<String> response = getRequestBearer(USERID_URL, null, String.class, dataStorage.getAccessToken());
        System.out.println(response.getBody());
    }
}
