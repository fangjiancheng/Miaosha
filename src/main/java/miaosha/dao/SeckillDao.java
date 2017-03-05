package miaosha.dao;

import miaosha.entity.Seckill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by fjc on 17-3-4.
 */
public interface SeckillDao {
    /**
     *减库存
     * 返回减库存影响的行数
     * @param seckillId
     * @param killTime
     * @return
     */
    int reduceNumber(@Param("seckillId") long seckillId,@Param("killTime") Date killTime);

    /**
     *根据id查询
     * @param seckillId
     * @return
     */
    Seckill queryById(long seckillId);


    /**
     *
     * @param offset
     * @param limit
     * @return
     */
    List<Seckill> queryAll(@Param("offset")int offset,@Param("limit") int limit);

    void seckillByProduce(Map<String, Object> params);
}
