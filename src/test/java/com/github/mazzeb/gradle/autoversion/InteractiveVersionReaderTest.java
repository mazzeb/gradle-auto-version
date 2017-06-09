package com.github.mazzeb.gradle.autoversion;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static com.github.mazzeb.gradle.autoversion.InteractiveVersionReader.interactiveVersionReader;
import static com.github.mazzeb.gradle.autoversion.Version.versionBuilder;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class InteractiveVersionReaderTest {

    InputStream inputStream;
    PrintStream printStream;
    InteractiveVersionReader interactiveVersionReader;

    @Before
    public void setUp() throws Exception {
        inputStream = new ByteArrayInputStream("1.4.2-SNAPSHOT".getBytes());
        printStream = mock(PrintStream.class);
        interactiveVersionReader = interactiveVersionReader(inputStream, printStream);
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

    @Test
    public void shouldReadVersion() throws Exception {
        String prompt = "please enter version";

        Version version = interactiveVersionReader.readVersion(prompt);

        verify(printStream, times(1)).print(eq(prompt));
        assertThat(version, is(versionBuilder()
                .withMajor(1L)
                .withMinor(4L)
                .withPatch(2L)
                .withLabel("SNAPSHOT")
                .build()));

    }
}