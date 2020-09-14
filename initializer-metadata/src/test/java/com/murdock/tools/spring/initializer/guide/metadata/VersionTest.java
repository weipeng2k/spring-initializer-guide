package com.murdock.tools.spring.initializer.guide.metadata;

import io.spring.initializr.metadata.InitializrMetadata;
import io.spring.initializr.metadata.InitializrMetadataBuilder;
import io.spring.initializr.metadata.TextCapability;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

/**
 * @author weipeng2k 2020年09月12日 下午19:13:23
 */
public class VersionTest {
    private static InitializrMetadata initializrMetadata;

    @BeforeClass
    public static void init() {
        InitializrMetadataBuilder initializrMetadataBuilder = InitializrMetadataBuilder.fromInitializrProperties(
                InitializerMetadataTest.load(
                        InitializerMetadataTest.loadProperties(new ClassPathResource("application-test-default.yml"))));
        initializrMetadata = initializrMetadataBuilder.build();
    }

    @Test
    public void get_version() {
        TextCapability version = initializrMetadata.getVersion();
        System.out.println(version.getContent());
    }
}
