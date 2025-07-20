package com.digital.tolk.test.controller;

import com.digital.tolk.test.dto.TranslationDto;
import com.digital.tolk.test.dto.TranslationSearchRequest;
import com.digital.tolk.test.entity.Translation;
import com.digital.tolk.test.service.TranslationService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/translations")
public class TranslationController {
    private final TranslationService service;

    public TranslationController(TranslationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Translation> create(@RequestBody TranslationDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Translation> update(@PathVariable Long id, @RequestBody TranslationDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Translation>> search(@Valid @ModelAttribute TranslationSearchRequest request,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, Math.min(size, 100)); // enforce max page size
        return ResponseEntity.ok(service.search(request.getTag(), request.getKey(), request.getContent(), pageable));
    }


    @GetMapping("/export")
    public ResponseEntity<Map<String, String>> export(@RequestParam("locale") String locale) {
        return ResponseEntity.ok(service.export(locale));
    }
}

