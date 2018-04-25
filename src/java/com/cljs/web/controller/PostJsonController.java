package com.cljs.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cljs.web.pojo.User;

@RequestMapping("/post")
@Controller
public class PostJsonController {
	@RequestMapping("/userinfo")
	@ResponseBody
	public User userinfo(String name,String age,HttpServletRequest request){
		System.out.println("=========content-type========="+request.getContentType());
		User user=new User();
		//user.setId(Integer.parseInt(age));
		//user.setName(name);
		return user;
	}
	
	@RequestMapping("/userinfojson")
	@ResponseBody
	public User userinfojson(@RequestBody String msg,HttpServletRequest request){
		System.out.println("=========content-type========="+request.getContentType());
		User user=new User();
		System.out.println("======================"+msg);
		return user;
	}
}
