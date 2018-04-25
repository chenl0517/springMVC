package com.cljs.web.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HttpClient {
	
	public static void main(String[] args) throws IOException {
		get();
	}
	
	public static void get() throws IOException{
		String urlstr="http://118.89.158.149:8300/ipmsinvoice/invoice/selectVague?customerName=陈亮";
		URL url = new URL(urlstr);
		HttpURLConnection   urlConnection = (HttpURLConnection) url.openConnection();
		
		Map<String,String> propertys=new HashMap<String,String>();
		propertys.put("Charset", "UTF-8");
        propertys.put("Content-Type", "application/json");
		if (propertys != null) {
            for (String key : propertys.keySet()) {
                urlConnection.addRequestProperty(key, propertys.get(key));
            }
        }
		 urlConnection.setRequestMethod("GET");
	     urlConnection.setDoOutput(true);
	     urlConnection.setDoInput(true);
	     urlConnection.setUseCaches(false);
		
		  InputStream in = urlConnection.getInputStream();
		  BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in,"utf-8"));
          StringBuffer temp = new StringBuffer();
          String line = bufferedReader.readLine();
          while (line != null) {
              temp.append(line).append("\r\n");
              line = bufferedReader.readLine();
          }
          System.out.println(temp.toString());
          bufferedReader.close();
          urlConnection.getInputStream().close();
	}
	
	public static void post() throws IOException{
		String urlstr="http://127.0.0.1:8080/pos/posUnion/getArFkList2";
		URL url = new URL(urlstr);
		HttpURLConnection   urlConnection = (HttpURLConnection) url.openConnection();
		
		Map<String,String> propertys=new HashMap<String,String>();
		propertys.put("Charset", "UTF-8");
        propertys.put("Content-Type", "application/json;Charset=UTF-8");
		if (propertys != null) {
            for (String key : propertys.keySet()) {
                urlConnection.addRequestProperty(key, propertys.get(key));
            }
        }
		Map<String,String> parameters=new HashMap<String,String>();
		parameters.put("hotelGroupCode", "GCBZG");
		parameters.put("hotelCode", "GCBZ");
		parameters.put("keyInput", "陈亮");
			 StringBuffer param = new StringBuffer();
	            for (String key : parameters.keySet()) {
	                param.append("&");
	                param.append(key).append("=").append(parameters.get(key));
	            }
	            urlConnection.setRequestMethod("POST");
	            urlConnection.setDoOutput(true);
	            urlConnection.setDoInput(true);
	            urlConnection.setUseCaches(false);
	            urlConnection.getOutputStream().write(param.toString().getBytes());
	            urlConnection.getOutputStream().flush();
	            urlConnection.getOutputStream().close();
		  InputStream in = urlConnection.getInputStream();
		  BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in,"utf-8"));
          StringBuffer temp = new StringBuffer();
          String line = bufferedReader.readLine();
          while (line != null) {
              temp.append(line).append("\r\n");
              line = bufferedReader.readLine();
          }
          System.out.println(temp.toString());
          bufferedReader.close();
          urlConnection.getInputStream().close();
          //断开连接
          urlConnection.disconnect();
	}
}
