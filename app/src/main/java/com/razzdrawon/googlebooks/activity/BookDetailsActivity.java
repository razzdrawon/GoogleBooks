package com.razzdrawon.googlebooks.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.razzdrawon.googlebooks.R;
import com.razzdrawon.googlebooks.adapter.BookItemAdapter;
import com.razzdrawon.googlebooks.model.Book;
import com.razzdrawon.googlebooks.presenter.BookDetailsPresenter;
import com.razzdrawon.googlebooks.services.GoogleBooksService;
import com.razzdrawon.googlebooks.view.BookDetailsView;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class BookDetailsActivity extends DaggerAppCompatActivity implements BookDetailsView {

    @Inject
    public GoogleBooksService service;

    ImageView bookCover;
    TextView tvAuthors;
    TextView tvDescription;
    TextView tvPublishedDate;
    TextView failureMessage;
    LinearLayout bookDetailsLayout;
    ProgressBar progressBar;

    String bookId;
    BookDetailsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        getViews();

        Intent intent = getIntent();
        bookId = intent.getStringExtra(BookItemAdapter.BOOK_ID);

        presenter = new BookDetailsPresenter(this, service);
        presenter.getBookDetails(bookId);

    }

    private void getViews() {
        progressBar = (ProgressBar) findViewById(R.id.progressDetails);

        bookCover = findViewById(R.id.imgBookCover);
        tvAuthors = findViewById(R.id.tvAuthors);
        tvDescription = findViewById(R.id.tvDescription);
        tvPublishedDate = findViewById(R.id.tvPublishDate);

        failureMessage = (TextView) findViewById(R.id.failure_book_message);
        bookDetailsLayout = (LinearLayout) findViewById(R.id.book_details_layout);
    }


    @Override
    public void showWait() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void removeWait() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onAPIFailure() {
        failureMessage.setVisibility(View.VISIBLE);
        bookDetailsLayout.setVisibility(View.GONE);
    }

    @Override
    public void getBookDetailsSuccess(Book book) {
        tvPublishedDate.setText(book.getVolumeInfo().getPublishedDate());
        tvAuthors.setText(book.getVolumeInfo().getAuthorsString());
        tvDescription.setText(book.getVolumeInfo().getDescription());
        if(book.getVolumeInfo().getImageLinks() != null){
            Picasso.get().load(book.getVolumeInfo().getImageLinks().getSmallThumbnail()).into(bookCover);
        }
        else{
            bookCover.setImageResource(R.mipmap.ic_launcher);
        }

    }
}
