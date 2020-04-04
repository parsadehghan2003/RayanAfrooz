package com.PechPech.pechpechprivate;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

public class G extends Application {

    public static final String HOST_ADDRESS = "parsa.boshrapardaz.ir/PechPechOnWeb";
    public static final int PORT_NUMBER = 9090;
    public static final String WEB_SOCKET_URL = "wss://parsa.boshrapardaz.ir/PechPechOnWeb/chat?username=";
    public static Context context;
    public static String mStringPassword;
    public static String mStringUsername;

    public void onCreate() {
        String str = "User";
        SharedPreferences sharedPreferences = getSharedPreferences(str, 0);
        String str2 = BuildConfig.FLAVOR;
        mStringUsername = sharedPreferences.getString("username", str2);
        mStringPassword = getSharedPreferences(str, 0).getString("password", str2);
        super.onCreate();
        context = getApplicationContext();
    }

    public static int MessageNotReadCount() {
        int MessageNotReadCount = 0;
        List<RecyclerViewDataModel> messages = new DataBase(context).getAllMessages(0);
        for (int i = 0; i < messages.size(); i++) {
            if (((RecyclerViewDataModel) messages.get(i)).isRead() == 0) {
                MessageNotReadCount++;
            }
        }
        return MessageNotReadCount;
    }
}
