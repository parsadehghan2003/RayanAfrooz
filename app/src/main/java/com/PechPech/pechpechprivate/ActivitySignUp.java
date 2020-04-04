package com.PechPech.pechpechprivate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.steamcrafted.loadtoast.LoadToast;

import org.imaginativeworld.oopsnointernet.ConnectionCallback;
import org.imaginativeworld.oopsnointernet.NoInternetDialog;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActivitySignUp extends AppCompatActivity {


    Button mButtonSignUp;
    EditText mEditTextCompany;
    EditText mEditTextPassword1;
    EditText mEditTextPassword2;
    EditText mEditTextUsername;
    String mStringUsername;
    NoInternetDialog noInternetDialog;
    ProgressDialog progressDialog;

    /* JADX WARNING: type inference failed for: r3v0, types: [android.content.Context, ir.rayanafrooz.app.ActivitySignUp, androidx.appcompat.app.AppCompatActivity] */
    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        ActivitySignUp.super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        this.mEditTextUsername = (EditText) findViewById(R.id.edt_username);
        this.mEditTextPassword1 = (EditText) findViewById(R.id.edt_password1);
        this.mEditTextPassword2 = (EditText) findViewById(R.id.edt_password2);
        this.mEditTextCompany = (EditText) findViewById(R.id.sp_positions);
        this.mButtonSignUp = (Button) findViewById(R.id.btn_sign_up);
        final RequestQueue mRequestQueueSpinner = Volley.newRequestQueue(this);
        this.mButtonSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                progressDialog = new ProgressDialog(ActivitySignUp.this);
                progressDialog.setMessage("لطفا کمی صبر کنید");

                ActivitySignUp activitySignUp = ActivitySignUp.this;
                activitySignUp.mStringUsername = activitySignUp.mEditTextUsername.getText().toString();
                String mStringPassword1 = ActivitySignUp.this.mEditTextPassword1.getText().toString();
                String mStringPassword2 = ActivitySignUp.this.mEditTextPassword2.getText().toString();
                String mStringCompany = ActivitySignUp.this.mEditTextCompany.getText().toString();
                if (ActivitySignUp.this.mStringUsername.isEmpty() || mStringPassword1.isEmpty() || mStringPassword2.isEmpty() || mStringCompany.isEmpty()) {
                    if (ActivitySignUp.this.mStringUsername.isEmpty()) {
                        ActivitySignUp.this.mEditTextUsername.setError("لطفا شماره همراه خود را وارد کنید");
                    } else if (mStringPassword1.isEmpty()) {
                        ActivitySignUp.this.mEditTextPassword1.setError("لطفا رمز عبور را وارد کنید");
                    } else if (mStringPassword2.isEmpty()) {
                        ActivitySignUp.this.mEditTextPassword2.setError("لطفا رمز عبور را تایید کنید");
                    } else {
                        ActivitySignUp.this.mEditTextCompany.setError("لطفا کد شرکت را وارد کنید");
                    }
                } else if (mStringPassword1.equalsIgnoreCase(mStringPassword2)) {
                    progressDialog.show();
//                    ActivitySignUp.this.lt.setTextColor(ActivitySignUp.this.getColor(2131099879)).setBackgroundColor(ActivitySignUp.this.getColor(2131099696)).setProgressColor(ActivitySignUp.this.getColor(2131099697));
                    if (ActivitySignUp.this.mEditTextUsername.getText().toString().charAt(0) == '0') {
                        ActivitySignUp activitySignUp2 = ActivitySignUp.this;
                        activitySignUp2.mStringUsername = activitySignUp2.mStringUsername.replaceFirst("0","");
                    }
                    final String str = mStringPassword1;
                    final String str2 = mStringCompany;
                    StringRequest mStringRequestSignUp = new StringRequest(Request.Method.POST,

                            "https://"+G.HOST_ADDRESS+"/SignUp",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    progressDialog.dismiss();
                                    JSONObject jsonObject = null;
                                    try {

                                        jsonObject = new JSONObject(response);

                                        if (jsonObject.getInt("success") == 1){

                                            SharedPreferences mSharedPref = ActivitySignUp.this.getSharedPreferences("User", Context.MODE_PRIVATE);
                                            SharedPreferences.Editor mSharedPrefEditor = mSharedPref.edit();
                                            mSharedPrefEditor.putString("username", mEditTextUsername.getText().toString());
                                            mSharedPrefEditor.putString("password", mEditTextPassword1.getText().toString());
                                            mSharedPrefEditor.apply();

                                            Intent mIntent = new Intent(ActivitySignUp.this,ActivityLogin.class);
                                            startActivity(mIntent);

                                        }else if (jsonObject.getInt("success") == 0){
                                            Toast.makeText(ActivitySignUp.this, jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            progressDialog.dismiss();
                        }
                    }){
                        /* access modifiers changed from: protected */
                        public Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            String sb = 0 +
                                    ActivitySignUp.this.mStringUsername;
                            params.put("username", sb);
                            params.put("password", str);
                            params.put("company", str2);
                            return params;
                        }
                    };
                    mRequestQueueSpinner.add(mStringRequestSignUp);
                } else {
                    ActivitySignUp.this.mEditTextPassword2.setError("خطا در تکرار رمز عبور");
                }
            }
        });
    }

    /* JADX WARNING: type inference failed for: r4v0, types: [ir.rayanafrooz.app.ActivitySignUp, androidx.appcompat.app.AppCompatActivity, android.app.Activity] */
    /* access modifiers changed from: protected */
    public void onResume() {
        String str = "عدم دسترسی به اینترنت";
        ActivitySignUp.super.onResume();
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
