package com.murdock.tools.spring.initializer.guide.generator.language;

import io.spring.initializr.generator.language.SourceStructure;
import io.spring.initializr.generator.language.java.JavaLanguage;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.nio.file.Path;

/**
 * <pre>
 * 文件结构
 * 需要工程的ROOT目录
 * </pre>
 *
 * @author weipeng2k 2020年09月08日 下午14:56:54
 */
public class SourceStructureTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void source_structure() throws Exception {
        Path path = folder.getRoot().toPath();
        Path resolve = path.resolve("src/main");
        System.out.println(resolve);
        SourceStructure sourceStructure = new SourceStructure(resolve, new JavaLanguage());
        Path test = sourceStructure.createSourceFile("com.example", "Test");
        System.out.println(test);
    }
}
