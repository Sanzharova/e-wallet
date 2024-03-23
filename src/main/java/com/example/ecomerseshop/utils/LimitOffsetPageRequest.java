package com.example.ecomerseshop.utils;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class LimitOffsetPageRequest implements Pageable {

    private final int offset;
    private final int limit;
    private Sort sort;

    public LimitOffsetPageRequest(int offset, int limit, Sort sort) {
        if (offset < 0)
            throw new IllegalArgumentException("Смещение не должно быть меньше нуля!");

        if (limit < 0)
            throw new IllegalArgumentException("Лимит не должен быть меньше нуля!");

        this.offset = offset;
        this.limit = limit;

        if (sort != null) {
            this.sort = sort;
        }
    }

    public LimitOffsetPageRequest(int offset, int limit) {
        this(offset, limit, null);
    }

    @Override
    public int getPageNumber() { return 0; }

    @Override
    public int getPageSize() { return limit; }

    @Override
    public long getOffset() { return offset; }

    @Override
    public Sort getSort() { return this.sort; }

    @Override
    public Pageable next() { return null; }

    @Override
    public Pageable previousOrFirst() { return this; }

    @Override
    public Pageable first() { return this; }

    @Override
    public Pageable withPage(int pageNumber) { return null; }

    @Override
    public boolean hasPrevious() { return false; }
}