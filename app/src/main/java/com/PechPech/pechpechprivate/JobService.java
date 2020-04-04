package com.PechPech.pechpechprivate;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.ConnectivityManager.OnNetworkActiveListener;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat.Builder;
import androidx.core.app.NotificationCompat.DecoratedCustomViewStyle;
import androidx.core.content.ContextCompat;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Random;
import me.leolin.shortcutbadger.ShortcutBadger;
import org.imaginativeworld.oopsnointernet.NoInternetDialog;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: ir.rayanafrooz.app.JobService */
public class JobService extends Service  {
    public static final String CHANNEL_NAME = "Notification Channel";
    public static final String NOTIFICATION_CHANNEL_ID = "channel_id";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_SELF = "self";
    DataBase dataBase;
    int importance = 3;
    String mStringPassword;
    String mStringUsername;
    private Utils mUtils;
    NoInternetDialog noInternetDialog;
    WebSocketClient webSocket;

    public int onStartCommand(Intent intent, int flags, int startId) {
        ShortcutBadger.applyCount(this, G.MessageNotReadCount());
        dataBase = new DataBase(G.context);
        String str = "User";
        SharedPreferences sharedPreferences = getSharedPreferences(str, 0);
        String str2 = BuildConfig.FLAVOR;
        mStringUsername = sharedPreferences.getString("username", str2);
        mStringPassword = getSharedPreferences(str, 0).getString("password", str2);
        mUtils = new Utils(getApplicationContext());
        URI uri = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(G.WEB_SOCKET_URL);
            sb.append(mStringUsername);
            sb.append("&password=");
            sb.append(mStringPassword);
            uri = new URI(sb.toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        if (uri != null) {
            webSocket = new WebSocketClient(uri) {
                public void onOpen(ServerHandshake handshake) {
                    Log.i("status", handshake.getHttpStatusMessage());
                }

                public void onMessage(String message) {
                    parseMessage(message);
                }

                public void onClose(int code, String reason, boolean remote) {
                    onDestroy();
                    ContextCompat.startForegroundService(G.context, new Intent(G.context, JobService.class));
                }

                public void onError(Exception ex) {
                    Log.i("Error", ex.getMessage());
                }
            };
        }
        webSocket.connect();
        return START_STICKY;
    }

    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
    }

    public String byteToHex(byte[] data) {
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[(data.length * 2)];
        for (int i = 0; i < data.length; i++) {
            int v = data[i] & 255;
            hexChars[i * 2] = hexArray[v >>> 4];
            hexChars[(i * 2) + 1] = hexArray[v & 15];
        }
        return new String(hexChars);
    }

    public void parseMessage(String str) {
        String str2 = TAG_MESSAGE;
        try {
            JSONObject mJsonObject = new JSONObject(str);
            String mStringFlag = mJsonObject.getString("flag");
            String str3 = "sessionId";
            if (mStringFlag.equalsIgnoreCase(TAG_SELF)) {
                mUtils.storeSessionId(mJsonObject.getString(str3));
            } else if (mStringFlag.equalsIgnoreCase(str2)) {
                String fromName = G.mStringUsername;
                String message = mJsonObject.getString(str2);
                boolean isSelf = true;
                if (!mJsonObject.getString(str3).equals(mUtils.getSessionId())) {
                    fromName = mJsonObject.getString("name");
                    isSelf = false;
                }
                BuildNotification(new Message(fromName, message, isSelf));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void BuildNotification(Message m) {
        String[] mStringMessageParser = ParsForNotification(m.getMessage());
        if (mStringMessageParser != null) {
            String str2 = " در زمان ";
            String str3 = "رایان";
            if (VERSION.SDK_INT < 26) {
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                Builder contentTitle = new Builder(G.context).setContentTitle(str3);
                StringBuilder sb = new StringBuilder();
                sb.append(mStringMessageParser[0]);
                sb.append(str2);
                sb.append(mStringMessageParser[1]);
                Builder builder = contentTitle.setContentText(sb.toString()).setSmallIcon(R.drawable.ic_launcher_foreground).setAutoCancel(true).setVibrate(new long[]{500, 500, 500, 500, 500}).setLights(-16711681, 500, 1200).setPriority(2).setStyle(new DecoratedCustomViewStyle());
                builder.setSound(RingtoneManager.getDefaultUri(2));
                if (notificationManager != null) {
                    notificationManager.notify(new Random().nextInt(), builder.build());
                    return;
                }
                return;
            }
            ConnectivityManager cm = (ConnectivityManager) G.context.getSystemService("connectivity");
            if (cm != null) {
                cm.addDefaultNetworkActiveListener(new OnNetworkActiveListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    public void onNetworkActive() {
                        webSocket.connect();
                    }
                });
            }
            String str4 = NOTIFICATION_CHANNEL_ID;
            Builder contentTitle2 = new Builder(this, str4).setSmallIcon(R.drawable.ic_launcher_foreground).setContentTitle(str3);
            StringBuilder sb2 = new StringBuilder();
            sb2.append(mStringMessageParser[0]);
            sb2.append(str2);
            sb2.append(mStringMessageParser[1]);
            Builder builder2 = contentTitle2.setContentText(sb2.toString());
            ShortcutBadger.applyNotification(this, builder2.getNotification(), G.MessageNotReadCount());
//            builder2.setContentIntent(PendingIntent.getActivity(G.context, 10, new Intent(G.context, MainActivity.class), ));
            builder2.setSound(RingtoneManager.getDefaultUri(2));
            NotificationChannel notificationChannel = new NotificationChannel(str4, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);
            notificationChannel.setLightColor(-16711936);
            notificationChannel.setVibrationPattern(new long[]{500, 500, 500, 500, 500});
            notificationChannel.setLockscreenVisibility(1);
            NotificationManager notificationManager2 = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager2 != null) {
                notificationManager2.createNotificationChannel(notificationChannel);
                notificationManager2.notify(new Random().nextInt(), builder2.build());
            }
        }
    }

    public void onStart(Intent intent, int startId) {
        Intent intents = new Intent(getBaseContext(), MainActivity.class);
//        intents.setFlags();
        startActivity(intents);
        Log.d("Constraints", "onStart");
    }

    public String[] ParsForNotification(String json) {
        try {
            JSONArray jsonArray = new JSONArray(json);
            if (!jsonArray.getJSONObject(0).getString("username").equalsIgnoreCase(mStringUsername)) {
                return null;
            }
            RecyclerViewDataModel model = new RecyclerViewDataModel();
            String json2 = jsonArray.getJSONObject(0).getString("title");
            String message = jsonArray.getJSONObject(1).toString().trim();
            String a = jsonArray.getJSONObject(0).getString("time");
            model.setDescription(message);
            model.setTitle(json2);
            model.setDate(a);
            model.setRead(0);
            dataBase.addMessage(model);
            ShortcutBadger.applyCount(this, G.MessageNotReadCount());
            if (!getSharedPreferences("Setting", 0).getBoolean("Notification", false)) {
                return null;
            }
            String[] b = {json2, a};
            WebSocketClient webSocketClient = webSocket;
            String sb = "{\"sessionId\":\"" +
                    mUtils.getSessionId() +
                    "\",\"message\":\"\",\"flag\":\"message\"}";
            webSocketClient.send(sb);
            return b;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void onCreate() {
        super.onCreate();
        String CHANNEL_ID = "رایان";
        String CHANNEL_NAME2 = "سرویس ارسال نوتیفیکیشن رایان";
        if (VERSION.SDK_INT >= 26) {
            ((NotificationManager) Objects.requireNonNull(getSystemService(Context.NOTIFICATION_SERVICE))).createNotificationChannel(new NotificationChannel(CHANNEL_ID, CHANNEL_NAME2, NotificationManager.IMPORTANCE_NONE));
            startForeground(101, new Builder(this, CHANNEL_ID).setCategory("service").setAutoCancel(true).setSmallIcon(R.drawable.ic_launcher_foreground).setPriority(-2).build());
        }
    }

}

