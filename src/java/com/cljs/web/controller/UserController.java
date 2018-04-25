package com.cljs.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cljs.web.pojo.Friend;
import com.cljs.web.pojo.User;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@RequestMapping(value="/mytest")
	@ResponseBody
	public User sys(User user){
		//System.out.println("=========="+user.getName());
		
		 List noFriends=new ArrayList();
		 List herFriends=new ArrayList();
		 List youFriends=new ArrayList();
		 List hahFriends=new ArrayList();
		for(int i=0;i<50;i++){
			Friend f=new Friend();
			f.setId(i+10);
			f.setName("name"+i+100);
			f.setA1("a#"+i);
			f.setA2("a#"+i);
			f.setA3("a#"+i);
			f.setA4("a#"+i);
			f.setA5("a#"+i);
			f.setA6("a#"+i);
			f.setA7("a#"+i);
			f.setA8("a#"+i);
			f.setA9("a#"+i);
			f.setA10("a#"+i);
			f.setA11("a#"+i);
			f.setA12("a#"+i);
			noFriends.add(f);
		}
		for(int i=0;i<3;i++){
			Friend f=new Friend();
			f.setId(i+15);
			f.setName("name#"+i+100);
			f.setA1("a@"+i);
			f.setA2("a@"+i);
			f.setA3("a@"+i);
			f.setA4("a@"+i);
			f.setA5("a@"+i);
			f.setA6("a@"+i);
			f.setA7("a@"+i);
			f.setA8("a@"+i);
			f.setA9("a@"+i);
			f.setA10("a@"+i);
			f.setA11("a@"+i);
			f.setA12("a@"+i);
			herFriends.add(f);
		}
		for(int i=0;i<2;i++){
			Friend f=new Friend();
			f.setId(i+500);
			f.setName("name@"+i+100);
			f.setA1("a@&"+i);
			f.setA2("a@&"+i);
			f.setA3("a@&"+i);
			f.setA4("a@&"+i);
			f.setA5("a@&"+i);
			f.setA6("a@&"+i);
			f.setA7("a@&"+i);
			f.setA8("a@&"+i);
			f.setA9("a@&"+i);
			f.setA10("a@&"+i);
			f.setA11("a@&"+i);
			f.setA12("a@&"+i);
			youFriends.add(f);
		}
		for(int i=0;i<7;i++){
			Friend f=new Friend();
			f.setId(i+30);
			f.setName("name$"+i+100);
			f.setA1("a@*"+i);
			f.setA2("a@*"+i);
			f.setA3("a@*"+i);
			f.setA4("a@*"+i);
			f.setA5("a@*"+i);
			f.setA6("a@*"+i);
			f.setA7("a@*"+i);
			f.setA8("a@*"+i);
			f.setA9("a@*"+i);
			f.setA10("a@*"+i);
			f.setA11("a@*"+i);
			f.setA12("a@*"+i);
			hahFriends.add(f);
		}
		user.setHahFriends(hahFriends);
		user.setHerFriends(herFriends);
		user.setNoFriends(noFriends);
		user.setYouFriends(youFriends);
		return user;
		
	}
	
	@RequestMapping(value="/cljs")
	@ResponseBody
	/**
	 * 这种情况下传过来的数据必须是json格式的
	 * @param user
	 * @return
	 */
	public String sys2(@RequestBody User user){
		System.out.println("=========="+user);
	//	System.out.println("-----"+user.getBirthDate());
		return "succ";
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	@ResponseBody
	private User user(@PathVariable("id")String id,HttpServletResponse response){
		User user=new User();
		//user.setId(id);
		//user.setName("chenliang");
		System.out.println("========================="+id);
		
		return user;
	}
	
	@RequestMapping(value="/string/{id}",method=RequestMethod.GET)
	@ResponseBody
	private String useraa(@PathVariable("id")int id,HttpServletResponse response){
		String result=id+"_"+"key";
		return result;
	}
	
	
	@RequestMapping(value="/infopost",method=RequestMethod.GET)
	@ResponseBody
	private User index(HttpServletResponse response,@RequestBody String poststr,HttpServletRequest request){
		System.out.println("---123---"+request.getParameter("name"));
		System.out.println("----json---"+poststr);
		User user=new User();
		//user.setName("chenl");
		return user;
	}
	
	@RequestMapping(value="/info",method=RequestMethod.GET)
	private String info(Date birthDate,HttpServletResponse response,HttpServletRequest request){
		if(request.getHeader("Authorization")==null){
			 response.setStatus(401);  
             response.setHeader("Cache-Control", "no-store");  
             response.setDateHeader("Expires", 0);  
             response.setHeader("WWW-authenticate", "Basic Realm=\"����\""); 
             return "authFail";
		}
		//User user=new User();
		//user.setBirthDate(birthDate);
		return "index";
	}
	
	@RequestMapping(value="/check/{id}",method=RequestMethod.GET)
	private void user2(@PathVariable("id")int id,HttpServletResponse response,HttpServletRequest request){
		
		System.out.println("Content-type:>>>>>>>>>>>>>>>>>>>>>>>>"+request.getContentType());
		
		User user=new User();
		//user.setId(id);
		//user.setName("chenliang");
		
		OutputJson(user,response);
	}
	
	public  void OutputJson(Object object,HttpServletResponse response) {
		PrintWriter out = null;
		
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		String json=null;
		try {
			out = response.getWriter();
			 // ObjectMapper mapper = new ObjectMapper(); 
			//json = mapper.writeValueAsString(object);
			
			json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss",SerializerFeature.WriteMapNullValue);
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.print(json);
		out.close();
	}

}
