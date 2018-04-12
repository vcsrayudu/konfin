package com.konfin.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.Objects;

public class Utils {

    //Email Validation pattern
    public static final String regEx = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";

    //Fragments Tags
    public static final String Login_Fragment = "Login_Fragment";
    public static final String SignUp_Fragment = "SignUp_Fragment";
    public static final String Portpolio_Fragment = "Portpolio_Fragment";
    public static final String ForgotPassword_Fragment = "ForgotPassword_Fragment";
    public static final String Fund_Fragment = "Fund_Fragment";
    public static final String Admin_Fragment = "Admin_Fragment";

    public static String convertObjectToJson(Object object) {
        String jsonInString=null;
        try {
            ObjectMapper mapper = new ObjectMapper();
             jsonInString = mapper.writeValueAsString(object);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonInString;
    }
    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

}
