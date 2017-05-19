package com.github.mazzeb.gradle.autoversion;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class VersionFileTest {

    String tmpFileName = "";

    @Before
    public void setUp() throws Exception {
        tmpFileName = File.createTempFile("version", ".properties").getAbsolutePath();
        System.out.printf("Using tmpfile: %s%n", tmpFileName);
    }

    @After
    public void tearDown() throws Exception {
        System.out.printf("Removing tmpfile: %s%n", tmpFileName);
        new File(tmpFileName).delete();
    }

    @Test
    public void shouldReadVersionFile() throws Exception {
        Version version = VersionFile.readFromFile("src/test/resources/test-version.properties");

        assertThat(version.toString(), is("2.3.1"));
    }

    @Test
    public void shouldSaveReleaseToVersionFile() throws Exception {
        Version version = Version.versionBuilder()
                .withMajor(1L)
                .withMinor(6L)
                .withPatch(2L)
                .withSnapshot(false)
                .build();

        VersionFile.saveToFile(tmpFileName, version);
        Version resultVersion = VersionFile.readFromFile(tmpFileName);

        assertThat(resultVersion.toString(), is("1.6.2"));
    }
}