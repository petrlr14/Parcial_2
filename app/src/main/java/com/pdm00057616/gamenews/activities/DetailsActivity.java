package com.pdm00057616.gamenews.activities;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.pdm00057616.gamenews.R;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    String title, descipcion, contenido, image;
    TextView new_title, new_description, new_contenido;
    ImageView imageView;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        if (bundle!=null) {
            getInfo(bundle);
        }
        bindViews();
        setInfo();
    }

    private void bindViews(){
        new_title=findViewById(R.id.new_title);
        new_description=findViewById(R.id.new_description);
        new_contenido=findViewById(R.id.news_content);
        imageView=findViewById(R.id.news_image);
        collapsingToolbarLayout=findViewById(R.id.collapsing_toolbar);
        toolbar=findViewById(R.id.toolbar);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
    }

    private void getInfo(Bundle bundle){
        title=bundle.getString(getString(R.string.title));
        descipcion=bundle.getString(getString(R.string.description));
        contenido=bundle.getString(getString(R.string.content));
        image=bundle.getString(getString(R.string.image));
    }

    private void setInfo(){
        new_title.setText(title);
        new_description.setText(descipcion);
        new_contenido.setText(contenido);
        Picasso.get().load(image).into(imageView);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return true;
    }
}
