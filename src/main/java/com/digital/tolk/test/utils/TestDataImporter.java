package com.digital.tolk.test.utils;

import com.digital.tolk.test.entity.Translation;
import com.digital.tolk.test.repository.TranslationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class TestDataImporter implements CommandLineRunner {
    private final TranslationRepository repository;

    public TestDataImporter(TranslationRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (repository.count() < 100000) {
            List<Translation> batch = new ArrayList<>();
            for (int i = 0; i < 100_000; i++) {
                Translation t = new Translation();
                t.setLocale("en");
                t.setKey("key_" + i);
                t.setContent("This is sample content " + i);
                t.setTags(Set.of("web"));
                batch.add(t);
            }
            repository.saveAll(batch);
        }
    }
}
