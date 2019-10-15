package com.application_task;

/* Using GSON, since we work with a lot of small files */
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

import java.io.*;
import java.util.*;

public class id_scrambler {

    public static void main(String[] args) {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Group.class, new JsonParser());
        Gson gson = builder.create();

        String inputPath = args[0];
        BidiMap<Integer, Integer> usrDict = new DualHashBidiMap<Integer, Integer>();

        TarArchiveInputStream tarIn;

        try {
            /* Wrapping one InputStream into the other */
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
            while((entry = tarIn.getNextEntry()) != null)
            {
                if (entry.getSize() < 1) continue;

                Group currentGroup = gson.fromJson(entry.getName(),Group.class);
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

        int resultValue = Collections.max(usrDict.values());
        int resultUid = usrDict.getKey(resultValue);
        System.out.println(resultUid);

        try {
            tarIn.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }
}
