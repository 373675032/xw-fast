package world.xuewei.fast.core.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 业务运行时异常类
 */
@Setter
@Getter
public class BusinessRunTimeException extends RuntimeException {

    private static final long serialVersionUID = -4879677283847539655L;

    protected int code;

    protected String message;

    public BusinessRunTimeException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BusinessRunTimeException(String message, Object... objects) {
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
    public BusinessRunTimeException setCause(Throwable cause) {
        this.initCause(cause);
        return this;
    }
}