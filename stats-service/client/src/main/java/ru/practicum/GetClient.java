package ru.practicum;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class GetClient {

    private RestTemplate restTemplate = new RestTemplate();

    public List<ViewStatsDto> readStat(List<String> uris) {
        StringBuilder str = new StringBuilder();
        str.append("http://localhost:9090/stats?uris=");
        for (String uri : uris) {
            str.append(uri + ",");
        }
        str.deleteCharAt(str.length() - 1);
        str.append("&unique=true&start=2000-05-05 00:00:00");
        try {
            ViewStatsDto[] response = restTemplate.getForObject(str.toString(), ViewStatsDto[].class);
            return List.of(response);
        } catch (Exception e) {
            return new ArrayList<>();
        }

    }
}
