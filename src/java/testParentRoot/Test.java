package testParentRoot;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cljs.web.pojo.User;

public class Test {
	public static void main(String[] args) {
		ApplicationContext ac3=new ClassPathXmlApplicationContext("applicationContext4.xml");
		//ac3.
		
	//String[] beannames=ac3.getBeanNamesForType(Object.class);
	
	//for(String name:beannames){
		//System.out.println(name);
		//Class type=	ac3.getType(name);
		//System.out.println(type);
	//}
		User user=(User) ac3.getBean("usera");
	//	System.out.println(user.getUser().getName());
	}

}
