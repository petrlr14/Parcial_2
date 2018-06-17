package com.pdm00057616.gamenews.API.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.pdm00057616.gamenews.models.New;

import java.lang.reflect.Type;

public class NewsDeserializer implements JsonDeserializer<New> {

    @Override
    public New deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        New news = new New();

        JsonObject newsJsonObject = json.getAsJsonObject();
        news.set_id(newsJsonObject.get("_id").getAsString());
        news.setTitle(newsJsonObject.get("title").getAsString());
        news.setBody(newsJsonObject.get("body").getAsString());
        news.setGame(newsJsonObject.get("game").getAsString());
        news.setCreated_date(newsJsonObject.get("created_date").getAsString());

        news.setCover_image(newsJsonObject.get("coverImage").getAsString());
        news.setDescription(newsJsonObject.get("description").getAsString());
        return news;
    }
}
