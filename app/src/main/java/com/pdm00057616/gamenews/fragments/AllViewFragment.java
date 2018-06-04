package com.pdm00057616.gamenews.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pdm00057616.gamenews.API.GameNewsAPI;
import com.pdm00057616.gamenews.R;
import com.pdm00057616.gamenews.adapters.AllNewsAdapter;
import com.pdm00057616.gamenews.database.entities_models.NewEntity;
import com.pdm00057616.gamenews.models.New;
import com.pdm00057616.gamenews.viewmodels.NewsViewModel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AllViewFragment extends Fragment {

    private RecyclerView recyclerView;
    private AllNewsAdapter adapter;
    private GridLayoutManager manager;
    String aux;
    NewsViewModel viewModel;
    private SearchView searchView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = getActivity().getSharedPreferences("log", Context.MODE_PRIVATE);
        aux = preferences.getString("token", "");
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_news_fragment, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new AllNewsAdapter(getContext());
        viewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        viewModel.getAllNews().observe(this, news -> adapter.setNewList(news));
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
        init();
        return view;
    }

    private void init() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://gamenewsuca.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
        GameNewsAPI service = retrofit.create(GameNewsAPI.class);
        Call<List<New>> news = service.getNews("Beared " + aux);
        news.enqueue(new Callback<List<New>>() {
            @Override
            public void onResponse(Call<List<New>> call, Response<List<New>> response) {
                if (response.isSuccessful()) {
                    setListNewEntity(response.body());
                    Toast.makeText(getContext(), "Fetching data", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<List<New>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setListNewEntity(List<New> list) {
        List<NewEntity> entities = new ArrayList<>();
        for (New x : list) {
            NewEntity newEntity = new NewEntity(
                    x.get_id(), x.getTitle(), x.getCoverImage(), x.getDescription(),
                    x.getBody(), x.getGame(), x.getCreated_date()
            );
            viewModel.insert(newEntity);
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
                    adapter.setNewList(newEntities);
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

