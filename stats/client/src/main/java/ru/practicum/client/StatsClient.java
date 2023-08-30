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
import ru.practicum.dto.EndPointHit;
import ru.practicum.dto.ViewStat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatsClient extends BaseClient {

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

    public List<ViewStat> get(List<String> uris) {
        Map<String, Object> parameters = Map.of("uris", uris == null ? "" : uris.stream().map(String::valueOf).collect(Collectors.joining(",")));
        ResponseEntity<String> response = rest.getForEntity(serverUrl + "/client/stats?uris={uris}", String.class, parameters);
        if (response.getBody().equals("[]")) return new ArrayList<>();
        try {
            return List.of(new ObjectMapper().readValue(response.getBody(), ViewStat[].class));
        } catch (JsonProcessingException exception) {
            throw new RuntimeException(String.format("Ошибка обработки запроса. %s", exception.getMessage()));
        }
    }
}
