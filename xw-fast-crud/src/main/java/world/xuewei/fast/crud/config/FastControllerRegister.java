package world.xuewei.fast.crud.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo.BuilderConfiguration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import world.xuewei.fast.core.util.Assert;
import world.xuewei.fast.crud.annotation.FastController;
import world.xuewei.fast.crud.controller.BaseController;
import world.xuewei.fast.crud.service.BaseDBService;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * FastController 请求注册器
 * 项目启动时扫描 FastController 注册基础接口
 *
 * @author XUEW
 * @since 2023/11/8 12:38
 */
@Slf4j
@Configuration
public class FastControllerRegister implements ApplicationContextAware, WebMvcConfigurer {

    /**
     * 引入上下文
     */
    private ApplicationContext context;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.context = applicationContext;
    }

    /**
     * 注册请求
     *
     * @param handlerMapping 请求注册器
     */
    @Bean
    public String registerFastController(RequestMappingHandlerMapping handlerMapping) {
        // 扫描 FastController 注解
        Map<String, Object> fastMap = context.getBeansWithAnnotation(FastController.class);
        for (String beanName : fastMap.keySet()) {
            Object fastController = fastMap.get(beanName);
            Class<?> fastClass = fastController.getClass();
            // 声明 FastController 注解的控制器必须指定 @RequestMapping 注解，否则接口路径冲突
            RequestMapping requestMappingAnnotation = fastClass.getAnnotation(RequestMapping.class);
            if (Assert.isEmpty(requestMappingAnnotation)) {
                log.warn("FastController Must Be Tagged With @RequestMapping. {} Fail To Register", beanName);
                continue;
            }
            BaseDBService<Object> service = getService(fastClass, fastController);
            if (Assert.isEmpty(service)) {
                // 声明 FastController 注解的控制器必须包含并注入 service 成员变量
                log.warn("FastController Must Inject A Member Variable Named 'service'. {} Fail To Register", beanName);
                continue;
            }
            String[] annotations = requestMappingAnnotation.value();
            if (Assert.notEmpty(annotations)) {
                BuilderConfiguration initConfig = initConfig(handlerMapping);
                for (String annotation : annotations) {
                    BaseController<Object> baseController = new BaseController<>(service);
                    // 获取需要注册的方法
                    Method[] methods = BaseController.class.getDeclaredMethods();
                    for (Method method : methods) {
                        String methodName = method.getName();
                        String path = String.format("%s/%s", annotation, methodName);
                        // 注册路径逻辑
                        handlerMapping.registerMapping(
                                RequestMappingInfo
                                        .paths(path)
                                        .options(initConfig)
                                        .build()
                                , baseController, method);
                        log.info("FastController Interface Successfully Registered. Path {}", path);
                    }
                }
            }
        }
        return null;
    }


    /**
     * 请求映射配置
     */
    private BuilderConfiguration initConfig(RequestMappingHandlerMapping handlerMapping) {
        BuilderConfiguration config = new BuilderConfiguration();
        config.setTrailingSlashMatch(handlerMapping.useTrailingSlashMatch());
        config.setContentNegotiationManager(handlerMapping.getContentNegotiationManager());
        if (handlerMapping.getPatternParser() != null) {
            config.setPatternParser(handlerMapping.getPatternParser());
        } else {
            config.setSuffixPatternMatch(handlerMapping.useSuffixPatternMatch());
            config.setRegisteredSuffixPatternMatch(handlerMapping.useRegisteredSuffixPatternMatch());
            config.setPathMatcher(handlerMapping.getPathMatcher());
        }
        return config;
    }

    private BaseDBService<Object> getService(Class<?> fastClass, Object fastController) {
        Field serviceField;
        Object serviceInstance;
        try {
            serviceField = fastClass.getDeclaredField("service");
            serviceField.setAccessible(true);
            serviceInstance = serviceField.get(fastController);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // 声明 FastController 注解的控制器必须包含并注入 service 成员变量
            return null;
        }
        return Assert.notEmpty(serviceInstance) ? (BaseDBService) serviceInstance : null;
    }
}
