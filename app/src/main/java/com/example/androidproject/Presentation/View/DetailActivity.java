package com.example.androidproject.Presentation.View;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidproject.Constants;
import com.example.androidproject.Presentation.Model.Pokemon;
import com.example.androidproject.R;
import com.example.androidproject.Singletons;

public class DetailActivity extends AppCompatActivity {

    private TextView textDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        textDetail = findViewById(R.id.detail_txt);
        Intent intent = getIntent();
        String pokemonJson = intent.getStringExtra(Constants.KEY_POKEMON);
        Pokemon pokemon = Singletons.getGson().fromJson(pokemonJson, Pokemon.class);
        showDetail(pokemon);
    }

    private void showDetail(Pokemon pokemon) {
        textDetail.setText(pokemon.getName());
    }
}


