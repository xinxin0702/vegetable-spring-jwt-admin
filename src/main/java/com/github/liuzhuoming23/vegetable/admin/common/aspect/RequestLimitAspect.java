package com.github.liuzhuoming23.vegetable.admin.common.aspect;

import com.alibaba.fastjson.JSONObject;
import com.github.liuzhuoming23.vegetable.admin.common.annotation.RequestLimit;
import com.github.liuzhuoming23.vegetable.admin.common.cons.RedisKey;
import com.github.liuzhuoming23.vegetable.admin.common.exception.SveaException;
import com.github.liuzhuoming23.vegetable.admin.common.redis.RedisOperation;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 请求次数限制切面
 *
 * @author liuzhuoming
 */
@Aspect
@Component
@Slf4j
public class RequestLimitAspect {

    @Autowired
    private RedisOperation redisOperation;

    @Pointcut("@annotation(com.github.liuzhuoming23.vegetable.admin.common.annotation.RequestLimit)")
    public void pointcut() {
        //do nothing...
    }

    @Before("pointcut()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
            .getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        String ip = request.getRemoteAddr();
        String uri = request.getRequestURI();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RequestLimit annotation = method.getAnnotation(RequestLimit.class);

        int count = annotation.count();
        long interval = annotation.interval();

        String field = ip + "::" + uri;

        String json = redisOperation.value().get(RedisKey.LIMIT_HASH_KEY + "::" + field);

        int times = 1;
        long ts = System.currentTimeMillis();

        //redis信息不存在
        if (StringUtils.isNotEmpty(json)) {
            JSONObject jsonObject = JSONObject.parseObject(json);

            int timesRedis = jsonObject.getInteger("times") + 1;
            long tsRedis = jsonObject.getLong("ts");

            //redis存的过期时间小于当前时间（时间限制解除），重设限制时间
            if (tsRedis >= ts) {
                //redis存的请求次数大于设置的最大请求次数，则请求失败
                if (timesRedis > count) {
                    throw new SveaException(String.format(
                        "exceeding the maximum number of requests, limit %d times every %d millisecond",
                        count, interval));
                } else {
                    ts = tsRedis;
                    times = timesRedis;
                }
            } else {
                ts += interval;
            }
        }

        Map<String, Object> params = new HashMap<>(2);
        params.put("times", times);
        params.put("ts", ts);

        redisOperation.value()
            .set(RedisKey.LIMIT_HASH_KEY + "::" + field, JSONObject.toJSONString(params));
        redisOperation.expire(RedisKey.LIMIT_HASH_KEY + "::" + field,
            new Date(System.currentTimeMillis() + interval));
    }
}
