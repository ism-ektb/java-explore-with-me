package ru.practicum;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class GetClient {

    private RestTemplate restTemplate = new RestTemplate();
    @Value("${stats.url}")
    private String server;

    public List<ViewStatsDto> readStat(List<String> uris) {
        StringBuilder str = new StringBuilder();
        str.append(server);
        str.append("/stats?uris=");
        for (String uri : uris) {
            str.append(uri + ",");
        }
        str.deleteCharAt(str.length() - 1);
        str.append("&unique=true&start=2000-05-05 00:00:00");
        try {
            ViewStatsDto[] response = restTemplate.getForObject(str.toString(), ViewStatsDto[].class);
            return List.of(response);
        } catch (Exception e) {
            log.warn(e.getMessage());
            return new ArrayList<>();
        }

    }
}
