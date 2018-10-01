package com.razzdrawon.googlebooks.model;

import java.util.List;

public class BookResponse {

    private Integer totalItems;
    private List<Book> items;

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public List<Book> getItems() {
        return items;
    }

    public void setItems(List<Book> items) {
        this.items = items;
    }
}
