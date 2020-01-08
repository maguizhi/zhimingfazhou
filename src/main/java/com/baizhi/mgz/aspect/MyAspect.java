package com.baizhi.mgz.aspect;

import com.baizhi.mgz.service.LogService;
import com.baizhi.mgz.entity.Log;
import com.baizhi.mgz.entity.Admin;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.UUID;


//@aspect  //代表切面
//@Pointcut  //代表切入点
//@Before()   //前置通知
//@After()    //后置通知
//@Around()   //环绕通知
//@AfterThrowing //异常通知

@Component
@Slf4j //日志
@Aspect
public class MyAspect {
    @Autowired
    HttpServletRequest request;
    @Autowired
    LogService logService;

//    @Pointcut(value = "execution(* com.baizhi.mgz.service.*.*(..))")
    @Pointcut(value = "@annotation(com.baizhi.mgz.aspect.LogAnnotation)")
    public void pt(){
        //空方法
    }

    @Around(value = "pt()")
    public Object cc(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("---环绕通知开始：方法执行之前---");
        //获取执行操作的用户
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        //获取执行时间
        Date date = new Date();
        //String name = proceedingJoinPoint.getSignature().getName();//获取做了什么事(执行方法的名称)
        //获取做了什么事
        //获取方法对象
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        //获取注解对象,之后获取注解对象的内容
        LogAnnotation annotation = method.getAnnotation(LogAnnotation.class);
        String value = annotation.value();
        //事情的执行结果
        boolean flag = false;

        Object proceed =null;
        try {
            //原始方法的返回值
            proceed = proceedingJoinPoint.proceed();
            log.info("this is  result=====>{}",proceed);
            flag = true;
            //必须返回 否则controller拿不到结果
            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw throwable;
        }finally {
            System.out.println(admin.getUsername());
            System.out.println(date);
            System.out.println(value);
            System.out.println(flag);
            log.info("person--->{},date--->{},thing--->{},flag--->{}", admin.getUsername(), date, value, flag);
            Log mylog = new Log(UUID.randomUUID().toString(), value, admin.getUsername(), date, flag);
            logService.addLog(mylog);
            log.info("---环绕通知结束：方法执行之后---");
        }

    }
}
