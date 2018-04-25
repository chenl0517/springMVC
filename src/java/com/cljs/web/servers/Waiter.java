package com.cljs.web.servers;

import org.springframework.stereotype.Service;

@Service
public class Waiter {
	
	public void sayHello(String clname){
		System.err.println("hello............"+clname);
	}
	public void sayHello(){
		System.err.println("hello2222............");
	}
	
	public void happenExcep(){
			System.out.println(1/0);
	}

}
