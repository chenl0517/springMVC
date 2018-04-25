package com.cljs.web.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {
	//private int id;
	
	//private Date birthDate;
	
	//private String name;
	
	//private User user;
	
	private List noFriends=new ArrayList();
	private List herFriends=new ArrayList();
	private List youFriends=new ArrayList();
	private List hahFriends=new ArrayList();
	
	
	public List getNoFriends() {
		return noFriends;
	}

	public void setNoFriends(List noFriends) {
		this.noFriends = noFriends;
	}

	public List getHerFriends() {
		return herFriends;
	}

	public void setHerFriends(List herFriends) {
		this.herFriends = herFriends;
	}

	public List getYouFriends() {
		return youFriends;
	}

	public void setYouFriends(List youFriends) {
		this.youFriends = youFriends;
	}

	public List getHahFriends() {
		return hahFriends;
	}

	public void setHahFriends(List hahFriends) {
		this.hahFriends = hahFriends;
	}

	/*public User getUser() {
		return user;
	}

	public void setUser(User usera) {
		this.user = usera;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	*/
	

}
