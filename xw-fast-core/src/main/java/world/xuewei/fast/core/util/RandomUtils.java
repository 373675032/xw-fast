package world.xuewei.fast.core.util;

import java.util.Random;

/**
 * 随机工具类，获取随机内容
 *
 * @author XUEW
 * @since 2023/11/9 9:50
 */
public class RandomUtils {

    /**
     * 默认浮点数精度
     */
    public static final int DEFAULT_DOUBLE_PRECISION = 2;

    /**
     * 英文字符范围
     */
    public static final String EN_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    /**
     * 数字字符范围
     */
    public static final String NUM_CHARS = "0123456789";

    /**
     * 英文字符 + 数字字符
     */
    public static final String EN_NUM_CHARS = EN_CHARS + NUM_CHARS;

    /**
     * 生成指定范围内的随机整数
     *
     * @param min 下限
     * @param max 上限
     * @return 随机整数
     */
    public static Integer randomInt(Integer min, Integer max) {
        // 参数验证
        if (min >= max) {
            throw new IllegalArgumentException("Min Value Must Be Less Than Max Value");
        }
        min = min <= 0 ? 1 : min;
        // 使用 Random 类生成随机整数
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    /**
     * 生成随机整数
     *
     * @return 随机整数
     */
    public static Integer randomInt() {
        return randomInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /**
     * 生成指定范围内的随机浮点数，并且可以指定精度
     *
     * @param min       下限
     * @param max       上限
     * @param precision 精度，保留小数位数
     * @return 随机浮点数
     */
    public static Double randomDouble(Double min, Double max, Integer precision) {
        // 参数验证
        if (min >= max || precision < 0) {
            throw new IllegalArgumentException("Invalid input parameters");
        }
        // 使用 Random 类生成随机浮点数，然后根据精度保留小数位数
        Random random = new Random();
        double randomValue = min + (max - min) * random.nextDouble();
        double scaleFactor = Math.pow(10, precision);
        return Math.round(randomValue * scaleFactor) / scaleFactor;
    }

    /**
     * 生成指定范围内的随机浮点数
     *
     * @param min 下限
     * @param max 上限
     * @return 随机浮点数
     */
    public static Double randomDouble(Double min, Double max) {
        return randomDouble(min, max, DEFAULT_DOUBLE_PRECISION);
    }

    /**
     * 生成随机浮点数
     *
     * @return 随机浮点数
     */
    public static Double randomDouble() {
        return randomDouble(Double.MIN_VALUE, Double.MAX_VALUE, DEFAULT_DOUBLE_PRECISION);
    }

    /**
     * 生成随机浮点数，并且可以指定精度
     *
     * @param precision 精度，保留小数位数
     * @return 随机浮点数
     */
    public static Double randomDouble(Integer precision) {
        return randomDouble(Double.MIN_VALUE, Double.MAX_VALUE, precision);
    }

    /**
     * 生成随机字符串，并指定长度和范围
     *
     * @param len        长度
     * @param characters 字符范围
     * @return 随机字符串
     */
    public static String randomStr(Integer len, String characters) {
        if (len <= 0) {
            throw new IllegalArgumentException("Length Must Be Greater Than 0");
        }
        StringBuilder stringBuilder = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            int randomIndex = randomInt(0, characters.length() - 1);
            char randomChar = characters.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }
        return stringBuilder.toString();
    }

    /**
     * 生成随机英文字符串，并指定长度
     *
     * @param len 长度
     * @return 随机字符串
     */
    public static String randomEnStr(Integer len) {
        return randomStr(len, EN_CHARS);
    }

    /**
     * 生成随机验证码，并指定长度
     *
     * @param len 长度
     * @return 随机字符串
     */
    public static String randomCode(Integer len) {
        return randomStr(len, EN_NUM_CHARS);
    }

    /**
     * 生成随机英文验证码，并指定长度
     *
     * @param len 长度
     * @return 随机字符串
     */
    public static String randomEnCode(Integer len) {
        return randomEnStr(len);
    }

    /**
     * 生成数字验证码，并指定长度
     *
     * @param len 长度
     * @return 随机字符串
     */
    public static String randomNumCode(Integer len) {
        return randomStr(len, NUM_CHARS);
    }

    /**
     * 生成随机中文字符串，并指定长度
     *
     * @param len 长度
     * @return 随机字符串
     */
    public static String randomCnStr(Integer len) {
        if (len <= 0) {
            throw new IllegalArgumentException("Length Must Be Greater Than 0");
        }
        StringBuilder stringBuilder = new StringBuilder(len);
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            char randomCnChar = (char) (0x4e00 + random.nextInt(0x9fff - 0x4e00 + 1));
            stringBuilder.append(randomCnChar);
        }
        return stringBuilder.toString();
    }
}
