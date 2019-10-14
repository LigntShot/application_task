package com.application_task;

/* Using GSON, since we work with a lot of small files */
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

import java.io.*;

public class id_scrambler {

    public static void main(String[] args) {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Group.class, new JsonParser());
        Gson gson = builder.create();

        String inputPath = args[0];

//        BufferedReader br;

        try {
            /* Wrapping one InputStream into the other */
            FileInputStream in = new FileInputStream(inputPath);
            BZip2CompressorInputStream bzIn = new BZip2CompressorInputStream(in);
            TarArchiveInputStream tarIn = new TarArchiveInputStream(bzIn);
            ArchiveEntry entry = null;

            while((entry = tarIn.getNextEntry()) != null)
            {
                if (entry.getSize() < 1) continue;
                Group currentGroup = gson.fromJson(entry.getName(),Group.class);

            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("Undefined exception: " + e.getMessage());
        }


    }
}
