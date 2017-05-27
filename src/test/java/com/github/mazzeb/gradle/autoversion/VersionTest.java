package com.github.mazzeb.gradle.autoversion;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class VersionTest {

    @Test
    public void shouldBuildVersionWithLabel() throws Exception {
        Version version = Version.versionBuilder()
                .withMajor(1L)
                .withMinor(4L)
                .withPatch(0L)
                .withLabel("someLabel")
                .build();

        String versionString = version.toString();

        assertThat(versionString, is("1.4.0-someLabel"));
    }

    @Test
    public void shouldBuildVersionWithoutLabel() throws Exception {
        Version version = Version.versionBuilder()
                .withMajor(2L)
                .withMinor(1L)
                .withPatch(3L)
                .build();

        String versionString = version.toString();

        assertThat(versionString, is("2.1.3"));
    }

    @Test
    public void shouldHaveDefaultValues() throws Exception {
        Version version = Version.versionBuilder().build();

        String versionString = version.toString();

        assertThat(versionString, is("0.0.0"));
    }

    @Test
    public void shouldGetNextMajorWithLabel() throws Exception {
        Version version = Version.versionBuilder()
                .withMajor(1L)
                .withMinor(4L)
                .withPatch(0L)
                .build();

        Version nextMajor = version.nextMajor().withLabel("SNAPSHOT");

        assertThat(nextMajor.toString(), is("2.0.0-SNAPSHOT"));

    }

    @Test
    public void shouldGetNextMajorRelease() throws Exception {
        Version version = Version.versionBuilder()
                .withMajor(1L)
                .withMinor(4L)
                .withPatch(3L)
                .build();

        Version nextMajor = version.nextMajor();

        assertThat(nextMajor.toString(), is("2.0.0"));
    }

    @Test
    public void shouldGetNextMinorSnapshot() throws Exception {
        Version version = Version.versionBuilder()
                .withMajor(1L)
                .withMinor(4L)
                .withPatch(2L)
                .build();

        Version nextMajor = version.nextMinor().withLabel("SNAPSHOT");

        assertThat(nextMajor.toString(), is("1.5.0-SNAPSHOT"));

    }

    @Test
    public void shouldGetNextMinorRelease() throws Exception {
        Version version = Version.versionBuilder()
                .withMajor(1L)
                .withMinor(4L)
                .withPatch(3L)
                .build();

        Version nextMajor = version.nextMinor();

        assertThat(nextMajor.toString(), is("1.5.0"));
    }


    @Test
    public void shouldGetNextPatchSnapshot() throws Exception {
        Version version = Version.versionBuilder()
                .withMajor(1L)
                .withMinor(4L)
                .withPatch(1L)
                .build();

        Version nextMajor = version.nextPatch().withLabel("SNAPSHOT");

        assertThat(nextMajor.toString(), is("1.4.2-SNAPSHOT"));

    }

    @Test
    public void shouldGetNextPatchRelease() throws Exception {
        Version version = Version.versionBuilder()
                .withMajor(1L)
                .withMinor(4L)
                .withPatch(3L)
                .build();

        Version nextMajor = version.nextPatch();

        assertThat(nextMajor.toString(), is("1.4.4"));
    }

}