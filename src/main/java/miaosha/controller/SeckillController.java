package miaosha.controller;

import com.sun.javafx.sg.prism.NGShape;
import miaosha.dto.Exposer;
import miaosha.dto.SeckillExecution;
import miaosha.dto.SeckillResult;
import miaosha.entity.Seckill;
import miaosha.enums.SeckillState;
import miaosha.exception.RepeatKillExecption;
import miaosha.exception.SeckillClose;
import miaosha.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by fjc on 17-3-4.
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @RequestMapping(name = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        List<Seckill> seckills = seckillService.getSeckillList();
        model.addAttribute("list", seckills);
        return "list";
    }

    @RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId, Model model) {
        if (seckillId == null) {
            return "redirect:/seckill/list";
        }
        Seckill seckill = seckillService.getById(seckillId);
        if (seckill == null) {
            return "foward://seckill/list";
        }
        model.addAttribute("seckill", seckill);
        return "detail";
    }

    @RequestMapping(value = "/{seckillId}/exposer", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId) {
        SeckillResult<Exposer> result;
        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(true, exposer);

        } catch (Exception e) {
            result = new SeckillResult<Exposer>(false, e.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/{seckillId}/{md5}/execution", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
                                                   @CookieValue(value = "killPhone", required = false) Long userPhone,
                                                   @PathVariable("md5") String md5) {
        SeckillResult<SeckillExecution> result = null;

        if (userPhone == null) {
            result = new SeckillResult<SeckillExecution>(false, "未注册");
        }
        try {
            SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, md5, userPhone);
            result = new SeckillResult<SeckillExecution>(true, seckillExecution);
        } catch (RepeatKillExecption e) {
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillState.REAPEAT_KILL);
            result = new SeckillResult<SeckillExecution>(true, seckillExecution);
        } catch (SeckillClose e) {
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillState.END);
            result = new SeckillResult<SeckillExecution>(true, seckillExecution);
        } catch (Exception e) {
            SeckillExecution seckillExecution=new SeckillExecution(seckillId, SeckillState.INNER_ERROR);
            result=new SeckillResult<SeckillExecution>(false,seckillExecution);
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    //获取系统时间
    @RequestMapping(value = "/time/now",method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long> time()
    {
        Date now=new Date();
        return new SeckillResult<Long>(true,now.getTime());
    }

}






























