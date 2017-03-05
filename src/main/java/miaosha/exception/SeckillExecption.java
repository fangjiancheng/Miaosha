package miaosha.exception;

/**
 * 所有业务相关异常
 * Created by fjc on 16-12-8.
 */
public class SeckillExecption extends RuntimeException{
    public SeckillExecption(String message) {
        super(message);
    }

    public SeckillExecption(String message, Throwable cause) {
        super(message, cause);
    }
}
