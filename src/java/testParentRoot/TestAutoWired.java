package testParentRoot;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cljs.web.pojo.User;

public class TestAutoWired {
	public static void main(String[] args) {
		ApplicationContext ac3=new ClassPathXmlApplicationContext("applicationContext4.xml");
		User user=	(User) ac3.getBean("user");
		//System.out.println(user.getUser().getName());
	}

}
