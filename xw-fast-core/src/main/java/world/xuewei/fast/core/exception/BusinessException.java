package world.xuewei.fast.core.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 业务异常类
 */
@Setter
@Getter
public class BusinessException extends Exception {

    private static final long serialVersionUID = -4879677283847539655L;

    protected int code;

    protected String message;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BusinessException(String message, Object... objects) {
        super(message);
        for (Object o : objects) {
            message = message.replaceFirst("\\{\\}", o.toString());
        }
        this.message = message;
    }

    /**
     * 设置原因
     *
     * @param cause 原因
     */
    public BusinessException setCause(Throwable cause) {
        this.initCause(cause);
        return this;
    }
}