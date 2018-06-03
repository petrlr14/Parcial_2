package com.pdm00057616.gamenews.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pdm00057616.gamenews.API.GameNewsAPI;
import com.pdm00057616.gamenews.R;
import com.pdm00057616.gamenews.adapters.AllNewsAdapter;
import com.pdm00057616.gamenews.database.entities_models.NewEntity;
import com.pdm00057616.gamenews.models.New;
import com.pdm00057616.gamenews.viewmodels.NewsViewModel;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = getActivity().getSharedPreferences("log", Context.MODE_PRIVATE);
        aux = preferences.getString("token", "");
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

}
