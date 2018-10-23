package com.razzdrawon.googlebooks.view;

import com.razzdrawon.googlebooks.model.Book;

public interface BookDetailsView {

    void showWait();

    void removeWait();

    void onAPIFailure();

    void getBookDetailsSuccess(Book book);
}
