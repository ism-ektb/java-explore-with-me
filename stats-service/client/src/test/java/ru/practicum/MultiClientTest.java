package ru.practicum;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

@Slf4j
class MultiClientTest {

    @Test
    @SneakyThrows
    void saveHit() {
        MultiClient multiClient = new MultiClient();
        for (int i = 0; i < 10; i++) {

            EndPointHitDto endPointHitDto = EndPointHitDto.builder()
                    .app("test")
                    .uri("test" + i)
                    .ip("100.100.100.100")
                    .timestamp(LocalDateTime.now()).build();
            multiClient.saveHit(endPointHitDto);
            log.info("Дано задание на сохраниени Hit: {}", i);
        }
        log.info("Все задания запущены");

        long start = System.currentTimeMillis();
        long end = start + 5 * 1000;
        while (System.currentTimeMillis() < end) {
        }

    }
}