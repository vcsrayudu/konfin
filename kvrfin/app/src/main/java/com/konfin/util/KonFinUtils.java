package com.konfin.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.telephony.TelephonyManager;

public class KonFinUtils {
	
	public static HttpGet transferPostData(String userId, String password) {
	    // Create a new HttpClient and Post Header
		HttpGet httppost = new HttpGet(CONSTANTS.serverURL+"/funds/"+userId);
	  //  HttpPost httppost = new HttpPost("www.google.com");

	    try {
	   // 	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    	//nameValuePairs.add(new BasicNameValuePair("operation",""+CONSTANTS.TRANSFER));
	    	

	    //    nameValuePairs.add(new BasicNameValuePair("password", password));
	      
	     //   httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	        
	        return httppost;
	    }  catch (Exception e) {
	        // TODO Auto-generated catch block
	    }

	    return null;
	} 
	
	public static HttpPost getBalancePostdata(String userId, String password){
		  HttpPost httppost = new HttpPost(CONSTANTS.serverURL+"/transferAmount");
		  //  HttpPost httppost = new HttpPost("www.google.com");

		    try {
		    	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();  

		    	nameValuePairs.add(new BasicNameValuePair("userId", userId));
		     	        nameValuePairs.add(new BasicNameValuePair("password", password));
		      
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		        
		        return httppost;
		    }  catch (Exception e) {
		        // TODO Auto-generated catch block
		    }

		    return null;
	}
	

}
