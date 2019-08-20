package com.bicsoft.sy.util;

import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;

import net.sf.json.JSONObject;

public class HttpUtil {

	public static JSONObject sendPost(String reqUrl, List <NameValuePair> param) throws Exception{
		 CloseableHttpClient httpclient = HttpClients.createDefault();
		 HttpPost httpPost = new HttpPost(reqUrl.toString());
		 httpPost.getParams().setParameter("http.protocol.content-charset", HTTP.UTF_8);
		 httpPost.getParams().setParameter(HTTP.CONTENT_ENCODING, HTTP.UTF_8);
		  
		 JSONObject resultJson = null;
		 CloseableHttpResponse response = null;
		 try{
			 httpPost.setEntity(new UrlEncodedFormEntity(param, "UTF-8"));
	         response = httpclient.execute(httpPost);    
	         String message = IOUtils.toString(response.getEntity().getContent());  
	         resultJson = JSONObject.fromObject(message);
		 }catch(Exception e){
			 e.printStackTrace();
		 }finally{
			 if(response != null) response.close();
		 }
         return resultJson;
	}

	public static JSONObject sendGet(String reqUrl) throws Exception{
		 CloseableHttpClient httpclient = HttpClients.createDefault();
		 HttpGet httpGet = new HttpGet(reqUrl.toString());
		 httpGet.getParams().setParameter("http.protocol.content-charset", HTTP.UTF_8);
		 httpGet.getParams().setParameter(HTTP.CONTENT_ENCODING, HTTP.UTF_8);
		  
		 JSONObject resultJson = null;
		 CloseableHttpResponse response = null;
		 try{
	         response = httpclient.execute(httpGet);    
	         String message = IOUtils.toString(response.getEntity().getContent());  
	         resultJson = JSONObject.fromObject(message);
		 }catch(Exception e){
			 e.printStackTrace();
		 }finally{
			 if(response != null) response.close();
		 }
        return resultJson;
	}
}
