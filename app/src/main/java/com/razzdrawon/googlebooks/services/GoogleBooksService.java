package com.razzdrawon.googlebooks.services;

import com.razzdrawon.googlebooks.model.Book;
import com.razzdrawon.googlebooks.model.BookResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GoogleBooksService {


    @GET("volumes")
    Call<BookResponse> getBooks(@Query("q") String query, @Query("startIndex") Integer startIndex, @Query("maxResults") Integer maxResults);

    @GET("volumes/{volumeId}")
    Call<Book> getBookById(@Path("volumeId") String bookId);

}
