package com.example.proyecto_pkemon;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_pkemon.adapters.PokemonAdapter;
import com.example.proyecto_pkemon.entities.Pokemon;
import com.example.proyecto_pkemon.entities.PokemonResponse;
import com.example.proyecto_pkemon.services.PokemonService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonActivity extends AppCompatActivity {

    RecyclerView rvPokemon;
    boolean isLoading = false;
    boolean isLastPage = false;
    List<Pokemon> data = new ArrayList<>();
    PokemonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pokemon);


        Toast.makeText(getApplicationContext(),"PokemonActivity onCreate", Toast.LENGTH_SHORT).show();

        rvPokemon = findViewById(R.id.rvPokemon);
        rvPokemon.setLayoutManager(new LinearLayoutManager(this));

        setUpReciclerView();
    }
    protected void onResume()
    {
        super.onResume();

        Toast.makeText(getApplicationContext(), "PokemonActivity OnResume", Toast.LENGTH_LONG).show();

        data.clear();
        adapter.notifyDataSetChanged(); //notifica al adapter que los datos han cambiado

        loadMorePokemons();
    }

    private void loadMorePokemons()
    {
        isLoading = true;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PokemonService service = retrofit.create(PokemonService.class);
        service.getPokemon().enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                isLoading = false;

                if (!response.isSuccessful()) return;
                if (response.body() == null) return;
                if (response.body().results.isEmpty())
                {
                    isLastPage = true;
                    return;
                }

                data.addAll(response.body().results);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable throwable) {
                isLoading = false;
                Toast.makeText(getApplicationContext(), "Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setUpReciclerView()
    {
        adapter = new PokemonAdapter(data);
        rvPokemon.setAdapter(adapter);

        //Scroll Listener permite detectar cuando el usuario hace scroll y llega al final de la lista
        //para cargar mas datos
        rvPokemon.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager == null) return;

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!isLoading && !isLastPage)
                {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0)
                    {
                        loadMorePokemons();
                    }
                }
            }
        });
    }
}