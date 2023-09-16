package com.example.study1.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class TimeTraceAop {
    @Around("execution(* hello.hellospring..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{
       long start = System.currentTimeMillis();
       System.out.println("START : " + joinPoint.toString());
       try{
        Object result = joinPoint.proceed();
       //proceed() : 다음 메소드로 진행 / 해당 결과를 result 변수로 저장
           return result;
    }
       finally {
           long finish = System.currentTimeMillis();
            long timeMS = finish - start;
           System.out.println("END : " + joinPoint.toString() + " " + timeMS + "ms");

       }
       }

}
