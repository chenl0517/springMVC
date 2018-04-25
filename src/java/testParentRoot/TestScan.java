package testParentRoot;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cljs.web.pojo.User;

public class TestScan {
	public static void main(String[] args) {
		ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext5.xml");
		User user=ac.getBean("usera", User.class);
		//System.out.println(user.getName());
	}
}
