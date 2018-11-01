package com.ccpp.demo3ds;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class Currency implements Parcelable{
	
	private String code;
	private String name;
  
 
	public Currency(){
		this.code = "";
		this.name = ""; 
	}
	
	public Currency(String name,String code){
		this.code = code;
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeStringArray(new String[]{
				this.code,
				this.name
		});
	}

	public Currency(Parcel in){
		String[] data= new String[2];
		in.readStringArray(data);
		this.code = data[0];
		this.name = data[1];
	}

	public static final Creator<Currency> CREATOR= new Parcelable.Creator<Currency>() { 
		@Override
		public Currency createFromParcel(Parcel source) {
		// TODO Auto-generated method stub
		return new Currency(source);  
		}

		@Override
		public Currency[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Currency[size];
		}
	};
	
	
	
	public static ArrayList<Currency> currencyArr;
	public static void initCurrencyList(){
		currencyArr = new ArrayList<Currency>();
		currencyArr.add(new Currency("Thai Baht","764"));
		currencyArr.add(new Currency("US Dollar","840"));
		currencyArr.add(new Currency("Singapore Dollar","702")); 
		currencyArr.add(new Currency("Japan Yen","392")); 
		currencyArr.add(new Currency("Pound Sterling", "826"));
		currencyArr.add(new Currency("Malaysian Ringgit","458")); 
		currencyArr.add(new Currency("Indonesia Rupiah","360")); 
		currencyArr.add(new Currency("Euro", "978"));
		currencyArr.add(new Currency("Myanmar", "104"));
	}	
	public static ArrayList<Currency> getCurrencyList(){
		if(currencyArr==null) initCurrencyList();
		return currencyArr;
	}
	
//	public static HashMap<String, Currency> getCurrencyMapByCountryCode(){
//		HashMap<String, Currency> countryMap = new HashMap<String, Currency>();
//		if(Currency.countryArr!=null){
//			for (Currency country : Currency.countryArr) {
//				countryMap.put(country.getCode(), country);
//				}
//		}
//		return countryMap;
//	}
//	
//	public static HashMap<String, Currency> getCountryMapByCountryNumeric(){
//		HashMap<String, Currency> countryMap = new HashMap<String, Currency>();
//		if(Currency.countryArr!=null){
//			for (Currency country : Currency.countryArr) {
//				countryMap.put(country.getId(), country);
//				}
//		}
//		return countryMap;
//	}
//	
//	public static Currency findCountryByCode(String countryCode){
//		HashMap<String, Currency> countryMap = getCountryMapByCountryCode();
//		if(countryMap.containsKey(countryCode))
//			return countryMap.get(countryCode);
//		return new Currency();
//	}
}



