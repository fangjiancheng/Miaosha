package miaosha.exception;

/**
 * 重复秒杀异常(运行时候的异常)
 * Created by fjc on 16-12-8.
 */
public class RepeatKillExecption extends SeckillExecption {
    public RepeatKillExecption(String message) {
        super(message);
    }

    public RepeatKillExecption(String message, Throwable cause) {
        super(message, cause);
    }
}
