package com.pdm00057616.gamenews.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pdm00057616.gamenews.R;
import com.pdm00057616.gamenews.adapters.GameViewPagerAdapter;

public class IndividualGameFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private GameViewPagerAdapter adapter;
    private String category;

    public static IndividualGameFragment newInstance(String category) {

        Bundle args = new Bundle();
        args.putString("category", category);
        IndividualGameFragment fragment = new IndividualGameFragment();
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
        View view = inflater.inflate(R.layout.individual_game_fragment, container, false);
        adapter = new GameViewPagerAdapter(getChildFragmentManager());
        viewPager = view.findViewById(R.id.view_pager);
        tabLayout = view.findViewById(R.id.tab_layout);
        setFragments();
        return view;
    }

    private void setFragments() {
        adapter.addFragment(NewsViewFragment.newInstance(1, category), "News");
        adapter.addFragment(TopPlayersFragmet.newInstance(category), "Top Players");
        adapter.addFragment(GameImagesFragment.newInstance(category), "Images");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
