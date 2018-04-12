package com.konfin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.*;
import java.util.List;
import java.util.StringTokenizer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.konfin.bean.Fund;
import com.konfin.bean.InputParams;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.springframework.web.client.RestTemplate;

public class FundsUtils {
	static JSONParser jsonParser = new JSONParser();
 static String currentDate=getFormatedDateAsString(new Date());
 static String amfURI="https://www.amfiindia.com/spages/NAVAll.txt";
	public static JSONArray getFunds(String user) {
		JSONObject portpolios = null;
		JSONObject navUpdateDate = null;
		JSONObject sipUpdateDate = null;
		JSONArray portpolio = new JSONArray();
		// System.out.println(user);
		
		Date currentDate1=new Date();
		try {

			portpolios = readFile(user + ".json");
			if (portpolios != null) {
				
				//Condition to update the every day NAV value
				JSONArray portpolioList = (JSONArray) portpolios.get("portpolio");
				Date navdate=convertStringToDate(portpolios.get("navUpdateDate").toString());
				String sipDate=portpolios.get("sipUpdateDate").toString();
				Date sipdate=convertStringToDate(sipDate);
				sipdate=incrementDateOneMonth(sipdate);
				Date cDate=convertStringToDate(currentDate);
				boolean sipupdate=false;
				if (navdate.compareTo(cDate)<0) {
					
					for (Object fundObject : portpolioList.toArray()) {
						Fund fundObjectJson = new ObjectMapper().readValue(fundObject.toString(), Fund.class);
						//Fund fundObjectJson = (Fund) fundObject;
						//Getting current Nav
						Double nav=getNavValue(amfURI,fundObjectJson.getFundName());
						fundObjectJson.setNav(nav);
						if(sipdate.compareTo(cDate)<0)
						{
							fundObjectJson=setSipUnits(fundObjectJson,sipDate);
							sipupdate=true;
						}
						ObjectMapper mapper = new ObjectMapper();
						String jsonInString = mapper.writeValueAsString(fundObjectJson);
						JSONObject jsonObj = (JSONObject) jsonParser.parse(jsonInString);
						portpolio.add(jsonObj);
						//Update SIP Units
						
					}
					if(sipupdate) {
						portpolios.put("sipUpdateDate",currentDate);
					}
					portpolios.put("portpolio", portpolio);
					portpolios.put("navUpdateDate",currentDate);
					
					writeFile(portpolios.toJSONString(), user + ".json");
					
				}

				// System.out.println("portpolios: "+portpolios);
				portpolio = (JSONArray) portpolios.get("portpolio");

				// System.out.println("polio: "+polio);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return portpolio;
	}

	public static InputParams addFund(InputParams inputParams) {
		String user = inputParams.getUserId();
		Fund fund = inputParams.getFund();
		JSONObject portpolios = null;
		JSONArray portpolioList = null;
		// System.out.println(user);
		try {

			portpolios = readFile(user + ".json");
			boolean fundExist = false;
			// System.out.println("portpolios: "+portpolios);
			if (portpolios != null) {
				portpolioList = (JSONArray) portpolios.get("portpolio");

				for (Object fundObject : portpolioList.toArray()) {
					JSONObject fundObjectJson = (JSONObject) fundObject;
					
					String fundid = (String) fundObjectJson.get("fundName");
					if (fundid.equals(fund.getFundName())) {

						fundExist = true;
						break;
					}
				}
			}
			else
			{
				portpolios = new JSONObject();
				portpolioList = new JSONArray();
				//Date date=new Date();
				portpolios.put("navUpdateDate",currentDate);
				portpolios.put("sipUpdateDate",currentDate);
				
				
			}

			if (!fundExist) {
				ObjectMapper mapper = new ObjectMapper();
				if(fund.getFundValue()!=0)
				{
					String uri = "http://portal.amfiindia.com/DownloadNAVHistoryReport_Po.aspx?mf=" + fund.getFundHouseValue()+ "&tp=1&frmdt=" + fund.getBuyDate() + "&todt=" + fund.getBuyDate();
					double totalUnits=fund.getFundValue()/getNavValue(uri,fund.getFundName());
					fund.setTotalUnits(totalUnits);
				}
				fund = setSipUnits(fund,fund.getSipStartDate());
				
				fund.setNav(getNavValue(amfURI,fund.getFundName()));
				String jsonInString = mapper.writeValueAsString(fund);
				JSONObject jsonObj = (JSONObject) jsonParser.parse(jsonInString);
				
				portpolioList.add(jsonObj);
				portpolios.put("portpolio", portpolioList);

				try {
					writeFile(portpolios.toJSONString(), user + ".json");
					inputParams.setStatus("SUCCESSFULLY_ADDED");
					// inputParams.setFund(null);
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				System.out.println("Fund already Exist");
				inputParams.setStatus("FUND_EXIST");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return inputParams;
	}

	public static InputParams updateFund(InputParams inputParams) {
		String user = inputParams.getUserId();
		Fund fund = inputParams.getFund();
		JSONObject portpolios = null;
		JSONArray portpolioList = null;
		// System.out.println(user);
		try {
			boolean fundExist = false;
			portpolios = readFile(user + ".json");
			// System.out.println("portpolios: "+portpolios);
			if (portpolios != null) {
				portpolioList = (JSONArray) portpolios.get("portpolio");

				for (Object fundObject : portpolioList.toArray()) {
					JSONObject fundObjectJson = (JSONObject) fundObject;
					String fundid = (String) fundObjectJson.get("fundName");
					if (fundid.equals(fund.getFundName())) {

						fundExist = true;
						portpolioList.remove(fundObjectJson);
						break;
					}
				}
			}

			if (fundExist) {
				ObjectMapper mapper = new ObjectMapper();
				if(fund.getFundValue()!=0)
				{
					String uri = "http://portal.amfiindia.com/DownloadNAVHistoryReport_Po.aspx?mf=" + fund.getFundHouseValue()+ "&tp=1&frmdt=" + fund.getBuyDate() + "&todt=" + fund.getBuyDate();
					double totalUnits=fund.getFundValue()/getNavValue(uri,fund.getFundName());
					fund.setTotalUnits(totalUnits);
				}
				fund = setSipUnits(fund,fund.getSipStartDate());
			//	fund.setNav(getNavValue(fund.getFundHouseValue(),fund.getFundName(),currentDate));
				
				String jsonInString = mapper.writeValueAsString(fund);
				JSONObject jsonObj = (JSONObject) jsonParser.parse(jsonInString);

				portpolioList.add(jsonObj);
				portpolios.put("portpolio", portpolioList);

				try {
					writeFile(portpolios.toJSONString(), user + ".json");
					inputParams.setStatus("SUCCESSFULLY_UPDATED");
					// inputParams.setFund(null);
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				System.out.println("FUND_NOT_EXIST");
				inputParams.setStatus("FUND_NOT_EXIST");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return inputParams;
	}

	// Delete existing fund
	public static InputParams deleteFund(InputParams inputParams) {
		String user = inputParams.getUserId();
		Fund fund = inputParams.getFund();
		JSONObject portpolios = null;
		JSONArray portpolioList = null;
		boolean fundExist = false;
		portpolios = readFile(user + ".json");
		if (portpolios != null) {
			// System.out.println("portpolios: "+portpolios);
			portpolioList = (JSONArray) portpolios.get("portpolio");

			for (Object fundObject : portpolioList.toArray()) {
				JSONObject fundObjectJson = (JSONObject) fundObject;
				String fundName = (String) fundObjectJson.get("fundName");

				if (fundName.equals(fund.getFundName())) {

					fundExist = true;
					portpolioList.remove(fundObjectJson);

					break;
				}
			}

			if (fundExist) {
				portpolios.put("portpolio", portpolioList);

				try {
					writeFile(portpolios.toJSONString(), user + ".json");
					inputParams.setStatus("SUCCESSFULLY_DELETED");
					// inputParams.setFund(null);
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				System.out.println("FUND_NOT_EXIST");
				inputParams.setStatus("FUND_NOT_EXIST");
			}
		} else {
			System.out.println("FUND_NOT_EXIST");
			inputParams.setStatus("USER_NOT_EXIST");
		}

		return inputParams;
	}

	// Read a file and convert to jsonObject
	public static JSONObject readFile(String fileName) {
		JSONObject object = null;

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		// System.out.println("PAth : "+classLoader.getResource("/"));
		File file = null;

		FileReader reader = null;
		try {
			// URL filePath=classLoader.getResource("/");
			file = new File(classLoader.getResource(".").getFile() + "/" + fileName);
			if (!file.exists()) {
				System.out.println("PAth : " + file);
				file.createNewFile();
			} else {
				reader = new FileReader(file);
				if(reader!=null)
				{
				Object obj = jsonParser.parse(reader);
				object = (JSONObject) obj;
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return object;
	}

	// Write a json object to file
	public static void writeFile(String polios, String fileName) {

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		File file = null;
		// new File(classLoader.getResource(fileName).getFile());
		FileWriter writer = null;
		try {
			file = new File(classLoader.getResource(".").getFile() + "/" + fileName);
			if (file.exists()) {
				writer = new FileWriter(file);
				writer.write(polios);
			}
			else
			{
				writer = new FileWriter(file);
				writer.write(polios);
			}
System.out.println(""+file.getAbsolutePath());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
private static String getFormatedDateAsString(Date date)
{
	String dateString=null;
	HashMap<Integer, String> Months = new HashMap<>();
	Months.put(1, "Jan");
	Months.put(2, "Feb");
	Months.put(3, "Mar");
	Months.put(4, "Apr");
	Months.put(5, "May");
	Months.put(6, "Jun");
	Months.put(7, "Jul");
	Months.put(8, "Aug");
	Months.put(9, "Sep");
	Months.put(10, "Oct");
	Months.put(11, "Nov");
	Months.put(12, "Dec");
	SimpleDateFormat df = new SimpleDateFormat("yyyy");
	String year = df.format(date);
	dateString=date.getDate() + "-" + Months.get(date.getMonth() + 1) + "-" + year;
	return dateString;
}
	// Calculating SIP units based on the buy date.
	private static Fund setSipUnits(Fund fund,String date1) {
		Date buyDate = convertStringToDate(date1);
		String fundName = fund.getFundName();
		String fundHouseValue = fund.getFundHouseValue();
		double sipAmount = fund.getSipAmount();
		double fundValue = fund.getFundValue();
		double totalUnits = fund.getTotalUnits();
		int sip=fund.getSip();
		Date currentDate = new Date();
		double nav = 0;
		Date date = buyDate;
		System.out.println("date : "+date);
		System.out.println("date1 : "+date1);
		System.out.println("currentDate : "+currentDate);

		// get NAV for each SIP on date base
		List<Double> sipUnits = fund.getSipUnits();
        if(sipUnits==null)
        	sipUnits = new ArrayList<Double>();
//		if (fundValue != 0) {
//			String uri = "http://portal.amfiindia.com/DownloadNAVHistoryReport_Po.aspx?mf=" + fundHouseValue+ "&tp=1&frmdt=" + getFormatedDateAsString(buyDate) + "&todt=" + getFormatedDateAsString(buyDate);
//			
//			nav = getNavValue(uri, fundName);
//			totalUnits = fundValue / nav;
//		
//
//		} 
        System.out.println("sipUnits.size() : "+sipUnits.size());
			while ((date.compareTo(currentDate) < 0) && (sip>sipUnits.size())) {
				String uri = "http://portal.amfiindia.com/DownloadNAVHistoryReport_Po.aspx?mf=" + fundHouseValue+ "&tp=1&frmdt=" + getFormatedDateAsString(date) + "&todt=" + getFormatedDateAsString(date);
				
				nav = getNavValue(uri, fundName);
				sipUnits.add(sipAmount / nav);
				totalUnits += sipAmount / nav;
				System.out.println("totalUnits : "+totalUnits);
				
				date = incrementDateOneMonth(date);
			}
			fund.setSipUnits(sipUnits);

			fundValue+=sipUnits.size()*sipAmount;
			fund.setFundValue(fundValue);
		fund.setTotalUnits(totalUnits);
		return fund;
	}

	private static Date incrementDateOneMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// cal.set(Calendar.MONTH, (cal.get(Calendar.MONTH)+6));
		cal.add(Calendar.MONTH, 1);
		date = cal.getTime();
		System.out.println(date);
		return date;
	}

	// Get NAV value based on date for specific fund house and fund name
	private static double getNavValue(String uri, String fundName) {
		double nav = 0;
		//String uri = "http://portal.amfiindia.com/DownloadNAVHistoryReport_Po.aspx?mf=" + fundHouseValue+ "&tp=1&frmdt=" + date + "&todt=" + date;
		System.out.println(uri);
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(uri, String.class);
		StringTokenizer lines = new StringTokenizer(result, "\n");

		// Loop through all lines

		while (lines.hasMoreTokens()) {
			// console.log('Line ' + j + ' is ' + lines[j])
			String fundLine = lines.nextToken();
			// fundLine=str.toLowerCase();

			if (fundLine.contains(fundName)) {

				StringTokenizer values = new StringTokenizer(fundLine, ";");
				int value_len = values.countTokens();
				int k = 0;
				while (values.hasMoreTokens()) {
					k++;
					if (k == (value_len - 2)) {
						nav = Double.parseDouble(values.nextToken());
						break;
					} else {
						values.nextToken();
					}
				}

			}
		}
		System.out.println(nav);
		return nav;
	}

	// Convert String value to Date format
	private static Date convertStringToDate(String dateString) {
		Date date = null;
		Date formatteddate = null;
		DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		try {
			date = df.parse(dateString);
			// formatteddate = df.format(date);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return date;
	}

}
