package com.konfin.util;

import java.util.HashMap;

public class CONSTANTS {
	//Server URL
	public static final String serverURL="http://192.168.43.215:8080/SampleTest";

	public static  HashMap<Integer,String> fundHouseMap;
	static
	{
		fundHouseMap = new HashMap<Integer, String>();
		fundHouseMap.put(3, "Aditya Birla Mutual Fund");
		fundHouseMap.put(53, "Axis Mutual Fund");
		fundHouseMap.put(6, "DSP BlackRock Mutual Fund");
		fundHouseMap.put(27, "Franklin Mutual Fund");
		fundHouseMap.put(9, "HDFC Mutual Fund");
		fundHouseMap.put(56, "L&amp;T Mutual Fund");
		fundHouseMap.put(45, "Mirae Asset Mutual Fund");
		fundHouseMap.put(55, "Motilal Oswal Mutual Fund");
		fundHouseMap.put(21, "Reliance Mutual Fund");
		fundHouseMap.put(22, "SBI Mutual Fund");
		fundHouseMap.put(25, "Tata Mutual Fund");
		fundHouseMap.put(20, "ICICI Prudential Mutual Fund");
		fundHouseMap.put(33, "Sundaram Mutual Fund");
		fundHouseMap.put(28, "UTI Mutual Fund");


	}

	public static  HashMap<Integer,String> fundNameMap;
	static
	{
		fundNameMap = new HashMap<Integer, String>();
		fundNameMap.put(103155, "Aditya Birla Sun Life Balanced 95 Fund - Regular Plan-Growth");
		fundNameMap.put(112210, "Axis Liquid Fund - Growth Option");
		fundNameMap.put(112213, "Axis Liquid Fund - Monthly Dividend Option");
		fundNameMap.put(27, "Franklin Mutual Fund");
		fundNameMap.put(9, "HDFC Mutual Fund");
		fundNameMap.put(56, "L&amp;T Mutual Fund");
		fundNameMap.put(45, "Mirae Asset Mutual Fund");
		fundNameMap.put(55, "Motilal Oswal Mutual Fund");
		fundNameMap.put(21, "Reliance Mutual Fund");
		fundNameMap.put(22, "SBI Mutual Fund");
		fundNameMap.put(25, "Tata Mutual Fund");
		fundNameMap.put(20, "ICICI Prudential Mutual Fund");
		fundNameMap.put(33, "Sundaram Mutual Fund");
		fundNameMap.put(28, "UTI Mutual Fund");


	}


}
