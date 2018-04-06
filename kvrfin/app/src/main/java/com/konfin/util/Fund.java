package com.konfin.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Fund {
	//long id;
	String fundName;
	String fundHouseValue;
	

	long fundValue;
	String buyDate;
	long sip;
	Double totalUnits;
	Double nav;
	
	public Double getNav() {
		return nav;
	}

	public void setNav(Double nav) {
		this.nav = nav;
	}

	//String user;
	List sipUnits;

/*	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}*/
	public String getFundHouseValue() {
		return fundHouseValue;
	}

	public void setFundHouseValue(String fundHouseValue) {
		this.fundHouseValue = fundHouseValue;
	}

	public void setSip(long sip) {
		this.sip = sip;
	}

	public void setTotalUnits(Double totalUnits) {
		this.totalUnits = totalUnits;
	}
	public Fund() {
		super();
	}

	public Double getTotalUnits() {
		return totalUnits;
	}

	public Fund(int i, String fundName, long fundValue) {
		super();
		//this.id = i;
		this.fundName = fundName;
		this.fundValue = fundValue;
	}

	public String getBuyDate() {

		return buyDate;
	}

	public void setBuyDate(String buyDate) {
		
		this.buyDate = buyDate;
	}

	public long getSip() {

		return sip;
	}

	

	

	
	public List getSipUnits() {
		return sipUnits;
	}

	public void setSipUnits(List sipUnits) {
		//System.out.println("sipUnits: "+sipUnits);
		this.sipUnits = sipUnits;
	}

	/*public long getId() {
		return id;
	}

	public void setId(long id) {
		// System.out.println("id: "+id);
		this.id = id;
	}
*/
	public String getFundName() {
		return fundName;
	}

	public void setFundName(String fundName) {
		// System.out.println("fundName: "+fundName);
		this.fundName = fundName;
	}

	public long getFundValue() {
		return fundValue;
	}

	public void setFundValue(long fundValue) {
		// System.out.println("fundValue: "+fundValue);
		this.fundValue = fundValue;
	}
}
