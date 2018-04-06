package com.konfin.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;

public class RequestTask extends AsyncTask<String, String, String>{
public static JSONArray results=null;
	HttpGet getData=null;
	public RequestTask(HttpGet getData)
	{
		this.getData=getData;
		
	}
	@Override
       protected void onPreExecute() {
           super.onPreExecute();
       }
	

	 

   @Override
   protected String doInBackground(String... uri) {
   	 HttpClient httpclient = new DefaultHttpClient();
       HttpResponse response;
       
//       this.dialog.setMessage("Processing..."); 
//       this.dialog.show();
       String responseString = null;
       try {
           System.out.println("URI : "+ getData.getURI());
          response = httpclient.execute(getData);
           StatusLine statusLine = response.getStatusLine();
           System.out.println("statusLine : "+statusLine.getStatusCode());
           if(statusLine.getStatusCode() == HttpStatus.SC_OK){
               ByteArrayOutputStream out = new ByteArrayOutputStream();
              // response.getEntity().writeTo(out);
               BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
               String json="";
               String line=null;
               		//reader.readLine();
//               line=reader.readLine();
//               line=reader.readLine();
               
               while ((line = reader.readLine()) != null) {
                   json += line;
               }
               results = new JSONArray(json);
 System.out.println("results : "+results);
               // RETRIEVE EACH JSON OBJECT'S FIELDS
             
          //  RESULT=jo.getInt("RES");
     
               out.close();
           } else{
               //Closes the connection.
      //     	TRANS_RESPONSE="TRANSACTION UNSUCCESFULL";
               response.getEntity().getContent().close();
               throw new IOException(statusLine.getReasonPhrase());
           }
       } catch (ClientProtocolException e) {
           //TODO Handle problems..
          e.printStackTrace();
    //   	TRANS_RESPONSE="TRANSACTION UNSUCCESFULL : "+e.getStackTrace();
       } catch (Exception e) {
           //TODO Handle problems..
           e.printStackTrace();
   //    	TRANS_RESPONSE="TRANSACTION UNSUCCESFULL : "+e;
       }
       return responseString;
   }

   @Override
   protected void onPostExecute(String result) {
       super.onPostExecute(result);
       //Do anything with response..
   }
}
