package com.github.mazzeb.gradle.autoversion;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class VersionSerializer implements JsonSerializer<Version> {

    public static final String MAJOR = "major";
    public static final String MINOR = "minor";
    public static final String PATCH = "patch";
    public static final String SNAPSHOT = "snapshot";

    @Override
    public JsonElement serialize(Version version,
                                 Type type,
                                 JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(MAJOR, version.getMajor());
        jsonObject.addProperty(MINOR, version.getMinor());
        jsonObject.addProperty(PATCH, version.getPatch());
        jsonObject.addProperty(SNAPSHOT, version.isSnapshot());
        return jsonObject;
    }
}
