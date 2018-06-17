package com.pdm00057616.gamenews.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pdm00057616.gamenews.R;
import com.pdm00057616.gamenews.database.entities_models.PlayerEntity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {

    private List<PlayerEntity> list;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        bindView(list.get(position), holder);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void setList(List<PlayerEntity> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    private void bindView(PlayerEntity player, ViewHolder holder) {
        holder.textViewName.setText(player.getName());
        holder.textViewBio.setText(player.getBio());
        if (player.getAvatar() != null)
            Picasso.get().load(player.getAvatar()).error(R.drawable.error_loading).into(holder.imageView);
        else
            Picasso.get().load(R.drawable.error_loading).error(R.drawable.error_loading).into(holder.imageView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textViewName, textViewBio;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.player_image);
            textViewName = itemView.findViewById(R.id.player_name);
            textViewBio = itemView.findViewById(R.id.bio);
        }
    }
}
