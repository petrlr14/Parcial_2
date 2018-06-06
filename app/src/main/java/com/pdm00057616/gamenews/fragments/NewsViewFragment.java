package com.pdm00057616.gamenews.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.pdm00057616.gamenews.API.ClientRequest;
import com.pdm00057616.gamenews.R;
import com.pdm00057616.gamenews.adapters.AllNewsAdapter;
import com.pdm00057616.gamenews.database.entities_models.NewEntity;
import com.pdm00057616.gamenews.viewmodels.NewsViewModel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class NewsViewFragment extends Fragment {

    private RecyclerView recyclerView;
    private AllNewsAdapter adapter;
    private GridLayoutManager manager;
    private String aux, category;
    private NewsViewModel viewModel;
    private SearchView searchView;
    private SwipeRefreshLayout refreshLayout;
    private boolean isCategory;

    public static NewsViewFragment newInstance(boolean category, String ...categories) {

        Bundle args = new Bundle();
        args.putBoolean("isCategory", category);
        System.out.println(categories.length+"asdsadsadsad");
        if (categories.length>0) {
            args.putString("category", categories[0]);
        }
        NewsViewFragment fragment = new NewsViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = getActivity().getSharedPreferences("log", Context.MODE_PRIVATE);
        aux = preferences.getString("token", "");
        setHasOptionsMenu(true);
        isCategory=getArguments().getBoolean("isCategory");
        if(getArguments().getString("category")!=null){
            category=getArguments().getString("category");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view_fragmet, container, false);
        refreshLayout=view.findViewById(R.id.refresh);
        System.out.println(refreshLayout);
        refreshLayout.setOnRefreshListener(()->ClientRequest.fetchAllNews(getContext(), viewModel, aux,refreshLayout));
        recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new AllNewsAdapter();
        viewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        viewModel.getAllNews().observe(this, this::setList);
        manager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL,
                false);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (position % 3 == 0 ? 2 : 1);
            }
        });
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void setList(List<NewEntity> list){
        if (isCategory) {
            List<NewEntity> aux=new ArrayList<>();
            for(NewEntity x:list){
                System.out.println(x.getGame()+"---"+category);
                if(x.getGame().equals(category)){
                    aux.add(x);
                }
            }
            adapter.setNewList(aux);
        }else{
            adapter.setNewList(list);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_view_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView = (SearchView) item.getActionView();
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(onQueryTextListener);
        setUpSearchView();

    }


    private SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            getNewsFromDB(query);
            searchView.clearFocus();
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            getNewsFromDB(newText);
            return true;
        }
    };

    private void getNewsFromDB(String query) {
        query = "%" + query + "%";
        viewModel.getNewsByQuery(query)
                .observe(this, newEntities -> {
                    if (newEntities == null) {
                        return;
                    }
                    setList(newEntities);
                });
    }

    private void setUpSearchView() {
        EditText txtSearch = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        txtSearch.setHint("Search...");
        txtSearch.setHintTextColor(Color.DKGRAY);
        txtSearch.setTextColor(getResources().getColor(R.color.white));
        AutoCompleteTextView searchTextView = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        try {
            Field mCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            mCursorDrawableRes.setAccessible(true);
            mCursorDrawableRes.set(searchTextView, 0); //This sets the cursor resource ID to 0 or @null which will make it visible on white background
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
