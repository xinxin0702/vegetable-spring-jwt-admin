package com.github.liuzhuoming23.vegetable.admin.common.aspect;

import com.alibaba.fastjson.JSONObject;
import com.github.liuzhuoming23.vegetable.admin.app.domain.Account;
import com.github.liuzhuoming23.vegetable.admin.app.domain.LogDetail;
import com.github.liuzhuoming23.vegetable.admin.app.service.LogDetailService;
import com.github.liuzhuoming23.vegetable.admin.common.annotation.Log;
import com.github.liuzhuoming23.vegetable.admin.common.context.AccountContext;
import com.github.liuzhuoming23.vegetable.admin.common.properties.SveaProperties;
import java.lang.reflect.Method;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;

/**
 * 日志切面
 *
 * @author liuzhuoming
 */
@Aspect
@Component
public class LogAspect {

    @Autowired
    private LogDetailService logDetailService;
    @Autowired
    private SveaProperties sveaProperties;

    @Pointcut("@annotation(com.github.liuzhuoming23.vegetable.admin.common.annotation.Log)")
    public void pointcut() {
        //do nothing...
    }

    @Around("pointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        Exception ex = null;
        long start = System.currentTimeMillis();
        try {
            result = joinPoint.proceed();
            return result;
        } catch (Exception e) {
            ex = e;
            throw e;
        } finally {
            long end = System.currentTimeMillis();
            addLogDetail(joinPoint, result, end - start, ex);
        }
    }

    /**
     * 添加日志记录
     *
     * @param joinPoint 切入点
     * @param result 运行结果
     * @param runTime 运行时间
     * @param ex 异常信息
     */
    private void addLogDetail(JoinPoint joinPoint, Object result, long runTime, Exception ex) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Account account = AccountContext.get();
        Log annotation = method.getAnnotation(Log.class);
        Object[] args = joinPoint.getArgs();
        LocalVariableTableParameterNameDiscoverer localVariableTableParameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = localVariableTableParameterNameDiscoverer.getParameterNames(method);

        //如果日志等级小于设置的日志保存等级，则不保存
        if (annotation == null || annotation.level().getVal() < sveaProperties.getLogLevel()) {
            return;
        }

        StringBuilder params = new StringBuilder();
        if (args != null && paramNames != null) {
            for (int i = 0; i < args.length; i++) {
                Object arg = args[i];
                String paramName = paramNames[i];
                if (sveaProperties.getSensitiveFields().contains(paramName)) {
                    params.append(paramName).append("=").append("******").append(",");
                } else {
                    params.append(paramName).append("=").append(JSONObject.toJSONString(arg))
                        .append(",");
                }
            }
        }

        if (params.length() > 0) {
            params.deleteCharAt(params.length() - 1);
        }

        LogDetail logDetail = new LogDetail();
        //操作账号
        if (account != null) {
            logDetail.setAccountId(account.getId());
            logDetail.setUsername(account.getUsername());
        }

        //运行信息
        logDetail.setRunTime(runTime);

        //方法信息
        logDetail.setMethod(signature.getDeclaringTypeName() + "." + signature.getName());
        logDetail.setArgs(params.toString());
        if (result != null) {
            logDetail.setReturnVal(JSONObject.toJSONString(result));
        }

        //注解信息
        logDetail.setLevel(annotation.level().getVal());
        logDetail.setDescription(annotation.description());

        //异常信息
        if (ex != null) {
            logDetail.setException(ex.toString());
        }

        logDetailService.insert(logDetail);
    }
}
