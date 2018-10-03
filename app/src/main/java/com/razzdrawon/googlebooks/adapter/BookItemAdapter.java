package com.razzdrawon.googlebooks.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.razzdrawon.googlebooks.R;
import com.razzdrawon.googlebooks.activity.BookDetailsActivity;
import com.razzdrawon.googlebooks.model.Book;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookItemAdapter extends RecyclerView.Adapter<BookItemAdapter.MyViewHolder> {

    public static final String BOOK_ID = "book_id";

    public List<Book> mBooks;

    // Provide a suitable constructor (depends on the kind of dataset)
    public BookItemAdapter(List<Book> books) {
        this.mBooks = books;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public BookItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        return new MyViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(mBooks.get(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mBooks.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Book item);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
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
        this.mBooks.addAll(books);
        notifyDataSetChanged();
    }
}
