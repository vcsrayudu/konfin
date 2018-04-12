package com.konfin.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.konfin.bean.InputParams;
import com.konfin.bean.User;
import com.konfin.service.UserService;

@RestController
public class UserController {
	UserService userService = new UserService();
	@RequestMapping(value = "/users", method = RequestMethod.GET, headers = "Accept=application/json,application/xml")
	public HashMap getUsers() {
		HashMap<String,String> listOfUsers = userService.getAllUsers();
		return listOfUsers;
	}
	@RequestMapping(value = "/users", method = RequestMethod.POST, headers = "Accept=application/json,application/xml,text/plain")
	public User addNewUser(@RequestBody  User input) {

		return userService.addUser(input);
	}
	@RequestMapping(value = "/users", method = RequestMethod.PUT, headers = "Accept=application/json,application/xml")
	public User updateFund(@RequestBody User input) {
		return userService.updateUser(input);

	}
}
