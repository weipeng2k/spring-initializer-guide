package com.murdock.tools.spring.initializer.guide.generator.spring.code;

import io.spring.initializr.generator.buildsystem.BuildSystem;
import io.spring.initializr.generator.condition.ConditionalOnPackaging;
import io.spring.initializr.generator.io.IndentingWriterFactory;
import io.spring.initializr.generator.io.SimpleIndentStrategy;
import io.spring.initializr.generator.language.Annotation;
import io.spring.initializr.generator.language.Language;
import io.spring.initializr.generator.language.Parameter;
import io.spring.initializr.generator.language.java.JavaMethodDeclaration;
import io.spring.initializr.generator.language.java.JavaMethodInvocation;
import io.spring.initializr.generator.language.java.JavaReturnStatement;
import io.spring.initializr.generator.language.java.JavaTypeDeclaration;
import io.spring.initializr.generator.packaging.Packaging;
import io.spring.initializr.generator.packaging.jar.JarPackaging;
import io.spring.initializr.generator.project.MutableProjectDescription;
import io.spring.initializr.generator.project.ProjectDirectoryFactory;
import io.spring.initializr.generator.spring.code.MainApplicationTypeCustomizer;
import io.spring.initializr.generator.spring.code.SourceCodeProjectGenerationConfiguration;
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
 *  JavaSourceCode      是源代码
 *  CompilationUnit     是编译单元
 *  JavaTypeDeclaration 是类型声明
 *
 *  MainApplicationTypeCustomizer 是针对编译单元层面的定制，且是Application主类
 * </pre>
 *
 * @author weipeng2k 2020年09月14日 下午13:40:31
 */
public class MainApplicationTypeCustomizerTest {

    private final ProjectAssetTester projectTester = new ProjectAssetTester().withConfiguration(
            SourceCodeProjectGenerationConfiguration.class, JavaProjectGenerationConfiguration.class,
            MainApplicationTypeCustomizerTest.Config.class);

    @Test
    public void main_compilation_unit() {
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
        public MainApplicationTypeCustomizer<JavaTypeDeclaration> mainApplicationTypeCustomizer() {
            return new MainApplicationTypeCustomizer<JavaTypeDeclaration>() {
                @Override
                public void customize(JavaTypeDeclaration javaTypeDeclaration) {
                    javaTypeDeclaration.annotate(Annotation.name("org.springframework.stereotype.Service"));
                    JavaMethodDeclaration.Builder builder = JavaMethodDeclaration.method("t1");
                    JavaMethodDeclaration body = builder.modifiers(Modifier.PRIVATE)
                            .returning("T1")
                            .parameters(new Parameter("java.lang.String", "name"))
                            .body(new JavaReturnStatement(new JavaMethodInvocation("this", "t1")));
                    javaTypeDeclaration.addMethodDeclaration(body);
                }

                @Override
                public int getOrder() {
                    return 1;
                }
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
