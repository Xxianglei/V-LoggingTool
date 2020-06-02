package com.xianglei.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xianglei.domain.RequestLogDo;
import org.springframework.stereotype.Repository;

/**
 * @Auther: Xianglei
 * @Company: venusgroup
 * @Date: 2020/6/2 14:27
 * @Version 1.0
 * 日志持久化mapper
 */
@Repository
public interface LogMapper extends BaseMapper<RequestLogDo> {
}
