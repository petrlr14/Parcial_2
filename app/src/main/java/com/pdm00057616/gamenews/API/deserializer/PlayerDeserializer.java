package com.pdm00057616.gamenews.API.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.pdm00057616.gamenews.models.Player;

import java.lang.reflect.Type;

public class PlayerDeserializer implements JsonDeserializer<Player> {
    @Override
    public Player deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Player player = new Player();
        if (json.getAsJsonObject() != null) {
            JsonObject array = json.getAsJsonObject();
            player.setGame(array.get("game").getAsString());
            player.set_id(array.get("_id").getAsString());
            player.setAvatar(array.get("avatar").getAsString());
            player.setBiografia(array.get("biografia").getAsString());
            player.setName(array.get("game").getAsString());
        }
        return player;
    }
}
