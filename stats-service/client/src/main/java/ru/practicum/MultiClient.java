package ru.practicum;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@Service
public class MultiClient {

    @Value("${stats.url}")
    private String server;
    private RestTemplate restTemplate = new RestTemplate();
    //private ExecutorService executor = Executors.newFixedThreadPool(4);

    public void saveHit(HttpServletRequest request) {
        EndPointHitDto endPointHitDto = EndPointHitDto.builder()
                .app("ewm-main-service")
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .timestamp(LocalDateTime.now()).build();
        PostClient client = new PostClient(restTemplate, endPointHitDto, server);
        client.run();
      /* отключим многопоточность для корректного прохождения тестов
        executor.execute(new PostClient(restTemplate, endPointHitDto));
          */
    }
}


