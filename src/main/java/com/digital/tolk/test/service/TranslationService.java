package com.digital.tolk.test.service;

import com.digital.tolk.test.cache.TranslationCacheManager;
import com.digital.tolk.test.dto.TranslationDto;
import com.digital.tolk.test.entity.Translation;
import com.digital.tolk.test.repository.TranslationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TranslationService {
    private final TranslationRepository repository;
    private final TranslationCacheManager cacheManager;

    public TranslationService(TranslationRepository repository, TranslationCacheManager cacheManager) {
        this.repository = repository;
        this.cacheManager = cacheManager;
    }

    public Translation create(TranslationDto dto) {
        Translation t = new Translation();
        t.setLocale(dto.locale());
        t.setKey(dto.key());
        t.setContent(dto.content());
        t.setTags(dto.tags());
        return repository.save(t);
    }

    public Page<Translation> search(String tag, String key, String content, Pageable pageable) {
        if (tag != null) return repository.findByTagsContaining(tag, pageable);
        if (key != null) return repository.findByKey(key, pageable);
        if (content != null) return repository.findByContentContaining(content, pageable);
        return repository.findAll(pageable);
    }


    public Translation update(Long id, TranslationDto dto) {
        Translation existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Translation not found with ID " + id));

        existing.setLocale(dto.locale());
        existing.setKey(dto.key());
        existing.setContent(dto.content());
        existing.setTags(dto.tags());

        return repository.save(existing);
    }

    public Map<String, String> export(String locale) {
        return cacheManager.getFreshTranslations(locale);
    }
}

