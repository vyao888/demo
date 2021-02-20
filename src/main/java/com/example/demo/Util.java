package com.example.demo;

import org.apache.commons.lang3.StringUtils;

final public class Util {
  private Util() {}

  public static void validate(final String value, final String name) {
    if(StringUtils.isEmpty(value)) {
      throw new IllegalArgumentException(String.format("Invalid %s: %s", name, value));
    }
  }

  public static void validateNumber(final String value, final String name) {
    if(StringUtils.isEmpty(value)) {
      throw new IllegalArgumentException(String.format("Invalid %s: %s", name, value));
    }
    if(!StringUtils.isNumeric(value)) {
      throw new IllegalArgumentException(String.format("Invalid %s: %s", name, value));
    }
  }

}
