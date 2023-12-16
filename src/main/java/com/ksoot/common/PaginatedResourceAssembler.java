package com.ksoot.common;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.UriTemplate;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.function.Function;

import static org.springframework.web.util.UriComponentsBuilder.fromUri;

public class PaginatedResourceAssembler {

  public static final String PAGE_MUST_NOT_BE_NULL = "Page must not be null!";
  public static final String PAGE_CONTENT_MUST_NOT_BE_NULL = "Page Content must not be null!";
  public static final String PAGEABLE_MUST_NOT_BE_NULL = "Pageable must not be null!";
  public static final String PAGE_TRANSFORMER_MUST_NOT_BE_NULL =
      "'pageTransformer' must not be null!";
  private static final boolean FORCE_FIRST_AND_LAST_RELS = false;

  @NonNull private static HateoasPageableHandlerMethodArgumentResolver pageableResolver;

  public PaginatedResourceAssembler(
      @Nullable final HateoasPageableHandlerMethodArgumentResolver resolver) {
    PaginatedResourceAssembler.pageableResolver =
        resolver == null ? new HateoasPageableHandlerMethodArgumentResolver() : resolver;
  }

  /**
   * Use in case of non reactive web application, base URL can be automatically determined from
   * ServletUriComponentsBuilder
   *
   * @param <T> Class of the content of page
   * @param content Page data
   * @param pageRequest Requested Page
   * @param totalRecords Total number of records
   * @return PaginatedResource
   */
  public static <T> PaginatedResource<T> assemble(
      final List<T> content, final Pageable pageRequest, final long totalRecords) {
    return assemble(new PageImpl<T>(content, pageRequest, totalRecords), getUriTemplate());
  }

  public static <T, R> PaginatedResource<R> assemble(
      final List<T> content,
      final Pageable pageRequest,
      final long totalRecords,
      final Function<List<T>, List<R>> pageTransformer) {

    final Page<R> newPage =
        PageableExecutionUtils.getPage(
            pageTransformer.apply(content), pageRequest, () -> totalRecords);

    return assemble(newPage, getUriTemplate());
  }

  /**
   * Use in case of non reactive web application, base URL can be automatically determined from
   * ServletUriComponentsBuilder
   *
   * @param <T> Class of the content of page
   * @param page Requested Page
   * @return PaginatedResource
   */
  public static <T> PaginatedResource<T> assemble(final Page<T> page) {

    Assert.notNull(page, PAGE_MUST_NOT_BE_NULL);
    Assert.notNull(page.getContent(), PAGE_CONTENT_MUST_NOT_BE_NULL);
    Assert.notNull(page.getPageable(), PAGEABLE_MUST_NOT_BE_NULL);

    final PaginatedResource<T> paginatedResource = new PaginatedResource<>(page);
    addPaginationLinks(paginatedResource, page, getUriTemplate());
    return paginatedResource;
  }

  public static <T, R> PaginatedResource<R> assemble(
      final Page<T> page, final Function<List<T>, List<R>> pageTransformer) {
    Assert.notNull(page, PAGE_MUST_NOT_BE_NULL);
    Assert.notNull(page.getContent(), PAGE_CONTENT_MUST_NOT_BE_NULL);
    Assert.notNull(page.getPageable(), PAGEABLE_MUST_NOT_BE_NULL);
    Assert.notNull(pageTransformer, PAGE_TRANSFORMER_MUST_NOT_BE_NULL);

    final Page<R> newPage =
        PageableExecutionUtils.getPage(
            pageTransformer.apply(page.getContent()), page.getPageable(), page::getTotalElements);

    final PaginatedResource<R> paginatedResource = new PaginatedResource<>(newPage);
    addPaginationLinks(paginatedResource, newPage, getUriTemplate());
    return paginatedResource;
  }

  private static <T> PaginatedResource<T> assemble(final Page<T> page, final UriTemplate base) {

    Assert.notNull(page, PAGE_MUST_NOT_BE_NULL);
    Assert.notNull(page.getContent(), PAGE_CONTENT_MUST_NOT_BE_NULL);
    Assert.notNull(page.getPageable(), PAGEABLE_MUST_NOT_BE_NULL);

    final PaginatedResource<T> paginatedResource = new PaginatedResource<>(page);
    addPaginationLinks(paginatedResource, page, base);
    return paginatedResource;
  }

  private static <T> void addPaginationLinks(
      final PaginatedResource<T> paginatedResource, final Page<T> page, final UriTemplate base) {

    final boolean isNavigable = page.hasPrevious() || page.hasNext();
    final Link selfLink = createLink(base, page.getPageable(), IanaLinkRelations.SELF);
    paginatedResource.getMetadata().add(selfLink);
    if (page.hasPrevious()) {
      paginatedResource
          .getMetadata()
          .add(createLink(base, page.previousPageable(), IanaLinkRelations.PREV));
    }
    if (page.hasNext()) {
      paginatedResource
          .getMetadata()
          .add(createLink(base, page.nextPageable(), IanaLinkRelations.NEXT));
    }
    if (isNavigable || FORCE_FIRST_AND_LAST_RELS) {
      paginatedResource
          .getMetadata()
          .add(
              createLink(
                  base,
                  PageRequest.of(0, page.getSize(), page.getSort()),
                  IanaLinkRelations.FIRST));
    }
    if (isNavigable || FORCE_FIRST_AND_LAST_RELS) {
      final int lastIndex = page.getTotalPages() == 0 ? 0 : page.getTotalPages() - 1;
      paginatedResource
          .getMetadata()
          .add(
              createLink(
                  base,
                  PageRequest.of(lastIndex, page.getSize(), page.getSort()),
                  IanaLinkRelations.LAST));
    }
  }

  private static Link createLink(
      final UriTemplate base, final Pageable pageable, final LinkRelation relation) {
    final UriComponentsBuilder builder = fromUri(base.expand());
    pageableResolver.enhance(builder, null, pageable);
    return Link.of(UriTemplate.of(builder.build().toString()), relation);
  }

  private static UriTemplate getUriTemplate() {
    return UriTemplate.of(currentRequest());
  }

  // Work in case of Servlet web application, in case of Reactive web application throws
  // NullPointerException
  private static String currentRequest() {
    return ServletUriComponentsBuilder.fromCurrentRequest().build().toString();
  }
}
