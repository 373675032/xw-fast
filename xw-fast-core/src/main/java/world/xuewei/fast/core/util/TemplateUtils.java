package world.xuewei.fast.core.util;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static world.xuewei.fast.core.constant.DefaultEmailTemplate.DEFAULT_TEMPLATES;

/**
 * 模板文件读取工具类
 *
 * @author XUEW
 * @since 2023/11/1 16:33
 */
public class TemplateUtils {


    /**
     * 读取邮件模板文件
     *
     * @param fileName 文件名称（不包含扩展名）
     * @return 内容字符串
     */
    public static String readTemplateFile(String fileName) {
        String target = String.format("email-templates/%s.html", fileName);
        StringBuilder stringBuilder = new StringBuilder();
        try (Scanner scanner = new Scanner(new ClassPathResource(target).getInputStream(), StandardCharsets.UTF_8.name())) {
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine());
            }
        } catch (IOException e) {
            // 尝试获取默认模板
            String content = DEFAULT_TEMPLATES.get(fileName);
            if (Assert.notEmpty(content)) {
                return content;
            }
            // 处理异常
            throw new RuntimeException(e.getMessage());
        }
        return stringBuilder.toString();
    }
}
