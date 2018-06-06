package com.pdm00057616.gamenews.fragments;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pdm00057616.gamenews.API.ClientRequest;
import com.pdm00057616.gamenews.R;
import com.pdm00057616.gamenews.adapters.PlayerAdapter;
import com.pdm00057616.gamenews.viewmodels.PlayerViewModel;

public class TopPlayersFragmet extends Fragment{

    private RecyclerView recyclerView;
    private String game, token;
    private PlayerAdapter adapter;
    private PlayerViewModel viewModel;
    private SwipeRefreshLayout refreshLayout;
    private Activity activity;

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
        SharedPreferences preferences = getActivity().getSharedPreferences("log", Context.MODE_PRIVATE);
        token= preferences.getString("token", "");
        activity=getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.recycler_view_fragmet, container, false);
        recyclerView=view.findViewById(R.id.recycler_view);
        refreshLayout=view.findViewById(R.id.refresh);
        adapter=new PlayerAdapter();
        viewModel= ViewModelProviders.of(this).get(PlayerViewModel.class);
        viewModel.getPlayersByGame(game).observe(this, list->adapter.setList(list));
        refreshLayout.setOnRefreshListener(()-> ClientRequest.getPlayers(token, viewModel, refreshLayout));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
}
