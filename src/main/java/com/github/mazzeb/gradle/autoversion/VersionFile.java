package com.github.mazzeb.gradle.autoversion;

import org.gradle.api.GradleException;

import java.io.*;
import java.util.Properties;

import static com.github.mazzeb.gradle.autoversion.Version.versionBuilder;

public class VersionFile {

    public static final String MAJOR = "major";
    public static final String MINOR = "minor";
    public static final String PATCH = "patch";
    public static final String SNAPSHOT = "snapshot";

    public static Version readFromFile(String fileName) {
        try (InputStream in = new FileInputStream(fileName)) {
            Properties properties = new Properties();
            properties.load(in);

            return versionBuilder()
                    .withMajor(Long.valueOf(properties.getProperty(MAJOR)))
                    .withMinor(Long.valueOf(properties.getProperty(MINOR)))
                    .withPatch(Long.valueOf(properties.getProperty(PATCH)))
                    .withSnapshot(Boolean.valueOf(properties.getProperty(SNAPSHOT)))
                    .build();
        } catch (IOException e) {
            throw new GradleException(e.getMessage());
        }
    }

    public static void saveToFile(String fileName, Version version) {
        Properties properties = new Properties();
        properties.setProperty(MAJOR, version.getMajor().toString());
        properties.setProperty(MINOR, version.getMinor().toString());
        properties.setProperty(PATCH, version.getPatch().toString());
        properties.setProperty(SNAPSHOT, version.isSnapshot()? "true" : "false");
        try (Writer fileWriter = new FileWriter(fileName)){
            properties.store(fileWriter, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
