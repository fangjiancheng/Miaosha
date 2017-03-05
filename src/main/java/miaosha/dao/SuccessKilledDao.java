package miaosha.dao;

import miaosha.entity.SuccessKilled;
import org.apache.ibatis.annotations.Param;

/**
 * Created by fjc on 17-3-4.
 */
public interface SuccessKilledDao {
    /**
     *插入购买明细可以过滤重复
     * 返回插入结果影响的行数
     * @param seckillId
     * @param usePhone
     * @return
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId,@Param("userPhone") long usePhone);

    /**
     * 查询秒杀记录
     * @param seckillId
     * @param userPhone
     * @return
     */
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId,@Param("userPhone")long userPhone);

}
