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
    private static Map<Integer, Boolean> fundHouseExpantion;
    private static FragmentManager fragmentManager;


    private String fund;
    private String status;

    public Fund_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args = getArguments();
        fundHouseExpantion = new HashMap<>();
        fund = args.getString("fund");
        //status = args.getString("status");
        view = inflater.inflate(R.layout.fund_layout, container,
                false);
        //   rowIds=new ArrayList<>();
        initViews();

        setListeners();
        return view;
    }


    private void createFundHouseLayout(LinearLayout fundHouse, Fund fundObject) {
        int fundHouseValue = Integer.parseInt(fundObject.getFundHouseValue());
        int fundNameValue = Integer.parseInt(fundObject.getFundName());

        System.out.println("Fund House Object: " + fundObject.getFundHouseValue());
        int black=getResources().getColor(R.color.black);
        TextView fundName = (TextView)view.findViewById(R.id.fundHouse);
        fundName.setText(CONSTANTS.fundHouseMap.get(fundHouseValue));
        fundName.setTextColor(black);
        TextView scheme = new TextView(getActivity());
        scheme.setText("Scheme : " + CONSTANTS.fundNameMap.get(fundNameValue));
        scheme.setPadding(18, 10, 15, 10);
        scheme.setTextColor(black);
       // scheme.setTypeface(Typeface.DEFAULT_BOLD);
       // scheme.setTextColor(R.color.white);
        scheme.setTextSize(18);
        fundHouse.addView(scheme);

        TextView units = new TextView(getActivity());
        units.setText("Total Units : " + fundObject.getTotalUnits().toString());
        units.setPadding(18, 10, 15, 10);
        units.setTextColor(black);
        fundHouse.addView(units);
        TextView iamount = new TextView(getActivity());
        iamount.setText("Invested Amount : " + fundObject.getFundValue());
        iamount.setPadding(18, 10, 15, 10);
        iamount.setTextColor(black);
        fundHouse.addView(iamount);
        TextView camount = new TextView(getActivity());
        double currentValue = fundObject.getNav() * fundObject.getTotalUnits();
        camount.setText("Market Value : " + currentValue);
        camount.setPadding(18, 10, 15, 10);
        camount.setTextColor(black);
        fundHouse.addView(camount);
        TextView gain = new TextView(getActivity());
        double gainValue = (currentValue - fundObject.getFundValue())/fundObject.getFundValue();
        gain.setText("Gain : " + (gainValue*100)+"%");
        gain.setPadding(18, 10, 15, 10);
        gain.setTextColor(black);
        fundHouse.addView(gain);
        TextView buyDate = new TextView(getActivity());
        buyDate.setText("Buy Date : " + fundObject.getBuyDate());
        buyDate.setPadding(18, 10, 15, 10);
        buyDate.setTextColor(black);
        fundHouse.addView(buyDate);

        TextView nav = new TextView(getActivity());
        nav.setText("NAV  : " + fundObject.getNav());
        nav.setPadding(18, 10, 15, 10);
        nav.setTextColor(black);
        fundHouse.addView(nav);

//        TextView buyDate = new TextView(getActivity());
//        buyDate.setText("Buy Date : " + fundObject.getBuyDate());
//        buyDate.setPadding(18, 20, 15, 20);
//        fundHouse.addView(buyDate);
//        TextView buyDate = new TextView(getActivity());
//        buyDate.setText("Buy Date : " + fundObject.getBuyDate());
//        buyDate.setPadding(18, 20, 15, 20);
//        fundHouse.addView(buyDate);

    }

    // Initialize the views
    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();
        try {

          //  LinearLayout portpolioTable = (LinearLayout) view.findViewById(R.id.fundDisplay);
                   Fund fundObject = new ObjectMapper().readValue(fund.toString(), Fund.class);
                    int fundHouseValue = Integer.parseInt(fundObject.getFundHouseValue());
                    int fundNameValue = Integer.parseInt(fundObject.getFundName());
                    LinearLayout fundHouse = (LinearLayout) view.findViewById(R.id.fundDisplay);
                    fundHouse.setId(fundNameValue);
                  //  fundHouse.setOrientation(LinearLayout.VERTICAL);
                   // fundHouse.setOnClickListener(this);
                    createFundHouseLayout(fundHouse, fundObject);
                  //  portpolioTable.addView(fundHouse);





        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    // Set Listeners over buttons
    private void setListeners() {
        Button back=(Button)view.findViewById(R.id.backToPortpolio);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int eventId = v.getId();
    //    System.out.println("Event :"+eventId);
    //   fragmentManager.popBackStack();
        fragmentManager.popBackStackImmediate();


    }

}