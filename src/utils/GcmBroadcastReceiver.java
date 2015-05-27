package utils;

import NoteObjects.ShoppingList;
import activities.MainActivity;
import activities.ShoppingListActivity;
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
	public void onReceive(Context context, Intent intent) 
	{
		String action = intent.getAction();
		if (action.equals("com.google.android.c2dm.intent.REGISTRATION")) 
		{	
			String registrationId = intent.getStringExtra("registration_id");
			String error = intent.getStringExtra("error");
			String unregistered = intent.getStringExtra("unregistered");

		}	 
		else if (action.equals("com.google.android.c2dm.intent.RECEIVE")) 
		{	 	
			String titel = intent.getStringExtra("titel");
			String msg = intent.getStringExtra("msg");
			String listId = intent.getStringExtra("listId");
			sendNotification(context, titel, msg, listId);
		}
	}

	private void sendNotification(Context context, String titel, String msg, String listId)
	{
		int NOTIFY_ID=100;
		Intent notificationIntent = new Intent(context, ShoppingListActivity.class);
		notificationIntent.putExtra(ConstService.BUNDLE_LIST_ID, listId);
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
