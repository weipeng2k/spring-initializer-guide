package com.murdock.tools.spring.initializer.guide.generator.io.section;

import io.spring.initializr.generator.io.template.MustacheTemplateRenderer;
import io.spring.initializr.generator.io.template.TemplateRenderer;
import io.spring.initializr.generator.io.text.BulletedSection;
import org.junit.Test;

import java.io.PrintWriter;

/**
 * @author weipeng2k 2020年09月07日 下午17:06:07
 */
public class BulletedSectionTest {

    /**
     * <pre>
     *     <b>test</b>
     *     <b>test</b>
     *     <b>test</b>
     * </pre>
     *
     * @throws Exception
     */
    @Test
    public void bulleted_section() throws Exception {
        TemplateRenderer templateRenderer = new MustacheTemplateRenderer("classpath:/templates/mustache");
        BulletedSection<String> bulletedSection = new BulletedSection<>(templateRenderer, "for", "keys");
        bulletedSection.addItem("test");
        bulletedSection.addItem("test");
        bulletedSection.addItem("test");
        PrintWriter printWriter = new PrintWriter(System.out);
        bulletedSection.write(printWriter);
        printWriter.flush();
    }
}
