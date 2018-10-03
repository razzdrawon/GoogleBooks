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
import com.razzdrawon.googlebooks.presenter.MainActivityPresenter;
import com.razzdrawon.googlebooks.services.GoogleBooksService;
import com.razzdrawon.googlebooks.services.RetrofitClient;
import com.razzdrawon.googlebooks.view.MainActivityView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements MainActivityView{



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

    MainActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getViews();

        service = RetrofitClient.getInstance().getService();

        presenter = new MainActivityPresenter(this);
        presenter.getBoolList("android", 0, 15);




        mAdapter = new BookItemAdapter(books);

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
                    presenter.getBoolList("android", totalItems, 15);
                }
            }
        });

    }


    private void getViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.progress);
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
    public void onFailure(String appErrorMessage) {

    }

    @Override
    public void getBookResponseSuccess(BookResponse bookListResponse) {
        mAdapter.updateBooks(bookListResponse.getItems());
    }
}
