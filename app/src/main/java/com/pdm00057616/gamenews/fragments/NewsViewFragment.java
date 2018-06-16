package com.pdm00057616.gamenews.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
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
import com.pdm00057616.gamenews.activities.DetailsActivity;
import com.pdm00057616.gamenews.adapters.AllNewsAdapter;
import com.pdm00057616.gamenews.database.entities_models.NewEntity;
import com.pdm00057616.gamenews.viewmodels.NewsViewModel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class NewsViewFragment extends Fragment {


    private static final int ALL=0;
    private static final int CATEGORIES=1;
    private static final int FAV=2;


    private RecyclerView recyclerView;
    private AllNewsAdapter adapter;
    private GridLayoutManager manager;
    private String aux, category;
    private NewsViewModel newsViewModel;

    private SearchView searchView;
    private SwipeRefreshLayout refreshLayout;
    private int fragmentType;

    private Context context;

    private TextView noFavorites;

    public static NewsViewFragment newInstance(int tipo,String ...categories) {

        Bundle args = new Bundle();
        args.putInt("fragmentType", tipo);
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
        fragmentType =getArguments().getInt("fragmentType");
        if(getArguments().getString("category")!=null){
            category=getArguments().getString("category");
        }
        context=getContext();
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view_fragmet, container, false);
        refreshLayout=view.findViewById(R.id.refresh);
        noFavorites=view.findViewById(R.id.no_favorites);
        refreshLayout.setOnRefreshListener(()->ClientRequest.fetchAllNews(getContext(), newsViewModel, aux,refreshLayout));
        recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new AllNewsAdapter() {
            @Override
            public void onclickFav(View v, String id, int current) {
                addFav(id, v, current);
            }
            @Override
            public void onClickDetails(String titulo, String descripcion, String contenido, String image) {
                startDetails(titulo, descripcion, contenido, image);
            }
        };
        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        newsViewModel.getAllNews().observe(this, this::setList);
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
        newsViewModel.getNewsByQuery(query)
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

    private void setList(List<NewEntity> news){
        if(fragmentType==FAV){
            List<NewEntity>favs=new ArrayList<>();
            for(NewEntity x:news){
                if(x.getIsFav()==1){
                    favs.add(x);
                }
            }
            adapter.setNewList(favs);
            if(favs.size()==0){
                noFavorites.setVisibility(View.VISIBLE);
            }else{
                noFavorites.setVisibility(View.GONE);
            }
        }else if(fragmentType==CATEGORIES){
            List<NewEntity>category=new ArrayList<>();
            for (NewEntity newEntity : news) {
                if (newEntity.getGame().equals(this.category)){
                    category.add(newEntity);
                }
            }
            adapter.setNewList(category);
        }else{
            adapter.setNewList(news);
        }
    }

    private void addFav(String id, View view, int current){
        newsViewModel.update(((current==0)?1:0), id, aux);
    }

    private void startDetails(String titulo, String descripcion, String contenido, String image){
        Intent intent=new Intent(getContext(), DetailsActivity.class);
        intent.putExtra("titulo", titulo);
        intent.putExtra("descripcion", descripcion);
        intent.putExtra("contenido", contenido);
        intent.putExtra("image", image);
        context.startActivity(intent);
    }
}

