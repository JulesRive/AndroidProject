package com.example.androidproject.data;

import com.example.androidproject.Presentation.Model.Pokemon;
import com.example.androidproject.Presentation.Model.RestPokemonResponse;

import java.util.List;

public interface PokeCallback {
    void onSuccess(List<Pokemon> response);
    void onFailed();
}
