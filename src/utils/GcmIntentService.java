package utils;

import activities.ShoppingListActivity;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.idc.milab.mrnote.R;

public class GcmIntentService extends IntentService {
    public static final int NOTIFICATION_ID = 1;

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
            if (GoogleCloudMessaging.
                    MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
              Log.i("testGCM", "Send error: " + extras.toString());
            } else if (GoogleCloudMessaging.
                    MESSAGE_TYPE_DELETED.equals(messageType)) {
            	 Log.i("testGCM", "Deleted messages on server: " + extras.toString());
            // If it's a regular GCM message, do some work.
            } else if (GoogleCloudMessaging.
                    MESSAGE_TYPE_MESSAGE.equals(messageType)) {
               
                sendNotification(intent.getStringExtra("titel"), intent.getStringExtra("msg"), intent.getStringExtra("listId"));
            }
        }
        
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }
	private void sendNotification(String titel, String msg, String listId)
	{
		Intent notificationIntent = new Intent(this, ShoppingListActivity.class);
		notificationIntent.putExtra(ConstService.BUNDLE_LIST_ID, listId);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
		NotificationCompat.Builder mBuilder =
				new NotificationCompat.Builder(this)
		.setContentIntent(pendingIntent)
		.setSmallIcon(R.drawable.logo)
		.setContentTitle(titel)
		.setContentText(msg)
		.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

		NotificationManager mgr = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
		mgr.notify(NOTIFICATION_ID, mBuilder.build());
	}
}
