package com.example.smartmedicinereminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.widget.Toast;

public class ReminderReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context con, Intent intent) {
		Toast.makeText(con, "Alarm Triggered", Toast.LENGTH_LONG).show();
		
	    final MediaPlayer mp=MediaPlayer.create(con, R.raw.alarm);
		mp.start();
		
		new CountDownTimer(10000,1000){

			@Override
			public void onTick(long millisUntilFinished) {
				
			}
			
			@Override
			public void onFinish() {
				mp.stop();
				mp.release();
			}
			
		}.start();
		
		Intent in = new Intent(con,ReminderActivity.class );
		in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		con.startActivity(in);	
	}

}
