package com.PechPech.pechpechprivate;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/* renamed from: ir.rayanafrooz.app.Utils */
public class Utils {
    private static int KEY_MODE_PRIVATE = 0;
    private static String KEY_SESSION_ID = "sessionId";
    private static String KEY_SHARED_PREF = "ANDROID_WEB_NOTIFICATION";
    private Context context;
    private SharedPreferences sharedPref = G.context.getSharedPreferences(KEY_SHARED_PREF, KEY_MODE_PRIVATE);

    public Utils(Context t) {
        this.context = t;
    }

    public void storeSessionId(String sessionId) {
        Editor editor = this.sharedPref.edit();
        editor.putString(KEY_SESSION_ID, sessionId);
        editor.apply();
    }

    public String getSessionId() {
        return this.sharedPref.getString(KEY_SESSION_ID, null);
    }
}
