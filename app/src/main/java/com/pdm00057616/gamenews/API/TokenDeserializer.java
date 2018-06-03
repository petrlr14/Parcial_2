package com.pdm00057616.gamenews.API;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class TokenDeserializer implements JsonDeserializer<String> {
    @Override
    public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String token="";
        System.out.println(json.getAsJsonObject());
        if(json.getAsJsonObject()!=null){
            JsonObject tokenJsonObject=json.getAsJsonObject();
            token=tokenJsonObject.get("token").getAsString();
        }
        return token;
    }
}
