package com.github.mazzeb.gradle.autoversion;


import com.google.gson.*;

import java.lang.reflect.Type;

import static com.github.mazzeb.gradle.autoversion.Version.versionBuilder;
import static com.github.mazzeb.gradle.autoversion.VersionSerializer.*;

public class VersionDeserializer implements JsonDeserializer<Version> {

    @Override
    public Version deserialize(JsonElement jsonElement,
                               Type type,
                               JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        return versionBuilder()
                .withMajor(safeGetLong(jsonObject.getAsJsonPrimitive(MAJOR)))
                .withMinor(safeGetLong(jsonObject.getAsJsonPrimitive(MINOR)))
                .withPatch(safeGetLong(jsonObject.getAsJsonPrimitive(PATCH)))
                .withLabel(safeGetString(jsonObject.getAsJsonPrimitive(LABEL)))
                .build();
    }

    private String safeGetString(JsonPrimitive primitive) {
        if (primitive != null) {
            return primitive.getAsString();
        } else {
            return null;
        }
    }

    private Long safeGetLong(JsonPrimitive primitive) {
        if (primitive != null) {
            return primitive.getAsLong();
        } else {
            return 0L;
        }
    }

}
