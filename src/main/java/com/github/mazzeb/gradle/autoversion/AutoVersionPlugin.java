package com.github.mazzeb.gradle.autoversion;

import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.tasks.TaskContainer;
import org.slf4j.Logger;

import static com.github.mazzeb.gradle.autoversion.VersionFile.openVersionFile;
import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static org.slf4j.LoggerFactory.getLogger;

public class AutoVersionPlugin implements Plugin<Project> {

    private static final String VERSION_FILE = "version.json";
    private static final String UNSPECIFIED = "unspecified";

    private Logger logger = getLogger(this.getClass());

    private Version version;
    private VersionFile versionFile;

    @Override
    public void apply(Project project) {
        logger.debug("plugin apply");
        project.afterEvaluate(this::afterEvaluate);
        TaskContainer taskContainer = project.getTasks();

        taskContainer.create("nextMajor", this::configureNextMajor);
        taskContainer.create("nextMinor", this::configureNextMinor);
        taskContainer.create("nextPatch", this::configureNextPatch);
    }

    private void configureNextMajor(Task task) {
        task.setGroup("version");
        task.setDescription("update version to next Major Release");
        task.setActions(singletonList(this::nextMajor));
    }

    private void configureNextMinor(Task task) {
        task.setGroup("version");
        task.setDescription("update version to next minor Release");
        task.setActions(singletonList(this::nextMinor));
    }

    private void configureNextPatch(Task task) {
        task.setGroup("version");
        task.setDescription("update version to next patch Release");
        task.setActions(singletonList(this::nextPatch));
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

    private void updateVersion(Project project, Version version) {
        project.setVersion(version);
        versionFile.saveToFile(version);
    }

    private void afterEvaluate(Project project) {
        versionFile = openVersionFile(VERSION_FILE);
        Object existingVersion = project.getVersion();
        logger.debug(format("version before apply: '%s'", existingVersion.toString()));
        if (UNSPECIFIED.equals(existingVersion)) {
            version = versionFile.readFromFile();
            project.setVersion(version);
        } else {
            throw new GradleException("please specify version in version.gradle file and remove it from build.gradle");
        }
        logger.debug("the version: " + project.getVersion());
    }
}
