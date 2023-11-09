package world.xuewei.core.util;

import org.junit.Test;
import world.xuewei.fast.core.util.RandomUtils;

/**
 * 随机工具类单元测试
 *
 * @author XUEW
 * @since 2023/11/9 9:56
 */
public class RandomUtilsTest {

    /**
     * 测试整数随机
     */
    @Test
    public void randomIntTest() {
        System.out.println(RandomUtils.randomInt(0, 10));
        System.out.println(RandomUtils.randomInt(0, Integer.MAX_VALUE));
        System.out.println(RandomUtils.randomInt(Integer.MIN_VALUE, Integer.MAX_VALUE));
        System.out.println(RandomUtils.randomInt());
    }

    /**
     * 测试浮点数随机
     */
    @Test
    public void randomDoubleTest() {
        System.out.println(RandomUtils.randomDouble());
        System.out.println(RandomUtils.randomDouble(3));
        System.out.println(RandomUtils.randomDouble(5.21, 10.24));
        System.out.println(RandomUtils.randomDouble(5.21, 10.24, 3));
    }

    /**
     * 测试生成随机英文字符串
     */
    @Test
    public void randomStrTest() {
        System.out.println(RandomUtils.randomStr(100, "ABCD1234"));
        System.out.println(RandomUtils.randomEnStr(100));
        System.out.println(RandomUtils.randomCnStr(2));
    }

    /**
     * 测试随机验证码
     */
    @Test
    public void randomCodeTest() {
        System.out.println(RandomUtils.randomCode(10));
        System.out.println(RandomUtils.randomEnCode(10));
        System.out.println(RandomUtils.randomNumCode(10));
    }
}
