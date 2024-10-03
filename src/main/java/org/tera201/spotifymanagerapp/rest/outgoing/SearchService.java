package org.tera201.spotifymanagerapp.rest.outgoing;

import org.springframework.stereotype.Service;
import org.tera201.spotifymanagerapp.rest.model.SearchResponseModel;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class SearchService extends BaseService {

    private static final String URL = "https://api.spotify.com/v1/search?q=%S&type=track";

    public SearchResponseModel search(String track, String author) {
        String query = encodeQuery("%s %s".formatted(track, author));
       return getRequestBearer(URL.formatted(query), null, SearchResponseModel.class).getBody();
    }

    public String encodeQuery(String query) {
        StringBuilder encoded = new StringBuilder();
        for (char ch : query.toCharArray()) {
            if (isSafeCharacter(ch)) {
                encoded.append(ch);
            } else {
                encoded.append(URLEncoder.encode(Character.toString(ch), StandardCharsets.UTF_8));
            }
        }
        return encoded.toString();
    }

    private static boolean isSafeCharacter(char ch) {
        return Character.isLetterOrDigit(ch);
    }
}
