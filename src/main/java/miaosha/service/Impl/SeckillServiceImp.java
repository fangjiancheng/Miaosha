package miaosha.service.Impl;

import miaosha.dao.SeckillDao;
import miaosha.dao.SuccessKilledDao;
import miaosha.dto.Exposer;
import miaosha.dto.SeckillExecution;
import miaosha.entity.Seckill;
import miaosha.entity.SuccessKilled;
import miaosha.enums.SeckillState;
import miaosha.exception.RepeatKillExecption;
import miaosha.exception.SeckillClose;
import miaosha.exception.SeckillExecption;
import miaosha.service.SeckillService;
import miaosha.util.Md5Util;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import miaosha.dao.cache.redisDao;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fjc on 17-3-4.
 */
@Service
public class SeckillServiceImp implements SeckillService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    @Autowired
    private redisDao redisD;

    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 4);
    }

    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {
//        使用redis优化
        Seckill seckill = redisD.getSeckill(seckillId);
        if (seckill == null) {
            seckill = seckillDao.queryById(seckillId);
            if (seckill == null) {
                return new Exposer(false, seckillId);
            } else {
                redisD.putSeckill(seckill);
            }
        }


        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date date = new Date();
        if (date.getTime() < startTime.getTime() || date.getTime() > endTime.getTime()) {
            return new Exposer(false, seckillId, date.getTime(), startTime.getTime(), endTime.getTime());
        }
        String md5 = Md5Util.getMd5(seckillId);
        return new Exposer(true, md5, seckillId);
    }


    @Transactional
    public SeckillExecution executeSeckill(long seckillId, String md5, long userPhone) throws SeckillExecption, SeckillClose, RepeatKillExecption {
        if (md5 == null || !md5.equals(Md5Util.getMd5(seckillId))) {
            throw new SeckillExecption("seckill data rewite");
        }
        Date now = new Date();
        try {
            int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
            if (insertCount <= 0) {
                throw new RepeatKillExecption("重复秒杀");
            } else {
                int updatecount = seckillDao.reduceNumber(seckillId, now);
                if (updatecount <= 0) {
                    throw new SeckillClose("秒杀结束");
                } else {
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillState.SUCCESS, successKilled);
                }
            }
        } catch (SeckillClose e1) {
            throw e1;
        } catch (RepeatKillExecption e2) {
            throw e2;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SeckillExecption("innorError");
        }

    }


    public SeckillExecution executeSeckillProcedure(long seckillId, String  md5, long userPhone){
        if(md5 == null || !md5.equals(Md5Util.getMd5(seckillId))){//数据篡改
            return new SeckillExecution(seckillId, SeckillState.DATE_REWRITE);
        }
        Date now = new Date();
        Map params = new HashMap();
        params.put("seckillId", seckillId);
        params.put("phone", userPhone);
        params.put("killTime", now);
        params.put("result", null);
        try {
            seckillDao.seckillByProduce(params);
            Integer result = MapUtils.getInteger(params, "result", -3);
            if(result == 1){//成功
                SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                return new SeckillExecution(seckillId, SeckillState.SUCCESS, successKilled);
            }else{//如果失败，把错误状态-1或者-2等返回
                return new SeckillExecution(seckillId, SeckillState.stateOf(result));
            }
        } catch (Exception e){//出现异常也要返回
            logger.error(e.getMessage(), e);
            return new SeckillExecution(seckillId, SeckillState.INNER_ERROR);
        }
    }
}
