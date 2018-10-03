package com.razzdrawon.googlebooks.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.razzdrawon.googlebooks.R;
import com.razzdrawon.googlebooks.adapter.BookItemAdapter;
import com.razzdrawon.googlebooks.model.Book;
import com.razzdrawon.googlebooks.model.BookResponse;
import com.razzdrawon.googlebooks.services.GoogleBooksService;
import com.razzdrawon.googlebooks.services.RetrofitClient;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookDetailsActivity extends AppCompatActivity {

    private Book book;
    ProgressBar progressBar;
    public GoogleBooksService service;

    ImageView bookCover;
    TextView tvAuthors;
    TextView tvDescription;
    TextView tvPublishedDate;

    String bookId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);


        service = RetrofitClient.getInstance().getService();

        Intent intent = getIntent();
        bookId = intent.getStringExtra(BookItemAdapter.BOOK_ID);


        progressBar = (ProgressBar) findViewById(R.id.progressDetails);

        bookCover = findViewById(R.id.imgBookCover);
        tvAuthors = findViewById(R.id.tvAuthors);
        tvDescription = findViewById(R.id.tvDescription);
        tvPublishedDate = findViewById(R.id.tvPublishDate);



        //Toast.makeText(BookDetailsActivity.this, "Item:  " + bookId, Toast.LENGTH_LONG).show();

        getBookDetails(bookId);
    }

    public void getBookDetails(final String bookId) {

        progressBar.setVisibility(View.VISIBLE);

        service.getBookById(bookId).enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                if (response.isSuccessful()) {
                    book = response.body();

                    tvPublishedDate.setText(book.getVolumeInfo().getPublishedDate());

                    String authors = "";
                    for(String author: book.getVolumeInfo().getAuthors()){
                        if(authors == ""){
                            authors = author;
                        }
                        else{
                            authors = authors + "\n" + author;
                        }

                    }

                    tvAuthors.setText(authors);
                    tvDescription.setText(book.getVolumeInfo().getDescription());

                    if(book.getVolumeInfo().getImageLinks() != null){
                        Picasso.get().load(book.getVolumeInfo().getImageLinks().getSmallThumbnail()).into(bookCover);
                    }
                    else{
                        bookCover.setImageResource(R.mipmap.ic_launcher);
                    }

                    //Toast.makeText(BookDetailsActivity.this, "We got Book: " + book.getId(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
                else {
                    // error case
                    switch (response.code()) {
                        case 404:
                            Toast.makeText(BookDetailsActivity.this, "not found", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(BookDetailsActivity.this, "server broken", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(BookDetailsActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                Toast.makeText(BookDetailsActivity.this, "Network failure. Inform the user and possibly retry", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
