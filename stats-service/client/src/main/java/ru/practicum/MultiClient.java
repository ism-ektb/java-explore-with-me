package ru.practicum;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class MultiClient {

    private RestTemplate restTemplate = new RestTemplate();

    public void saveHit(EndPointHitDto endPointHitDto) {
        Thread thread = new Thread(new Task(restTemplate, endPointHitDto));
        thread.start();
        thread.interrupt();
    }
}


