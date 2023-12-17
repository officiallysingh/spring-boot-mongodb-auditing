package com.ksoot.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;

public class PaginatedResource<T> implements Iterable<T> {

    public static final int HASH_CODE_PRIME_NUM = 31;
    private final Collection<T> content;

    private PaginationData metadata;

    public PaginatedResource(final Page<T> page) {
        this.content = page.getContent();
        this.metadata = PaginationData.of(page.getPageable(), page.getTotalElements());
    }

    public PaginatedResource(
            final Collection<T> content, final Pageable pageable, final long totalRecords) {
        this.content = content;
        this.metadata = PaginationData.of(pageable, totalRecords);
    }

    @JsonProperty("page")
    public PaginationData getMetadata() {
        return this.metadata;
    }

    @JsonProperty("content")
    public Collection<T> getContent() {
        return Collections.unmodifiableCollection(this.content);
    }

    @Override
    public Iterator<T> iterator() {
        return this.content.iterator();
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || !getClass().equals(other.getClass())) {
            return false;
        }

        final PaginatedResource<?> that = (PaginatedResource<?>) other;
        final boolean metadataEquals = Objects.equals(this.metadata, that.metadata);

        // return metadataEquals ? super.equals(other) : false;
        return metadataEquals == super.equals(other);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result += this.metadata == null ? 0 : HASH_CODE_PRIME_NUM * this.metadata.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return String.format("PagedResource { content: %s, metadata: %s}", getContent(), this.metadata);
    }
}
