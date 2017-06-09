package com.github.mazzeb.gradle.autoversion;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.InputStream;
import java.io.PrintStream;

import static com.github.mazzeb.gradle.autoversion.Version.versionBuilder;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class InteractiveVersionReaderTest {

    @Mock
    InputStream inputStream;

    @Mock
    PrintStream printStream;

    @InjectMocks
    InteractiveVersionReader interactiveVersionReader;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldParseVersionStringWithoutLabel() throws Exception {
        String string = "1.4.2";

        Version version = interactiveVersionReader.parseVersion(string);

        assertThat(version, is(versionBuilder()
                .withMajor(1L)
                .withMinor(4L)
                .withPatch(2L)
                .build()));

    }

    @Test
    public void shouldParseVersionStringWithLabel() throws Exception {
        String string = "1.4.2-SNAPSHOT";

        Version version = interactiveVersionReader.parseVersion(string);

        assertThat(version, is(versionBuilder()
                .withMajor(1L)
                .withMinor(4L)
                .withPatch(2L)
                .withLabel("SNAPSHOT")
                .build()));

    }

    @Test
    public void shouldParseVersionStringWithLabelAndSomeRandomStuff() throws Exception {
        String string = "1.4.2-beta23.1-SNAPSHOT";

        Version version = interactiveVersionReader.parseVersion(string);

        assertThat(version, is(versionBuilder()
                .withMajor(1L)
                .withMinor(4L)
                .withPatch(2L)
                .withLabel("beta23.1-SNAPSHOT")
                .build()));

    }
}