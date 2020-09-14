package com.murdock.tools.spring.initializer.generator.spring.ignore;

import io.spring.initializr.generator.buildsystem.maven.MavenBuildSystem;
import io.spring.initializr.generator.project.MutableProjectDescription;
import io.spring.initializr.generator.spring.scm.git.GitIgnore;
import io.spring.initializr.generator.spring.scm.git.GitProjectGenerationConfiguration;
import io.spring.initializr.generator.test.io.TextTestUtils;
import io.spring.initializr.generator.test.project.ProjectAssetTester;
import org.junit.Assert;
import org.junit.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

/**
 * @author weipeng2k 2020年09月13日 上午10:22:35
 */
public class GitIgnoreTest {

    private final ProjectAssetTester projectTester = new ProjectAssetTester().withConfiguration(
            GitProjectGenerationConfiguration.class);

    private List<String> generateGitIgnore(MutableProjectDescription description) {
        return projectTester.generate(description, (context) -> {
            GitIgnore gitIgnore = context.getBean(GitIgnore.class);
            StringWriter out = new StringWriter();
            gitIgnore.write(new PrintWriter(out));
            return TextTestUtils.readAllLines(out.toString());
        });
    }

    private GitIgnore getGitIgnore(MutableProjectDescription description) {
        return projectTester.generate(description, (context) -> context.getBean(GitIgnore.class));
    }

    @Test
    public void print_git_ignore() {
        MutableProjectDescription description = new MutableProjectDescription();
        description.setBuildSystem(new MavenBuildSystem());
        generateGitIgnore(description).forEach(System.out::println);
    }

    @Test
    public void get_git_ignore() {
        MutableProjectDescription description = new MutableProjectDescription();
        description.setBuildSystem(new MavenBuildSystem());
        GitIgnore gitIgnore = getGitIgnore(description);
        Assert.assertTrue(gitIgnore.getGeneral().getItems().size() > 0);
    }

}
