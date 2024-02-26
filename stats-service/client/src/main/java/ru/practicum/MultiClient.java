package ru.practicum;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class MultiClient {

    private RestTemplate restTemplate = new RestTemplate();
    private ExecutorService executor =
            Executors.newFixedThreadPool(4);

    public void saveHit(EndPointHitDto endPointHitDto) {
        executor.execute(new GetClient(restTemplate, endPointHitDto));
    }
}


