package com.murdock.tools.spring.initializer.guide.metadata;

import io.spring.initializr.metadata.DependenciesCapability;
import io.spring.initializr.metadata.Dependency;
import io.spring.initializr.metadata.DependencyGroup;
import io.spring.initializr.metadata.InitializrMetadata;
import io.spring.initializr.metadata.InitializrMetadataBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

/**
 * @author weipeng2k 2020年09月12日 下午16:00:49
 */
public class DependencyTest {

    private static InitializrMetadata initializrMetadata;

    /**
     * <pre>
     *   dependencies:
     *     - name: Core
     *       content:
     *         - name: Web
     *           id: web
     *           description: Web dependency description
     *           facets:
     *             - web
     *           links:
     *             - rel: guide
     *               href: https://example.com/guide
     *               description: Building a RESTful Web Service
     *             - rel: reference
     *               href: https://example.com/doc
     *         - name: Security
     *           id: security
     *         - name: Data JPA
     *           id: data-jpa
     *           aliases:
     *             - jpa
     *     - name: Other
     *       content:
     *         - name: Foo
     *           groupId: org.acme
     *           artifactId: foo
     *           version: 1.3.5
     *           weight: 42
     *           keywords:
     *             - thefoo
     *             - dafoo
     *           links:
     *             - rel: guide
     *               href: https://example.com/guide1
     *             - rel: reference
     *               href: https://example.com/{bootVersion}/doc
     *             - rel: guide
     *               href: https://example.com/guide2
     *               description: Some guide for foo
     *         - name: Bar
     *           id: org.acme:bar
     *           version: 2.1.0
     *         - name: Biz
     *           groupId: org.acme
     *           artifactId: biz
     *           scope: runtime
     *           version: 1.3.5
     *           compatibilityRange: 2.2.0.BUILD-SNAPSHOT
     *         - name: Bur
     *           id: org.acme:bur
     *           version: 2.1.0
     *           scope: test
     *           compatibilityRange: "[2.1.4.RELEASE,2.2.0.BUILD-SNAPSHOT)"
     *         - name: My API
     *           id : my-api
     *           groupId: org.acme
     *           artifactId: my-api
     *           scope: provided
     *           bom: my-api-bom
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
    public void dependency_group() {
        DependenciesCapability dependencies = initializrMetadata.getDependencies();
        dependencies.validate();
        DependencyGroup dependencyGroup = dependencies.getContent().get(0);
        Assert.assertNotNull(dependencyGroup.getBom());

        // DependencyGroup
        dependencies.getContent().forEach(this::print);
    }

    @Test
    public void dependency() {
        DependenciesCapability dependencies = initializrMetadata.getDependencies();
        Dependency dependency = dependencies.get("my-api");
        print(dependency);
    }

    @Test
    public void dependency_list() {
        DependenciesCapability dependencies = initializrMetadata.getDependencies();
        dependencies.getAll().forEach(this::print);
    }

    public void print(Object obj) {
        System.out.println(ToStringBuilder.reflectionToString(obj, ToStringStyle.MULTI_LINE_STYLE));
    }
}
