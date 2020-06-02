package com.xianglei.service.impl;

import cn.hutool.core.date.DateTime;
import com.xianglei.domain.RequestLogDO;
import com.xianglei.domain.RequestLogVO;
import com.xianglei.mapper.LogMapper;
import com.xianglei.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * @Auther: Xianglei
 * @Company: venusgroup
 * @Date: 2020/6/2 14:04
 * @Version 1.0
 * 日志service
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    LogMapper logMapper;

    private final static Logger logger = LoggerFactory.getLogger(LogServiceImpl.class);

    @Transactional
    @Override
    public Boolean addLog(RequestLogVO requestLogVo) {
        logger.info("审计日志持久化到数据库");
        RequestLogDO requestLogDo = new RequestLogDO();
        requestLogDo.setCreateDate(DateTime.now());
        requestLogDo.setFlowId(UUID.randomUUID().toString());
        requestLogDo.setRequestCategory(requestLogVo.getRequestCategory());
        requestLogDo.setRequestDesc(requestLogVo.getRequestDesc());
        requestLogDo.setRequestParams(requestLogVo.getRequestParams());
        requestLogDo.setRequestType(requestLogVo.getRequestType());
        requestLogDo.setRequestUrl(requestLogVo.getRequestUrl());
        requestLogDo.setSession(requestLogVo.getSession());
        int insert = logMapper.insert(requestLogDo);
        return insert != 0;
    }
}
