package cn.decentchina.manager.log;

import cn.decentchina.manager.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Parameter;
import java.util.List;

/**
 * @author 张超
 * @date 2018-08-30
 */
@Aspect
@Component
@Slf4j
public class logAspect {
    @Pointcut("@annotation(org.springframework.stereotype.Controller) || @annotation(org.springframework.web.bind.annotation.RestController)")
    private void controllerAspect() {
    }

    /**
     * 日志切面
     *
     * @param pjp
     * @return
     */
    @Around("controllerAspect()")
    public Object doAround(ProceedingJoinPoint pjp) {
        // 拦截的实体类，就是当前正在执行的controller
        Object target = pjp.getTarget();
        // 拦截的方法名称。当前正在执行的方法
        String methodName = pjp.getSignature().getName();
        // 拦截的方法参数
        Object[] args = pjp.getArgs();
        // 拦截的放参数类型
        Signature sig = pjp.getSignature();
        MethodSignature msig = null;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        msig = (MethodSignature) sig;
        Parameter[] parameters = msig.getMethod().getParameters();
        StringBuilder requestMethod = new StringBuilder(200);
        requestMethod.append(target.getClass().getSimpleName()).append(" ");
        requestMethod.append(methodName).append(" ");
        StringBuilder requestInfo = new StringBuilder(200);
        requestInfo.append(requestMethod);
        requestInfo.append(" params ");
        int i = 0;
        for (Parameter parameter : parameters) {
            requestInfo.append(parameter.getName()).append(" ");
            requestInfo.append(args[i++]).append(" ");
        }
        log.info(requestInfo.toString());
        Object object = null;
        try {
            object = pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        if (object instanceof List) {
            log.info(requestMethod.append(" response ") + JsonUtil.JavaList2Json((List) object));
        } else {
            log.info(requestMethod.append(" response ") + JsonUtil.Java2Json(object));
        }
        return object;
    }


}
