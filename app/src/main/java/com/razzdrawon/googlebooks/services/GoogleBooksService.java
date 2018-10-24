package com.razzdrawon.googlebooks.services;

import com.razzdrawon.googlebooks.model.Book;
import com.razzdrawon.googlebooks.model.BookResponse;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface GoogleBooksService {

    @GET("volumes")
    Observable<BookResponse> getBooks(@Query("q") String query, @Query("startIndex") Integer startIndex, @Query("maxResults") Integer maxResults);

    @GET("volumes/{volumeId}")
    Observable<Book> getBookById(@Path("volumeId") String bookId);

}
