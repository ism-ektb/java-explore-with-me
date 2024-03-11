package ru.practicum;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@Slf4j

public class PostClient implements Runnable {
    private final String server;
    private RestTemplate restTemplate;
    private EndPointHitDto endPointHitDto;


    public PostClient(RestTemplate restTemplate, EndPointHitDto endPointHitDto, String server) {
        this.restTemplate = restTemplate;
        this.endPointHitDto = endPointHitDto;
        this.server = server;
    }

    @Override
    public void run() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<EndPointHitDto> requestEntity = new HttpEntity<>(endPointHitDto, headers);
        String uri = server + "/hit";
        try {
            restTemplate.exchange(uri, HttpMethod.POST,
                    requestEntity, EndPointHitDto.class);
            log.info("Успех! Hit {} сохранен в статистике", endPointHitDto.toString());
        } catch (Exception e) {
            log.warn("Ошибка сохранения данных с статистику: {}", e.getMessage());
        }
    }

}
