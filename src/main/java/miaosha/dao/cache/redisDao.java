package miaosha.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import miaosha.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 缓存优化代码
 * Created by fjc on 16-12-9.
 */
public class redisDao {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final JedisPool jedisPool;

    private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);

    public redisDao(String ip, int port) {
        jedisPool = new JedisPool(ip, port);
    }

    public Seckill getSeckill(long seckillId) {
        //redis逻辑操作
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckill" + seckillId;
//                a并没有实现内部序列化操作
//                get->byte[] 反序列化　->Object(Seckill)
//                采用自定义序列化
                byte[] bytes = jedis.get(key.getBytes());
//                不为空就是得到了,进行反序列化
                if (bytes != null) {
//                    空对象
                    Seckill seckill = schema.newMessage();
                    ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);
                    return seckill;
                }

            } finally {
                jedis.close();
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public String putSeckill(Seckill seckill) {
        //将对象序列化成数组
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckill" + seckill.getSeckillId();
                byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                int timeout=60*60;//缓存一个小时
                String result=jedis.setex(key.getBytes(),timeout,bytes);
//                System.out.println(result);
                return result;
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }
}
