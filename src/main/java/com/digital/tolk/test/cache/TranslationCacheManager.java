package com.digital.tolk.test.cache;

import com.digital.tolk.test.dto.TranslationExportResponse;
import com.digital.tolk.test.repository.TranslationRepository;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class TranslationCacheManager {
    private final TranslationRepository repository;
    private final ConcurrentMap<String, TranslationCache> localeTranslationCache = new ConcurrentHashMap<>();

    public TranslationCacheManager(TranslationRepository repository) {
        this.repository = repository;
    }

    public Map<String, String> getFreshTranslations(String locale) {
        if (localeTranslationCache.containsKey(locale)) {
            return refreshTranslationsForLocale(locale);
        }
        else {
            Map<String, String> freshTranslations = loadTranslationsForLocale(locale);
            localeTranslationCache.put(locale, new TranslationCache(freshTranslations, Instant.now()));
            return freshTranslations;
        }
    }

    private Map<String, String> refreshTranslationsForLocale(String locale) {
        TranslationCache cached = localeTranslationCache.get(locale);
        Instant lastUpdatedAt = cached.lastUpdated();
        List<TranslationExportResponse> updatedTranslations = repository.findRecentTranslationsByLocale(locale, lastUpdatedAt);
        for (TranslationExportResponse translation : updatedTranslations) {
            cached.translations().put(translation.getKey(), translation.getContent());
        }
        localeTranslationCache.put(locale, new TranslationCache(cached.translations(), Instant.now()));
        return cached.translations();
    }

    private Map<String, String> loadTranslationsForLocale(String locale) {

        List<TranslationExportResponse> translations = repository.findByLocale(locale);
        Map<String, String> result = new HashMap<>(translations.size());
        for (TranslationExportResponse translation : translations) {
            result.put(translation.getKey(), translation.getContent());
        }
        return result;
    }
}
