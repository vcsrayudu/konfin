package com.konfin;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.konfin.bean.FundHouseList;

public class Utility {

	public static void main(String arg[])
	{
		RestTemplate restTemplate = new RestTemplate();
		
		System.out.println("Program Started");
		JSONArray fundslist=new JSONArray();
		FundHouseList fundhouseList=new FundHouseList();
		for(int i=1;i<100;i++)
		{
		String uri = "http://portal.amfiindia.com/DownloadNAVHistoryReport_Po.aspx?mf="+i+"&tp=1&frmdt=06-Mar-2018&todt=06-Mar-2018";
		String result = restTemplate.getForObject(uri, String.class);
		StringTokenizer lines = new StringTokenizer(result, "\n");
		boolean found=false;
		HashMap<Integer, String> funds=new HashMap<Integer, String>();
		while (lines.hasMoreTokens()) {
			// console.log('Line ' + j + ' is ' + lines[j])
			String fundLine = lines.nextToken();
			// fundLine=str.toLowerCase();

			

				StringTokenizer values = new StringTokenizer(fundLine, ";");
				int value_len = values.countTokens();
				int k = 0;int j=0;
				int key=0;
				String value=null;
				while (values.hasMoreTokens()) {
					
					try {
					
					if(k==0||j==1)
					{
					if (k == 0) {
						key=Integer.parseInt(values.nextToken());
						System.out.println("key : "+key);
						found=true;
					} 
					if(j==1) {
						value=values.nextToken();
						System.out.println("value : "+value);
					}
					}
					else
						values.nextToken();
					k++;j++;
					}
					catch(Exception e)
					{
						
					}
				}
				if( value!=null)
				funds.put(key,value);

			}
		if(found&&funds.size()>0)
		{
			fundhouseList.setFunds(funds);
			fundhouseList.setFundhouseName("");
			fundhouseList.setFundhouseNumber(i);
			ObjectMapper mapper = new ObjectMapper();
			try
			{
			String jsonInString = mapper.writeValueAsString(fundhouseList);
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObj = (JSONObject) jsonParser.parse(jsonInString);
			fundslist.add(jsonObj);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
		
		}
		ObjectMapper mapper = new ObjectMapper();
		try
		{
		//String jsonInString = mapper.writeValueAsString(fundslist);
		FundsUtils.writeFile(fundslist.toJSONString(), "SubbuFunds.json");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		//System.out.println("Program Completed : "+funds.size());
	}
}
