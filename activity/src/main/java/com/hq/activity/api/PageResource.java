package com.hq.activity.api;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Iterator;
import java.util.List;

public class PageResource<T> extends ResourceSupport implements Page<T> {

    private final Page<T> page;

    public PageResource(Page<T> page, String pageParam, String sizeParam) {
        super();
        this.page = page;
        addPreviousLink(page, pageParam, sizeParam);
        addNextLink(page, pageParam, sizeParam);
        addFirstLink(page, pageParam, sizeParam);
        addLastLink(page, pageParam, sizeParam);
        addSelfLink(page, pageParam, sizeParam);
    }

    private void addPreviousLink(Page<T> page, String pageParam, String sizeParam) {
        if (page.hasPrevious()) {
            Link link = buildPageLink(pageParam, page.getNumber() - 1, sizeParam, page.getSize(), Link.REL_PREVIOUS);
            add(link);
        }
    }

    private void addNextLink(Page<T> page, String pageParam, String sizeParam) {
        if (page.hasNext()) {
            Link link = buildPageLink(pageParam, page.getNumber() + 1, sizeParam, page.getSize(), Link.REL_NEXT);
            add(link);
        }
    }

    private void addFirstLink(Page<T> page, String pageParam, String sizeParam) {
        if (page.hasContent()) {
            Link link = buildPageLink(pageParam, 0, sizeParam, page.getSize(), Link.REL_FIRST);
            add(link);
        }
    }

    private void addLastLink(Page<T> page, String pageParam, String sizeParam) {
        if (page.hasContent()) {
            Link link = buildPageLink(pageParam, page.getTotalPages() - 1, sizeParam, page.getSize(), Link.REL_LAST);
            add(link);
        }
    }

    private void addSelfLink(Page<T> page, String pageParam, String sizeParam) {
        if (page.hasContent()) {
            Link link = buildPageLink(pageParam, page.getNumber(), sizeParam, page.getSize(), Link.REL_SELF);
            add(link);
        }
    }

    private Link buildPageLink(String pageParam, int page, String sizeParam, int size, String rel) {
        String path = createBuilder().queryParam(pageParam, page).queryParam(sizeParam, size).build().toUriString();
        Link link = new Link(path, rel);
        return link;
    }

    private ServletUriComponentsBuilder createBuilder() {
        return ServletUriComponentsBuilder.fromCurrentRequestUri();
    }

    // Page<T> interface methods
    @Override
    public int getNumber() {
        return page.getNumber();
    }

    @Override
    public int getSize() {
        return page.getSize();
    }

    @Override
    public List<T> getContent() {
        return page.getContent();
    }

    @Override
    public int getNumberOfElements() {
        return page.getNumberOfElements();
    }

    @Override
    public Sort getSort() {
        return page.getSort();
    }

    @Override
    public boolean hasContent() {
        return page.hasContent();
    }

    @Override
    public boolean hasNext() {
        return page.hasNext();
    }

    @Override
    public boolean hasPrevious() {
        return page.hasPrevious();
    }

    @Override
    public boolean isFirst() {
        return page.isFirst();
    }

    @Override
    public boolean isLast() {
        return page.isLast();
    }

    @Override
    public Pageable nextPageable() {
        return page.nextPageable();
    }

    @Override
    public Pageable previousPageable() {
        return page.previousPageable();
    }

    @Override
    public Iterator<T> iterator() {
        return page.iterator();
    }

    @Override
    public long getTotalElements() {
        return page.getTotalElements();
    }

    @Override
    public int getTotalPages() {
        return page.getTotalPages();
    }

    @Override
    public <S> Page<S> map(Converter<? super T, ? extends S> arg0) {
        return page.map(arg0);
    }
}
