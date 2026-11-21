package com.example.smartmedicinereminder;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ReminderActivity extends Activity implements OnClickListener{
	TextView tv1;
	Button bt1,bt2,bt3;
	SQLiteDatabase db;
	String caregiver,doctor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reminder);
		tv1=(TextView)findViewById(R.id.text4);
		bt1=(Button)findViewById(R.id.but2);
		bt2=(Button)findViewById(R.id.but3);
		bt3=(Button)findViewById(R.id.but1);
		
		bt1.setOnClickListener(this);
		bt2.setOnClickListener(this);
		bt3.setOnClickListener(this);
		
		db=this.openOrCreateDatabase("medicinedb", MODE_PRIVATE, null);
		Cursor c=db.rawQuery("select * from medicine", null);
		
		if(c.moveToFirst()){
			String name=c.getString(0);
			String dose=c.getString(1);
			 caregiver=c.getString(2);
			 doctor=c.getString(3);
			
			tv1.setText("Take Medicine: "+name+"\n Dosage: "+dose);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.reminder, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
	
		if(v.getId()==R.id.but2){
			Toast.makeText(this, "MEDICINE TAKEN !", Toast.LENGTH_LONG).show();
			finish();
		}
		if(v.getId()==R.id.but3){
			SmsManager sms=SmsManager.getDefault();
			sms.sendTextMessage(caregiver, null, "Medicine NOT Taken!", null, null);
			Toast.makeText(getApplicationContext(), "SMS Sent To Caregiver !", Toast.LENGTH_LONG).show();
		}
		if(v.getId()==R.id.but1){
			Intent intent1 = new Intent(Intent.ACTION_CALL);
			intent1.setData(Uri.parse("tel:"+doctor));
			startActivity(intent1);
		}
	}

}
