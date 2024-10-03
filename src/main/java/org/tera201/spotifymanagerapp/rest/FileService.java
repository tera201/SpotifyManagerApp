package org.tera201.spotifymanagerapp.rest;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
public class FileService {

    public Map<String, List<String>> processAllFilesInPlaylists() {
        Map<String, List<String>> fileContentsMap = new HashMap<>();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            Resource[] resources = resolver.getResources("classpath:playlists/*");

            for (Resource resource : resources) {
                String filename = resource.getFilename();
                String playlist = filename.substring(filename.lastIndexOf('_') + 1, filename.indexOf(".txt"));
                log.info("Processing file: {} playlist: {}", filename, playlist);

                List<String> fileLines = new ArrayList<>();
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(resource.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        fileLines.add(line.substring(line.indexOf(" ") + 1));
                    }
                }
                fileContentsMap.put(playlist, fileLines);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileContentsMap;
    }
}
