package miaosha.service;

import miaosha.dto.Exposer;
import miaosha.dto.SeckillExecution;
import miaosha.entity.Seckill;
import miaosha.exception.RepeatKillExecption;
import miaosha.exception.SeckillClose;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by fjc on 17-3-4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:miaosha/config/spring-dao.xml",
        "classpath:miaosha/config/spring-service.xml"})
public class SeckillServiceTest {
    Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> list=seckillService.getSeckillList();
            logger.info("list={}",list);
    }

    @Test
    public void getById() throws Exception {
        long id=1000L;
        Seckill seckill=seckillService.getById(id);
        logger.info("seckill={}",seckill);
    }

    @Test
    public void exportSeckillUrl() throws Exception {
        long id=1000L;
        Exposer exposer=seckillService.exportSeckillUrl(id);
        logger.info("exposer={}",exposer);
    }

    @Test
    public void executeSeckill() throws Exception {
        long id=1000;
        Exposer exposer=seckillService.exportSeckillUrl(id);
        if(exposer.isExposed()){
            logger.info("exposer={}",exposer);
            String md4=exposer.getMd5();
            long phone=12345678921L;
            try {
                SeckillExecution seckillExecution = seckillService.executeSeckill(id, md4, phone);
                logger.info("seckillExuction={}",seckillExecution);
            }catch (RepeatKillExecption e){
                logger.error(e.getMessage());
            }catch (SeckillClose e){
                logger.error(e.getMessage());
            }

        }else{
            logger.error("秒杀未开启");
        }

    }


    @Test
    public void executeSeckillProcedure() throws Exception {
        long id=1003;
        long phone=14245125124L;
        Exposer exposer=seckillService.exportSeckillUrl(id);
        if(exposer.isExposed()){
            String md5=exposer.getMd5();
            SeckillExecution seckillExecution=seckillService.executeSeckillProcedure(id,md5,phone);
            logger.info(seckillExecution.getStateInfo());

        }

    }


}