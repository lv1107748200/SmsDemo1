package com.hxj.sms;

import java.util.List;

public class SmsEntity {


	public int id;
	public int thread_id;
	public String address;
	public String person;
	public String date;
	public String protocol;
	public int read;
	public int status;
	public int type;
	public String body;
	public String service_center;
	
	@Override
	public String toString() {
		return " [ id = " + id + " | thread_id = " + thread_id + " | address = " + address + " | person = " + person 
				+ " | date = " + date + " | body = " + body + " | service_center = " + service_center + " ] ";
	}

	public static String getListString(List<SmsEntity> mList){
		
		StringBuilder sb = new StringBuilder("");
		
		if(mList == null || mList.isEmpty()){
			return "";
		}
		
		for(int i = 0 ; i< mList.size() ; i++){
			
			sb.append(mList.get(i).toString()).append("\r\n");
		}
		return sb.toString();
	}
}
