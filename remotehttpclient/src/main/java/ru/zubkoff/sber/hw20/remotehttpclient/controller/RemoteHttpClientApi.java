package ru.zubkoff.sber.hw20.remotehttpclient.controller;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class RemoteHttpClientApi {
  private final Map<String, Response> cache;

  public RemoteHttpClientApi() {
    this.cache = new HashMap<>();
  }

  @GetMapping("/api/get/{url}")
  public ResponseEntity<?> get(@PathVariable String url) {
    WebClient client = WebClient.builder().build();
    url = "https://" + url;
    var response = cache.computeIfAbsent(url, clientTargetUrl -> client.get()
        .uri(clientTargetUrl)
        .exchangeToMono(clientResponse -> {
          if (!clientResponse.statusCode().is2xxSuccessful()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Response was not 2xx");
          }
          HttpHeaders updatedHeaders = new HttpHeaders();
          updatedHeaders.addAll(clientResponse.headers().asHttpHeaders());
          updatedHeaders.add("X-Cached", Instant.now().toString());
          return clientResponse.bodyToMono(String.class)
              .map(body -> new Response(clientResponse.statusCode(), updatedHeaders, body));
        }).block());

    return ResponseEntity.status(response.statusCode().value())
        .headers(response.headers())
        .body(response.body());
  }
}
