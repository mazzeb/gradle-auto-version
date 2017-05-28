package com.github.mazzeb.gradle.autoversion;

public class AutoVersionConfig {
    private String versionFile = "version.json";

    public String getVersionFile() {
        return versionFile;
    }

    public void setVersionFile(String versionFile) {
        this.versionFile = versionFile;
    }
}
