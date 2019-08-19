package com.gmail.bishoybasily.cdc;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class Filter {

    private String query;
    private Integer page;
    private Integer count;
    private String[] sort;
    private String[] direction;

    public static Filter empty() {
        return new Filter();
    }

    //**

    public String getQuery() {
        return query;
    }

    public Filter setQuery(String query) {
        this.query = query;
        return this;
    }

    public Integer getPage() {
        return page;
    }

    public Filter setPage(Integer page) {
        this.page = page;
        return this;
    }

    public Integer getCount() {
        return count;
    }

    public Filter setCount(Integer count) {
        this.count = count;
        return this;
    }

    public String[] getSort() {
        return sort;
    }

    public Filter setSort(String[] sort) {
        this.sort = sort;
        return this;
    }

    public String[] getDirection() {
        return direction;
    }

    public Filter setDirection(String[] direction) {
        this.direction = direction;
        return this;
    }

    //**

    public boolean isPaginationPresented() {
        return page != null && count != null;
    }

    public boolean isSortPresented() {
        return sort != null && direction != null;
    }

    public boolean isSearchPresented() {
        return query != null && !"".equals(query);
    }

    public Pageable pageable() {
        if (isSortPresented())
            return new PageRequest(page, count, sort());
        return new PageRequest(page, count);
    }

    public Sort sort() {

        if (sort.length != direction.length)
            throw new IllegalArgumentException("Lengths are not the same");

        List<Sort.Order> orders = new ArrayList<>();
        for (int i = 0; i < sort.length; i++)
            orders.add(new Sort.Order(Sort.Direction.fromString(direction[i]), sort[i]));

        return new Sort(orders);
    }

    public String query() {
        return query;
    }

}
