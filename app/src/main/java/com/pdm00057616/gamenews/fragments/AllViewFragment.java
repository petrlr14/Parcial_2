package com.pdm00057616.gamenews.fragments;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.TextView;

import com.google.gson.Gson;
import com.pdm00057616.gamenews.API.GameNewsAPI;
import com.pdm00057616.gamenews.R;
import com.pdm00057616.gamenews.adapters.AllNewsAdapter;
import com.pdm00057616.gamenews.models.New;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AllViewFragment extends Fragment {

    private RecyclerView recyclerView;
    private AllNewsAdapter adapter;
    private GridLayoutManager manager;

    private TextView textView;
    List<New> newList = new ArrayList<>();
    String aux;

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
        textView = new TextView(getContext());
        recyclerView = view.findViewById(R.id.recycler_view);
        System.out.println(newList.size());
        adapter = new AllNewsAdapter(getContext());
        manager =new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL,
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

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request request = original.newBuilder()
                                .header("Authorization", "Bearer " + aux)
                                .build();
                        return chain.proceed(request);
                    }
                }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://gamenewsuca.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .client(client)
                .build();
        GameNewsAPI service = retrofit.create(GameNewsAPI.class);
        Call<List<New>> news = service.getNews();
        news.enqueue(new Callback<List<New>>() {
            @Override
            public void onResponse(Call<List<New>> call, Response<List<New>> response) {
                adapter.setNewList(response.body());
            }

            @Override
            public void onFailure(Call<List<New>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


}
