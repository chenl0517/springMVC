package com.cljs.web.util;

import java.nio.charset.Charset;  
import org.apache.http.HttpEntity;  
import org.apache.http.client.config.RequestConfig;  
import org.apache.http.client.methods.CloseableHttpResponse;  
import org.apache.http.client.methods.HttpPost;  
import org.apache.http.entity.StringEntity;  
import org.apache.http.impl.client.CloseableHttpClient;  
import org.apache.http.impl.client.HttpClientBuilder;  
import org.apache.http.util.EntityUtils;  
import org.apache.log4j.Logger;  

public class HttpClientCallSoapUtil {
	static int socketTimeout = 30000;// 请求超时时间  
    static int connectTimeout = 30000;// 传输超时时间  
    static Logger logger = Logger.getLogger(HttpClientCallSoapUtil.class);  
  
    /** 
     * 使用SOAP1.1发送消息 
     *  
     * @param postUrl 
     * @param soapXml 
     * @param soapAction 
     * @return 
     */  
    public static String doPostSoap1_1(String postUrl, String soapXml,  
            String soapAction) {  
        String retStr = "";  
        // 创建HttpClientBuilder  
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();  
        // HttpClient  
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();  
        HttpPost httpPost = new HttpPost(postUrl);  
                //  设置请求和传输超时时间  
        RequestConfig requestConfig = RequestConfig.custom()  
                .setSocketTimeout(socketTimeout)  
                .setConnectTimeout(connectTimeout).build();  
        httpPost.setConfig(requestConfig);  
        try {  
            httpPost.setHeader("Content-Type", "text/xml;charset=UTF-8");  
            httpPost.setHeader("SOAPAction", soapAction);  
            StringEntity data = new StringEntity(soapXml,  
                    Charset.forName("UTF-8"));  
            httpPost.setEntity(data);  
            CloseableHttpResponse response = closeableHttpClient  
                    .execute(httpPost);  
            HttpEntity httpEntity = response.getEntity();  
            if (httpEntity != null) {  
                // 打印响应内容  
                retStr = EntityUtils.toString(httpEntity, "UTF-8");  
            }  
            // 释放资源  
            closeableHttpClient.close();  
        } catch (Exception e) {  
        	e.printStackTrace();
        }  
        return retStr;  
    }  
  
    /** 
     * 使用SOAP1.2发送消息 
     *  
     * @param postUrl 
     * @param soapXml 
     * @param soapAction 
     * @return 
     */  
    public static String doPostSoap1_2(String postUrl, String soapXml,  
            String soapAction) {  
        String retStr = "";  
        // 创建HttpClientBuilder  
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();  
        // HttpClient  
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();  
        HttpPost httpPost = new HttpPost(postUrl);  
                // 设置请求和传输超时时间  
        RequestConfig requestConfig = RequestConfig.custom()  
                .setSocketTimeout(socketTimeout)  
                .setConnectTimeout(connectTimeout).build();  
        httpPost.setConfig(requestConfig);  
        try {  
            httpPost.setHeader("Content-Type",  
                    "application/soap+xml;charset=UTF-8");  
            httpPost.setHeader("SOAPAction", soapAction);  
            StringEntity data = new StringEntity(soapXml,  
                    Charset.forName("UTF-8"));  
            httpPost.setEntity(data);  
            CloseableHttpResponse response = closeableHttpClient  
                    .execute(httpPost);  
            HttpEntity httpEntity = response.getEntity();  
            if (httpEntity != null) {  
                // 打印响应内容  
                retStr = EntityUtils.toString(httpEntity, "UTF-8");  
                logger.info("response:" + retStr);  
            }  
            // 释放资源  
            closeableHttpClient.close();  
        } catch (Exception e) {  
            logger.error("exception in doPostSoap1_2", e);  
        }  
        return retStr;  
    }  
  
    public static void main(String[] args) {  
        String orderSoapXml = "<?xml version = \"1.0\" ?>"  
       +"<LoginRequest xmlns=\"http://schemas.datacontract.org/2004/07/com.oracle.pos.fb.simphony.ServiceLayer.ImportExport\"> "
       +" <orgCode></orgCode>"
       +" <password>ChangePassword#5</password>"
       +" <userName>orcl</userName>"
       +"</LoginRequest>";  
        String querySoapXml = "<?xml version = \"1.0\" ?>"  
                + "<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://webservices.b.com\">"  
                + "   <soapenv:Header/>"  
                + "   <soapenv:Body>"  
                + "      <web:query soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">"  
                + "         <in0 xsi:type=\"web:QueryRequest\">"  
                + "            <endTime xsi:type=\"xsd:dateTime\">?</endTime>"  
                + "            <mobile xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">?</mobile>"  
                + "            <startTime xsi:type=\"xsd:dateTime\">?</startTime>"  
                + "         </in0>" + "      </web:query>"  
                + "   </soapenv:Body>" + "</soapenv:Envelope>";  
        String postUrl = "https://192.168.2.250/ImportExportAPI/SecurityService.svc";  
        //采用SOAP1.1调用服务端，这种方式能调用服务端为soap1.1和soap1.2的服务  
        System.out.println(  doPostSoap1_1(postUrl, orderSoapXml, "GetUserAuthenticationToken"));  
       // doPostSoap1_1(postUrl, querySoapXml, "");  
  
        //采用SOAP1.2调用服务端，这种方式只能调用服务端为soap1.2的服务  
        //doPostSoap1_2(postUrl, orderSoapXml, "order");  
        //doPostSoap1_2(postUrl, querySoapXml, "query");  
    }  

}
