package com.github.mazzeb.gradle.autoversion;

import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.String.format;

public class AutoVersionPlugin implements Plugin<Project> {

    private static final String VERSION_FILE = "version.gradle";
    private static final String UNSPECIFIED = "unspecified";

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Version version;

    @Override
    public void apply(Project project) {
        logger.debug("plugin apply");
        project.afterEvaluate(this::afterEvaluate);
        project.getTasks().create("nextMajor", this::incMajor);
        project.getTasks().create("nextMinor", this::incMinor);
        project.getTasks().create("nextPatch", this::incPatch);
    }

    private void incMajor(Task task) {
        version = version.nextMajor();
    }

    private void incMinor(Task task) {

    }

    private void incPatch(Task task) {

    }

    private void afterEvaluate(Project project) {
        Object existingVersion = project.getVersion();
        logger.debug(format("version before apply: '%s'", existingVersion.toString()));
        if (UNSPECIFIED.equals(existingVersion)) {
            readVersion();
            project.setVersion("0.1-SNAPSHOT");
        } else {
            throw new GradleException("please specify version in version.gradle file and not in build.gradle");
        }
        logger.debug("the version: " + project.getVersion());
    }

    private Version readVersion() {
        version = VersionFile.readFromFile(VERSION_FILE);

        return version;
    }

}
