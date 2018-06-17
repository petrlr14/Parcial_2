package com.pdm00057616.gamenews.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pdm00057616.gamenews.R;
import com.pdm00057616.gamenews.database.entities_models.NewEntity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public abstract class AllNewsAdapter extends RecyclerView.Adapter<AllNewsAdapter.ViewHolder> {

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
    }

    private void bindViews(NewEntity news, ViewHolder holder) {
        holder.newsID = news.getId();
        holder.title.setText(news.getTitle());
        holder.description.setText(news.getDescription());
        holder.currentFav = news.getIsFav();
        holder.progressBar.setVisibility(View.VISIBLE);
        setfav(holder.currentFav, holder);
        if (!(news.getCoverImage() == null)) {
            Picasso.get().load(news.getCoverImage())
                    .error(R.drawable.error_loading)
                    .into(holder.imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            holder.progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Exception e) {
                            holder.progressBar.setVisibility(View.GONE);
                        }
                    });

        } else {
            Picasso.get()
                    .load(R.drawable.error_loading)
                    .error(R.drawable.error_loading)
                    .into(holder.imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            holder.progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Exception e) {
                            holder.progressBar.setVisibility(View.GONE);
                        }
                    });
        }
        fillInfo(holder, news);
    }

    private void setfav(int fav, ViewHolder holder) {
        holder.favButton.setImageResource((fav == 0) ? R.drawable.ic_no_fav_24dp : R.drawable.ic_fav_24dp);
    }

    private void fillInfo(ViewHolder holder, NewEntity news) {
        holder.titulo = news.getTitle();
        holder.descripcion = news.getDescription();
        holder.contenido = news.getBody();
        holder.image = news.getCoverImage();
    }

    public abstract void onclickFav(String id, int currentFav);

    public abstract void onClickDetails(String titulo, String descripcion, String contenido, String image);

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView title, description, date;
        private ImageButton favButton;
        private String newsID, titulo, descripcion, contenido, image;
        private ProgressBar progressBar;
        private int currentFav;

        private ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.news_image);
            title = itemView.findViewById(R.id.new_title);
            description = itemView.findViewById(R.id.new_description);
            date = itemView.findViewById(R.id.date_text);
            progressBar = itemView.findViewById(R.id.progress_bar);
            favButton = itemView.findViewById(R.id.fav_button);
            favButton.setOnClickListener(v -> onclickFav(newsID, currentFav));
            itemView.setOnClickListener(v -> onClickDetails(titulo, descripcion, contenido, image));
        }
    }
}
