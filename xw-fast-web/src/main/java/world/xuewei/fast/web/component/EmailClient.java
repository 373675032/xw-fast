package world.xuewei.fast.web.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import world.xuewei.fast.core.util.TemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 邮件客户端
 *
 * @author XUEW
 */
@Slf4j
@Component
public class EmailClient {

    /**
     * 邮件发送器
     */
    private final JavaMailSenderImpl mailSender;

    /**
     * 发件人标题
     */
    @Value("${spring.mail.title:Xw-Fast}")
    private String title = "";

    /**
     * 应用信息
     */
    private final AppInfo appInfo;

    /**
     * 验证码有效分钟数
     */
    @Value("${spring.mail.valid-minutes:5}")
    public Integer validMinutes;

    /**
     * 构造器注入
     */
    public EmailClient(@Autowired(required = false) JavaMailSenderImpl javaMailSender, AppInfo appInfo) {
        this.mailSender = javaMailSender;
        this.appInfo = appInfo;
    }

    /**
     * 发送邮件（支持HTML）
     *
     * @param subject     标题
     * @param content     文本内容
     * @param targetEmail 目标邮箱
     * @throws MessagingException 邮件发送异常
     */
    public void sendEmail(String subject, String content, String targetEmail) throws MessagingException {
        MimeMessage mailMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true);
        messageHelper.setSubject(subject);
        messageHelper.setText(content, true);
        messageHelper.setTo(targetEmail);
        messageHelper.setFrom(String.format("%s<%s>", title, mailSender.getUsername()));
        mailSender.send(mailMessage);
    }

    /**
     * 发生邮箱验证码
     *
     * @param targetEmail 目标邮箱
     * @param code        验证码
     * @throws MessagingException 邮件发送异常
     */
    public void sendCode(String targetEmail, String code) throws MessagingException {
        String codeTemplate = TemplateUtils.readTemplateFile("code");
        codeTemplate = codeTemplate.replace("{CODE}", code);
        codeTemplate = codeTemplate.replace("{VALID_MINUTES}", String.valueOf(validMinutes));
        codeTemplate = codeTemplate.replace("{APP_NAME}", appInfo.getCnName());
        codeTemplate = codeTemplate.replace("{TIME}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        sendEmail("邮箱验证", codeTemplate, targetEmail);
    }
}
