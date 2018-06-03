package com.pdm00057616.gamenews.fragments;

import android.app.Activity;
import android.content.Context;
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

public class IndividualGameFragment extends Fragment{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    /*private ViewPagerAdapter adapter;
    private CategoryViewModel categoryViewModel;*/
    private Context context;
    private Activity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.individual_game_fragment, container, false);

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
