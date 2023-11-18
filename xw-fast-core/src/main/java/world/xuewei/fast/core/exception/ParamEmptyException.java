package world.xuewei.fast.core.exception;

/**
 * 参数为空异常类
 */
public class ParamEmptyException extends BusinessRunTimeException {

    private static final long serialVersionUID = -4879677283847539655L;

    public ParamEmptyException(int code, String message) {
        super(code, message);
        this.code = code;
        this.message = message;
    }

    public ParamEmptyException(String message, Object... objects) {
        super("");
        for (Object o : objects) {
            message = message.replaceFirst("\\{\\}", o.toString());
        }
        super.setMessage(message);
        this.message = message;
    }

    public static ParamEmptyException build(String param) {
        throw new ParamEmptyException("参数<" + param + ">为空");
    }

    /**
     * 设置原因
     *
     * @param cause 原因
     */
    public ParamEmptyException setCause(Throwable cause) {
        this.initCause(cause);
        return this;
    }
}