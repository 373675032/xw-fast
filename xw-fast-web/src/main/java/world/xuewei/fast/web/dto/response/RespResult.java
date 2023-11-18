package world.xuewei.fast.web.dto.response;

import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 接口相应结果
 *
 * @author XUEW
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RespResult {

    /**
     * 响应编码
     */
    protected String code;

    /**
     * 响应信息
     */
    protected String message;

    /**
     * 响应数据
     */
    protected Object data;

    /**
     * 请求成功
     */
    public static RespResult success() {
        return RespResult.builder()
                .code("SUCCESS")
                .message("请求成功")
                .build();
    }

    /**
     * 请求成功
     */
    public static RespResult success(String message) {
        return RespResult.builder()
                .code("SUCCESS")
                .message(message)
                .build();
    }

    /**
     * 请求成功
     */
    public static RespResult success(String message, Object data) {
        return RespResult.builder()
                .code("SUCCESS")
                .message(message)
                .data(data)
                .build();
    }


    /**
     * 未查询到数据
     */
    public static RespResult notFound() {
        return RespResult.builder()
                .code("NOT_FOUND")
                .message("请求资源不存在")
                .build();
    }

    /**
     * 未查询到数据
     */
    public static RespResult notFound(String message) {
        return RespResult.builder()
                .code("NOT_FOUND")
                .message(String.format("%s不存在", message))
                .build();
    }

    /**
     * 未查询到数据
     */
    public static RespResult notFound(String message, Object data) {
        return RespResult.builder()
                .code("NOT_FOUND")
                .message(String.format("%s不存在", message))
                .data(data)
                .build();
    }

    /**
     * 请求参数不能为空
     */
    public static RespResult paramEmpty() {
        return RespResult.builder()
                .code("PARAM_EMPTY")
                .message("请求参数为空")
                .build();
    }

    /**
     * 请求参数不能为空
     */
    public static RespResult paramEmpty(String message) {
        return RespResult.builder()
                .code("PARAM_EMPTY")
                .message(String.format("%s参数为空", message))
                .build();
    }

    /**
     * 请求参数不能为空
     */
    public static RespResult paramEmpty(String message, Object data) {
        return RespResult.builder()
                .code("PARAM_EMPTY")
                .message(String.format("%s参数为空", message))
                .data(data)
                .build();
    }

    /**
     * 请求失败
     */
    public static RespResult fail() {
        return RespResult.builder()
                .code("FAIL")
                .message("请求失败")
                .build();
    }

    /**
     * 请求失败
     */
    public static RespResult fail(String message) {
        return RespResult.builder()
                .code("FAIL")
                .message(message)
                .build();
    }

    /**
     * 请求失败
     */
    public static RespResult fail(String message, Object data) {
        return RespResult.builder()
                .code("FAIL")
                .message(message)
                .data(data)
                .build();
    }

    /**
     * 请求是否成功
     */
    public boolean yesSuccess() {
        return "SUCCESS".equals(code);
    }

    /**
     * 请求是否成功并有数据返回
     */
    public boolean yesSuccessWithDateResp() {
        return "SUCCESS".equals(code) && ObjectUtil.isNotEmpty(data);
    }

    /**
     * 请求是否成功
     */
    public boolean notSuccess() {
        return !yesSuccess();
    }
}
