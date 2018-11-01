package com.ccpp.demo3ds;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
  
 
public class CurrencyActivity extends ListActivity{

    private CurrencyAdapter currency_adapter;
    private ArrayList<Currency> currency_src;
    public String selectedCurrencyCode;
    public int pos;
        
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          
        selectedCurrencyCode = "";
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.default_list);
        currency_src =  Currency.getCurrencyList();
        currency_adapter = new CurrencyAdapter(this, currency_src);
        setListAdapter(currency_adapter);
        this.getListView().setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        
        Bundle extras = getIntent().getExtras(); 
		if (extras != null){
		   String s = extras.getString("selectedCurrencyCode");
		   if(s!=null && !s.equals("")) selectedCurrencyCode = s;
		}
		
		for(int i=0;i<currency_src.size();i++){
			if(selectedCurrencyCode.equals(currency_src.get(i).getCode())) {
	        	pos = i;
	        	break;
	        }
		}
		getListView().postDelayed(new Runnable() {          
    	    @Override
    	    public void run() {
    	        setSelection(pos);
    	    }
    	},50L);
    }
 
   

    public void onListItemClick(ListView parent, View v, int position, long id) {
    	Currency c = currency_src.get(position);
    	selectedCurrencyCode = c.getCode();
    	pos = position;
    	Intent intent = new Intent();
		intent.putExtra("selectedCurrencyCode", selectedCurrencyCode);
		setResult(RESULT_OK, intent);
    	finish();
    }
       
    public class CurrencyAdapter extends BaseAdapter{

        private ArrayList<Currency> currencySrc;
        private Context context;

        public CurrencyAdapter(Context tmpContext, ArrayList<Currency> arrayList) {
                this.currencySrc = arrayList;
                context = tmpContext;
        }

        public View getView(int position, View view, ViewGroup parent) {
            
            if (view == null) {
            	LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = layoutInflater.inflate(R.layout.row_currency, null);
            } 
            LinearLayout ll_row = (LinearLayout) view.findViewById(R.id.ll_row);
            ll_row.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            TextView tv = (TextView) view.findViewById(R.id.tv_currency);
            Currency currency = (Currency)currencySrc.get(position);
            tv.setText(currency.getName());
           
            if(selectedCurrencyCode.equals(currency.getCode())) {
            	pos = position;
            	ll_row.setBackgroundColor(getResources().getColor(R.color.blue));
            }
            return view;
        }
    	@Override
    	public int getCount() {
    		//Log.d("currnecy count"+ countrySrc.size());
    		if(currencySrc!=null)
    			return currencySrc.size();
    		else
    			return 0;
    	}
    	@Override
    	public Object getItem(int position) {
    		return position;
    	}
    	@Override
    	public long getItemId(int position) {
    		return position;
    	}
    }

   

}

