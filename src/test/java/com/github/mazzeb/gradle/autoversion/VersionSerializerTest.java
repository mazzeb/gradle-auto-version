package com.github.mazzeb.gradle.autoversion;

import com.google.gson.JsonElement;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import static com.github.mazzeb.gradle.autoversion.Version.versionBuilder;

public class VersionSerializerTest {

    VersionSerializer testee;

    @Before
    public void setUp() throws Exception {
        testee = new VersionSerializer();
    }

    @Test
    public void shouldSerializeVersion() throws Exception {
        Version version = versionBuilder()
                .withMajor(2L)
                .withMinor(4L)
                .withPatch(1L)
                .withLabel("someLabel")
                .build();
        JsonElement serialize = testee.serialize(version, null, null);

        JSONAssert.assertEquals(serialize.toString(), "{\n" +
                "  \"major\": 2,\n" +
                "  \"minor\": 4,\n" +
                "  \"patch\": 1,\n" +
                "  \"label\": \"someLabel\"\n" +
                "}", false);
    }
}