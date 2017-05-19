package com.github.mazzeb.gradle.autoversion;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class VersionTest {

    @Test
    public void shouldBuildSnapshotVersion() throws Exception {
        Version version = Version.versionBuilder()
                .withMajor(1L)
                .withMinor(4L)
                .withPatch(0L)
                .withSnapshot(true)
                .build();

        String versionString = version.toString();

        assertThat(versionString, is("1.4.0-SNAPSHOT"));
    }

    @Test
    public void shouldBuildReleaseVersion() throws Exception {
        Version version = Version.versionBuilder()
                .withMajor(2L)
                .withMinor(1L)
                .withPatch(3L)
                .withSnapshot(false)
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
    public void shouldGetNextMajorSnapshot() throws Exception {
        Version version = Version.versionBuilder()
                .withMajor(1L)
                .withMinor(4L)
                .withPatch(0L)
                .build();

        Version nextMajor = version.nextMajor().asSnapshot();

        assertThat(nextMajor.toString(), is("2.0.0-SNAPSHOT"));

    }

    @Test
    public void shouldGetNextMajorRelease() throws Exception {
        Version version = Version.versionBuilder()
                .withMajor(1L)
                .withMinor(4L)
                .withPatch(3L)
                .build();

        Version nextMajor = version.nextMajor().asRelease();

        assertThat(nextMajor.toString(), is("2.0.0"));
    }

    @Test
    public void shouldGetNextMinorSnapshot() throws Exception {
        Version version = Version.versionBuilder()
                .withMajor(1L)
                .withMinor(4L)
                .withPatch(2L)
                .build();

        Version nextMajor = version.nextMinor().asSnapshot();

        assertThat(nextMajor.toString(), is("1.5.0-SNAPSHOT"));

    }

    @Test
    public void shouldGetNextMinorRelease() throws Exception {
        Version version = Version.versionBuilder()
                .withMajor(1L)
                .withMinor(4L)
                .withPatch(3L)
                .build();

        Version nextMajor = version.nextMinor().asRelease();

        assertThat(nextMajor.toString(), is("1.5.0"));
    }


    @Test
    public void shouldGetNextPatchSnapshot() throws Exception {
        Version version = Version.versionBuilder()
                .withMajor(1L)
                .withMinor(4L)
                .withPatch(1L)
                .build();

        Version nextMajor = version.nextPatch().asSnapshot();

        assertThat(nextMajor.toString(), is("1.4.2-SNAPSHOT"));

    }

    @Test
    public void shouldGetNextPatchRelease() throws Exception {
        Version version = Version.versionBuilder()
                .withMajor(1L)
                .withMinor(4L)
                .withPatch(3L)
                .build();

        Version nextMajor = version.nextPatch().asRelease();

        assertThat(nextMajor.toString(), is("1.4.4"));
    }

}