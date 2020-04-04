package com.PechPech.pechpechprivate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.imaginativeworld.oopsnointernet.ConnectionCallback;
import org.imaginativeworld.oopsnointernet.NoInternetDialog;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActivityLogin extends AppCompatActivity {

    boolean aBoolean;

    Button mButtonLogin;
    TextView mButtonSignUp;
    EditText mEditTextPassword;
    EditText mEditTextUsername;
    RequestQueue mRequestQueue;
    NoInternetDialog noInternetDialog;
    SharedPreferences sharedPreferences;
    String username;
    ProgressDialog progressDialog;

    public void onCreate(Bundle savedInstanceState) {
        ActivityLogin.super.onCreate(savedInstanceState);
        String str = "User";
        this.sharedPreferences = getSharedPreferences(str, 0);
        this.mRequestQueue = Volley.newRequestQueue(this);
        String str2 = "username";
        if (getSharedPreferences(str, 0).getString(str2, null) != null) {
            this.aBoolean = false;
            Login(getSharedPreferences(str, 0).getString(str2, null), getSharedPreferences(str, 0).getString("password", null), false, false);
            return;
        }
        progressDialog = new ProgressDialog(ActivityLogin.this);

        this.aBoolean = true;
        setContentView(R.layout.activity_activiy_login);
        this.mEditTextUsername = (EditText) findViewById(R.id.edt_username);
        this.mEditTextPassword = (EditText) findViewById(R.id.edt_password);
        this.mButtonLogin = (Button) findViewById(R.id.btn_login);
        this.mButtonSignUp = (TextView) findViewById(R.id.button);
        this.mButtonSignUp.setOnClickListener(new View.OnClickListener() {
            /* JADX WARNING: type inference failed for: r1v0, types: [android.content.Context, ir.rayanafrooz.app.ActivityLogin] */
            /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r1v0, types: [android.content.Context, ir.rayanafrooz.app.ActivityLogin]
              assigns: [ir.rayanafrooz.app.ActivityLogin]
              uses: [android.content.Context]
              mth insns count: 6
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
            /* JADX WARNING: Unknown variable types count: 1 */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void onClick(android.view.View r4) {
                Intent intent = new Intent(ActivityLogin.this,ActivitySignUp.class);
                startActivity(intent);
            }
        });
        this.mButtonLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!ActivityLogin.this.mEditTextUsername.getText().toString().isEmpty() && !ActivityLogin.this.mEditTextPassword.getText().toString().isEmpty()) {
                    ActivityLogin activityLogin = ActivityLogin.this;
                    activityLogin.username = activityLogin.mEditTextUsername.getText().toString();
                    if (ActivityLogin.this.mEditTextUsername.getText().toString().charAt(0) == '0') {
                        ActivityLogin activityLogin2 = ActivityLogin.this;
                        activityLogin2.username = activityLogin2.username.replaceFirst("0", "");
                    }
                    ActivityLogin.this.mEditTextUsername.setEnabled(false);
                    ActivityLogin.this.mEditTextPassword.setEnabled(false);
                    ActivityLogin.this.mButtonLogin.setEnabled(false);
                    ActivityLogin.this.mButtonSignUp.setEnabled(false);
                    progressDialog.setMessage("لطفا کمی صبر کنید");
                    progressDialog.show();
                    Login(username, mEditTextPassword.getText().toString(), true, true);
                } else if (ActivityLogin.this.mEditTextUsername.getText().toString().isEmpty()) {
                    ActivityLogin.this.mEditTextUsername.setError("لطفا شماره همراه خود را وارد کنید");
                } else {
                    ActivityLogin.this.mEditTextPassword.setError("لطفا رمز عبور را وارد کنید");
                }
            }
        });
    }

    public void Login(final String username, final String password, final boolean showToast, final boolean showDialog) {
        StringRequest mStringRequestLogin = new StringRequest(Request.Method.POST,
                "https://"+G.HOST_ADDRESS+"/SignIn",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            jsonObject.names();
                            if (showDialog) {
                                progressDialog.dismiss();

                                if (jsonObject.getInt("success") == 1) {

                                    SharedPreferences mSharedPref = ActivityLogin.this.getSharedPreferences("User", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor mSharedPrefEditor = mSharedPref.edit();
                                    mSharedPrefEditor.putString("username", 0+username);
                                    mSharedPrefEditor.putString("password", password);
                                    mSharedPrefEditor.apply();
                                    if (showToast)
                                        Toast.makeText(ActivityLogin.this,  "کاربر عزیز خوش آمدید", Toast.LENGTH_SHORT).show();

                                    Intent mIntent = new Intent(ActivityLogin.this, MainActivity.class);
                                    startActivity(mIntent);
                                    finish();

                                } else if (jsonObject.getInt("success") == 0) {
                                    ActivityLogin.this.mEditTextUsername.setEnabled(true);
                                    ActivityLogin.this.mEditTextPassword.setEnabled(true);
                                    ActivityLogin.this.mButtonLogin.setEnabled(true);
                                    ActivityLogin.this.mButtonSignUp.setEnabled(true);
                                    Toast.makeText(ActivityLogin.this, jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                if (jsonObject.getInt("success") == 1) {
                                    Intent mIntent = new Intent(ActivityLogin.this, MainActivity.class);
                                    startActivity(mIntent);
                                    finish();
                                }else{
                                    SharedPreferences mSharedPref = ActivityLogin.this.getSharedPreferences("User", Context.MODE_PRIVATE);

                                    SharedPreferences.Editor mSharedPrefEditor = mSharedPref.edit();

                                    mSharedPrefEditor.clear();
                                    mSharedPrefEditor.apply();
                                    recreate();

                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (showDialog) {
                    progressDialog.dismiss();

                    new AlertDialog.Builder(ActivityLogin.this)
                            .setTitle("خطای ارتباط با سرور")
                            .setMessage("لطفا از وصل بودن خود به سرور اطمینان حاصل نمایید")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
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
                else {
                    new AlertDialog.Builder(ActivityLogin.this)
                            .setTitle("خطای ارتباط با سرور")
                            .setMessage("لطفا از وصل بودن خود به سرور اطمینان حاصل نمایید")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setCancelable(false)
                            .setPositiveButton("ادامه", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent mIntent = new Intent(ActivityLogin.this, MainActivity.class);
                                    startActivity(mIntent);
                                    finish();
                                }
                            })
                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();


                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    String str = "username";
                    if (showDialog) {
                        String sb = 0 +
                                username;
                        params.put(str, sb);
                    } else {
                        params.put(str, username);
                    }
                    params.put("password", password);
                    return params;
            }
        };
        mRequestQueue.add(mStringRequestLogin);

    }
    public void onResume() {
        String str = "عدم دسترسی به اینترنت";
        super.onResume();
        try {
            NoInternetDialog.Builder noInternetDialog2 = new NoInternetDialog.Builder(this);
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
