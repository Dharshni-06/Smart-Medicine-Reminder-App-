package com.example.smartmedicinereminder;

import java.util.Calendar;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	EditText e1,e2,e3,e4;
	TimePicker t1;
	Button b1;
	SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1=(EditText)findViewById(R.id.editText1);
        e2=(EditText)findViewById(R.id.editText2);
        e3=(EditText)findViewById(R.id.editText3);
        e4=(EditText)findViewById(R.id.editText4);
        t1=(TimePicker)findViewById(R.id.timePicker1);
        b1=(Button)findViewById(R.id.button1);
        b1.setOnClickListener(this);
        
        db=this.openOrCreateDatabase("medicinedb", MODE_PRIVATE, null);
        db.execSQL("create table if not exists medicine(name text,dosage text,caregiver text,doctor text)");
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


	@Override
	public void onClick(View v) {
		String s1= e1.getText().toString();
		String s2=e2.getText().toString();
		String s3=e3.getText().toString();
		String s4=e4.getText().toString();
		
		if(s1.equals("") && s2.equals("") && s3.equals("") && s4.equals("") ){
			Toast.makeText(this, "Please Fill All Fields", Toast.LENGTH_LONG).show();
		}
		else{
			
			String q="insert into medicine values('"+s1+"','"+s2+"','"+s3+"','"+s4+"')";
			db.execSQL(q);
			
			int hour=t1.getCurrentHour();
			int minute=t1.getCurrentMinute();
			
			Calendar c=Calendar.getInstance();
			c.set(Calendar.HOUR_OF_DAY,hour );
			c.set(Calendar.MINUTE,minute );
			c.set(Calendar.SECOND, 0);
			
			Intent i=new Intent(this,ReminderReceiver.class);
			PendingIntent pi=PendingIntent.getBroadcast(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
			
			AlarmManager am=(AlarmManager)getSystemService(ALARM_SERVICE);
			am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
			
			Toast.makeText(getApplicationContext(), "Reminder Set!", Toast.LENGTH_LONG).show();
		}
	}
    
}
