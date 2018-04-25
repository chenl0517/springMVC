package com.cljs.web.util;

import java.io.InputStreamReader;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.alibaba.fastjson.JSONObject;

public class HttpsWebService {
	public static void main(String[] args) {
		  JSONObject j = new JSONObject();  
          j.put("orgCode", "");
          j.put("password", "ChangePassword#5");
          j.put("userName","orcl");
		String result=sendPost("https://192.168.2.250/ImportExportAPI/SecurityService.svc/web/GetUserAuthenticationToken",j.toJSONString());
		System.out.println(result.substring(1, result.lastIndexOf("\"")));
		Map<String,String> headers=new HashMap<String,String>();
		headers.put("AuthenticationToken", result.substring(1, result.lastIndexOf("\"")));
		String tableType=sendGet("https://192.168.2.250/ImportExportAPI/DataService.svc/web/GetExportableTypes/orcl/",headers);
		System.out.println("tableType=="+tableType);
	}
	
	public static String sendGet(String url, Map<String,String> headers){
		String result=null;
		try {
			DefaultHttpClient httpclient=getDefaultHttpClient();
			HttpGet httpget=new HttpGet(url);
			httpget.setHeader("AuthenticationToken", headers.get("AuthenticationToken"));
			  HttpResponse response = httpclient.execute(httpget);
	            HttpEntity resEntity = response.getEntity();
	            InputStreamReader reader = new InputStreamReader(resEntity.getContent());
	            char[] buff = new char[1024];
	            int length = 0;
	            while ((length = reader.read(buff)) != -1) {
	                result += new String(buff, 0, length);
	            }
	            httpclient.getConnectionManager().shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	private static DefaultHttpClient getDefaultHttpClient(){
		DefaultHttpClient httpclient = new DefaultHttpClient();
		try {
		 // First create a trust manager that won't care.
        X509TrustManager trustManager = new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                // Don't do anything.
            }
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                // Don't do anything.
            }
            public X509Certificate[] getAcceptedIssuers() {
                // Don't do anything.
                return null;
            }
        };
        SSLContext sslcontext;
		 sslcontext = SSLContext.getInstance("TLSv1.2");
         sslcontext.init(null, new TrustManager[] {trustManager}, null);
         SSLSocketFactory sf = new SSLSocketFactory(sslcontext);
         sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
         httpclient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", sf,443));
		} catch (Exception e) {
		}
		return httpclient;
	}
	
	public static String sendPost(String url,String jsonParam) {
        String result = "";
        try {
        	DefaultHttpClient httpclient=getDefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            // httpPost.setHeader("Authorization", "basic " + "dGNsb3VkYWRtaW46dGNsb3VkMTIz");
            httpPost.setHeader("Content-type", "application/json");
            StringEntity uefEntity = new StringEntity(jsonParam, "UTF-8");
            BufferedHttpEntity bhe = new BufferedHttpEntity(uefEntity);
            httpPost.setEntity(bhe);
            HttpResponse response = httpclient.execute(httpPost);
            HttpEntity resEntity = response.getEntity();
            InputStreamReader reader = new InputStreamReader(resEntity.getContent());
            char[] buff = new char[1024];
            int length = 0;
            while ((length = reader.read(buff)) != -1) {
                result += new String(buff, 0, length);
            }
            httpclient.getConnectionManager().shutdown();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return result;
    }
}
