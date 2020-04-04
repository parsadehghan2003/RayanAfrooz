package com.PechPech.pechpechprivate;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import net.steamcrafted.loadtoast.LoadToast;
import org.imaginativeworld.oopsnointernet.ConnectionCallback;
import org.imaginativeworld.oopsnointernet.NoInternetDialog;
import org.imaginativeworld.oopsnointernet.NoInternetDialog.Builder;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/* renamed from: ir.rayanafrooz.app.SettingsActivity */
public class SettingsActivity extends AppCompatActivity {

    /* renamed from: lt */
    LoadToast f48lt;
    Button mButtonSetNewPassword;
    EditText mEditTextLastPassword;
    EditText mEditTextNewPassword1;
    EditText mEditTextNewPassword2;
    NoInternetDialog noInternetDialog;

    /* JADX WARNING: type inference failed for: r5v0, types: [android.content.Context, ir.rayanafrooz.app.SettingsActivity, androidx.appcompat.app.AppCompatActivity] */
    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        SettingsActivity.super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        final SharedPreferences mSharedPref = getSharedPreferences("User", 0);
        this.mEditTextLastPassword = (EditText) findViewById(R.id.edt_last_password);
        this.mEditTextNewPassword1 = (EditText) findViewById(R.id.edt_new_password1);
        this.mEditTextNewPassword2 = (EditText) findViewById(R.id.edt_new_password2);
        this.mButtonSetNewPassword = (Button) findViewById(R.id.btn_set_new_password);
        ImageButton button = (ImageButton) findViewById(R.id.imageButton2);
        this.f48lt = new LoadToast(this);
        final RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        this.mButtonSetNewPassword.setOnClickListener(new OnClickListener() {
            /* JADX WARNING: type inference failed for: r0v5, types: [android.content.Context, ir.rayanafrooz.app.SettingsActivity] */
            /* JADX WARNING: type inference failed for: r2v14, types: [android.content.Context, ir.rayanafrooz.app.SettingsActivity] */
            /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r0v5, types: [android.content.Context, ir.rayanafrooz.app.SettingsActivity]
              assigns: [ir.rayanafrooz.app.SettingsActivity]
              uses: [android.content.Context]
              mth insns count: 67
            	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
            	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
            	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
            	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
            	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
            	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
            	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
            	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
            	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
            	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$0(DepthTraversal.java:13)
            	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
            	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:13)
            	at jadx.core.ProcessClass.process(ProcessClass.java:30)
            	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
            	at jadx.api.JavaClass.decompile(JavaClass.java:62)
            	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
             */
            /* JADX WARNING: Unknown variable types count: 2 */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void onClick(android.view.View r9) {
                StringRequest mStringRequestLogin = new StringRequest(Request.Method.POST,
                        "https://"+G.HOST_ADDRESS+"/ChangePassword",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject.getInt("success") == 1) {

                                        SharedPreferences mSharedPref = SettingsActivity.this.getSharedPreferences("User", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor mSharedPrefEditor = mSharedPref.edit();
                                        mSharedPrefEditor.putString("username", mSharedPref.getString("username",null));
                                        mSharedPrefEditor.putString("password", mEditTextNewPassword1.getText().toString());
                                        mSharedPrefEditor.apply();


                                        Toast.makeText(SettingsActivity.this, "تغییر رمز با موفقیت انجام شد", Toast.LENGTH_SHORT).show();

                                    } else {
                                        Toast.makeText(SettingsActivity.this, jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        new AlertDialog.Builder(SettingsActivity.this)
                                .setTitle("خطای ارتباط با سرور")
                                .setMessage("لطفا از وصل بودن خود به سرور اطمینان حاصل نمایید")
                                .setPositiveButton("خروج", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                })

                                // A null listener allows the button to dismiss the dialog and take no further action.
                                .setNegativeButton("دوباره امتحان میکنم", null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("username", mSharedPref.getString("username",null));
                        params.put("password", mEditTextLastPassword.getText().toString());
                        params.put("newPassword", mEditTextNewPassword1.getText().toString());

                        return params;
                    }
                };
                if (mEditTextNewPassword1.getText().toString().equalsIgnoreCase(mEditTextNewPassword2.getText().toString()))
                    mRequestQueue.add(mStringRequestLogin);
                else
                    Toast.makeText(SettingsActivity.this, "خطا در تکرار رمز عبور جدید", Toast.LENGTH_SHORT).show();

            }
        });
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                SettingsActivity.super.onBackPressed();
            }
        });
    }

    /* JADX WARNING: type inference failed for: r4v0, types: [ir.rayanafrooz.app.SettingsActivity, androidx.appcompat.app.AppCompatActivity, android.app.Activity] */
    /* access modifiers changed from: protected */
    public void onResume() {
        String str = "عدم دسترسی به اینترنت";
        SettingsActivity.super.onResume();
        try {
            Builder noInternetDialog2 = new Builder(this);
            noInternetDialog2.setConnectionCallback(new ConnectionCallback() {
                public void hasActiveConnection(boolean b) {
                }
            });
            noInternetDialog2.setNoInternetConnectionTitle(str);
            noInternetDialog2.setNoInternetConnectionMessage("لطفا از اتصال به اینترنت اطمینان حاصل کنید");
            noInternetDialog2.setShowInternetOnButtons(true);
            noInternetDialog2.setPleaseTurnOnText("لطفا اتصال خود را بر قرار کنید");
            noInternetDialog2.setWifiOnButtonText("وای فای");
            noInternetDialog2.setMobileDataOnButtonText("اینترنت همراه");
            noInternetDialog2.setCancelable(false);
            noInternetDialog2.setOnAirplaneModeTitle(str);
            noInternetDialog2.setOnAirplaneModeMessage("شما حالت پرواز را روشن کرده اید");
            noInternetDialog2.setPleaseTurnOffText("لطفا آن را خاموش کنید");
            noInternetDialog2.setAirplaneModeOffButtonText("حالت پرواز");
            noInternetDialog2.setShowAirplaneModeOffButtons(true);
            this.noInternetDialog = noInternetDialog2.build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
