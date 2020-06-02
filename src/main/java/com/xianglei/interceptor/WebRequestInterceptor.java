package com.xianglei.interceptor;

import com.alibaba.fastjson.JSON;
import com.xianglei.annotation.VLogHunter;
import com.xianglei.domain.RequestLogVo;
import com.xianglei.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Auther: Xianglei
 * @Company: venusgroup
 * @Date: 2020/6/2 14:04
 * @Version 1.0
 * 通过拦截器，拦截所有的请求
 * 解析自定义注解 封装实体类
 */
@Component
public class WebRequestInterceptor implements HandlerInterceptor {

    @Autowired
    LogService logService;

    private final static Logger logger = LoggerFactory.getLogger(WebRequestInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("客户访问拦截，进入拦截逻辑");
        if (handler != null && handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            logger.info(JSON.toJSONString(method));
            // 直接获取注解，如果有注解，才生效
            VLogHunter vLogHunter = method.getAnnotation(VLogHunter.class);
            if (vLogHunter == null) {
                logger.info("该方法不存在该注解！");
            } else {
                Map<String, String[]> params = request.getParameterMap();
                RequestLogVo rle = new RequestLogVo();
                rle.setRequestCategory(vLogHunter.method().toString());
                rle.setRequestDesc(vLogHunter.description());
                rle.setRequestParams(JSON.toJSONString(params));
                rle.setRequestType(vLogHunter.method().toString());
                rle.setRequestUrl(JSON.toJSONString(vLogHunter.value()));
                rle.setSession(request.getSession().getId());
                logger.info(JSON.toJSONString(rle));
                // 调用service存储到数据库即可
                logService.addLog(rle);
            }
        }
        return true;
    }
}
