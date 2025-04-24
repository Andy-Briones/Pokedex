package com.example.proyecto_pkemon.adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_pkemon.FormPokemonActivity;
import com.example.proyecto_pkemon.R;
import com.example.proyecto_pkemon.entities.Pokemon;

import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.BasicViewHolder>
{
    String url;
    String idPK;
    String imagenUrl;
    private List<Pokemon>data;
    public PokemonAdapter(List<Pokemon>data){this.data=data;}

    @NonNull
    @Override
    public BasicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pokemon, parent, false);

        return new BasicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BasicViewHolder holder, int position) {
        Pokemon pokemon = data.get(position);

        TextView tvnomPok = holder.itemView.findViewById(R.id.tvNomPokemon);
        TextView tvurlPok = holder.itemView.findViewById(R.id.tvUrlPokemon);
        ImageView vPokemonBg = holder.itemView.findViewById(R.id.imgPokemonBg);

        tvnomPok.setText(pokemon.name);

        vPokemonBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), FormPokemonActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        //https://pokeapi.co/api/v2/pokemon/1
        url = pokemon.url;
        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }
        idPK = url.substring(url.lastIndexOf("/") + 1);

        imagenUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + idPK + ".png";

        Glide.with(holder.itemView.getContext())
                .load(imagenUrl)
                .into(vPokemonBg);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class BasicViewHolder extends RecyclerView.ViewHolder
    {
        public BasicViewHolder(@NonNull View itemView){super(itemView);}
    }
}
