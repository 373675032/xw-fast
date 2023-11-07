package world.xuewei.fast.web.component;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import world.xuewei.fast.core.exception.*;
import world.xuewei.fast.web.dto.response.RespError;
import world.xuewei.fast.web.dto.response.RespResult;


import javax.servlet.http.HttpServletRequest;

/**
 * 异常处理器
 *
 * @author XUEW
 */
@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    /**
     * 接口限流异常处理
     */
    @ExceptionHandler(value = ServerBusyException.class)
    public RespResult handleBusinessException(BusinessException e, HttpServletRequest request) {
        log.error("服务器繁忙，错误信息：{}", e.getMessage());
        RespError error = new RespError();
        error.setCode("SERVER_BUSY");
        error.setMessage("服务器繁忙，请稍后再试");
        error.setRequestUrl(request.getRequestURL().toString());
        error.setException(e.getClass().getName());
        return error;
    }

    /**
     * 权限异常处理
     */
    @ExceptionHandler(value = AuthorityException.class)
    public RespResult handleAuthorityException(AuthorityException e, HttpServletRequest request) {
        log.error("操作权限不足，错误信息：{}", e.getMessage());
        RespError error = new RespError();
        error.setCode("NO_AUTHORITY");
        error.setMessage("当前用户无权限访问");
        error.setRequestUrl(request.getRequestURL().toString());
        error.setException(e.getClass().getName());
        return error;
    }

    /**
     * 参数为空异常
     */
    @ExceptionHandler(value = {ParamEmptyException.class})
    public RespResult handleParamEmptyException(ParamEmptyException e, HttpServletRequest request) {
        log.error("参数为空异常，错误信息：{}", e.getMessage());
        RespError error = new RespError();
        error.setCode("PARAM_EMPTY");
        error.setMessage(e.getMessage());
        error.setRequestUrl(request.getRequestURL().toString());
        error.setException(e.getClass().getName());
        return error;
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(value = {BusinessRunTimeException.class, BusinessException.class, Exception.class, RuntimeException.class})
    public RespResult handleBusinessException(Exception e, HttpServletRequest request) {
        log.error("业务异常，错误信息：{}", e.getMessage());
        RespError error = new RespError();
        error.setCode("FAIL");
        error.setMessage("系统异常");
        error.setData(e.getMessage());
        error.setRequestUrl(request.getRequestURL().toString());
        error.setException(e.getClass().getName());
        if (error.getException().contains("HttpMessageNotReadableException")) {
            error.setMessage("请求参数不可为空");
        }
        return error;
    }
}
