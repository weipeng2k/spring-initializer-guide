package com.murdock.tools.spring.initializer.guide.generator.buildsystem.maven;

import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import io.spring.initializr.generator.buildsystem.maven.MavenBuildWriter;
import io.spring.initializr.generator.io.IndentingWriter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;

import java.io.StringWriter;

/**
 * @author weipeng2k 2020年09月10日 下午14:26:13
 */
public class MavenBuildTest {

    @Test
    public void maven_build() {
        MavenBuild mavenBuild = new MavenBuild();
        mavenBuild.settings()
                .coordinates("com.murdock.tools", "initializer")
                .build();

        String s = ToStringBuilder.reflectionToString(mavenBuild.getSettings());
        System.out.println(s);

        MavenBuildWriter mavenBuildWriter = new MavenBuildWriter();
        StringWriter stringWriter = new StringWriter();
        mavenBuildWriter.writeTo(new IndentingWriter(stringWriter), mavenBuild);

        System.out.println(stringWriter.toString());
    }

}
