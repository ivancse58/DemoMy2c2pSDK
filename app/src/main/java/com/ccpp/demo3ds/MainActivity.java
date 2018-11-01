package com.ccpp.demo3ds;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.ccpp.my2c2psdk.cores.My2c2pConstants;
import com.ccpp.my2c2psdk.cores.My2c2pResponse;
import com.ccpp.my2c2psdk.cores.My2c2pSDK;
import com.ccpp.my2c2psdk.cores.My2c2pSDK.InstallmentType;
import com.ccpp.my2c2psdk.cores.My2c2pSDK.PaymentOption;
import com.ccpp.my2c2psdk.cores.My3DSActivity;
import com.ccpp.my2c2psdk.cores.My2c2pSDK.PaymentChannel;
import com.ccpp.my2c2psdk.mobile.SamsungPayPayment;
import com.ccpp.my2c2psdk.model.SubMerchant;
import com.ccpp.my2c2psdk.model.SubMerchant.SubMerchantBuilder;
import com.ccpp.my2c2psdk.utils.MobilePaymentUtils;
import com.google.gson.Gson;
import com.samsung.android.sdk.samsungpay.v2.SamsungPay;
import com.samsung.android.sdk.samsungpay.v2.StatusListener;

public class MainActivity extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener{
	private static final int REQUEST_SDK = 1;
	private static final int REQUEST_CURRENCY = 2;
	
	//Please change to your own private key if test with own merchant id
	static String PRIVATE_KEY = "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";
		
	//Only for Mobile Payment - SamsungPay, Please refer onResume() for sample code
	//and its only for sdk.paymentUI = false and sdk.paymentChannel = PaymentChannel.SAMSUNG_PAY
	//Not required handle this if using sdk.paymentUI = true
	private boolean mReloadSamsungPayPayment = false;
	
	private LinearLayout layout_payment_channel, layout_payment_option;
	
	private Button btn_back, btn_submit, btn_get;
	private Spinner spinner_payment_channel, spinner_payment_option, spinner_installment_type;
	private ArrayAdapter<String> paymentChannelAdapter, paymentOptionAdapter, installmentTypeAdapter;
	
	private ToggleButton toogle_paymentUI;
	private ToggleButton toogle_enableStoreCard;
	private ToggleButton toogle_enableTokenizeWithoutAuthorization;
	private ToggleButton toogle_useStoredCardOnly;
	
	private EditText et_version;
	private EditText et_merchant_id;
	private EditText et_sub_merchant_id;
	private EditText et_unique_transaction_id;
	private EditText et_description;
	private EditText et_amount;
	private EditText et_currency_code;
	private EditText et_statement_descriptor;
	
	private EditText et_store_unique_id;
	private ToggleButton toogle_store_card;
	private EditText et_card_number;
	private EditText et_card_expiry_month;
	private EditText et_card_expiry_year;
	private EditText et_security_code;
	private EditText et_pin;
	private EditText et_pan_country;
	private EditText et_pan_bank;
	private EditText et_card_holder_name;
	private EditText et_card_holder_email;
	 
	private EditText et_category_id;
	private EditText user_define1;
	private EditText user_define2;
	private EditText user_define3;
	private EditText user_define4;
	private EditText user_define5;
	private EditText et_request_3ds;
	
	private ToggleButton toogle_prodMode;
	private ToggleButton toogle_ipp_transaction;
	private EditText et_installement_period;
	private EditText et_interest_type;
	private EditText et_ipp_product_code;
	private EditText et_ipp_installment_peroid_filter;
	
	private ToggleButton toogle_recurring;
	private EditText et_invoice_prefix;
	private EditText et_recurring_amount;
	private ToggleButton toogle_allow_accumulate;
	private EditText et_recurring_interval;
	private EditText et_max_accumulate_amount;
	private EditText et_recurring_count;
	private EditText et_charge_next_date;
	private EditText et_promotion;
	private EditText et_secretKey;
	
	private EditText et_agent_code;
	private EditText et_channel_code;
	private EditText et_mobile_no;
	
	private EditText et_samsung_pay_service_id;
	private EditText et_samsung_pay_merchant_name;
	
	private EditText et_gcash_account_no;
	
	private TextView tv_response;
	private TextView tv_version;
	private ScrollView sv_response;
	private ScrollView sv_data;
	private String selectedCurrencyCode;
	
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		layout_payment_channel = (LinearLayout) findViewById(R.id.layout_payment_channel);
		layout_payment_option = (LinearLayout) findViewById(R.id.layout_payment_option);

		btn_submit = ((Button) findViewById(R.id.btn_submit)); 
		btn_back = ((Button) findViewById(R.id.btn_back)); 
		btn_get = ((Button) findViewById(R.id.btn_get)); 
		
		sv_response = ((ScrollView) findViewById(R.id.sv_response)); 
		sv_data = ((ScrollView) findViewById(R.id.sv_data)); 
		tv_response = ((TextView) findViewById(R.id.tv_response)); 
		tv_version = ((TextView) findViewById(R.id.tv_version)); 

		et_version = (EditText) findViewById(R.id.et_version);
		
		spinner_payment_channel = (Spinner) findViewById(R.id.spinner_payment_channel);
		paymentChannelAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getPaymentChannelCodeList());
		paymentChannelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		spinner_payment_option = (Spinner) findViewById(R.id.spinner_payment_option);
		paymentOptionAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getPaymentOptionCodeList());
		paymentOptionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		spinner_installment_type = (Spinner) findViewById(R.id.spinner_installment_type);
		installmentTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getInstallmentTypeCodeList());
		installmentTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		toogle_prodMode = (ToggleButton) findViewById(R.id.toogle_prodMode);
		toogle_paymentUI = ((ToggleButton) findViewById(R.id.toogle_paymentUI)); 
		toogle_enableStoreCard = ((ToggleButton) findViewById(R.id.toogle_enableStoreCard)); 
		toogle_enableTokenizeWithoutAuthorization = ((ToggleButton) findViewById(R.id.toogle_enableTokenizeWithoutAuthorization)); 
		toogle_useStoredCardOnly = ((ToggleButton) findViewById(R.id.toogle_useStoredCardOnly));
				
		et_merchant_id = ((EditText) findViewById(R.id.et_merchant_id)); 
		et_sub_merchant_id = ((EditText) findViewById(R.id.et_sub_merchant_id)); 
		et_unique_transaction_id = ((EditText) findViewById(R.id.et_unique_transaction_id)); 
		et_description = ((EditText) findViewById(R.id.et_description)); 
		et_amount = ((EditText) findViewById(R.id.et_amount)); 
		et_currency_code = ((EditText) findViewById(R.id.et_currency_code)); 
		et_statement_descriptor = ((EditText) findViewById(R.id.et_statement_descriptor)); 

		et_store_unique_id = ((EditText) findViewById(R.id.et_store_unique_id)); 
		toogle_store_card = ((ToggleButton) findViewById(R.id.toogle_store_card)); 
		et_card_number = ((EditText) findViewById(R.id.et_card_number)); 
		et_card_expiry_month = ((EditText) findViewById(R.id.et_card_expiry_month)); 
		et_card_expiry_year = ((EditText) findViewById(R.id.et_card_expiry_year)); 
		et_security_code = ((EditText) findViewById(R.id.et_security_code)); 
		et_pin = ((EditText) findViewById(R.id.et_pin)); 
		et_pan_country = ((EditText) findViewById(R.id.et_pan_country)); 
		et_pan_bank = ((EditText) findViewById(R.id.et_pan_bank)); 
		et_card_holder_name = ((EditText) findViewById(R.id.et_card_holder_name)); 
		et_card_holder_email = ((EditText) findViewById(R.id.et_card_holder_email)); 
		et_request_3ds = (EditText) findViewById(R.id.et_request_3ds);
		et_category_id = ((EditText) findViewById(R.id.et_category_id)); 
		user_define1 = ((EditText) findViewById(R.id.user_define1)); 
		user_define2 = ((EditText) findViewById(R.id.user_define2)); 
		user_define3 = ((EditText) findViewById(R.id.user_define3)); 
		user_define4 = ((EditText) findViewById(R.id.user_define4)); 
		user_define5 = ((EditText) findViewById(R.id.user_define5)); 

		toogle_ipp_transaction = ((ToggleButton) findViewById(R.id.toogle_ipp_transaction)); 
		et_installement_period = ((EditText) findViewById(R.id.et_installement_period)); 
		et_interest_type = ((EditText) findViewById(R.id.et_interest_type)); 
		et_ipp_product_code = ((EditText) findViewById(R.id.et_ipp_product_code));
		et_ipp_installment_peroid_filter = ((EditText) findViewById(R.id.et_ipp_installment_peroid_filter));
		
		toogle_recurring = ((ToggleButton) findViewById(R.id.toogle_recurring)); 
		et_invoice_prefix = ((EditText) findViewById(R.id.et_invoice_prefix)); 
		et_recurring_amount = ((EditText) findViewById(R.id.et_recurring_amount)); 
		toogle_allow_accumulate = ((ToggleButton) findViewById(R.id.toogle_allow_accumulate)); 
		et_recurring_interval = ((EditText) findViewById(R.id.et_recurring_interval)); 
		et_max_accumulate_amount = ((EditText) findViewById(R.id.et_max_accumulate_amount)); 
		et_recurring_count = ((EditText) findViewById(R.id.et_recurring_count)); 
		et_charge_next_date = ((EditText) findViewById(R.id.et_charge_next_date)); 
		et_promotion = ((EditText) findViewById(R.id.et_promotion)); 
		et_secretKey = ((EditText) findViewById(R.id.et_secretKey));
		et_request_3ds = (EditText) findViewById(R.id.et_request_3ds);
		
		et_agent_code = ((EditText) findViewById(R.id.et_agent_code));
		et_channel_code = (EditText) findViewById(R.id.et_channel_code);
		et_mobile_no = (EditText) findViewById(R.id.et_mobile_no);
		
		et_samsung_pay_service_id = (EditText) findViewById(R.id.et_samsung_pay_service_id);
		et_samsung_pay_merchant_name = (EditText) findViewById(R.id.et_samsung_pay_merchant_name);
		
		et_gcash_account_no = (EditText) findViewById(R.id.et_gcash_account_no);
		
		et_version.setText(String.valueOf(My2c2pSDK.API_VERSION_DEFAULT));
		et_secretKey.setText("7jYcp4FxFdf0"); 
		et_merchant_id.setText("JT01");
		et_request_3ds.setText("Y");
		et_card_number.setText("4111111111111111");
		et_card_expiry_month.setText("12");
		et_card_expiry_year.setText("2019");
		et_pan_bank.setText("test");
		et_security_code.setText("123");
		et_pan_country.setText("SG");
		et_card_holder_email.setText("davidbilly@2c2p.com");
		et_amount.setText("1");
		et_description.setText("test");
		et_card_holder_name.setText("billy");
		et_currency_code.setText("702");
		
		et_samsung_pay_service_id.setText("5042c0257cb345bc82fd20");
		et_samsung_pay_merchant_name.setText("Demo Merchant SG");
		
		selectedCurrencyCode = "";
	
		tv_version.setText("v " + My2c2pConstants.CONSTANT_SDK_VERSION);
		
		btn_submit.setOnClickListener(this);
		btn_get.setOnClickListener(this);
		btn_back.setOnClickListener(this);
		et_currency_code.setOnClickListener(this);
		
		spinner_payment_channel.setAdapter(paymentChannelAdapter);
		spinner_payment_channel.setOnItemSelectedListener(this); 
		spinner_payment_channel.setSelection(0); // default for NONE
		
		spinner_payment_option.setAdapter(paymentOptionAdapter);
		spinner_payment_option.setOnItemSelectedListener(this); 
		spinner_payment_option.setSelection(0); // default for ALL
		
		spinner_installment_type.setAdapter(installmentTypeAdapter);
		spinner_installment_type.setOnItemSelectedListener(this); 
		spinner_installment_type.setSelection(0); // default for NORMAL
		
		toogle_paymentUI.setOnCheckedChangeListener(new OnCheckedChangeListener() {			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked) {
					layout_payment_channel.setVisibility(View.GONE);
					layout_payment_option.setVisibility(View.VISIBLE);
				} else {
					layout_payment_channel.setVisibility(View.VISIBLE);
					layout_payment_option.setVisibility(View.GONE);
				}
			}
		});
		
		toogle_prodMode.setOnCheckedChangeListener(new OnCheckedChangeListener() {			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked) {
					//prod
					et_secretKey.setText("7jYcp4FxFdf0");
					et_merchant_id.setText("JT01");
					
					
					et_samsung_pay_service_id.setText("5042c0257cb345bc82fd20");
					et_samsung_pay_merchant_name.setText("Happy 2C2P");
				} else {
					//demo
					et_secretKey.setText("7jYcp4FxFdf0");
					et_merchant_id.setText("JT01");
					
					et_samsung_pay_service_id.setText("5042c0257cb345bc82fd20");
					et_samsung_pay_merchant_name.setText("Demo Merchant SG");
				}
			}
		});
		
		
		btn_get.performClick();
		
		toogle_paymentUI.setChecked(false);
		layout_payment_channel.setVisibility(View.VISIBLE);
		layout_payment_option.setVisibility(View.GONE);
		
		resetView();
	}

	private List<String> getPaymentChannelCodeList() {
		List<String> list = new ArrayList<String>();
		for(PaymentChannel channel : PaymentChannel.values()) {
			list.add(channel.name());
		}
		return list;
	}
	
	private List<String> getPaymentOptionCodeList() {
		List<String> list = new ArrayList<String>();
		for(PaymentOption channel : PaymentOption.values()) {
			list.add(channel.name());
		}
		return list;
	}
	
	private List<String> getInstallmentTypeCodeList() {
		List<String> list = new ArrayList<String>();
		for(InstallmentType channel : InstallmentType.values()) {
			list.add(channel.name());
		}
		return list;
	}

	public void resetView(){
		btn_back.setVisibility(View.GONE);
		btn_submit.setVisibility(View.VISIBLE);
		sv_response.setVisibility(View.GONE);
		sv_data.setVisibility(View.VISIBLE);
		tv_response.setText("");
	}
	
	@Override
	public void onClick(View view) {
		if(view == btn_submit){
			if(toogle_enableTokenizeWithoutAuthorization != null && toogle_enableTokenizeWithoutAuthorization.isChecked()) {
				requestSDKTWA();
			} else {
				if(!toogle_paymentUI.isChecked() && PaymentChannel.SAMSUNG_PAY.equals(PaymentChannel.valueOf(spinner_payment_channel.getSelectedItem().toString()))) {
					requestSDKSamsungPay();
				} else if(toogle_paymentUI.isChecked() && PaymentOption.SAMSUNG_PAY.equals(PaymentOption.valueOf(spinner_payment_option.getSelectedItem().toString()))) {
					requestSDKSamsungPay();
				} else {
					requestSDK();
				}
			}
		}else if(view == btn_get){
			et_unique_transaction_id.setText(String.valueOf(System.currentTimeMillis() / 1000L));
		}else if(view == btn_back){
			resetView();
		}else if(view == et_currency_code){
			 Intent intent = new Intent(this, CurrencyActivity.class);
		     intent.putExtra("selectedCountryCode", selectedCurrencyCode);
		     startActivityForResult(intent, REQUEST_CURRENCY);
		}
	}
	
	/**
	 * For TokenizeWithoutAuthorization Request
	 */
	public void requestSDKTWA() {
		
		//Please change to your own private key if test with own merchant id
		My2c2pSDK sdk = new My2c2pSDK(PRIVATE_KEY);
		
		//set mandatory fields
		sdk.productionMode = toogle_prodMode.isChecked();
		
		if(toogle_paymentUI.isChecked()) {
			//For UI Request
			//set mandatory fields
			sdk.merchantID = et_merchant_id.getText().toString();
			sdk.secretKey = et_secretKey.getText().toString();
		    sdk.tokenizeWithoutAuthorization = true;
		} else {
			//For NON-UI Request
			//set mandatory fields
		    sdk.version = sdk.version = et_version.getText().toString();
		    sdk.merchantID = et_merchant_id.getText().toString();
		    sdk.pan = et_card_number.getText().toString();
		    sdk.cardExpireMonth =  et_card_expiry_month.getText().toString();
		    sdk.cardExpireYear = et_card_expiry_year.getText().toString();
		    sdk.cardHolderName = et_card_holder_name.getText().toString();	
		    sdk.secretKey = et_secretKey.getText().toString();
		    sdk.tokenizeWithoutAuthorization = true;
		}
	    
	    //Optional
	    sdk.paymentUI = toogle_paymentUI.isChecked();
	    sdk.cardHolderEmail = et_card_holder_email.getText().toString();
	    sdk.panBank = et_pan_bank.getText().toString();
	    sdk.request3DS = et_request_3ds.getText().toString();
	    
		Intent intent = new Intent(MainActivity.this, My3DSActivity.class);
		intent.putExtra(My2c2pSDK.PARAMS, sdk);
		startActivityForResult(intent, REQUEST_SDK);
	}
	   	
	/**
	 * For Normal Payment Request
	 */
	public void requestSDK() {
		
		//Please change to your own private key if test with own merchant id
		My2c2pSDK sdk = new My2c2pSDK(PRIVATE_KEY);

		double amount = 0;
		try{
			amount = Double.parseDouble(et_amount.getText().toString());
		}catch(Exception e){
  
		}
		et_amount.setText(amount+"");
		sdk.paymentUI = toogle_paymentUI.isChecked();
		sdk.productionMode = toogle_prodMode.isChecked();
		sdk.version = et_version.getText().toString();
		sdk.paymentChannel = PaymentChannel.valueOf(spinner_payment_channel.getSelectedItem().toString()); // only support for Non-UI Payment Request, Refer My2c2pSDK.PaymentChannel class
		sdk.paymentOption = PaymentOption.valueOf(spinner_payment_option.getSelectedItem().toString()); // only support for UI Payment Request, Refer My2c2pSDK.PaymentOption class
	    sdk.merchantID = et_merchant_id.getText().toString();
	    sdk.subMerchantID = et_sub_merchant_id.getText().toString();
	    sdk.uniqueTransactionCode = et_unique_transaction_id.getText().toString();
		sdk.desc = et_description.getText().toString();
		sdk.amount = amount;
		sdk.currencyCode = et_currency_code.getText().toString();	
		sdk.pan = et_card_number.getText().toString();
		sdk.securityCode = et_security_code.getText().toString();
		sdk.cardExpireMonth = et_card_expiry_month.getText().toString();
		sdk.statementDescriptor = et_statement_descriptor.getText().toString();
		
		sdk.cardExpireYear = et_card_expiry_year.getText().toString();
		sdk.cardHolderName = et_card_holder_name.getText().toString();	
		sdk.panCountry = et_pan_country.getText().toString();
		sdk.panBank = et_pan_bank.getText().toString();
		sdk.storeCard = toogle_store_card.isChecked();
		sdk.enableStoreCard = toogle_enableStoreCard.isChecked();
		sdk.cardHolderEmail = et_card_holder_email.getText().toString();
		sdk.payCategoryID = et_category_id.getText().toString();
		sdk.storedCardUniqueID = et_store_unique_id.getText().toString();
		sdk.secretKey = et_secretKey.getText().toString();
		sdk.tokenizeWithoutAuthorization = toogle_enableTokenizeWithoutAuthorization.isChecked();
		sdk.useStoredCardOnly = toogle_useStoredCardOnly.isChecked();
		
		sdk.userDefined1 = user_define1.getText().toString();
		sdk.userDefined2 = user_define2.getText().toString();
		sdk.userDefined3 = user_define3.getText().toString();
		sdk.userDefined4 = user_define4.getText().toString();
		sdk.userDefined5 = user_define5.getText().toString();
	    sdk.recurring = toogle_recurring.isChecked();    
	    sdk.allowAccumulate = toogle_allow_accumulate.isChecked();
	    sdk.ippTransaction = toogle_ipp_transaction.isChecked();
	    sdk.chargeNextDate = et_charge_next_date.getText().toString(); //DDMMYYYY
	    sdk.promotion = et_promotion.getText().toString();

	    sdk.request3DS = et_request_3ds.getText().toString();
	    
	    sdk.agentCode = et_agent_code.getText().toString();
	    sdk.channelCode = et_channel_code.getText().toString();
	    sdk.mobileNo = et_mobile_no.getText().toString();

	    //mandatory only if recurring is set to true
	    sdk.invoicePrefix = et_invoice_prefix.getText().toString();
	    try{
	    	sdk.recurringInterval = Integer.parseInt(et_recurring_interval.getText().toString());
		}catch(Exception e){}
	    try{
	    	sdk.recurringCount = Integer.parseInt(et_recurring_count.getText().toString());
		}catch(Exception e){}
		try{
			sdk.recurringAmount = Double.parseDouble(et_recurring_amount.getText().toString());
		}catch(Exception e){}
	    try{
	    	sdk.maxAccumulateAmt = Double.parseDouble(et_max_accumulate_amount.getText().toString());
		}catch(Exception e){

		} 
	    try{
			sdk.installmentPeriod = Integer.parseInt(et_installement_period.getText().toString());
		}catch(Exception e){}
	    sdk.interestType = et_interest_type.getText().toString();    //C or M
	    sdk.productCode = et_ipp_product_code.getText().toString();
	    sdk.installmentPeriodFilter = et_ipp_installment_peroid_filter.getText().toString(); //3,6,12
	    sdk.installmentType = InstallmentType.valueOf(spinner_installment_type.getSelectedItem().toString()); // Refer My2c2pSDK.InstallmentType class
	    
	    et_recurring_amount.setText(sdk.recurringAmount+"");
	    et_max_accumulate_amount.setText(sdk.maxAccumulateAmt+"");
	    
	    sdk.accountNo = et_gcash_account_no.getText().toString();
	    sdk.cardPin = et_pin.getText().toString();


	    //end SubMerchant List
		sdk.privateKey = "";
		Gson gson = new Gson();
		Log.d("MainActivity", gson.toJson(sdk)) ;
		Intent intent = new Intent(MainActivity.this, My3DSActivity.class);
		intent.putExtra(My2c2pSDK.PARAMS, sdk);
		startActivityForResult(intent, REQUEST_SDK);
	}
	
	/**
	 * For Mobile Payment Request - Samsung Pay
	 */
	public void requestSDKSamsungPay() {

		final My2c2pSDK sdk = new My2c2pSDK(PRIVATE_KEY);

		double amount = 0;
		try {
			amount = Double.parseDouble(et_amount.getText().toString());
		} catch(Exception e){ }
		
		//set mandatory fields
		sdk.paymentUI = toogle_paymentUI.isChecked();
		sdk.productionMode = toogle_prodMode.isChecked();
		sdk.merchantID = et_merchant_id.getText().toString();
	    sdk.uniqueTransactionCode = et_unique_transaction_id.getText().toString();
		sdk.desc = et_description.getText().toString();
		sdk.amount = amount;
		sdk.currencyCode = et_currency_code.getText().toString();
		sdk.secretKey = et_secretKey.getText().toString();

		if(toogle_paymentUI.isChecked()) {

			//For UI Request				
			//check device availability for Samsung Pay if request with PaymentOption.SAMSUNG_PAY only
			if(MobilePaymentUtils.SamsungPay.isSupported(this)) {
				
				sdk.paymentOption = PaymentOption.SAMSUNG_PAY;
				
				Intent intent = new Intent(MainActivity.this, My3DSActivity.class);
	    		intent.putExtra(My2c2pSDK.PARAMS, sdk);
	    		startActivityForResult(intent, REQUEST_SDK);
			} else {
				//Samsung Pay not supported
				Toast.makeText(this, "This device not support for PaymentOption.SAMSUNG_PAY", Toast.LENGTH_SHORT).show();
			}
		} else {
			
			//For NON-UI Request	
			//set mandatory fields
			sdk.paymentChannel = PaymentChannel.SAMSUNG_PAY; // only support for Non-UI Payment Request, Refer My2c2pSDK.PaymentChannel class
			sdk.samsungPayServiceId = et_samsung_pay_service_id.getText().toString();
			sdk.samsungPayMerchantName = et_samsung_pay_merchant_name.getText().toString();
			
			try {
				//check device availability for Samsung Pay if request with PaymentChannel.SAMSUNG_PAY only
				final SamsungPay samsungPay = new SamsungPayPayment(this, sdk).build();
				samsungPay.getSamsungPayStatus(new StatusListener() {
		            @Override
		            public void onSuccess(int status, Bundle bundle) {
		            	
		            	switch (status) {
		                	case SamsungPay.SPAY_NOT_SUPPORTED:
		                		//SamsungPay not Supported
		        				Toast.makeText(MainActivity.this, "This device not support for PaymentChannel.SAMSUNG_PAY [SamsungPay.SPAY_NOT_SUPPORTED]", Toast.LENGTH_SHORT).show();
		                		break;
		                	case SamsungPay.SPAY_NOT_READY: //Activate SamsungPay or update SamsungPay, if needed.
		                		
		                		if (bundle != null) {
		                			//Please refer onResume() and its only for sdk.paymentUI = false and sdk.paymentChannel = PaymentChannel.SAMSUNG_PAY
		                			//Not required handle this if using sdk.paymentUI = true
		                			mReloadSamsungPayPayment = true;
		                			
		                			int error = bundle.getInt(SamsungPay.EXTRA_ERROR_REASON);
			                		if (SamsungPay.ERROR_SPAY_APP_NEED_TO_UPDATE == error) { 
			                			
			                			//Show SamsungPay update button.
			                			samsungPay.goToUpdatePage();
			                        } else if (SamsungPay.ERROR_SPAY_SETUP_NOT_COMPLETED == error) { 
			                        	
			                        	//Show SamsungPay activate button.
			                        	samsungPay.activateSamsungPay();
			                        }
		                		}
		                		
		                		//Note : after user exit from SamsungPay update or activate, 
		                		//re-call requestSDKSamsungPay() for SamsungPay on onResume() callback,
		                		//Since Samsung Pay SDK not using startActivtyForResult(), so have to handle on onResume() when user back to app,
		                		//its should be on SamsungPay.SPAY_READY status.        		
		                		break;
		                	case SamsungPay.SPAY_READY:
	
		                		//Show SamsungPay payment button and do payment.
		                		Intent intent = new Intent(MainActivity.this, My3DSActivity.class);
		                		intent.putExtra(My2c2pSDK.PARAMS, sdk);
		                		startActivityForResult(intent, REQUEST_SDK);
		                		break; 
		                	default:  // Unknown Status
		                		//SamsungPay Not Supported
		                		Toast.makeText(MainActivity.this, "This device not support for PaymentChannel.SAMSUNG_PAY [Unknown Status]", Toast.LENGTH_SHORT).show();
		                		break;
		            	}
		            }
	
		            @Override
		            public void onFail(int errorCode, Bundle bundle) {
		            	//SamsungPay Not Supported
		            	Toast.makeText(MainActivity.this, "This device not support for PaymentChannel.SAMSUNG_PAY [onFail]", Toast.LENGTH_SHORT).show();
		            }
		        });	
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(MainActivity.this, "Plese check your androidManifest for Samsung <meta> 'spay_sdk_api_level' & 'debug_mode' value", Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		//Only for Samsung Pay, handle back from Samsung Pay app for goToUpdatePage() / activateSamsungPay() and re-do samsung pay payment
		//Check for sdk.paymentUI = false and sdk.paymentChannel = PaymentChannel.SAMSUNG_PAY and mReloadSamsungPayPayment = true;
		//Not required handle this if using sdk.paymentUI = true		
		boolean paymentUI = toogle_paymentUI.isChecked();
		PaymentChannel paymentChannel = PaymentChannel.valueOf(spinner_payment_channel.getSelectedItem().toString());

		if(!paymentUI && paymentChannel.equals(PaymentChannel.SAMSUNG_PAY) && mReloadSamsungPayPayment) {	
			mReloadSamsungPayPayment = false;
			
			requestSDKSamsungPay();
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_SDK) {
			if(data!=null){	
				hideKeyboard(this, et_merchant_id);
				btn_back.setVisibility(View.VISIBLE);
				btn_submit.setVisibility(View.INVISIBLE);
				sv_response.setVisibility(View.VISIBLE);
				sv_data.setVisibility(View.GONE);
				 
				if(resultCode == RESULT_OK){
					System.out.println("result code"+resultCode);
				}else{
					System.out.println("result OK");
				}
				
				My2c2pResponse response = data.getExtras().getParcelable(My2c2pResponse.RESPONSE);
				if(response!=null){
					
					if(response.getRespCode().equals("301")){
						System.out.println(" transaction canceled"+resultCode);
					}
					tv_response.setText(response.toString() + "\n");	
					System.out.println("response"+response.toString());
				}
			}
		}else if (requestCode == REQUEST_CURRENCY) {
			if(data!=null){
				Bundle bundle = data.getExtras();
				selectedCurrencyCode = bundle.getString("selectedCurrencyCode");
				et_currency_code.setText(selectedCurrencyCode);
			}
		}
	}

	public  void hideKeyboard(Context context, View view){
		InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { }

	@Override
	public void onNothingSelected(AdapterView<?> arg0) { }
}