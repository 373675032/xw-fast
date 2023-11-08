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
import java.util.*;
import java.util.stream.Collectors;

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
            String[] basicUrls = requestMappingAnnotation.value();
            if (Assert.isEmpty(basicUrls)) {
                log.warn("FastController @RequestMapping Url Must Be Specify. {} Fail To Register", beanName);
                continue;
            }
            BaseDBService<Object> service = getService(fastClass, fastController);
            if (Assert.isEmpty(service)) {
                // 声明 FastController 注解的控制器必须包含并注入 service 成员变量
                log.warn("FastController Must Inject A Member Variable Named 'service'. {} Fail To Register", beanName);
                continue;
            }
            // 注解配置
            FastController classAnnotation = fastClass.getAnnotation(FastController.class);
            String[] includeMethods = classAnnotation.includeMethods();
            String[] excludeMethods = classAnnotation.excludeMethods();
            if (Assert.notEmpty(includeMethods) && Assert.notEmpty(excludeMethods)) {
                // 使用 FastController 注解时，includeMethods 属性和 excludeMethods 属性不可同时指定
                log.warn("FastController Cannot Configure Both 'includeMethods' and 'excludeMethods'. {} Fail To Register", beanName);
                continue;
            }
            // 请求映射配置
            BuilderConfiguration initConfig = initConfig(handlerMapping);
            // 这个对象就是真正提供服务的对象
            BaseController<Object> baseController = new BaseController<>(service);
            // 根据规则获取要注册的方法
            Set<Method> methods = getNeedRegisterMethods(includeMethods, excludeMethods);
            for (Method method : methods) {
                for (String basicUrl : basicUrls) {
                    doRegister(initConfig, handlerMapping, basicUrl, baseController, method);
                }
            }
        }
        return null;
    }

    /**
     * 注册规则判断
     *
     * @param includeMethods 包含注册的方法数组
     * @param excludeMethods 排除注册的方法数组
     * @return 需要注册的方法
     */
    private Set<Method> getNeedRegisterMethods(String[] includeMethods, String[] excludeMethods) {
        List<String> includeList = Arrays.stream(includeMethods).collect(Collectors.toList());
        List<String> excludeList = Arrays.stream(excludeMethods).collect(Collectors.toList());
        List<Method> methodList = Arrays.stream(BaseController.class.getDeclaredMethods()).collect(Collectors.toList());
        return Assert.notEmpty(includeMethods) ? includeRegister(includeList, methodList) : excludeRegister(excludeList, methodList);
    }

    /**
     * 包含策略
     */
    private Set<Method> includeRegister(List<String> includeList, List<Method> allMethods) {
        Set<Method> methodSet = new LinkedHashSet<>();
        for (Method method : allMethods) {
            String methodName = method.getName();
            if (includeList.contains(methodName)) {
                // 直接包含
                methodSet.add(method);
                continue;
            }
            // * 包含
            for (String s : includeList) {
                if (eligibleStar(methodName, s)) {
                    methodSet.add(method);
                    break;
                }
            }
        }
        return methodSet;
    }

    /**
     * 排除策略
     */
    private Set<Method> excludeRegister(List<String> excludeList, List<Method> allMethods) {
        Set<Method> methodSet = new LinkedHashSet<>(allMethods);
        Iterator<Method> iterator = methodSet.iterator();
        while (iterator.hasNext()) {
            Method method = iterator.next();
            String methodName = method.getName();
            if (excludeList.contains(methodName)) {
                // 直接排除
                iterator.remove();
                continue;
            }
            // * 排除
            for (String s : excludeList) {
                if (eligibleStar(methodName, s)) {
                    iterator.remove();
                    break;
                }
            }

        }
        return methodSet;
    }

    /**
     * 是否匹配 * 表达式
     */
    private static boolean eligibleStar(String methodName, String rule) {
        return methodName.matches(rule.replace("*", ".*"));
    }

    /**
     * 路由注册
     */
    private void doRegister(BuilderConfiguration config, RequestMappingHandlerMapping handlerMapping, String requestMapping, Object handler, Method method) {
        String path = String.format("%s/%s", requestMapping, method.getName());
        handlerMapping.registerMapping(RequestMappingInfo.paths(path).options(config).build(), handler, method);
        log.info("FastController Interface Successfully Registered. Path {}", path);
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

    /**
     * 获取 FastController 中指定的成员服务
     */
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
