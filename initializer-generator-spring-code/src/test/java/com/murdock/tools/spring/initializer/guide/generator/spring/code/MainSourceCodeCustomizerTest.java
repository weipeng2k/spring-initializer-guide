package com.murdock.tools.spring.initializer.guide.generator.spring.code;

import io.spring.initializr.generator.buildsystem.BuildSystem;
import io.spring.initializr.generator.condition.ConditionalOnPackaging;
import io.spring.initializr.generator.io.IndentingWriterFactory;
import io.spring.initializr.generator.io.SimpleIndentStrategy;
import io.spring.initializr.generator.language.Annotation;
import io.spring.initializr.generator.language.Language;
import io.spring.initializr.generator.language.java.JavaCompilationUnit;
import io.spring.initializr.generator.language.java.JavaMethodDeclaration;
import io.spring.initializr.generator.language.java.JavaMethodInvocation;
import io.spring.initializr.generator.language.java.JavaReturnStatement;
import io.spring.initializr.generator.language.java.JavaSourceCode;
import io.spring.initializr.generator.language.java.JavaTypeDeclaration;
import io.spring.initializr.generator.packaging.Packaging;
import io.spring.initializr.generator.packaging.jar.JarPackaging;
import io.spring.initializr.generator.project.MutableProjectDescription;
import io.spring.initializr.generator.project.ProjectDirectoryFactory;
import io.spring.initializr.generator.spring.code.MainSourceCodeCustomizer;
import io.spring.initializr.generator.spring.code.SourceCodeProjectGenerationConfiguration;
import io.spring.initializr.generator.spring.code.java.JavaProjectGenerationConfiguration;
import io.spring.initializr.generator.test.project.ProjectAssetTester;
import io.spring.initializr.generator.test.project.ProjectStructure;
import io.spring.initializr.generator.version.VersionParser;
import org.junit.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author weipeng2k 2020年09月13日 下午21:13:24
 */
public class MainSourceCodeCustomizerTest {
    private final ProjectAssetTester projectTester = new ProjectAssetTester().withConfiguration(
            SourceCodeProjectGenerationConfiguration.class, JavaProjectGenerationConfiguration.class,
            MainSourceCodeCustomizerTest.Config.class);

    @Test
    public void main_source_code() {
        MutableProjectDescription description = new MutableProjectDescription();
        description.setPackaging(Packaging.forId("jar"));
        description.setPackageName("com.foo");
        description.setLanguage(Language.forId("java", "1.8"));
        description.setPlatformVersion(VersionParser.DEFAULT.parse("1.5.11.RELEASE"));
        description.setBuildSystem(BuildSystem.forId("maven"));

        ProjectStructure generate = projectTester.generate(description);
        Path projectDirectory = generate.getProjectDirectory();
        System.out.println(projectDirectory);
    }


    @Configuration
    static class Config {

        /**
         * <pre>
         * 声明一个接口HelloWorld
         * </pre>
         */
        @Bean
        @ConditionalOnPackaging(JarPackaging.ID)
        public MainSourceCodeCustomizer<JavaTypeDeclaration, JavaCompilationUnit, JavaSourceCode> mainSourceCodeCustomizer1() {
            return (javaSourceCode) -> {
                JavaCompilationUnit helloWorldService = javaSourceCode.createCompilationUnit("com.foo.service",
                        "HelloWorldService");
                JavaMethodDeclaration.Builder builder = JavaMethodDeclaration.method("hello");
                JavaMethodDeclaration javaMethodDeclaration = builder.modifiers(Modifier.ABSTRACT | Modifier.PUBLIC)
                        .returning("java.lang.String")
                        .body();
                JavaTypeDeclaration type = helloWorldService.createTypeDeclaration("HelloWorldService");
                type.modifiers(Modifier.PUBLIC | Modifier.INTERFACE);
                type.addMethodDeclaration(javaMethodDeclaration);
            };
        }

        /**
         * <pre>
         * 声明一个接口HelloWorld
         * </pre>
         */
        @Bean
        @ConditionalOnPackaging(JarPackaging.ID)
        public MainSourceCodeCustomizer<JavaTypeDeclaration, JavaCompilationUnit, JavaSourceCode> mainSourceCodeCustomizer2() {
            return (javaSourceCode) -> {
                JavaCompilationUnit helloWorldService = javaSourceCode.createCompilationUnit("com.foo.service.impl",
                        "HelloWorldServiceImpl");

                JavaMethodDeclaration.Builder builder = JavaMethodDeclaration.method("hello");
                JavaMethodDeclaration javaMethodDeclaration = builder.modifiers(Modifier.PUBLIC)
                        .returning("java.lang.String")
                        .body(new JavaReturnStatement(new JavaMethodInvocation("\"hello\"", "toUpperCase")));
                javaMethodDeclaration.annotate(Annotation.name("java.lang.Override"));

                JavaTypeDeclaration type = helloWorldService.createTypeDeclaration("HelloWorldServiceImpl");
                type.addMethodDeclaration(javaMethodDeclaration);
                type.modifiers(Modifier.PUBLIC);
                type.extend("com.foo.service.HelloWorldService");
                type.annotate(Annotation.name("org.springframework.stereotype.Service(\"helloWorldService\")"));
            };
        }


        /**
         * <pre>
         * 需要一个工程文件位置的{@link ProjectDirectoryFactory}
         * </pre>
         *
         * @return
         */
        @Bean
        public ProjectDirectoryFactory projectDirectoryFactory() {
            return (description) -> Files.createTempDirectory("project-");
        }

        /**
         * <pre>
         * 输出时需要进行缩进的{@link IndentingWriterFactory}
         * </pre>
         *
         * @return
         */
        @Bean
        public IndentingWriterFactory indentingWriterFactory() {
            return IndentingWriterFactory.create(new SimpleIndentStrategy("    "));
        }
    }
}
