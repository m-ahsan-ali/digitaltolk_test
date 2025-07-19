package com.digital.tolk.test.dto;

import org.springframework.web.bind.annotation.RequestParam;

public class TranslationSearchRequest {

    private String tag;

    private String key;

    private String content;

    // Getters & setters

    public String getTag() { return tag; }
    public void setTag(String tag) { this.tag = tag; }

    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}

