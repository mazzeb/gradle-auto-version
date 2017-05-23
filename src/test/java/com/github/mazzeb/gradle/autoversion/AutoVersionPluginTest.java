package com.github.mazzeb.gradle.autoversion;

import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.gradle.tooling.BuildLauncher;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;
import org.junit.Test;

import java.io.File;

public class AutoVersionPluginTest {
    @Test
    public void shouldApplyVersion() throws Exception {
        Project someProject = ProjectBuilder.builder()
                .build();

        someProject.getPluginManager().apply(AutoVersionPlugin.class);
    }

    @Test
    public void shouldExecuteNextPatchTask() throws Exception {
        GradleConnector connector = GradleConnector.newConnector();
        connector.forProjectDirectory(new File("."));
        ProjectConnection connection = connector.connect();
        try {
            BuildLauncher launcher = connection.newBuild();
            launcher.forTasks("nextPatch");
            launcher.run();
        } finally {
            connection.close();
        }
    }
}