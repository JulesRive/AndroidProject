package com.example.androidproject.data;

import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.androidproject.Constants;
import com.example.androidproject.Presentation.Model.Pokemon;
import com.example.androidproject.Presentation.Model.RestPokemonResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.Toast.makeText;

public class PokeRepository {

    private PokeApi pokeApi;
    private SharedPreferences sharedPreferences;
    private Gson gson;
    public PokeRepository(PokeApi pokeApi, SharedPreferences sharedPreferences, Gson gson) {
        this.pokeApi = pokeApi;
        this.sharedPreferences = sharedPreferences;
        this.gson = gson;
    }

    public void getPokemonResponse(final PokeCallback callback) {
        List<Pokemon> list = getDataFromCache();
        if(list != null) {
            callback.onSuccess(list);
        } else {
            pokeApi.getPokemonResponse().enqueue(new Callback<RestPokemonResponse>() {
                @Override
                public void onResponse(Call<RestPokemonResponse> call, Response<RestPokemonResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onSuccess(response.body().getResults());
                    } else {
                        callback.onFailed();
                    }
                }

                @Override
                public void onFailure(Call<RestPokemonResponse> call, Throwable t) {
                    callback.onFailed();
                }
            });
        };
    }
    private void saveList(List<Pokemon> pokemonList) {
        String jsonString = gson.toJson(pokemonList);
        sharedPreferences
                .edit()
                .putString(Constants.KEY_POKEMONLIST, jsonString)
                .apply();

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
