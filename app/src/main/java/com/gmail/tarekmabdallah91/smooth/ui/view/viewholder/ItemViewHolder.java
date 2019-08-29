

package com.gmail.tarekmabdallah91.smooth.ui.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmail.tarekmabdallah91.news.R;
import com.gmail.tarekmabdallah91.news.models.articles.Article;
import com.gmail.tarekmabdallah91.smooth.ui.listeners.ItemClickListener;

import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.loadImage;

public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    private Article article;
    private TextView titleTextView;
    private TextView authorTV;
    private ImageView thumbnailIV;
    private ItemClickListener itemClickListener;

    public ItemViewHolder(View view, ItemClickListener itemClickListener) {
        super(view);
        this.titleTextView = view.findViewById(R.id.article_title);
        this.authorTV = view.findViewById(R.id.author);
        this.thumbnailIV = view.findViewById(R.id.article_image);
        this.itemClickListener = itemClickListener;
        view.setOnClickListener(this);

    }

    public void bindTo(Article article) {
        this.article = article;
        titleTextView.setText(article.getWebTitle());
        authorTV.setText(article.getFields().getAuthorName());
        if(article.getFields().getThumbnail() != null) {
            loadImage(article.getFields().getThumbnail(),thumbnailIV);
        }
    }

    @Override
    public void onClick(View view) {
        if (itemClickListener != null) {
            itemClickListener.OnItemClick(article);
        }
    }

}
