package com.xianglei.controller;

import com.xianglei.annotation.VLogHunter;
import com.xianglei.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: Xianglei
 * @Company: venusgroup
 * @Date: 2020/6/2 14:04
 * @Version 1.0
 */
@RestController
public class DemoController {

    private final static Logger logger = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    LogService userService;

    @RequestMapping(value = {"/{name}"}, method = RequestMethod.GET)
    @VLogHunter(value = {"view/aaa", "view/bbb", "view/ccc"}, description = "this is indexContrller!", method = VLogHunter.MethodType.GET)
    public String index(@PathVariable String name) {
        logger.info("\n====================名称为\t" + name + "====================\n");
        return "我的名字是:\t" + name;
    }
}
