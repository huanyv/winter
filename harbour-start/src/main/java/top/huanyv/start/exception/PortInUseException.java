package top.huanyv.start.exception;

/**
 * 端口占用异常
 *
 * @author huanyv
 * @date 2023/2/20 13:49
 */
public class PortInUseException extends RuntimeException {
    public PortInUseException(String message) {
        super(message);
    }
}
