package ru.practicum.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import ru.practicum.client.StatsClient;

@SpringBootApplication
@Import({StatsClient.class})
public class ExploreWithMeMain {
    public static void main(String[] args) {
        SpringApplication.run(ExploreWithMeMain.class, args);
    }
}
