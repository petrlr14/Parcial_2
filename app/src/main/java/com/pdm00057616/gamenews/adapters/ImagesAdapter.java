package com.pdm00057616.gamenews.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pdm00057616.gamenews.R;
import com.pdm00057616.gamenews.database.entities_models.NewEntity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder> {

    private List<NewEntity> newsList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.images_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewEntity newEntity=newsList.get(position);
        System.out.println(newEntity.getTitle());
        if(newEntity.getCoverImage()!=null)
            Picasso.get().load(newEntity.getCoverImage()).error(R.drawable.error_loading).into(holder.imageView);
        else
            Picasso.get().load(R.drawable.error_loading).error(R.drawable.error_loading).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return newsList==null?0:newsList.size();
    }

    public void setNewsList(List<NewEntity> list){
        newsList=list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_);
        }
    }
}
