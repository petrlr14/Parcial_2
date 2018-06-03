package com.pdm00057616.gamenews.API;

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
        user.setDate(userObject.get("crated_date").getAsString());
        user.setV(userObject.get("__v").getAsInt());

        JsonElement newsJsonElement=userObject.get("favoriteNews");
        JsonArray newsJsonArray=newsJsonElement.getAsJsonArray();
        user.setFavNews(getFavNews(newsJsonArray));
        return user;
    }

    private List<New> getFavNews(JsonArray array){
        List<New> newsList=new ArrayList<>();
        for(JsonElement x:array){
            New news=new New();
            news.set_id(x.getAsJsonObject().get("_id").getAsString());
            news.setTitle(x.getAsJsonObject().get("title").getAsString());
            news.setBody(x.getAsJsonObject().get("body").getAsString());
            news.setGame(x.getAsJsonObject().get("game").getAsString());
            news.setCreated_date(x.getAsJsonObject().get("created_date").getAsString());
            news.set__v(x.getAsJsonObject().get("__v").getAsInt());
            newsList.add(news);
        }
        return newsList;
    }
}
