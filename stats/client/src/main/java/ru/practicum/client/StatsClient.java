package ru.practicum.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.Constant;
import ru.practicum.dto.EndPointHit;
import ru.practicum.dto.ViewStat;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatsClient extends BaseClient {
    private final String date1 = LocalDateTime.of(1900, 1, 1, 0, 0).format(Constant.FORMATTER);
    private final String date2 = LocalDateTime.of(2100, 1, 1, 0, 0).format(Constant.FORMATTER);

    @Autowired
    public StatsClient(@Value("${stats-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build(),
                serverUrl
        );
    }

    public void create(EndPointHit hit) {
        post("/hit", hit);
    }

    public List<ViewStat> getAllForClient(List<String> uris) {
        Map<String, Object> parameters = Map.of("uris", uris == null ? "" : uris.stream().map(String::valueOf).collect(Collectors.joining(",")));
        ResponseEntity<String> response = rest.getForEntity(serverUrl + "/stats?start=" + date1 + "&end=" + date2 + "&uris={uris}", String.class, parameters);
        if (response.getBody().equals("[]")) return Collections.emptyList();
        try {
            return List.of(new ObjectMapper().readValue(response.getBody(), ViewStat[].class));
        } catch (JsonProcessingException exception) {
            throw new RuntimeException(String.format("Ошибка обработки запроса. %s", exception.getMessage()));
        }
    }

    public List<ViewStat> get(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        Map<String, Object> parameters = Map.of("uris", uris == null ? "" : uris.stream().map(String::valueOf).collect(Collectors.joining(",")));
        String startFormatter = start.format(Constant.FORMATTER);
        String endFormatter = end.format(Constant.FORMATTER);
        ResponseEntity<String> response = rest.getForEntity(serverUrl + "/stats?start=" + startFormatter +
                "&end=" + endFormatter + "&uris=" + uris + "&unique=" + unique, String.class, parameters);
        if (response.getBody().equals("[]")) return Collections.emptyList();
        try {
            return List.of(new ObjectMapper().readValue(response.getBody(), ViewStat[].class));
        } catch (JsonProcessingException exception) {
            throw new RuntimeException(String.format("Ошибка обработки запроса. %s", exception.getMessage()));
        }
    }
}
