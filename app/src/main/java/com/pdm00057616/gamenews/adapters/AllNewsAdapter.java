package com.pdm00057616.gamenews.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.pdm00057616.gamenews.R;
import com.pdm00057616.gamenews.database.entities_models.NewEntity;
import com.squareup.picasso.Picasso;

import java.util.List;

public abstract class AllNewsAdapter extends RecyclerView.Adapter<AllNewsAdapter.ViewHolder> {

    private List<NewEntity> newList;

    private Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_news_cardview, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewEntity newAux = newList.get(holder.getAdapterPosition());
        bindViews(newAux, holder, context);

    }

    @Override
    public int getItemCount() {
        return newList == null ? 0 : newList.size();
    }

    public void setNewList(List<NewEntity> newList) {
        this.newList = newList;
        notifyDataSetChanged();
    }

    private void bindViews(NewEntity news, ViewHolder holder, Context context) {
        holder.newsID = news.getId();
        holder.title.setText(news.getTitle());
        holder.description.setText(news.getDescription());
        holder.currentFav = news.getIsFav();
        String[] dateArray=news.getCreateDate().split("T");
        String date=context.getResources().getString(R.string.date_string)+": "+dateArray[0];
        holder.date.setText(date);
        setfav(holder.currentFav, holder);
        int a = context.getResources().getDisplayMetrics().widthPixels;
        int b = Math.round(context.getResources().getDisplayMetrics().density);
        if (!(news.getCoverImage() == null)) {
            Picasso.get().load(news.getCoverImage())
                    .error(R.drawable.error_loading)
                    .into(holder.imageView);

        } else {
            Picasso.get()
                    .load(R.drawable.error_loading)
                    .error(R.drawable.error_loading)
                    .into(holder.imageView);
        }
    }

    private void setfav(int fav, ViewHolder holder) {
        holder.favButton.setImageResource((fav == 0) ? R.drawable.ic_no_fav_24dp : R.drawable.ic_fav_24dp);
    }

    public abstract void onclickFav(View view, String id, int currentFav);

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView title, description, date;
        private ImageButton favButton;
        private String newsID;
        private int currentFav;

        private ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.news_image);
            title = itemView.findViewById(R.id.new_title);
            description = itemView.findViewById(R.id.new_description);
            date=itemView.findViewById(R.id.date_text);
            favButton = itemView.findViewById(R.id.fav_button);
            favButton.setOnClickListener(v -> onclickFav(v, newsID, currentFav));
        }
    }
}
