package com.murdock.tools.spring.initializer.guide.generator.version;

import io.spring.initializr.generator.version.VersionParser;
import io.spring.initializr.generator.version.VersionRange;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author weipeng2k 2020年09月06日 下午20:03:11
 */
public class VersionRangeTest {

    @Test
    public void get_version_range() {
        VersionRange versionRange = VersionParser.DEFAULT.parseRange("[1.1.0, 1.3.0)");
        System.out.println(versionRange);
        Assert.assertFalse(versionRange.isHigherInclusive());
        Assert.assertTrue(versionRange.isLowerInclusive());
    }
}
