package com.example.androidproject.Presentation.Controller;

import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.androidproject.Constants;
import com.example.androidproject.Singletons;
import com.example.androidproject.Presentation.Model.Pokemon;
import com.example.androidproject.Presentation.Model.RestPokemonResponse;
import com.example.androidproject.Presentation.View.MainActivity;
import com.example.androidproject.data.PokeCallback;
import com.example.androidproject.data.PokeRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.Toast.makeText;

public class MainController {

    private final PokeRepository pokeRepository;
    private MainActivity view;

    public MainController(MainActivity mainActivity, PokeRepository pokeRepository) {
        this.pokeRepository = pokeRepository;
        this.view = mainActivity;
    }
    public void onStart()  {
        pokeRepository.getPokemonResponse(new PokeCallback() {
            @Override
            public void onSuccess(List<Pokemon> response) {
                view.showList(response);
            }

            @Override
            public void onFailed() {
                view.showError();
            }
        });
    }
    public void onItemClick(Pokemon pokemon) {
        view.navigateToDetails(pokemon);
    }
}
