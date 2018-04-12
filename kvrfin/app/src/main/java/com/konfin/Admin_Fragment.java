package com.konfin;

/**
 * Created by cvidudal on 4/11/2018.
 */

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.konfin.util.CONSTANTS;
import com.konfin.util.Fund;
import com.konfin.util.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.konfin.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

public class Admin_Fragment extends Fragment implements
        View.OnClickListener {
    private static View view;
    private static Map<Integer, Boolean> fundHouseExpantion;
    private static FragmentManager fragmentManager;

    private static Spinner userName;
    //private String portpolio;
    private String status;
    private HashMap<String,String> userList;
private String users;
    public Admin_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args = getArguments();
        fundHouseExpantion = new HashMap<>();
       // portpolio = args.getString("portpolio");
         users=args.getString("users");

        status = args.getString("status");
        view = inflater.inflate(R.layout.admin_layout, container,
                false);
        //   rowIds=new ArrayList<>();
        userName = (Spinner) view.findViewById(R.id.user);
        initViews();

        setListeners();
        return view;
    }


    private void createFundHouseLayout(LinearLayout fundHouse, Fund fundObject) {
        int fundHouseValue = Integer.parseInt(fundObject.getFundHouseValue());

        System.out.println("Fund House Object: " + fundObject.getFundHouseValue());

        TextView fundName = new TextView(getActivity());
        fundName.setText(CONSTANTS.fundHouseMap.get(fundHouseValue));
        fundName.setPadding(2, 5, 15, 15);
        fundName.setTypeface(Typeface.DEFAULT_BOLD);
        fundName.setTextSize(20);
        int black = getResources().getColor(R.color.black);
        fundName.setTextColor(black);
        fundHouse.addView(fundName);
        TextView units = new TextView(getActivity());

        units.setText("Total Units : " + fundObject.getTotalUnits().toString());
        units.setPadding(15, 5, 15, 5);
        units.setTextColor(black);
        fundHouse.addView(units);
        TextView iamount = new TextView(getActivity());
        iamount.setText("Invested Amount : " + fundObject.getFundValue());
        iamount.setPadding(15, 5, 15, 5);
        iamount.setTextColor(black);
        fundHouse.addView(iamount);
        TextView camount = new TextView(getActivity());
        double currentValue = fundObject.getNav() * fundObject.getTotalUnits();
        camount.setText("Market Value : " + currentValue);
        camount.setTextColor(black);
        camount.setPadding(15, 5, 15, 5);
        fundHouse.addView(camount);
        TextView gain = new TextView(getActivity());
        gain.setTextColor(black);
        double gainValue = (currentValue - fundObject.getFundValue()) / fundObject.getFundValue();
        gain.setText("Gain : " + (gainValue * 100) + "%");
        gain.setPadding(15, 5, 15, 5);
        fundHouse.addView(gain);

    }

    private void selectUser() {

    }

    // Initialize the views
    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();

        try {
            userList = new ObjectMapper().readValue(users, HashMap.class);
            userList.values();
            List items = new ArrayList(userList.values());
            //String []itemsString=new String[];
            ArrayAdapter<String>adapter = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_item,items);
            userName.setAdapter(adapter);






//            System.out.println("portpolioTable.getLayoutParams().width : "+view.getLayoutParams().width);
//            double ratio=view.getLayoutParams().width/10;
//            System.out.println("Width : "+ratio);
//            int width=(int)(ratio*9);
//            System.out.println("Width : "+width);
//            portpolioTable.setLayoutParams(new FrameLayout.LayoutParams(width,LinearLayout.LayoutParams.FILL_PARENT));


        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    // Set Listeners over buttons
    private void setListeners() {
        userName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                if (item != null) {
                    displayPortpolio(item.toString());
                   // Toast.makeText(view.getContext(), item.toString(),
                  //          Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub

            }
        });
    }
private void displayPortpolio(String userNameSelected)
{
    String user=Utils.getKeyByValue(userList,userNameSelected);
    RequestTask requestTask=new RequestTask(KonFinUtils.getFundsData(user,""));

    try
    {
        LinearLayout portpolioTable = (LinearLayout) view.findViewById(R.id.portpolio);
        portpolioTable.removeAllViews();
        String str_result=  requestTask.execute().get();
        Bundle args = new Bundle();
        JSONArray fundsList=requestTask.getResponce;


        if(fundsList.length()!=0)  {
            // JSONArray fundsList = new JSONArray(portpolio);
            for (int i = 0; i < fundsList.length(); i++) {
                JSONObject fundJSON = fundsList.getJSONObject(i);
                Fund fundObject = new ObjectMapper().readValue(fundJSON.toString(), Fund.class);
                int fundHouseValue = Integer.parseInt(fundObject.getFundHouseValue());
                int fundNameValue = Integer.parseInt(fundObject.getFundName());
                System.out.println("Fund Object: " + fundObject.getFundName());
                LinearLayout fundHouse = new LinearLayout(getActivity());
                fundHouse.setId(fundNameValue);
                fundHouse.setOrientation(LinearLayout.VERTICAL);
                fundHouse.setOnClickListener(this);
                createFundHouseLayout(fundHouse, fundObject);
                portpolioTable.addView(fundHouse);
                Button delete = new Button(getActivity());
                delete.setText("Delete");
                delete.setPadding(2, 20, 15, 20);
                portpolioTable.addView(delete);
                Button edit = new Button(getActivity());
                edit.setText("Edit");
                edit.setPadding(2, 20, 15, 20);
                portpolioTable.addView(edit);

            }
        } else {
            TextView status = new TextView(getActivity());
            status.setText("Portpolio is not created. Please Add new funds");
            status.setPadding(2, 20, 15, 20);

            portpolioTable.addView(status);
            Button addNew = new Button(getActivity());
            addNew.setText("Add New Funds");
            addNew.setPadding(2, 20, 15, 20);
        }







    }
    catch(Exception e)
    {
        e.printStackTrace();
    }
}
    @Override
    public void onClick(View v) {
        int eventId = v.getId();
        switch (eventId) {
            case R.id.user:

                break;
            case R.id.back:
                break;
            default:
                try {
                    JSONArray fundsList = null ;//new JSONArray(portpolio);
                    for (int i = 0; i < fundsList.length(); i++) {
                        JSONObject fundJSON = fundsList.getJSONObject(i);
                        Fund fundObject = new ObjectMapper().readValue(fundJSON.toString(), Fund.class);

                        if (fundObject.getFundName().equals("" + eventId)) {
                            Bundle args = new Bundle();
                            Fund_Fragment f = new Fund_Fragment();
                            args.putString("fund", fundJSON.toString());
                            f.setArguments(args);
                            fragmentManager
                                    .beginTransaction()
                                    .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                                    .addToBackStack(null)
                                    .replace(R.id.frameContainer,
                                            f,
                                            Utils.Fund_Fragment).commit();
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }


    }

}