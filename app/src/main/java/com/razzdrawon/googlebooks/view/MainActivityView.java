package com.razzdrawon.googlebooks.view;

import com.razzdrawon.googlebooks.model.BookResponse;

public interface MainActivityView {

    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getBookResponseSuccess(BookResponse bookListResponse);

}
