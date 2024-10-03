package org.tera201.spotifymanagerapp.rest.outgoing;

import org.springframework.http.*;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.tera201.spotifymanagerapp.config.SpotifyConfig;
import org.tera201.spotifymanagerapp.rest.DataStorage;

@Log4j2
public abstract class BaseService {
    @Autowired
    protected RestTemplate restTemplate;
    @Autowired
    protected SpotifyConfig spotifyConfig;
    @Autowired
    protected DataStorage dataStorage;

    protected <T> ResponseEntity<T> getRequestBasic(String url, Object body, Class<T> responseType) {
        log.info("Request {} <GET>", url);

       HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + credentials());
        log.debug("Headers: {}", headers);
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET,
                new HttpEntity<>(body, headers), responseType);
        log.debug(response);
        return response;
    }

    protected <T> ResponseEntity<T> getRequestBearer(String url, Object body, Class<T> responseType) {
        log.info(String.format("Request %s <GET>", url));

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(dataStorage.getAccessToken());
        log.debug("Headers: {}", headers);
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET,
                new HttpEntity<>(body, headers), responseType);
        log.debug(response);
        return response;
    }

    protected <T> ResponseEntity<T> postRequestBasic(String url, Object body, Class<T> responseType, String contentType) {
        log.info(String.format("Request %s <POST>", url));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + credentials());
        headers.add("Content-Type", contentType);
        log.debug("Headers: {}", headers);
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.POST,
                new HttpEntity<>(body, headers), responseType);
        log.debug(response);
        return response;
    }

    protected <T> ResponseEntity<T> postRequestBearer(String url, Object body, Class<T> responseType, MediaType contentType) {
        log.info(String.format("Request %s <POST>", url));

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(dataStorage.getAccessToken());
        headers.setContentType(contentType);
        log.debug("Headers: {}", headers);
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.POST,
                new HttpEntity<>(body, headers), responseType);
        log.debug(response);
        return response;
    }

    private String credentials() {
        return Base64.encodeBase64String((spotifyConfig.getClient() + ":" + spotifyConfig.getSecret()).getBytes());
    }
}
