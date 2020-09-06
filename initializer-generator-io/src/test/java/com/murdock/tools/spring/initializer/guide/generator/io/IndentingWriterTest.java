package com.murdock.tools.spring.initializer.guide.generator.io;

import io.spring.initializr.generator.io.IndentingWriter;
import io.spring.initializr.generator.io.IndentingWriterFactory;
import io.spring.initializr.generator.io.SimpleIndentStrategy;
import org.junit.Test;

import java.io.StringWriter;

/**
 * @author weipeng2k 2020年09月06日 下午21:43:45
 */
public class IndentingWriterTest {

    /**
     * <pre>
     * int i = 0;
     * if (i > 0) {
     *   System.out.println(i);
     *   if (i < 5) {
     *     System.out.println(i + 1);
     *   }
     * }
     * </pre>
     */
    @Test
    public void indenting_write() {
        String googleStyle = "  ";
        SimpleIndentStrategy simpleIndentStrategy = new SimpleIndentStrategy(googleStyle);
        StringWriter out = new StringWriter();
        IndentingWriter indentingWriter = IndentingWriterFactory.create(simpleIndentStrategy).createIndentingWriter("test", out);
        indentingWriter.println("int i = 0;");
        indentingWriter.println("if (i > 0) {");
        indentingWriter.indented(() -> {
            indentingWriter.println("System.out.println(i);");
            indentingWriter.println("if (i < 5) {");
            indentingWriter.indented(() -> indentingWriter.println("System.out.println(i + 1);"));
            indentingWriter.println("}");
        });
        indentingWriter.println("}");
        System.out.println(out.toString());
    }
}
