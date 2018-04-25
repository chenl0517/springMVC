package com.cljs.web.pojo;

import com.alibaba.fastjson.JSONObject;

public class JsonUitl {
	public static void main(String[] args) {
		String jsonstr="{['id':'1','name':'chenl']}";
		
		//String str="success";
		
		JSONObject jo=new JSONObject();
		
		User user=jo.parseObject(jsonstr, User.class);
		
		//System.out.println(user.getName());
	}

}
