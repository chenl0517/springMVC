package com.cljs.web.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cljs.web.pojo.User;

/**
 * 1、参数直接拼接在url上，参数不会放到流中,不论是post还是get,content-type是否设置application/json，
 *    服务器端只能通过request.getparam方式取值，@requestBody取不到值
 * 2、参数不直接拼接在url上,设置application/json：
 *   1）参数的格式是以地址符&拼接的key-value格式,此时数据会放置到流中，request.getParameter("XX")方式是取不到值的，
 *      只能从流中取值或直接只用@RequestBody取值，取到的参数字符串也是以&拼接的key-value格式。
 *   2）参数的格式是以json字符串格式传递的，此时数据会放置到流中，也只能通过@RequestBody取值（也只有此时才能直接绑定传参对象），或者直接从流中取
 *   
 * @author root1
 *
 */
public class HttpClientUtil {
	public static void main(String[] args) {
		//postKeyValue();
		//postForm();
		get();
		postJson();
	/*	 User user2=new User();
		 JSONObject j = new JSONObject();  
	        j.put("id", "12");
	        j.put("name", null);
	        j.put("birthDate", new Date());
	        
	       User user= j.parseObject(j.toJSONString(j, SerializerFeature.WriteNonStringValueAsString), User.class);
	      //  System.out.println();
	       System.out.println(user.getName() );*/
	        
		sendByHttp();
	}
	
	/** 
     * HttpClient
     */  
    public void ssl() {  
        CloseableHttpClient httpclient = null;  
        try {  
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());  
            FileInputStream instream = new FileInputStream(new File("d:\\tomcat.keystore"));  
            try {  
                // keyStore d:\\tomcat.keystore    
                trustStore.load(instream, "123456".toCharArray());  
            } catch (CertificateException e) {  
                e.printStackTrace();  
            } finally {  
                try {  
                    instream.close();  
                } catch (Exception ignore) {  
                }  
            }  
            SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(trustStore, new TrustSelfSignedStrategy()).build();  
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,  
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);  
            httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();  
            HttpGet httpget = new HttpGet("https://localhost:8443/myDemo/Ajax/serivceJ.action");  
            System.out.println("executing request" + httpget.getRequestLine());  
            CloseableHttpResponse response = httpclient.execute(httpget);  
            try {  
                HttpEntity entity = response.getEntity();  
                System.out.println("----------------------------------------");  
                System.out.println(response.getStatusLine());  
                if (entity != null) {  
                    System.out.println("Response content length: " + entity.getContentLength());  
                    System.out.println(EntityUtils.toString(entity));  
                    EntityUtils.consume(entity);   
                }  
            } finally {  
                response.close();  
            }  
        } catch (ParseException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (KeyManagementException e) {  
            e.printStackTrace();  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        } catch (KeyStoreException e) {  
            e.printStackTrace();  
        } finally {  
            if (httpclient != null) {  
                try {  
                    httpclient.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
    }  
  
	
	 /** 
     */  
    public static void postForm() {  
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        HttpPost httppost = new HttpPost("http://127.0.0.1:8080/user/infopost");  
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
        formparams.add(new BasicNameValuePair("id", "12")); 
        formparams.add(new BasicNameValuePair("name", "chenl")); 
        UrlEncodedFormEntity uefEntity;  
        try {  
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
            httppost.addHeader("Content-Type", "application/json;charset=UTF-8");
            httppost.setEntity(uefEntity);  
            System.out.println("executing request " + httppost.getURI());  
            CloseableHttpResponse response = httpclient.execute(httppost);  
            try {  
                HttpEntity entity = response.getEntity();  
                if (entity != null) {  
                    System.out.println("--------------------------------------");  
                    System.out.println("Response content: " + EntityUtils.toString(entity, "UTF-8"));  
                    System.out.println("--------------------------------------");  
                }  
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e1) {  
            e1.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
    
    
    /** 
     * post json
     */  
    public static void postJson() {  
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        HttpPost httppost = new HttpPost("https://192.168.2.250/ImportExportAPI/SecurityService.svc/web/help/operations/GetUserAuthenticationToken");  
        JSONObject j = new JSONObject();  
        j.put("orgCode", "");
        j.put("password", "ChangePassword#5");
        j.put("userName","orcl");
        StringEntity uefEntity;  
        try {  
            uefEntity = new StringEntity(j.toJSONString(), "UTF-8");
           // httppost.addHeader("Content-Type", "application/json;charset=UTF-8");
            httppost.setEntity(uefEntity);  
            CloseableHttpResponse response = httpclient.execute(httppost);  
            try {  
                HttpEntity entity = response.getEntity();  
                if (entity != null) {  
                    System.out.println("--------------------------------------");  
                    System.out.println("Response content: " + EntityUtils.toString(entity, "UTF-8"));  
                    System.out.println("--------------------------------------");  
                }  
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e1) {  
            e1.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
    
    public static CloseableHttpClient createSSLClientDefault() {  
        try {  
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {  
                // 信任所有  
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {  
                    return true;  
                }  
            }).build();  
            HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;  
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);  
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();  
        } catch (KeyManagementException e) {  
            e.printStackTrace();  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        } catch (KeyStoreException e) {  
            e.printStackTrace();  
        }  
        return HttpClients.createDefault();  
  
    }  
    
    /**  
     * 发送https请求  
     *   
     * @param jsonPara  
     * @throws Exception  
     */  
    public static String sendByHttp() {  
    	CloseableHttpClient    httpClient=null;
    	CloseableHttpResponse  httpResponse = null;
        try {  
            HttpPost httpPost = new HttpPost("https://192.168.2.250/ImportExportAPI/SecurityService.svc/web/GetUserAuthenticationToken");  
            JSONObject j = new JSONObject();  
            j.put("orgCode", "");
            j.put("password", "ChangePassword#5");
            j.put("userName","orcl");
            StringEntity uefEntity;  
            uefEntity = new StringEntity(j.toJSONString(), "UTF-8");
            httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
            httpPost.setEntity(uefEntity);  
            httpClient =createSSLClientDefault();  
            httpResponse = httpClient.execute(httpPost);  
            HttpEntity httpEntity = httpResponse.getEntity();  
            if (httpEntity != null) {  
               String jsObject = EntityUtils.toString(httpEntity, "UTF-8");  
               System.out.println("result========"+jsObject);
               return jsObject;
            } else {  
                return null;  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        } finally {  
            try {
            	if(httpResponse!=null){
            		httpResponse.close();  
            	}
                httpClient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
    
    public static void postKeyValue(){
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        HttpPost httppost = new HttpPost("http://127.0.0.1:8080/user/infopost?id=1&name=chenl");  
        httppost.setHeader("Content-Type","application/json;charset=UTF-8");// "application/x-www-form-urlencoded");
        try {  
            CloseableHttpResponse response = httpclient.execute(httppost);  
            try {  
                HttpEntity entity = response.getEntity();  
                if (entity != null) {  
                    String  content=EntityUtils.toString(entity, "UTF-8");  
                    System.out.println("==================:"+content);
                }  
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e1) {  
            e1.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    	
    }
  
    /** 
     */  
    public static void post() {  
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        HttpPost httppost = new HttpPost("http://192.168.0.75:8100/pos/posUnion/updateCardInfoToMaster");  
        //httppost.setHeader("Content-Type","application/json;charset=UTF-8");// "application/x-www-form-urlencoded");
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
        formparams.add(new BasicNameValuePair("accnt", "P0011712110021"));  
        formparams.add(new BasicNameValuePair("cardInfo", "8��"));  
        formparams.add(new BasicNameValuePair("cardNo", "0000690")); 
        formparams.add(new BasicNameValuePair("dscMode", "008"));  
        formparams.add(new BasicNameValuePair("hotelCode", "GCBZ"));  
        formparams.add(new BasicNameValuePair("hotelGroupCode", "GCBZG"));  
        UrlEncodedFormEntity uefEntity;  
        try {  
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");  
            httppost.setEntity(uefEntity);  
            System.out.println("executing request " + httppost.getURI());  
            CloseableHttpResponse response = httpclient.execute(httppost);  
            try {  
                HttpEntity entity = response.getEntity();  
                if (entity != null) {  
                    String  content=EntityUtils.toString(entity, "UTF-8");  
                    JSONObject jsonParam = new JSONObject();  
                    System.out.println("�ۿ�ģʽ======"+jsonParam.parseObject(content).getString("errorMsg"));
                }  
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e1) {  
            e1.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
  
    /** 
     */  
    public static void get() {  
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        try {  
            //HttpGet httpget = new HttpGet("https://192.168.2.250/ImportExportAPI/DataService.svc/web/GetRequestStatus/42/orcl/''");  
        	
        		HttpGet httpget = new HttpGet("https://192.168.2.250/ImportExportAPI/DataService.svc/web/GetBuildVersion");
        	// httpget.setHeader("Content-Type", "application/json;charset=UTF-8");
            CloseableHttpResponse response = httpclient.execute(httpget);  
            try {  
               InputStream is=response.getEntity().getContent();
               System.out.println(is+"==============");
            	
                HttpEntity entity = response.getEntity();  
                System.out.println(response.getStatusLine());  
                if (entity != null) {  
                   // System.out.println("Response content length: " + entity.getContentLength());  
                  String content= EntityUtils.toString(entity);  
                  System.out.println(content);
                    
                }  
                System.out.println("------------------------------------");  
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (ParseException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
    
    public static String httpPostWithJSON(String url) throws Exception {
    	  
    	          HttpPost httpPost = new HttpPost(url);
    	          CloseableHttpClient client = HttpClients.createDefault();
    	          String respContent = null;
    	          
    	         JSONObject jsonParam = new JSONObject();  
    	          jsonParam.put("name", "admin");
    	         jsonParam.put("pass", "123456");
    	         StringEntity entity = new StringEntity(jsonParam.toString(),"utf-8");
    	         entity.setContentEncoding("UTF-8");    
    	         entity.setContentType("application/json");    
    	         httpPost.setEntity(entity);
    	         System.out.println();
    	     
    	 //        List<BasicNameValuePair> pairList = new ArrayList<BasicNameValuePair>(); 
    	 //        pairList.add(new BasicNameValuePair("name", "admin"));
    	 //        pairList.add(new BasicNameValuePair("pass", "123456"));
    	 //        httpPost.setEntity(new UrlEncodedFormEntity(pairList, "utf-8"));   
    	         
    	         
    	         HttpResponse resp = client.execute(httpPost);
    	         if(resp.getStatusLine().getStatusCode() == 200) {
    	             HttpEntity he = resp.getEntity();
    	            respContent = EntityUtils.toString(he,"UTF-8");
    	         }
    	         return respContent;
    	     }
  

}
