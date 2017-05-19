package com.github.mazzeb.gradle.autoversion;

import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.Test;

public class AutoVersionPluginTest {
    @Test
    public void shouldApplyVersion() throws Exception {
        Project someProject = ProjectBuilder.builder()
                .build();

        someProject.getPluginManager().apply(AutoVersionPlugin.class);


    }
}