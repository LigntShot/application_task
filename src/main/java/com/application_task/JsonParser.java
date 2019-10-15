package com.application_task;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Класс JSON-парсера
 * @author Шаблонов Денис
 * @version 1.0
 */
public class JsonParser implements JsonDeserializer<Group> {
    /**
     * Кастомная перегрузка метода десериализации
     * @param json Ссылкак на JSON-объект
     * @param type Не используется
     * @param context Не используется
     * @return Объект группы соц. сети
     * @throws JsonParseException Исключение парсинга
     */
    public Group deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        int gid = obj.get("gid").getAsInt();

        /* Необходимо достать uids как массив JSON */
        JsonArray jsonArr = obj.get("uids").getAsJsonArray();
        /* Преобразовываем его в массив int'ов */
        int[] destArr = new int[jsonArr.size()];
        for (int i = 0; i < jsonArr.size(); i++) {
            destArr[i] = jsonArr.get(i).getAsInt();
        }

        return new Group(gid, destArr);
    }
}
