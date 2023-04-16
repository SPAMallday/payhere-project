package com.spamallday.payhere.util;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CursorResult<T> {
    private List<T> products;
    private Boolean hasNext;

    public CursorResult(List<T> products, Boolean hasNext) {
        this.products = products;
        this.hasNext = hasNext;
    }
}