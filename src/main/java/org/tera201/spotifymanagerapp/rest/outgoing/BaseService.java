package org.tera201.spotifymanagerapp.rest.outgoing;

import org.tera201.spotifymanagerapp.config.AppProperties;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Log4j2
public abstract class BaseService {
    @Autowired
    protected RestTemplate restTemplate;
    @Autowired
    protected AppProperties appProperties;

    protected <T> ResponseEntity<T> getRequestBasic(String url, Object model, Class<T> responseType) {
        log.info(String.format("Request %s <GET>", url));

        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.add("Authorization", "Basic " + credentials());
        log.info(String.format("Headers: %s", headers.toString()));
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET,
                new HttpEntity<>(model, headers), responseType);
        log.info(response);
        return response;
    }

    protected <T> ResponseEntity<T> getRequestBearer(String url, Object model, Class<T> responseType, String accessToken) {
        log.info(String.format("Request %s <GET>", url));

        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        log.info(String.format("Headers: %s", headers.toString()));
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET,
                new HttpEntity<>(model, headers), responseType);
        log.info(response);
        return response;
    }

    protected <T> ResponseEntity<T> postRequestBasic(String url, Object model, Class<T> responseType) {
        log.info(String.format("Request %s <GET>", url));

        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.add("Authorization", "Basic " + credentials());
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        log.info(String.format("Headers: %s", headers.toString()));
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.POST,
                new HttpEntity<>(model, headers), responseType);
        log.info(response);
        return response;
    }

    private String credentials() {
        return Base64.encodeBase64String((appProperties.getClient() + ":" + appProperties.getSecret()).getBytes());
    }
}
