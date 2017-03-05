package miaosha.exception;

/**
 * 秒杀关闭异常
 * Created by fjc on 16-12-8.
 */
public class SeckillClose extends SeckillExecption{
    public SeckillClose(String message) {
        super(message);
    }

    public SeckillClose(String message, Throwable cause) {
        super(message, cause);
    }
}
