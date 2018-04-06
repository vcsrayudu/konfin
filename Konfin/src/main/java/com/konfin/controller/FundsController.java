package com.konfin.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.konfin.bean.Fund;
import com.konfin.bean.InputParams;
import com.konfin.service.FundService;

@RestController
public class FundsController {

	FundService fundService = new FundService();

	@RequestMapping(value = "/funds", method = RequestMethod.GET, headers = "Accept=application/json,application/xml")
	public List getFunds(@RequestBody String userName) {
		List listOfFunds = fundService.getAllFunds(userName);
		return listOfFunds;
	}

	@RequestMapping(value = "/funds/{userName}", method = RequestMethod.GET, headers = "Accept=application/json,application/xml")
	public List getFundByName(@PathVariable String userName) {
		List listOfFunds = fundService.getAllFunds(userName);
		return listOfFunds;
	}

	@RequestMapping(value = "/funds", method = RequestMethod.POST, headers = "Accept=application/json,application/xml,text/plain")
	public InputParams addNewFund(@RequestBody InputParams input) {

		return fundService.addFund(input);
	}

	@RequestMapping(value = "/funds", method = RequestMethod.PUT, headers = "Accept=application/json,application/xml")
	public InputParams updateFund(@RequestBody InputParams input) {
		return fundService.updateFund(input);

	}

	@RequestMapping(value = "/funds", method = RequestMethod.DELETE, headers = "Accept=application/json,application/xml")
	public InputParams deleteFund(@RequestBody InputParams input) {

		return fundService.deleteFund(input);
	}
}