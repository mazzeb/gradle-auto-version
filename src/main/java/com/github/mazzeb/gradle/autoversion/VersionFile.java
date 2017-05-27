package com.github.mazzeb.gradle.autoversion;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.gradle.api.GradleException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class VersionFile {

    private final Gson GSON;
    private final String fileName;

    private VersionFile(String fileName) {
        this.fileName = fileName;
        GSON = new GsonBuilder()
                .registerTypeAdapter(Version.class, new VersionSerializer())
                .registerTypeAdapter(Version.class, new VersionDeserializer())
                .setPrettyPrinting()
                .create();
    }

    public static VersionFile openVersionFile(String fileName) {
        return new VersionFile(fileName);
    }

    public Version readFromFile() {
        try (JsonReader jsonReader = GSON.newJsonReader(new FileReader(fileName))) {
            return GSON.fromJson(jsonReader, Version.class);
        } catch (IOException e) {
            throw new GradleException(e.getMessage());
        }
    }

    public void saveToFile(Version version) {
        try (JsonWriter writer = GSON.newJsonWriter(new FileWriter(fileName))) {
            GSON.toJson(version, Version.class, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
