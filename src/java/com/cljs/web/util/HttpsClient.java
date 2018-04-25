package com.cljs.web.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpsClient {
	private static class TrustAnyTrustManager implements X509TrustManager {  
		  
        public void checkClientTrusted(X509Certificate[] chain, String authType)  
                throws CertificateException {  
        }  
  
        public void checkServerTrusted(X509Certificate[] chain, String authType)  
                throws CertificateException {  
        }  
  
        public X509Certificate[] getAcceptedIssuers() {  
            return new X509Certificate[] {};  
        }  
    }  
  
    private static class TrustAnyHostnameVerifier implements HostnameVerifier {  
        public boolean verify(String hostname, SSLSession session) {  
            return true;  
        }  
    }  
	 public static byte[] postGetHttps(String url, String content, String charset,String requestType,Map<String,String> headers)  
	            throws NoSuchAlgorithmException, KeyManagementException,  
	            IOException {  
	        SSLContext sc = SSLContext.getInstance("TLSv1.2");  
	       
	        sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },  
	                new java.security.SecureRandom());  
	        URL console = new URL(url);  
	        HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();  
	        conn.setSSLSocketFactory(sc.getSocketFactory());  
	        conn.setRequestProperty("Content-Type", "application/json");
	        if(headers!=null){
	        	conn.setRequestProperty("AuthenticationToken", headers.get("AuthenticationToken"));
	        }
	        conn.setHostnameVerifier(new TrustAnyHostnameVerifier());  
	        conn.setDoOutput(true);  
	        conn.connect();  
	        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
	        if(requestType.equals("POST")){
	        out.write(content.getBytes(charset));  
	        out.flush();  
	        out.close();  
	        InputStream is = conn.getInputStream();  
	        if (is != null) {  
	            ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
	            byte[] buffer = new byte[1024];  
	            int len = 0;  
	            while ((len = is.read(buffer)) != -1) {  
	                outStream.write(buffer, 0, len);  
	            }  
	            is.close();  
	            return outStream.toByteArray();  
	        }  
	        }else if(requestType.equals("GET")){
		        InputStream is = conn.getInputStream();  
		        if (is != null) {  
		            ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
		            byte[] buffer = new byte[1024];  
		            int len = 0;  
		            while ((len = is.read(buffer)) != -1) {  
		                outStream.write(buffer, 0, len);  
		            }  
		            is.close();  
		            return outStream.toByteArray();  
		        }  
	        }
	        return null;  
	    }  
	

public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, IOException {
		//DisableSSLCertificateCheckUtil.disableChecks();
		Map<String,String> header = new HashMap<String, String>();
		header.put("Content-Type", "application/json");
	   byte[] result = postGetHttps("https://192.168.2.250/ImportExportAPI/SecurityService.svc/web/GetUserAuthenticationToken",
				" {\"userName\":\"orcl\",\"password\":\"ChangePassword#5\",\"orgCode\":\"\"}","UTF-8","POST",null);
	   String token = new String(result);
	   header.put("AuthenticationToken", token);
	   byte[] resultget = postGetHttps("https://192.168.2.250/ImportExportAPI/DataService.svc/web/GetExportableTypes/orcl",
				"","UTF-8","GET",header);
	   String tabletypes = new String(resultget);
		System.out.println(tabletypes);
	}
}
