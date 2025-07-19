package com.digital.tolk.test.cache;

import java.time.Instant;
import java.util.Map;

public record TranslationCache(Map<String, String> translations, Instant lastUpdated) {
}
