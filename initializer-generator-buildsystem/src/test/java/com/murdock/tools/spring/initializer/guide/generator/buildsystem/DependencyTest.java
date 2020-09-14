package com.murdock.tools.spring.initializer.guide.generator.buildsystem;

import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.buildsystem.DependencyScope;
import io.spring.initializr.generator.version.VersionReference;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author weipeng2k 2020年09月10日 上午10:48:45
 */
public class DependencyTest {

    @Test
    public void dependency() {
        Dependency dependency = Dependency.withCoordinates("com.murdock.tools", "initializer-generator-buildsystem")
                .artifactId("initializer-generator")
                .classifier("jdk15")
                .scope(DependencyScope.COMPILE)
                .type("jar")
                .version(VersionReference.ofProperty("initializer-generator.version"))
                .exclusions(new Dependency.Exclusion("com.alibaba", "junit"))
                .build();
        System.out.println(ToStringBuilder.reflectionToString(dependency));

        Assert.assertEquals("initializer-generator", dependency.getArtifactId());
    }
}
