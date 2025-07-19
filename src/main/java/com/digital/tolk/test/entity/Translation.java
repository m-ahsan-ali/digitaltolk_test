package com.digital.tolk.test.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.JoinColumn;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "translations")
public class Translation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String locale;

    @Column(nullable = false)
    private String key;

    @Column(nullable = false, length = 2000)
    private String content;

    @ElementCollection
    @CollectionTable(name = "translation_tags", joinColumns = @JoinColumn(name = "translation_id"))
    @Column(name = "tag")
    private Set<String> tags = new HashSet<>();

    @Column(nullable = false)
    private Instant updatedAt = Instant.now();

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public String getKey() {
        return key;
    }

    public String getContent() {
        return content;
    }
}
