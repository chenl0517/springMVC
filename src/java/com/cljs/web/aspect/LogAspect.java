package com.cljs.web.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

@Aspect
@Service
public class LogAspect {
	
//	@After("execution(* com.cljs.web.servers.*.*(..)) && args(name)")
//	public void sayByb(String name){
//		System.out.println("bye........................"+name);
//	}
	
	@After("execution(* com.cljs.web.servers.*.*(..))")
	public void sayByb(JoinPoint jp){
		System.out.println(jp.getTarget().getClass().getName());
		System.out.println(jp.getThis().getClass().getName());
		System.out.println("bye........................"+jp.getArgs()[0]);
	}
	
//	@AfterThrowing(pointcut="execution(* com.cljs.web.servers.*.*(..))",throwing="ex")
//	public void sayException(JoinPoint jp,Exception ex){
//		Signature signature=jp.getSignature();
//		
//		System.out.println(signature.getName());
//		
//		System.out.println("呟械俺彌。。。。。。。。。"+ex.getMessage());
//		//ex.printStackTrace();
//	}

}
