package kz.shaiba.shaibanews;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){

        SharedPreferences sPref = getSharedPreferences("notifications", MODE_PRIVATE);
        Boolean notification = sPref.getBoolean("breaking_news", true);

        if(notification){
            showNotification(remoteMessage);
        }

    }

    private void showNotification(RemoteMessage remoteMessage) {

        Uri soundUri = Uri.parse("android.resource://" + this.getPackageName() +"/raw/" + R.raw.notif_sound);

        Intent intent = new Intent(this, PostPage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("post_id", remoteMessage.getData().get("post_id"));
        intent.putExtra("date_unix", remoteMessage.getData().get("date_unix"));
        intent.putExtra("title", remoteMessage.getData().get("title_news"));
        intent.putExtra("post_content", "");

        PendingIntent pendingIntent = TaskStackBuilder.create(this)
                .addNextIntentWithParentStack(intent)
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle(remoteMessage.getData().get("title"))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(remoteMessage.getData().get("message")))
                .setSmallIcon(R.drawable.notif_icon)
                .setLargeIcon(BaseUtils.getBitmapFromURL(remoteMessage.getData().get("largeIcon")))
                .setSound(soundUri)
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.notify(0, builder.build());
    }

}
