package com.pdm00057616.gamenews.API.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.pdm00057616.gamenews.models.Login;

import java.lang.reflect.Type;

public class TokenDeserializer implements JsonDeserializer<Login> {
    @Override
    public Login deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Login aux=new Login();
        if(json.getAsJsonObject()!=null){
            JsonObject tokenJsonObject=json.getAsJsonObject();
            if (tokenJsonObject.get("token")!=null) {
                aux.setToken(tokenJsonObject.get("token").getAsString());
                aux.setOKResponse(true);
            }else{
                aux.setOKResponse(false);
                aux.setToken(tokenJsonObject.get("message").getAsString());
            }
        }
        return aux;
    }
}
