package com.murdock.tools.spring.initializer.guide.metadata;

import io.spring.initializr.metadata.InitializrMetadata;
import io.spring.initializr.metadata.InitializrMetadataBuilder;
import io.spring.initializr.metadata.Type;
import io.spring.initializr.metadata.TypeCapability;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

/**
 * @author weipeng2k 2020年09月12日 下午18:51:13
 */
public class TypeTest {
    private static InitializrMetadata initializrMetadata;

    /**
     * <pre>
     *   types:
     *     - name: Maven POM
     *       id: maven-build
     *       tags:
     *         build: maven
     *         format: build
     *       default: false
     *       action: /pom.xml
     *     - name: Maven Project
     *       id: maven-project
     *       tags:
     *         build: maven
     *         format: project
     *       default: true
     *       action: /starter.zip
     *     - name: Gradle Config
     *       id: gradle-build
     *       tags:
     *         build: gradle
     *         format: build
     *       default: false
     *       action: /build.gradle
     *     - name: Gradle Project
     *       id: gradle-project
     *       tags:
     *         build: gradle
     *         format: project
     *       default: false
     *       action: /starter.zip
     * </pre>
     */
    @BeforeClass
    public static void init() {
        InitializrMetadataBuilder initializrMetadataBuilder = InitializrMetadataBuilder.fromInitializrProperties(
                InitializerMetadataTest.load(
                        InitializerMetadataTest.loadProperties(new ClassPathResource("application-test-default.yml"))));
        initializrMetadata = initializrMetadataBuilder.build();
    }

    @Test
    public void type_capability() {
        TypeCapability types = initializrMetadata.getTypes();
        Type type = types.getDefault();
        Assert.assertEquals("maven-project", type.getId());
    }
}
