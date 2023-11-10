package world.xuewei.fast.core.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 默认邮件模板
 *
 * @author XUEW
 * @since 2023/11/7 19:13
 */
public class DefaultEmailTemplate {

    public static final Map<String, String> DEFAULT_TEMPLATES = new HashMap<>();

    static {
        DEFAULT_TEMPLATES.put("code", "您好，您的验证码为：{CODE}，{VALID_MINUTES} 分钟有效。");
    }
}
