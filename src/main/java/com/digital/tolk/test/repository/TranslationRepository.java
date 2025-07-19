package com.digital.tolk.test.repository;

import com.digital.tolk.test.dto.TranslationExportResponse;
import com.digital.tolk.test.entity.Translation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface TranslationRepository extends JpaRepository<Translation, Long> {

    Page<Translation> findByTagsContaining(String tag, Pageable pageable);
    Page<Translation> findByKey(String key, Pageable pageable);
    Page<Translation> findByContentContaining(String content, Pageable pageable);
    Page<Translation> findAll(Pageable pageable);

    @Query(value = """
            SELECT t.key as key, t.content as content FROM Translation t WHERE t.locale = :locale""")
    List<TranslationExportResponse> findByLocale(String locale);

    @Query("SELECT t.key as key, t.content as content FROM Translation t WHERE t.locale = :locale AND t.updatedAt > :lastCachedAt")
    List<TranslationExportResponse> findRecentTranslationsByLocale (String locale, Instant lastCachedAt);
}
