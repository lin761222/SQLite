package com.example.testsql;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	private EditText etId;
	private EditText etName;
	private EditText etPhoneNo;
	private EditText etAddress;
	private Button btnInsert;
	private Button btnClear;
	private SitesDBHlp dbHlp;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViews();
	}

	@Override
	public void onResume() {
		super.onResume();
		if (dbHlp == null)
			dbHlp = new SitesDBHlp(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		if (dbHlp != null) {
			dbHlp.close();
			dbHlp = null;
		}
	}

	private void findViews() {
		etId = (EditText) findViewById(R.id.etId);
		etName = (EditText) findViewById(R.id.etName);
		etPhoneNo = (EditText) findViewById(R.id.etPhoneNo);
		etAddress = (EditText) findViewById(R.id.etAddress);
		btnInsert = (Button) findViewById(R.id.btnInsert);
		btnClear = (Button) findViewById(R.id.btnClear);

		btnInsert.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String id = etId.getText().toString().trim();
				String name = etName.getText().toString().trim();
				String phoneNo = etPhoneNo.getText().toString().trim();
				String address = etAddress.getText().toString().trim();
				if (id.length() <= 0 || name.length() <= 0) {
					Toast.makeText(MainActivity.this,
							getString(R.string.blank), Toast.LENGTH_SHORT)
							.show();
					return;
				}

				StringBuilder sb = new StringBuilder();
				Site site = new Site(id, name, phoneNo, address);
				long rowId = dbHlp.insertDB(site);
				if (rowId != -1) {
					sb.append(getString(R.string.insert_success));
				} else {
					sb.append(getString(R.string.insert_fail));
				}
				Toast.makeText(MainActivity.this, sb, Toast.LENGTH_SHORT)
						.show();
			}
		});

		btnClear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				etId.setText("");
				etName.setText("");
				etPhoneNo.setText("");
				etAddress.setText("");
			}
		});
	}
}