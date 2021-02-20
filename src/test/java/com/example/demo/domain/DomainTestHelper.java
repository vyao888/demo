package com.example.demo.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;

import java.io.IOException;

@Log
public class DomainTestHelper {

  private final ObjectMapper mapper;

  public DomainTestHelper(final ObjectMapper mapper) {
    if(mapper == null) {
      throw new IllegalArgumentException("null ObjectMapper.");
    }
    this.mapper = mapper;
  }

  public String toJson(Object domain) {
    String json;
    try {
      json = this.mapper.writeValueAsString(domain);
    } catch (JsonProcessingException e) {
      String message = String.format("Failed to create json string from Domain object: %s.", domain);
      log.severe(message);
      throw new RuntimeException(message, e);
    }
    return json;
  }

  public Object toDomain(String content, Class cls) {
    Object domain;
    try {
      domain = this.mapper.readValue(content, cls);
    } catch (IOException e) {
      String message = String.format("Failed to create Domain: %s from the json string: %s.", cls.getName(), content);
      log.severe(message);
      throw new RuntimeException(message, e);
    }
    return domain;
  }

}
