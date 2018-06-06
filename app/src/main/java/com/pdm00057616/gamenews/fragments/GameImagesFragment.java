package com.pdm00057616.gamenews.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pdm00057616.gamenews.R;
import com.pdm00057616.gamenews.adapters.ImagesAdapter;
import com.pdm00057616.gamenews.viewmodels.NewsViewModel;

public class GameImagesFragment extends Fragment {

    private RecyclerView recyclerView;
    private ImagesAdapter adapter;
    private NewsViewModel viewModel;
    private String category;

    public static GameImagesFragment newInstance(String category) {

        Bundle args = new Bundle();
        args.putString("category", category);
        GameImagesFragment fragment = new GameImagesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        category = getArguments().getString("category");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view_fragmet, container, false);
        adapter = new ImagesAdapter();
        viewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        viewModel.getNewsByGame(category).observe(this, list -> adapter.setNewsList(list));
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        return view;
    }


}
