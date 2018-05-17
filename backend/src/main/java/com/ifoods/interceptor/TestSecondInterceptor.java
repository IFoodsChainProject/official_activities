package com.ifoods.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.ifoods.utils.ThreadLocalManager;
import com.ifoods.utils.entity.ConsoleContext;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author zhenghui.li
 * @date 2018年4月17日
 * 
 * 测试拦截器 1;
 */
@Component
@Slf4j
public class TestSecondInterceptor extends HandlerInterceptorAdapter {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                throws Exception {
            log.info("test second interceptor");
            log.info("request header: {}", JSON.toJSONString(request.getHeaderNames()));
            if(! setConsoleContext(request)) {
                log.warn("set local content failed. traceID: " + request.getHeader("traceID"));
                return false;
            }
            String requestURI = request.getRequestURI();
            //BuriedPoint.write(null,request.getRequestURI());
            /*if (handler instanceof HandlerMethod) {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                Method method = handlerMethod.getMethod();
                Atomic annotation = method.getAnnotation(Atomic.class);
                if (annotation != null) {
                    String traceID = AtomicUtils.getTraceID(request);
                    String result;
                    if (traceID != null && !StringUtils.isEmpty(result = AtomicUtils.updateResult(traceID, AtomicUtils.WAITING, ""))) {
                        JSONObject res = JSON.parseObject(result);
                        res.put(AtomicUtils.TRACEID, traceID);
                        setResult(response, res.toJSONString());
                        return false;
                    }
                }
                return true;
            } else {
                return super.preHandle(request, response, handler);
            }*/
            return super.preHandle(request, response, handler);
        }
        
        private boolean setConsoleContext(HttpServletRequest request) {
            try {
                ConsoleContext context = new ConsoleContext();
                /*context.setRequest(request);
                context.setBeginMs(System.currentTimeMillis());*/
                String traceID = request.getHeader("traceID");
                /*if(traceID==null || traceID.isEmpty()) {
                    context.setTraceId(TraceUtil.newTraceId());
                }else {
                    context.setTraceId(request.getHeader("traceID"));
                }

                User user = UserHolder.getCurrentUser().get();
                if(user!=null) {
                    context.setOperEmpId(user.getUserId());
                    context.setOperEmpName(user.getUserName());
                    context.setGroupId(user.getGroupId());
                }else {// exclude old
                    log.warn("error in getUser. url is:"+request.getRequestURL());
                    //return false;
                }*/
                ThreadLocalManager.setConsoleContext(context);
            } catch (Exception e) {
                log.error("error in preHandle getUser. url is:"+request.getRequestURL(), e);
                return false;
            }
            return true;
        }
}
