package com.razzdrawon.googlebooks.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.razzdrawon.googlebooks.R;
import com.razzdrawon.googlebooks.adapter.BookItemAdapter;
import com.razzdrawon.googlebooks.model.Book;
import com.razzdrawon.googlebooks.model.BookResponse;
import com.razzdrawon.googlebooks.services.GoogleBooksService;
import com.razzdrawon.googlebooks.services.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    public static final String BOOK_ID = "book_id";


    //private EndlessScrollListener mRecyclerView;

    private RecyclerView mRecyclerView;
    private BookItemAdapter mAdapter;
    private GridLayoutManager mLayoutManager = new GridLayoutManager(this, 1);


    public GoogleBooksService service;


    private BookResponse bookResponse;
    private List<Book> books = new ArrayList<>();

    Boolean isScrolling = false;
    int currentItems, totalItems, scrollOutItems;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getViews();

        service = RetrofitClient.getInstance().getService();

        addBookItems("android", 0, 15);

        configureAdapter();

    }

    private void configureAdapter() {
        mAdapter = new BookItemAdapter(books, new BookItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Book book) {
                //Toast.makeText(MainActivity.this, "Item Clicked:  " + book.getVolumeInfo().getTitle(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, BookDetailsActivity.class);
                intent.putExtra(BOOK_ID, book.getId());
                startActivity(intent);

            }
        });

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = mLayoutManager.getChildCount();
                totalItems = mLayoutManager.getItemCount();
                scrollOutItems = mLayoutManager.findFirstCompletelyVisibleItemPosition();

                if(isScrolling && (currentItems + scrollOutItems == totalItems)) {
                    isScrolling = false;
                    addBookItems("android", totalItems, 3);
                }
            }
        });
    }

    private void getViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.progress);
    }

    public void addBookItems(String query, Integer startIndex, Integer maxResults) {

        progressBar.setVisibility(View.VISIBLE);

        service.getBooks(query, startIndex, maxResults).enqueue(new Callback<BookResponse>() {
            @Override
            public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {

                if (response.isSuccessful()) {
                    bookResponse = response.body();
                    mAdapter.mBooks.addAll(bookResponse.getItems());
                    mAdapter.notifyDataSetChanged();
                    //Toast.makeText(MainActivity.this, "server returned : " + response.body().getItems().size() + " Total: " + mAdapter.getItemCount(), Toast.LENGTH_SHORT).show();
                }
                else {
                    // error case
                    switch (response.code()) {
                        case 400:
                            Toast.makeText(MainActivity.this, "No more books were found", Toast.LENGTH_SHORT).show();
                        case 404:
                            Toast.makeText(MainActivity.this, "not found", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(MainActivity.this, "server broken", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(MainActivity.this, "unknown error when calling rest service", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<BookResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Network failure. Inform the user and possibly retry", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
