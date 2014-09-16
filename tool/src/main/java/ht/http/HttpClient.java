package ht.http;

import ht.ser.GSConfig;
import ht.ser.GameServerManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class HttpClient {
	
	private static Log logger = LogFactory.getLog(HttpClient.class);
	
	public static String post(String urladdress, String param){
		
		StringBuilder result = new StringBuilder();
		HttpURLConnection uc = null;
		try {
			URL url = new URL(urladdress);

			uc = (HttpURLConnection) url.openConnection();
			uc.setDoInput(true);
			uc.setDoOutput(true);
			uc.setInstanceFollowRedirects(true); // 不允许重定向
			uc.setRequestMethod("POST");
			uc.setConnectTimeout(5000); // 连接超时
			uc.setReadTimeout(5000); // 返回超时
			uc.getOutputStream().write(param.getBytes());
			uc.connect();
			int t = uc.getResponseCode();
			logger.error("发送到" + urladdress + " resultcode=" + t);
			if(t == 404){
				return "";
			}else{
				try {
					String responseMessage = uc.getResponseMessage();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(uc.getInputStream()));
					while (reader.ready()) {
						result.append(reader.readLine());
					}
					reader.close();
					logger.error("返回值" + t + " " + responseMessage + " : " + result);
				} catch (Exception e) {
					logger.error(e, e);
				}
				return result.toString();
			}
		} catch (Exception e) {
			logger.error("请求异常" + urladdress, e);
		} finally {
			try {
				if (uc != null && uc.getInputStream() != null) {
//				 uc.disconnect();//释放资源，并有可能影响到持久连接
				uc.getInputStream().close();// 只释放实例资源，不会影响持久连接
				}
			} catch (IOException e) {
				logger.error("打开的连接异常 , url = " + urladdress, e);
			}
		}
		return result.toString();
	}
	
	public static String postAndGetValue(String ip, String path){
		GSConfig gsConfig = GameServerManager.getMe().getGsconofig().get(ip);
		String url = "http://" + ip +":" + gsConfig.getHttp() + "/" + path;
		String json = post(url, "");
		if(StringUtils.isNotEmpty(json)){
			JSONObject jo = JSON.parseObject(json);
			return jo.get("res").toString();
		}
		return "";
	}
	
	public static void main(String[] args) {
		String json = post("http://127.0.0.1:9091/index", "");
		
		JSONObject jo = JSON.parseObject(json);
		if (jo != null) {
//			System.out.println(json);
			System.out.println(jo.get("res"));
		}
	}

}
