package com.pdm00057616.gamenews.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pdm00057616.gamenews.R;
import com.pdm00057616.gamenews.database.entities_models.NewEntity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoProvider;

import java.util.List;

public class AllNewsAdapter extends RecyclerView.Adapter<AllNewsAdapter.ViewHolder> {

    private List<NewEntity> newList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_news_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewEntity newAux = newList.get(holder.getAdapterPosition());
        bindViews(newAux, holder);

    }

    @Override
    public int getItemCount() {
        return newList == null ? 0 : newList.size();
    }

    public void setNewList(List<NewEntity> newList) {
        this.newList = newList;
        notifyDataSetChanged();
        System.out.println(newList.size());
    }

    private void bindViews(NewEntity news, ViewHolder holder) {
        holder.title.setText(news.getTitle());
        holder.description.setText(news.getGame());
        if (!(news.getCoverImage() == null) &&
                news.getCoverImage().length() > 20) {
            Picasso.get().load(news.getCoverImage()).error(R.drawable.error_loading).into(holder.imageView);
        } else {
            Picasso.get().load(R.drawable.error_loading).error(R.drawable.error_loading).into(holder.imageView);
        }
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView title, description;

        private ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.news_image);
            title = itemView.findViewById(R.id.new_title);
            description = itemView.findViewById(R.id.new_description);
        }
    }
}
