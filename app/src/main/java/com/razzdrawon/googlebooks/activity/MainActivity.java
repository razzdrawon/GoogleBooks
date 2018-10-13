package com.razzdrawon.googlebooks.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.razzdrawon.googlebooks.R;
import com.razzdrawon.googlebooks.adapter.BookItemAdapter;
import com.razzdrawon.googlebooks.model.BookResponse;
import com.razzdrawon.googlebooks.presenter.MainActivityPresenter;
import com.razzdrawon.googlebooks.services.GoogleBooksService;
import com.razzdrawon.googlebooks.view.MainActivityView;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity implements MainActivityView {

    public static final Integer COLUMNS_NBR = 1;

    @Inject
    MainActivityPresenter presenter;

    //Endless book list
    ProgressBar progressBar;
    Boolean isScrolling = false;
    int currentItems, totalItems, scrollOutItems;
    TextView failureMessage;

    //RecyclerViewVars
    private RecyclerView mRecyclerView;
    private BookItemAdapter mAdapter;
    private GridLayoutManager mLayoutManager = new GridLayoutManager(this, COLUMNS_NBR);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getViews();

        //presenter = new MainActivityPresenter(this, service);
        presenter.getBoolList("android", 0, 15);

        mAdapter = new BookItemAdapter();
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new MyOnScrollListener());
    }

    private void getViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        failureMessage = (TextView) findViewById(R.id.failure_message);
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
    }

    @Override
    public void getBookResponseSuccess(BookResponse bookListResponse) {
        mAdapter.updateBooks(bookListResponse.getItems());
        failureMessage.setVisibility(View.GONE);
    }

    public class MyOnScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true;
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            currentItems = mLayoutManager.getChildCount();
            totalItems = mLayoutManager.getItemCount();
            scrollOutItems = mLayoutManager.findFirstCompletelyVisibleItemPosition();

            if (isScrolling && (currentItems + scrollOutItems == totalItems)) {
                isScrolling = false;
                presenter.getBoolList("android", totalItems, 5);
            }
        }
    }
}
