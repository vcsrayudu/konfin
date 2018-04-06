package com.konfin.controller;
 
import java.util.List;
import  com.konfin.bean.Country;
import  com.konfin.service.CountryService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
 
@RestController
public class CountryController {
 
 CountryService countryService = new CountryService();
 
 @RequestMapping(value = "/countries", method = RequestMethod.GET, headers = "Accept=application/json,application/xml")
 public List getCountries() {
 List listOfCountries = countryService.getAllCountries();
 return listOfCountries;
 }
 
 @RequestMapping(value = "/country/{id}", method = RequestMethod.GET, headers = "Accept=application/json,application/xml")
 public Country getCountryById(@PathVariable int id) {
 return countryService.getCountry(id);
 }
 
 @RequestMapping(value = "/countries", method = RequestMethod.POST, headers = "Accept=application/json,application/xml")
 public Country addCountry(@RequestBody Country country) {
 return countryService.addCountry(country);
 }
 
 @RequestMapping(value = "/countries", method = RequestMethod.PUT, headers = "Accept=application/json,application/xml")
 public Country updateCountry(@RequestBody Country country) {
 return countryService.updateCountry(country);
 
 }
 
 @RequestMapping(value = "/country/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json,application/xml")
 public void deleteCountry(@PathVariable("id") int id) {
 countryService.deleteCountry(id);
 
 } 
}