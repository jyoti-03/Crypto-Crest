package com.stackroute.cryptoserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> List<T> parseJsonToList(String json, TypeReference<List<T>> typeReference) {
        try {
            JsonNode jsonData = objectMapper.readTree(json);
            if (jsonData.has("data") && jsonData.get("data").isArray()) {
                return objectMapper.readValue(jsonData.get("data").toString(), typeReference);
            }
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error parsing JSON: " + e.getMessage(), e);
        }
        return Collections.emptyList();
    }
}
