package com.cljs.web.convert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class MyConvert implements Converter<String, Date>{

	@Override
	public Date convert(String source) {
		if(StringUtils.isEmpty(source)){
			return new Date();
		}
		else{
			if(source.indexOf("_")!=-1){
				SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
			try {
				return sf.parse(source);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			}
		}
		
		return null;
	}

}
