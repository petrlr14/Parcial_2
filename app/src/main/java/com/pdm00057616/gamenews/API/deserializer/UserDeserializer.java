package com.pdm00057616.gamenews.API.deserializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.pdm00057616.gamenews.models.New;
import com.pdm00057616.gamenews.models.User;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UserDeserializer implements JsonDeserializer<User>{
    @Override
    public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        User user=new User();
        JsonObject userObject=json.getAsJsonObject();
        user.setId(userObject.get("_id").getAsString());
        user.setUser(userObject.get("user").getAsString());
        user.setPassword(userObject.get("password").getAsString());
        if (userObject.get("avatar")!=null) {
            user.setAvatar(userObject.get("avatar").getAsString());
        }
        JsonArray newsJsonArray=userObject.get("favoriteNews").getAsJsonArray();
        user.setFavNews(getFavNews(newsJsonArray));
        return user;
    }

    private List<String> getFavNews(JsonArray array){
        List<String> newsList=new ArrayList<>();
        for(JsonElement x:array){
                newsList.add(x.getAsString());
        }
        return newsList;
    }
}
