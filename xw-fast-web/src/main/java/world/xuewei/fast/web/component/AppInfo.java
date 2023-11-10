package world.xuewei.fast.web.component;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 应用信息
 *
 * @author XUEW
 * @since 2023/11/1 17:07
 */
@Data
@Component
public class AppInfo {

    /**
     * 应用中文名称
     */
    @Value("${app.cn-name:Xw-Fast}")
    private String cnName;

    /**
     * 应用英文名称
     */
    @Value("${app.en-name:Xw-Fast}")
    private String enName;
}
