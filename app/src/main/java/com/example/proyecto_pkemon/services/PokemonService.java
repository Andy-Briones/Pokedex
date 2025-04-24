package com.example.proyecto_pkemon.services;

import com.example.proyecto_pkemon.entities.Pokemon;
import com.example.proyecto_pkemon.entities.PokemonResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PokemonService {
    //https://pokeapi.co/api/v2/pokemon
    //api/v2/pokemon

//    @GET("/pokemon")
//    Call<List<pokemon>> getColors(@Query("limit") int limit, @Query("page") int page);
    @GET("/api/v2/pokemon")
    Call<PokemonResponse> getPokemon();
//    @GET("/api/v2/pokemon")
//    Call<PokemonResponse> getPokemon(@Query("limit") int limit, @Query("page") int page);

}
