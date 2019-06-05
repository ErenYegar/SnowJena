package cn.yueshutong.currentlimitingticketserver.rule;

import cn.yueshutong.commoon.entity.LimiterRule;
import cn.yueshutong.currentlimitingticketserver.rule.entity.Result;
import cn.yueshutong.currentlimitingticketserver.rule.service.RuleService;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RuleController {
    @Autowired
    private RuleService ruleService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 心跳包
     * @param rule 规则字符串
     * @return 最新规则字符串
     */
    @RequestMapping(value = "/heart",method = RequestMethod.POST)
    public String heartbeat(@RequestParam("data") String rule){
        logger.debug("heart:"+rule);
        LimiterRule limiterRule = JSON.parseObject(rule, LimiterRule.class);
        return JSON.toJSONString(ruleService.heartbeat(limiterRule));
    }

    /**
     * @return 获取实例的规则
     */
    @RequestMapping(value = "/rule",method = RequestMethod.GET)
    public Result<LimiterRule> get(String app, String id, String name,int page,int limit){
        return ruleService.getAll(app, id, name, page, limit);
    }

    /**
     * 修改限流规则
     * @return true/false
     */
    @RequestMapping(value = "/rule",method = RequestMethod.PUT)
    public Result update(@RequestParam("data") String rule){
        LimiterRule limiterRule = JSON.parseObject(rule, LimiterRule.class);
        boolean update = ruleService.update(limiterRule);
        Result result = new Result();
        result.setMsg(Boolean.toString(update));
        return result;
    }

}