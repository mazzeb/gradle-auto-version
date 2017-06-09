package com.github.mazzeb.gradle.autoversion;

import org.gradle.api.*;
import org.gradle.api.tasks.TaskContainer;
import org.slf4j.Logger;

import static com.github.mazzeb.gradle.autoversion.VersionFile.openVersionFile;
import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static org.slf4j.LoggerFactory.getLogger;

public class AutoVersionPlugin implements Plugin<Project> {

    private static final String UNSPECIFIED = "unspecified";

    private Logger logger = getLogger(this.getClass());

    private Version version;
    private VersionFile versionFile;
    private AutoVersionConfig autoVersionConfig;


    @Override
    public void apply(Project project) {
        logger.debug("plugin apply");
        project.afterEvaluate(this::afterEvaluate);
        TaskContainer taskContainer = project.getTasks();

        autoVersionConfig = project.getExtensions().create("autoversion", AutoVersionConfig.class);

        taskContainer.create("nextMajor",
                configureTask("update version to next Major Release", this::nextMajor));
        taskContainer.create("nextMinor",
                configureTask("update version to next minor Release", this::nextMinor));
        taskContainer.create("nextPatch",
                configureTask("update version to next patch Release", this::nextPatch));
    }

    private Action<Task> configureTask(String description, Action<Task> action) {
        return (task) -> {
            task.setGroup("version");
            task.setDescription(description);
            task.setActions(singletonList(action));
        };
    }

    private void nextMajor(Task task) {
        logger.info("updating major version");
        version = version.nextMajor();
        updateVersion(task.getProject(), version);
    }

    private void nextMinor(Task task) {
        logger.info("updating minor version");
        version = version.nextMinor();
        updateVersion(task.getProject(), version);
    }

    private void nextPatch(Task task) {
        logger.info("updating patch version");
        version = version.nextPatch();
        updateVersion(task.getProject(), version);
    }

    private void readVersionInteractive(Task task) {

    }

    private void updateVersion(Project project, Version version) {
        project.setVersion(version);
        versionFile.saveToFile(version);
    }

    private void afterEvaluate(Project project) {
        logger.debug("opening version file: " + autoVersionConfig.getVersionFile());
        versionFile = openVersionFile(autoVersionConfig.getVersionFile());
        Object existingVersion = project.getVersion();
        logger.debug(format("legacy project version: '%s'", existingVersion.toString()));
        if (UNSPECIFIED.equals(existingVersion)) {
            try {
                version = versionFile.readFromFile();
            } catch (GradleException e) {
                logger.info(format("Could not read version from %s", autoVersionConfig.getVersionFile()));
            }
            project.setVersion(version);
        } else {
            throw new GradleException("please specify version in version.gradle file and remove it from build.gradle");
        }
        logger.debug("applied project version: " + project.getVersion());

    }
}
