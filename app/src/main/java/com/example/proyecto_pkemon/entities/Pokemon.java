package com.example.proyecto_pkemon.entities;

import com.google.gson.annotations.SerializedName;

public class Pokemon
{
    public int id;
    public String name;
    public String url;
    public Pokemon() {}

    public Pokemon(String name,String url)
    {
        this.name=name;
        this.url=url;
    }
}
