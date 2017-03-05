package miaosha.dao;

import miaosha.entity.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by fjc on 17-3-4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:miaosha/config/spring-dao.xml")
public class SuccessKilledDaoTest {
    @Resource
    private SuccessKilledDao successKilledDao;
    @Test
    public void insertSuccessKilled() throws Exception {
        long id=1004;
        long phone=12345678911L;
        int number=successKilledDao.insertSuccessKilled(id,phone);
        System.out.println(number);
    }

    @Test
    public void queryByIdWithSeckill() throws Exception {
        long id=1000;
        long phone=12345678911L;
        SuccessKilled successKilled=successKilledDao.queryByIdWithSeckill(id,phone);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());
    }

}