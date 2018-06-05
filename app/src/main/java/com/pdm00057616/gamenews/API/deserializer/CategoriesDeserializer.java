package com.pdm00057616.gamenews.API.deserializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CategoriesDeserializer implements JsonDeserializer<List<String>>{
    @Override
    public List<String> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List<String> categories=new ArrayList<>();
        JsonArray array=json.getAsJsonArray();
        for(JsonElement x:array){
            categories.add(x.getAsString());
        }
        return categories;
    }
}
