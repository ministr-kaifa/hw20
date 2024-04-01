package ru.zubkoff.sber.hw20.remotehttpclient.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;

public record Response(HttpStatusCode statusCode, HttpHeaders headers, String body) {
}
