package com.razzdrawon.googlebooks.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.razzdrawon.googlebooks.R;
import com.razzdrawon.googlebooks.activity.BookDetailsActivity;
import com.razzdrawon.googlebooks.model.Book;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BookItemAdapter extends RecyclerView.Adapter<BookItemAdapter.MyViewHolder> {

    public static final String BOOK_ID = "book_id";
    public List<Book> mBooks;

    public BookItemAdapter() {
        mBooks = new ArrayList<>();
    }

    @Override
    public BookItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(mBooks.get(position));
    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public View mView;
        ImageView thumbnail;
        TextView title;
        TextView publishedDate;

        public MyViewHolder(View view) {
            super(view);
            mView = view;

            thumbnail = (ImageView)mView.findViewById(R.id.imgThumbnail);
            title = (TextView)mView.findViewById(R.id.tvTitle);
            publishedDate = (TextView)mView.findViewById(R.id.tvPublishDate);

        }

        public void bind(final Book book) {

            final Context context = itemView.getContext();

            if(book.getVolumeInfo() != null){
                title.setText(book.getVolumeInfo().getTitle());
                publishedDate.setText(book.getVolumeInfo().getPublishedDate());
                if(book.getVolumeInfo().getImageLinks() != null){
                    Picasso.get().load(book.getVolumeInfo().getImageLinks().getSmallThumbnail()).into(thumbnail);
                }
                else{
                    thumbnail.setImageResource(R.mipmap.ic_launcher);
                }
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        Intent intent = new Intent(context, BookDetailsActivity.class);
                        intent.putExtra(BOOK_ID, book.getId());
                        context.startActivity(intent);
                    }
                });
            }
        }
    }

    public void updateBooks(List<Book> books){
        if(books != null){
            this.mBooks.addAll(books);
            notifyDataSetChanged();
        }
    }
}
