package com.cljs.web.scan;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Lazy(value=false)
public class HelloService {
	
	public HelloService(){
		System.out.println("=============constructor===========");
	}
	
	public void say(String name){
		System.out.println("==============================="+name);
	}

}
