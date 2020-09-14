package com.murdock.tools.spring.initializer.guide.generator.spring.configuration;

import io.spring.initializr.generator.spring.configuration.ApplicationPropertiesContributor;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author weipeng2k 2020年09月13日 下午14:36:28
 */
public class ConfigurationTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void application_config() throws Exception {
        Path projectDir = Files.createTempDirectory(folder.getRoot().toPath(), "project-");
        new ApplicationPropertiesContributor().contribute(projectDir);
        File file = projectDir.resolve("src/main/resources/application.properties").toFile();
        Assert.assertTrue(file.exists());
    }
}
