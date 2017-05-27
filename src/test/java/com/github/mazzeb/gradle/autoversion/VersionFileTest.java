package com.github.mazzeb.gradle.autoversion;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static com.github.mazzeb.gradle.autoversion.Version.versionBuilder;
import static java.io.File.createTempFile;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class VersionFileTest {

    String tmpFileName = "";
    private VersionFile testee;

    @Before
    public void setUp() throws Exception {
        tmpFileName = createTempFile("version", ".json").getAbsolutePath();
        testee = VersionFile.openVersionFile(tmpFileName);
        System.out.printf("Using tmpfile: %s%n", tmpFileName);
    }

    @After
    public void tearDown() throws Exception {
        System.out.printf("Removing tmpfile: %s%n", tmpFileName);
        new File(tmpFileName).delete();
    }

    @Test
    public void shouldReadVersionFile() throws Exception {
        testee = VersionFile.openVersionFile("src/test/resources/test-version.json");

        Version version = testee.readFromFile();

        assertThat(version.toString(), is("2.3.1"));
    }

    @Test
    public void shouldSaveReleaseToVersionFile() throws Exception {
        Version version = versionBuilder()
                .withMajor(1L)
                .withMinor(6L)
                .withPatch(2L)
                .withLabel("someLabel")
                .build();

        testee.saveToFile(version);
        Version resultVersion = testee.readFromFile();

        assertThat(resultVersion.toString(), is("1.6.2-someLabel"));
    }
}