package com.example.androidproject.Presentation.Controller;

import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.androidproject.Constants;
import com.example.androidproject.Singletons;
import com.example.androidproject.Presentation.Model.Pokemon;
import com.example.androidproject.Presentation.Model.RestPokemonResponse;
import com.example.androidproject.Presentation.View.MainActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.Toast.makeText;

public class MainController {
    private SharedPreferences sharedPreferences;
    private Gson gson;
    private MainActivity view;

    public MainController(MainActivity mainActivity, Gson gson, SharedPreferences sharedPreferences) {
        this.view = mainActivity;
        this.gson = gson;
        this.sharedPreferences = sharedPreferences;
    }
    public void onStart()  {

        List<Pokemon> pokemonList = getDataFromCache();
        if(pokemonList != null) {
            view.showList(pokemonList);
        } else {
            makeApiCall();
        }
    }
    public void onItemClick(Pokemon pokemon) {

    }
    private void makeApiCall() {

        Call<RestPokemonResponse> call = Singletons.getPokeApi().getPokemonResponse();
        call.enqueue(new Callback<RestPokemonResponse>() {
            @Override
            public void onResponse(Call<RestPokemonResponse> call, Response<RestPokemonResponse> response) {
                if(response.isSuccessful() && response.body() != null) {
                    List<Pokemon> pokemonList = response.body().getResults();
                    saveList(pokemonList);
                    view.showList(pokemonList);
                } else {
                    view.showError();
                }
            }

            @Override
            public void onFailure(Call<RestPokemonResponse> call, Throwable t) {
                view.showError();
            }
        });
    }

    private void saveList(List<Pokemon> pokemonList) {
        String jsonString = gson.toJson(pokemonList);
        sharedPreferences
                .edit()
                .putString(Constants.KEY_POKEMONLIST, jsonString)
                .apply();
        makeText(view.getApplicationContext(), "List saved !", Toast.LENGTH_SHORT).show();

    }
    private List<Pokemon> getDataFromCache() {
        String jsonPokemon = sharedPreferences.getString(Constants.KEY_POKEMONLIST, null);
        if (jsonPokemon==null) {
            return null;
        }
        Type listType = new TypeToken<List<Pokemon>>(){}.getType();
        return gson.fromJson(jsonPokemon,listType);

    }

}
