package com.pdm00057616.gamenews.API.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class PushFav implements JsonDeserializer<String> {
    @Override
    public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return json.getAsJsonObject().get("success").getAsString();
    }
}
