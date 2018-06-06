package com.pdm00057616.gamenews.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pdm00057616.gamenews.R;
import com.pdm00057616.gamenews.adapters.PlayerAdapter;
import com.pdm00057616.gamenews.viewmodels.PlayerViewModel;

public class TopPlayersFragmet extends Fragment{

    private RecyclerView recyclerView;
    private String game;
    private PlayerAdapter adapter;
    private PlayerViewModel viewModel;

    public static TopPlayersFragmet newInstance(String category) {

        Bundle args = new Bundle();
        args.putString("category", category);
        TopPlayersFragmet fragment = new TopPlayersFragmet();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game=getArguments().getString("category");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.recycler_view_fragmet, container, false);
        recyclerView=view.findViewById(R.id.recycler_view);
        adapter=new PlayerAdapter();
        viewModel= ViewModelProviders.of(this).get(PlayerViewModel.class);
        viewModel.getPlayersByGame(game).observe(this, list->adapter.setList(list));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
}
