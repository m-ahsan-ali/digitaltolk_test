package com.digital.tolk.test.dto;

import java.util.Set;

public record TranslationDto(String locale, String key, String content, Set<String> tags) {
}
