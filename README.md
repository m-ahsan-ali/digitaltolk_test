To run this app create DB tables and add indexes using below queries:

CREATE TABLE translations (
    id SERIAL PRIMARY KEY,
    locale VARCHAR NOT NULL,
    key VARCHAR NOT NULL,
    content VARCHAR(2000) NOT NULL,
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE translation_tags (
    translation_id INTEGER NOT NULL,
    tag VARCHAR NOT NULL,
    CONSTRAINT fk_translation
        FOREIGN KEY (translation_id)
        REFERENCES translations (id)
        ON DELETE CASCADE
);

CREATE INDEX idx_translations_locale     ON translations(locale);
CREATE INDEX idx_translations_key        ON translations(key);
CREATE INDEX idx_translations_updated_at ON translations(updated_at);
CREATE INDEX idx_translation_tags_tag    ON translation_tags(tag);

Create DB setup and replace user_name and password of DB in app.props file.
