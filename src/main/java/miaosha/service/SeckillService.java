package miaosha.service;

import miaosha.dto.Exposer;
import miaosha.dto.SeckillExecution;
import miaosha.entity.Seckill;
import miaosha.exception.*;


import java.util.List;

/**
 * Created by fjc on 16-12-7.
 * 业务接口：站在使用者的角度设计接口
  *方法定义力度，参数，返回类型/异常
 */
public interface SeckillService {
    /**
     * 查询所有秒杀记录
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * 获取单个秒杀记录
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);


    /**
     * 秒杀执行时输出秒杀接口地址
     * 否则输出系统时间和秒杀时间
     * 从而使得用户不能提前知道url地址，防止脚本秒杀
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     *执行秒杀操作
     * @param seckillId
     * @param md5
     * @param userPhone
     */
    SeckillExecution executeSeckill(long seckillId, String  md5, long userPhone) throws SeckillExecption,SeckillClose,RepeatKillExecption;

    /**
     *执行秒杀操作
     * @param seckillId
     * @param md5
     * @param userPhone
     */
    SeckillExecution executeSeckillProcedure(long seckillId, String  md5, long userPhone);

}
