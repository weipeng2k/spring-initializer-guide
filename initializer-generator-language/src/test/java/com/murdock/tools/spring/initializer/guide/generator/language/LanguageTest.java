package com.murdock.tools.spring.initializer.guide.generator.language;

import io.spring.initializr.generator.language.Language;
import io.spring.initializr.generator.language.LanguageFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.util.List;

/**
 * @author weipeng2k 2020年09月08日 下午14:17:39
 */
public class LanguageTest {

    @Test
    public void get_language() {
        List<LanguageFactory> languageFactories = SpringFactoriesLoader.loadFactories(LanguageFactory.class,
                LanguageFactory.class.getClassLoader());
        languageFactories
                .forEach(System.out::println);

        Language java = Language.forId("java", "1.8");
        System.out.println(java);

        Assert.assertEquals("java", java.sourceFileExtension());
    }
}
