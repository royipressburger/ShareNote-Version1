package utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.idc.milab.mrnote.R;

public class GcmBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {


		Log.i("testGCM", "Im In the BrodcastReciver222222");
		String action = intent.getAction();
		Log.i("testGCM", "Got action");
		Log.i("testGCM", "The Action is" + action);
		if (action.equals("com.google.android.c2dm.intent.REGISTRATION")) 
		{	
			String registrationId = intent.getStringExtra("registration_id");
			String error = intent.getStringExtra("error");
			Log.i("testGCM", registrationId);
			String unregistered = intent.getStringExtra("unregistered");

		}	 
		else if (action.equals("com.google.android.c2dm.intent.RECEIVE")) 
		{	 	
			String titel = intent.getStringExtra("titel");
			String msg = intent.getStringExtra("msg");
			Log.i("testGCM", String.format("I got Notification this is the data! %s %s ", titel, msg));
			sendNotification(context, titel, msg);

		}
	}

	private void sendNotification(Context context, String titel, String msg)
	{
//		NotificationManager notifManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
//		Notification note = new Notification(R.drawable.ic_launcher, titel, System.currentTimeMillis());
//
//		PendingIntent intent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);
//
//		note.setLatestEventInfo(context, titel, msg, intent);
//
//		notifManager.notify(12, note);

		int NOTIFY_ID=100;
		Intent notificationIntent = new Intent(context, Notification.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

		NotificationCompat.Builder mBuilder =
				new NotificationCompat.Builder(context)
		.setContentIntent(pendingIntent)
		.setSmallIcon(R.drawable.logo)
		.setContentTitle(titel)
		.setContentText(msg)
		.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

		NotificationManager mgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		mgr.notify(NOTIFY_ID, mBuilder.build());
	}
}
