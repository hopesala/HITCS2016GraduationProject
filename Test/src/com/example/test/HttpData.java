package com.example.test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;


import android.os.AsyncTask;

public class HttpData extends AsyncTask<String, Void, String> {
	
//	private HttpClient mHttpClient;
//	private HttpGet mHttpGet;
//	private HttpResponse mHttpResponse;
//	private HttpEntity mHttpEntity;
	private InputStream inputStream;
	private HttpGetDataListener listener;
	
	private String url;
	public HttpData(String url,HttpGetDataListener listener) {
		this.url=url;
		this.listener=listener;
	}
	
	@Override
	protected String doInBackground(String... params) {
		try {
//			mHttpClient=new DefaultHttpClient();
//			mHttpGet=new HttpGet(url);
//			
//			mHttpResponse=mHttpClient.execute(mHttpGet);
//			mHttpEntity=mHttpResponse.getEntity();
//			inputStream=mHttpEntity.getContent();
			
			URL u=new URL(url);
			HttpURLConnection connection=(HttpURLConnection) u.openConnection();
			connection.setRequestProperty("User-Agent", "");
	        connection.setRequestMethod("POST");
	        connection.setDoInput(true);
	        connection.connect();
			inputStream=connection.getInputStream();
			BufferedReader brBufferedReader=new BufferedReader(new InputStreamReader(inputStream));
			String lineString=null;
			StringBuffer sbBuffer=new StringBuffer();
			while ((lineString=brBufferedReader.readLine())!=null) {
				sbBuffer.append(lineString);
			}
			return sbBuffer.toString();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	@Override
	protected void onPostExecute(String result) {
		listener.getDataUrl(result);
		super.onPostExecute(result);
	}
}
