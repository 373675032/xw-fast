package world.xuewei.core;

import org.junit.Test;
import world.xuewei.fast.core.util.TemplateProvider;

/**
 * @author XUEW
 * @since 2023/11/7 16:54
 */
public class TemplateFileReaderTest {

    @Test
    public void test1() {
        System.out.println(TemplateProvider.provide("a"));
    }
}
