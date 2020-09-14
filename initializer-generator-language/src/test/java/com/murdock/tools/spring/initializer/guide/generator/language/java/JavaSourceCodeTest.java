package com.murdock.tools.spring.initializer.guide.generator.language.java;

import io.spring.initializr.generator.io.IndentingWriterFactory;
import io.spring.initializr.generator.language.Language;
import io.spring.initializr.generator.language.Parameter;
import io.spring.initializr.generator.language.SourceStructure;
import io.spring.initializr.generator.language.java.JavaCompilationUnit;
import io.spring.initializr.generator.language.java.JavaExpressionStatement;
import io.spring.initializr.generator.language.java.JavaLanguage;
import io.spring.initializr.generator.language.java.JavaMethodDeclaration;
import io.spring.initializr.generator.language.java.JavaMethodInvocation;
import io.spring.initializr.generator.language.java.JavaReturnStatement;
import io.spring.initializr.generator.language.java.JavaSourceCode;
import io.spring.initializr.generator.language.java.JavaSourceCodeWriter;
import io.spring.initializr.generator.language.java.JavaTypeDeclaration;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author weipeng2k 2020年09月08日 下午15:09:58
 */
public class JavaSourceCodeTest {
    private static final Language LANGUAGE = new JavaLanguage();
    private final JavaSourceCodeWriter writer = new JavaSourceCodeWriter(IndentingWriterFactory.withDefaultSettings());
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void write_class_hello_world() throws Exception {
        JavaSourceCode sourceCode = new JavaSourceCode();
        JavaCompilationUnit compilationUnit = sourceCode.createCompilationUnit("com.example", "Test");
        JavaTypeDeclaration test = compilationUnit.createTypeDeclaration("Test");
        test.addMethodDeclaration(JavaMethodDeclaration.method("trim").returning("java.lang.String")
                .modifiers(Modifier.PUBLIC).parameters(new Parameter("java.lang.String", "value"))
                .body(new JavaReturnStatement(new JavaMethodInvocation("value", "trim"))));

        List<String> lines = writeSingleType(sourceCode, "com/example/Test.java");
        lines.forEach(System.out::println);
    }

    @Test
    public void hello_world() throws Exception {
        JavaSourceCode sourceCode = new JavaSourceCode();
        JavaCompilationUnit compilationUnit = sourceCode.createCompilationUnit("com.example", "Test");
        JavaTypeDeclaration typeDeclaration = compilationUnit.createTypeDeclaration("Test");
        typeDeclaration.addMethodDeclaration(
                JavaMethodDeclaration.method("main")
                        .modifiers(Modifier.PUBLIC | Modifier.STATIC)
                        .returning("void")
                        .parameters(new Parameter("java.lang.String[]", "args"))
                        .body(new JavaExpressionStatement(
                                new JavaMethodInvocation("System", "out.println", "\"hello, world\""))));

        List<String> lines = writeSingleType(sourceCode, "com/example/Test.java");
        lines.forEach(System.out::println);
    }

    @Test
    public void complex_code() throws Exception {
        JavaSourceCode sourceCode = new JavaSourceCode();
        sourceCode.createCompilationUnit("com.example", "Test");
    }


    private List<String> writeSingleType(JavaSourceCode sourceCode, String location) throws IOException {
        Path source = writeSourceCode(sourceCode).resolve(location);
        System.out.println("COD:" + source);
        try (InputStream stream = Files.newInputStream(source)) {
            String[] lines = StreamUtils.copyToString(stream, StandardCharsets.UTF_8).split("\\r?\\n");
            return Arrays.asList(lines);
        }
    }

    private Path writeSourceCode(JavaSourceCode sourceCode) throws IOException {
        // 源目录
        Path srcDirectory = folder.getRoot().toPath().resolve(UUID.randomUUID().toString());
        System.out.println("SRC:" + srcDirectory);
        SourceStructure sourceStructure = new SourceStructure(srcDirectory, LANGUAGE);
        this.writer.writeTo(sourceStructure, sourceCode);
        return sourceStructure.getSourcesDirectory();
    }
}
