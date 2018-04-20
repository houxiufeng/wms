package com.home.wms.service.aspect;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by fitz on 2018/4/19.
 */
@Component
@Aspect
public class LogAspect {

    private static final Logger LOG = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut(value = "execution(* com.home.wms.service.impl..*.*(..))")
    private void writeLog() {}

    @Around(value="writeLog()")
    public Object methodLog(final ProceedingJoinPoint point) throws Throwable{
        String methodName = point.getSignature().getName();
        Long beginMils = System.currentTimeMillis();
        Object[] args = point.getArgs();
        LOG.info(StrUtil.format("-----{}--args:{}", methodName, JSONObject.toJSONString(args)));
        Object object = point.proceed();
        LOG.info(StrUtil.format("-----{}--used:{}"), methodName, (System.currentTimeMillis() - beginMils));
        return object;
    }
}
