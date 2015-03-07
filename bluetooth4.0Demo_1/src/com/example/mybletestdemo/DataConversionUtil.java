package com.example.mybletestdemo;

import com.uplus.bluetooth.thirdapi.Attribute;


public class DataConversionUtil {

	private DataConversionUtil(){}
	private static DataConversionUtil instance;
	public static DataConversionUtil getInstance(){
		if (instance == null) {
			instance = new DataConversionUtil();
		}
		return instance;
	}
	
	public byte[] dataConversion(Attribute da){
		if (da.getName().equals("000001")) {
			byte[] d = {0x68,0x02,0x00,0x02,0x00,0x01,0x11,0x7E,0x16};
			return d;
		}else if (da.getName().equals("000002")) {
			byte[] d = {0x68,0x02,0x00,0x02,0x00,0x02,0x11,0x7F,0x16};
			return d;
		}
		return null;
		
	}
	
}
