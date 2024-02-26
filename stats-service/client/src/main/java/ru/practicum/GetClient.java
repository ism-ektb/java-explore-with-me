package ru.practicum;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class GetClient implements Runnable {
    private RestTemplate restTemplate;
    private EndPointHitDto endPointHitDto;


    public GetClient(RestTemplate restTemplate, EndPointHitDto endPointHitDto) {
        this.restTemplate = restTemplate;
        this.endPointHitDto = endPointHitDto;
    }

    @Override
    public void run() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<EndPointHitDto> requestEntity = new HttpEntity<>(endPointHitDto, headers);
        try {
            restTemplate.exchange("http://localhost:9090/hit", HttpMethod.POST,
                    requestEntity, EndPointHitDto.class);
            log.info("Успех! Hit {} сохранен в статистике", endPointHitDto.toString());
        } catch (Exception e) {
            log.warn("Ошибка сохранения данных с статистику: {}", e.getMessage());
        }
    }

}
