package com.razzdrawon.googlebooks.view;

import com.razzdrawon.googlebooks.model.BookResponse;

public interface MainActivityView {

    void showWait();

    void removeWait();

    void onAPIFailure();

    void getBookResponseSuccess(BookResponse bookListResponse);

}
