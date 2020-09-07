package com.murdock.tools.spring.initializer.guide.generator.io.template;

import io.spring.initializr.generator.io.template.MustacheTemplateRenderer;
import io.spring.initializr.generator.io.template.TemplateRenderer;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * 模板渲染测试
 * </pre>
 *
 * @author weipeng2k 2020年09月07日 下午15:26:30
 */
public class TemplateRendererTest {

    @Test
    public void render() throws Exception {
        TemplateRenderer templateRenderer = new MustacheTemplateRenderer("classpath:/templates/mustache");
        Map<String, Object> map = new HashMap<>();
        map.put("key", "weipeng2k");
        String test = templateRenderer.render("test", map);
        System.out.println(test);
        Assert.assertEquals("hello, weipeng2k", test);
    }
}
