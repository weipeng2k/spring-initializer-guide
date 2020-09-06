package com.murdock.tools.spring.initializer.guide.generator.version;

import io.spring.initializr.generator.version.Version;
import io.spring.initializr.generator.version.VersionParser;
import org.junit.Assert;
import org.junit.Test;

/**
 * <pre>
 * Version的基本定义
 * 能够从VersionParser中找寻出当前版本的具体内容
 * </pre>
 *
 * @author weipeng2k 2020年09月06日 下午19:35:42
 */
public class VersionTest {

    @Test
    public void get_version() {
        Version version = VersionParser.DEFAULT.parse("1.0.0-SNAPSHOT");
        Assert.assertSame(version.getFormat(), Version.Format.V2);

        Assert.assertEquals(1, version.getMajor().intValue());
        Assert.assertEquals(0, version.getMinor().intValue());
        Assert.assertEquals(0, version.getPatch().intValue());
        Assert.assertEquals("SNAPSHOT", version.getQualifier().getId());
    }

}
