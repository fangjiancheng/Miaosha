package miaosha.dao.cache;

import miaosha.dao.SeckillDao;
import miaosha.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by fjc on 17-3-4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:miaosha/config/spring-dao.xml")
public class redisDaoTest {
    private long id=1000L;
    @Autowired
    private redisDao redisDao;
    @Autowired
    private SeckillDao seckillDao;

    @Test
    public void TestSeckill() throws Exception {
        //测试get和put方法
        Seckill seckill=redisDao.getSeckill(id);
        if(seckill==null){
            seckill=seckillDao.queryById(id);
            if(seckill!=null){
                String result=redisDao.putSeckill(seckill);
                System.out.println(result);
                seckill=redisDao.getSeckill(id);
                System.out.println(seckill);
            }
        }

    }


}