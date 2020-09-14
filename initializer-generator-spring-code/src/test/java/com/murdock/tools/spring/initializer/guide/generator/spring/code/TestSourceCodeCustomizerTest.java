package com.murdock.tools.spring.initializer.guide.generator.spring.code;

import io.spring.initializr.generator.buildsystem.BuildSystem;
import io.spring.initializr.generator.condition.ConditionalOnPackaging;
import io.spring.initializr.generator.io.IndentingWriterFactory;
import io.spring.initializr.generator.io.SimpleIndentStrategy;
import io.spring.initializr.generator.language.Annotation;
import io.spring.initializr.generator.language.Language;
import io.spring.initializr.generator.language.java.JavaCompilationUnit;
import io.spring.initializr.generator.language.java.JavaMethodDeclaration;
import io.spring.initializr.generator.language.java.JavaSourceCode;
import io.spring.initializr.generator.language.java.JavaTypeDeclaration;
import io.spring.initializr.generator.packaging.Packaging;
import io.spring.initializr.generator.packaging.jar.JarPackaging;
import io.spring.initializr.generator.project.MutableProjectDescription;
import io.spring.initializr.generator.project.ProjectDirectoryFactory;
import io.spring.initializr.generator.spring.code.SourceCodeProjectGenerationConfiguration;
import io.spring.initializr.generator.spring.code.TestSourceCodeCustomizer;
import io.spring.initializr.generator.spring.code.java.JavaProjectGenerationConfiguration;
import io.spring.initializr.generator.test.project.ProjectAssetTester;
import io.spring.initializr.generator.test.project.ProjectStructure;
import io.spring.initializr.generator.version.VersionParser;
import org.junit.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * <pre>
 * TestSourceCodeCustomizer 测试源码定制器
 * </pre>
 *
 * @author weipeng2k 2020年09月14日 下午14:24:07
 */
public class TestSourceCodeCustomizerTest {
    private final ProjectAssetTester projectTester = new ProjectAssetTester().withConfiguration(
            SourceCodeProjectGenerationConfiguration.class, JavaProjectGenerationConfiguration.class,
            TestSourceCodeCustomizerTest.Config.class);

    @Test
    public void test_source_code() {
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

        @Bean
        @ConditionalOnPackaging(JarPackaging.ID)
        public TestSourceCodeCustomizer<JavaTypeDeclaration, JavaCompilationUnit, JavaSourceCode> testSourceCodeCustomizer() {
            return sourceCode -> {
                JavaCompilationUnit test1 = sourceCode.createCompilationUnit("com.foo", "Test1");
                JavaTypeDeclaration typeDeclaration = test1.createTypeDeclaration("Test1");
                typeDeclaration.modifiers(Modifier.PUBLIC);

                JavaMethodDeclaration.Builder hello = JavaMethodDeclaration.method("hello");
                JavaMethodDeclaration javaMethodDeclaration = hello.parameters()
                        .returning("void")
                        .modifiers(Modifier.PUBLIC)
                        .body();
                javaMethodDeclaration.annotate(Annotation.name("org.junit.Test"));
                typeDeclaration.addMethodDeclaration(javaMethodDeclaration);
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
