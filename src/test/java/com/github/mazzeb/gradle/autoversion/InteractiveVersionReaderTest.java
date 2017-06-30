package com.github.mazzeb.gradle.autoversion;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockSettings;
import org.mockito.internal.creation.bytebuddy.ByteBuddyMockMaker;
import org.mockito.plugins.MockMaker;

import java.io.Console;
import java.io.PrintStream;

import static com.github.mazzeb.gradle.autoversion.InteractiveVersionReader.interactiveVersionReader;
import static com.github.mazzeb.gradle.autoversion.Version.versionBuilder;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class InteractiveVersionReaderTest {

    private Console console;
    private InteractiveVersionReader interactiveVersionReader;

    @Before
    public void setUp() throws Exception {
        console = mock(Console.class);
        interactiveVersionReader = interactiveVersionReader(console);
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
        String prompt = "Please enter version";

        when(console.readLine(any())).thenReturn("1.4.2-SNAPSHOT");

        Version version = interactiveVersionReader.readVersion(prompt, versionBuilder().build());

        verify(console, times(1)).readLine(eq("Please enter version (0.0.0): "));
        assertThat(version, is(versionBuilder()
                .withMajor(1L)
                .withMinor(4L)
                .withPatch(2L)
                .withLabel("SNAPSHOT")
                .build()));

    }
}