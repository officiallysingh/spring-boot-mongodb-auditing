package com.ksoot.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@NoArgsConstructor
public class PaginationData extends RepresentationModel<PaginationData> {

    @Schema(description = "Current page number", example = "3")
    @Setter(value = AccessLevel.NONE)
    private int currentPage;

    @Schema(description = "Page size, number of records per page", example = "16")
    @Setter(value = AccessLevel.NONE)
    private int pageSize;

    @Setter(value = AccessLevel.NONE)
    @JsonIgnore
    private Sort sort;

    @Schema(description = "Total number of pages available, matching given filters", example = "35")
    @Setter(value = AccessLevel.NONE)
    private int totalPages;

    @Schema(
            description = "Total number of records available, matching given filters",
            required = true,
            example = "145")
    @Setter(value = AccessLevel.NONE)
    private long totalRecords;

    private PaginationData(
            final int currentPage, final int pageSize, final long totalRecords, final Sort sort) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalRecords = totalRecords;
        this.totalPages =
                this.pageSize == 0
                        ? 0
                        : (int) Math.ceil((double) this.totalRecords / (double) this.pageSize);
        this.sort = sort;
    }

    public static PaginationData of(final Pageable pageable, final long totalRecords) {
        return new PaginationData(
                pageable.isUnpaged() ? 0 : pageable.getPageNumber(),
                pageable.isUnpaged() ? 0 : pageable.getPageSize(),
                totalRecords,
                pageable.getSort());
    }

    @Schema(description = "Is this page first", example = "true")
    @JsonProperty("isFirst")
    public boolean isFirst() {
        return !hasPrevious();
    }

    @Schema(description = "Is this page last", example = "false")
    @JsonProperty("isLast")
    public boolean isLast() {
        return !hasNext();
    }

    @Schema(description = "Does next page exists", example = "true")
    @JsonProperty("hasNext")
    public boolean hasNext() {
        return getCurrentPage() + 1 < getTotalPages();
    }

    @Schema(description = "Does previous page exists", example = "false")
    @JsonProperty("hasPrevious")
    public boolean hasPrevious() {
        return getCurrentPage() > 0;
    }

    public int queryFirstResult() {
        return this.getCurrentPage() * this.getPageSize();
    }

    public int queryMaxResults() {
        return this.getPageSize();
    }

    @Schema(description = "Text description of this page", example = "Page 0 of 12")
    @JsonProperty("header")
    @Override
    public String toString() {
        return String.format(
                "Page %s of %d", this.totalRecords == 0 ? 0 : this.currentPage + 1, this.totalPages);
    }
}
