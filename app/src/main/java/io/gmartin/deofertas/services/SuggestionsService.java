package io.gmartin.deofertas.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.os.Process;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

import java.io.Serializable;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.gmartin.deofertas.R;
import io.gmartin.deofertas.activities.SuggestionsActivity;
import io.gmartin.deofertas.controllers.BaseController;
import io.gmartin.deofertas.controllers.SuggestionController;
import io.gmartin.deofertas.models.SuggestedOffer;

public class SuggestionsService extends Service
                                implements SuggestionController.SuggestionControllerListener,
                                           BaseController.BaseControllerListener {

    APIBinder mBinder = new APIBinder();

    private final static int MAIN_ACTIVITY_REQUEST = 1;
    private final static String SERVICE_START_ARGUMENTS = "ServiceStartArguments";
    private final static String LAST_SUGGESTION_ID = "io.gmartin.deofertas.service.last_suggestion_id";
    public final static int NOTIFICATION_ID = 1;
    public final static String SUGGESTION_DATA = "io.gmartin.deofertas.service.suggestion_data";

    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;
    private Timer mTimer = new Timer();
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    private SharedPreferences mPreferences;

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            getLastSuggestions();
        }
    }

    public SuggestionsService(){}

    public void onCreate() {
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        HandlerThread thread = new HandlerThread(SERVICE_START_ARGUMENTS,
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        // Get the HandlerThread's Looper and use it for our Handler
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    public void onDestroy() {
        mTimer.cancel();
    }

    public class APIBinder extends Binder {}

    public void getLastSuggestions() {
        long lastId = 0;

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        lastId = mPreferences.getLong(LAST_SUGGESTION_ID, 0);

        SuggestionController suggestionController = new SuggestionController(SuggestionsService.this);

        suggestionController.fetchLastSuggestion(lastId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /*@Override
    public boolean onUnbind(Intent intent) {
        return false;
    }*/

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        mTimer.schedule(new TimerTask(){
            @Override
            public void run() {
                Message msg = mServiceHandler.obtainMessage();
                msg.arg1 = startId;
                mServiceHandler.sendMessage(msg);
            }
        }, 0, 60000);

        return START_STICKY;
    }

    @Override
    public void onLastSuggestionDataReceived(SuggestedOffer suggestedOffer) {
        if(suggestedOffer != null && suggestedOffer.getId() != null) {
            SharedPreferences.Editor edit = mPreferences.edit();
            edit.putLong(LAST_SUGGESTION_ID, suggestedOffer.getId());
            edit.apply();

            Intent intent = new Intent(SuggestionsService.this, SuggestionsActivity.class);
            intent.putExtra(SUGGESTION_DATA, suggestedOffer.getOffer());

            PendingIntent pendingIntent = PendingIntent.getActivity(SuggestionsService.this, MAIN_ACTIVITY_REQUEST,
                    intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            mBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_shopping_cart_black_24dp)
                    .setContentTitle(getString(R.string.suggestions_new_offer))
                    .setContentIntent(pendingIntent)
                    .setColor(getResources().getColor(R.color.colorPrimary, null))
                    .setSound(defaultSoundUri);

            String notification = String.format("%s: %s.",
                                                suggestedOffer.getOffer().getStoreName(),
                                                suggestedOffer.getOffer().getTitle(),
                                                getResources().getString(R.string.article_price_label),
                                                suggestedOffer.getOffer().getPrice());
            mBuilder.setContentText(notification);

            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
            inboxStyle.setBigContentTitle(getString(R.string.suggestions_new_offer));
            inboxStyle.addLine(String.format("%s: %s.",
                    suggestedOffer.getOffer().getStoreName(),
                    suggestedOffer.getOffer().getTitle()));

            inboxStyle.addLine(String.format("%s: %.2f",
                                getResources().getString(R.string.article_price_label),
                                suggestedOffer.getOffer().getPrice()));

            mBuilder.setStyle(inboxStyle);
            mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        }
    }

    @Override
    public void onSuggestionDataReceived(List<SuggestedOffer> suggestedOffers) {
        //Nothing to do.
    }

    @Override
    public void onErrorEvent(String message) {

    }

    @Override
    public void onDataReceived(Object data) {
        //Nothing to do;
    }
}