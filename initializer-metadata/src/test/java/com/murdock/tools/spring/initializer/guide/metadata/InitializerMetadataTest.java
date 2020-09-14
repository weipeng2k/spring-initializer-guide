package com.murdock.tools.spring.initializer.guide.metadata;

import io.spring.initializr.metadata.Dependency;
import io.spring.initializr.metadata.DependencyGroup;
import io.spring.initializr.metadata.InitializrMetadata;
import io.spring.initializr.metadata.InitializrMetadataBuilder;
import io.spring.initializr.metadata.InitializrProperties;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.Properties;

/**
 * @author weipeng2k 2020年09月11日 下午17:00:53
 */
public class InitializerMetadataTest {

    /**
     * properties -> bean
     *
     * @param properties props
     * @return bean
     */
    static InitializrProperties load(Properties properties) {
        ConfigurationPropertySource source = new MapConfigurationPropertySource(properties);
        Binder binder = new Binder(source);
        // source中的前缀
        return binder.bind("initializr", InitializrProperties.class).get();
    }

    /**
     * yaml -> props
     *
     * @param resource 资源
     * @return props
     */
    static Properties loadProperties(Resource resource) {
        YamlPropertiesFactoryBean yamlFactory = new YamlPropertiesFactoryBean();
        yamlFactory.setResources(resource);
        yamlFactory.afterPropertiesSet();
        return yamlFactory.getObject();
    }

    @Test
    public void dep() {
        DependencyGroup group = DependencyGroup.create("Extra");
        Dependency dependency = Dependency.withId("com.foo:foo:1.0.0");
        group.getContent().add(dependency);
        InitializrMetadata metadata = InitializrMetadataBuilder.create()
                .withCustomizer((m) -> m.getDependencies().getContent().add(group)).build();


        String s = ToStringBuilder.reflectionToString(metadata.getDependencies());
        System.out.println(s);
    }

    @Test
    public void init() {
        InitializrMetadataBuilder initializrMetadataBuilder = InitializrMetadataBuilder.fromInitializrProperties(
                load(loadProperties(new ClassPathResource("application-test-default.yml"))));
        InitializrMetadata initializrMetadata = initializrMetadataBuilder.build();

        System.out.println(ToStringBuilder.reflectionToString(initializrMetadata.getBootVersions()));
    }


    /**
     * yaml --> properties
     */
    @Test
    public void yaml() {
        Properties properties = loadProperties(new ClassPathResource("application-test-default.yml"));
        String property = properties.getProperty("initializr.dependencies[0].name");
        System.out.println(property);
        Assert.assertEquals("Core", property);
        String property1 = properties.getProperty("initializr.dependencies[0].content[2].aliases[0]");
        System.out.println(property1);
    }

    @Test
    public void bind() {
        Properties properties = new Properties();
        properties.setProperty("s1.name", "weipeng");
        properties.setProperty("s1.pwd", "111");
        properties.setProperty("s1.hobbies[0].kind", "swimming");
        properties.setProperty("s1.hobbies[1].kind", "football");
        properties.setProperty("s1.hobbies[2].kind", "gaming");
        ConfigurationPropertySource source = new MapConfigurationPropertySource(properties);
        Binder binder = new Binder(source);
        // source中的前缀
        Student s1 = binder.bind("s1", Student.class).get();
        System.out.println(s1);
    }

    static class Student {
        private String name;
        private String pwd;
        private List<Hobby> hobbies;

        public List<Hobby> getHobbies() {
            return hobbies;
        }

        public void setHobbies(
                List<Hobby> hobbies) {
            this.hobbies = hobbies;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", pwd='" + pwd + '\'' +
                    ", hobbies=" + hobbies +
                    '}';
        }
    }

    static class Hobby {
        private String kind;

        public String getKind() {
            return kind;
        }

        public void setKind(String kind) {
            this.kind = kind;
        }

        @Override
        public String toString() {
            return "Hobby{" +
                    "kind='" + kind + '\'' +
                    '}';
        }
    }
}
