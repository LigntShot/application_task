package com.application_task;

/* Используем GSON, так как работаем с большим количеством мелких файлов */
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
/* Распаковщики Apache */
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
/* Компонент Apache Commons/IO */
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.*;

/**
 * Программа, выполняющее тестовое задание
 * @author Шаблонов Денис
 * @version 1.0
 */
public class id_scrambler {

    public static void main(String[] args) {

        /* Делаем кастомный парсер с кастомным методом десериазилации */
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Group.class, new JsonParser());
        Gson gson = builder.create();

        /* Читаем аргумент - путь к архиву */
        String inputPath = args[0];

        /* Словарь, хранящий значения  <uid, numOccur>, где numOccur - количество совпадений */
        /* Словарь, так как с ним проще избежать повтроений ключей (uid'ов) */
        HashMap<Integer, Integer> usrDict = new HashMap<Integer, Integer>();

        /* Поток распакованного файла */
        TarArchiveInputStream tarIn;

        try {
            /* Обертываем один поток в другой, чтобы достать JSON'ы */
            FileInputStream in = new FileInputStream(inputPath);
            BZip2CompressorInputStream bzIn = new BZip2CompressorInputStream(in);
            tarIn = new TarArchiveInputStream(bzIn);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
            return;
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("Undefined exception: " + e.getMessage());
            return;
        }

        ArchiveEntry entry = null;

        try {
            /* Пока есть что читать */
            while((entry = tarIn.getNextEntry()) != null)
            {
                /* Пропустить "пустышку" */
                if (entry.getSize() < 1) continue;

                /* Читаем JSON в строку, используя Apache Commons/IO */
                String json = IOUtils.toString(tarIn, "UTF-8");
                /* Десериализуем */
                Group currentGroup = gson.fromJson(json, Group.class);
                /* Забиваем uid'ы в словарь */
                int[] currentUids = currentGroup.uids;
                int uid;
                for (int currentUid : currentUids) {
                    uid = currentUid;
                    if (!usrDict.containsKey(uid)) {
                        usrDict.put(uid, 1);
                    } else {
                        usrDict.put(uid, usrDict.get(uid) + 1);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
            return;
        }

        /*
         * Среди значений (количество совпадений) находим максимальное
         */
        Integer resultValue = Collections.max(usrDict.values());
        Integer resultUid = -1;

        /* Ищем ключ по значению */
        Set<Map.Entry<Integer,Integer>> entrySet = usrDict.entrySet();
        for (Map.Entry<Integer,Integer> pair : entrySet) {
            if (resultValue.equals(pair.getValue())) {
                resultUid =  pair.getKey();// нашли наше значение и возвращаем  ключ
            }
        }
        if (resultUid == -1) {
            System.err.println("No UID was found");
            return;
        }
        System.out.println(resultUid);

        try {
            tarIn.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }
}
