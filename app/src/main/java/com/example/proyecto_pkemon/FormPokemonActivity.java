package com.example.proyecto_pkemon;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_pkemon.entities.Pokemon;
import com.example.proyecto_pkemon.entities.PokemonResponse;
import com.example.proyecto_pkemon.services.PokemonService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FormPokemonActivity extends AppCompatActivity {

    PokemonService service;
    Button btnAtras;
    TextView tvNombre;
    TextView tvDesc;
    ImageView imagenPK;
    int pokeID=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_pokemon);

        setUpPokemons();
        setUpViews();

    }
    private void setUpViews()
    {
        btnAtras = findViewById(R.id.btnAtras);
        tvNombre = findViewById(R.id.tvNombre);
        tvDesc = findViewById(R.id.tvDescripcion);
        imagenPK = findViewById(R.id.imagenPokemon);
    }
    private void setButtonAtras()
    {

    }
    private void setUpPokemons()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/pokemon").addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(PokemonService.class);

        Intent intent = getIntent();
        pokeID = intent.getIntExtra("pokeid",0);
        String nombrePokemon = intent.getStringExtra("namePokemon");

        tvNombre.setText(nombrePokemon);
//        service.getPokemon(pokeID).enqueue(new Callback<Pokemon>() {
//            @Override
//            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<Pokemon> call, Throwable throwable) {
//
//            }
//        });
    }
}