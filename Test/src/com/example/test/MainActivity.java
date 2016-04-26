package com.example.test;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


public class MainActivity extends Activity implements HttpGetDataListener , OnClickListener{
	private HttpData httpData;
	private List<ListData> lists;
	private ListView lvListView;
	private EditText sendText;
	private Button send_btnButton;
	private String content_strString;
	private TextAdapter adapter;
	private String[] welcome_array;
	private double currentTime,oldTime=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

	@Override
	public void getDataUrl(String data) {
		//System.out.println(data);
		parseText(data);
	}
	private String getRandomWelcomeTips() {
		String welcome_tip=null;
		welcome_array=this.getResources().getStringArray(R.array.welcome_tips);
		int index=(int) (Math.random()*(welcome_array.length-1));
		welcome_tip=welcome_array[index];
		return welcome_tip;
	}
	private void initView() {
		//content_strString=sendText.getText().toString();
		lvListView=(ListView) findViewById(R.id.lv);
		sendText=(EditText) findViewById(R.id.sendText);
		send_btnButton=(Button) findViewById(R.id.send_btn);
		send_btnButton.setBackgroundColor(Color.parseColor("#707038"));
		lists=new ArrayList<ListData>();
		send_btnButton.setOnClickListener(this);
		adapter=new TextAdapter(lists, this);
		lvListView.setAdapter(adapter);
		lists.add(new ListData(getRandomWelcomeTips(), ListData.RECEIVER,getTime()));
	}
	public void parseText(String str){
		try {
			JSONObject jb=new JSONObject(str);
//			System.out.println(jb.getString("code"));
//			System.out.println(jb.getString("text"));
			ListData listData;
			listData=new ListData(jb.getString("text"),ListData.RECEIVER,getTime());
			lists.add(listData);
			adapter.notifyDataSetChanged();
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void onClick(View v) {
		getTime();
		content_strString=sendText.getText().toString();
		String processedString=content_strString.replace(" ", "").replace("\n", "");
		if (content_strString.isEmpty() || content_strString=="") {
			//send_btnButton.setClickable(false);
			//send_btnButton.setEnabled(false);
			//send_btnButton.setBackgroundColor(Color.parseColor("#8e8e8e"));
		}
		else {
			sendText.setText("");
			//send_btnButton.setClickable(true);
			//send_btnButton.setBackgroundColor(Color.parseColor("#707038"));
			ListData listData;
			listData=new ListData(content_strString,ListData.SEND,getTime());
			lists.add(listData);
			if (lists.size()>50) {
				for (int i = 0; i < 25; i++) {
					lists.remove(i);
				}
			}
			adapter.notifyDataSetChanged();
			//监听事件
	        httpData=(HttpData) new HttpData("http://www.tuling123.com/openapi/api?key=0d0071599bacf4cf7e6742cf713d03be&info="+processedString, this).execute();
		}
		
	}
    private String getTime() {
    	currentTime=System.currentTimeMillis();
    	SimpleDateFormat format=new SimpleDateFormat("yyyy年MM月dd日 HH：mm：ss");
    	Date curDate=new Date();
    	String str=format.format(curDate);
    	if (currentTime-oldTime>=60*1000) {
			oldTime=currentTime;
			return str;
		}
    	return "";
    }
}
