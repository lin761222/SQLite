package com.example.testsql;


import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView tvRow; 
	private TextView tvId; 
	private TextView tvName; 
	private TextView tvPhoneNo; 
	private TextView tvAddress; 
	private Button btnNext; 
	private Button btnBack; 
	private SitesDBHlp dbHlp; 
	private ArrayList<Site> sites; 
	private int index; 
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDB(); 
        findViews();
		showSites(index);
    }

	private void initDB() {
		if(dbHlp == null)
			dbHlp = new SitesDBHlp(this);
		dbHlp.fillDB();	
		sites = dbHlp.getAllSites();
	}

	
	private void showSites(int index) {	
		if(sites.size() > 0){
			tvRow.setText((index + 1) + "/" + sites.size() + 
							getString(R.string.rowid_rowCount));
			tvId.setText(sites.get(index).getId());
			tvName.setText(sites.get(index).getName());
			tvPhoneNo.setText(sites.get(index).getPhoneNo());
			tvAddress.setText(sites.get(index).getAddress());	
		}else{
			tvRow.setText("0/0" + getString(R.string.noData));
			tvId.setText("");
			tvName.setText("");
			tvPhoneNo.setText("");
			tvAddress.setText("");				
		}
	}

	private void findViews() {
		tvRow = (TextView)findViewById(R.id.tvRow);
        tvId = (TextView)findViewById(R.id.tvId);
        tvName = (TextView)findViewById(R.id.tvName);
        tvPhoneNo = (TextView)findViewById(R.id.tvPhoneNo);
        tvAddress = (TextView)findViewById(R.id.tvAddress);     
        btnNext = (Button)findViewById(R.id.btnNext);
        btnBack = (Button)findViewById(R.id.btnBack);
        
        
        btnNext.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				index++;
				if(index >= sites.size())
					index = 0;
				showSites(index);
			}
		});
        
        
        btnBack.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				index--;
				if(index < 0)
					index = sites.size()-1;
				showSites(index);
			}
		});
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if(dbHlp == null)
			dbHlp = new SitesDBHlp(this); 
		sites = dbHlp.getAllSites();
        showSites(index);		
	}

	@Override
	public void onPause() {
		super.onPause();
		if(dbHlp != null){
			dbHlp.close(); 
			dbHlp = null;
		}
		sites.clear(); 
	}
}