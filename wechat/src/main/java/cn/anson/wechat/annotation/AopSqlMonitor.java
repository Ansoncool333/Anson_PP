package cn.anson.wechat.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class AopSqlMonitor {
    //TODO 此处替换成mybatis相关方法
    @Around("execution(* org.springframework.jdbc.core.JdbcTemplate.query*(..)) " +
            "|| execution(* org.springframework.jdbc.core.JdbcTemplate.update*(..)) " +
            "|| execution(* org.springframework.jdbc.core.JdbcTemplate.execute*(..)) ")
    public Object monitor(ProceedingJoinPoint pjp) throws Throwable {
        if (pjp.getSignature().getName().startsWith("query")) {
//            EnvUtils.getEnv().startDatabaseRead(pjp.getArgs()[0].toString());
        } else {
//            EnvUtils.getEnv().startDatabaseUpdate(pjp.getArgs()[0].toString());
        }
        try {
            return pjp.proceed();
        } finally {
//            EnvUtils.getEnv().stopDatabaseOperation();
        }
    }

}