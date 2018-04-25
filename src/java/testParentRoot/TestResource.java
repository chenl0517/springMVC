package testParentRoot;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cljs.web.controller.UserController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:applicationContext.xml"})
public class TestResource {
	
	@Resource
	private UserController userController;
	
	@Test
	public void test(){
		
		System.out.println("=================="+userController);
	}
	

}
