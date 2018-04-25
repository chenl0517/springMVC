package com.cljs;

import java.beans.PropertyDescriptor;

import org.apache.commons.beanutils.BeanUtilsBean;


public class TestApache {
	
	public static void main(String[] args) {
		//List list=new ArrayList();
		
		Stu stu=new Stu();
		
		stu.setAge(10);
		
		BeanUtilsBean bean=	BeanUtilsBean.getInstance();
		PropertyDescriptor[] origDescriptors =bean.
                getPropertyUtils().getPropertyDescriptors(stu);
		
		for(PropertyDescriptor pd:origDescriptors){
			
			System.out.println(pd.getName());
		}
		
		
		
	}

}
