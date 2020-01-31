package com.sihanwang.study.spelling;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.log4j.PropertyConfigurator;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class FanyiV3Util {

	private Logger logger = LoggerFactory.getLogger(FanyiV3Util.class);

	private final String YOUDAO_URL = "https://openapi.youdao.com/api";

	private final String APP_KEY = "41f3cfa3ef6ede36";

	private final String APP_SECRET = "k5etDxlOm608Uks4XCETFh1VWN0fCLsj";

	private CloseableHttpClient httpClient = HttpClients.createDefault();

	public Map<String, String> createParams(String word) {

		Map<String, String> params = new HashMap<String, String>();
		String salt = String.valueOf(System.currentTimeMillis());
		params.put("from", "en");
		params.put("to", "zh-CHS");
		params.put("signType", "v3");
		String curtime = String.valueOf(System.currentTimeMillis() / 1000);
		params.put("curtime", curtime);
		String signStr = APP_KEY + truncate(word) + salt + curtime + APP_SECRET;
		String sign = getDigest(signStr);
		params.put("appKey", APP_KEY);
		params.put("q", word);
		params.put("salt", salt);
		params.put("sign", sign);

		return params;
	}

	public YoudaoDictResponse requestForHttp(Map<String, String> params) throws IOException {

		/** httpPost */
		HttpPost httpPost = new HttpPost(YOUDAO_URL);
		List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
		Iterator<Map.Entry<String, String>> it = params.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> en = it.next();
			String key = en.getKey();
			String value = en.getValue();
			paramsList.add(new BasicNameValuePair(key, value));
		}
		httpPost.setEntity(new UrlEncodedFormEntity(paramsList, "UTF-8"));
		CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
		try {
			Header[] contentType = httpResponse.getHeaders("Content-Type");
			logger.info("Content-Type:" + contentType[0].getValue());
			if ("application/json;charset=UTF-8".equals(contentType[0].getValue())) {
				HttpEntity httpEntity = httpResponse.getEntity();
				String json = EntityUtils.toString(httpEntity, "UTF-8");
				logger.info(json);

				ObjectMapper mapper = new ObjectMapper();
				mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
				return mapper.readValue(json, YoudaoDictResponse.class);
			} else {
				logger.error("Unknown content type!");
				return null;
			}
		} finally {
			try {
				if (httpResponse != null) {
					httpResponse.close();
				}
			} catch (IOException e) {
				logger.error("Release resouce error", e);
			}
		}
	}

	public byte[] requestForMp3(String MP3URL) throws IOException {
		HttpPost httpPost = new HttpPost(MP3URL);
		CloseableHttpResponse httpResponse = httpClient.execute(httpPost);

		try {
			Header[] contentType = httpResponse.getHeaders("Content-Type");
			logger.info("Content-Type:" + contentType[0].getValue());
			if ("audio/mp3".equals(contentType[0].getValue())) {
				
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                httpResponse.getEntity().writeTo(baos);
                byte[] result = baos.toByteArray();
				return result;
				
			} else {
				logger.error("Unknown content type!");
				return null;
			}
		} finally {
			try {
				if (httpResponse != null) {
					httpResponse.close();
				}
			} catch (IOException e) {
				logger.error("Release resouce error", e);
			}
		}

	}

	/**
	 * 生成加密字段
	 */
	public static String getDigest(String string) {
		if (string == null) {
			return null;
		}
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		byte[] btInput = string.getBytes(StandardCharsets.UTF_8);
		try {
			MessageDigest mdInst = MessageDigest.getInstance("SHA-256");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (byte byte0 : md) {
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	

	public static String truncate(String q) {
		if (q == null) {
			return null;
		}
		int len = q.length();
		String result;
		return len <= 20 ? q : (q.substring(0, 10) + len + q.substring(len - 10, len));
	}
}