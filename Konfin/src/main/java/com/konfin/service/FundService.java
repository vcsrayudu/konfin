package com.konfin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.konfin.FundsUtils;
import com.konfin.bean.Fund;
import com.konfin.bean.InputParams;

public class FundService {
	public FundService() {
		super();

		
	}

	public List getAllFunds(String user) {

		List funds = FundsUtils.getFunds(user);
		// new ArrayList(fundsMap.values());
		return funds;
	}

	
	public Fund getFund(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public InputParams deleteFund(InputParams fund) {
		FundsUtils.deleteFund(fund);

		return fund;

	}

	public InputParams updateFund(InputParams fund) {
		FundsUtils.updateFund(fund);

		return fund;
	}

	public InputParams addFund(InputParams fund) {
		System.out.println("Fund : " + fund);
		FundsUtils.addFund(fund);

		return fund;
	}

}
