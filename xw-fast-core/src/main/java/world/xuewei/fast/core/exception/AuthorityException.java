package world.xuewei.fast.core.exception;

/**
 * 权限异常类
 */
public class AuthorityException extends BusinessException {

    private static final long serialVersionUID = -4879677283847539655L;

    public AuthorityException(int code, String message) {
        super(code, message);
        this.code = code;
        this.message = message;
    }

    public AuthorityException(String message, Object... objects) {
        super("");
        for (Object o : objects) {
            message = message.replaceFirst("\\{\\}", o.toString());
        }
        super.setMessage(message);
        this.message = message;
    }

    /**
     * 设置原因
     *
     * @param cause 原因
     */
    public AuthorityException setCause(Throwable cause) {
        this.initCause(cause);
        return this;
    }
}