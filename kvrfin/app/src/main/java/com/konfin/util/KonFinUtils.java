package com.konfin.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.telephony.TelephonyManager;

public class KonFinUtils {

    public static HttpGet getFundsData(String userLoninId, String password) {
        // Create a new HttpClient and Post Header
        HttpGet httppost = new HttpGet(CONSTANTS.serverURL + "/funds/" + userLoninId);
        //  HttpPost httppost = new HttpPost("www.google.com");

        try {
            // 	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            //nameValuePairs.add(new BasicNameValuePair("operation",""+CONSTANTS.TRANSFER));


            //    nameValuePairs.add(new BasicNameValuePair("password", password));

            //   httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            return httppost;
        } catch (Exception e) {
            // TODO Auto-generated catch block
        }

        return null;
    }
    public static HttpGet getUsers(String userLoninId) {
        // Create a new HttpClient and Post Header
        HttpGet httppost = new HttpGet(CONSTANTS.serverURL + "/users");
        //  HttpPost httppost = new HttpPost("www.google.com");

        try {
            // 	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            //nameValuePairs.add(new BasicNameValuePair("operation",""+CONSTANTS.TRANSFER));


            //    nameValuePairs.add(new BasicNameValuePair("password", password));

            //   httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            return httppost;
        } catch (Exception e) {
            // TODO Auto-generated catch block
        }

        return null;
    }

    public static HttpPost createAccount(String UserName, String mailid, String password, String pan, String phone) {
        // Create a new HttpClient and Post Header
        User user = new User();
        user.setUserLoginID(pan);
        user.setPassword(password);
        user.setPhone(phone);
        user.setUserFullName(UserName);
       user.setUserMailID(mailid);

        HttpPost httppost = new HttpPost(CONSTANTS.serverURL + "/users");
        //  HttpPost httppost = new HttpPost("www.google.com");

        try {
            String userAsString=Utils.convertObjectToJson(user);
            StringEntity input = new StringEntity(userAsString);
            input.setContentType("application/json");
            httppost.setEntity(input);

            //    nameValuePairs.add(new BasicNameValuePair("password", password));

            //   httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            return httppost;
        } catch (Exception e) {
            // TODO Auto-generated catch block
        }

        return null;
    }

    public static HttpPost getBalancePostdata(String userId, String password) {
        HttpPost httppost = new HttpPost(CONSTANTS.serverURL + "/transferAmount");
        //  HttpPost httppost = new HttpPost("www.google.com");

        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("userId", userId));
            nameValuePairs.add(new BasicNameValuePair("password", password));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            return httppost;
        } catch (Exception e) {
            // TODO Auto-generated catch block
        }

        return null;
    }


}
