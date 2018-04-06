package com.konfin.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.konfin.FundsUtils;
import com.konfin.bean.User;

public class UserService {
	public UserService() {
		super();

	}

	public static List getAllUsers() {
		List<String> userList = new ArrayList<String>();
		JSONObject usersJson = FundsUtils.readFile("users.json");
		JSONArray userArray = (JSONArray) usersJson.get("users");
		for (Object userObj : userArray.toArray()) {
			JSONObject user = (JSONObject) userObj;
			userList.add(user.get("userName").toString());
		}
		// new ArrayList(fundsMap.values());

		return userList;
	}

	public static User addUser(User user) {
		JSONObject users = null;
		JSONArray usersList = null;
		// System.out.println(user);
		try {

			users = FundsUtils.readFile("users.json");
			// System.out.println("portpolios: "+portpolios);
			usersList = (JSONArray) users.get("users");
			boolean userExist = false;
			for (Object userObj : usersList.toArray()) {
				JSONObject userJson = (JSONObject) userObj;
				String userName = (String) userJson.get("userName");
				if (userName.equals(user.getUserName())) {

					userExist = true;
					break;
				}
			}
			if (!userExist) {
				ObjectMapper mapper = new ObjectMapper();

				String jsonInString = mapper.writeValueAsString(user);
				JSONObject jsonObj = (JSONObject) ((new JSONParser()).parse(jsonInString));

				usersList.add(jsonObj);
				users.put("users", usersList);

				try {
					FundsUtils.writeFile(users.toJSONString(), "users.json");
					user.setPassword("");
					user.setUserPan("");
					// inputParams.setFund(null);
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				System.out.println("User already Exist");

			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return user;
	}
	public static User updateUser(User user) {
		JSONObject users = null;
		JSONArray usersList = null;
		// System.out.println(user);
		try {

			users = FundsUtils.readFile("users.json");
			// System.out.println("portpolios: "+portpolios);
			usersList = (JSONArray) users.get("users");
			boolean userExist = false;
			for (Object userObj : usersList.toArray()) {
				JSONObject userJson = (JSONObject) userObj;
				String userName = (String) userJson.get("userName");
				if (userName.equals(user.getUserName())) {

					userExist = true;
					usersList.remove(userJson);
					break;
				}
			}
			if (userExist) {
				ObjectMapper mapper = new ObjectMapper();

				String jsonInString = mapper.writeValueAsString(user);
				JSONObject jsonObj = (JSONObject) ((new JSONParser()).parse(jsonInString));

				usersList.add(jsonObj);
				users.put("users", usersList);

				try {
					FundsUtils.writeFile(users.toJSONString(), "users.json");
					user.setPassword("");
					user.setUserPan("");
					// inputParams.setFund(null);
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				System.out.println("User Not Exist");

			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return user;
	}

}
