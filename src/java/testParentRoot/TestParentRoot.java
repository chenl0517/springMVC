package testParentRoot;

import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cljs.web.pojo.User;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"classpath:"})
public class TestParentRoot {
	
	public static void main(String[] args) {
		ApplicationContext ac3=new ClassPathXmlApplicationContext("applicationContext3.xml");
		String[] locations={"applicationContext2.xml"};
		ApplicationContext ac=new ClassPathXmlApplicationContext(locations,ac3);
	    User user=	(User) ac.getBean("user");
	    //System.out.println(user.getName()+"==="+user.getId());
	    
	    
		
		//1、如果在同一个spring上下文中定义两个相同bean，后加载的bean会覆盖先加载的bean定义
	    
//	    String[] locations4={"applicationContext3.xml","applicationContext2.xml"};
//	    ApplicationContext ac4=new ClassPathXmlApplicationContext(locations4);
//	    User user=	(User) ac4.getBean("user");
//	    
//	    System.out.println(user.getName()+"==="+user.getId());
		
		//在同一个spring上下文中，不能定义两个id/name的bean
//	    String[] locations4={"applicationContext4.xml"};
//	    ApplicationContext ac4=new ClassPathXmlApplicationContext(locations4);
//	    User user=	(User) ac4.getBean("user");
//	    
//	    System.out.println(user.getName()+"==="+user.getId());
		
	}
}
