package com.murdock.tools.spring.initializer.generator.spring.ignore;

import io.spring.initializr.generator.buildsystem.maven.MavenBuildSystem;
import io.spring.initializr.generator.condition.ConditionalOnBuildSystem;
import io.spring.initializr.generator.project.MutableProjectDescription;
import io.spring.initializr.generator.spring.scm.git.GitIgnore;
import io.spring.initializr.generator.spring.scm.git.GitIgnoreCustomizer;
import io.spring.initializr.generator.spring.scm.git.GitProjectGenerationConfiguration;
import io.spring.initializr.generator.test.project.ProjectAssetTester;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author weipeng2k 2020年09月13日 上午11:03:10
 */
public class ExtendsGitIgnoreTest {

    private final ProjectAssetTester projectTester = new ProjectAssetTester().withConfiguration(
            GitProjectGenerationConfiguration.class, Config.class);

    private GitIgnore getGitIgnore(MutableProjectDescription description) {
        return projectTester.generate(description, (context) -> context.getBean(GitIgnore.class));
    }

    @Test
    public void get_haha() {
        MutableProjectDescription description = new MutableProjectDescription();
        description.setBuildSystem(new MavenBuildSystem());
        GitIgnore gitIgnore = getGitIgnore(description);
        Assert.assertTrue(gitIgnore.getGeneral().getItems().contains("haha/"));
    }

    @Configuration
    static class Config {

        @Bean
        @ConditionalOnBuildSystem(MavenBuildSystem.ID)
        public GitIgnoreCustomizer mavenGitIgnoreCustomizer1() {
            return (gitIgnore) -> {
                gitIgnore.getGeneral().add("haha/");
            };
        }
    }
}
