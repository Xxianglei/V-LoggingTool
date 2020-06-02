package com.xianglei.service;

import com.xianglei.domain.RequestLogVo;

public interface LogService {
	/**
	 * 添加拦截信息到数据库
	 * @param requestLogVo 请求数据封装vo
	 * @return Boolean
	 */
	Boolean addLog(RequestLogVo requestLogVo) ;
}
