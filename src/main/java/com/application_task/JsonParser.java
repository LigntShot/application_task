package com.application_task;

import com.google.gson.*;

import java.lang.reflect.Type;

public class JsonParser implements JsonDeserializer<Group> {
    public Group deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        int gid = obj.get("gid").getAsInt();

        JsonArray jsonArr = obj.get("uids").getAsJsonArray();
        int[] destArr = new int[jsonArr.size()];
        for (int i = 0; i < jsonArr.size(); i++) {
            destArr[i] = jsonArr.get(i).getAsInt();
        }

        return new Group(gid, destArr);
    }
}
