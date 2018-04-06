package com.konfin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.konfin.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

public class Fund_Fragment extends Fragment implements
        OnClickListener {
    private static View view;
    private static Map<Integer,Boolean> fundHouseExpantion;
    private static FragmentManager fragmentManager;


    private String fund;
    public Fund_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args = getArguments();
        fundHouseExpantion=new HashMap<>();
        fund = args.getString("fund");
        view = inflater.inflate(R.layout.fund_layout, container,
                false);
         //   rowIds=new ArrayList<>();
        initViews();

        setListeners();
        System.out.println("On Crete");
        return view;
    }

    // Initialize the views
    private void initViews()  {
        try {
            fragmentManager = getActivity().getSupportFragmentManager();
            LinearLayout layout=(LinearLayout)view.findViewById(R.id.fundDisplay);
         //   LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
        //            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            Fund fundObject = new ObjectMapper().readValue(fund, Fund.class);

            int fundNameValue = Integer.parseInt(fundObject.getFundName());
            int fundHouseValue = Integer.parseInt(fundObject.getFundHouseValue());
            TextView fundHouseName = (TextView)view.findViewById(R.id.fundHouse);;
            fundHouseName.setText(CONSTANTS.fundHouseMap.get(fundHouseValue));

            fundHouseName.setOnClickListener(this);
//            fundHouseName.setPadding(2, 20, 15, 20);
//            fundHouseName.setLayoutParams(lparams);
//            fundHouseName.setTypeface(Typeface.DEFAULT_BOLD);
//            fundHouseName.setTextSize(20);
           // layout.addView(fundHouseName);
            TextView fundName = new TextView(getActivity());
            fundName.setText("Scheme : "+CONSTANTS.fundNameMap.get(fundNameValue));
            fundName.setPadding(12, 20, 15, 20);
         //   fundName.setLayoutParams(lparams);
           //fundName.setTypeface(Typeface.DEFAULT_BOLD);
            fundName.setTextSize(18);
            layout.addView(fundName);

            TextView units = new TextView(getActivity());
           // units.setTypeface(Typeface.DEFAULT_BOLD);
            units.setTextSize(18);
            units.setText("Units : "+fundObject.getTotalUnits().toString());
            units.setPadding(2, 20, 15, 20);
            layout.addView(units);
            TextView iamount = new TextView(getActivity());
            iamount.setText("Invested Amount : " + fundObject.getFundValue());
            iamount.setPadding(2, 20, 15, 20);
          //  iamount.setTypeface(Typeface.DEFAULT_BOLD);
            iamount.setTextSize(18);
            layout.addView(iamount);
            TextView camount = new TextView(getActivity());
            double currentValue = fundObject.getNav() * fundObject.getTotalUnits();
            camount.setText("Market Value : " + currentValue);
            //camount.setTypeface(Typeface.DEFAULT_BOLD);
            camount.setTextSize(18);
            camount.setPadding(2, 20, 15, 20);
            System.out.println("Current Value: " + currentValue);
            layout.addView(camount);
            TextView gain = new TextView(getActivity());
           // gain.setTypeface(Typeface.DEFAULT_BOLD);
            gain.setTextSize(18);
            double gainValue = currentValue-fundObject.getFundValue();
            gain.setText("Gain : " + gainValue);
            gain.setPadding(2, 20, 15, 20);
            layout.addView(gain);
            gain.setOnClickListener(this);
            layout.setOnClickListener(this);



        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Set Listeners over buttons
    private void setListeners() {
     //   LinearLayout layout=(LinearLayout)view.findViewById(R.id.fundDisplay);
        Button back = (Button)view.findViewById(R.id.backToPortpolio);
 //       Button back=new Button(getActivity());
   //     back.setText("Back");

       // layout.addView(back);
        back.setOnClickListener(this);
        System.out.println("Set on Back");

    }
    private void Backpresses() {
        System.out.print("Click on Back");
       // fragmentManager.popBackStack();
        System.out.print("Clicked on Back");
    }

    @Override
    public void onClick(View v) {
        System.out.print("On Click  start : "+v.getTag());
      //  Backpresses();
        System.out.print("On Click  after : "+v.getTag());

    }

}