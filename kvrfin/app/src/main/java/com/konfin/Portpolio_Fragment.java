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
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.konfin.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

public class Portpolio_Fragment extends Fragment implements
        OnClickListener {
    private static View view;
  private static Map<Integer,Boolean> fundHouseExpantion;
    private static FragmentManager fragmentManager;


private String portpolio;
    public Portpolio_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args = getArguments();
        fundHouseExpantion=new HashMap<>();
         portpolio = args.getString("portpolio");
        view = inflater.inflate(R.layout.portpolio_layout, container,
                false);
     //   rowIds=new ArrayList<>();
            initViews();

        setListeners();
        return view;
    }

    private TableRow createFundRow(Fund fundObject)
    {
        int fundNameValue = Integer.parseInt(fundObject.getFundName());
        TableRow fundRow = new TableRow(getActivity());
        //   int fundHouseValue = Integer.parseInt(fundObject.getFundHouseValue());
        //   newTable.setId(fundHouseValue + 1000);
        //    fundHouseExpantion.put(fundHouseValue, false);
        System.out.println("Fund House Object: " + fundObject.getFundHouseValue());
        //     rowIds.add(fundHouseValue);
        fundRow.setId(fundNameValue);
        fundRow.setOnClickListener(this);
        TextView fundName = new TextView(getActivity());
        fundName.setText(CONSTANTS.fundNameMap.get(fundNameValue));
        fundName.setPadding(20, 20, 15, 20);

        fundRow.addView(fundName);
        TextView units = new TextView(getActivity());

        units.setText(fundObject.getTotalUnits().toString());
        units.setPadding(2, 20, 15, 20);
        fundRow.addView(units);
        TextView iamount = new TextView(getActivity());
        iamount.setText("" + fundObject.getFundValue());
        iamount.setPadding(2, 20, 15, 20);
        fundRow.addView(iamount);
        TextView camount = new TextView(getActivity());
        double currentValue = fundObject.getNav() * fundObject.getTotalUnits();
        camount.setText("" + currentValue);
        camount.setPadding(2, 20, 15, 20);
        System.out.println("Current Value: " + currentValue);
        fundRow.addView(camount);
        TextView gain = new TextView(getActivity());
        double gainValue = currentValue-fundObject.getFundValue();
        gain.setText("" + gainValue);
        gain.setPadding(2, 20, 15, 20);
        System.out.println("Current Value: " + currentValue);
        fundRow.addView(gain);
        return fundRow;
    }

    private TableRow createFundHouseRow(Fund fundObject)
    {
        int fundHouseValue = Integer.parseInt(fundObject.getFundHouseValue());

     //   int fundNameValue = Integer.parseInt(fundObject.getFundName());
        TableRow fundRow = new TableRow(getActivity());
        //   int fundHouseValue = Integer.parseInt(fundObject.getFundHouseValue());
        //   newTable.setId(fundHouseValue + 1000);
        //    fundHouseExpantion.put(fundHouseValue, false);
        System.out.println("Fund House Object: " + fundObject.getFundHouseValue());
        //     rowIds.add(fundHouseValue);
        fundRow.setId(fundHouseValue);
        fundRow.setOnClickListener(this);
        TextView fundName = new TextView(getActivity());
        fundName.setText(CONSTANTS.fundHouseMap.get(fundHouseValue));
        fundName.setPadding(2, 20, 15, 20);

        fundRow.addView(fundName);
        TextView units = new TextView(getActivity());

        units.setText(fundObject.getTotalUnits().toString());
        units.setPadding(2, 20, 15, 20);
        fundRow.addView(units);
        TextView iamount = new TextView(getActivity());
        iamount.setText("" + fundObject.getFundValue());
        iamount.setPadding(2, 20, 15, 20);
        fundRow.addView(iamount);
        TextView camount = new TextView(getActivity());
        double currentValue = fundObject.getNav() * fundObject.getTotalUnits();
        camount.setText("" + currentValue);
        camount.setPadding(2, 20, 15, 20);
        System.out.println("Current Value: " + currentValue);
        fundRow.addView(camount);
        TextView gain = new TextView(getActivity());
        double gainValue = currentValue-fundObject.getFundValue();
        gain.setText("" + gainValue);
        gain.setPadding(2, 20, 15, 20);
        System.out.println("Current Value: " + currentValue);
        fundRow.addView(gain);
        return fundRow;
    }
    // Initialize the views
    private void initViews()  {
        fragmentManager = getActivity().getSupportFragmentManager();
        try {

           TableLayout portpolioTable = (TableLayout) view.findViewById(R.id.portpolio);

            JSONArray fundsList=new JSONArray(portpolio);
           for(int i=0;i<fundsList.length();i++) {
               JSONObject fundJSON = fundsList.getJSONObject(i);
               Fund fundObject = new ObjectMapper().readValue(fundJSON.toString(), Fund.class);
               int fundHouseValue = Integer.parseInt(fundObject.getFundHouseValue());
               int fundNameValue = Integer.parseInt(fundObject.getFundName());
               System.out.println("Fund Object: " + fundObject.getFundName());
             TableLayout fundHouse = (TableLayout) view.findViewById(fundHouseValue+1000);
             if(fundHouse==null) {
                 fundHouse = new TableLayout(getActivity());
                 fundHouse.setId(fundHouseValue+1000);
                 TableRow houseRow=createFundHouseRow(fundObject);
                 TableRow fundRow=createFundRow(fundObject);
                 fundHouse.addView(houseRow);
                 fundHouse.addView(fundRow);

                 portpolioTable.addView(fundHouse);
             }
             else
             {
                 TableRow fundRow=createFundRow(fundObject);
                 TableRow houseRow=(TableRow)view.findViewById(fundHouseValue);
              //   TextView amount=(TextView)view.findViewById() ;
                 fundHouse.addView(fundRow);

               //  portpolioTable.addView(fundHouse);

             }
//                         TableRow fundRow = new TableRow(getActivity());
//            //   int fundHouseValue = Integer.parseInt(fundObject.getFundHouseValue());
//            //   newTable.setId(fundHouseValue + 1000);
//           //    fundHouseExpantion.put(fundHouseValue, false);
//               System.out.println("Fund House Object: " + fundObject.getFundHouseValue());
//               //     rowIds.add(fundHouseValue);
//               fundRow.setId(fundNameValue);
//               fundRow.setOnClickListener(this);
//               TextView fundName = new TextView(getActivity());
//               fundName.setText(CONSTANTS.fundNameMap.get(fundNameValue));
//               fundName.setPadding(2, 20, 15, 20);
//
//               fundRow.addView(fundName);
//               TextView units = new TextView(getActivity());
//
//               units.setText(fundObject.getTotalUnits().toString());
//               units.setPadding(2, 20, 15, 20);
//               fundRow.addView(units);
//               TextView iamount = new TextView(getActivity());
//               iamount.setText("" + fundObject.getFundValue());
//               iamount.setPadding(2, 20, 15, 20);
//               fundRow.addView(iamount);
//               TextView camount = new TextView(getActivity());
//               double currentValue = fundObject.getNav() * fundObject.getTotalUnits();
//               camount.setText("" + currentValue);
//               camount.setPadding(2, 20, 15, 20);
//               System.out.println("Current Value: " + currentValue);
//               fundRow.addView(camount);
//               TextView gain = new TextView(getActivity());
//               double gainValue = currentValue-fundObject.getFundValue();
//               gain.setText("" + gainValue);
//               gain.setPadding(2, 20, 15, 20);
//               System.out.println("Current Value: " + currentValue);
//               fundRow.addView(gain);
//
//               portpolioTable.addView(fundRow);


           }



        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Set Listeners over buttons
    private void setListeners() {

    }

    @Override
    public void onClick(View v) {
    int eventId=v.getId();
    try {
        JSONArray fundsList = new JSONArray(portpolio);
        System.out.println("System Evnt ID : "+eventId);
        for (int i = 0; i < fundsList.length(); i++) {
            JSONObject fundJSON = fundsList.getJSONObject(i);
            Fund fundObject = new ObjectMapper().readValue(fundJSON.toString(), Fund.class);

            if(fundObject.getFundName().equals(""+eventId))
            {
                System.out.println("Event Occers : "+eventId);
                Bundle args = new Bundle();
                Fund_Fragment f=new Fund_Fragment();
                args.putString("fund", fundJSON.toString());
                f.setArguments(args);
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.frameContainer,
                                f,
                                Utils.Portpolio_Fragment).commit();
                break;
            }
        }
    }
        catch (Exception e) {
        e.printStackTrace();
    }
//        if(!fundHouseExpantion.get(eventId)) {
//
//            TableLayout fundsTable = (TableLayout) view.findViewById(R.id.portpolioList);
//            TableLayout fundHouseTable = (TableLayout) view.findViewById(eventId+1000);
//            TableRow fundHouseRow = (TableRow) view.findViewById(eventId);
//            try {
//                TableRow funds = new TableRow(getActivity());
//                Fund fundObject = new ObjectMapper().readValue(portpolio, Fund.class);
//                int fundNameValue = Integer.parseInt(fundObject.getFundName());
//                funds.setId(fundNameValue);
//                funds.setOnClickListener(this);
//                TextView fundName = new TextView(getActivity());
//                fundName.setText(CONSTANTS.fundNameMap.get(fundNameValue));
//                fundName.setPadding(20, 0, 15, 0);
//
//                funds.addView(fundName);
//                TextView buyDate = new TextView(getActivity());
//
//                buyDate.setText(fundObject.getBuyDate());
//                buyDate.setPadding(2, 0, 15, 0);
//                funds.addView(buyDate);
//                TextView iamount = new TextView(getActivity());
//                iamount.setText("" + fundObject.getFundValue());
//                iamount.setPadding(2, 0, 15, 0);
//                funds.addView(iamount);
//                TextView camount = new TextView(getActivity());
//                double currentValue = fundObject.getNav() * fundObject.getTotalUnits();
//                camount.setText("" + currentValue);
//                camount.setPadding(2, 0, 15, 0);
//             //   System.out.println("fundHouseExpantion.get(v.getId()) " + fundHouseExpantion.get(v.getId()));
//                funds.addView(camount);
//              //  fundHouseRow.addView(funds);
//                fundHouseTable.addView(funds);
//                fundHouseExpantion.put(eventId,true);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//        else
//        {
//            fundHouseExpantion.put(eventId,false);
//
//            TableLayout fundsTable = (TableLayout) view.findViewById(R.id.portpolioList);
//            TableLayout fundHouseTable = (TableLayout) view.findViewById(eventId+1000);
//            TableRow fundHouse = (TableRow) view.findViewById(eventId);
//            fundHouseTable.removeAllViews();
//            fundHouseTable.addView(fundHouse);
//            System.out.println("Removing Value: "+fundHouse.getTag());
//
//
//        }
    }

}